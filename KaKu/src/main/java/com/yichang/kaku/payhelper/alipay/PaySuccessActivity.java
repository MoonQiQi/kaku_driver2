package com.yichang.kaku.payhelper.alipay;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.ad.CheTieOrderListActivity;
import com.yichang.kaku.home.baoyang.BaoYangOrderListActivity;
import com.yichang.kaku.home.baoyang.OilPingJiaActivity;
import com.yichang.kaku.member.truckorder.TruckOrderListActivity;

public class PaySuccessActivity extends BaseActivity {


    //titleBar
    private TextView left, right, title;
    TextView textView, tv_price;
    private Button btn_result_list, btn_result_home;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        textView = (TextView) this.findViewById(R.id.tv_payresult_info);
        tv_price = (TextView) this.findViewById(R.id.tv_price);

        tv_price.setText("￥" + KaKuApplication.realPayment);

        btn_result_list = (Button) findViewById(R.id.btn_result_list);
        btn_result_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (KaKuApplication.payType.equals("SERVICE")) {
                    gotoBaoYangOrderListActivity("");
                    KaKuApplication.payType = "";
                } else if (KaKuApplication.payType.equals("TRUCK")) {
                    gotoTruckOrderListActivity("");
                    KaKuApplication.payType = "";
                } else if (KaKuApplication.payType.equals("STICKER")) {
                    gotoCheTieOrderListActivity();
                    KaKuApplication.payType = "";
                }
            }
        });
        btn_result_home = (Button) findViewById(R.id.btn_result_home);
        if ("yellow".equals(KaKuApplication.success_type)) {
            btn_result_home.setText("评价订单");
        } else {
            btn_result_home.setText("返回首页");
        }
        btn_result_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("yellow".equals(KaKuApplication.success_type)) {
                    Intent intent = new Intent(context, OilPingJiaActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
                    startActivity(intent);
                    finish();
                }
            }
        });
        iniTitleBar();
    }

    private void iniTitleBar() {

        left = (TextView) findViewById(R.id.tv_left);
        left.setVisibility(View.GONE);
        left.setEnabled(false);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回时跳转到待安装界面或待发货界面
                if (KaKuApplication.payType.equals("SERVICE")) {
                    gotoBaoYangOrderListActivity("B");
                    KaKuApplication.payType = "";
                } else if (KaKuApplication.payType.equals("TRUCK")) {
                    gotoTruckOrderListActivity("B");
                    KaKuApplication.payType = "";
                } else if (KaKuApplication.payType.equals("STICKER")) {
                    gotoCheTieOrderListActivity();
                    KaKuApplication.payType = "";
                }
                finish();
            }
        });
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("支付成功");
    }

    private void gotoCheTieOrderListActivity() {
        Intent intent = new Intent(context, CheTieOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void gotoTruckOrderListActivity(String state) {
        Intent intent = new Intent(context, TruckOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        KaKuApplication.truck_order_state = state;
        //intent.putExtra("state", "");
        startActivity(intent);
        finish();
    }

    private void gotoBaoYangOrderListActivity(String state) {
        Intent intent = new Intent(context, BaoYangOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        KaKuApplication.color_order = state;
        KaKuApplication.state_order = state;
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}