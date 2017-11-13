package com.keyness.weixin.message.req;

/**
 * Created by Keyness on 2017/11/10.
 */
public class ImageMessage extends BaseMessage {

    //图片链接
    private String PicUrl;
    private String MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
