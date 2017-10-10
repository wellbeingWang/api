/*
 * [文 件 名]:ServiceFactory.java
 * [创 建 人]:Wiley
 * [创建时间]:2012-10-12
 * [编　　码]:UTF-8
 * [版　　权]:Copyright © 2012 B5Msoft Co,Ltd. 
*/

package com.fanmila.service;

//import org.pricehistory.api.HBaseConnection;

import com.fanmila.service.decorator.BaseServiceDecorator;
import com.fanmila.service.impl.*;


/**
 *	[简要描述]:
 *	[详细描述]:
 *	@author	
 *	@email	
 *	@version	[版本号,2012-10-12]
 *	@see		[ServiceFactory]
 *	@package	
 *	@since	[comb5mpluginserver]
 */
public class ServiceFactory
{

	
	/**
	 * 
	* @Title: createOmsLVMService 
	* @Description: 运营管理系统 
	* @param @return    设定文件 
	* @return AppServiceFace    返回类型 
	* @throws
	 */
	public AppServiceFace createOmsLVMService() {
		return new BaseServiceDecorator(new OmsLVMServiceImpl());
	}
	/**
	 * 
	* @Title: createLv1DHConfigService 
	* @Description: 导航站配置管理接口
	* @param @return    设定文件 
	* @return AppServiceFace    返回类型 
	* @throws
	 */
	public AppServiceFace createLv1DHConfigService() {
		return new BaseServiceDecorator(new LV1DaoHangConfigServiceImpl());
	}

	public AppServiceFace createCltService() {
		return new BaseServiceDecorator(new CltServiceImpl());
	}

	public AppServiceFace createPluginService() {
		return new BaseServiceDecorator(new PluginServiceImpl());
	}


	public AppServiceFace createCpsRedirectService() {
		return new BaseServiceDecorator(new CpsRedirectServiceImpl());
	}

	public AppServiceFace createTaoVasRedirectService() {
		return new BaseServiceDecorator(new TaoVasRedirectServiceImpl());
	}



}
