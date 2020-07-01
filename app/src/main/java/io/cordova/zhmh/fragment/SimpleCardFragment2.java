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
import io.cordova.zhmh.web.BaseWebActivity4;


@SuppressLint("ValidFragment")
public class SimpleCardFragment2 extends Fragment {
    private String json;
    RecyclerView rvMsgList;
    private NewsAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
    List<ItemNewsBean2> listTen = new ArrayList<>();
    private int position = 0;
    private RelativeLayout rl_more;
    private List<String> listsUrl = new ArrayList<>();
    private List<String> listsName = new ArrayList<>();
    private String s;
    public static SimpleCardFragment2 getInstance(String json, int position, String s) {
        SimpleCardFragment2 sf = new SimpleCardFragment2();
        sf.json = json;
        sf.position = position;
        sf.s = s;
        sf.position = position;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        rvMsgList = v.findViewById(R.id.rv_msg_list);
        rl_more = v.findViewById(R.id.rl_more);
        listTen.clear();
        listsUrl.clear();
        listsName.clear();
        List<ItemNewsBean2> list = jsonStringConvertToList(json, ItemNewsBean2[].class);
        for (int i = 0; i < 10; i++) {
            listTen.add(list.get(i));
        }

        listsUrl.add("http://www.kfu.edu.cn/xww/tzgg.htm");
        listsUrl.add("http://www.kfu.edu.cn/xww/xxxw.htm");
        listsUrl.add("http://www.kfu.edu.cn/xww/ybxw.htm");
        listsUrl.add("http://www.kfu.edu.cn/xww/mtkd.htm");

        listsName.add(" 通知公告");
        listsName.add("学校新闻");
        listsName.add("院部新闻");
        listsName.add("媒体开大");
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false);
        rvMsgList.setLayoutManager(mLinearLayoutManager);
        adapter = new NewsAdapter(getActivity(),R.layout.list_item_new_news,listTen,s);

        rvMsgList.setAdapter(adapter);
        rvMsgList.setHasFixedSize(true);
        rvMsgList.setNestedScrollingEnabled(false);
        adapter.notifyDataSetChanged();
        rl_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), BaseWebActivity4.class);
                intent.putExtra("appUrl",listsUrl.get(position));
                //intent.putExtra("appName",listsName.get(position));
                startActivity(intent);

                }
            }
        );
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