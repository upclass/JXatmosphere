package net.univr.pushi.jxatmosphere.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.CollectionListBeen;
import net.univr.pushi.jxatmosphere.utils.ShipeiUtils;
import net.univr.pushi.jxatmosphere.widget.LeftSlideView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/09/18
 * desc   :
 * version: 1.0
 */


public class RecycleSlipAdapter extends RecyclerView.Adapter<RecycleSlipAdapter.MyViewHolder> implements LeftSlideView.IonSlidingButtonListener {
    private Context mContext;

    private List<CollectionListBeen.DataBean> mDatas = new ArrayList<>();

    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    private LeftSlideView mMenu = null;


    public RecycleSlipAdapter(Context context, List<CollectionListBeen.DataBean> dataBeans) {

        mContext = context;
        mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
        mDatas = dataBeans;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.time.setText(mDatas.get(position).getTime());
        holder.type.setText(mDatas.get(position).getType());
        holder.ctype.setText("时次:"+mDatas.get(position).getCtype());
        Picasso.with(mContext).load(mDatas.get(position).getUrl()).into(holder.imageView);

        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = ShipeiUtils.getWidth(mContext);

        //item正文点击事件
        holder.layout_content.setOnClickListener(new View.OnClickListener() {
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
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(view, n);
            }
        });

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {

        //获取自定义View的布局（加载item布局）
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycle_slip_item, arg0, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView btn_Delete;
        public ViewGroup layout_content;
        public ImageView imageView;
        public TextView time;
        public TextView type;
        public TextView ctype;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn_Delete = itemView.findViewById(R.id.tv_delete);
            layout_content = itemView.findViewById(R.id.layout_content);
            imageView = itemView.findViewById(R.id.picUrl);
            time = itemView.findViewById(R.id.collection_time);
            type = itemView.findViewById(R.id.type);
            ctype = itemView.findViewById(R.id.ctype);
            ((LeftSlideView) itemView).setSlidingButtonListener(RecycleSlipAdapter.this);
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
