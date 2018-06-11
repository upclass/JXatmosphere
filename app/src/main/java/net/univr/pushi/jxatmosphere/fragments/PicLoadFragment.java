package net.univr.pushi.jxatmosphere.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.widget.TouchListener;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicLoadFragment extends RxLazyFragment {

    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.frame)
    FrameLayout frameLayout;
    ImageView imageView;

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
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (imageView != null) {
                    pic.setVisibility(View.VISIBLE);
                    frameLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    frameLayout.removeView(imageView);
                }

                return false;
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setBackgroundColor(getResources().getColor(R.color.tine_black));
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                       800, 800);
                Drawable drawable = pic.getDrawable();
                lp.gravity = Gravity.CENTER;
                imageView = new ImageView(getContext());
                imageView.setBackgroundColor(getResources().getColor(R.color.white));
                imageView.setImageDrawable(drawable);
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                imageView.setOnTouchListener(new TouchListener());
                frameLayout.addView(imageView, lp);
                pic.setVisibility(View.GONE);
            }
        });
    }

}
