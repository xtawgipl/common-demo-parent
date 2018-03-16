package common.demo.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangjj
 * @create 2018-03-14 10:52
 **/
public class RedisConfig {

    public static void main(String[] args) {
        Set<String> sentines = new HashSet<String>(){{
            add("119.29.184.142:26379");
        }};
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentines);
        Jedis jedis = jedisSentinelPool.getResource();
        String test = jedis.get("test");
        System.out.println("test = " + test);
        jedis.set("test", "000000001111111");
        test = jedis.get("test");
        System.out.println("test = " + test);

    }
}
