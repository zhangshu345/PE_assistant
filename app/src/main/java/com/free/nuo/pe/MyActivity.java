package com.free.nuo.pe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

/**
 * 作者：yanxiaonuo on 2019/6/20 08:27
 * 邮箱：yanxiaonuo@foxmail.com
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
        ActivityManager.getInstance().delActivity(this);
        super.onDestroy();
    }

}
