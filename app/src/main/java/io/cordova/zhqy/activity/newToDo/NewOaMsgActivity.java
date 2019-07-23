package io.cordova.zhqy.activity.newToDo;

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
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.adapter.MyAdapter;
import io.cordova.zhqy.bean.OAMsgListBean;
import io.cordova.zhqy.utils.BaseActivity2;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.ViewUtils;

/**
 * Created by Administrator on 2019/2/22 0022.
 */

public class NewOaMsgActivity extends BaseActivity2  {
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

    @Override
    protected int getResourceId() {
        return R.layout.oa_msg_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        type = getIntent().getStringExtra("type");
        msgType = getIntent().getStringExtra("msgType");
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
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.findUserMessagesByTypeUrl)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("pageSize", 15)
                .params("type",type)
                .params("pageNum",num)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());
                        oaMsgListBean = JSON.parseObject(response.body(), OAMsgListBean.class);
                        if(oaMsgListBean.getObj().size() > 0){
                            oaMsgListBean2.getObj().addAll(oaMsgListBean.getObj());
                            adapter = new MyAdapter(NewOaMsgActivity.this,R.layout.item_to_do_my_msg,oaMsgListBean2.getObj());
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

    private void netWorkSysMsgListOnRefresh(final RefreshLayout refreshlayout) {
        num = 1;
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.findUserMessagesByTypeUrl)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("pageSize", 15)
                .params("type",type)
                .params("pageNum",1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());
                        oaMsgListBean2 = JSON.parseObject(response.body(), OAMsgListBean.class);
                        if (oaMsgListBean2.isSuccess()) {
                            Log.i("消息列表",response.body());
                            //setRvOAMsgList();
                            adapter = new MyAdapter(NewOaMsgActivity.this,R.layout.item_to_do_my_msg,oaMsgListBean2.getObj());

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
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.findUserMessagesByTypeUrl)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("pageNum", 1)
                .params("type",type)
                .params("pageSize","15")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());
                        ViewUtils.cancelLoadingDialog();
                        oaMsgListBean2 = JSON.parseObject(response.body(), OAMsgListBean.class);
                        if (oaMsgListBean2.isSuccess()) {
                            Log.i("消息列表",response.body());
                            //setRvOAMsgList();
                            adapter = new MyAdapter(NewOaMsgActivity.this,R.layout.item_to_do_my_msg,oaMsgListBean2.getObj());
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
