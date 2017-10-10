package com.fanmila.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IBaseRedisMapper {

	// 判断key是否存在
	public boolean exists(String key);

	// 通过key删除数据
	public boolean delete(String key);
	
	// 通过key设置过期时间
	public Long expire(String key, int seconds);
	
	// 设置过期时间存value为String类型
	public String setString(String key, String value);
	
	// 查value为String类型
	public String getString(String key);

	// 存value为List类型，尾部插入
	public void setListToTail(String key, List<String> strings);
	
	public void setObjectToSet(String key,Object obj);

	public Set<String> getObjectFromSet(String key);

	// 存value为List类型，头部插入
	public void setListToHead(String key, List<String> strings);

	// 存sorted set类型，带权重值，用于排序的需求
	public void setWeight(String key, Double score, String member);

	// 查sorted set类型，带权重值，用于排序，从小到大
	public Set<String> rangeFromSmallToLarge(String key, Long start, Long end);

	// 查sorted set类型，带权重值，用于排序，从大到小
	public Set<String> rangeFromLargeToSmall(String key, Long start, Long end);
	
	
	public boolean setObject(String key,Object obj);
	
	public boolean setObjectWithExpire(String key,int seconds, Object obj);
	
	public Object getObject(String key);

	public Object hgetObject(String key, String field);
	//jedis.set("person:100".getBytes(), SerializeUtil.serialize(person));

	public String hget(String key, String field);
	
	//存map
	public String setMap(String key, Map<String, String> map);
	
	//取map
	public Map<String, String> getMap(String key);
	
	//存set
	public Long setSet(String key, String element);
		
	//取set
	public Set<String> getSet(String key);
	
	public Set<byte[]> getSet(byte[] bytes);
	
	public long hincrby(String key, String fieid, long value);
	
	public long incrby(String key, long value);
	
	public long pfadd(String key, String value);
	
	public long pfcount(String key);

	/** 
	 * @Title: setSortedSet 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param key
	 * @param @param weight
	 * @param @param vlaue    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	void setSortedSet(String key, Double weight, String vlaue);

	/** 
	 * @Title: getSortedSet 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param key
	 * @param @param start
	 * @param @param end
	 * @param @return    设定文件 
	 * @return Set<String>    返回类型 
	 * @throws 
	 */
	Set<String> getSortedSet(String key, long start, long end);
	
}
