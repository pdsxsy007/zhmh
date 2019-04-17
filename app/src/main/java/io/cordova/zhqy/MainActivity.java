//package menhu.jh.myapplication;
//
//
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.webkit.WebSettings;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import butterknife.BindView;
//import io.cordova.zhqy.R;
//import io.cordova.zhqy.adapter.ViewPageFragmentAdapter;
//import io.cordova.zhqy.fragment.HomeFragment;
//import io.cordova.zhqy.utils.BaseActivity;
//
//
//public class MainActivity extends BaseActivity {
//
//    @BindView(R.id.frameLayout)
//    ViewPager viewPager;
//    @BindView(R.id.tab_group)
//    TabLayout tabGroup;
//
//    private ArrayList<String> tabNames;
//    ViewPageFragmentAdapter vfAdapter;
//    private List<Fragment> fragments = new ArrayList<>();
//    @Override
//    protected int getResourceId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected void initData() {
//        super.initData();
//
//        List<String> list = new ArrayList<>();
//        list.add("12");
//        list.add("13");
//        list.add("14");
//        list.add("15");
//        list.add("16");
//
//        tabNames = new ArrayList<>();
//
//        for (int i = 0; i < list.size(); i++) {
//            tabNames.add("item" +i);
//           // tabGroup.addTab(tabGroup.newTab().setText("item" +i).setIcon(R.mipmap.ic_launcher));
//            fragments.add(HomeFragment.newInstance(i+"",
//                    "item" +i));
//        }
//        //创建adapter
//        vfAdapter = new ViewPageFragmentAdapter(getSupportFragmentManager(), tabNames, fragments);
//        //ViewPager绑定adapter
//        viewPager.setAdapter(vfAdapter);
//        viewPager.setCurrentItem(0);
//
//        //tabLayout绑定viewPager
//        tabGroup.setupWithViewPager(viewPager);
//        for (int i = 0; i < list.size(); i++) {
//            tabGroup.getTabAt(i).setIcon(R.mipmap.ic_launcher);
//        }
//    }
//
//}
