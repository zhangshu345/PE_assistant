package com.free.nuo.pe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 速度，步数输入页面
 * <p>
 * @author yanxiaonuo
 * @email yanxiaonuo@foxmail.com
 */
public class InputActivity extends MyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputactivity_layout);

    }

    /**
     * 按钮点击事件
     */
    public void go(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText stepNumber = findViewById(R.id.stepNumber);
        String trim = stepNumber.getText().toString().trim();

        if (trim.isEmpty()) {
            Toast.makeText(InputActivity.this, "请输入", Toast.LENGTH_SHORT).show();
            return;
        }

        //放入步数
        intent.putExtra(Contant.STEPNUMBER, trim);

        EditText speed = findViewById(R.id.edit_speed);
        String speedStr = speed.getText().toString().trim();
        if (speedStr.isEmpty()) {
            Toast.makeText(InputActivity.this, "请输入", Toast.LENGTH_SHORT).show();
            return;
        }

        //放入步速
        intent.putExtra(Contant.SPEED, speed.getText().toString());

        startActivity(intent);
    }

    public void onAllDestory(View view) {
        ActivityManager.getInstance().exit();
    }
}
