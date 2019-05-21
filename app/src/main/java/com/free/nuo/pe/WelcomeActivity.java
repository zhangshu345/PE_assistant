package com.free.nuo.pe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 密码输入框
     */
    private EditText mEtPassword;


    /**
     * 账户输入框
     */
    private EditText mEtMobile;

    private static final int REQUEST_CODE_GO_TO_REGIST = 100;
    private ProgressBar mProgressBar;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarUtil.setTransparent(this);

        //初始化用户名输入框
        initUserNameEditText();

        //初始化密码输入框
        initPasswordEditText();

        //初始化图片点击（清除用户名，密码，显示隐藏密码）
        initImageButton();

        //初始化登录按钮
        initLoginButton();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_CODE_GO_TO_REGIST:

                //判断注册是否成功  如果注册成功
                if (resultCode == RESULT_OK) {
                    //则获取data中的账号和密码  动态设置到EditText中
                    String username = data.getStringExtra("user_name");
                    mEtMobile.setText(username);
                }
                break;
        }
    }

    /**
     * 初始化登录界面
     */
    private void initLoginButton() {
        //获取
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        login = (Button) findViewById(R.id.btn_login);

        //设置点击（mProgressBar和button配合）
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtMobile.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "用户名为空", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 1080);//设置提示框显示的位置

                    toast.show();//显示消息
                } else if (TextUtils.isEmpty(mEtPassword.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "请输入正确的密码", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 1080);//设置提示框显示的位置

                    toast.show();//显示消息
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

        findViewById(R.id.regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivityForResult(intent, REQUEST_CODE_GO_TO_REGIST);
            }
        });
    }

    private void signIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket sk = new Socket("172.22.9.157", 38380);
                    PrintStream ps = new PrintStream(sk.getOutputStream());// 将客户端套接字的输出流用printStream包装起来，类似于C语言中的fprintf类型转换

                    ps.println("denglv#" + mEtMobile.getText() + "#" + mEtPassword.getText());// 把控制台输入的内容送入被printstream类包装的输出流里面

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
                                        CircularAnim.fullActivity(WelcomeActivity.this, mProgressBar)
                                                .colorOrImageRes(R.color.theme)
                                                .go(new CircularAnim.OnAnimationEndListener() {
                                                    @Override
                                                    public void onAnimationEnd() {
                                                        Toast.makeText(WelcomeActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent();
                                                        intent.setClass(WelcomeActivity.this, HellowActivity.class);
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
                                Toast.makeText(WelcomeActivity.this, "账号或密码不正确！", Toast.LENGTH_LONG).show();
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
     * 初始化图片点击（清除用户名，密码，显示隐藏密码）
     */
    private void initImageButton() {
        findViewById(R.id.iv_clean_phone).setOnClickListener(this);
        findViewById(R.id.clean_password).setOnClickListener(this);
        findViewById(R.id.iv_show_pwd).setOnClickListener(this);
    }

    /**
     * 初始化密码输入框
     */
    private void initPasswordEditText() {
        mEtPassword = findViewById(R.id.et_password);
        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            /**
             * 输入完检测
             * @param s
             */
            @Override
            public void afterTextChanged(Editable s) {
                ImageView mCleanPassword = findViewById(R.id.clean_password);
                if (!TextUtils.isEmpty(s) && mCleanPassword.getVisibility() == View.GONE) {
                    mCleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mCleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty()) {
                    return;
                }
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(WelcomeActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    //取消当前的输入
                    mEtPassword.setSelection(s.length());
                }
            }
        });
    }

    /**
     * 初始化用户名输入框
     */
    private void initUserNameEditText() {
        mEtMobile = findViewById(R.id.et_mobile);
        mEtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ImageView mIvCleanPhone = findViewById(R.id.iv_clean_phone);
                if (!TextUtils.isEmpty(s) && mIvCleanPhone.getVisibility() == View.GONE) {
                    mIvCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mIvCleanPhone.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clean_phone:
                mEtMobile.setText("");
                break;
            case R.id.clean_password:
                mEtPassword.setText("");
                break;
            case R.id.iv_show_pwd:
                if (mEtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ImageView mIvShowPwd = findViewById(R.id.iv_show_pwd);
                    mIvShowPwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ImageView mIvShowPwd = findViewById(R.id.iv_show_pwd);
                    mIvShowPwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd = mEtPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    mEtPassword.setSelection(pwd.length());
                break;
        }
    }
}
