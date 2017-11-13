package com.keyness.weixin.message.req;

/**
 * Created by Keyness on 2017/11/10.
 */
public class TextMessage extends BaseMessage {

    //消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
