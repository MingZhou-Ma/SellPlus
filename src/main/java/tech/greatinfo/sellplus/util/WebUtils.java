package tech.greatinfo.sellplus.util;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ericwyn on 18-7-31.
 */
public class WebUtils {
    public static OkHttpClient client = new OkHttpClient();

    private static final String appid = "wx0ad95240d57cb5ee";
    private static final String appsecret="07618c31603772e3836d003d2262c87c";

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

}