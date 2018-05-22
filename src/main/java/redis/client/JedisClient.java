package redis.client;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Map;

/**
 * This class use for the communicate with one redis instance
 */
public class JedisClient {

    private static final String REDIS_HOST = "127.0.0.1";
    private static final Integer REDIS_PORT = 7002;
    static Logger log = Logger.getLogger(JedisClient.class.getName());
    private static JedisPool pool = null;

    public JedisClient() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(200);
        pool = new JedisPool(genericObjectPoolConfig, REDIS_HOST, REDIS_PORT);
    }

    public void addData(int numOfDataInstances) {
        try {
            Jedis jedis = pool.getResource();
            //save to redis
            for (int i = 0; i < numOfDataInstances; i++) {
                RedisDataInstance redisDataInstance = new RedisDataInstance();
                log.info("key: " + redisDataInstance.getMapKey());
                long startTime = System.nanoTime();
                String resultCode = jedis.hmset(redisDataInstance.getMapKey(), redisDataInstance.getKeyValueMap());
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                log.info("Result Code: " + resultCode + "   Writing time:" + duration);
            }
        } catch (JedisException e) {
            log.error("Can not add data to redis cache: " + e.getMessage());
        }
    }

    public void addData() {
        try {
            Jedis jedis = pool.getResource();
            //save to redis
            RedisDataInstance redisDataInstance = new RedisDataInstance();
            log.info("key: " + redisDataInstance.getMapKey());
            long startTime = System.nanoTime();
            String resultCode = jedis.hmset(redisDataInstance.getMapKey(), redisDataInstance.getKeyValueMap());
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            log.info("Result Code: " + resultCode + "   Writing time:" + duration);
        } catch (JedisException e) {
            log.error("Can not add data to redis cache: " + e.getMessage());
        }
    }

    public Map<String, String> readDataWithKey(String key) {
        Map<String, String> retrieveMap = null;
        try {
            Jedis jedis = pool.getResource();
            long startTimeRead = System.nanoTime();
            retrieveMap = jedis.hgetAll(key);
            long endTimeRead = System.nanoTime();
            long durationRead = (endTimeRead - startTimeRead);
            log.info("Total Reading Time:" + durationRead);
            for (String keyMap : retrieveMap.keySet()) {
                System.out.println(keyMap + " " + retrieveMap.get(keyMap));
            }
        } catch (JedisException e) {
            log.error("Can not read data from redis cache: " + e.getMessage());
        }
        return retrieveMap;
    }

    public String readFieldValueWithKey(String key, String field) {
        String value = null;
        try {
            Jedis jedis = pool.getResource();
            long startTimeRead = System.nanoTime();
            value = jedis.hget(key, field);
            long endTimeRead = System.nanoTime();
            long durationRead = (endTimeRead - startTimeRead);
            log.info("Total Scanning Time for one value :" + durationRead);
            log.info("Field Value:" + value);
        } catch (JedisException e) {
            log.error("Can not read data from redis cache: " + e.getMessage());
        }
        return value;
    }

    public static void main(String args[]) {

        JedisClient main = new JedisClient();
        //main.addData();
        main.addData(10);
//      main.readData();
//      main.readDataWithKey("codeBookTbl:.app1-HPXXJXWO.app2-BT7XQPT5.app3-PXT8FMAS.app4-4VP1G4EW.app5-2SWTYFK0.app6-BVQR3GZ3.app7-05LKRB6Y.app8-7MYCEXFJ.app9-EOUNO9QX.app10-AM6XRS6G");
        main.readFieldValueWithKey("codeBookTbl:.app1-HPXXJXWO.app2-BT7XQPT5.app3-PXT8FMAS.app4-4VP1G4EW.app5-2SWTYFK0.app6-BVQR3GZ3.app7-05LKRB6Y.app8-7MYCEXFJ.app9-EOUNO9QX.app10-AM6XRS6G", "app1");


        // main.readDataWithKey("codeBookTbl:.app1-HPXXJXWO.app2-BT7XQPT5.app3-PXT8FMAS.app4-4VP1G4EW.app5-2SWTYFK0.app6-BVQR3GZ3.app7-05LKRB6Y.app8-7MYCEXFJ.app9-EOUNO9QX.app10-AM6XRS6G");
        //main.addData();

    }
}