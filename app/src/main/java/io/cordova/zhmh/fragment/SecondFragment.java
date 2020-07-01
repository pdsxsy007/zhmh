package io.cordova.zhmh.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.activity.LoginActivity2;
import io.cordova.zhmh.activity.MyToDoMsgActivity;
import io.cordova.zhmh.bean.CountBean;
import io.cordova.zhmh.bean.ItemNewsBean;
import io.cordova.zhmh.bean.NewsBean;
import io.cordova.zhmh.utils.BadgeView;
import io.cordova.zhmh.utils.LighterHelper;
import io.cordova.zhmh.utils.MobileInfoUtils;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.NoScrollViewPager;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.StringUtils;
import me.samlss.lighter.Lighter;
import me.samlss.lighter.interfaces.OnLighterListener;
import me.samlss.lighter.parameter.Direction;
import me.samlss.lighter.parameter.LighterParameter;
import me.samlss.lighter.parameter.MarginOffset;
import me.samlss.lighter.shape.CircleShape;

/**
 * Created by Administrator on 2019/8/21 0021.
 */

public class SecondFragment extends BaseFragment {

    RelativeLayout rl_msg_app;

    private BadgeView badge1;
    boolean isLogin = false;
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    NoScrollViewPager mViewPager;

    int num1,num2,num3,num4,num5;
    String count;
    SlidingTabLayout mTabLayout_1;

    SmartRefreshLayout mSwipeRefreshLayout;
    int pos = 0;
    private int flag = 0;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initView(final View view) {
        isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""));

        mViewPager = view.findViewById(R.id.vp_2);
        mTabLayout_1 = view.findViewById(R.id.tl_1);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);



        rl_msg_app = view.findViewById(R.id.rl_msg_app1);

        isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"userId",""));
        rl_msg_app.setVisibility(View.VISIBLE);
        count = (String) SPUtils.get(getActivity(), "count", "");
        badge1 = new BadgeView(getActivity(), rl_msg_app);
        remind();
        if (!isLogin){
            badge1.hide();
        }else {
            if(count != null){
                if(count.equals("0")){

                    badge1.hide();
                }else {
                    badge1.show();
                }
            }else {
                badge1.hide();
            }



        }


        rl_msg_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""));
                if (isLogin){
                    Intent intent = new Intent(MyApp.getInstance(), MyToDoMsgActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MyApp.getInstance(), LoginActivity2.class);
                    startActivity(intent);
                }
            }
        });

        String home02 = (String) SPUtils.get(MyApp.getInstance(), "home02", "");
        if(home02.equals("")){
            setGuideView();
        }

        getNewsData(view);
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                getNewsData(view);


               /* mTabLayout_1.setSelected(true);
                mTabLayout_1.setCurrentTab(pos);*/
                refreshlayout.finishRefresh();
            }
        });
    }

    private void setGuideView() {


        CircleShape circleShape = new CircleShape(10);
        circleShape.setPaint(LighterHelper.getDashPaint()); //set custom paint
        View tipView1 = getLayoutInflater().inflate(R.layout.fragment_home_gl, null);
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
                        SPUtils.put(MyApp.getInstance(),"home02","1");
                    }
                })
                .addHighlight(new LighterParameter.Builder()
                        //.setHighlightedViewId(R.id.rl_msg_app)
                        .setHighlightedView(rl_msg_app)
                        .setTipLayoutId(R.layout.fragment_home_gl3)
                        //.setLighterShape(new RectShape(80, 80, 50))
                        //.setLighterShape(circleShape)
                        .setTipViewRelativeDirection(Direction.BOTTOM)
                        .setTipViewRelativeOffset(new MarginOffset(150, 0, 30, 0))
                        .build()).show();
    }

    private void remind() { //BadgeView的具体使用



        String count = (String) SPUtils.get(getActivity(), "count", "");
        if(count.equals("")){
            count = "0";
        }

        //badge1.setText(count); // 需要显示的提醒类容
        if (Integer.parseInt(count) > 99) {

            badge1.setText("99+");
        }else{
            badge1.setText(count); // 需要显示的提醒类容

        }
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setTextColor(Color.WHITE); // 文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
        //badge1.setBackgroundResource(R.mipmap.icon_message_png); //设置背景图片
        badge1.setTextSize(10); // 文本大小
        badge1.setBadgeMargin(3, 3); // 水平和竖直方向的间距

        if(count.equals("0")){
            badge1.hide();
        }else {
            badge1.show();// 只有显示

        }

    }

