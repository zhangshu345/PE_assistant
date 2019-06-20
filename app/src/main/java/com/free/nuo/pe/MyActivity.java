package com.free.nuo.pe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

/**
 * 模板模式
 * 将 ActivityManager 活动管理器固定写入 MyActivity，本软件所有 Activity 继承 MyActivity。
 *
 * @author yanxiaonuo
 * @email yanxiaonuo@foxmail.com
 */
public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarUtil.setTransparent(this);

        ActivityManager.getInstance().addActivity(this);
    }


    @Override
    protected void onDestroy() {
        if (this!=null){
            ActivityManager.getInstance().delActivity(this);
            super.onDestroy();
        }
    }

}
