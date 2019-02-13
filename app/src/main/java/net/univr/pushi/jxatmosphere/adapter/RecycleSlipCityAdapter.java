package net.univr.pushi.jxatmosphere.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.feature.LocationChangeActivity;
import net.univr.pushi.jxatmosphere.utils.ShipeiUtils;
import net.univr.pushi.jxatmosphere.widget.LeftSlideView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2019/02/12
 * desc   :
 * version: 1.0
 */


public class RecycleSlipCityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements LeftSlideView.IonSlidingButtonListener {
    private Context mContext;

    private List<String> mDatas = new ArrayList<>();

    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    private LeftSlideView mMenu = null;


    private final static int TYPE_CONTENT = 1;
    private final static int TYPE_FOOTER = 2;


    public RecycleSlipCityAdapter(Context context, List<String> dataBeans) {

        mContext = context;
        mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
        mDatas = dataBeans;
    }

    @Override
    public int getItemCount() {
        return mDatas.size()+1;//多加一个尾部
    }

    public int getContentSize(){
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        int contentSize = getContentSize();
        if(position ==  contentSize){ // 尾部
            return TYPE_FOOTER;
        }else{
            return TYPE_CONTENT;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(position==mDatas.size()){
            FootViewHolder holder2 = (FootViewHolder) holder;
            holder2.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(mContext, LocationChangeActivity.class);
                    ((Activity) mContext).startActivityForResult(intent1, 11);
                }
            });
        }else{
            MyViewHolder holder1 = (MyViewHolder) holder;
            holder1.city.setText(mDatas.get(position));
            //设置内容布局的宽为屏幕宽度
            holder1.layout_content.getLayoutParams().width = ShipeiUtils.getWidth(mContext);

            //item正文点击事件
            holder1.layout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //判断是否有删除菜单打开
                    if (menuIsOpen()) {
                        closeMenu();//关闭菜单
                    } else {
                        int n = holder.getLayoutPosition();
                        mIDeleteBtnClickListener.onItemClick(v, n);
                    }

                }
            });
            //左滑删除点击事件
            holder1.btn_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onDeleteBtnCilck(view, n);
                }
            });
        }
    }


    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_FOOTER){
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.city_foot_layout,parent,false);
            FootViewHolder holder = new FootViewHolder(itemView);
            return holder;
        }else {
            //获取自定义View的布局（加载item布局）
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycle_slip_city_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView btn_Delete;
        public ViewGroup layout_content;
        public TextView city;
        public LeftSlideView  leftSlideView;
        public MyViewHolder(View itemView) {
            super(itemView);
            btn_Delete = itemView.findViewById(R.id.tv_delete);
            layout_content = itemView.findViewById(R.id.layout_content);
            city = itemView.findViewById(R.id.city);
            leftSlideView= itemView.findViewById(R.id.leftSlideView);
            leftSlideView.setSlidingButtonListener(RecycleSlipCityAdapter.this);
        }
    }


    class FootViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout add;


        public FootViewHolder(View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.add_linear);
        }
    }


    /**
     * 删除item
     *
     * @param position
     */
    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }


    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (LeftSlideView) view;
    }


    /**
     * 滑动或者点击了Item监听
     *
     * @param leftSlideView
     */
    @Override
    public void onDownOrMove(LeftSlideView leftSlideView) {
        if (menuIsOpen()) {
            if (mMenu != leftSlideView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断菜单是否打开
     *
     * @return
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }


    /**
     * 注册接口的方法：点击事件。在Mactivity.java实现这些方法。
     */
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);//点击item正文

        void onDeleteBtnCilck(View view, int position);//点击“删除”
    }


}
