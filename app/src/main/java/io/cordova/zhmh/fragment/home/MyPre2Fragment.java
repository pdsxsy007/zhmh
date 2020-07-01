package io.cordova.zhmh.fragment.home;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.View;

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
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.activity.AppSetting;
import io.cordova.zhmh.activity.LoginActivity2;
import io.cordova.zhmh.activity.MyCollectionActivity;
import io.cordova.zhmh.activity.MyDataActivity;
import io.cordova.zhmh.activity.MyToDoMsgActivity;
import io.cordova.zhmh.bean.CountBean;
import io.cordova.zhmh.bean.MyCollectionBean;
import io.cordova.zhmh.bean.OAMsgListBean;
import io.cordova.zhmh.bean.ServiceAppListBean;
import io.cordova.zhmh.bean.UserMsgBean;
import io.cordova.zhmh.utils.AesEncryptUtile;
import io.cordova.zhmh.utils.BadgeView;
import io.cordova.zhmh.utils.BaseFragment;
import io.cordova.zhmh.utils.DargeFaceByMefColletUtils;
import io.cordova.zhmh.utils.DargeFaceByMefgUtils;
import io.cordova.zhmh.utils.FinishActivity;
import io.cordova.zhmh.utils.LighterHelper;
import io.cordova.zhmh.utils.MobileInfoUtils;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.NetStateUtils;
import io.cordova.zhmh.utils.PermissionsUtil;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.StringUtils;
import io.cordova.zhmh.utils.ToastUtils;
import io.cordova.zhmh.utils.netState;
import io.cordova.zhmh.web.BaseWebActivity4;
import io.cordova.zhmh.web.BaseWebCloseActivity;
import io.cordova.zhmh.widget.XCRoundImageView;
import me.samlss.lighter.Lighter;
import me.samlss.lighter.interfaces.OnLighterListener;
import me.samlss.lighter.parameter.Direction;
import me.samlss.lighter.parameter.LighterParameter;
import me.samlss.lighter.parameter.MarginOffset;
import me.samlss.lighter.shape.CircleShape;

import static io.cordova.zhmh.utils.MyApp.getInstance;

/**
 * Created by Administrator on 2018/11/22 0022.
 */

public class MyPre2Fragment extends BaseFragment implements PermissionsUtil.IPermissionsCallback{
    @BindView(R.id.tv_app_msg)
    ImageView ivAppMsg;
    @BindView(R.id.iv_user_head)
    XCRoundImageView ivUserHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
   @BindView(R.id.tv_zhuan_ye)
    TextView tvZhangye;
    @BindView(R.id.rv_user_data)
    RelativeLayout rvUserData;
    @BindView(R.id.tv_data_num)
    TextView tvDataNum;
  //  @BindView(R.id.tv_app_setting1)
    ImageView tvAppSetting;
    @BindView(R.id.tv_my_collection_num)
    TextView tvMyCollectionNum;
    @BindView(R.id.tv_my_to_do_msg_num)
    TextView tvMyToDoMsgNum;
    @BindView(R.id.my_oa_to_do_list)
    RecyclerView myOaToDoList;
    @BindView(R.id.ll_oa)
    LinearLayout llOa;
    @BindView(R.id.my_collection_list)
    RecyclerView myCollectionList;
    @BindView(R.id.ll_my_collection)
    LinearLayout llMyCollection;
    @BindView(R.id.my_data_list)
    RecyclerView myDataList;
    @BindView(R.id.ll_my_data)
    LinearLayout llMyData;
    @BindView(R.id.rl_msg_app)
    RelativeLayout rlMsgApp;

    @BindView(R.id.rl_title)
    RelativeLayout rl_title;

    private static Object object = new Object();
    int allMsgNum = 0;

    boolean isLogin = false;
    BadgeView badge1;
    private PermissionsUtil permissionsUtil;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment2_my_pre;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        count = (String) SPUtils.get(getActivity(), "count", "");
        tvAppSetting = view.findViewById(R.id.tv_app_setting1);
        badge1 = new BadgeView(getActivity(), rlMsgApp);
        remind();
        if(count != null){
            if(count.equals("0")){

                badge1.hide();
            }else {
                badge1.show();
            }
        }else {
            badge1.hide();
        }
        tvMyToDoMsgNum.setText(count);
        initLoadPage();

