package net.univr.pushi.jxatmosphere.beens;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/06/04
 * desc   :
 * version: 1.0
 */


public class MultiItemGdybTx implements MultiItemEntity {
    public static final int TIME_TEXT = 1;
    public static final int IMG = 2;


    private int itemType;

    public GkdmClickBeen getContent() {
        return content;
    }

    public void setContent(GkdmClickBeen content) {
        this.content = content;
    }

    private GkdmClickBeen content;
    private int resId;

    public MultiItemGdybTx(int itemType, GkdmClickBeen content) {
        this.itemType = itemType;
        this.content = content;
    }

    public MultiItemGdybTx(int itemType, int resId) {
        this.itemType = itemType;
        this.resId = resId;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
