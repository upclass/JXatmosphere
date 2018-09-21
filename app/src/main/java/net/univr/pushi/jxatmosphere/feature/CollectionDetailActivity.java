package net.univr.pushi.jxatmosphere.feature;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.LoginActivity;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.interfaces.BrightnessActivity;
import net.univr.pushi.jxatmosphere.interfaces.CallBackUtil;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.FileUtils;
import net.univr.pushi.jxatmosphere.utils.PicUtils;
import net.univr.pushi.jxatmosphere.utils.ShipeiUtils;

import java.io.File;

import butterknife.BindView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectionDetailActivity extends BaseActivity {
    @BindView(R.id.collection_detail_pic)
    ImageView pic;
    @BindView(R.id.back)
    ImageView back;
    private String url;
    private Bitmap bitmap;
    private String pack;
    private String finalUrl;
    private int collectId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_collection_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        InitViews();
    }

    private void InitViews() {
        bitmap = PicUtils.readLocalImageWithouChange(url, pack);
        if (bitmap == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PicUtils.decodeUriAsBitmapFromNet(url, pack);
                    uiHandler.sendEmptyMessage(0);
                }
            }).start();
        } else {
            pic.setImageBitmap(bitmap);
        }

        pic.setOnLongClickListener(v -> {
            if (bitmap == null) {
                ;
            } else {
                show(context);
                ShipeiUtils.backgroundAlpha(context, 0.5f);
            }
            return true;
        });
        pic.setOnClickListener(v -> {
            try {
                if (bitmap == null) {
                    ;
                } else {
                    Intent intent = new Intent(CollectionDetailActivity.this, PicDealActivity.class);
                    intent.putExtra("url", finalUrl);
                    intent.putExtra("pack", pack);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putStringArrayListExtra("urls", null);
                    startActivity(intent);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        CallBackUtil.setBrightness(new BrightnessActivity() {
            @Override
            public void onDispatchDarken() {
                final Window window = getWindow();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.5f);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.alpha = (Float) animation.getAnimatedValue();
                        window.setAttributes(params);
                    }
                });
                valueAnimator.start();
            }

            @Override
            public void onDispatchLight() {
                final Window window = getWindow();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.5f, 1.0f);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.alpha = (Float) animation.getAnimatedValue();
                        window.setAttributes(params);
                    }
                });

                valueAnimator.start();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIntentData() {
        url = getIntent().getStringExtra("url");
        pack = getIntent().getStringExtra("pack");
        collectId = getIntent().getIntExtra("collectId", -1);
        finalUrl = url;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    private void show(Context context) {
        View popLayout = LayoutInflater.from(context).inflate(R.layout.application_pic_share1, null);
        PopupWindow popupWindow = new PopupWindow(popLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                setting_main.setBackgroundColor(getResources().getColor(R.color.light_white));
                ShipeiUtils.backgroundAlpha(context, 1f);
            }
        });
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.showAtLocation(pic, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        Button send_to_friend = popLayout.findViewById(R.id.send_to_friend);
        Button deleteCollection = popLayout.findViewById(R.id.delete_collection);
        Button save_pic = popLayout.findViewById(R.id.save_pic);
        Button cancle = popLayout.findViewById(R.id.cancle);
        send_to_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File PHOTO_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/images" + "/" + pack);
                String tempFileName;
                int i = url.lastIndexOf("/");
                tempFileName = url.substring(i + 1, url.length());
                File tempFile = new File(PHOTO_DIR, tempFileName);
                showShare(context, url, tempFile.getAbsolutePath());
                popupWindow.dismiss();
            }
        });
        deleteCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                String currentUser = SPUtils.getInstance().getString("userId");
                if (currentUser == null || currentUser.equals("")) {
                    (CollectionDetailActivity.this).removeALLActivity();
                    Intent intent = new Intent(CollectionDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (collectId != -1) {
                        RetrofitHelper.getUserAPI()
                                .deleteCollection(collectId)
                                .compose(bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(collectionRet -> {
                                    String errcode = collectionRet.getErrcode();
                                    if (errcode.equals("0")) {
                                        Intent intent = new Intent();
                                        intent.putExtra("refresh", true); //将计算的值回传回去
                                        setResult(1, intent);
                                        finish(); //结束当前的activity的生命周期
                                        showShortToast("删除成功");
                                    } else
                                        showShortToast("服务异常");
                                }, throwable -> {
                                });
                    }

                }
            }
        });
        save_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File PHOTO_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/images" + "/" + pack);
                String tempFileName;
                int i = url.lastIndexOf("/");
                tempFileName = url.substring(i + 1, url.length());
                File tempFile = new File(PHOTO_DIR, tempFileName);
                FileUtils.saveImageToGallery(context, tempFile, url);
                popupWindow.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void showShortToast(String message) {
        ToastUtils.showShort(message);
        ToastUtils.setBgColor(Color.parseColor("#f6f6f6"));
        ToastUtils.setMsgColor(getResources().getColor(R.color.yujin_black));
    }


    private void showShare(Context context, String path, String localPath) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享图片");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(path);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("您的好友分享了一张图片");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(localPath);//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(path);
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (Wechat.NAME.equals(platform.getName()) ||
                        WechatMoments.NAME.equals(platform.getName())) {
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    paramsToShare.setUrl(path);
                    paramsToShare.setText("您的好友分享了一张图片");
                    paramsToShare.setImageUrl(localPath);
                    paramsToShare.setTitle("分享图片");
                }
                if (SinaWeibo.NAME.equals(platform.getName())) {
                    paramsToShare.setText("您的好友分享了一张图片");
                    paramsToShare.setUrl(path);
                    paramsToShare.setImageUrl(localPath);
                }
                if (QQ.NAME.equals(platform.getName())) {
                    paramsToShare.setTitle("分享图片");
                    paramsToShare.setTitleUrl(path);
                    paramsToShare.setText("您的好友分享了一张图片");
                    paramsToShare.setUrl(path);
                    paramsToShare.setImageUrl(localPath);
                }
            }
        });
        oks.show(context);
    }

    String getModuleName(String pack) {
        String ret = null;
        if (pack.equals("ldptRadar/" + 0)) ret = "雷达拼图";
        else if (pack.equals("ldptRadar/" + 1)) ret = "卫星云图";
        else if (pack.equals("gkdm/000")) ret = "地面填图";
        else if (pack.equals("gkdm/500")) ret = "500百帕高空观测";
        else if (pack.equals("gkdm/700")) ret = "700帕高空观测";
        else if (pack.equals("gkdm/850")) ret = "850帕高空观测";
        else if (pack.equals("gkdm/925")) ret = "925帕高空观测";
        else if (pack.equals("radarForecast/ref")) ret = "回波反射率预报";
        else if (pack.equals("radarForecast/rain")) ret = "降水预报";
        else if (pack.equals("weathWarn/dz")) ret = "地质灾害";
        else if (pack.equals("weathWarn/sh")) ret = "山洪灾害";
        else if (pack.equals("weathWarn/hl")) ret = "中小河流洪水";
        else {
            int i = pack.lastIndexOf("/");
            String substring = pack.substring(i + 1, pack.length());
            ret = substring;
        }
        return ret;
    }
}