        String home04 = (String) SPUtils.get(MyApp.getInstance(), "home04", "");
        if(home04.equals("")){
            setGuideView();
        }
    }

    private void setGuideView() {
        CircleShape circleShape = new CircleShape(10);
        circleShape.setPaint(LighterHelper.getDashPaint()); //set custom paint
        // 使用图片
        Lighter.with(getActivity())
                .setBackgroundColor(0xB9000000)
                .setOnLighterListener(new OnLighterListener() {
                    @Override
                    public void onShow(int index) {


                    }

                    @Override
                    public void onDismiss() {
                        SPUtils.put(MyApp.getInstance(),"home04","1");
                    }
                })
                .addHighlight(new LighterParameter.Builder()
                        //.setHighlightedViewId(R.id.tv_app_setting1)
                        .setHighlightedView(tvAppSetting)
                        .setTipLayoutId(R.layout.fragment_home_gl5)
                        //.setLighterShape(new RectShape(80, 80, 50))
                        .setLighterShape(circleShape)
                        .setTipViewRelativeDirection(Direction.BOTTOM)
                        .setTipViewRelativeOffset(new MarginOffset(150, 0, 30, 0))
                        .build(),
                        new LighterParameter.Builder()
                        //.setHighlightedViewId(R.id.tv_app_setting1)
                        .setHighlightedView(rlMsgApp)
                        .setTipLayoutId(R.layout.fragment_home_gl3)
                        //.setLighterShape(new RectShape(80, 80, 50))
                        .setLighterShape(circleShape)
                        .setTipViewRelativeDirection(Direction.BOTTOM)
                        .setTipViewRelativeOffset(new MarginOffset(150, 0, 30, 0))
                        .build()).show();
    }

    private void remind() { //BadgeView的具体使用
        if(count.equals("")){
            count = "0";
        }
        int i1 = Integer.parseInt(count);
        if(i1>99){
            badge1.setText("99+"); // 需要显示的提醒类容
        }else {
            badge1.setText(count); // 需要显示的提醒类容
        }

        SPUtils.put(getActivity(),"count",count+"");
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setTextColor(Color.WHITE); // 文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
        //badge1.setBackgroundResource(R.mipmap.icon_message_png); //设置背景图片
        badge1.setTextSize(10); // 文本大小
        badge1.setBadgeMargin(3, 3); // 水平和竖直方向的间距
//        badge1.setBadgeMargin(5); //各边间隔
        // badge1.toggle(); //显示效果，如果已经显示，则影藏，如果影藏，则显示
        badge1.show();// 只有显示
        // badge1.hide();//影藏显示
    }
    /**判断登录状态*/
    private void initLoadPage() {
        isLogin = !StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""));
        isLoginState();
    }

    /**判断是否登录*/
    private void isLoginState() {
        if (isLogin){
            netWorkUserMsg();
            netWorkSystemMsg();
            netWorkMyCollection();//我的收藏

            dbDataList();//OA待办消息
//            tvDataNum.setText("11");

        }else {
           /* ((Main2Activity)  getActivity()).mainRadioGroup.check(R.id.rb_home_page);
            ((Main2Activity)  getActivity()).showFragment(0);*/
        }
    }

    private void dbDataList() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_workFolwDbList)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type","db")
                .params("size", 15)
                .params("workType","workdb")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("s",response.toString());
                        oaMsgListBean = JSON.parseObject(response.body(), OAMsgListBean.class);
                        if (oaMsgListBean.isSuccess()) {
                            Log.i("消息列表",response.body());
                            if(oaMsgListBean.getCount() > 0){
                                setRvOAMsgList();
                            }else {
                                llOa.setVisibility(View.GONE);
                            }


                        }else {
                            //T.showShort(MyApp.getInstance(), "没有数据");
                            llOa.setVisibility(View.GONE);
                        }

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);


                    }
                });
    }

    /**点击事件*/
    @OnClick({R.id.rv_user_data, R.id.rv_my_collection, R.id.rv_my_to_do_msg, R.id.exit_login,R.id.tv_app_setting1,R.id.tv_app_msg})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rv_user_data:
                if (isLogin){
                    intent = new Intent(MyApp.getInstance(), MyDataActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rv_my_collection:
                if (isLogin){
                    intent = new Intent(MyApp.getInstance(), MyCollectionActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rv_my_to_do_msg:
            case R.id.tv_app_msg:
                if (isLogin){
                    //intent = new Intent(MyApp.getInstance(), NewMyToDoMsgActivity.class);
                    intent = new Intent(MyApp.getInstance(), MyToDoMsgActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_app_setting1:
                if (isLogin){
                    intent = new Intent(MyApp.getInstance(), AppSetting.class);
                    startActivity(intent);
                    FinishActivity.addActivity(getActivity());
                }
                break;
            case R.id.exit_login:
                break;
        }
    }

    /**个人信息*/
    UserMsgBean userMsgBean;
    private void netWorkUserMsg() {
        try {
            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.User_Msg)
                    .params("userId", (String) SPUtils.get(MyApp.getInstance(),"userId",""))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            //Log.e("result1",response.body()+"   --防空");
                            userMsgBean = JSON.parseObject(response.body(), UserMsgBean.class);
                            if (userMsgBean.isSuccess()) {
                                if(null != userMsgBean.getObj()) {
                                    if(userMsgBean.getObj().getModules().getMemberOtherDepartment() != null){
                                        tvZhangye.setText(userMsgBean.getObj().getModules().getMemberOtherDepartment());
                                    }

                                    tvUserName.setText(userMsgBean.getObj().getModules().getMemberNickname());

                                    StringBuilder sb = new StringBuilder();
                                    if(userMsgBean.getObj().getModules().getRolecodes()!= null){

                                        if (userMsgBean.getObj().getModules().getRolecodes().size() > 0){
                                            for (int i = 0; i < userMsgBean.getObj().getModules().getRolecodes().size(); i++) {
                                                sb.append(userMsgBean.getObj().getModules().getRolecodes().get(i).getRoleCode()).append(",");
                                            }
                                            String ss = sb.substring(0, sb.lastIndexOf(","));
                                            //Log.e("TAG",ss);
                                            SPUtils.put(MyApp.getInstance(),"rolecodes",ss);
                                        }

                                        String ss = sb.substring(0, sb.lastIndexOf(","));
                                        //Log.e("TAG",ss);
                                        SPUtils.put(MyApp.getInstance(),"rolecodes",ss);
                                    }

                                     /*获取头像*/
                                    netGetUserHead();
                                    netWorkMyData();//我的信息
                                }else {
                                    llMyData.setVisibility(View.GONE);
                                }



                            }
                        }
                    });
        }catch (Exception e){

        }


    }

    private void netGetUserHead() {
//        ?memberId=admin&pwd=d632eeeb1548643667060e18656e0112
        try {
            String pwd = URLEncoder.encode(userMsgBean.getObj().getModules().getMemberPwd(),"UTF-8");
         String ingUrl =  UrlRes.HOME_URL + "/authentication/public/getHeadImg?memberId="+userMsgBean.getObj().getModules().getMemberUsername()+"&pwd="+pwd;

            SPUtils.put(MyApp.getInstance(),"logoUrl",ingUrl);
            Glide.with(getActivity())
                    .load(ingUrl)
                    .asBitmap()
                    .placeholder(R.mipmap.tabbar_user_pre2)
                    .into(ivUserHead);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    CountBean countBean2;
    /**OA消息列表*/
    OAMsgListBean oaMsgListBean;
//    private void netWorkOAToDoMsg() {
//        try{
//            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
//                    .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
//                    .params("type", "db")
//                    .params("workType", "workdb")
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(Response<String> response) {
//                            //Log.e("s",response.toString());
//
//                            countBean2 = JSON.parseObject(response.body(), CountBean.class);
//                            netWorkDyMsg();
//                        }
//
//                        @Override
//                        public void onError(Response<String> response) {
//                            super.onError(response);
//
//                        }
//                    });
//        }catch (Exception e){
//
//        }
//
//    }

//    private void netWorkOAToDoMsg() {
//        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.countUserMessagesByTypeUrl)
//                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
//                .params("type", "1")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        //Log.e("s",response.body());
//                        countBean2 = JSON.parseObject(response.body(), CountBean.class);
//                        netWorkDyMsg();
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//
//                    }
//                });
//
//    }
    /**OA消息列表填充*/
    CommonAdapter<OAMsgListBean.ObjBean> oaAdapter;
    private void setRvOAMsgList() {
        llOa.setVisibility(View.VISIBLE);
        myOaToDoList.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        oaAdapter = new CommonAdapter<OAMsgListBean.ObjBean>(getContext(),R.layout.item_to_do_my_msg,oaMsgListBean.getObj()) {
            @Override
            protected void convert(ViewHolder holder, OAMsgListBean.ObjBean objBean, int position) {
                holder.setVisible(R.id.tv_msg_num,false);
                ImageView iv = holder.getConvertView().findViewById(R.id.oa_img);
                switch (position%6){
                    case 0:
                        Glide.with(mContext)
                                .load(R.mipmap.message_icon2)
                                //.transform(new CircleCrop(mContext))
                                .into(iv);
                        break;
                    case 1:
                        Glide.with(mContext)
                                .load(R.mipmap.message_icon1)
                               // .transform(new CircleCrop(mContext))
                                .into(iv);
                        break;
                    case 2:
                        Glide.with(mContext)
                                .load(R.mipmap.message_icon2)
                                //.transform(new CircleCrop(mContext))
                                .into(iv);
                        break;
                    case 3:
                        Glide.with(mContext)
                                .load(R.mipmap.message_icon4)
                                //.transform(new CircleCrop(mContext))
                                .into(iv);
                        break;
                    case 4:
                        Glide.with(mContext)
                                .load(R.mipmap.message_icon3)
                                //.transform(new CircleCrop(mContext))
                                .into(iv);
                        break;
                    case 5:
                        Glide.with(mContext)
                                .load(R.mipmap.message_icon5)
                                //.transform(new CircleCrop(mContext))
                                .into(iv);
                        break;
                }
                holder.setText(R.id.tv_name,objBean.getYwlx());
                holder.setText(R.id.tv_present,objBean.getTitle());

            }
        };
        myOaToDoList.setAdapter(oaAdapter);
        oaAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (!oaMsgListBean.getObj().get(position).getTodourl().isEmpty()){
                    //Log.e("url  ==",oaMsgListBean.getObj().get(position).getTodourl()+ "");
                    if (null != oaMsgListBean.getObj().get(position).getTodourl()){
                        Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                        intent.putExtra("appUrl",oaMsgListBean.getObj().get(position).getTodourl());
                        intent.putExtra("oaMsg","oaMsg");
                        intent.putExtra("appName",oaMsgListBean.getObj().get(position).getYwlx());
                        intent.putExtra("scan","scan");
                        //intent.putExtra("appName",oaMsgListBean.getObj().get(position).getAppName()+"");
//                    intent.putExtra("appId",listBean.getAppId()+"");
                        startActivity(intent);
                    }

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        oaAdapter.notifyDataSetChanged();
    }
    
    /**我的收藏列表*/
    MyCollectionBean collectionBean;
    private void netWorkMyCollection() {
        try {
            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.My_Collection)
                    .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            //Log.e("s",response.toString());
                            collectionBean = JSON.parseObject(response.body(), MyCollectionBean.class);
                            if (collectionBean.isSuccess()) {
                                if(collectionBean.getObj() != null){
                                    if (collectionBean.getObj().size() > 0) {
                                        llMyCollection.setVisibility(View.VISIBLE);
                                        tvMyCollectionNum.setText(collectionBean.getObj().size() + "");
                                        setCollectionList();

                                    } else {
                                        llMyCollection.setVisibility(View.GONE);

                                    }
                                }else {
                                    llMyCollection.setVisibility(View.GONE);
                                    tvMyCollectionNum.setText("0");
                                }

                            } else {
                                llMyCollection.setVisibility(View.GONE);
//                            T.showShort(MyApp.getInstance(), collectionBean.getMsg());
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            llMyCollection.setVisibility(View.GONE);

                        }
                    });
        }catch (Exception e){

        }

    }

    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 500L;

    /**我的收藏列表填充*/
    CommonAdapter<MyCollectionBean.ObjBean> collectionAdapter;
    private void setCollectionList() {
        myCollectionList.setLayoutManager(new GridLayoutManager(getActivity(), 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        collectionAdapter = new CommonAdapter<MyCollectionBean.ObjBean>(getActivity(), R.layout.item_service_app, collectionBean.getObj()) {
            @Override
            protected void convert(ViewHolder holder, MyCollectionBean.ObjBean objBean, int position) {
              

                holder.setText(R.id.tv_app_name,objBean.getAppName());

                   /*appIntranet  1 需要内网*/
                if (objBean.getAppIntranet()==1){
                    holder.setVisible(R.id.iv_del,true);
                    Glide.with(getActivity())
                            .load(R.mipmap.nei_icon)
                            .error(R.color.white)
                            .into((ImageView) holder.getView(R.id.iv_del));
                }else {
                    holder.setVisible(R.id.iv_del,false);
                }

                if (null != objBean.getPortalAppIcon() && null != objBean.getPortalAppIcon().getTempletAppImage()){

                    Glide.with(getActivity())
                            .load(UrlRes.HOME3_URL + objBean.getPortalAppIcon().getTempletAppImage())
                            .error(getResources().getColor(R.color.app_bg))
                            .into((ImageView) holder.getView(R.id.iv_app_icon));
                }else {
                    Glide.with(getActivity())
                            .load(UrlRes.HOME3_URL + objBean.getAppImages())
                            .error(getResources().getColor(R.color.app_bg))
                            .into((ImageView) holder.getView(R.id.iv_app_icon));
                }
            }
        };
        myCollectionList.setAdapter(collectionAdapter);
        collectionAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                long nowTime = System.currentTimeMillis();
                if (nowTime - mLastClickTime > TIME_INTERVAL) {
                    mLastClickTime = nowTime;
                    MyCollectionBean.ObjBean appsBean = collectionBean.getObj().get(position);
                    if (null != appsBean.getAppAndroidSchema()&& appsBean.getAppAndroidSchema().trim().length() != 0){
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
                                    }
                                    if (appUrl.contains("{quicklyTicket}")){
                                        String s3 =  URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"TGC",""), "UTF-8");
                                        appUrl = appUrl.replace("{quicklyTicket}",s3);
                                    }
                                    Log.e("TAG", appUrl+"");
                                    Uri uri = Uri.parse(appUrl);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);

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
                            if(appsBean.getAppLoginFlag()==0){
                                Intent intent = new Intent(getActivity(),LoginActivity2.class);
                                startActivity(intent);
                            }else {
                                Intent intent = null;
                                String isOpen = (String) SPUtils.get(MyApp.getInstance(), "isOpen", "");
                                if(isOpen.equals("") || isOpen.equals("1")){
                                    intent = new Intent(MyApp.getInstance(), BaseWebCloseActivity.class);
                                }else {
                                    intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                                }
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


                            ServiceAppListBean.ObjBean.AppsBean.PortalAppAuthentication portalAppAuthentication = appsBean.getPortalAppAuthentication();
                            if(portalAppAuthentication != null){
                                String appAuthenticationFace = appsBean.getPortalAppAuthentication().getAppAuthenticationFace();
                                if(appAuthenticationFace != null){
                                    if(!appAuthenticationFace.equals("0")){
                                        permissionsUtil=  PermissionsUtil
                                                .with(MyPre2Fragment.this)
                                                .requestCode(1)
                                                .isDebug(true)//开启log
                                                .permissions(PermissionsUtil.Permission.Camera.CAMERA,PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                                                .request();

                                        if(isOpen == 1){
                                            DargeFaceByMefColletUtils.cameraTask(appsBean,getActivity());
                                        }
                                    }else {
                                        DargeFaceByMefColletUtils.cameraTask(appsBean,getActivity());
                                    }

                                }else {
                                    DargeFaceByMefColletUtils.cameraTask(appsBean,getActivity());
                                }
                            }else {
                                DargeFaceByMefColletUtils.cameraTask(appsBean,getActivity());
                            }


                        }


                    }

                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        collectionAdapter.notifyDataSetChanged();
    }

    private boolean hasApplication(String scheme) {
        PackageManager manager = getActivity().getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse(scheme));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

    boolean haveMyData =false;
    /**我的应用信息列表*/

    ServiceAppListBean allAppListBean;
    private void netWorkMyData() {
        //ViewUtils.createLoadingDialog(getActivity());
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Service_APP_List)
                .params("Version", "1.0")
                .params("userId", (String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("rolecodes", (String) SPUtils.get(MyApp.getInstance(),"rolecodes",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //ViewUtils.cancelLoadingDialog();
                        //Log.e("OA消息列表",response.body());
                        allAppListBean = JSON.parseObject(response.body(),ServiceAppListBean.class);
                        if (allAppListBean.isSuccess()){

                            for (int i = 0; i < allAppListBean.getObj().size() ; i++) {
                                if (allAppListBean.getObj().get(i).getModulesName().equals("我的信息")){
                                    setMyAppDataList(allAppListBean.getObj().get(i).getApps());
                                    haveMyData = true;
                                }
                            }


                            if (haveMyData){
                                llMyData.setVisibility(View.VISIBLE);

                            }else {
                                llMyData.setVisibility(View.GONE);
                            }

                        }else {
                            llMyData.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //ViewUtils.cancelLoadingDialog();
                        llMyData.setVisibility(View.GONE);
                    }
                });
    }
    /**我的应用信息列表
     * @param appsBeans*/

    private void setMyAppDataList(final List<ServiceAppListBean.ObjBean.AppsBean> appsBeans) {
        final CommonAdapter<ServiceAppListBean.ObjBean.AppsBean> myAppListAdapter;
        myDataList.setLayoutManager(new GridLayoutManager(getActivity(),4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        myAppListAdapter = new CommonAdapter<ServiceAppListBean.ObjBean.AppsBean>(getActivity(),R.layout.item_service_app,appsBeans) {


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

                holder.setOnClickListener(R.id.ll_click, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        long nowTime = System.currentTimeMillis();
                        if (nowTime - mLastClickTime > TIME_INTERVAL) {
                            mLastClickTime = nowTime;
                            if (null != appsBean.getAppAndroidSchema()&& appsBean.getAppAndroidSchema().trim().length() != 0){
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
                                            }
                                            if (appUrl.contains("{quicklyTicket}")){
                                                String s3 =  URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"TGC",""), "UTF-8");
                                                appUrl = appUrl.replace("{quicklyTicket}",s3);
                                            }
                                            Log.e("TAG", appUrl+"");
                                            Uri uri = Uri.parse(appUrl);
                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                            intent.addCategory(Intent.CATEGORY_DEFAULT);

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
                                    if(appsBean.getAppLoginFlag()==0){
                                        Intent intent = new Intent(getActivity(),LoginActivity2.class);
                                        startActivity(intent);
                                    }else {
                                        Intent intent = null;
                                        String isOpen = (String) SPUtils.get(MyApp.getInstance(), "isOpen", "");
                                        if(isOpen.equals("") || isOpen.equals("1")){
                                            intent = new Intent(MyApp.getInstance(), BaseWebCloseActivity.class);
                                        }else {
                                            intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                                        }
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

                                    ServiceAppListBean.ObjBean.AppsBean.PortalAppAuthentication portalAppAuthentication = appsBean.getPortalAppAuthentication();
                                    if(portalAppAuthentication != null){
                                        String appAuthenticationFace = appsBean.getPortalAppAuthentication().getAppAuthenticationFace();
                                        if(appAuthenticationFace != null){
                                            if(!appAuthenticationFace.equals("0")){
                                                permissionsUtil=  PermissionsUtil
                                                        .with(MyPre2Fragment.this)
                                                        .requestCode(1)
                                                        .isDebug(true)//开启log
                                                        .permissions(PermissionsUtil.Permission.Camera.CAMERA,PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                                                        .request();

                                                if(isOpen == 1){
                                                    DargeFaceByMefgUtils.cameraTask(appsBean,getActivity());
                                                }
                                            }else {
                                                DargeFaceByMefgUtils.cameraTask(appsBean,getActivity());
                                            }

                                        }else {
                                            DargeFaceByMefgUtils.cameraTask(appsBean,getActivity());
                                        }
                                    }else {
                                        DargeFaceByMefgUtils.cameraTask(appsBean,getActivity());
                                    }


                                }


                            }
                        }


                    }
                });
            }
        } ;
        myDataList.setAdapter(myAppListAdapter);
        myAppListAdapter.notifyDataSetChanged();
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
                        //Log.e("result1",response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //Log.e("错误",response.body());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
