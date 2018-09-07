package tech.greatinfo.sellplus.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.intf.User;
import tech.greatinfo.sellplus.utils.EncryptUtils;
import tech.greatinfo.sellplus.utils.obj.AccessToken;


/**
 * Created by Ericwyn on 18-3-30.
 */
@Service
public class TokenService {
    private static HashMap<String, AccessToken> tokenMap = new HashMap<>();
    private static final int CLEAN_MAP_TIME = 60;   //清理过期accessToken的时间间隔，单位为分钟
    // token 的过期时间在AccessToken类里面设置

    private boolean startCleanThreadFlag = false;

    private CustomService customService;

    public void saveToken(final AccessToken token) {
        startClearCheckCodeMap();
        tokenMap.put(token.getUuid(),token);
    }

    public AccessToken saveToken(Customer customer) {
        AccessToken token = new AccessToken();
        token.setUser(customer);
        String uuid = encryptCustomer(customer);
        token.setUuid(uuid);
        startClearCheckCodeMap();
        tokenMap.put(token.getUuid(),token);
        return token;
    }

    public boolean isTokenExpired(AccessToken token) {
        return token.isExpired();
    }

    public AccessToken getToken(String token) {
        return tokenMap.get(token);
    }

    public User getUserByToken(String token){
        AccessToken tokenTemp = null;
        if((tokenTemp=getToken(token))!=null){
            if(tokenTemp.isExpired()){
                return null;
            }else {
                return tokenTemp.getUser();
            }
        }else {
            return null;
        }
    }

    //通过 openid 获取 已有的 Token , 来刷新过期时间
    public AccessToken getTokenByCustomOpenId(String openId){
        for (String token:tokenMap.keySet()){
            if (tokenMap.get(token).getUser() instanceof Customer &&
                    ((Customer)tokenMap.get(token).getUser()).getOpenid().equals(openId)){
                return tokenMap.get(token);
            }
        }
        return null;
    }

    public void removeToken(String token){
        tokenMap.remove(token);
    }

    public void startClearCheckCodeMap() {
        if (!startCleanThreadFlag){
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            Set<String> keys = tokenMap.keySet();
                            for (String key:keys){
                                if(tokenMap.get(key).isExpired()){
                                    tokenMap.remove(key);
                                }
                            }
                        }
                    },
                    CLEAN_MAP_TIME,
                    CLEAN_MAP_TIME,
                    TimeUnit.MINUTES);
            startCleanThreadFlag = true;
        }
    }

    private static String KEY_1 = "59e16db268f549098a6163b3ca621188";
    private static String KEY_2 = "77a01a8f5467421cb7ac96d1023a8061";

    private String encryptCustomer(Customer customer){
        return EncryptUtils.aes128Encrypt((EncryptUtils.aes128Encrypt(customer.getOpenid(), KEY_1)+"&encrypt&"+System.currentTimeMillis()), KEY_2);
    }

    public String getOpenIdFromOldToken(String token){
//        System.out.println("oldToken : "+token);
        String temp = EncryptUtils.aes128Decrypt(token,KEY_2);
        if (temp == null){
            return null;
        }
        String[] spl = temp.split("&encrypt&");
        //        String timeStemp = EncryptUtils.ase128_decrypt(spl[1]);
        return EncryptUtils.aes128Decrypt(spl[0], KEY_1);
    }
}

