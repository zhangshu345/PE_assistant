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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtPassword;
    private EditText mEtPassword1;
    private EditText mEtEmail;


    /**
     * 账户输入框
     */
    private EditText mEtMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //ActionBar隐藏，透明化任务栏
        StatusBarUtil.setTransparent(this);

        //初始化用户名输入框
        initUserNameEditText();

        //初始化密码输入框
        initPasswordEditText();

        //初始化图片点击（清除用户名，密码，显示隐藏密码）
        initImageButton();

        //初始化登录按钮
        initRegistButton();

        //初始化邮箱输入框
        initUserEmailEditText();
    }

    /**
     * 初始化注册界面
     */
    private void initRegistButton() {
        //获取
        final ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        final Button Regist = (Button) findViewById(R.id.btn_regist);

        //设置点击（mProgressBar和button配合）
        Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtMobile.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "用户名为空", Toast.LENGTH_LONG);
                    //参数1：当前的上下文环境。可用getApplicationContext()或this
                    //参数2：要显示的字符串。
                    //参数3：显示的时间长短。Toast默认的有两个LENGTH_LONG(长)和LENGTH_SHORT(短)
                    toast.setGravity(Gravity.CENTER, 0, 1080);//设置提示框显示的位置

                    toast.show();//显示消息
                } else if (TextUtils.isEmpty(mEtEmail.getText()) || !isEmail(mEtEmail.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "请输入正确的邮箱", Toast.LENGTH_LONG);
                    //参数1：当前的上下文环境。可用getApplicationContext()或this
                    //参数2：要显示的字符串。
                    //参数3：显示的时间长短。Toast默认的有两个LENGTH_LONG(长)和LENGTH_SHORT(短)
                    toast.setGravity(Gravity.CENTER, 0, 1080);//设置提示框显示的位置

                    toast.show();//显示消息
                } else if (TextUtils.isEmpty(mEtPassword.getText()) || TextUtils.isEmpty(mEtPassword1.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "请输入正确的密码", Toast.LENGTH_LONG);
                    //参数1：当前的上下文环境。可用getApplicationContext()或this
                    //参数2：要显示的字符串。
                    //参数3：显示的时间长短。Toast默认的有两个LENGTH_LONG(长)和LENGTH_SHORT(短)
                    toast.setGravity(Gravity.CENTER, 0, 1080);//设置提示框显示的位置

                    toast.show();//显示消息
                } else if (!mEtPassword.getText().toString().trim().equals(mEtPassword1.getText().toString().trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "两次输入的密码不一致", Toast.LENGTH_LONG);
                    //参数1：当前的上下文环境。可用getApplicationContext()或this
                    //参数2：要显示的字符串。
                    //参数3：显示的时间长短。Toast默认的有两个LENGTH_LONG(长)和LENGTH_SHORT(短)
                    toast.setGravity(Gravity.CENTER, 0, 1080);//设置提示框显示的位置

                    toast.show();//显示消息
                } else {
//                    user.setUsername(mEtMobile.getText().toString());// 设置用户名
//                    user.setPassword(mEtPassword.getText().toString());// 设置密码
//                    user.setEmail(mEtEmail.getText().toString());//设置邮箱
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
                    Socket sk = new Socket("172.22.9.157", 38380);
                    PrintStream ps = new PrintStream(sk.getOutputStream());// 将客户端套接字的输出流用printStream包装起来，类似于C语言中的fprintf类型转换
                    ps.println("zhuce#" + mEtMobile.getText() + "#" + mEtPassword.getText() + "#" + mEtEmail.getText());// 把控制台输入的内容送入被printstream类包装的输出流里面

                    BufferedReader bfr = new BufferedReader(new InputStreamReader(sk.getInputStream()));

                    final String readLine = bfr.readLine();
                    ps.close();// 关闭输出流包装
                    sk.close();// 关闭socket套接字，已经传完数据，才能关闭

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (readLine.equals("TRUE")) {
                                // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                                Toast.makeText(SignUpActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, WelcomeActivity.class);
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

        findViewById(R.id.cleanr_password).setOnClickListener(this);
        findViewById(R.id.ri_show_pwd).setOnClickListener(this);
        findViewById(R.id.clean_email).setOnClickListener(this);
    }

    /**
     * 初始化密码输入框
     */
    private void initPasswordEditText() {
        mEtPassword = findViewById(R.id.et_password);
        mEtPassword1 = findViewById(R.id.right_password);
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
                    Toast.makeText(SignUpActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    //取消当前的输入
                    mEtPassword.setSelection(s.length());
                }
            }
        });


        mEtPassword1.addTextChangedListener(new TextWatcher() {
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
                ImageView mCleanPassword = findViewById(R.id.cleanr_password);
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
                    Toast.makeText(SignUpActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    //取消当前的输入
                    mEtPassword1.setSelection(s.length());
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

    //初始化邮箱输入框
    private void initUserEmailEditText() {
        mEtEmail = findViewById(R.id.user_email);
        mEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ImageView mIvCleanEmail = findViewById(R.id.clean_email);
                if (!TextUtils.isEmpty(s) && mIvCleanEmail.getVisibility() == View.GONE) {
                    mIvCleanEmail.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mIvCleanEmail.setVisibility(View.GONE);
                }
            }
        });


    }

    //判断邮箱是否合乎格式
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
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
            case R.id.cleanr_password:
                mEtPassword1.setText("");
                break;
            case R.id.clean_email:
                mEtEmail.setText("");
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

            case R.id.ri_show_pwd:
                if (mEtPassword1.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mEtPassword1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ImageView mIvShowPwd = findViewById(R.id.ri_show_pwd);
                    mIvShowPwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    mEtPassword1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ImageView mIvShowPwd = findViewById(R.id.ri_show_pwd);
                    mIvShowPwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd1 = mEtPassword1.getText().toString();
                if (!TextUtils.isEmpty(pwd1))
                    mEtPassword1.setSelection(pwd1.length());
                break;
        }
    }
}
