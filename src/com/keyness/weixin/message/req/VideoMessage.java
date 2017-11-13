package com.keyness.weixin.message.req;

/**
 * Created by Keyness on 2017/11/10.
 */
public class VideoMessage extends BaseMessage {

    //媒体ID
    private String MediaId;
    //语音格式
    private String ThumbMediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
