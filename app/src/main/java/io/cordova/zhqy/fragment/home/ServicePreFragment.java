package io.cordova.zhqy.fragment.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhqy.Main2Activity;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.activity.AppSearchActivity;
import io.cordova.zhqy.activity.LoginActivity;
import io.cordova.zhqy.bean.AppListBean;
import io.cordova.zhqy.bean.LoginBean;
import io.cordova.zhqy.bean.ServiceAppListBean;
import io.cordova.zhqy.bean.TestBean;
import io.cordova.zhqy.bean.UserMsgBean;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.BaseFragment;
import io.cordova.zhqy.utils.CircleCrop;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.ViewUtils;
import io.cordova.zhqy.web.BaseWebActivity;

import static io.cordova.zhqy.utils.AesEncryptUtile.key;

/**
 * Created by Administrator on 2018/11/19 0019.
 */

public class ServicePreFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
     @BindView(R.id.go_login)
    TextView go_login;
//     @BindView(R.id.tv_search)
//    TextView tvSearch;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_searcch)
    ImageView ivSearch;
    @BindView(R.id.rv_content)
    RecyclerView rvConent;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    private ArrayList<ArrayList<TestBean>> lists;
    //判读是否是recyclerView主动引起的滑动，true- 是，false- 否，由tablayout引起的
    private boolean isRecyclerScroll;
    private LinearLayoutManager manager;
    //记录上一次位置，防止在同一内容块里滑动 重复定位到tablayout
    private int lastPos;
    //用于recyclerView滑动到指定的位置
    private boolean canScroll;
    private int scrollToPosition;
    boolean isLogin =false;
    private int lastH;


    @Override
    public int getLayoutResID() {
        return R.layout.fragment_service_pre;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        tvTitle.setText("应用服务");
        ivBack.setVisibility(View.GONE);
        ivSearch.setVisibility(View.VISIBLE);
        go_login.setVisibility(View.GONE);
        isLogin = !StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""));
