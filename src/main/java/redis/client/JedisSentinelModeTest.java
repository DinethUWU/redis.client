package redis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class JedisSentinelModeTest {
    private Set sentinels;


    public void setSentinels(String host){
        sentinels=new HashSet();
        sentinels.add(host);
    }

    public Set getSentinels(){
        return this.sentinels;
    }

    public static void main(String[] args){
        JedisSentinelModeTest obj=new JedisSentinelModeTest();
        obj.setSentinels("127.0.0.1:5000");
        obj.setSentinels("127.0.0.1:5001");
        obj.setSentinels("127.0.0.1:5002");
        JedisSentinelPool pool = new JedisSentinelPool("mymaster",obj.getSentinels());

        Jedis jedis =pool.getResource();
        jedis.set("key4","value1");
        jedis.set("key5","value2");
        jedis.set("key6","value3");
        jedis.close();
    }
}
