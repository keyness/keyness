package com.keyness.thread;

import com.keyness.pojo.Token;
import com.keyness.util.CommonUtil;
import com.keyness.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Keyness on 2017/11/14.
 */
public class TokenThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(TokenThread.class);

    public static String appid = "";
    public static String appsecret = "";
    public static Token accessToken = null;

    @Override
    public void run() {
        while(true){
            try{
                accessToken = CommonUtil.getToken(appid, appsecret);
                if(null != accessToken){
                    TokenUtil.saveToken(accessToken);
                    logger.info("获取access_token成功,有效时长{}秒 token: {}", accessToken.getExpiressIn(), accessToken.getExpiressIn());
                    Thread.sleep((accessToken.getExpiressIn() - 200) * 1000);
                }else{
                    Thread.sleep(60 * 1000);
                }
            }catch(InterruptedException e){
                try{
                    Thread.sleep(60 * 1000);
                }catch(InterruptedException ex){
                    logger.error("{}", ex);
                }
                logger.error("{}",e);
            }
        }
    }
}
