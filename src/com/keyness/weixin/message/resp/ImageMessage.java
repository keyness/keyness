package com.keyness.weixin.message.resp;

/**
 * Created by Keyness on 2017/11/10.
 */
public class ImageMessage extends BaseMessage {

    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        this.Image = image;
    }
}
