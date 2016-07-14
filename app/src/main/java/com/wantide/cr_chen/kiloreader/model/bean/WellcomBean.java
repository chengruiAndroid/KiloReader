package com.wantide.cr_chen.kiloreader.model.bean;

/**
 * Created by CR_Chen on 2016/5/25.
 */
public class WellcomBean {

    private String text;//供显示的图片版权信息
    private String img;//图像的 URL

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
