/*
 * [文 件 名]:B5MInitInfo.java
 * [创 建 人]:Wiley
 * [创建时间]:2012-10-9
 * [编　　码]:UTF-8
 * [版　　权]:Copyright © 2012 B5Msoft Co,Ltd. 
*/

package com.fanmila.service.listener;


/**
 *	[简要描述]:
 *	[详细描述]:
 *	@author	[Wiley]
 *	@version	[版本号,2012-10-9]
 *	@see		[B5MInitInfo]
 *	@since	[comb5mpluginserver]
 */
public class FanmilaInitInfo
{
    private static String contextPath;
    
    public static String getContextPath()
    {
        return contextPath;
    }

    public static void setContextPath(String contextPath)
    {
        FanmilaInitInfo.contextPath = contextPath;
    }

}
