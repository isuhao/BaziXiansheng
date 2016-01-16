package com.example.administrator.bazipaipan.chat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.bazipaipan.R;
import com.example.administrator.bazipaipan.chat.ChatContainerActivity;
import com.example.administrator.bazipaipan.chat.adapter.ChatHistoryAdapter;
import com.example.administrator.bazipaipan.login.model.MyUser;
import com.example.administrator.bazipaipan.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

/**
 * Created by 王中阳 on 2016/1/2.
 */
public class ChatHistoryFragment extends Fragment implements ChatHistoryAdapter.IClickListener {

    public static final String TAG = "ChatHistoryFragment";
    //1context
    private ChatContainerActivity mycontext;
    //2view对象
    private RecyclerView recyclerView;
    //3数据源
    private List<MyUser> datas;
    //4适配器
    private ChatHistoryAdapter mAdapter;
    //排队控件
    private TextView tv_history;
    private LinearLayout ll_container_chathistory;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mycontext = (ChatContainerActivity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chathistory, container, false);
        tv_history = (TextView) view.findViewById(R.id.tv_history);
        ll_container_chathistory = (LinearLayout) view.findViewById(R.id.ll_container_chathistory);
        ll_container_chathistory.getBackground().setAlpha(200);
        updateUI(view);
        return view;
    }

    //做成girdview的样式
    private void updateUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_chathistory);
        //方向
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), HORIZONTAL, false));
        //子布局装饰
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), HORIZONTAL));
        //适配器未装填内容
        mAdapter = new ChatHistoryAdapter(mycontext, this);
        recyclerView.setAdapter(mAdapter);
        //滑动加载更多的layout
//        mSwipeLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.swipe_container);
//        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_blue_bright, android.R.color.holo_blue_bright,
//                android.R.color.holo_blue_bright);
//        //需要实现方法
//        mSwipeLayout.setOnRefreshListener(this);
        updateFromNet();
    }

    //从网络中抓取数据，更新UI
    private void updateFromNet() {
        //查询多条数据
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.setLimit(10);
        datas = new ArrayList<>();
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
//        boolean isCache = query.hasCachedResult(mycontext, MyUser.class);
//        if (isCache) {
//            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
//        } else {
//            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
//        }
        query.findObjects(mycontext, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> object) {
                // TODO Auto-generated method stub
                for (MyUser bean : object) {
                    //数据源填充
                    datas.add(bean);
                }
                mAdapter.setMdatas(datas);
//                mHandler.sendEmptyMessage(0);
//                mSwipeLayout.setRefreshing(false);
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
//                mHandler.sendEmptyMessage(0);
//                mSwipeLayout.setRefreshing(false);
            }
        });
    }

    //点击item的跳转逻辑
    @Override
    public void onItemClicked(int position) {


    }

//    //刷新数据相关
//    public void setSwipeToRefreshEnabled(boolean enabled) {
//        mSwipeLayout.setEnabled(enabled);
//    }

//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
////                    if (mSwipeLayout != null) {
////
////                        if (mSwipeLayout.isRefreshing()) {
////                            mSwipeLayout.setRefreshing(false);
////                        }
////                    }
//
//                    break;
//            }
//        }
//    };

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        datas.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
