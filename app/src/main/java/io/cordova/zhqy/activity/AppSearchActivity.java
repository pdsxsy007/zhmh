package io.cordova.zhqy.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.AppListBean;
import io.cordova.zhqy.utils.BaseActivity2;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ViewUtils;
import io.cordova.zhqy.web.BaseWebActivity;
import io.cordova.zhqy.web.BaseWebActivity2;
import io.cordova.zhqy.web.BaseWebActivity3;
import io.cordova.zhqy.web.BaseWebActivity4;

/**
 * Created by Administrator on 2018/11/29 0029.
 * xiugai
 */

public class AppSearchActivity extends BaseActivity2 {
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
    @Override
    protected int getResourceId() {
        return R.layout.app_search_activity;
    }

    @Override
    protected void initView() {

        super.initView();
        isLogin = !StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""));
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
    }

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
                    Log.e("tvSearch --- ",StringUtils.getEditTextData(tvSearch));
                    Log.e("isEmpty --- ",StringUtils.getEditTextData(tvSearch).isEmpty() +"");
                    if (StringUtils.getEditTextData(tvSearch).isEmpty()){
                        T.showShort(MyApp.getInstance(),"请输入搜索关键字");
                    }else {
                        ViewUtils.saveSearchHistory(StringUtils.getEditTextData(tvSearch));
                        netWorkSearchApp();
                        flag = 1;
                        rl_history.setVisibility(View.GONE);
                        flSearchCache.setVisibility(View.GONE);
                    }

                }
                return false;

            }
        });

        tvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (StringUtils.getEditTextData(tvSearch).isEmpty()){
                        T.showShort(MyApp.getInstance(),"请输入搜索关键字");
                    }else {
                        ViewUtils.saveSearchHistory(StringUtils.getEditTextData(tvSearch));
                        netWorkSearchApp();
                        flag = 1;
                        rl_history.setVisibility(View.GONE);
                        flSearchCache.setVisibility(View.GONE);
                    }

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
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.APP_List)
                .params("Version", "1.0")
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("appName", StringUtils.getEditTextData(tvSearch))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("dsadadsad",response.body());
                        allAppListBean = JSON.parseObject(response.body(),AppListBean.class);
                        ViewUtils.cancelLoadingDialog();
                        if (allAppListBean.isSuccess()){
                            tvResult.setVisibility(View.VISIBLE);
                            searchResult.setVisibility(View.VISIBLE);
                            setRvAppList();
                            flSearchCache.setVisibility(View.GONE);
                            tvSearchCache.setVisibility(View.GONE);
                        }else {
                            tvResult.setVisibility(View.VISIBLE);
                            searchResult.setVisibility(View.GONE);
                            tvSearchCache.setVisibility(View.GONE);
                            flSearchCache.setVisibility(View.GONE);
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
                if (listBean.getAppIntranet()==1){
                    holder.setVisible(R.id.iv_del,true);
                    Glide.with(AppSearchActivity.this)
                            .load(R.mipmap.nei_icon)
                            .error(R.mipmap.nei_icon)
                            .into((ImageView) holder.getView(R.id.iv_del));
                }else {
                    holder.setVisible(R.id.iv_del,false);
                }

                if (!isLogin) {
                    if (listBean.getAppLoginFlag()==0){
                        holder.setVisible(R.id.iv_lock_close,true);
                        Glide.with(AppSearchActivity.this)
                                .load(R.mipmap.lock_icon)
                                .error(R.mipmap.lock_icon)
                                .into((ImageView) holder.getView(R.id.iv_lock_close));
                    }else {
                        holder.setVisible(R.id.iv_lock_close,false);
                    }
                }else {
                    holder.setVisible(R.id.iv_lock_close,false);
                }


//                if (StringUtils.isEmpty(listBean.getAppImages()+"")){
//                    Glide.with(getApplicationContext())
//                            .load(UrlRes.HOME3_URL + listBean.getPortalAppIcon().getTempletAppImage())
//                            .error(getResources().getColor(R.color.app_bg))
//                            .into((ImageView) holder.getView(R.id.iv_app_img));
//                }else {
//                    Glide.with(getApplicationContext())
//                            .load(UrlRes.HOME3_URL + listBean.getAppImages())
//                            .error(getResources().getColor(R.color.app_bg))
//                            .into((ImageView) holder.getView(R.id.iv_app_img));
//                }
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
                        Log.e("url",listBean.getAppUrl() + "");
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
                                if(listBean.getAppUrl().equals("http://iapp.zzuli.edu.cn/portal/app/mailbox/qqEmailLogin")){
                                    Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                                    intent.putExtra("appUrl",listBean.getAppUrl());
                                    intent.putExtra("appId",listBean.getAppId()+"");
                                    intent.putExtra("appName",listBean.getAppName()+"");
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
                                    intent.putExtra("appName",listBean.getAppName());
                                    intent.putExtra("search","1");
                                    intent.putExtra("appId",listBean.getAppId()+"");
                                    startActivity(intent);
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



    @Override
    protected void onResume() {
        super.onResume();
        String trim = tvSearch.getText().toString().trim();
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
            //tvSearchCache.setVisibility(View.GONE);
            rl_history.setVisibility(View.GONE);
            flSearchCache.setVisibility(View.GONE);
            netWorkSearchApp();
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
                tvSearch.setText(ViewUtils.getSearchHistory().get(position));
                netWorkSearchApp();
                flag = 1;
                rl_history.setVisibility(View.GONE);
                flSearchCache.setVisibility(View.GONE);
                return true;
            }
        });


//        flSearchCache.setOnSelectListener(new TagFlowLayout.OnSelectListener()
//        {
//            @Override
//            public void onSelected(Set<Integer> selectPosSet)
//            {
//
//            }
//        });

    }
}
