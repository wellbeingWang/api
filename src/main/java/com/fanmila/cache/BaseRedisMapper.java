package com.fanmila.cache;

import com.fanmila.util.SerializeUtil;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseRedisMapper implements IBaseRedisMapper {

	private ShardedJedisPool shardedJedisPool;// = ContextUtils.getBean(ShardedJedisPool.class);

	ShardedJedis shardedJedis = null;
	
	/**
	* 从jedis连接池中获取获取jedis对象 
	* @return
	*/
	public ShardedJedis getJedis() {
	return shardedJedisPool.getResource();
	}

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	// 判断key是否存在
	public boolean exists(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return false;
	};

	// 通过key删除数据
	public boolean delete(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.del(key) != null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return false;
	}

	// 通过key设置过期时间
	public Long expire(String key, int seconds) {
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.expire(key, seconds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return null;
	}

	// 存value为String类型
	public String setString(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return null;
	}

	// 查value为String类型
	public String getString(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return null;
	}


	// 存value为List类型，尾部插入
	public void setListToTail(String key, List<String> strings) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			for (String string : strings) {
				// 尾部插入
				shardedJedis.rpush(key, string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
	}

	// 存value为List类型，头部插入
	public void setListToHead(String key, List<String> strings) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			for (String string : strings) {
				// 头部插入
				shardedJedis.lpush(key, string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
	}

	// 存sorted set类型，带权重值，用于排序的需求
	public void setWeight(String key, Double score, String member) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.zadd(key, score, member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
	}

	// 查sorted set类型，带权重值，用于排序，从小到大
	public Set<String> rangeFromSmallToLarge(String key, Long start, Long end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.zrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return null;
	}

	// 查sorted set类型，带权重值，用于排序，从大到小
	public Set<String> rangeFromLargeToSmall(String key, Long start, Long end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.zrevrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return null;
	}

	@Override
	public boolean setObject(String key, Object obj) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.set(key.getBytes(),
					SerializeUtil.serialize(obj)) != null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return false;
	}
	


	public boolean setObjectWithExpire(String key, int seconds, Object obj) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.expire(key, seconds);
			return shardedJedis.set(key.getBytes(),
					SerializeUtil.serialize(obj)) != null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return false;

	}

	public Object getObject(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return SerializeUtil.unserialize(shardedJedis.get(key.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return null;
	}

	public Object hgetObject(String key, String field){
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			byte[] result = shardedJedis.hget(key.getBytes(), field.getBytes("utf-8"));
			if(result==null){
				return null;
			}else{
				return SerializeUtil.unserialize(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return null;
	}

	public String hget(String key, String field){
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			String result = shardedJedis.hget(key, field);
			if(result==null){
				return null;
			}else{
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return null;
	}

	// 存map
	public String setMap(String key, Map<String, String> map) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.hmset(key, map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return null;
	}

	// 取map
	public Map<String, String> getMap(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != shardedJedis) {
				shardedJedis.close();
			}
		}
		return null;
	}
	
	
	// 存set
		public Long setSet(String key, String element) {
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				return shardedJedis.sadd(key, element);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
			return null;
		}

		// 取set
		public Set<String> getSet(String key) {
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				return shardedJedis.smembers(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
			return null;
		}

		@Override
		public void setObjectToSet(String key, Object obj) {
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				shardedJedis.sadd(key.getBytes(), SerializeUtil.serialize(obj));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
			
		}

		@Override
		public Set<String> getObjectFromSet(String key) {
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				return shardedJedis.smembers(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
			return null;
		}
		
		public Set<byte[]> getSet(byte[] bytes){
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				return shardedJedis.smembers(bytes);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
			return null;
		}

		/**
		* 在指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
		*
		*            key
		*            fieid 存储位置
		*            long value 要增加的值,可以是负数
		* @return 增加指定数字后，存储位置的值
		* */
		public long hincrby(String key, String fieid, long value) {
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				long s = shardedJedis.hincrBy(key, fieid, value);
				return s;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
			return 0;
		}
		
		
		public long incrby(String key, long value) {
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				long s = shardedJedis.incrBy(key, value);
				return s;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
			return 0;
		}

		@Override
		public long pfadd(String key, String value) {
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				long s = shardedJedis.pfadd(key, value);
				return s;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
			return 0;
		}

		@Override
		public long pfcount(String key) {
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				long s = shardedJedis.pfcount(key);
				return s;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
			return 0;
		}
		
		/* (非 Javadoc) 
		 * <p>Title: setSortedSet</p> 
		 * <p>Description: </p> 
		 * @param key
		 * @param weight
		 * @param vlaue 
		 * @see com.fanmila.dmp.dao.IBaseRedisMapper#setSortedSet(java.lang.String, java.lang.Double, java.lang.String)
		 */
		@Override
		public void setSortedSet(String key, Double weight, String vlaue) {
			ShardedJedis shardedJedis = null;
			try {
				shardedJedis = shardedJedisPool.getResource();
				shardedJedis.zadd(key, weight, vlaue);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
		}

		/* (非 Javadoc) 
		 * <p>Title: getSortedSer</p> 
		 * <p>Description: </p> 
		 * @param key
		 * @param start
		 * @param end
		 * @return 
		 * @see com.fanmila.dmp.dao.IBaseRedisMapper#getSortedSer(java.lang.String, long, long)
		 */
		@Override
		public Set<String> getSortedSet(String key, long start, long end) {
			ShardedJedis shardedJedis = null;
			// TODO Auto-generated method stub
			try {
				shardedJedis = shardedJedisPool.getResource();
				return shardedJedis.zrevrange(key, start, end);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (null != shardedJedis) {
					shardedJedis.close();
				}
			}
			return null;
		}

}
