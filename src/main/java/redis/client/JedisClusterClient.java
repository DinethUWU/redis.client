package redis.client;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.*;

/**
 * This class use for the communicate with Redis data distribution cluster
 */
public class JedisClusterClient {

    private static final String REDIS_HOST = "127.0.0.1";
    private static final Integer REDIS_PORT = 7002;
    static Logger log = Logger.getLogger(JedisClusterClient.class.getName());
    private JedisCluster jedisCluster = null;

    public JedisClusterClient() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort(REDIS_HOST, REDIS_PORT));
        jedisCluster = new JedisCluster(jedisClusterNode, config);
    }

    private void printValue(Map map) {
        for (Object name : map.keySet()) {
            log.info("key : " + name.toString() + "value :" + map.get(name).toString());
        }
    }

    public void writeData(int numOfDataInstances) {
        for (int i = 1; i <= numOfDataInstances; i++) {
            RedisDataInstance redisDataInstance = new RedisDataInstance();
            jedisCluster.hmset(redisDataInstance.getMapKey(), redisDataInstance.getKeyValueMap());
            printValue(redisDataInstance.getKeyValueMap());
            log.info(redisDataInstance.getMapKey());
        }
    }

    public static void main(String[] args) {
        JedisClusterClient jcc = new JedisClusterClient();
        jcc.writeData(100);
    }
}
