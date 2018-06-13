package net.univr.pushi.jxatmosphere.feature;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.widget.TouchListener;

import butterknife.BindView;

public class PicDealActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView image;

//implements DispatchDrawable
//    @Override
//    public void onDispatchBitmap(Drawable drawable) {
//        image.setImageDrawable(drawable);
//        image.setOnTouchListener(new TouchListener());
//    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pic_deal;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("url");
        Picasso.with(this).load(url).placeholder(R.drawable.app_imageplacehold).into(image);
        image.setOnTouchListener(new TouchListener());

//        CallBackUtil.setDispatchDrawable(this);
//      try {
//          Intent intent = getIntent();
//          if (intent != null) {
//              byte [] mypics = intent.getByteArrayExtra("picture");
//              Bitmap bitmap = BitmapFactory.decodeByteArray(mypics,0,mypics.length);
//              image.setImageBitmap(bitmap);
//          }
//      }catch (Exception e){
//          e.printStackTrace();
//      }
    }


}
