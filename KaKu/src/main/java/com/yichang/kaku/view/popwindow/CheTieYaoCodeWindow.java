package com.yichang.kaku.view.popwindow;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.ad.AdImageActivity;
import com.yichang.kaku.home.ad.StickerOrderActivity;
import com.yichang.kaku.home.ad.XingShiZhengImageActivity;
import com.yichang.kaku.request.CheckCodeReq;
import com.yichang.kaku.response.CheckCodeResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.SecurityPasswordEditText;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by xiaosu on 2015/12/3.
 */
public class CheTieYaoCodeWindow extends PopupWindow {

    private BaseActivity context;

    private String strPwd;
    private final SecurityPasswordEditText sEdit;
    private Button btn_input_yaocode;


    public CheTieYaoCodeWindow(final BaseActivity context) {
        super(context);
        this.context = context;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C6000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        final View view = context.inflate(R.layout.layout_chetieyaocode_confirm);
        setContentView(view);

        LinearLayout ll_pwd_container = (LinearLayout) view.findViewById(R.id.ll_pwd_container);

        sEdit = (SecurityPasswordEditText) view.findViewById(R.id.et_s_pwd);
        sEdit.setSecurityEditCompleListener(new SecurityPasswordEditText.SecurityEditCompleListener() {
            @Override
            public void onNumCompleted(String num) {
                strPwd = num;
                if (num.length() == 6) {

                    checkCode(num);
                }
            }
        });


        ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
        btn_input_yaocode = (Button) view.findViewById(R.id.btn_input_yaocode);
        btn_input_yaocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCode("");
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void checkCode(final String code) {
        Utils.NoNet(context);

        CheckCodeReq req = new CheckCodeReq();
        req.code = "60013";
        req.id_driver = Utils.getIdDriver();
        req.code_recommended = code;

        KaKuApiProvider.CheckCode(req, new KakuResponseListener<CheckCodeResp>(context, CheckCodeResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("checkcode res：" + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.flag_recommended = t.flag_recommended;
                        KaKuApplication.id_advert = t.id_advert;
                        KaKuApplication.phone_driver = t.phone_driver;
                        mListener.confirmPwd(true);
                        mListener.stopDialog();
                        if ("A".equals(KaKuApplication.flag_recommended)) {
                            KaKuApplication.flag_nochetietv = "you";
                            KaKuApplication.flag_code = "60020";
                            Intent intent = new Intent(context, AdImageActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        } else if ("B".equals(KaKuApplication.flag_recommended)) {
                            KaKuApplication.flag_nochetietv = "wu";
                            KaKuApplication.flag_code = "60020";
                            Intent intent = new Intent(context, AdImageActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        } else if ("C".equals(KaKuApplication.flag_recommended) || "D".equals(KaKuApplication.flag_recommended)) {
                            KaKuApplication.flag_nochetietv = "wu";
                            KaKuApplication.flag_code = "60020";
                            Intent intent = new Intent(context, XingShiZhengImageActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        } else if ("".equals(KaKuApplication.flag_recommended)) {
                            KaKuApplication.flag_nochetietv = "wu";
                            KaKuApplication.flag_code = "60018";
                            Intent intent = new Intent(context, AdImageActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        } else if ("E".equals(KaKuApplication.flag_recommended)) {
                            KaKuApplication.flag_nochetietv = "wu";
                            KaKuApplication.flag_code = "60018";
                            Intent intent = new Intent(context, StickerOrderActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                        dismiss();
                    } else {

                        LogUtil.showShortToast(context, t.msg);
                        sEdit.clearSecurityEdit();
                        mListener.stopDialog();
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });

    }

    public interface ConfirmListener {
        void confirmPwd(Boolean isConfirmed);

        void showDialog();

        void stopDialog();
    }

    private ConfirmListener mListener;

    public void setConfirmListener(ConfirmListener listener) {
        this.mListener = listener;
    }
}
