package com.fanmila.service.impl;

import com.fanmila.cache.BaseRedisMapper;
import com.fanmila.service.IBaseRedisService;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class BaseRedisServiceImpl implements IBaseRedisService {

	private BaseRedisMapper baseRedisMapper ;//= ContextUtils.getBean(BaseRedisMapper.class);
	
	// 判断key是否存在
	public boolean exists(String key) {
		return baseRedisMapper.exists(key);
	}


	// 存value为String类型
	public String setString(String key, String value) {
		return baseRedisMapper.setString(key, value);
	}

	// 查value为String类型
	public String getString(String key) {
		return baseRedisMapper.getString(key);
	}

	// 存value为List类型，尾部插入
	public void setListToTail(String key, List<String> strings) {
		baseRedisMapper.setListToTail(key, strings);
	};

	// 存value为List类型，头部插入
	public void setListToHead(String key, List<String> strings) {
		baseRedisMapper.setListToHead(key, strings);
	}

	// 存value为String类型
	public String setValueWithExpire(String key, int seconds,String value) {
		baseRedisMapper.setString(key, value);
		baseRedisMapper.expire(key, seconds);
		return "";
	}
	
	//存过期时间的对象类型
	public boolean setObjectWithExpire(String key, int seconds,Object obj) {
		baseRedisMapper.setObject(key, obj);
		baseRedisMapper.expire(key, seconds);
		return true;
	}
	
	//取对象类型
	public Object getObject(String key) {
		return baseRedisMapper.getObject(key);
	}

	public Object hgetObject(String key, String field){
		return baseRedisMapper.hgetObject(key, field);
	}

	public String hget(String key, String field){
		return baseRedisMapper.hget(key, field);
	}
	
	//删除
	public boolean delete(String key) {
		return baseRedisMapper.delete(key);
	}

	//存对象类型
	public boolean setObject(String key, Object obj) {
		return baseRedisMapper.setObject(key, obj);
	}

	/*
	 * <p>Title: setMap</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @param map
	 * @return 
	 */
	public String setMap(String key, Map<String, String> map) {
		return baseRedisMapper.setMap(key, map);
	}

	/*
	 * <p>Title: getMap</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @return 
	 */
	public Map<String, String> getMap(String key) {
		return baseRedisMapper.getMap(key);
	}

	public Set<String> getSet(String key) {
		// TODO Auto-generated method stub
		return baseRedisMapper.getSet(key);
	}
	
	public Long setSet(String key, String element) {
		// TODO Auto-generated method stub
		return baseRedisMapper.setSet(key, element);
	}

	@Override
	public void setObjectToSet(String key, Object obj) {
		baseRedisMapper.setObjectToSet(key, obj);
		
	}

	public Set<byte[]> getSet(byte[] bytes) {
		// TODO Auto-generated method stub
		return baseRedisMapper.getSet(bytes);
	}
	
	/**
	 * 計數功能
	 */
	public long hincrby(String key, String fieid){
		return baseRedisMapper.hincrby(key, fieid, 1);
	}
	
	/**
	 * 計數功能
	 */
	public long incrby(String key, long value){
		return baseRedisMapper.incrby(key, value);
	}
	
	
	//存过期时间的对象类型
	public Long expire(String key, int seconds) {
		return baseRedisMapper.expire(key, seconds);
	}

	@Override
	public long pfadd(String key, String value) {
		// TODO Auto-generated method stub
		return baseRedisMapper.pfadd(key, value);
	}

	@Override
	public long pfcount(String key) {
		// TODO Auto-generated method stub
		return baseRedisMapper.pfcount(key);
	}


	public BaseRedisMapper getBaseRedisMapper() {
		return baseRedisMapper;
	}


	public void setBaseRedisMapper(BaseRedisMapper baseRedisMapper) {
		this.baseRedisMapper = baseRedisMapper;
	}

	/* (非 Javadoc) 
	 * <p>Title: setSortedSet</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @param weight
	 * @param vlaue 
	 */
	@Override
	public void setSortedSet(String key, Double weight, String vlaue) {
		baseRedisMapper.setSortedSet(key, weight, vlaue);
	}

	/* (非 Javadoc) 
	 * <p>Title: getSortedSet</p> 
	 * <p>Description: </p> 
	 * @param key
	 * @param start
	 * @param end
	 * @return 
	 */
	@Override
	public Set<String> getSortedSet(String key, long start, long end) {
		// TODO Auto-generated method stub
		return baseRedisMapper.getSortedSet(key, start, end);
	}

}
