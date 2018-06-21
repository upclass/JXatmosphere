package net.univr.pushi.jxatmosphere.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.feature.PicDealActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicLoadFragment extends RxLazyFragment {

    @BindView(R.id.pic)
    ImageView pic;
    private static ArrayList<String> temp;
    //    @BindView(R.id.frame)
//    FrameLayout frameLayout;
//    ImageView imageView;

    public static PicLoadFragment newInstance(String url, List<String > urls) {
        PicLoadFragment picLoadFragment = new PicLoadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        temp = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            temp.add(urls.get(i));
        }
        bundle.putStringArrayList("urls", temp);
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
        Picasso.with(getActivity()).load(url).placeholder(R.drawable.app_imageplacehold).into(pic);

        String finalUrl = url;
        pic.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(getActivity(), PicDealActivity.class);
                intent.putExtra("url", finalUrl);
                intent.putStringArrayListExtra("urls",temp);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

//        frameLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (imageView != null) {
//                    pic.setVisibility(View.VISIBLE);
//                    frameLayout.setBackgroundColor(getResources().getColor(R.color.white));
//                    frameLayout.removeView(imageView);
//                }
//
//                return false;
//            }
//        });
//        pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                frameLayout.setBackgroundColor(getResources().getColor(R.color.tine_black));
//                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
//                       800, 800);
//                Drawable drawable = pic.getDrawable();
//                lp.gravity = Gravity.CENTER;
//                imageView = new ImageView(getContext());
//                imageView.setBackgroundColor(getResources().getColor(R.color.white));
//                imageView.setImageDrawable(drawable);
//                imageView.setScaleType(ImageView.ScaleType.MATRIX);
//                imageView.setOnTouchListener(new TouchListener());
//                frameLayout.addView(imageView, lp);
//                pic.setVisibility(View.GONE);
//            }
//        });
//    }

//        pic.setOnClickListener(v -> {
//            try {
//                Drawable drawable = pic.getDrawable();
//                Intent intent = new Intent(getActivity(), PicDealActivity.class);
//                startActivity(intent);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        CallBackUtil.doDispatch(drawable);
//                    }
//                },1000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

//    private byte[] Bitmap2Bytes(Bitmap bitmap) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        return byteArrayOutputStream.toByteArray();
//    }


    // ImageView对象(iv_photo)必须做如下设置后，才能获取其中的图像
//                                    iv_photo.setDrawingCacheEnabled(true);
    // 在ImageView对象(iv_photo)被touch down的时候，获取ImageView中的图像
//    obmp = Bitmap.createBitmap(iv_photo.getDrawingCache());
    //然后在OK按钮(btn_photo)被touch down的时候，比较ImaageView对象(iv_photo)中的图像和
    //obmp是否一致，以便做进一步的处理，比如，如果不一致就保存，否则就不保存到数据库中。
    //从ImaggeView对象中获取图像后，要记得调用setDrawingCacheEnabled(false)清空画图缓
    //冲区，否则，下一次用getDrawingCache()方法回去图像时，还是原来的图像
//                                   iv_photo.setDrawingCacheEnabled(false);
}