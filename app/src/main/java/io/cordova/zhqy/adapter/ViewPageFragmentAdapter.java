//package io.cordova.zhqy.adapter;
//
//import android.os.Parcelable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//
//import java.util.List;
//
///**
// *
// */
//public class ViewPageFragmentAdapter extends FragmentStatePagerAdapter {
//    //tab集合
//    private List<String> tabNames;
//    //viewPager的集合
//    private List<Fragment> fragments;
//
//    public ViewPageFragmentAdapter(FragmentManager fm, List<String> tabNames, List<Fragment> fragments) {
//        super(fm);
//        this.tabNames = tabNames;
//        this.fragments = fragments;
//    }
//
//    public Fragment getItem(int position) {
//        return fragments.get(position);
//    }
//
//    @Override
//    public int getCount() {
//        return fragments.size();
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return tabNames.get(position);
//    }
//
//    @Override
//    public Parcelable saveState() {
//        return null;
//    }
//}