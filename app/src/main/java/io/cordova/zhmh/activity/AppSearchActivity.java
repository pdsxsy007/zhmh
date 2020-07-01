package io.cordova.zhmh.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.bean.AppListBean;
import io.cordova.zhmh.utils.AesEncryptUtile;
import io.cordova.zhmh.utils.BaseActivity2;
import io.cordova.zhmh.utils.DargeFaceSearchUtils;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.PermissionsUtil;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.StringUtils;
import io.cordova.zhmh.utils.T;
import io.cordova.zhmh.utils.ViewUtils;
import io.cordova.zhmh.web.BaseWebActivity4;


/**
 * Created by Administrator on 2018/11/29 0029.
 * xiugai;''
 * jjjjs
 */

public class AppSearchActivity extends BaseActivity2 implements PermissionsUtil.IPermissionsCallback{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_search_cache)
    TextView tvSearchCache;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.search_result)
    RecyclerView searchResult;

    @BindView(R.id.rl_history)
    RelativeLayout rl_history;

    @BindView(R.id.tv_clear)
    TextView tv_clear;

    @BindView(R.id.mFlowLayout)
    TagFlowLayout flSearchCache;
    boolean isLogin =false;
    private int flag = 0;
    private PermissionsUtil permissionsUtil;
    @Override
    protected int getResourceId() {
        return R.layout.app_search_activity;
    }

    @Override
    protected void initView() {

        super.initView();
        isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""));
        tvTitle.setText("应用搜索");
        tvResult.setVisibility(View.GONE);
        //tvSearchCache.setVisibility(View.GONE);
        rl_history.setVisibility(View.GONE);
        setEditListener();
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_history.setVisibility(View.GONE);
                flSearchCache.setVisibility(View.GONE);
                ViewUtils.clearSearchHistory(AppSearchActivity.this);
            }
        });

        registerBoradcastReceiver();
    }

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("refresh2");
        //注册广播
        registerReceiver(broadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("refresh2")){
                netWorkSearchApp();
            }
        }
    };

    private void setEditListener() {
        tvSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = tvSearch.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > tvSearch.getWidth()
                        - tvSearch.getPaddingRight()
                        - drawable.getIntrinsicWidth()){
                    //Log.e("tvSearch --- ",StringUtils.getEditTextData(tvSearch));
                    //Log.e("isEmpty --- ",StringUtils.getEditTextData(tvSearch).isEmpty() +"");
//                    if (StringUtils.getEditTextData(tvSearch).isEmpty()){
//                        T.showShort(MyApp.getInstance(),"请输入搜索关键字");
//                    }else {
                        ViewUtils.saveSearchHistory(StringUtils.getEditTextData(tvSearch));
                        netWorkSearchApp();
                        flag = 1;
                        rl_history.setVisibility(View.GONE);
                        flSearchCache.setVisibility(View.GONE);
//                    }

                }
                return false;

            }
        });

        tvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                   /* if (StringUtils.getEditTextData(tvSearch).isEmpty()){
                        T.showShort(MyApp.getInstance(),"请输入搜索关键字");
                    }else {
                        ViewUtils.saveSearchHistory(StringUtils.getEditTextData(tvSearch));
                        netWorkSearchApp();
                        flag = 1;
                        rl_history.setVisibility(View.GONE);
                        flSearchCache.setVisibility(View.GONE);
                    }*/
                    ViewUtils.saveSearchHistory(StringUtils.getEditTextData(tvSearch));
                    netWorkSearchApp();
                    flag = 1;
                    rl_history.setVisibility(View.GONE);
                    flSearchCache.setVisibility(View.GONE);
                    return true;
                }

                return false;
            }
        });
    }

    /*搜索数据请求*/
    AppListBean allAppListBean;
    private void netWorkSearchApp() {
        String userId = (String) SPUtils.get(MyApp.getInstance(), "userId", "");
        ViewUtils.createLoadingDialog(this);
        String editTextData = StringUtils.getEditTextData(tvSearch);
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.APP_List)
                .tag(this)
                .params("Version", "1.0")
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("appName", tvSearch.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("dsadadsad",response.body());
                        allAppListBean = JSON.parseObject(response.body(),AppListBean.class);
                        ViewUtils.cancelLoadingDialog();
                        if (allAppListBean.isSuccess()){
                            tvResult.setVisibility(View.VISIBLE);
                            searchResult.setVisibility(View.VISIBLE);
                            setSearchCache();
                            setRvAppList();
                            flSearchCache.setVisibility(View.VISIBLE);
                            tvSearchCache.setVisibility(View.VISIBLE);
                        }else {

                            tvResult.setVisibility(View.VISIBLE);
                            searchResult.setVisibility(View.GONE);
                            tvSearchCache.setVisibility(View.GONE);
                            setSearchCache();
                            flSearchCache.setVisibility(View.VISIBLE);
                            T.showShort(MyApp.getInstance(),allAppListBean.getMsg() );
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        tvResult.setVisibility(View.GONE);
                        ViewUtils.cancelLoadingDialog();
                        T.showShort(MyApp.getInstance(),"网络异常" );
                    }
                });
    }
    /*搜索数据填充*/
    CommonAdapter<AppListBean.ObjBean.ListBean> searchResultListAdapter;
    private void setRvAppList() {
        searchResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchResultListAdapter = new CommonAdapter<AppListBean.ObjBean.ListBean>(getApplicationContext(),R.layout.item_search_app,allAppListBean.getObj().getList()) {
            @Override
            protected void convert(ViewHolder holder, final AppListBean.ObjBean.ListBean listBean, int position) {
                holder.setText(R.id.tv_app_name,listBean.getAppName());
                holder.setText(R.id.tv_app_come,"来源 ：" +listBean.getSystemName());
                holder.setVisible(R.id.iv_del,true);

                if (null != listBean.getPortalAppIcon() && null != listBean.getPortalAppIcon().getTempletAppImage()){

                    Glide.with(getApplicationContext())
                            .load(UrlRes.HOME3_URL + listBean.getPortalAppIcon().getTempletAppImage())
                            .error(getResources().getColor(R.color.app_bg))
                            .into((ImageView) holder.getView(R.id.iv_app_img));
                }else {
                    Glide.with(getApplicationContext())
                            .load(UrlRes.HOME3_URL + listBean.getAppImages())
                            .error(getResources().getColor(R.color.app_bg))
                            .into((ImageView) holder.getView(R.id.iv_app_img));
                }
                holder.setOnClickListener(R.id.ll_go, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        long nowTime = System.currentTimeMillis();
                        if (nowTime - mLastClickTime > TIME_INTERVAL) {
                            mLastClickTime = nowTime;
                            if (!listBean.getAppUrl().isEmpty()){
                                if (!isLogin) {
                                    if(listBean.getAppLoginFlag()==0){
                                        Intent intent = new Intent(AppSearchActivity.this,LoginActivity2.class);
                                        startActivity(intent);
                                    }else {
                                        Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                                        if (listBean.getAppUrl().contains("{memberid}")){
                                            String appUrl = listBean.getAppUrl();
                                            String s1= null;
                                            try {
                                                s1 = URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"personName",""), "UTF-8");
                                                appUrl =  appUrl.replace("{memberid}", s1);
                                                intent.putExtra("appUrl",appUrl);
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }

                                        }else {
                                            intent.putExtra("appUrl",listBean.getAppUrl());
                                        }

                                        //intent.putExtra("appUrl",listBean.getAppUrl());
                                        intent.putExtra("appName",listBean.getAppName());
                                        intent.putExtra("search","1");
                                        startActivity(intent);
                                    }

                                }else {
                                    Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                                    if (listBean.getAppUrl().contains("{memberid}")){
                                        String appUrl = listBean.getAppUrl();
                                        String s1= null;
                                        try {
                                            s1 = URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"personName",""), "UTF-8");
                                            appUrl =  appUrl.replace("{memberid}", s1);
                                            intent.putExtra("appUrl",appUrl);
                                            intent.putExtra("appName",listBean.getAppName());
                                            intent.putExtra("search","1");
                                            intent.putExtra("appId",listBean.getAppId()+"");
                                            startActivity(intent);
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }

                                    }else if(listBean.getAppUrl().contains("{memberAesEncrypt}")){
                                        String appUrl = listBean.getAppUrl();
                                        try {
                                            String memberAesEncrypt = AesEncryptUtile.encrypt((String) SPUtils.get(MyApp.getInstance(),"personName",""), String.valueOf(listBean.getAppSecret()));
                                            String s2=  URLEncoder.encode(memberAesEncrypt, "UTF-8");
                                            appUrl =  appUrl.replace("{memberAesEncrypt}", s2);
                                            intent.putExtra("appUrl",appUrl);
                                            intent.putExtra("appId",listBean.getAppId()+"");
                                            intent.putExtra("appName",listBean.getAppName()+"");
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }else if(listBean.getAppUrl().contains("{quicklyTicket}")){
                                        String appUrl = listBean.getAppUrl();
                                        try {
                                            String s3 =  URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"TGC",""), "UTF-8");
                                            appUrl = appUrl.replace("{quicklyTicket}",s3);
                                            intent.putExtra("appUrl",appUrl);
                                            intent.putExtra("appId",listBean.getAppId()+"");
                                            intent.putExtra("appName",listBean.getAppName()+"");
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else {
                                    /*permissionsUtil=  PermissionsUtil
                                            .with(AppSearchActivity.this)
                                            .requestCode(1)
                                            .isDebug(true)//开启log
                                            .permissions(PermissionsUtil.Permission.Camera.CAMERA,PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                                            .request();
                                    if(isOpen == 1){
                                        DargeFaceSearchUtils.cameraTask(listBean,AppSearchActivity.this);
                                    }
*/
                                        AppListBean.ObjBean.PortalAppAuthentication portalAppAuthentication = listBean.getPortalAppAuthentication();
                                        if(portalAppAuthentication != null){
                                            String appAuthenticationFace = portalAppAuthentication.getAppAuthenticationFace();
                                            if(appAuthenticationFace != null){
                                                if(!appAuthenticationFace.equals("0")){
                                                    permissionsUtil=  PermissionsUtil
                                                            .with(AppSearchActivity.this)
                                                            .requestCode(1)
                                                            .isDebug(true)//开启log
                                                            .permissions(PermissionsUtil.Permission.Camera.CAMERA,PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                                                            .request();

                                                    if(isOpen == 1){
                                                        DargeFaceSearchUtils.cameraTask(listBean,AppSearchActivity.this);
                                                    }
                                                }else {
                                                    DargeFaceSearchUtils.cameraTask(listBean,AppSearchActivity.this);
                                                }

                                            }else {
                                                DargeFaceSearchUtils.cameraTask(listBean,AppSearchActivity.this);
                                            }
                                        }else {
                                            DargeFaceSearchUtils.cameraTask(listBean,AppSearchActivity.this);
                                        }


                                    }



                                }


                            }
                        }
                    }
                });
            }
        };
        searchResult.setAdapter(searchResultListAdapter);
        //searchResultListAdapter.notifyDataSetChanged();

    }

    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 500L;


    @Override
    protected void onResume() {
        super.onResume();


        if(flag == 0){

            setSearchCache();
            List<String> searchHistory = ViewUtils.getSearchHistory();
            if(searchHistory.size() > 0){
                flSearchCache.setVisibility(View.VISIBLE);
                //tvSearchCache.setVisibility(View.VISIBLE);
                rl_history.setVisibility(View.VISIBLE);
            }else {
                rl_history.setVisibility(View.GONE);
                flSearchCache.setVisibility(View.GONE);
            }

        }else {
            isLogin = !StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""));

            setSearchCache();
            rl_history.setVisibility(View.VISIBLE);
            flSearchCache.setVisibility(View.GONE);


        }


    }

    /*历史记录数据填充*/
    private void setSearchCache() {

        final LayoutInflater mInflater = LayoutInflater.from(getApplication());

        flSearchCache.setAdapter(new TagAdapter<String>(ViewUtils.getSearchHistory()) {

            @Override
            public View getView(com.zhy.view.flowlayout.FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        flSearchCache, false);
                tv.setText(s);
                return tv;
            }
        });

        flSearchCache.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, com.zhy.view.flowlayout.FlowLayout parent)
            {
                netWorkSearchApp();
                tvSearch.setText(ViewUtils.getSearchHistory().get(position));

                flag = 1;
                rl_history.setVisibility(View.GONE);
                flSearchCache.setVisibility(View.GONE);
                return true;
            }
        });



    }

    private int isOpen = 0;

    @Override
    public void onPermissionsGranted(int requestCode, String... permission) {
        isOpen = 1;
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
