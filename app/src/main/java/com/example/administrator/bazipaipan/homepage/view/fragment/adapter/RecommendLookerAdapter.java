package com.example.administrator.bazipaipan.homepage.view.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.bazipaipan.R;
import com.example.administrator.bazipaipan.augur.model.Augur;
import com.example.administrator.bazipaipan.utils.MyStringUtils;

import java.util.List;

/**
 * Created by 王中阳 on 2015/12/16.
 */
public class RecommendLookerAdapter extends RecyclerView.Adapter<RecommendLookerAdapter.RecyclerHolder> {
    /**
     * 1 inflater
     * 2context
     * 3datas   get set方法
     * --
     * 4 构造器
     * 5 data的 get set方法
     */
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Augur> mdatas;
    private IClickListener mCallBack; //信息回调

    public RecommendLookerAdapter(Context mContext, IClickListener mCallBack) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mCallBack = mCallBack;
    }

    public List<Augur> getMdatas() {
        return mdatas;
    }

    public void setMdatas(List<Augur> mdatas) {
        this.mdatas = mdatas;
        notifyDataSetChanged(); //观察者模式
    }

    //通过inflater引入item布局文件
    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recommend_looker, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        //从数据源中抽取javabean
        Augur bean = mdatas.get(position);
        if (bean == null) {
            return;
        }

        //上面删除后将此注释解开
        holder.looker_num.setText(MyStringUtils.cutout(bean.getLookerNum()));
        if (bean.getUserId() == null) {
            holder.augur_name.setText("无名大师");
        } else {
            holder.augur_name.setText(bean.getUserId());  //关联取值
        }


        holder.accuracy.setText(bean.getAccuracy());
        holder.init_divination.setText(bean.getDivinatedNum());
        //图片暂时写死
        if (bean.getAugur_pointer() != null && bean.getAugur_pointer().getAvatar() != null) {
            bean.getAugur_pointer().getAvatar().loadImageThumbnail(mContext, holder.augur_head, 186, 186, 100);
        } else {
            holder.augur_head.setImageResource(R.drawable.augur_head);
        }

    }

    @Override
    public int getItemCount() {
        if (mdatas != null && mdatas.size() > 0) {
            return mdatas.size();
        }
        return 0;
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView looker_num, augur_name, accuracy, init_divination;
        ImageView augur_head;

        public RecyclerHolder(View itemView) {
            super(itemView);
            looker_num = (TextView) itemView.findViewById(R.id.tv_recommend_looker_num);
            augur_name = (TextView) itemView.findViewById(R.id.tv_recommend_looker_augur_name);
            accuracy = (TextView) itemView.findViewById(R.id.tv_recommend_looker_accuracy);
            init_divination = (TextView) itemView.findViewById(R.id.tv_recommend_looker_init_divination);
            augur_head = (ImageView) itemView.findViewById(R.id.iv_recommend_augur_head);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.onItemClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface IClickListener {
        void onItemClicked(int position);
    }
}
