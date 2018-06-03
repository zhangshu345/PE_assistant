package com.free.nuo.pe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

public class HellowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hellowactivity_layout);
        StatusBarUtil.setTransparent(this);
    }

    public void go(View view) {
        Intent q = new Intent(this, MainActivity.class);
        EditText t = findViewById(R.id.edit);
        String trim = t.getText().toString().trim();
        if(trim.equals("")){
            Toast.makeText(HellowActivity.this,"请输入",Toast.LENGTH_SHORT).show();
            return ;
        }
        q.putExtra("num", t.getText().toString());

        EditText speed = findViewById(R.id.edit_speed);
        String speedStr = speed.getText().toString().trim();
        if(speedStr.equals("")){
            Toast.makeText(HellowActivity.this,"请输入",Toast.LENGTH_SHORT).show();
            return ;
        }
        q.putExtra("speed", speed.getText().toString());

        startActivity(q);
    }
}
