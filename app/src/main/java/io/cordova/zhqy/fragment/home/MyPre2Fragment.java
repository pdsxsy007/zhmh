package io.cordova.zhqy.fragment.home;

import android.content.Intent;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.coloros.mcssdk.mode.AppMessage;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhqy.Main2Activity;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.activity.AppSetting;
import io.cordova.zhqy.activity.MyCollectionActivity;
import io.cordova.zhqy.activity.MyDataActivity;
import io.cordova.zhqy.activity.MyToDoMsgActivity;
import io.cordova.zhqy.bean.AppListBean;
import io.cordova.zhqy.bean.MyCollectionBean;
import io.cordova.zhqy.bean.OAMsgListBean;
import io.cordova.zhqy.bean.ServiceAppListBean;
import io.cordova.zhqy.bean.UserMsgBean;
import io.cordova.zhqy.utils.BadgeHelper;
import io.cordova.zhqy.utils.BadgeView;
import io.cordova.zhqy.utils.BaseFragment;
import io.cordova.zhqy.utils.CircleCrop;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ViewUtils;
import io.cordova.zhqy.web.BaseWebActivity;

/**
 * Created by Administrator on 2018/11/22 0022.
 */

public class MyPre2Fragment extends BaseFragment {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_app_msg)
    ImageView ivAppMsg;
    @BindView(R.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
   @BindView(R.id.tv_zhuan_ye)
    TextView tvZhangye;
    @BindView(R.id.rv_user_data)
    RelativeLayout rvUserData;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tv_data_num)
    TextView tvDataNum;
    @BindView(R.id.tv_app_setting)
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
    private static Object object = new Object();
    int allMsgNum = 0;

    boolean isLogin = false;
    @Override
    public int getLayoutResID() {
        return R.layout.fragment2_my_pre;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        initLoadPage();
        //情况C(默认情况)
        remind(rlMsgApp);
    }
    private void remind(View view) { //BadgeView的具体使用
        BadgeView badge1 = new BadgeView(getActivity(), view);// 创建一个BadgeView对象，view为你需要显示提醒的控件
        badge1.setText("110"); // 需要显示的提醒类容
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
            netWorkOAToDoMsg();//OA待办
//            netWorkMyCollection();//我的收藏
            netWorkMyData();//我的信息
            tvDataNum.setText("11");
            //情况E(小红点与图片重叠)+ 数字模式

        }else {
            ((Main2Activity)  getActivity()).mainRadioGroup.check(R.id.rb_home_page);
            ((Main2Activity)  getActivity()).showFragment(0);
        }
    }

    /**点击事件*/
    @OnClick({R.id.rv_user_data, R.id.rv_my_collection, R.id.rv_my_to_do_msg, R.id.exit_login,R.id.tv_app_setting,R.id.tv_app_msg})
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
                    intent = new Intent(MyApp.getInstance(), MyToDoMsgActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_app_setting:
                if (isLogin){
                    intent = new Intent(MyApp.getInstance(), AppSetting.class);
                    startActivity(intent);
                }
                break;
            case R.id.exit_login:
                break;
        }
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
                            if(null != userMsgBean.getObj().getModules().getMemberOtherDepartment()) {
                                tvZhangye.setText(userMsgBean.getObj().getModules().getMemberOtherDepartment().toString());
                            }
                            tvUserName.setText(userMsgBean.getObj().getModules().getMemberNickname());
                            Glide.with(getActivity())
                                    .load(UrlRes.HOME3_URL + userMsgBean.getObj().getModules().getMemberImage())
                                    .transform(new CircleCrop(getActivity()))
                                    .error(R.mipmap.tabbar_user_pre)
                                    .into(ivUserHead);
                            StringBuilder sb = new StringBuilder();
                            if (userMsgBean.getObj().getModules().getRolecodes().size() > 0){
                                for (int i = 0; i < userMsgBean.getObj().getModules().getRolecodes().size(); i++) {
                                    sb.append(userMsgBean.getObj().getModules().getRolecodes().get(i).getRoleCode()).append(",");
                                }

                            }
                            String ss = sb.substring(0, sb.lastIndexOf(","));
                            Log.e("TAG",ss);
                            SPUtils.put(MyApp.getInstance(),"rolecodes",ss);
                        }
                    }
                });

    }

    /**OA消息列表*/
    OAMsgListBean oaMsgListBean;
    private void netWorkOAToDoMsg() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.OA_Msg_List)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("platformtype", "H5手机端")
                .params("sizes",5)
                .params("type", "db")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        oaMsgListBean = JSON.parseObject(response.body(), OAMsgListBean.class);
                        ViewUtils.cancelLoadingDialog();
                        if (oaMsgListBean.isSuccess()) {
                            Log.i("OA消息列表",response.body());
                            if (!oaMsgListBean.getObj().isEmpty()){
                                llOa.setVisibility(View.VISIBLE);
                                synchronized (object) {
                                        allMsgNum = allMsgNum + oaMsgListBean.getObj().size();
                                    Log.e("allMsgNum",allMsgNum+"");
                                }
                                tvMyToDoMsgNum.setText(oaMsgListBean.getObj().size()+"");
                                setRvOAMsgList();
                            }
                        }else {
                            llOa.setVisibility(View.GONE);
                            T.showShort(MyApp.getInstance(), "没有数据");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        llOa.setVisibility(View.GONE);
                        ViewUtils.cancelLoadingDialog();
                        T.showShort(MyApp.getInstance(), "没有数据");
                    }
                });
    }
    /**OA消息列表填充*/
    CommonAdapter<OAMsgListBean.ObjBean> oaAdapter;
    private void setRvOAMsgList() {
        llOa.setVisibility(View.VISIBLE);
        myOaToDoList.setLayoutManager(new LinearLayoutManager(getContext()));
        oaAdapter = new CommonAdapter<OAMsgListBean.ObjBean>(getContext(),R.layout.item_to_do_my_msg,oaMsgListBean.getObj()) {
            @Override
            protected void convert(ViewHolder holder, OAMsgListBean.ObjBean objBean, int position) {
                holder.setVisible(R.id.tv_msg_num,false);
                Glide.with(getActivity())
                        .load(R.mipmap.message_icon1)
                        .transform(new CircleCrop(getActivity()))
                        .into((ImageView) holder.getView(R.id.oa_img));
                holder.setText(R.id.tv_name,objBean.getTitle());
                holder.setText(R.id.tv_present,objBean.getYwlx());

            }
        };
        myOaToDoList.setAdapter(oaAdapter);
        oaAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (!oaMsgListBean.getObj().get(position).getTodourl().isEmpty()){
                    Log.e("url  ==",oaMsgListBean.getObj().get(position).getTodourl()+ "");
                    if (null != oaMsgListBean.getObj().get(position).getTodourl()){
                        Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity.class);
                        intent.putExtra("appUrl",oaMsgListBean.getObj().get(position).getTodourl());
                        intent.putExtra("oaMsg","oaMsg");
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
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.My_Collection)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        collectionBean = JSON.parseObject(response.body(), MyCollectionBean.class);
                        if (collectionBean.isSuccess()) {
                            if (collectionBean.getObj().size() > 0) {
                                llMyCollection.setVisibility(View.VISIBLE);
                                tvMyCollectionNum.setText(collectionBean.getObj().size() + "");
                                setCollectionList();

                            } else {
                                llMyCollection.setVisibility(View.GONE);
                            }
                        } else {
                            llMyCollection.setVisibility(View.GONE);
                            T.showShort(MyApp.getInstance(), collectionBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        llMyCollection.setVisibility(View.GONE);

                    }
                });
    }
    /**我的收藏列表填充*/
    CommonAdapter<MyCollectionBean.ObjBean> collectionAdapter;
    private void setCollectionList() {
        myCollectionList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
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

//                 /*appLoginFlag  0 需要登录*/
//                if (objBean.getAppLoginFlag()==0){
//                    holder.setVisible(R.id.iv_lock_open,true);
//                    Glide.with(getActivity())
//                            .load(R.mipmap.lock_icon)
//                            .error(R.color.white)
//                            .into((ImageView) holder.getView(R.id.iv_lock_open));
//                }else {
//                    holder.setVisible(R.id.iv_lock_open,false);
//                }
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
//                collectionBean.getObj().get(position)
                netWorkAppClick(collectionBean.getObj().get(position).getAppId());
                Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity.class);
                intent.putExtra("appUrl",collectionBean.getObj().get(position).getAppUrl());
                intent.putExtra("appId",collectionBean.getObj().get(position).getAppId()+"");
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        collectionAdapter.notifyDataSetChanged();
    }

    boolean haveMyData =false;
    /**我的应用信息列表*/

    ServiceAppListBean allAppListBean;
    private void netWorkMyData() {
        ViewUtils.createLoadingDialog(getActivity());
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Service_APP_List)
                .params("Version", "1.0")
                .params("userId", (String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("rolecodes", (String) SPUtils.get(MyApp.getInstance(),"rolecodes",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ViewUtils.cancelLoadingDialog();
                        Log.i("OA消息列表",response.body());
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
                            T.showShort(MyApp.getInstance(),allAppListBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ViewUtils.cancelLoadingDialog();
                        llMyData.setVisibility(View.GONE);
//                        T.showShort(MyApp.getInstance(),allAppListBean.getMsg());
                    }
                });
    }
    /**我的应用信息列表
     * @param appsBeans*/

    private void setMyAppDataList(final List<ServiceAppListBean.ObjBean.AppsBean> appsBeans) {
        final CommonAdapter<ServiceAppListBean.ObjBean.AppsBean> myAppListAdapter;
        myDataList.setLayoutManager(new GridLayoutManager(getActivity(),4));
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
                        if (!appsBean.getAppUrl().isEmpty()){
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
                        Log.e("result1",response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("错误",response.body());
                    }
                });
    }

    @Override
    public void onResume() {
        initLoadPage();
        super.onResume();

    }
}
