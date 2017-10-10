package com.fanmila.cron;

import com.fanmila.cache.IPTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 系统任务调度入口
 * @author lscm
 *	秒 分 时 日 月 周 年(可选)
 * 	 秒                         0-59                               , - * /  
               分                         0-59                               , - * /  
               小时                     0-23                               , - * /  
               日                         1-31                               , - * ? / L W C  
               月                         1-12 or JAN-DEC         , - * /  
               周几                     1-7 or SUN-SAT           , - * ? / L C #  
               年 (可选字段)     empty, 1970-2099      , - * /
    “?”字符：表示不确定的值

   “,”字符：指定数个值

   “-”字符：指定一个值的范围

   “/”字符：指定一个值的增加幅度。n/m表示从n开始，每次增加m

   “L”字符：用在日表示一个月中的最后一天，用在周表示该月最后一个星期X

   “W”字符：指定离给定日期最近的工作日(周一到周五)

   “#”字符：表示该月第几个周X。6#3表示该月第3个周五
   
   2）Cron表达式范例：

                 每天23点执行一次：0 0 23 * * ?

                 每天凌晨1点执行一次：0 0 1 * * ?

                 每月1号凌晨1点执行一次：0 0 1 1 * ?

                 每月最后一天23点执行一次：0 0 23 L * ?

                 每周星期天凌晨1点实行一次：0 0 1 ? * L

                 在26分、29分、33分执行一次：0 26,29,33 * * * ?

                 每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
 */
//@Component
public class CronService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/**
	 * 定时每天3点0分执行cps规则变更数据缓存
	 */
	@Scheduled(cron="0 0 0/10 * * ?")
	public void updateDBHandlerCatch()
	{

	}
	
	@Scheduled(cron="0 0 1 1 * ?")
	public void downIpFile(){
		IPTool.downloadIpFile();
	}

}
