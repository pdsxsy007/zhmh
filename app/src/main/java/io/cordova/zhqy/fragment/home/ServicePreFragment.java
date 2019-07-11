package io.cordova.zhqy.fragment.home;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.activity.AppSearchActivity;
import io.cordova.zhqy.activity.LoginActivity2;
import io.cordova.zhqy.bean.MyCollectionBean;
import io.cordova.zhqy.bean.ServiceAppListBean;
import io.cordova.zhqy.bean.TestBean;
import io.cordova.zhqy.bean.UserMsgBean;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.BaseFragment;
import io.cordova.zhqy.utils.CookieUtils;
import io.cordova.zhqy.utils.LighterHelper;
import io.cordova.zhqy.utils.MobileInfoUtils;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.ViewUtils;
import io.cordova.zhqy.utils.netState;
import io.cordova.zhqy.web.BaseWebActivity;
import io.cordova.zhqy.web.BaseWebActivity2;
import io.cordova.zhqy.web.BaseWebActivity3;
import io.cordova.zhqy.web.BaseWebActivity4;
import me.samlss.lighter.Lighter;
import me.samlss.lighter.interfaces.OnLighterListener;
import me.samlss.lighter.parameter.Direction;
import me.samlss.lighter.parameter.LighterParameter;
import me.samlss.lighter.parameter.MarginOffset;
import me.samlss.lighter.shape.CircleShape;

import static io.cordova.zhqy.utils.MyApp.getInstance;

/**
 * Created by Administrator on 2018/11/19 0019.
 */

public class ServicePreFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_searcch)
    ImageView ivSearch;
    @BindView(R.id.rv_content)
    RecyclerView rvConent;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.rl_no)
    RelativeLayout rl_no;

    @BindView(R.id.rl_load)
    RelativeLayout rl_load;
    @BindView(R.id.webView)
    WebView webView;
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

    private int flag = 0;

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
        isLogin = !StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""));
