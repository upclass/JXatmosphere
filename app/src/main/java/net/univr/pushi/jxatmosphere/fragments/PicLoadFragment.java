package net.univr.pushi.jxatmosphere.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.LoginActivity;
import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.base.RxLazyFragment;
import net.univr.pushi.jxatmosphere.feature.PicDealActivity;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.FileUtils;
import net.univr.pushi.jxatmosphere.utils.PicUtils;
import net.univr.pushi.jxatmosphere.utils.ShipeiUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                PicUtils.decodeUriAsBitmapFromNet(url, pack);
                uiHandler.sendEmptyMessage(0);
            }
        }).start();
        if (bitmap != null)
            pic.setImageBitmap(bitmap);


        pic.setOnLongClickListener(v -> {
            if (bitmap == null) {
                ;
            } else {
                show(getContext());
                ShipeiUtils.backgroundAlpha(getContext(), 0.5f);
            }
            return true;
        });
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


    private void show(Context context) {
        View popLayout = LayoutInflater.from(context).inflate(R.layout.application_pic_share, null);
        PopupWindow popupWindow = new PopupWindow(popLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                setting_main.setBackgroundColor(getResources().getColor(R.color.light_white));
                ShipeiUtils.backgroundAlpha(getContext(), 1f);
            }
        });
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.showAtLocation(pic, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        Button send_to_friend = popLayout.findViewById(R.id.send_to_friend);
        Button collection = popLayout.findViewById(R.id.collection);
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
                showShare(getContext(), url, tempFile.getAbsolutePath());
                popupWindow.dismiss();
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                String currentUser = SPUtils.getInstance().getString("userId");
                if (currentUser == null || currentUser.equals("")) {
                    ((BaseActivity) getContext()).removeALLActivity();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    RetrofitHelper.getUserAPI()
                            .addCollection(currentUser, url, getModuleName(pack))
                            .compose(bindToLifecycle())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(collectionRet -> {
                                String errcode = collectionRet.getErrcode();
                                if (errcode.equals("0"))
                                    showShortToast("收藏成功");
                                else
                                    showShortToast("网络异常");
                            }, throwable -> {
                            });
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
            public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
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


//    private void initPopUpWindow(View root, Context mContext){
//        Log.d("click","init popopop");
//        //inflate得到布局 ，底部弹出框的View
//        final View popView = LayoutInflater.from(mContext).inflate(
//                R.layout.application_pic_func, null);
//        View rootView = root; // 当前页面的根布局
//        //创建popUpWindow对象 宽高占满页面
//        final PopupWindow popupWindow = new PopupWindow(popView,
//                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        popupWindow.setTouchable(true);
//        // 设置弹出动画
//        popupWindow.setAnimationStyle(R.style.pop_anim);
//        // 显示在根布局的底部
//        popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0,
//                0);
//        //点击底部弹出框之外的部分让popUpWindow 消失
//        popView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int height = popView.findViewById(R.id.ll_popupwindow).getTop();
//                int y=(int) event.getY();
//                if(event.getAction()==MotionEvent.ACTION_UP){
//                    if(y<height){
//                        popupWindow.dismiss();
//                    }
//                }
//                return true;
//            }
//        });
//        //弹出框中控件的点击事件
//        TextView share_facebook= (TextView) popView.findViewById(R.id.popwindow_facebook);
//        share_facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                share_facebook(uuid,title);
//                popupWindow.dismiss();
//            }
//        });
//        }


}