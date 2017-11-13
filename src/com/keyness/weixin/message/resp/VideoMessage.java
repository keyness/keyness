package com.keyness.weixin.message.resp;

/**
 * Created by Keyness on 2017/11/10.
 */
public class VideoMessage extends BaseMessage {

    //视频
    private Video Video;

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        this.Video = video;
    }
}
