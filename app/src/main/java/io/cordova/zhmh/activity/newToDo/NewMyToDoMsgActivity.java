package io.cordova.zhmh.activity.newToDo;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.bean.CountBean;
import io.cordova.zhmh.utils.BaseActivity2;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;

/**
 * Created by Administrator on 2018/11/22 0022.
 */

public class NewMyToDoMsgActivity extends BaseActivity2 {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_msg_num)
    TextView tv_msg_num;

    @BindView(R.id.db_msg_num)
    TextView db_msg_num;

    @BindView(R.id.dy_msg_num)
    TextView dy_msg_num;

    @BindView(R.id.dy_msg_present)
    TextView dy_msg_present;

    @BindView(R.id.yb_msg_num)
    TextView yb_msg_num;

    @BindView(R.id.yy_msg_num)
    TextView yy_msg_num;

    @BindView(R.id.db_msg_present)
    TextView db_msg_present;

    @BindView(R.id.yb_msg_present)
    TextView yb_msg_present;

    @BindView(R.id.yy_msg_present)
    TextView yy_msg_present;

    @BindView(R.id.my_msg_present)
    TextView my_msg_present;

    @BindView(R.id.system_msg_present)
    TextView system_msg_present;

    @Override
    protected int getResourceId() {
        return R.layout.activity_my_to_do_msg;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText("消息中心");

//        netWorkYyMsg();
    }




    @OnClick({R.id.ll_system_msg, R.id.ll_db_msg, R.id.ll_dy_msg, R.id.ll_yb_msg, R.id.ll_yy_msg, R.id.ll_my_msg})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_system_msg://系统消息
                intent = new Intent(MyApp.getInstance(), NewSystemMsgActivity.class);
                intent.putExtra("msgType","系统消息");
                intent.putExtra("type","0");
                startActivity(intent);
                break;
            case R.id.ll_db_msg://d待办消息
                intent = new Intent(MyApp.getInstance(), NewOaMsgActivity.class);
                intent.putExtra("type","1");
                intent.putExtra("msgType","待办消息");
                startActivity(intent);
                break;
            case R.id.ll_dy_msg://待阅消息
                intent = new Intent(MyApp.getInstance(), NewOaMsgActivity.class);
                intent.putExtra("type","2");
                intent.putExtra("msgType","待阅消息");
                startActivity(intent);
                break;
            case R.id.ll_yb_msg://已办消息
                intent = new Intent(MyApp.getInstance(), NewOaMsgActivity.class);
                intent.putExtra("type","3");
                intent.putExtra("msgType","已办消息");
                startActivity(intent);
                break;
            case R.id.ll_yy_msg://已阅消息
                intent = new Intent(MyApp.getInstance(), NewOaMsgActivity.class);
                intent.putExtra("type","4");
                intent.putExtra("msgType","已阅消息");
                startActivity(intent);
                break;
            case R.id.ll_my_msg://我的消息
                intent = new Intent(MyApp.getInstance(), NewMyShenqingActivity.class);
                intent.putExtra("type","5");
                intent.putExtra("msgType","我的申请");
                startActivity(intent);
                break;
        }
    }

   /** 获取消息数量*/
    private void netWorkSystemMsg() {
        String userId = (String) SPUtils.get(MyApp.getInstance(), "userId", "");
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.countUserMessagesByTypeUrl)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type","0")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("系统消息数量",response.body());
                        CountBean countBean = JSON.parseObject(response.body(), CountBean.class);

                        if(countBean.getCount() == 0){
                            system_msg_present.setText("您还有未读的系统消息");
                            tv_msg_num.setText(countBean.getCount()+"");
                        }else {
                            tv_msg_num.setText(countBean.getCount()+"");
                            system_msg_present.setText("您还有"+countBean.getCount()+"条未读系统消息");
                        }

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("s",response.toString());
                    }
                });
    }


    private void netWorkDbMsg() {
        netWorkOAToDoMsg();
    }

    private void netWorkDyMsg() {
        netWorkOAToDoMsg1();
    }



    private void netWorkYbMsg() {
        netWorkOAToDoMsg2();
    }



    private void netWorkYyMsg() {
        netWorkOAToDoMsg3();
    }

    private void netWorkSqMsg() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.countUserMessagesByTypeUrl)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type", "5")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());
                        CountBean countBean = JSON.parseObject(response.body(), CountBean.class);
                        //yy_msg_num.setText(countBean.getCount()+"");
                        my_msg_present.setText("已申请"+countBean.getCount()+"条");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }
    private void netWorkOAToDoMsg2() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.countUserMessagesByTypeUrl)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type", "3")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());
                        CountBean countBean = JSON.parseObject(response.body(), CountBean.class);
                        yb_msg_num.setText(countBean.getCount()+"");
                        yb_msg_present.setText("已办消息"+countBean.getCount()+"条");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void netWorkOAToDoMsg3() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.countUserMessagesByTypeUrl)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type", "4")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());
                        CountBean countBean = JSON.parseObject(response.body(), CountBean.class);
                        yy_msg_num.setText(countBean.getCount()+"");
                        yy_msg_present.setText("已阅消息"+countBean.getCount()+"条");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }


    private void netWorkOAToDoMsg1() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.countUserMessagesByTypeUrl)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type", "2")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());
                        CountBean countBean = JSON.parseObject(response.body(), CountBean.class);
                        dy_msg_num.setText(countBean.getCount()+"");
                        if(countBean.getCount() == 0){
                            dy_msg_present.setText("您还没有待阅消息");
                        }else {
                            dy_msg_present.setText("您还有待阅消息"+countBean.getCount()+"条");
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }
    private void netWorkOAToDoMsg() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.countUserMessagesByTypeUrl)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type", "1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());
                        CountBean countBean = JSON.parseObject(response.body(), CountBean.class);
                        db_msg_num.setText(countBean.getCount()+"");
                        if(countBean.getCount() == 0){
                            db_msg_present.setText("您还有未处理读待办消息");
                        }else {
                            db_msg_present.setText("您还有待办消息"+countBean.getCount()+"条");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        netWorkSystemMsg();
        netWorkDbMsg();
        netWorkDyMsg();
        netWorkYbMsg();
        netWorkYyMsg();
        netWorkSqMsg();
    }
}
