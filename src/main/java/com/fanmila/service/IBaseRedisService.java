package com.fanmila.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IBaseRedisService {

	// 判断key是否存在
	public boolean exists(String key); 

	// 存value为String类型
	public String setString(String key, String value);

	// 查value为String类型
	public String getString(String key);

	// 存value为List类型，尾部插入
	public void setListToTail(String key, List<String> strings);

	// 存value为List类型，头部插入
	public void setListToHead(String key, List<String> strings);
	
	public void setObjectToSet(String key,Object obj);
	
	// 存过期时间的value
	public String setValueWithExpire(String key, int seconds, String value);

	// 取对象类型
	public Object getObject(String key);

	public Object hgetObject(String key, String field);

	public String hget(String key, String field);

	// 存对象类型
	public boolean setObject(String key, Object obj);

	// 存过期时间的对象类型
	public boolean setObjectWithExpire(String key, int seconds, Object obj);

	// 删除
	public boolean delete(String key);

	// 存map
	public String setMap(String key, Map<String, String> map);

	// 取map
	public Map<String, String> getMap(String key);
	
	public Long setSet(String key,  String string) ;
	
	public Set<String> getSet(String string) ;
	
	public Set<byte[]> getSet(byte[] bytes);

	/**
	 * public Long expire(String key, int seconds)
	* @Title: hincrby 
	* @Description: 計數功能
	* @param @param key
	* @param @param fieid
	* @param @return    设定文件 
	* @return long    返回类型 
	* @throws
	 */
	public long hincrby(String key, String fieid);
	
	/**
	 * 
	* @Title: incrby 
	* @Description: String键值的计数功能
	* @param @param key
	* @param @param value
	* @param @return    设定文件 
	* @return long    返回类型 
	* @throws
	 */
	public long incrby(String key, long value);
	
	/**
	 * 
	* @Title: expire 
	* @Description: 設置鍵值的過期時間
	* @param @param key
	* @param @param seconds
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	public Long expire(String key, int seconds);
	
	/**
	 * 
	* @Title: pfadd 
	* @Description: pfadd
	* @param @param key
	* @param @param value
	* @param @return    设定文件 
	* @return long    返回类型 
	* @throws
	 */
	public long pfadd(String key, String value);
	
	/**
	 * 
	* @Title: pfcount 
	* @Description: pfcount
	* @param @param key
	* @param @return    设定文件 
	* @return long    返回类型 
	* @throws
	 */
	public long pfcount(String key);
	
	public void setSortedSet(String key, Double weight, String vlaue);
	
	public Set<String> getSortedSet(String key, long start, long end);
}