//        计算 高度
        int screenH = getScreenHeight();
        int statusBarH = getStatusBarHeight(getActivity());
        int tabH = 50 * 3;
        lastH = screenH - statusBarH - tabH;


        checkNetState();
        rl_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNetState();
            }
        });
        flag = 0;
        String home03 = (String) SPUtils.get(MyApp.getInstance(), "home03", "");
        if(home03.equals("")){
            setGuideView();
        }
    }

    private void setGuideView() {

        CircleShape circleShape = new CircleShape(10);
        circleShape.setPaint(LighterHelper.getDashPaint()); //set custom paint

        // 使用图片
        Lighter.with(getActivity()
        )
                .setBackgroundColor(0xB9000000)
                .setOnLighterListener(new OnLighterListener() {
                    @Override
                    public void onShow(int index) {


                    }

                    @Override
                    public void onDismiss() {
                        SPUtils.put(MyApp.getInstance(),"home03","1");
                    }
                })
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.msg_hint)
                        .setTipLayoutId(R.layout.fragment_home_gl4)
                        //.setLighterShape(new RectShape(80, 80, 50))
                        //.setLighterShape(circleShape)
                        .setTipViewRelativeDirection(Direction.BOTTOM)
                        .setTipViewRelativeOffset(new MarginOffset(150, 0, 30, 0))
                        .build()).show();
    }

    private void checkNetState() {
        if (!netState.isConnect(getActivity()) ){
            ToastUtils.showToast(getActivity(),"网络连接异常!");
            rl_no.setVisibility(View.VISIBLE);
        }else {
            registerBoradcastReceiver();
            registerBoradcastReceiver2();
            rl_no.setVisibility(View.GONE);
            initShowPage();
        }
    }


    private void registerBoradcastReceiver2() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("refresh2");
        //注册广播
        getActivity().registerReceiver(broadcastReceiver, myIntentFilter);
    }


    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("refresh");
        //注册广播
        getActivity().registerReceiver(broadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("refresh")){
                isLogin = false;
                flag = 1;
                initShowPage();
            }else if(action.equals("refresh2")){
                isLogin = true;
                flag = 1;
                initShowPage();
            }
        }
    };
    private void initShowPage() {
//            tvSearch.setVisibility(View.VISIBLE);
//        netWorkAPPList();
        //ViewUtils.createLoadingDialog(getActivity());
        String rolecodes = (String) SPUtils.get(MyApp.getInstance(), "rolecodes", "");
        String userId = (String) SPUtils.get(MyApp.getInstance(),"userId","");
        if (isLogin){
            if(obj1 != null){
                obj1.clear();
            }
            if (!StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"rolecodes",""))){
                getMyCollectDatas();
                //netWorkServiceAPPList();
                //rvConent.scrollToPosition(lastItemPosition);

            }else {
                netWorkUserMsg();
            }
        }else {
            if(obj1 != null){
                obj1.clear();
            }
//            游客获取app列表
            netWorkServiceAPPListYou();
        }
    }
    ServiceAppListBean serviceAppListBean1;
    private void getMyCollectDatas() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.My_Collection)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());
                        serviceAppListBean1 = new ServiceAppListBean();

                        MyCollectionBean collectionBean = JSON.parseObject(response.body(), MyCollectionBean.class);
                        if (collectionBean.isSuccess()) {
                            if(collectionBean.getObj() != null){
                                if (collectionBean.getObj().size() > 0) {
                                    List<MyCollectionBean.ObjBean> obj = collectionBean.getObj();
                                    List<ServiceAppListBean.ObjBean.AppsBean> listsApp = new ArrayList<>();
                                    for (int i = 0; i < obj.size(); i++) {
                                        ServiceAppListBean.ObjBean.AppsBean appsBean = new ServiceAppListBean.ObjBean.AppsBean();
                                        int appLoginFlag = obj.get(i).getAppLoginFlag();
                                        int appId = obj.get(i).getAppId();
                                        String appAndroidSchema = obj.get(i).getAppAndroidSchema();
                                        String appName = obj.get(i).getAppName();
                                        String appUrl = obj.get(i).getAppUrl();
                                        String appImages = obj.get(i).getAppImages();
                                        int appIntranet = obj.get(i).getAppIntranet();
                                        Object appSecret = obj.get(i).getAppSecret();
                                        MyCollectionBean.ObjBean.PortalAppIconBean portalAppIcon = obj.get(i).getPortalAppIcon();
                                        if(appAndroidSchema != null){
                                            appsBean.setAppAndroidSchema(appAndroidSchema);
                                        }
                                        appsBean.setAppLoginFlag(appLoginFlag);
                                        appsBean.setAppId(appId);
                                        appsBean.setAppName(appName);
                                        appsBean.setAppUrl(appUrl);
                                        appsBean.setAppIntranet(appIntranet);
                                        appsBean.setAppSecret(appSecret);
                                        if(appImages != null){
                                            appsBean.setAppImages(appImages);
                                        }
                                        ServiceAppListBean.ObjBean.AppsBean.PortalAppIconBean portalAppIconBean = new ServiceAppListBean.ObjBean.AppsBean.PortalAppIconBean();
                                        if(portalAppIcon != null){
                                            String templetAppImage = obj.get(i).getPortalAppIcon().getTempletAppImage();
                                            if(templetAppImage != null){
                                                portalAppIconBean.setTempletAppImage(templetAppImage);
                                            }
                                        }
                                        appsBean.setPortalAppIcon(portalAppIconBean);
                                        listsApp.add(appsBean);
                                    }
                                    List<ServiceAppListBean.ObjBean> objBeans = new ArrayList<>();
                                    ServiceAppListBean.ObjBean obj1 = new ServiceAppListBean.ObjBean();
                                    obj1.setModulesName("我的收藏");
                                    obj1.setApps(listsApp);
                                    objBeans.add(obj1);
                                    serviceAppListBean1.setObj(objBeans);
                                    netWorkServiceAPPList();
                                }
                            }else {
                                List<ServiceAppListBean.ObjBean> objBeans = new ArrayList<>();
                                ServiceAppListBean.ObjBean obj1 = new ServiceAppListBean.ObjBean();
                                obj1.setModulesName("我的收藏");
                                serviceAppListBean1.setObj(objBeans);
                                netWorkServiceAPPList();
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);


                    }
                });
    }

    private void netWorkServiceAPPListYou() {

        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Service_APP_List)
                .params("Version", "1.0")
                .params("rolecodes","tourists")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("游客",response.toString());
                        //ViewUtils.cancelLoadingDialog();
                        obj1.clear();
                        serviceAppListBean1 = new ServiceAppListBean();
                        serviceAppListBean = JSON.parseObject(response.body(),ServiceAppListBean.class);
                        if (serviceAppListBean.isSuccess()){
                            obj1.addAll(serviceAppListBean.getObj());
                            setTap(serviceAppListBean);
                            setRvServiceList();

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //ViewUtils.cancelLoadingDialog();
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
                            if(userMsgBean.getObj() != null){
                                if (userMsgBean.getObj().getModules().getRolecodes().size() > 0){
                                    for (int i = 0; i < userMsgBean.getObj().getModules().getRolecodes().size(); i++) {
                                        sb.append(userMsgBean.getObj().getModules().getRolecodes().get(i).getRoleCode()).append(",");
                                    }
                                }
                                String ss = sb.substring(0, sb.lastIndexOf(","));
                                Log.e("TAG",ss);
                                SPUtils.put(MyApp.getInstance(),"rolecodes",ss);
                                getMyCollectDatas();
                            }else {
                                /*ToastUtils.showToast(getActivity(),"获取个人信息失败!");
                                ViewUtils.cancelLoadingDialog();*/
                                //getMyCollectDatas();
                                netWorkServiceAPPList();
                            }



                        }
                    }
                });

    }

    ServiceAppListBean serviceAppListBean;
    List<ServiceAppListBean.ObjBean> obj1 = new ArrayList<>();
    private void netWorkServiceAPPList() {
        Log.e("userId---------------",(String) SPUtils.get(MyApp.getInstance(),"userId",""));
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Service_APP_List)
                .params("Version", "1.0")
                .params("userId", (String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("rolecodes", (String) SPUtils.get(MyApp.getInstance(),"rolecodes",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("ssadsadsa--------------",response.body());
                        //ViewUtils.cancelLoadingDialog();
                        serviceAppListBean = JSON.parseObject(response.body(),ServiceAppListBean.class);

                        if (serviceAppListBean.isSuccess()){

                            obj1 = serviceAppListBean1.getObj();
                            List<ServiceAppListBean.ObjBean> obj = serviceAppListBean.getObj();
                            obj1.addAll(obj);
                            setTap(serviceAppListBean);
                            setRvServiceList();

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //ViewUtils.cancelLoadingDialog();
                    }
                });

    }
    CommonAdapter<ServiceAppListBean.ObjBean> adapterSysApp;
    CommonAdapter<ServiceAppListBean.ObjBean.AppsBean> adapterApp;
    int lastItemPosition;
    int firstItemPosition;
    private void setRvServiceList() {
        rvConent.setVisibility(View.VISIBLE);
        manager  = new LinearLayoutManager(getActivity());
        rvConent.setLayoutManager(manager);

        adapterSysApp = new CommonAdapter<ServiceAppListBean.ObjBean>(getActivity(),R.layout.itme_service_app_list,obj1) {
            @Override
            protected void convert(ViewHolder holder, ServiceAppListBean.ObjBean objBean, int position) {

               /* if (position == serviceAppListBean.getObj().size()) {
                    if (holder.getView(R.id.rv_app_list).getHeight() < lastH) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.height = lastH;
                        holder.getView(R.id.ll_ce_tb).setLayoutParams(params);
                    }
                }*/
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
                                        Intent intent = new Intent(getActivity(),LoginActivity2.class);
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
                                    if (!isLogin){
                                        //if(appsBean.getAppIntranet()==1){
                                        if(appsBean.getAppLoginFlag()==0){
                                            Intent intent = new Intent(getActivity(),LoginActivity2.class);
                                            startActivity(intent);
                                        }else {
                                            Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                                            if (netState.isConnect(getActivity())) {
                                                netWorkAppClick(appsBean.getAppId());
                                            }
                                            Log.e("url  ==",appsBean.getAppUrl() + "");
                                            intent.putExtra("appUrl",appsBean.getAppUrl());
                                            intent.putExtra("appId",appsBean.getAppId()+"");
                                            intent.putExtra("appName",appsBean.getAppName()+"");
                                            startActivity(intent);
                                        }

                                    }else {
                                        if (netState.isConnect(getActivity())) {
                                            netWorkAppClick(appsBean.getAppId());
                                        }
                                        if(appsBean.getAppUrl().equals("http://iapp.zzuli.edu.cn/portal/app/mailbox/qqEmailLogin")){
                                           /* webView.setWebViewClient(mWebViewClient);
                                            webView.loadUrl("http://iapp.zzuli.edu.cn/portal/login/appLogin");*/
                                            Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                                            intent.putExtra("appUrl",appsBean.getAppUrl());
                                            intent.putExtra("appId",appsBean.getAppId()+"");
                                            intent.putExtra("appName",appsBean.getAppName()+"");
                                           /* intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                                            intent.addCategory(Intent.CATEGORY_OPENABLE);*/
                                            startActivity(intent);
                                        }else {
                                            Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                                            if (appsBean.getAppUrl().contains("{memberid}")){
                                                String appUrl = appsBean.getAppUrl();
                                                String s1= null;
                                                try {
                                                    s1 = URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"personName",""), "UTF-8");
                                                    appUrl =  appUrl.replace("{memberid}", s1);
                                                    intent.putExtra("appUrl",appUrl);
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }

                                            }else if(appsBean.getAppUrl().contains("{memberAesEncrypt}")){
                                                String appUrl = appsBean.getAppUrl();
                                                try {
                                                    String memberAesEncrypt = AesEncryptUtile.encrypt((String) SPUtils.get(MyApp.getInstance(),"personName",""), String.valueOf(appsBean.getAppSecret()));
                                                    String s2=  URLEncoder.encode(memberAesEncrypt, "UTF-8");
                                                    appUrl =  appUrl.replace("{memberAesEncrypt}", s2);
                                                    intent.putExtra("appUrl",appUrl);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }else if(appsBean.getAppUrl().contains("{quicklyTicket}")){
                                                String appUrl = appsBean.getAppUrl();
                                                try {
                                                    String s3 =  URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"TGC",""), "UTF-8");
                                                    appUrl = appUrl.replace("{quicklyTicket}",s3);
                                                    intent.putExtra("appUrl",appUrl);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            else{
                                                intent.putExtra("appUrl",appsBean.getAppUrl());
                                            }
                                            Log.e("url  ==",appsBean.getAppUrl() + "");

                                            //intent.putExtra("appUrl",appsBean.getAppUrl());
                                            intent.putExtra("appId",appsBean.getAppId()+"");
                                            intent.putExtra("appName",appsBean.getAppName()+"");
                                            startActivity(intent);
                                        }

                                    }


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

        //tablayout.setScrollPosition(firstItemPosition,0,true);
        //moveToPosition(manager, rvConent, firstItemPosition);
        manager.scrollToPositionWithOffset(firstItemPosition, 0);
        //isRecyclerScroll = true;
        //manager.scrollToPositionWithOffset(firstItemPosition, 0);
       /* tablayout.setScrollPosition(firstItemPosition, 0f, true);
        manager.scrollToPositionWithOffset(firstItemPosition, 0);*/

        rvConent.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置

                    lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置

                    firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    Log.e("lastItemPosition------",lastItemPosition+"");
                   Log.e("firstItemPosition------",firstItemPosition+"");
                }
            }
        });

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

        for (int i = 0; i < obj1.size(); i++) {
            tablayout.addTab(tablayout.newTab().setText(obj1.get(i).getModulesName()));
        }
        //设置分割线
        LinearLayout linearLayout = (LinearLayout) tablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider)); //设置分割线的样式
        linearLayout.setDividerPadding(dip2px(10)); //设置分割线间隔


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
                tablayout.setScrollPosition(pos,0,true);
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
                isRecyclerScroll =false;
                if (canScroll) {
                    canScroll = false;
                    moveToPosition(manager, recyclerView, scrollToPosition);

                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isRecyclerScroll = true;
                if (isRecyclerScroll) {
                    //第一个可见的view的位置，即tablayou需定位的位置
//                    int position = 0;

                    int position = manager.findFirstVisibleItemPosition();
                    tablayout.setScrollPosition(position, 0, true);
                    if (lastPos != position) {
                        tablayout.setScrollPosition(position, 0, true);
                    }
                    lastPos = position;
                }else {
                    tablayout.setScrollPosition(firstItemPosition, 0, true);
                    //tablayout.setScrollPosition(scrollToPosition,0,true);
                }
            }
        });

    }
    //像素单位转换
    public int dip2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
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

        super.onResume();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            //做一些事情 你懂得
          /*  if(obj1 != null){
                obj1.clear();
            }*/
            if (isLogin){
                if (!StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"rolecodes",""))){
                    getMyCollectDatas();

                }else {
                    netWorkUserMsg();
                }

            }else {
                /*if(obj1 != null){
                    obj1.clear();
                }*/
//            游客获取app列表
                netWorkServiceAPPListYou();
            }


            return;
        }else{  // 在最前端显示 相当于调用了onResume();
            netInsertPortal("3");
            registerBoradcastReceiver();
            registerBoradcastReceiver2();
            if (isLogin){
                webView.setWebViewClient(mWebViewClient);
                webView.loadUrl("http://iapp.zzuli.edu.cn/portal/login/appLogin");
            }
        }

    }

    private void netInsertPortal(final String insertPortalAccessLog) {
        String imei = MobileInfoUtils.getIMEI(getActivity());
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Four_Modules)
                .params("portalAccessLogMemberId",(String) SPUtils.get(getInstance(),"userId",""))
                .params("portalAccessLogEquipmentId",(String) SPUtils.get(getInstance(),"imei",""))//设备ID
                .params("portalAccessLogTarget", insertPortalAccessLog)//访问目标
                .params("portalAccessLogVersionNumber", (String) SPUtils.get(getActivity(),"versionName", ""))//版本号
                .params("portalAccessLogOperatingSystem", "ANDROID")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("sdsaas",response.body());

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("userAgent4",  view.getSettings().getUserAgentString());


        }

        //        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url =  null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            }


            if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getActivity(),LoginActivity2.class);
                    startActivity(intent);

                    return true;
                }
            }
//            else if (url.contains("myoa.zzuli.edu.cn")) {
//                if (!url.contains("fromnewcas=Y")){
//                    url = url + "&fromnewcas=Y";
//
//                    view.loadUrl(url);
//                    Log.i("url", "== " + url);
//                    return true;
//                }
//                return false;
//            }
//            Log.i("url2", "== " + url);
            return super.shouldOverrideUrlLoading(view, request);
        }

        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getActivity(),LoginActivity2.class);
                    startActivity(intent);
                    return true;
                }
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            CookieUtils.syncCookie("http://kys.zzuli.edu.cn","CASTGC="+SPUtils.get(getActivity(),"TGC",""),getActivity());


        }

    };

}
