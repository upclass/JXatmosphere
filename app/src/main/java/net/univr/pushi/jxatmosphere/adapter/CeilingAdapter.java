package net.univr.pushi.jxatmosphere.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.univr.pushi.jxatmosphere.R;
import net.univr.pushi.jxatmosphere.beens.GdybBeen1;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static net.univr.pushi.jxatmosphere.adapter.WeathMainAdapter.initWeathTuPianJson;


/**
 * @author：byd666 on 2017/12/2 15:19
 */
public class CeilingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 头视图
     */
    public static final int TYPE_HEADER = 0;
    /**
     * 正常的
     */
    public static final int TYPE_NORMAL = 1;

    private View mHeaderView;

    private Context context;
    private List<GdybBeen1.DataBean.ContentBean> stickyExampleModels;

    public CeilingAdapter(Context context, List<GdybBeen1.DataBean.ContentBean> recyclerViewModels) {
        this.context = context;
        this.stickyExampleModels = recyclerViewModels;
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.gdyb_recycle_item1, parent, false);
        return new HomeRecyclerViewHolder(view);
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        final int pos = getRealPosition(viewHolder);
        if (viewHolder instanceof HomeRecyclerViewHolder) {
            HomeRecyclerViewHolder holder = (HomeRecyclerViewHolder) viewHolder;
            holder.setData(pos);
            if (mListener == null) {
                return;
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos);
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        return holder.getLayoutPosition();
    }

    @Override
    public int getItemCount() {
        return stickyExampleModels == null ? 0 : stickyExampleModels.size();
    }

    public class HomeRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView hour;
        public TextView weather_desc;
        public ImageView weather_img;

        public HomeRecyclerViewHolder(View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.hour);
            weather_desc = itemView.findViewById(R.id.weather_desc);
            weather_img = (ImageView) itemView.findViewById(R.id.weather_img);
        }

        public void setData(int position) {
            GdybBeen1.DataBean.ContentBean bean = stickyExampleModels.get(position);
            String forecastTime = bean.getForecastTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            try {
                Date parse = format.parse(forecastTime);
                calendar.setTime(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int hourInt = calendar.get(Calendar.HOUR_OF_DAY);
            hour.setText(hourInt+":00");
            //得到天气图片
            String tqxx = bean.getWeatherDesc();
            String picBiaoJi = "";
            JsonArray jsonElements = initWeathTuPianJson();
            for (int j = 0; j < jsonElements.size(); j++) {
                JsonObject jsonObject = jsonElements.get(j).getAsJsonObject();
                JsonElement picInfo = jsonObject.get("name");
                if (picInfo.getAsString().equals(tqxx)) {
                    picBiaoJi = jsonObject.get("image").getAsString();
                    break;
                } else continue;
            }
            String imageName = picBiaoJi;
            weather_img.setImageResource(getResource(imageName));
            String temperStr = bean.getTemper();
            Double aDouble = Double.valueOf(temperStr);
            BigDecimal bd = new BigDecimal(aDouble).setScale(0, BigDecimal.ROUND_HALF_UP);
            String tempInt = String.valueOf(Integer.parseInt(bd.toString())) + "℃";
            weather_desc.setText(bean.getWeatherDesc() + tempInt);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public int getResource(String imageName) {
        Context ctx = ((Activity) context).getBaseContext();
        int resId = context.getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}

