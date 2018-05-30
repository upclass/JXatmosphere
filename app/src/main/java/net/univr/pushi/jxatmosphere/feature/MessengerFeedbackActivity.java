package net.univr.pushi.jxatmosphere.feature;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.facebook.stetho.common.LogUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static net.univr.pushi.jxatmosphere.remote.ApiConstants.API_BASE_URL;


public class MessengerFeedbackActivity extends BaseActivity {


    @BindView(R.id.upload_pic)
    ImageView uploadPic;
    @BindView(R.id.redy_upload_pic)
    ImageView redyUploadPic;
    @BindView(R.id.feedback_plain)
    EditText feedbackPlain;
    String upload_pic_path;
    PopupWindow popupWindow;
    @BindView(R.id.location)
    TextView location;
//    @BindView(R.id.feedback_submit)
//    Button submit;
    ProgressDialog progressDialog=null;


    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption;

    @Override
    public int getLayoutId() {
        return R.layout.activity_messenger_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initLocation();
    }

    private void initLocation() {
        mlocationClient = new AMapLocationClient(this);
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
//设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //解析定位结果
//                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                        aMapLocation.getLatitude();//获取纬度
//                        aMapLocation.getLongitude();//获取经度
//                        aMapLocation.getAccuracy();//获取精度信息
                        String address = aMapLocation.getAddress();
                        location.setText(address);
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = new Date(aMapLocation.getTime());
//                        df.format(date);//定位时间
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        location.setText("定位失败,请查看是否开启定位权限");
                    }
                }
            }
        });


    }

    @OnClick({R.id.info_feedback_back, R.id.upload_pic, R.id.feedback_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.info_feedback_back:
                finish();
                break;
            case R.id.upload_pic:
                show(context);
                break;
            case R.id.feedback_submit:
                String plain = feedbackPlain.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String time = formatter.format(curDate);
                if (null != plain && !plain.equals("")) {

                    JSONObject object = new JSONObject();
                    try {
                        object.put("position", location.getText().toString());
                        object.put("explain", plain);
                        object.put("time", time);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (redyUploadPic.getVisibility() == View.VISIBLE)
                        upload(upload_pic_path, object);
                    else {
                        Toast.makeText(context, "图片不能为空", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(context, "说明不能为空", Toast.LENGTH_LONG).show();
                }
        }
    }

    private void show(Context context) {
//        submit.setVisibility(View.INVISIBLE);
        View popLayout = LayoutInflater.from(this).inflate(R.layout.popup_messenger_feedback_layout, null);
        popupWindow = new PopupWindow(popLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        Drawable drawable = getResources().getDrawable(R.drawable.popbackground);
        popupWindow.setBackgroundDrawable(drawable);
        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.showAtLocation(uploadPic, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        TextView camera = popLayout.findViewById(R.id.camera);
        TextView photo = popLayout.findViewById(R.id.photo);
        TextView cancle = popLayout.findViewById(R.id.cancle);
        camera.setOnClickListener(view -> PictureSelector.create((Activity) context)
                .openCamera(PictureMimeType.ofImage())
                .forResult(PictureConfig.CHOOSE_REQUEST));

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create((Activity) context)
                        .openGallery(PictureMimeType.ofImage())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
//                submit.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种 path
                    // 1.media.getPath(); 为原图 path
                    // 2.media.getCutPath();为裁剪后 path，需判断 media.isCut();是否为 true
                    // 3.media.getCompressPath();为压缩后 path，需判断 media.isCompressed();是否为 true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    LocalMedia localMedia1 = localMedia.get(0);
                    String path = localMedia1.getPath();
                    Log.i("11111", "onActivityResult: " + path);

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
//                    Bundle bundle = data.getExtras();
//                    Bitmap bitmap = bundle.getParcelable("data");
                    redyUploadPic.setImageBitmap(bitmap);
                    redyUploadPic.setVisibility(View.VISIBLE);
                    upload_pic_path = path;
                    popupWindow.dismiss();
//                    submit.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }


    public void upload(String path, JSONObject json) {
        //构建RequestParams对象，传入请求的服务器地址URL
        progressDialog = ProgressDialog.show(context, "请稍等...", "获取数据中...", true);
        progressDialog.setCancelable(true);
        String url=API_BASE_URL+"feed.do";
        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("file", new File(path)));
        list.add(new KeyValue("parameters", json.toString()));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求结果：" + result);
                progressDialog.dismiss();

//                Gson gson = new Gson();
//                Type listType = new TypeToken<Map<String, String>>() {
//                }.getType();//TypeToken内的泛型就是Json数据中的类型
//                Map<String, String> map = gson.fromJson(result, listType);
//                String a = map.get("errmsg");
//                String b = map.get("errcode");
                Toast.makeText(context, "长传成功", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFinished() {
                //上传完成
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(CancelledException cex) {
                //取消上传
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //上传失败
                LogUtil.e("请求失败：" + ex.toString());
                progressDialog.dismiss();

            }
        });
    }


}
