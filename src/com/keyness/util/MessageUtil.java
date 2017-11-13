package com.keyness.util;

import com.keyness.weixin.message.resp.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Keyness on 2017/11/10.
 */
public class MessageUtil {

    //请求消息类型:文本
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    //请求消息类型:图片
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    //请求消息类型:语音
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    //请求消息类型:视频
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    //请求消息类型:小视频
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    //请求消息类型:地理位置
    public static final String REQ_MESSAGE_TYPE_LOACTION = "location";
    //请求消息类型:链接
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
    //请求消息类型:事件推送
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    //事件类型:subscribe(订阅)
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    //事件类型:unsubscribe(取消订阅)
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    //事件类型:scan(用户已关注时的扫描带参数二维码)
    public static final String EVENT_TYPE_SCAN = "scan";
    //事件类型:LOCATION(上报地理位置)
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
    //事件类型:CLICK(自定义菜单)
    public static final String EVENT_TYPE_CLICK = "CLICK";

    //响应消息类型:文本
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    //响应消息类型:图片
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
    //响应消息类型:语音
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
    //响应消息类型:视频
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
    //响应消息类型:音乐
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
    //响应消息类型:图文
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 解析微信发来的请求
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        //解析结果存进HashMap中
        Map<String, String> map = new HashMap<String, String>();

        //从request中取得输入流
        InputStream inputStream = request.getInputStream();
        //读取输入流
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        //得到xml根元素
        Element root = document.getRootElement();
        //得到根元素的所有子节点
        List<Element> elementList = root.elements();

        for(Element e: elementList){
            map.put(e.getName(),e.getText());
        }

        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * 扩展xstream支持CDATA
     */
    private static XStream xstream = new XStream(new XppDriver(){
        public HierarchicalStreamWriter createWriter(Writer out){
            return new PrettyPrintWriter(out){
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz){
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text){
                    if(cdata){
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }else{
                        writer.write(text);
                    }
                }

            };
        }
    });

    /**
     * 文本消息对象转换成xml
     * @param textMessage
     * @return
     */
    public static String messageToXml(TextMessage textMessage){
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 图片消息对象转换成xml
     * @param imageMessage
     * @return
     */
    public static String messageToXml(ImageMessage imageMessage){
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }

    /**
     * 语音消息对象转换成xml
     * @param voiceMessage
     * @return
     */
    public static String messageToXml(VoiceMessage voiceMessage){
        xstream.alias("xml", voiceMessage.getClass());
        return xstream.toXML(voiceMessage);
    }

    /**
     * 视频消息对象转换成xml
     * @param videoMessage
     * @return
     */
    public static String messageToXml(VideoMessage videoMessage){
        xstream.alias("xml", videoMessage.getClass());
        return xstream.toXML(videoMessage);
    }

    /**
     * 音乐消息对象转换成xml
     * @param musicMessage
     * @return
     */
    public static String messageToXml(MusicMessage musicMessage){
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    public static String messageToXml(NewsMessage newsMessage){
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

}