//        计算 高度
        int screenH = getScreenHeight();
        int statusBarH = getStatusBarHeight(getActivity());
        int tabH = 50 * 3;
        lastH = screenH - statusBarH - tabH;

        initShowPage();
    }

    private void initShowPage() {
//            tvSearch.setVisibility(View.VISIBLE);
//        netWorkAPPList();
        ViewUtils.createLoadingDialog(getActivity());
        if (isLogin){
            if (!StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"rolecodes",""))){
                netWorkServiceAPPList();
            }else {
                netWorkUserMsg();
            }
        }else {
//            游客获取app列表
            netWorkServiceAPPListYou();
        }
    }

    private void netWorkServiceAPPListYou() {

        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Service_APP_List)
                .params("Version", "1.0")
                .params("rolecodes","tourists")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ViewUtils.cancelLoadingDialog();
                        serviceAppListBean = JSON.parseObject(response.body(),ServiceAppListBean.class);
                        if (serviceAppListBean.isSuccess()){
                            setTap(serviceAppListBean);
                            setRvServiceList();

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ViewUtils.cancelLoadingDialog();
                    }
                });
    }


    /**个人信息*/
    UserMsgBean userMsgBean;
    private void netWorkUserMsg() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.User_Msg)
                .params("userId", (String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("result1",response.body()+"   --防空");
                        userMsgBean = JSON.parseObject(response.body(), UserMsgBean.class);
                        if (userMsgBean.isSuccess()) {

                            StringBuilder sb = new StringBuilder();
                            if (userMsgBean.getObj().getModules().getRolecodes().size() > 0){
                                for (int i = 0; i < userMsgBean.getObj().getModules().getRolecodes().size(); i++) {
                                    sb.append(userMsgBean.getObj().getModules().getRolecodes().get(i).getRoleCode()).append(",");
                                }
                            }
                            String ss = sb.substring(0, sb.lastIndexOf(","));
                            Log.e("TAG",ss);
                            SPUtils.put(MyApp.getInstance(),"rolecodes",ss);
                            netWorkServiceAPPList();
                        }
                    }
                });

    }

    ServiceAppListBean serviceAppListBean;
    private void netWorkServiceAPPList() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Service_APP_List)
                .params("Version", "1.0")
                .params("userId", (String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("rolecodes", (String) SPUtils.get(MyApp.getInstance(),"rolecodes",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ViewUtils.cancelLoadingDialog();
                        serviceAppListBean = JSON.parseObject(response.body(),ServiceAppListBean.class);
                        if (serviceAppListBean.isSuccess()){
                            setTap(serviceAppListBean);
                            setRvServiceList();

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ViewUtils.cancelLoadingDialog();
                    }
                });

    }
    CommonAdapter<ServiceAppListBean.ObjBean> adapterSysApp;
    CommonAdapter<ServiceAppListBean.ObjBean.AppsBean> adapterApp;
    private void setRvServiceList() {
        rvConent.setVisibility(View.VISIBLE);
        manager  = new LinearLayoutManager(getActivity());
        rvConent.setLayoutManager(manager);
        adapterSysApp = new CommonAdapter<ServiceAppListBean.ObjBean>(getActivity(),R.layout.itme_service_app_list,serviceAppListBean.getObj()) {
            @Override
            protected void convert(ViewHolder holder, ServiceAppListBean.ObjBean objBean, int position) {

                if (position == serviceAppListBean.getObj().size()) {
                    if (holder.getView(R.id.rv_app_list).getHeight() < lastH) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.height = lastH;
                        holder.getView(R.id.ll_ce_tb).setLayoutParams(params);
                    }
                }
                holder.setText(R.id.tv_content,objBean.getModulesName());
                RecyclerView recyclerView = holder.getView(R.id.rv_app_list);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
                adapterApp = new CommonAdapter<ServiceAppListBean.ObjBean.AppsBean>(getActivity(), R.layout.item_service_app,objBean.getApps()) {
                    @Override
                    protected void convert(ViewHolder holder, final ServiceAppListBean.ObjBean.AppsBean appsBean, int position) {
                        holder.setText(R.id.tv_app_name, appsBean.getAppName());

                        if (null != appsBean.getPortalAppIcon() && null != appsBean.getPortalAppIcon().getTempletAppImage()){

                            Glide.with(getActivity())
                                    .load(UrlRes.HOME3_URL + appsBean.getPortalAppIcon().getTempletAppImage())
                                    .error(getResources().getColor(R.color.app_bg))
                                    .into((ImageView) holder.getView(R.id.iv_app_icon));
                        }else {
                            Glide.with(getActivity())
                                    .load(UrlRes.HOME3_URL + appsBean.getAppImages())
                                    .error(getResources().getColor(R.color.app_bg))
                                    .into((ImageView) holder.getView(R.id.iv_app_icon));
                        }

                        /*appIntranet  1 需要内网*/
                        if (appsBean.getAppIntranet()==1){
                            holder.setVisible(R.id.iv_del,true);
                            Glide.with(getActivity())
                                    .load(R.mipmap.nei_icon)
                                    .error(R.mipmap.nei_icon)
                                    .into((ImageView) holder.getView(R.id.iv_del));
                        }else {
                            holder.setVisible(R.id.iv_del,false);
                        }
                        /*appLoginFlag  0 需要登录*/
                        if (!isLogin) {
                            if (appsBean.getAppLoginFlag()==0){
                                holder.setVisible(R.id.iv_lock_close,true);
                                Glide.with(getActivity())
                                        .load(R.mipmap.lock_icon)
                                        .error(R.mipmap.lock_icon)
                                        .into((ImageView) holder.getView(R.id.iv_lock_close));
                            }else {
                                holder.setVisible(R.id.iv_lock_close,false);
                            }
                        }
                        holder.setOnClickListener(R.id.ll_click, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (null != appsBean.getAppAndroidSchema()){
                                    if (!isLogin){
                                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                                        startActivity(intent);
                                    }else {
                                        String appUrl =  appsBean.getAppAndroidSchema()+"";
                                        String intercept = appUrl.substring(0,appUrl.indexOf(":")+3);
//                                    hasApplication(appUrl);
                                        Log.e("TAG", hasApplication(intercept)+"");
                                        if (hasApplication(intercept)){
                                            try {
                                                //直接根据Scheme打开软件  拼接参数
                                                if (appUrl.contains("{memberid}")){
                                                    String s1=  URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"personName",""), "UTF-8");
                                                    appUrl =  appUrl.replace("{memberid}", s1);
                                                }
                                                if (appUrl.contains("{memberAesEncrypt}")){
                                                    String memberAesEncrypt = AesEncryptUtile.encrypt((String) SPUtils.get(MyApp.getInstance(),"personName",""), String.valueOf(appsBean.getAppSecret()));
                                                    String s2=  URLEncoder.encode(memberAesEncrypt, "UTF-8");
                                                    appUrl =  appUrl.replace("{memberAesEncrypt}", s2);
//                                                 appUrl.substring(0,appUrl.indexOf("\"{memberAesEncrypt}\""));
                                                }
                                                if (appUrl.contains("{quicklyTicket}")){
                                                    String s3 =  URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"TGC",""), "UTF-8");
                                                    appUrl = appUrl.replace("{quicklyTicket}",s3);
                                                }
                                                Log.e("TAG", appUrl+"");
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                                startActivity(intent);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }else {
                                            //获取下载地址 后跳到浏览器下载
                                            if(null!= appsBean.getAppAndroidDownloadLink()){
                                                String dwon = appsBean.getAppAndroidDownloadLink()+"";
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dwon));
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                                startActivity(intent);
                                            }
                                        }
                                    }


                                }else if (!appsBean.getAppUrl().isEmpty()){
                                    netWorkAppClick(appsBean.getAppId());
                                    Log.e("url  ==",appsBean.getAppUrl() + "");
                                    Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity.class);
                                    intent.putExtra("appUrl",appsBean.getAppUrl());
                                    intent.putExtra("appId",appsBean.getAppId()+"");
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                };
                recyclerView.setAdapter(adapterApp);
                adapterApp.notifyDataSetChanged();

            }
        };
        rvConent.setAdapter(adapterSysApp);
        adapterSysApp.notifyDataSetChanged();
    }


    /**
     * 判断是否安装了应用
     * @return true 为已经安装
     */
    private boolean hasApplication(String scheme) {
        PackageManager manager = getActivity().getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse(scheme));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
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

    /**设置Tab  点击联动
     * @param serviceAppListBean
     * */

    @SuppressLint("ClickableViewAccessibility")
    private void setTap(ServiceAppListBean serviceAppListBean) {

        if (tablayout.getTabCount() > 0) {
            tablayout.removeAllTabs();
        }
        for (int i = 0; i < serviceAppListBean.getObj().size(); i++) {
            tablayout.addTab(tablayout.newTab().setText(serviceAppListBean.getObj().get(i).getModulesName()));
        }

        rvConent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当滑动由recyclerView触发时，isRecyclerScroll 置true
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    isRecyclerScroll = true;
                }
                return false;
            }
        });

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                        //点击标签，使recyclerView滑动，isRecyclerScroll置false
                int pos = tab.getPosition();
                isRecyclerScroll = false;
                moveToPosition(manager, rvConent, pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rvConent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (canScroll) {
                    canScroll = false;
                    moveToPosition(manager, recyclerView, scrollToPosition);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isRecyclerScroll) {
                    //第一个可见的view的位置，即tablayou需定位的位置
//                    int position = 0;
                    int position = manager.findFirstVisibleItemPosition();
                    if (lastPos != position) {
                        tablayout.setScrollPosition(position, 0, true);
                    }
                    lastPos = position;
                }
            }
        });

    }



    @OnClick(R.id.iv_searcch)
    public void onViewClicked() {
        //跳转到搜索页面
        Intent intent = new Intent(MyApp.getInstance(), AppSearchActivity.class);
        startActivity(intent);
    }

    public void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int position) {
        // 第一个可见的view的位置
        int firstItem = manager.findFirstVisibleItemPosition();
        // 最后一个可见的view的位置
        int lastItem = manager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            // 如果跳转位置firstItem 之前(滑出屏幕的情况)，就smoothScrollToPosition可以直接跳转，
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在firstItem 之后，lastItem 之间（显示在当前屏幕），smoothScrollBy来滑动到指定位置
            int top = mRecyclerView.getChildAt(position - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            // 如果要跳转的位置在lastItem 之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用当前moveToPosition方法，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            scrollToPosition = position;
            canScroll = true;
        }
    }

    /**屏幕高度获取*/
    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    /**得到Android状态栏高度*/
    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onResume() {
//        initShowPage();
        super.onResume();

    }
}
