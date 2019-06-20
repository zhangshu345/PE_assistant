package com.free.nuo.pe;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.free.nuo.pe.utils.EmailUtils;
import com.free.nuo.pe.utils.Factory;
import com.jaeger.library.StatusBarUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 注册Activity
 *
 * @author yanxiaonuo
 * @email yanxiaonuo@foxmail.com
 */
public class SignUpActivity extends MyActivity {

    /**
     * 密码
     */
    private EditText mEtPassword;


    /**
     * 确认密码
     */
    private EditText mRightEtPassword;


    /**
     * 邮箱
     */
    private EditText mEtEmail;


    /**
     * 账户输入框
     */
    private EditText mEtMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        StatusBarUtil.setTransparent(this);

        //初始化注册按钮
        initRegistButton();
    }

    /**
     * 初始化注册界面
     */
    private void initRegistButton() {
        //获取控件
        Button regist = findViewById(R.id.sign_up_btn_regist);
        mEtMobile = findViewById(R.id.sign_up_et_mobile);
        mEtEmail = findViewById(R.id.sign_up_user_email);
        mEtPassword = findViewById(R.id.sign_up_et_password);
        mRightEtPassword = findViewById(R.id.sign_up_right_password);

        //事件监听
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //本地初步判断，减少服务器压力
                if (TextUtils.isEmpty(mEtMobile.getText())) {
                    Toast.makeText(getApplicationContext(), "用户名为空", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(mEtEmail.getText()) || !isEmail(mEtEmail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "请输入正确的邮箱", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(mEtPassword.getText()) || TextUtils.isEmpty(mRightEtPassword.getText())) {
                    Toast.makeText(getApplicationContext(), "请输入正确的密码", Toast.LENGTH_LONG).show();
                } else if (!mEtPassword.getText().toString().trim().equals(mRightEtPassword.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "两次输入的密码不一致", Toast.LENGTH_LONG).show();
                } else {
                    //注册
                    signUp();
                }
            }
        });


    }

    /**
     * 递交服务端注册验证
     */
    private void signUp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket sk = new Socket(Contant.IP, 38380);
                    PrintStream ps = new PrintStream(sk.getOutputStream());
                    ps.println("zhuce#" + mEtMobile.getText() + "#" + mEtPassword.getText() + "#" + mEtEmail.getText());

                    BufferedReader bfr = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                    final String readLine = bfr.readLine();
                    ps.close();
                    sk.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (readLine.equals(Contant.SUCCESS)) {
                                // 注册成功
                                Toast.makeText(SignUpActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                SignUpActivity.this.finish();
                            } else {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(SignUpActivity.this, "注册失败，请重试！", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /**
     * 判断邮箱是否正确
     * @param email 待验证邮箱
     * @return  邮箱格式是否合法
     */
    public boolean isEmail(String email) {
        EmailUtils emailUtils = (EmailUtils) new Factory().createEmailUtils();

        boolean rs = emailUtils.validateEmail(email);

        return rs;
    }

}
