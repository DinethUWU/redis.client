package redis.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RedisDataInstance {

    private Map<String,String> hmap=new HashMap();
    private String MAP_KEY="codeBookTbl:";
    private final String APP_NAME="app";
    private final String SALT_CHARS="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public RedisDataInstance(){
        genarateValueMapWithMapkey();
    }

    private void genarateValueMapWithMapkey(){
        for(int i=1;i<=10;i++) {
            hmap.put(APP_NAME + i, getSaltString());
            String appValue = "." + APP_NAME + i + "-" + getSaltString();
            MAP_KEY=MAP_KEY.concat(appValue);
        }
    }

    public String getMapKey(){
        return MAP_KEY;
    }

    public Map<String, String> getKeyValueMap(){
        return hmap;
    }

    private String getSaltString() {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALT_CHARS.length());
            salt.append(SALT_CHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}

