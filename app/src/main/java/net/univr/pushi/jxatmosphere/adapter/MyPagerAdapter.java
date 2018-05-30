package net.univr.pushi.jxatmosphere.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/04/25
 * desc   :
 * version: 1.0
 */


public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private FragmentManager mFragmentManager;


    public MyPagerAdapter(FragmentManager fm, List<Fragment> list,List<Fragment> Huancun) {
        super(fm);
        this.mFragmentManager = fm;
        clearFragfment(Huancun,list);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public void clearFragfment(List<Fragment> Huancun,List<Fragment> list) {
        if (Huancun != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            for (Fragment f : Huancun) {
                fragmentTransaction.remove(f);
            }
            fragmentTransaction.commit();
            mFragmentManager.executePendingTransactions();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
