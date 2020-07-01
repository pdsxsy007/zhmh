package io.cordova.zhmh.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.adapter.MyAdapter;
import io.cordova.zhmh.bean.OAMsgListBean;
import io.cordova.zhmh.utils.BaseActivity2;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.ToastUtils;
import io.cordova.zhmh.utils.ViewUtils;

/**
 * Created by Administrator on 2019/2/22 0022.
 */

public class OaMsgActivity extends BaseActivity2  {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_msg_list)
    RecyclerView rvMsgList;

    @BindView(R.id.swipeLayout)
    SmartRefreshLayout mSwipeLayout;


    @BindView(R.id.rl_empty)
    RelativeLayout rl_empty;

    private MyAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    String type,msgType;
    private int num = 1;
    @BindView(R.id.header)
    ClassicsHeader header;
    private int size = 0;
    private int lastSize = -1;
    @Override
    protected int getResourceId() {
        return R.layout.oa_msg_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        type = getIntent().getStringExtra("type");
        msgType = getIntent().getStringExtra("msgType");
        //Log.e("type = ",type);
        tvTitle.setText(msgType);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        rvMsgList.setLayoutManager(mLinearLayoutManager);
        ViewUtils.createLoadingDialog(this);
        netWorkOaMsgList();

        header.setEnableLastTime(false);
    }


    @Override
    protected void initListener() {
        super.initListener();
        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                num = 1;
                netWorkSysMsgListOnRefresh(refreshlayout);
            }
        });

        mSwipeLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore( RefreshLayout refreshlayout) {
                netWorkSysMsgListOnLoadMore(refreshlayout);

            }
        });
    }

    private void netWorkSysMsgListOnLoadMore(final RefreshLayout refreshlayout) {
        if(lastSize == size ){
            ToastUtils.showToast(this,"没有更多数据了!");
            refreshlayout.finishLoadmore();
        }else {
            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_workFolwDbList)
                    .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                    .params("size", 15*num)
                    .params("type",type)
                    .params("workType","workdb")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            //Log.e("s",response.toString());
                            oaMsgListBean = JSON.parseObject(response.body(), OAMsgListBean.class);
                            oaMsgListBean2.getObj().clear();
                            oaMsgListBean2.getObj().addAll(oaMsgListBean.getObj());
                            lastSize = size;
                            size = oaMsgListBean2.getObj().size();
                            if(size % 15 > 0){
                                lastSize = size;
                            }
                            if (oaMsgListBean.isSuccess()) {
                                Log.i("消息列表",response.body());
                                adapter = new MyAdapter(OaMsgActivity.this,R.layout.item_to_do_my_msg,oaMsgListBean.getObj());
                                rvMsgList.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                num += 1;
                                refreshlayout.finishLoadmore();
                            }else {
                                refreshlayout.finishLoadmore();
                            }
                        }
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            refreshlayout.finishLoadmore();

                        }
                    });
        }


    }

    private void netWorkSysMsgListOnRefresh(final RefreshLayout refreshlayout) {
        num = 1;
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_workFolwDbList)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("size", 15)
                .params("type",type)
                .params("workType","workdb")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("s",response.toString());
                        oaMsgListBean2 = JSON.parseObject(response.body(), OAMsgListBean.class);
                        if (oaMsgListBean2.isSuccess()) {
//                            Log.i("消息列表",response.body());
                            //setRvOAMsgList();
                            adapter = new MyAdapter(OaMsgActivity.this,R.layout.item_to_do_my_msg,oaMsgListBean2.getObj());

                            rvMsgList.setAdapter(adapter);
                            refreshlayout.finishRefresh();
                            num = 2;
                            mSwipeLayout.setVisibility(View.VISIBLE);
                            rl_empty.setVisibility(View.GONE);
                        }else {
                            refreshlayout.finishRefresh();
                            mSwipeLayout.setVisibility(View.GONE);
                            rl_empty.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        refreshlayout.finishRefresh();

                    }
                });
    }


    OAMsgListBean oaMsgListBean;
    OAMsgListBean oaMsgListBean2 = new OAMsgListBean();
    private void netWorkOaMsgList() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_workFolwDbList)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("size", 15)
                .params("type",type)
                .params("workType","workdb")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("s",response.toString());
                        ViewUtils.cancelLoadingDialog();
                        oaMsgListBean2 = JSON.parseObject(response.body(), OAMsgListBean.class);
                        if (oaMsgListBean2.isSuccess()) {
//                            Log.i("消息列表",response.body());
                            //setRvOAMsgList();
                            adapter = new MyAdapter(OaMsgActivity.this,R.layout.item_to_do_my_msg,oaMsgListBean2.getObj());
                            rvMsgList.setAdapter(adapter);
                            num = 2;
                            mSwipeLayout.setVisibility(View.VISIBLE);
                            rl_empty.setVisibility(View.GONE);
                        }else {
                            mSwipeLayout.setVisibility(View.GONE);
                            rl_empty.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ViewUtils.cancelLoadingDialog();

                    }
                });
    }






}
