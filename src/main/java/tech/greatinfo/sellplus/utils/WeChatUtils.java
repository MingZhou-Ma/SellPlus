package tech.greatinfo.sellplus.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by Ericwyn on 18-7-31.
 */
public class WeChatUtils {
    public static OkHttpClient client = new OkHttpClient();

    private static final String appid = "wxce729655183dc5cc";
    private static final String appsecret="dde6e8a32671b894a770d35632663e5e";

    /*@Value("${appid}")
    private static String appid;

    @Value("${appsecret}")
    private static String appsecret;*/

    public static String getAccessToken() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful() && response.code() == 200){
            String resp = response.body().string();
            if (resp.contains("access_token")){
                return JSONObject.parseObject(resp).getString("access_token");
            }else {
                return null;
            }
        }else {
            return null;
        }
    }




    /**
     * 将头像小图 url 转变为 大图 url
     * 其实就是将 url 最末尾的尺寸数字变为 0
     * @param url
     * @return
     */
    public static String getBigAvatarURL(String url){
        if (url.endsWith("/132")){
            url = url.substring(0,url.length()-3)+"0";
        }
        return url;
    }

}