//        count = (String) SPUtils.get(getActivity(), "count", "");
////        tvMyToDoMsgNum.setText(count);
//        badge1.setText(count); // 需要显示的提醒类容
        if (NetStateUtils.isConnect(getActivity())){
            //ToastUtils.showToast(getActivity(),"onResume");
            initLoadPage();
        }else {
            ToastUtils.showToast(getActivity(),"网络连接异常");
        }

    }

    CountBean countBean1;
    /** 获取消息数量*/

    private void netWorkSystemMsg() {

        String userId = (String) SPUtils.get(MyApp.getInstance(), "userId", "");
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_countUnreadMessagesForCurrentUser)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("系统消息数量",response.body());
                        countBean1 = JSON.parseObject(response.body(), CountBean.class);
//                        //yy_msg_num.setText(countBean.getCount()+"");
//                        netWorkOAToDoMsg1();//OA待办

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //Log.e("s",response.toString());
                    }
                });

    }
    CountBean countBean3;
    String count;


//    private void netWorkDyMsg() {
//
//        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
//                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
//                .params("type", "dy")
//                .params("workType", "workdb")
//                .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(Response<String> response) {
//                            //Log.e("s",response.body());
//
//                            countBean3 = JSON.parseObject(response.body(), CountBean.class);
//
//
//                            count = countBean2.getCount() + countBean1.getCount() + countBean3.getCount() + "";
//                            if(null == count){
//                                count = "0";
//                            }
//
//                            SPUtils.put(MyApp.getInstance(),"count",count+"");
//                            if(!count.equals("") && !"0".equals(count)){
//                                remind();
//                                SPUtils.get(getActivity(),"count","");
//                            }else {
//                                badge1.hide();
//                            }
//                            tvMyToDoMsgNum.setText(count);
//
//                        }
//
//
//                        @Override
//                        public void onError(Response<String> response) {
//                            super.onError(response);
//                        }
//                    });
//
//    }

