package com.yichang.kaku.view.uploadimages.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.ad.PingJiaAdActivity;
import com.yichang.kaku.home.shop.PingJiaOrderActivity;
import com.yichang.kaku.view.uploadimages.adapter.FolderAdapter;
import com.yichang.kaku.view.uploadimages.util.Bimp;
import com.yichang.kaku.view.uploadimages.util.PublicWay;
import com.yichang.kaku.view.uploadimages.util.Res;

/**
 * 这个类主要是用来进行显示包含图片的文件夹
 *
 * @author king
 * @version 2014年10月18日  下午11:48:06
 * @QQ:595163260
 */
public class ImageFile extends Activity {

    private FolderAdapter folderAdapter;
    private Button bt_cancel;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Res.getLayoutID("plugin_camera_image_file"));
        PublicWay.activityList.add(this);
        mContext = this;
        bt_cancel = (Button) findViewById(Res.getWidgetID("cancel"));
        bt_cancel.setOnClickListener(new CancelListener());
        GridView gridView = (GridView) findViewById(Res.getWidgetID("fileGridView"));
        TextView textView = (TextView) findViewById(Res.getWidgetID("headerTitle"));
        textView.setText(Res.getString("photo"));
        folderAdapter = new FolderAdapter(this);
        gridView.setAdapter(folderAdapter);
    }

    private class CancelListener implements OnClickListener {// 取消按钮的监听

        public void onClick(View v) {
            //清空选择的图片
            Bimp.tempSelectBitmap.clear();
            Bimp.max = 0;
            if ("Ad".equals(KaKuApplication.PingJiaShopOrAd)) {
                startActivity(new Intent(mContext, PingJiaAdActivity.class));
            } else {
                startActivity(new Intent(mContext, PingJiaOrderActivity.class));
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            if ("Ad".equals(KaKuApplication.PingJiaShopOrAd)) {
                startActivity(new Intent(mContext, PingJiaAdActivity.class));
            } else {
                startActivity(new Intent(mContext, PingJiaOrderActivity.class));
            }
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        folderAdapter = null;
        PublicWay.activityList.remove(this);
        super.onDestroy();
    }
}
