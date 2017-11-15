package com.keyness.util;

import com.keyness.menu.Menu;
import com.keyness.pojo.AccessToken;
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
 * Created by Keyness on 2017/11/15.
 */
public class WeixinUtil {
    private static Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    public static int createMenu(Menu menu, String accessToken){
        int result = 0;
        String url = menu_create_url.replace("ACCESS_TOKEN",accessToken);
        String jsonMenu = JSONObject.fromObject(menu).toString();
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
        if(jsonObject != null){
            if(0 != jsonObject.getInt("errcode")){
                result = jsonObject.getInt("errcode");
                logger.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return result;
    }

    public static AccessToken getAccessToken(String appid, String appsecret){
        AccessToken accessToken = null;

        String requestUrl = access_token_url.replace("APPID",appid).replace("APPSECRET",appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

        if(null != jsonObject){
            try{
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            }catch(JSONException e){
                accessToken = null;
                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr){
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();

        try{
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpsURLConn = (HttpsURLConnection) url.openConnection();
            httpsURLConn.setSSLSocketFactory(ssf);

            httpsURLConn.setDoInput(true);
            httpsURLConn.setDoOutput(true);
            httpsURLConn.setUseCaches(false);

            httpsURLConn.setRequestMethod(requestMethod);

            if("GET".equalsIgnoreCase(requestMethod)){
                httpsURLConn.connect();
            }
            if(null != outputStr){
                OutputStream outputStream = httpsURLConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpsURLConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while((str = bufferedReader.readLine()) != null){
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            httpsURLConn.disconnect();
            jsonObject = jsonObject.fromObject(buffer.toString());
        }catch(ConnectException ce){
            logger.error("Weixin server connection timed out.");
        }catch(Exception e){
            logger.error("https request error:{}", e);
        }
        return jsonObject;
    }

}
