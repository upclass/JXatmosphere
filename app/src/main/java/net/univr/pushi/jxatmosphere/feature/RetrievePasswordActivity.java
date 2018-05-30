package net.univr.pushi.jxatmosphere.feature;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.remote.RetrofitHelper;
import net.univr.pushi.jxatmosphere.utils.PhoneFormatCheckUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrievePasswordActivity extends BaseActivity {

    @BindView(R.id._phone)
    EditText re_phone;
    @BindView(R.id.retrieve_password_code)
    EditText re_code;


    @OnClick({R.id.retrieve_password_back, R.id.get_sms_code, R.id.go_next_retrieve})
    public void onViewClicked(View view) {
        String phone = re_phone.getText().toString();
        switch (view.getId()) {
            case R.id.retrieve_password_back:
                finish();
                break;
            case R.id.get_sms_code:
                if (TextUtils.isEmpty(phone))
                    Toast.makeText(context, "手机号不能为空", Toast.LENGTH_SHORT).show();
                else {
                    boolean chinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
                    if (chinaPhoneLegal == true) {
                        isExitPhone(re_phone.getText().toString());
                    } else
                        Toast.makeText(context, "手机号码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_next_retrieve:
                boolean chinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
                String code = re_code.getText().toString();
                boolean b = chinaPhoneLegal && !TextUtils.isEmpty(code);
                if(!chinaPhoneLegal) Toast.makeText(context, "请填写正确手机号", Toast.LENGTH_SHORT).show();
                else if(code.isEmpty()) Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show();
                if (b)
                    SMSSDK.submitVerificationCode("86", phone, code);
                break;
        }
    }

    private void isExitPhone(String phone) {
        RetrofitHelper.getUserAPI()
                .userVerify(phone)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userBeen -> {
                    String errcode = userBeen.getErrcode();
                    String errmsg = userBeen.getErrmsg();
                    if (errcode.equals("0")) {
                        SMSSDK.registerEventHandler(new EventHandler() {
                            public void afterEvent(int event, int result, Object data) {
                                Message m = Message.obtain();
                                m.what = 1;
                                m.arg1 = event;//event
                                m.arg2 = result;//result
                                mHandler.sendMessage(m);
                            }
                        });
                        // 触发操作
                        SMSSDK.getVerificationCode("86", phone);
                    } else Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    LogUtils.e(throwable);
                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_retrieve_password;
    }


    private Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case 1:
                if (msg.arg2 == SMSSDK.RESULT_COMPLETE) {//发送成功的情况
                    if (msg.arg1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//验证成功通过
                        Toast.makeText(context, "验证码正确", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, RetrievePasswordNextActivity.class);
                        String phone = re_phone.getText().toString();
                        intent.putExtra("phone", phone);
                        context.startActivity(intent);
                        finish();
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
}
