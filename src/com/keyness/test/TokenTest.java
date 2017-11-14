package com.keyness.test;

import com.keyness.pojo.Token;
import com.keyness.util.CommonUtil;
import com.keyness.util.MyX509TrustManager;
import com.keyness.util.TokenUtil;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

/**
 * Created by Keyness on 2017/11/14.
 */
public class TokenTest {

    @Test
    public void testGetToken1() throws Exception{
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx52bec4095b6a3134&secret=05e661c633722ee74caba033739cb44e";
        //建立连接
        URL url = new URL(tokenUrl);
        HttpsURLConnection httpsURLConn = (HttpsURLConnection) url.openConnection();

        TrustManager[] tm = { new MyX509TrustManager() };
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        httpsURLConn.setSSLSocketFactory(ssf);
        httpsURLConn.setDoOutput(true);
        httpsURLConn.setDoInput(true);

        httpsURLConn.setRequestMethod("GET");

        InputStream inputStream = httpsURLConn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuffer buffer = new StringBuffer();
        String str = null;
        while((str = bufferedReader.readLine()) != null){
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        httpsURLConn.disconnect();
        System.out.println(buffer);
    }

    @Test
    public void testGetToken2(){
        Token token = CommonUtil.getToken("wx52bec4095b6a3134","05e661c633722ee74caba033739cb44e");
        System.out.println("access_token: "+token.getAccessToken());
        System.out.println("expires_in: "+token.getExpiressIn());
    }

    @Test
    public void testgetToken(){
        Map<String, Object> token = TokenUtil.getToken();
        System.out.println(token.get("access_token"));
        System.out.println(token.get("expires_in"));
    }

    @Test
    public void testSaveToken(){
        Token token = CommonUtil.getToken("wx52bec4095b6a3134","05e661c633722ee74caba033739cb44e");
        TokenUtil.saveToken(token);
    }

}
