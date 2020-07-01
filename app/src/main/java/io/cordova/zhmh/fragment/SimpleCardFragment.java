package io.cordova.zhmh.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.cordova.zhmh.R;
import io.cordova.zhmh.adapter.NewsAdapter;
import io.cordova.zhmh.bean.ItemNewsBean2;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.web.BaseWebActivity4;
import io.cordova.zhmh.web.BaseWebCloseActivity;


@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment {
    private String json;
    RecyclerView rvMsgList;
    private NewsAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
    public  List<ItemNewsBean2> listTen = new ArrayList<>();
    private int position = 0;
    private RelativeLayout rl_more;
    private List<String> listsUrl = new ArrayList<>();
    private List<String> listsName = new ArrayList<>();
    private String s;
    private String s1;

    public SimpleCardFragment() {

    }

    public SimpleCardFragment(String json, int position, String s, String s1) {
        //Log.e("jsonSimpleCardFragment",json);
        this.json = json;
        //Log.e("赋值后",json);

        this.position = position;
        this.s = s;
        this.s1 = s1;
    }

   /* public SimpleCardFragment getInstance(String json, int position, String s, String s1) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.json = json;
        sf.position = position;
        sf.s = s;
        sf.s1 = s1;


        return sf;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.e("jsononCreateView",json);

        //Log.e("jsononCreateView",this.json);

        View v = inflater.inflate(R.layout.fr_simple_card, null);
        //String jsonmy= (String) getArguments().get("jsonmy");
        rvMsgList = v.findViewById(R.id.rv_msg_list);
        rl_more = v.findViewById(R.id.rl_more);
        listTen.clear();
      /*  listsUrl.clear();
        listsName.clear();*/


        if(json != null){
            List<ItemNewsBean2> list = jsonStringConvertToList(json, ItemNewsBean2[].class);
            for (int i = 0; i < 10; i++) {
                listTen.add(list.get(i));
            }


            mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false);
            rvMsgList.setLayoutManager(mLinearLayoutManager);
            //adapter = new NewsAdapter(getActivity(),R.layout.list_item_news,listTen);
            adapter = new NewsAdapter(getActivity(),R.layout.list_item_new_news,listTen,s1);
            rvMsgList.setAdapter(adapter);
            //解决滑动冲突、滑动不流畅
            rvMsgList.setHasFixedSize(true);
            rvMsgList.setNestedScrollingEnabled(false);
            adapter.notifyDataSetChanged();
            rl_more.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {

                                               Intent intent = null;
                                               String isOpen = (String) SPUtils.get(MyApp.getInstance(), "isOpen", "");
                                               if(isOpen.equals("") || isOpen.equals("1")){
                                                   intent = new Intent(MyApp.getInstance(), BaseWebCloseActivity.class);
                                                   intent.putExtra("appUrl",s);
                                                   intent.putExtra("appName",s1);
                                               }else {
                                                   intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                                                   intent.putExtra("appUrl",s);
                                                   intent.putExtra("appName",s1);
                                               }


                                               startActivity(intent);

                                           }
                                       }
            );
        }
        return v;
    }

    /**
     * 把一个json的字符串转换成为一个包含POJO对象的List
     *
     * @param string
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonStringConvertToList(String string, Class<T[]> cls) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(string, cls);
        return Arrays.asList(array);
    }


}