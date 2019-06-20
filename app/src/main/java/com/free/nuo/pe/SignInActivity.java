package com.free.nuo.pe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.free.nuo.pe.ui.CircularAnim;
import com.jaeger.library.StatusBarUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class SignInActivity extends MyActivity {

    /**
     * 密码输入框
     */
    private EditText mEtPassword;


    /**
     * 账户输入框
     */
    private EditText mEtMobile;

    private ProgressBar mProgressBar;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarUtil.setTransparent(this);


        //初始化登录按钮
        initLoginButton();

    }


    /**
     * 初始化登录界面
     */
    private void initLoginButton() {
        //获取
        mEtMobile = findViewById(R.id.et_mobile);
        mEtPassword = findViewById(R.id.et_password);

        mProgressBar = findViewById(R.id.progressBar2);
        login = findViewById(R.id.btn_login);

        //设置点击（mProgressBar和button配合）
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtMobile.getText())) {
                    Toast.makeText(getApplicationContext(), "用户名为空", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(mEtPassword.getText())) {
                    Toast.makeText(getApplicationContext(), "请输入正确的密码", Toast.LENGTH_LONG).show();
                } else {
                    CircularAnim.hide(login)
                            .endRadius(mProgressBar.getHeight() / 2)
                            .go(new CircularAnim.OnAnimationEndListener() {
                                @Override
                                public void onAnimationEnd() {
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    signIn();
                                }
                            });
                }
            }
        });


        //注册
        findViewById(R.id.regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    private void signIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket sk = new Socket(Contant.ip, 38380);

                    PrintStream ps = new PrintStream(sk.getOutputStream());
                    ps.println("denglv#" + mEtMobile.getText() + "#" + mEtPassword.getText());


                    BufferedReader bfr = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                    final String readLine = bfr.readLine();
                    ps.close();// 关闭输出流包装
                    sk.close();// 关闭socket套接字，已经传完数据，才能关闭

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (readLine.equals("TRUE")) {
                                mProgressBar.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CircularAnim.fullActivity(SignInActivity.this, mProgressBar)
                                                .colorOrImageRes(R.color.theme)
                                                .go(new CircularAnim.OnAnimationEndListener() {
                                                    @Override
                                                    public void onAnimationEnd() {
                                                        Toast.makeText(SignInActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent();
                                                        intent.setClass(SignInActivity.this, InputActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }
                                }, 3000);

                            } else {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                login.setVisibility(View.VISIBLE);
                                Toast.makeText(SignInActivity.this, "账号或密码不正确！", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