//    OAMsgListBean3 oaMsgListBean = new OAMsgListBean3();
//    private void dbDataOAList() {
//        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Oaworkflow)
//                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
//                .params("type","1")//（1：待办    2：会议  3:公告）
//                .params("limit", 15)
//                .params("page","1")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        //Log.e("s",response.toString());
//                        oaMsgListBean = JSON.parseObject(response.body(), OAMsgListBean3.class);
//
//                        num1 = oaMsgListBean.getCount();
//                        dbDataOAEmail();
////                        netWorkDyMsg();
//
//                    }
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//
//
//                    }
//                });
//    }
//    WeidbBean weidbBean = new WeidbBean();
//    private void getWeiDbData(){
//        String userId = (String) SPUtils.get(MyApp.getInstance(), "userId", "");
//        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.workFolwDb)
//                .params("type","db")
//                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        //Log.e("微应用待办的数据",response.body());
//                        weidbBean = JSON.parseObject(response.body(),WeidbBean.class);
//
//                        num4 = weidbBean.getCount();
//                        netWorkSqMsg();
//                    }
//                });
//
//    }



    CountBean countBean = new CountBean();
    private void netWorkSqMsg() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type", "sq")
                .params("workType", "worksq")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("s申请",response.body());
                        countBean = JSON.parseObject(response.body(), CountBean.class);
                        num5 = countBean.getCount();

                        String obj = countBean1.getObj();//系统消息

                        if(obj == null){
                            obj = "0";
                        }
                        //String s = oaMsgListBean.getCount() + oaMsgListBean2.getCount() + Integer.parseInt(countBean1.getObj()) + weidbBean.getCount() + countBean.getCount()+ "";
                        String s =   + countBean.getCount() + Integer.parseInt(obj)+ "";

                        if(null == s){
                            s = "0";
                        }
                        SPUtils.put(MyApp.getInstance(),"count",s+"");

                        count = (String) SPUtils.get(getActivity(), "count", "");

                        if(!count.equals("") && !"0".equals(count)){
                            remind();

                        }else {


                            badge1.hide();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }
    CountBean countBean1;
    /** 获取消息数量*/

    private void netWorkSystemMsg() {

        try {
            String userId = (String) SPUtils.get(MyApp.getInstance(), "userId", "");
            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_countUnreadMessagesForCurrentUser)
                    .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            //Log.e("s___",response.body());

                            countBean1 = JSON.parseObject(response.body(), CountBean.class);

                            num3 = Integer.parseInt(countBean1.getObj());

                        }
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                        }
                    });
        }catch (Exception e){

        }

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();


        }else{  // 在最前端显示 相当于调用了onResume();
            getNewsData(getView());
            isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""));
            netInsertPortal("2");
            String count = (String) SPUtils.get(getActivity(),"count","");
            //Log.e("count-------",count);
            //badge1.setText(count);
            badge1 = new BadgeView(getActivity(), rl_msg_app);
            if (!isLogin){
                badge1.hide();
            }else {

//                dbDataOAList();

                //remind(rl_msg_app);
            }


        }
    }

    private void netInsertPortal(final String insertPortalAccessLog) {
        String imei = MobileInfoUtils.getIMEI(getActivity());
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Four_Modules)
                .params("portalAccessLogMemberId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("portalAccessLogEquipmentId",(String) SPUtils.get(MyApp.getInstance(),"imei",""))//设备ID
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




    CountBean countBean2;
    /**OA消息列表*/
    private void netWorkOAToDoMsg() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type", "db")
                .params("workType", "workdb")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("s",response.toString());

                        countBean2 = JSON.parseObject(response.body(), CountBean.class);

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //Log.e("sssssss",response.toString());
                    }
                });
    }




    List<String> newstitle;
    List<String> newstitleUrl;
    List<List<ItemNewsBean>> lists;
    List<String> mlists = new ArrayList<>();

    private void getNewsData(final View view) {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.findNewsUrl)
                .params("isReturnWithUrl","1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("news",response.body());
                        Gson gson = new Gson();
//                        NewsBean newsBean = JsonUtil.parseJson(response.body(),NewsBean.class);
//                        JsonObject jsonObject = new JsonObject();



                        NewsBean newsBean = gson.fromJson(response.body(), new TypeToken<NewsBean>(){}.getType());


                        Map<String, List<ItemNewsBean>> obj = (Map<String, List<ItemNewsBean>>) newsBean.getObj();

                        newstitle = new ArrayList<>();
                        newstitleUrl = new ArrayList<>();
                        int i = 0;
                        List<Map<String,List<ItemNewsBean>>> list = new ArrayList<>();

                        lists = new ArrayList<>();

                        mFragments.clear();
                        newstitle.clear();
                        mlists.clear();
                        lists.clear();
                        for (Map.Entry<String, List<ItemNewsBean>> entry : obj.entrySet()) {

                            String key = entry.getKey();
                            if(key.contains("[@gilight]")){//最版的
                                flag = 1;
                                String[] split = key.split("\\[@gilight\\]");
                                List<ItemNewsBean> value = entry.getValue();

                                String s = gson.toJson(value);
                                mlists.add(s);
                                newstitle.add(split[0]);
                                newstitleUrl.add(split[1]);
                                lists.add(value);
                            }else {//老版的
                                flag = 0;
                                List<ItemNewsBean> value = entry.getValue();

                                String s = gson.toJson(value);
                                mlists.add(s);
                                newstitle.add(key);
                                lists.add(value);
                            }

                        }

                        initTablayout(view,flag);
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);


                    }
                });
    }



    private void initTablayout(View view, int flag) {

        if(flag == 0){
            for (int i = 0; i < newstitle.size(); i++) {

                mFragments.add(SimpleCardFragment2.getInstance(mlists.get(i),i,newstitle.get(i)));
            }

        }else {
            for (int i = 0; i < newstitle.size(); i++) {

                SimpleCardFragment simpleCardFragment = new SimpleCardFragment(mlists.get(i), i, newstitleUrl.get(i), newstitle.get(i));
                mFragments.add(simpleCardFragment);
            }
//            for (int i = 0; i < newstitle.size(); i++) {
//
//                mFragments.add(SimpleCardFragment2.getInstance(mlists.get(i),i,newstitle.get(i)));
//            }

        }

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);

        mTabLayout_1.setViewPager(mViewPager);

        mTabLayout_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int i) {
                mViewPager.setCurrentItem(i);
                pos = i;
            }

            @Override
            public void onTabReselect(int i) {

            }
        });

        mTabLayout_1.setCurrentTab(pos);
    }


    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return newstitle.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }


        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

    }
}
