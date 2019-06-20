package com.free.nuo.pe.utils.interFace;

/**
 * email工具箱接口
 * 暂有功能如下：
 * <p>
 * 1.提供邮箱验证
 * <p>
 * 作者：yanxiaonuo on 2019/6/19 21:59
 * 邮箱：yanxiaonuo@foxmail.com
 */

public interface IEmailUtils extends IUtils{

    /**
     * 邮箱合法性验证
     *
     * @param email :邮箱地址
     * @return true:合法 , false:不合法
     */
    public boolean validateEmail(String email);

}
