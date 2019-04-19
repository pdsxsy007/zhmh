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

/**
 * Created by Administrator on 2018/11/29 0029.
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
    @BindView(R.id.mFlowLayout)
    TagFlowLayout flSearchCache;

    @Override
    protected int getResourceId() {
        return R.layout.app_search_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText("应用搜索");
        tvResult.setVisibility(View.GONE);
        tvSearchCache.setVisibility(View.GONE);
        setEditListener();

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
        ViewUtils.createLoadingDialog(this);
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.APP_List)
                .params("Version", "1.0")
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("appName", StringUtils.getEditTextData(tvSearch))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
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
                        T.showShort(MyApp.getInstance(),"请求错误" );
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
                        if (!listBean.getAppUrl().isEmpty()){
                            Log.e("url",listBean.getAppUrl() + "");
                            Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity.class);
                            intent.putExtra("appUrl",listBean.getAppUrl());
                            intent.putExtra("search","1");
                            startActivity(intent);
                        }
                    }
                });
            }
        };
        searchResult.setAdapter(searchResultListAdapter);
        searchResultListAdapter.notifyDataSetChanged();

    }



    @Override
    protected void onResume() {
        super.onResume();
        setSearchCache();
        flSearchCache.setVisibility(View.VISIBLE);
        tvSearchCache.setVisibility(View.VISIBLE);
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
//                Toast.makeText(getApplicationContext(), ViewUtils.getSearchHistory().get(position), Toast.LENGTH_SHORT).show();
                tvSearch.setText(ViewUtils.getSearchHistory().get(position));
                //view.setVisibility(View.GONE);
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
