package com.free.nuo.pe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import am.widget.circleprogressbar.CircleProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTransparent(this);

        Intent intent = getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle = intent.getExtras();//.getExtras()得到intent所附带的额外数据
        String temp = bundle.getString("num");//getString()返回指定key的值
        int num = Integer.valueOf(temp);
        if(num>36018){
            num=36018;
        }
        if(num<0){
            num=0;
        }

        CircleProgressBar cpbDemo = findViewById(R.id.circleprogressbar_cpb_demo);
        cpbDemo.setProgress(num);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        double km = Double.valueOf(num + ".00");
        km = km * 0.7 / 1000;
        String KM_last = decimalFormat.format(km);

        double x = 0.0499;//一步多少千卡
        String KCal_last = decimalFormat.format(x * num);

        cpbDemo.setBottomText("  " + KM_last + "公里  \n" + KCal_last + "千卡");


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        TextView tv=findViewById(R.id.sss);
        tv.setText(time);

        temp = bundle.getString("speed");
        float speed=Float.valueOf(temp);
        String speedStr = decimalFormat.format(speed);
        TextView tv_speed=findViewById(R.id.speed);
        tv_speed.setText("平均速度："+speedStr+"km/h");


        //        cpbDemo.setStartAngle(-180);
//        cpbDemo.setSweepAngle(180);
//        cpbDemo.setGradientColors(0xffff4444);
//        cpbDemo.setBackgroundSize(0);
//        cpbDemo.setProgress(520);
//        cpbDemo.setProgressSize(64);
//        cpbDemo.setDialVisibility(View.GONE);
//        cpbDemo.setProgressMode(CircleProgressBar.ProgressMode.PROGRESS);
//        cpbDemo.setShowProgressValue(true);
//        cpbDemo.setTopText("步数");
//        cpbDemo.setBottomText(null);
    }

    public void back(View view) {
        finish();
    }
}
