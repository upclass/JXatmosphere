package net.univr.pushi.jxatmosphere.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/04/25
 * desc   :
 * version: 1.0
 */
public class ComPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;


    public ComPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
