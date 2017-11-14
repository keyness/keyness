package com.keyness.util;

import com.keyness.pojo.Token;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

/**
 * Created by Keyness on 2017/11/14.
 */
public class CommonUtil {

    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static final String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr){
        JSONObject jsonObject = null;
        try{
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod(requestMethod);

            if(null != outputStr){
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while(( str = bufferedReader.readLine()) != null){
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = jsonObject.fromObject(buffer.toString());
        }catch(ConnectException ce){
            logger.error("连接超时:{}", ce);
        }catch(Exception e){
            logger.error("https请求异常:{}", e);
        }
        return jsonObject;
    }

    public static Token getToken(String appid, String appsecret){
        Token token = null;
        String requestUrl = token_url.replace("APPID",appid).replace("APPSECRET",appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

        if(null != jsonObject){
            try{
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiressIn(jsonObject.getInt("expires_in"));
            }catch(JSONException e){
                token = null;
                logger.error("获取token失败 errcode:{} errmsg:{}",jsonObject.getInt("errcode"),jsonObject.getString("errmsg"));
            }
        }
        return token;
    }

}
