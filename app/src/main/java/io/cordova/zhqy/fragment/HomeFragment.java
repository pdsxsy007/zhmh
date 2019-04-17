//package io.cordova.zhqy.fragment;
//
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//import butterknife.BindView;
//import io.cordova.zhqy.R;
//import io.cordova.zhqy.utils.BaseFragment2;
//import io.cordova.zhqy.utils.L;
//
//
///**
// * Created by Administrator on 2018/6/22.
// */
//
//public class HomeFragment extends BaseFragment2 {
//
//    @BindView(R.id.tv_no_data)
//    TextView tvNoData;
//    private String type ,name;
//    private String pageTitle;
//
//    public static HomeFragment newInstance(String type, String name) {
//        HomeFragment homeFragment = new HomeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("type", type);
//        bundle.putString("name", name);
//        homeFragment.setArguments(bundle);
//        return homeFragment;
//
//    }
//
////    public static HomeFragment newNames(String name) {
////        HomeFragment myOrderFragment = new HomeFragment();
////        Bundle bundle = new Bundle();
////        bundle.putString("name", name);
////        myOrderFragment.setArguments(bundle);
////        return myOrderFragment;
////
////    }
//
//    @Override
//    public int getLayoutResID() {
//        return R.layout.fragment_home;
//    }
//
//
//        @Override
//        public void initView(View view) {
//            super.initView(view);
//            Bundle args = getArguments();
//            if (args != null) {
//                type = args.getString("type");
//                name = args.getString("name");
//            }
//
//            L.e("type =  " , type);
//            L.e("name  = " , name);
//            tvNoData.setText(name);
//        }
//
//    @Override
//    public void initListener() {
//        super.initListener();
//        tvNoData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (type.equals("0")){
////                    Intent intent = new Intent(MyApp.getInstance(), ZxingScanningActivity.class);
////                    startActivity(intent);
////                } else if (type.equals("1")) {
////                    Intent intent = new Intent(MyApp.getInstance(), ZxingScanningActivity.class);
////                    startActivity(intent);
////                }
//            }
//        });
//    }
//}
