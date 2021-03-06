package com.yichang.kaku.home.faxian;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.obj.DiscoveryItemObj;
import com.yichang.kaku.request.DiscoveryListReq;
import com.yichang.kaku.response.DiscoveryListResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryActivity extends BaseActivity implements OnClickListener {

    //    titleBar
    private TextView title, left, right;
    /*private Activity mActivity;*/
    private XListView xListView;

    private final static int STEP = 5;// 每次加载5条
    private int start = 0, pageindex = 0, pagesize = STEP, sort = 0;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;

    private List<DiscoveryItemObj> discoveryItemList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("每日资讯");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("收藏");
        right.setOnClickListener(this);
        xListView = (XListView) findViewById(R.id.lv_discovery);
        xListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        setPullState(false);
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            goToHome();
        } else if (R.id.tv_right == id) {
            startActivity(new Intent(this, DiscoveryFavorActivity.class));
            finish();
        }

    }

    public void getDiscoveryList(int pageIndex, int pageSize) {
        showProgressDialog();
        DiscoveryListReq req = new DiscoveryListReq();
        req.code = "70010";
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        KaKuApiProvider.getDiscoveryList(req, new KakuResponseListener<DiscoveryListResp>(this, DiscoveryListResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub

                if (t != null) {
                    LogUtil.E("yidiantong res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.newss);
                    } else {
                        Toast.makeText(context, t.msg, Toast.LENGTH_SHORT).show();
                    }
                    onLoadStop();
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void setData(List<DiscoveryItemObj> list) {

        if (list != null) {
            discoveryItemList.addAll(list);
        } else {
            return;
        }

        DiscoveryAdapter adapter = new DiscoveryAdapter(DiscoveryActivity.this, discoveryItemList);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setPullRefreshEnable(false);
        xListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(context)) {
                    stopProgressDialog();
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(context)) {
                    stopProgressDialog();
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopLoadMore();
                    return;
                }
                setPullState(true);
            }
        });
    }

    private void setPullState(boolean isUp) {
        if (isUp) {
            isShowProgress = true;
            start++;
            pageindex = start * STEP;
        } else {
            start = 0;
            pageindex = 0;
            if (discoveryItemList != null) {
                discoveryItemList.clear();
            }
        }
        getDiscoveryList(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
        startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToHome();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        discoveryItemList.clear();
    }
}