//    private void netWorkOAToDoMsg1() {
//        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
//                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
//                .params("type", "db")
//                .params("workType", "workdb")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        //Log.e("s",response.toString());
//                        countBean2 = JSON.parseObject(response.body(), CountBean.class);
//
//                        netWorkDyMsg();
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//
//                    }
//                });
//    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""));

        String count = (String) SPUtils.get(getActivity(),"count","");
        //Log.e("count-------",count);
//        badge1.setText(count);
        badge1 = new BadgeView(getActivity(), rlMsgApp);
        //ToastUtils.showToast(getActivity(),"onHiddenChanged");
        if (!isLogin){
            badge1.hide();
        }else {
            netWorkSystemMsg();
            netInsertPortal("4");
        }

    }

    private void netInsertPortal(final String insertPortalAccessLog) {
        String imei = MobileInfoUtils.getIMEI(getActivity());
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Four_Modules)
                .params("portalAccessLogMemberId",(String) SPUtils.get(getInstance(),"userId",""))
                .params("portalAccessLogEquipmentId",(String) SPUtils.get(getInstance(),"imei",""))//设备ID
                .params("portalAccessLogTarget", insertPortalAccessLog)//访问目标
                .params("portalAccessLogVersionNumber", (String) SPUtils.get(getActivity(),"versionName", ""))//版本号
                .params("portalAccessLogOperatingSystem", "ANDROID")//版本号
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("sdsaas",response.body());

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

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
}
