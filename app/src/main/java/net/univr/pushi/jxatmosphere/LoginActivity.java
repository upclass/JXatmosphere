package net.univr.pushi.jxatmosphere;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.feature.MainActivity;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.phone)
    EditText re_phone;
    @BindView(R.id.phone_code)
    EditText re_code;
    @BindView(R.id.send_code)
    TextView sms_button;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sms_button.setOnClickListener(v -> {
//            String phone = re_phone.getText().toString();
//            if (TextUtils.isEmpty(phone))
//                Toast.makeText(context, "手机号不能为空", Toast.LENGTH_SHORT).show();
//            else {
//                boolean chinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
//                if (chinaPhoneLegal == true) {
//                    // 注册一个事件回调，用于处理发送验证码操作的结果
//                    SMSSDK.registerEventHandler(new EventHandler() {
//                        public void afterEvent(int event, int result, Object data) {
//                            Message m = Message.obtain();
//                            m.what = 1;
//                            m.arg1 = event;//event
//                            m.arg2 = result;//result
//                            mHandler.sendMessage(m);
//                        }
//                    });
//                    // 触发操作
//                    SMSSDK.getVerificationCode("86", phone);
//                } else
//                    Toast.makeText(context, "手机号码错误", Toast.LENGTH_SHORT).show();
//            }
//
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }


    private Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case 1:
                if (msg.arg2 == SMSSDK.RESULT_COMPLETE) {//发送成功的情况
                    if (msg.arg1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//验证成功通过
                        Toast.makeText(context, "验证码正确", Toast.LENGTH_SHORT).show();
                        String phone = re_phone.getText().toString();

                        RetrofitHelper.getUserAPI()
                                .userVerify(phone)
                                .compose(bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(userBeen -> {
                                    String errmsg = userBeen.getErrmsg();
                                    String errcode = userBeen.getErrcode();
                                    if (errcode.equals("0")) {
                                        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(context, MainActivity.class);
                                        intent.putExtra("phone",phone);
                                        startActivity(intent);
                                        finish();
                                    } else
                                        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
                                }, throwable -> {
                                    LogUtils.e(throwable);
                                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                                });
                    } else if (msg.arg1 == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//验证码已经从服务器发出
                        Toast.makeText(context, "验证码已发出,请注意查收", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return false;
    });


    @OnClick(R.id.login)
    public void onViewClicked() {
//        String phone = re_phone.getText().toString();
//        boolean chinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
//        String code = re_code.getText().toString();
//        boolean b = chinaPhoneLegal && !TextUtils.isEmpty(code);
//        if (!chinaPhoneLegal)
//            Toast.makeText(context, "请填写正确手机号", Toast.LENGTH_SHORT).show();
//        else if (code.isEmpty())
//            Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show();
//        if (b)
//            SMSSDK.submitVerificationCode("86", phone, code);

        Intent intent=new Intent(context, MainActivity.class);
//        intent.putExtra("phone",phone);
        startActivity(intent);
        SPUtils.getInstance().put("isFirstLogin",false);
        finish();
    }
}
