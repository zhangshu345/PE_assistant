package com.free.nuo.pe.utils.interFace;


/**
 * 大工厂接口
 * 目前可生产
 * 1.邮箱工具
 * <p>
 * 作者：yanxiaonuo on 2019/6/19 22:20
 * 邮箱：yanxiaonuo@foxmail.com
 */
public interface IFactory {


    /**
     * 创建 EmailUtils 邮箱工具
     *
     * @return Utils 工具接口
     */
    IUtils createEmailUtils();

}
