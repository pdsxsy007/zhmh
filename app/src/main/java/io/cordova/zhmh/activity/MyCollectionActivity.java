package io.cordova.zhmh.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.cxz.swipelibrary.SwipeBackLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.bean.BaseBean;
import io.cordova.zhmh.bean.MyCollectionBean;
import io.cordova.zhmh.bean.ServiceAppListBean;
import io.cordova.zhmh.utils.BaseActivity2;
import io.cordova.zhmh.utils.DargeFaceMeColletUtils;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.PermissionsUtil;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.T;
import io.cordova.zhmh.web.BaseWebActivity4;
import io.cordova.zhmh.web.BaseWebCloseActivity;

/**
 * Created by Administrator on 2018/11/22 0022.
 */

public class MyCollectionActivity extends BaseActivity2 implements PermissionsUtil.IPermissionsCallback{

    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_cancel_collection)
    TextView tvCancelCollection;
    @BindView(R.id.rv_my_collection)
    RecyclerView rvMyCollection;

    @BindView(R.id.rl_empty)
    RelativeLayout rl_empty;
    int edit = 0;
    private SwipeBackLayout mSwipeBackLayout;
    @Override
    protected int getResourceId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initView() {
        super.initView();



    }


    @Override
    protected void onResume() {
        super.onResume();
        netWorkMyCollection();
    }

    MyCollectionBean collectionBean;
    private void netWorkMyCollection() {

        //ViewUtils.createLoadingDialog(this);
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.My_Collection)
                .tag(this)
                .params("userId", (String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        collectionBean = JSON.parseObject(response.body(), MyCollectionBean.class);

                        if (collectionBean.isSuccess()) {
                            if(null != collectionBean.getObj()){
                                rvMyCollection.setVisibility(View.VISIBLE);
                                rl_empty.setVisibility(View.GONE);
                                setCollectionList();
                            }else {
                                rvMyCollection.setVisibility(View.GONE);
                                rl_empty.setVisibility(View.VISIBLE);
                            }

                        } else {

                            T.showShort(MyApp.getInstance(), collectionBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //ViewUtils.cancelLoadingDialog();

                    }
                });
    }

    private PermissionsUtil permissionsUtil;

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


                }else if(tvEdit.getText().equals("完成")) {
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
                        long nowTime = System.currentTimeMillis();
                        if (nowTime - mLastClickTime > TIME_INTERVAL) {
                            mLastClickTime = nowTime;
                            netWorkAppClick(objBean.getAppId());
                            Intent intent = null;
                            String isOpen = (String) SPUtils.get(MyApp.getInstance(), "isOpen", "");
                            if(isOpen.equals("") || isOpen.equals("1")){
                                intent = new Intent(MyApp.getInstance(), BaseWebCloseActivity.class);
                            }else {
                                intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                            }
                            ServiceAppListBean.ObjBean.AppsBean.PortalAppAuthentication portalAppAuthentication = collectionBean.getObj().get(position).getPortalAppAuthentication();
                            if(portalAppAuthentication != null){
                                String appAuthenticationFace = portalAppAuthentication.getAppAuthenticationFace();
                                if(appAuthenticationFace != null){
                                    if(!appAuthenticationFace.equals("0")){
                                        permissionsUtil=  PermissionsUtil
                                                .with(MyCollectionActivity.this)
                                                .requestCode(1)
                                                .isDebug(true)//开启log
                                                .permissions(PermissionsUtil.Permission.Camera.CAMERA,PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                                                .request();

                                        if(isOpens == 1){
                                            DargeFaceMeColletUtils.cameraTask(collectionBean.getObj().get(position),MyCollectionActivity.this);
                                        }
                                    }else {
                                        DargeFaceMeColletUtils.cameraTask(collectionBean.getObj().get(position),MyCollectionActivity.this);
                                    }

                                }else {
                                    DargeFaceMeColletUtils.cameraTask(collectionBean.getObj().get(position),MyCollectionActivity.this);
                                }
                            }else {
                                DargeFaceMeColletUtils.cameraTask(collectionBean.getObj().get(position),MyCollectionActivity.this);
                            }
                        }

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

    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 500L;


    BaseBean baseBean;
    private void netCancelCollection(int appId) {
        OkGo.<String>post(UrlRes.HOME_URL+ UrlRes.Cancel_Collection)
                .tag(this)
                .params( "version","1.0" )
                .params( "collectionAppId",appId )
                .params( "userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        //handleResponse(response);
                        //Log.e("tag",response.body());
                        baseBean = JSON.parseObject(response.body(), BaseBean.class);
                        if (baseBean.isSuccess()){
                            netWorkMyCollection();
                            T.showShort(MyApp.getInstance(),baseBean.getMsg());
                            Intent intent = new Intent();
                            intent.putExtra("refreshService","dongtai");
                            intent.setAction("refresh2");
                            sendBroadcast(intent);
                        }else {

                            T.showShort(MyApp.getInstance(),baseBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);


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
                .tag(this)
                .params("appId",appId)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("result1",response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //Log.e("错误",response.body());
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
                    tvEdit.setText("完成");
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_cancel_collection:

                break;
        }
    }


    private int isOpens = 0;

    @Override
    public void onPermissionsGranted(int requestCode, String... permission) {
        isOpens = 1;
    }

    @Override
    public void onPermissionsDenied(int requestCode, String... permission) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(permissionsUtil != null){
            permissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(permissionsUtil != null){
            permissionsUtil.onActivityResult(requestCode, resultCode, data);
        }


    }
}
