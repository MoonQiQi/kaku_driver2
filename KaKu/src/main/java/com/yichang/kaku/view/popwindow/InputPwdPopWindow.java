package com.yichang.kaku.view.popwindow;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.payhelper.wxpay.net.sourceforge.simcpux.MD5;
import com.yichang.kaku.request.CheckWithDrawCodeReq;
import com.yichang.kaku.response.BaseResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.SecurityPasswordEditText;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by xiaosu on 2015/12/3.
 */
public class InputPwdPopWindow extends PopupWindow {

    private BaseActivity context;

    private String strPwd;
    private final SecurityPasswordEditText sEdit;


    public InputPwdPopWindow(final BaseActivity context) {
        super(context);
        this.context = context;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#77000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        final View view = context.inflate(R.layout.layout_input_pwd_confirm);
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

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

        TextView tv_forget_code = (TextView) view.findViewById(R.id.tv_forget_code);
        tv_forget_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetWithDrawCodeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isPassExist", true);
                intent.putExtra("flag_next_activity", "CONFIRM_ORDER");
                context.startActivity(intent);
            }
        });

    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void checkCode(final String code) {
        Utils.NoNet(context);
        mListener.showDialog();

        CheckWithDrawCodeReq req = new CheckWithDrawCodeReq();
        req.code = "5008";
        req.id_driver = Utils.getIdDriver();
        req.pay_pass = code;
        req.sign = genAppSign(code);

        KaKuApiProvider.checkWithDrawCode(req, new KakuResponseListener<BaseResp>(context, BaseResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("checkWithDrawCode res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                        mListener.confirmPwd(true);
                        mListener.stopDialog();

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

    private String genAppSign(String code) {
        StringBuilder sb = new StringBuilder();
        //拼接签名字符串
        sb.append("id_driver=");
        sb.append(Utils.getIdDriver());

        sb.append("&pay_pass=");
        sb.append(code);

        sb.append("&key=");
        sb.append(Constants.MSGKEY);
        LogUtil.E("sb:" + sb);

        String appSign1 = MD5.getMessageDigest(sb.toString().getBytes());
        String appSign = MD5.getMessageDigest(appSign1.getBytes()).toUpperCase();
        return appSign;
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
