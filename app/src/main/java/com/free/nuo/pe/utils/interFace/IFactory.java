package com.free.nuo.pe.utils.interFace;


/**
 * 大工厂接口
 * 目前可生产
 * 1.邮箱工具
 * <p>
 * @author yanxiaonuo
 * @date   2019/6/17 10:05
 * @email yanxiaonuo@foxmail.com
 */
public interface IFactory {


    /**
     * 创建 EmailUtils 邮箱工具
     *
     * @return IUtils 工具接口
     */
    IUtils createEmailUtils();

}
