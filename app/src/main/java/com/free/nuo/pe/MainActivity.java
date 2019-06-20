package com.free.nuo.pe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import am.widget.circleprogressbar.CircleProgressBar;

/**
 * 步数，速度展示界面
 * <p>
 * @author yanxiaonuo
 * @email yanxiaonuo@foxmail.com
 */
public class MainActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTransparent(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String temp = bundle.getString("num");

        int num = Integer.valueOf(temp);
        if (num > 36018) {
            num = 36018;
        }
        if (num < 0) {
            num = 0;
        }

        CircleProgressBar cpbDemo = findViewById(R.id.circleprogressbar_cpb_demo);
        cpbDemo.setProgress(num);
        //构造方法的字符格式这里如果小数不足2位,会以0补足.
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        double km = Double.valueOf(num + ".00");
        km = km * 0.7 / 1000;
        String KM_last = decimalFormat.format(km);

        //一步多少千卡
        double x = 0.0499;
        String KCal_last = decimalFormat.format(x * num);

        cpbDemo.setBottomText("  " + KM_last + "公里  \n" + KCal_last + "千卡");


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        TextView tv = findViewById(R.id.sss);
        tv.setText(time);

        temp = bundle.getString("speed");
        float speed = Float.valueOf(temp);
        String speedStr = decimalFormat.format(speed);
        TextView tv_speed = findViewById(R.id.speed);
        tv_speed.setText("平均速度：" + speedStr + "km/h");


    }

    public void back(View view) {
        finish();
    }

}
