package com.adachina.common.util.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 *KryoRedisSerializer
* @Description:
* @author zzm
* @date 2017年12月5日 下午2:41:21
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T> {  
  
    @Override  
    public byte[] serialize(Object obj) throws SerializationException {  
        if (null == obj) {
			return EMPTY_ARRAY;
		}
         return KryoUtil.writeToByteArray(obj);
  
    }  
    
    static final byte[] EMPTY_ARRAY = new byte[0];

	static boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}
  
  
    @Override  
    public T deserialize(byte[] bytes) throws SerializationException {  
    	 if (isEmpty(bytes)) {
  			return null;
  		}
        return KryoUtil.readFromByteArray(bytes);
    }  
  
}  