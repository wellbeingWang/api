/*
 * [文 件 名]:BaseServiceDecorator.java
 * [创 建 人]:Wiley
 * [创建时间]:2012-10-12
 * [编　　码]:UTF-8
 * [版　　权]:Copyright © 2012 B5Msoft Co,Ltd. 
 */

package com.fanmila.service.decorator;

import com.fanmila.service.AppServiceFace;
import com.fanmila.service.AppServiceCombination;
//import ServiceFactory;

/**
 * [简要描述]: [详细描述]:
 * 
 * @author [Wiley]
 * @version [版本号,2012-10-12]
 * @see [BaseServiceDecorator]
 * @since [comb5mpluginserver]
 */
public class BaseServiceDecorator extends AbstractAppServiceDecorator {
	private AppServiceCombination factory = new AppServiceCombination();

	public BaseServiceDecorator(AppServiceFace service) {
		super(service);
	}

	/**
	 * [简要描述]: [详细描述]:
	 * 
	 * @return
	 * @exception
	 * @see com.fanmila.plugin.face.IServiceFace#service()
	 */
	@Override
	public String service() {
		AppServiceFace face = factory.decorator(this.service,OperateLogDecorator.class);
		face.setRequestJSON(this.getRequestJSON());
		return face.service();
	}

}
