package net.univr.pushi.jxatmosphere.feature;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;
import net.univr.pushi.jxatmosphere.utils.PhoneFormatCheckUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegistActivity extends BaseActivity {
    @BindView(R.id.re_phone)
    EditText re_phone;
    @BindView(R.id.re_code)
    EditText re_code;
    @BindView(R.id.get_sms_code)
    Button sms_button;
    @BindView(R.id.go_next_regist)
    Button go_next;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sms_button.setOnClickListener(v -> {
            String phone = re_phone.getText().toString();
            if (TextUtils.isEmpty(phone))
                Toast.makeText(RegistActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            else {
                boolean chinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
                if (chinaPhoneLegal == true) {
                    // 注册一个事件回调，用于处理发送验证码操作的结果
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
                } else
                    Toast.makeText(RegistActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
            }
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
                        Intent intent = new Intent(context, RegistNextActivity.class);
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


    @Override
    public int getLayoutId() {
        return R.layout.activity_regist;
    }


    @OnClick({R.id.regist_back, R.id.go_next_regist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.regist_back:
                finish();
                break;
            case R.id.go_next_regist:
                String phone = re_phone.getText().toString();
                boolean chinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
                String code = re_code.getText().toString();
                boolean b = chinaPhoneLegal && !TextUtils.isEmpty(code);
                if(!chinaPhoneLegal) Toast.makeText(RegistActivity.this, "请填写正确手机号", Toast.LENGTH_SHORT).show();
                else if(code.isEmpty()) Toast.makeText(RegistActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                if (b)
                    SMSSDK.submitVerificationCode("86", phone, code);

             /*   String phone = re_phone.getText().toString();
                Intent intent = new Intent(context, RegistNextActivity.class);
                intent.putExtra("phone",phone);
                context.startActivity(intent);*/
                break;
        }
    }
}
