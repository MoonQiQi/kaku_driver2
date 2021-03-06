package com.yichang.kaku.tools;

import com.yichang.kaku.global.KaKuApplication;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

/* 定义一个倒计时的内部类 */
public class CaptchaDownTimerInText extends CountDownTimer {
    public TextView tView;
    public Button button;

    /**
     * @param millisInFuture    总时长
     * @param countDownInterval 事件间隔时长
     */
    public CaptchaDownTimerInText(long millisInFuture, long countDownInterval, TextView tv,Button btn) {
        super(millisInFuture, countDownInterval);
        tView = tv;
        button = btn;
    }

    public void setView(Button btn) {
        this.button = btn;
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        if (button == null)
            return;
        button.setText("重新接收短信");
        button.setEnabled(true);
        KaKuApplication.timer = null;
        tView.setText("可以重新请求");
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        if (tView == null)
            return;
        button.setEnabled(false);
        int second = (int) (millisUntilFinished / 1000);
        tView.setText("您可以在"+second+"秒后重新请求");
    }
}
