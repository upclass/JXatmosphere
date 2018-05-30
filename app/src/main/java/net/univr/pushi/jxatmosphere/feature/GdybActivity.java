package net.univr.pushi.jxatmosphere.feature;

import android.os.Bundle;
import android.widget.TextView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.base.BaseActivity;

import butterknife.BindView;

public class GdybActivity extends BaseActivity {


    @BindView(R.id.text)
    TextView text1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gdyb;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        getTestdata();
    }

//    private void getTestdata() {
//        RetrofitHelper.getWeatherMonitorAPI()
//                .getGdyb("115.12573242187501", "28.555576049185973", "3", "2018-05-08 08:00:00")
//                .compose(bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(gdybBeen -> {
//                    GdybBeen.DataBean data = gdybBeen.getData();
//                    List<GdybBeen.DataBean.AllCloudBean> allCloud = data.getAllCloud();
//                    GdybBeen.DataBean.AllCloudBean allCloudBean = allCloud.get(0);
//                    String text = allCloudBean.getText();
//                    text1.setText(text);
//                }, throwable -> {
//                    LogUtils.e(throwable);
//                    ToastUtils.showShort(getString(R.string.getInfo_error_toast));
//                });
//    }


}
