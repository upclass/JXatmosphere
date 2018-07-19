package net.univr.pushi.jxatmosphere.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.feature.PicDealActivity;
import net.univr.pushi.jxatmosphere.utils.PicUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicLoadFragment extends RxLazyFragment {

    @BindView(R.id.pic)
    ImageView pic;
    private ArrayList<String> temp;
    private String url;
    private Bitmap bitmap;
    private String pack;
    private String finalUrl;


    public static PicLoadFragment newInstance(String url, List<String> urls, String pack) {
        PicLoadFragment picLoadFragment = new PicLoadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("pack", pack);
        picLoadFragment.temp = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            picLoadFragment.temp.add(urls.get(i));
        }
        bundle.putStringArrayList("urls", picLoadFragment.temp);
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
        url = "";
        if (getArguments() != null) {
            url = getArguments().getString("url");
            pack = getArguments().getString("pack");
        }
        finalUrl = url;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bitmap = PicUtils.readLocalImageWithouChange(url, pack);
        if (bitmap == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PicUtils.decodeUriAsBitmapFromNet(url, pack);
                    uiHandler.sendEmptyMessage(0);
                }
            }).start();
        }
        pic.setImageBitmap(bitmap);
        pic.setOnClickListener(v -> {
            try {
                if (bitmap == null) {
                    ;
                } else {
                    Intent intent = new Intent(getActivity(), PicDealActivity.class);
                    intent.putExtra("url", finalUrl);
                    intent.putExtra("pack", pack);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putStringArrayListExtra("urls", temp);
                    startActivity(intent);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        uiHandler.removeCallbacksAndMessages(null);
        if (bitmap != null) {
            bitmap.recycle();
            System.gc();
        }

    }


    private Handler uiHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                bitmap = PicUtils.readLocalImageWithouChange(url, pack);
                if (pic != null) {
                    pic.setImageBitmap(bitmap);
                }
            }
        }
    };
}