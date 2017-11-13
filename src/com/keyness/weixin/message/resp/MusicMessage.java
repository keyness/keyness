package com.keyness.weixin.message.resp;

/**
 * Created by Keyness on 2017/11/10.
 */
public class MusicMessage extends BaseMessage {

    //音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        this.Music = music;
    }
}
