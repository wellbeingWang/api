package com.fanmila.cache;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.fanmila.model.common.CacheConstant;
import com.fanmila.model.omslvm.LVMChannelFilterModel;
import com.fanmila.model.omslvm.LVMMerchantFilterModel;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.framework.ContextUtils;

/**
 * 
 * 
 * @author lscm
 * 
 */
public class LVMRedisSynch {
	private static Logger logger = LoggerFactory.getLogger(LVMRedisSynch.class);

	// jdbcTemplate
	private JdbcTemplate jdbcTemplate = (JdbcTemplate) ContextUtils
			.getBean("jdbcTemplate");

	private BaseRedisServiceImpl baseRedisService = ContextUtils
			.getBean(BaseRedisServiceImpl.class);

	/**
	 * 
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void inintLVMCatchPool() throws Exception {
		
		String merchantLVMSql = "select t1.merchant_id, t2.merchant_domain, t3.business_name, t1.city_name, "
				+ " t1.product_id, t1.time_point, t1.is_open, t1.uuid_last_no, "
				+ " t1.uuid_start, t1.uuid_end  "
				+ " from oms_fact_merchant_data t1 "
				+ " left join dmp_public_dim_merchant t2 on t1.merchant_id=t2.merchant_id"
				+ " left join oms_dim_business t3 on t1.business_id=t3.business_id";

		List<LVMMerchantFilterModel> lvmMerchantList = jdbcTemplate.query(merchantLVMSql,
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return new LVMMerchantFilterModel(rs.getInt("product_id"), rs
								.getString("merchant_domain"), rs
								.getString("business_name"), rs
								.getString("city_name"), rs
								.getString("time_point"), rs
								.getString("uuid_last_no"), rs
								.getInt("uuid_start"), rs.getInt("uuid_end"),
								rs.getString("is_open"));
					}
				});

		String channelLVMSql = "select t1.channel_id, t2.channel_number, t3.business_name, t1.city_name, "
				+ " t1.product_id, t1.time_point, t1.is_open, t1.uuid_last_no, "
				+ " t1.uuid_start, t1.uuid_end  "
				+ " from oms_fact_channel_data t1 "
				+ " left join oms_dim_channel t2 on t1.channel_id=t2.channel_id"
				+ " left join oms_dim_business t3 on t1.business_id=t3.business_id";

		for (LVMMerchantFilterModel lvmMerchant : lvmMerchantList) {
			String key = CacheConstant.OMS_LVM + "_" + lvmMerchant.getProductId()
					+ "_" + lvmMerchant.getMerchantHost();
			baseRedisService.setObjectToSet(key, lvmMerchant);
		}

		List<LVMChannelFilterModel> lvmChannelList = jdbcTemplate.query(channelLVMSql,
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return new LVMChannelFilterModel(rs.getInt("product_id"), rs.getString("channel_number"), rs.getString("business_name"), 
								rs.getString("city_name"), rs.getString("time_point"), rs.getString("uuid_last_no"), rs.getInt("uuid_start"),
								rs.getInt("uuid_end"),rs.getString("is_open"));
					}
				});

		for (LVMChannelFilterModel lvmChannel : lvmChannelList) {
			String key = CacheConstant.OMS_LVM + "_" + lvmChannel.getProductId()
					+ "_" + lvmChannel.getChannel();
			baseRedisService.setObjectToSet(key, lvmChannel);
		}

	}

	public static void flash() {

		try {
			new LVMRedisSynch().inintLVMCatchPool();
		} catch (Exception e) {
			for (int i = 1; i <= 5; i++) {
				try {
					logger.info("**************Flash　Error,Try agine :time is  "
							+ i);
					Thread.sleep(1000);
					new LVMRedisSynch().inintLVMCatchPool();
					logger.info("**************Flash　Success,Try agine :time is  "
							+ i);
					break;
				} catch (Exception e1) {
					logger.error(e.getMessage(), e1);
				}
			}
		}
	}

}
