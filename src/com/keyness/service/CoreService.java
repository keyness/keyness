package com.keyness.service;

import com.keyness.util.MessageUtil;
import com.keyness.weixin.message.resp.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by Keyness on 2017/11/10.
 */
public class CoreService {

    public static String processRequest(HttpServletRequest request){
        //xm格式的消息数据
        String respXml = null;
        //默认返回的文本消息内容
        String respContent = "未知的消息类型!";

        try{
            //调用parseXml方法解析请求消息
            Map<String ,String> requestMap = MessageUtil.parseXml(request);
            //发送方
            String fromUserName = requestMap.get("FromUserName");
            //开发者
            String toUserName = requestMap.get("ToUserName");
            //消息类型
            String msgType = requestMap.get("MsgType");

            //回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
                respContent = "你发送的是文本消息.";
            }else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
                respContent = "你发送的是图片消息.";
            }else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
                respContent = "你发送的是语音消息.";
            }else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)){
                respContent = "你发送的是视频消息.";
            }else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)){
                respContent = "你发送的是小视频消息.";
            }else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOACTION)){
                respContent = "你发送的是地理位置消息.";
            }else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
                respContent = "你发送的是连接消息.";
            }else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
                String eventType = requestMap.get("Event");
                if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
                    respContent = "感谢儿子的关注.";
                }else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){

                }else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){

                }else if(eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)){

                }else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){

                }
            }
            textMessage.setContent(respContent);
            respXml = MessageUtil.messageToXml(textMessage);
        }catch(Exception e){
            e.printStackTrace();
        }
        return respXml;
    }

}
