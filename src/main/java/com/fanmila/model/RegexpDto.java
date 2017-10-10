/*
 * [文 件 名]:SiteRegexpDto.java
 * [创 建 人]:Wiley
 * [创建时间]:2012-10-11
 * [编　　码]:UTF-8
 * [版　　权]:Copyright © 2012 B5Msoft Co,Ltd. 
*/

package com.fanmila.model;

import java.io.Serializable;

/**
 *	[简要描述]:
 *	[详细描述]:
 *	@author	[Wiley]
 *	@version	[版本号,2012-10-11]
 *	@see		[SiteRegexpDto]
 *	@since	[comb5mpluginserver]
 */
public class RegexpDto implements Serializable
{
    private static final long serialVersionUID = 8847952287907792611L;

    private String name;

    private String regexp;

    public RegexpDto()
    {
        super();
    }

    public RegexpDto(String name, String regexp)
    {
        this.setName(name);
        this.setRegexp(regexp);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRegexp()
    {
        return regexp;
    }

    public void setRegexp(String regexp)
    {
        this.regexp = regexp;
    }

}
