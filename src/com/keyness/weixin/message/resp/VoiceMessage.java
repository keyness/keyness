package com.keyness.weixin.message.resp;

/**
 * Created by Keyness on 2017/11/10.
 */
public class VoiceMessage extends BaseMessage {

    //语音
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        this.Voice = voice;
    }
}
