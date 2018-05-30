package net.univr.pushi.jxatmosphere.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicLoadFragment extends RxLazyFragment {

    @BindView(R.id.pic)
    ImageView pic;

    public static PicLoadFragment newInstance(String url) {
        PicLoadFragment picLoadFragment = new PicLoadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        picLoadFragment.setArguments(bundle);
        return picLoadFragment;
    }

    public PicLoadFragment() {
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_pic_load;
    }

    @Override
    public void finishCreateView(Bundle state) {
        String url = "";
        if (getArguments() != null) {
            //取出保存的值
            url = getArguments().getString("url");
        }
//        LogUtils.i(url);
        Picasso.with(getActivity()).load(url).placeholder(R.drawable.app_imageplacehold).into(pic);
    }

}
