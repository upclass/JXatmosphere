package net.univr.pushi.jxatmosphere.widget;

import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/05/07
 * desc   :
 * version: 1.0
 */


public class MySnapHelper extends LinearSnapHelper {


    Weizhi weizhi;

    private int flag;//左滑还是右划 1右划一位  -1左滑一位   0不滑    2找到了滑动位子

    private int postion;

    public void setWeizhi(Weizhi weizhi) {
        this.weizhi = weizhi;
    }

    public int getFlag() {
        return flag;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        postion = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        if (postion == -1 && velocityX > 0) {
            flag = 1;
        } else if (postion == -1 && velocityX < 0) {
            if (postion == 0) {
                postion = 0;
                flag = 0;
            } else flag = -1;
        }else flag=2;
        weizhi.returnWeizhi(postion);
        return super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
    }


    public interface Weizhi {
        void returnWeizhi(int position);
    }

}
