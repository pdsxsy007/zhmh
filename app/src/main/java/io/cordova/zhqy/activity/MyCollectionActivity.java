package io.cordova.zhqy.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.BaseBean;
import io.cordova.zhqy.bean.MyCollectionBean;
import io.cordova.zhqy.utils.BaseActivity2;
import io.cordova.zhqy.utils.CircleCrop;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ViewUtils;
import io.cordova.zhqy.web.BaseWebActivity;

/**
 * Created by Administrator on 2018/11/22 0022.
 */

public class MyCollectionActivity extends BaseActivity2 {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_cancel_collection)
    TextView tvCancelCollection;
    @BindView(R.id.rv_my_collection)
    RecyclerView rvMyCollection;

    int edit = 0;
    @Override
    protected int getResourceId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText("我的收藏");
        //netWorkMyCollection();
        netWorkMyCollection();
    }

    MyCollectionBean collectionBean;
    private void netWorkMyCollection() {

        ViewUtils.createLoadingDialog(this);
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.My_Collection)
                .params("userId", (String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        collectionBean = JSON.parseObject(response.body(), MyCollectionBean.class);
                        ViewUtils.cancelLoadingDialog();
                        if (collectionBean.isSuccess()) {
                            setCollectionList();
                        } else {

                            T.showShort(MyApp.getInstance(), collectionBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ViewUtils.cancelLoadingDialog();
                        T.showShort(MyApp.getInstance(), "没有数据");
                    }
                });
    }

    CommonAdapter<MyCollectionBean.ObjBean> adapter;

    private void setCollectionList() {

        rvMyCollection.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        adapter = new CommonAdapter<MyCollectionBean.ObjBean>(getApplicationContext(), R.layout.item_service_app, collectionBean.getObj()) {
            @Override
            protected void convert(ViewHolder holder, final MyCollectionBean.ObjBean objBean, final int position) {
                if (tvEdit.getText().equals("编辑")){
                    holder.setVisible(R.id.iv_lock_open,false);
//                    holder.setVisible(R.id.iv_lock_close,true);

                             /*appIntranet  1 需要内网*/
                    if (objBean.getAppIntranet()==1){
                        holder.setVisible(R.id.iv_del,true);
                        Glide.with(getApplicationContext())
                                .load(R.mipmap.nei_icon)
                                .error(R.color.white)
                                .into((ImageView) holder.getView(R.id.iv_del));
                    }else {
                        holder.setVisible(R.id.iv_del,false);
                    }


                }else if(tvEdit.getText().equals("取消收藏")) {
                    holder.setVisible(R.id.iv_lock_close,false);
                    holder.setVisible(R.id.iv_del,false);
                    holder.setVisible(R.id.iv_lock_open,true);

                }
//                holder.setVisible(R.id.iv_lock_open,true);
//
//                Glide.with(getApplicationContext())
//                        .load(R.mipmap.lock_icon)
//                        .transform(new CircleCrop(getApplicationContext()))
//                        .into((ImageView) holder.getView(R.id.iv_lock_open));

                holder.setText(R.id.tv_app_name,objBean.getAppName());
                if (null != objBean.getPortalAppIcon() && null != objBean.getPortalAppIcon().getTempletAppImage()){

                    Glide.with(getApplicationContext())
                            .load(UrlRes.HOME3_URL + objBean.getPortalAppIcon().getTempletAppImage())
                            .error(R.mipmap.message_icon1)
                            .into((ImageView) holder.getView(R.id.iv_app_icon));
                }else {
                    Glide.with(getApplicationContext())
                            .load(UrlRes.HOME3_URL + objBean.getAppImages())
                            .error(R.mipmap.message_icon1)
                            .into((ImageView) holder.getView(R.id.iv_app_icon));
                }

                holder.setOnClickListener(R.id.ll_click, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        netWorkAppClick(objBean.getAppId());
                        Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity.class);
                        intent.putExtra("appUrl",objBean.getAppUrl());
                        intent.putExtra("appId",objBean.getAppId()+"");
                        startActivity(intent);
                    }
                });

                holder.setOnClickListener(R.id.iv_lock_open, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消收藏
                        netCancelCollection(objBean.getAppId());
                    }
                });


            }
        };
        rvMyCollection.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        adapter.notifyDataSetChanged();
    }
    BaseBean baseBean;
    private void netCancelCollection(int appId) {
        OkGo.<String>post(UrlRes.HOME_URL+ UrlRes.Cancel_Collection)
                .params( "version","1.0" )
                .params( "collectionAppId",appId )
                .params( "userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        //handleResponse(response);
                        Log.e("tag",response.body());
                        baseBean = JSON.parseObject(response.body(), BaseBean.class);
                        if (baseBean.isSuccess()){
                            netWorkMyCollection();
                            T.showShort(MyApp.getInstance(),baseBean.getMsg());
                        }else {

                            T.showShort(MyApp.getInstance(),baseBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T.showShort(getApplicationContext(),"找不到服务器了，请稍后再试");

                    }
                });

    }

    /**
     * 记录该微应用的的访问量
     * @param appId
     *
     * */
    private void netWorkAppClick(int appId) {
        OkGo.<String>get(UrlRes.HOME_URL +UrlRes.APP_Click_Number)

                .params("appId",appId)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("result1",response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("错误",response.body());
                    }
                });
    }


    @OnClick({R.id.tv_edit, R.id.tv_cancel_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                if (edit!=0){
                    edit = 0;
                    tvEdit.setText("编辑");
                }else {
                    edit = 1;
                    tvEdit.setText("取消收藏");
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_cancel_collection:

                break;
        }
    }


}
