package com.keyness.weixin.message.event;

/**
 * Created by Keyness on 2017/11/10.
 */
public class MenuEvent extends BaseEvent {

    //事件KEY值
    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
