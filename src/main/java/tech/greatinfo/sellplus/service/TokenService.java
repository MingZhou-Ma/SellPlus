package tech.greatinfo.sellplus.service;

import com.alibaba.fastjson.JSONObject;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import tech.greatinfo.sellplus.domain.intf.User;
import tech.greatinfo.sellplus.utils.obj.AccessToken;


/**
 * Created by Ericwyn on 18-3-30.
 */
@Service
public class TokenService {
    private static HashMap<String, AccessToken> tokenMap = new HashMap<>();
    private static final int CLEAN_MAP_TIME = 50;   //清理过期accessToken的时间间隔，单位为分钟
    // token 的过期时间在AccessToken类里面设置

    @Resource
    StringRedisTemplate stringRedisTemplate;

    private boolean startCleanThreadFlag = false;

    public void saveToken(final AccessToken token) {
        startClearCheckCodeMap();
        stringRedisTemplate.opsForValue().set(token.getUuid(), JSONObject.toJSONString(token));
    }

    public boolean isTokenExpired(AccessToken token) {
        return token.isExpired();
    }

    public AccessToken getToken(String token) {
        startClearCheckCodeMap();
        String valueString;
        if ((valueString = stringRedisTemplate.opsForValue().get(token))!=null){
            try {
                AccessToken resToken = JSONObject.parseObject(valueString,AccessToken.class);
                return resToken;
            }catch (Exception e){
                return null;
            }
        }else {
            return null;
        }
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
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key:keys){
            String tokenString;
            // 因为已经使用 json 序列化了，所以可以直接使用 contains 判断是否存在
            if ((tokenString = stringRedisTemplate.opsForValue().get(key)) != null
                    && tokenString.contains(openId)){
                try {
                    AccessToken resToken = JSONObject.parseObject(tokenString,AccessToken.class);
                    return resToken;
                }catch (Exception e){
                    return null;
                }
            }
        }
        return null;
    }

    public void removeToken(String token){
        stringRedisTemplate.delete(token);
    }

    public void startClearCheckCodeMap() {
        if (!startCleanThreadFlag){
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            Set<String> keys = stringRedisTemplate.keys("*");
                            for (String key:keys){
                                try {
                                    AccessToken resToken = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(key),AccessToken.class);
                                    if (resToken.isExpired()){
                                        stringRedisTemplate.delete(key);
                                    }
                                }catch (Exception e){
                                    System.out.println("token service can not parse json : "+stringRedisTemplate.opsForValue().get(key)+":"+new Date());
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

}

