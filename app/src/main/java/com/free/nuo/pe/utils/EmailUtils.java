package com.free.nuo.pe.utils;

import com.free.nuo.pe.utils.interFace.IEmailUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * email工具箱接口 实现类
 * <p>
 * @author yanxiaonuo
 * @date   2019/6/19 22:06
 * @email yanxiaonuo@foxmail.com
 */
public class EmailUtils implements IEmailUtils {


    /**
     * 邮箱合法性验证
     *
     * @param email :邮箱地址
     * @return true:合法 , false:不合法
     */
    @Override
    public boolean validateEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }


}
