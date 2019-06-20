package com.free.nuo.pe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class SignUpActivity extends MyActivity {

    private EditText mEtPassword;
    private EditText mRightEtPassword;
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

        //初始化登录按钮
        initRegistButton();
    }

    /**
     * 初始化注册界面
     */
    private void initRegistButton() {
        //获取
        Button regist = findViewById(R.id.sign_up_btn_regist);
        mEtMobile = findViewById(R.id.sign_up_et_mobile);
        mEtEmail = findViewById(R.id.sign_up_user_email);
        mEtPassword = findViewById(R.id.sign_up_et_password);
        mRightEtPassword = findViewById(R.id.sign_up_right_password);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtMobile.getText())) {
                    Toast.makeText(getApplicationContext(), "用户名为空", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(mEtEmail.getText()) || !isEmail(mEtEmail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "请输入正确的邮箱", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(mEtPassword.getText()) || TextUtils.isEmpty(mRightEtPassword.getText())) {
                    Toast.makeText(getApplicationContext(), "请输入正确的密码", Toast.LENGTH_LONG).show();
                } else if (!mEtPassword.getText().toString().trim().equals(mRightEtPassword.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "两次输入的密码不一致", Toast.LENGTH_LONG).show();
                } else {
                    signUp();
                }
            }
        });


    }

    private void signUp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket sk = new Socket(Contant.ip, 38380);
                    System.out.println("aaaaa" + "2222");
                    PrintStream ps = new PrintStream(sk.getOutputStream());
                    ps.println("zhuce#" + mEtMobile.getText() + "#" + mEtPassword.getText() + "#" + mEtEmail.getText());

                    BufferedReader bfr = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                    final String readLine = bfr.readLine();
                    ps.close();
                    sk.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (readLine.equals("TRUE")) {
                                // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                                Toast.makeText(SignUpActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                intent.putExtra("user_name", mEtMobile.getText().toString().trim());
                                setResult(RESULT_OK, intent);
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
                    System.out.println(e.toString());
                }
            }
        }).start();

    }


    public boolean isEmail(String email) {
        EmailUtils emailUtils = (EmailUtils) new Factory().createEmailUtils();

        boolean rs = emailUtils.validateEmail(email);

        return rs;
    }

}
