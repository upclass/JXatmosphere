package net.univr.pushi.jxatmosphere;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.beens.UserRetBeen;
import net.univr.pushi.jxatmosphere.feature.MainActivity;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.PhoneFormatCheckUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
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
    long lastSmsClick;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void onResume() {
        super.onResume();
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message m = Message.obtain();
                m.what = 1;
                m.arg1 = event;//event
                m.arg2 = result;//result
                mHandler.sendMessage(m);
            }
        });
        sms_button.setOnClickListener(v -> {
            long now = System.currentTimeMillis();
            String phone = re_phone.getText().toString();
            if (TextUtils.isEmpty(phone))
                Toast.makeText(context, "手机号不能为空", Toast.LENGTH_SHORT).show();
            else {
                boolean chinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
                if (chinaPhoneLegal == true) {
                    // 注册一个事件回调，用于处理发送验证码操作的结果
                    // 触发操作
                    if ((now - lastSmsClick) < 60000) {
                        ToastUtils.showShort("请60s后重新发送");
                    } else
                        SMSSDK.getVerificationCode("86", phone);
                } else
                    Toast.makeText(context, "手机号码错误", Toast.LENGTH_SHORT).show();

            }
            lastSmsClick = now;
        });
    }


    @OnClick(R.id.login)
    public void onViewClicked() {
        String phone = re_phone.getText().toString();
        boolean chinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
        String code = re_code.getText().toString();
        boolean b = chinaPhoneLegal && !TextUtils.isEmpty(code);
        if (!chinaPhoneLegal)
            Toast.makeText(context, "请填写正确手机号", Toast.LENGTH_SHORT).show();
        else if (code.isEmpty())
            Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show();
        if (b)
            SMSSDK.submitVerificationCode("86", phone, code);
//        {
//            Intent intent = new Intent(context, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        {
//            Intent intent = new Intent(context, MainActivity.class);
//            SPUtils.getInstance().put("userId", "18702515202");
//            startActivity(intent);
//            SPUtils.getInstance().put("isFirstLogin", false);
//            finish();
//        }
    }

    private Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case 1:
                int event = msg.arg1;
                int result = msg.arg2;
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // TODO 处理成功得到验证码的结果
                        // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        Toast.makeText(context, "验证码已发出,请注意查收", Toast.LENGTH_SHORT).show();
                    } else {
                        // TODO 处理错误的结果
                        Toast.makeText(context, "验证码不能正常发出", Toast.LENGTH_SHORT).show();
                    }
                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // TODO 处理验证码验证通过的结果
                        String phone = re_phone.getText().toString();
                        RetrofitHelper.getUserAPI()
                                .userVerify(phone)
                                .compose(bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(userBeen -> {
                                    String errmsg = userBeen.getErrmsg();
                                    String errcode = userBeen.getErrcode();
                                    UserRetBeen.UserBean user = userBeen.getUser();
                                    if (errcode.equals("0")) {
                                        Intent intent = new Intent(context, MainActivity.class);
                                        SPUtils.getInstance().put("userId", user.getUserid());
                                        startActivity(intent);
                                        SPUtils.getInstance().put("isFirstLogin", false);
                                        finish();
                                    } else
                                        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
                                }, throwable -> {
                                    LogUtils.e(throwable);
                                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                                });
                    } else {
                        // TODO 处理错误的结果
                        Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return false;
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }
}
