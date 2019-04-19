package io.cordova.zhqy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import butterknife.BindView;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.adapter.MyRefrshAdapter;
import io.cordova.zhqy.adapter.RefreshAdapter;
import io.cordova.zhqy.bean.SysMsgBean;
import io.cordova.zhqy.utils.BaseActivity2;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.ViewUtils;


/**
 * Created by Administrator on 2019/2/22 0022.
 */

public class SystemMsgActivity extends BaseActivity2 {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_msg_list)
    RecyclerView rvMsgList;
    @BindView(R.id.swipeLayout)
    SmartRefreshLayout mSwipeRefresh;


    private LinearLayoutManager mLinearLayoutManager;
    int num = 1,pageSize = 20;
    String msgType;
    private MyRefrshAdapter adapter;
    @Override
    protected int getResourceId() {
        return  R.layout.oa_msg_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        msgType = getIntent().getStringExtra("msgType");
        tvTitle.setText(msgType);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        rvMsgList.setLayoutManager(mLinearLayoutManager);
        ViewUtils.createLoadingDialog(this);
        netWorkSysMsgList();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initListener() {
        super.initListener();
        mSwipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                netWorkSysMsgListOnRefresh(refreshlayout);
            }
        });

        mSwipeRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore( RefreshLayout refreshlayout) {
                netWorkSysMsgListOnLoadMore(refreshlayout);

            }
        });
    }

    private void netWorkSysMsgListOnLoadMore(final RefreshLayout refreshlayout) {

        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.System_Msg_List)
                .params("version","1.0")
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("pageSize", 20)
                .params("pageNum",num)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("SysMsg",response.body());
                        SysMsgBean sysMsgBean2 = JSON.parseObject(response.body(), SysMsgBean.class);
                            ViewUtils.cancelLoadingDialog();
                        if (sysMsgBean2.getObj().size() > 0) {
                            Log.i("消息列表",response.body());
                            sysMsgBean.getObj().addAll(sysMsgBean2.getObj());
                            adapter.notifyDataSetChanged();
                            num += 1;
                            refreshlayout.finishLoadmore();
                        }else {
                            ToastUtils.showToast(SystemMsgActivity.this,"暂无更多数据!");
                            refreshlayout.finishLoadmore();
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }

    private void netWorkSysMsgListOnRefresh(final RefreshLayout refreshlayout) {
        num = 1;
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.System_Msg_List)
                .params("version","1.0")
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("pageSize", 20)
                .params("pageNum",num)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("SysMsg",response.body());
                        sysMsgBean = JSON.parseObject(response.body(), SysMsgBean.class);

                        if (sysMsgBean.isSuccess()) {
                            Log.i("消息列表",response.body());
                            adapter = new MyRefrshAdapter(SystemMsgActivity.this,R.layout.item_to_do_my_msg,sysMsgBean.getObj());
                            rvMsgList.setAdapter(adapter);
                            num = 2;
                            refreshlayout.finishRefresh();

                        }else {

                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }

    SysMsgBean sysMsgBean;
    private void netWorkSysMsgList() {
        num = 1;
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.System_Msg_List)
                .params("version","1.0")
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("pageSize", 20)
                .params("pageNum",num)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("SysMsg",response.body());
                        sysMsgBean = JSON.parseObject(response.body(), SysMsgBean.class);
                        ViewUtils.cancelLoadingDialog();
                        if (sysMsgBean.isSuccess()) {
                            Log.i("消息列表",response.body());
                            adapter = new MyRefrshAdapter(SystemMsgActivity.this,R.layout.item_to_do_my_msg,sysMsgBean.getObj());
                            rvMsgList.setAdapter(adapter);
                            num = 2;

                        }else {

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
