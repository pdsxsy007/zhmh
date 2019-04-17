//package menhu.jh.myapplication.fragment.home;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.Unbinder;
//import menhu.jh.myapplication.R;
//import menhu.jh.myapplication.activity.MyDataActivity;
//import menhu.jh.myapplication.utils.BaseFragment;
//import menhu.jh.myapplication.utils.MyApp;
//
///**
// * Created by Administrator on 2018/11/19 0019.
// */
//
//public class MyPreFragment extends BaseFragment {
//
//
//    @BindView(R.id.iv_back)
//    ImageView ivBack;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.iv_user_head)
//    ImageView ivUserHead;
//    @BindView(R.id.tv_user_name)
//    TextView tvUserName;
//    @BindView(R.id.tv_company)
//    TextView tvCompany;
//    @BindView(R.id.tv_data_num)
//    TextView tvDataNum;
//    @BindView(R.id.tv_my_collection)
//    TextView tvMyCollection;
//    @BindView(R.id.tv_my_to_do_msg)
//    TextView tvMyToDoMsg;
//    @BindView(R.id.rv_to_do_msg_list)
//    RecyclerView rvToDoMsgList;
//
//    @Override
//    public int getLayoutResID() {
//        return R.layout.fragment_my_pre;
//    }
//
//    @Override
//    public void initView(View view) {
//        super.initView(view);
//        ivBack.setVisibility(View.GONE);
//        tvTitle.setText("个人中心");
//    }
//
//    @OnClick({R.id.rv_my_data, R.id.iv_user_head, R.id.rv_my_collection, R.id.rv_my_to_do_msg, R.id.exit_login})
//    public void onViewClicked(View view) {
//        Intent intent ;
//        switch (view.getId()) {
//            case R.id.iv_user_head:
//                break;
//            case R.id.rv_my_data:
//                intent = new Intent(MyApp.getInstance(), MyDataActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.rv_my_collection:
//                break;
//            case R.id.rv_my_to_do_msg:
//                break;
//            case R.id.exit_login:
//                break;
//        }
//    }
//}
