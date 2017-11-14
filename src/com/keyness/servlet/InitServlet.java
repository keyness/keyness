package com.keyness.servlet;

import com.keyness.thread.TokenThread;
import com.keyness.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Keyness on 2017/11/14.
 */
@WebServlet(name = "InitServlet")
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    @Override
    public void init() throws ServletException {
        TokenThread.appid = getInitParameter("appid");
        TokenThread.appsecret = getInitParameter("appsecret");

        log.info("weixin api appid:{}", TokenThread.appid);
        log.info("weixin api appsecret:{}", TokenThread.appsecret);

        if("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)){
            log.error("appid and appsecret configuration error, please check carefully.");
        }else{
            new Thread(new TokenThread()).start();
        }
    }
}
