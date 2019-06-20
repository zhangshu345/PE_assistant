package com.free.nuo.pe.utils;

import com.free.nuo.pe.utils.interFace.IFactory;
import com.free.nuo.pe.utils.interFace.IUtils;

import java.util.HashMap;

/**
 * 大工厂
 * <p>
 * 考虑到经常调用，节省资源，采用享元模式进行工具类的生产
 *
 * <p>
 * 目前可生产
 * 1.邮箱工具
 * <p>
 * @author yanxiaonuo
 * @email yanxiaonuo@foxmail.com
 */

public class Factory implements IFactory {


    /**
     * hash表存储工具类
     */
    private HashMap<String, IUtils> hashMapUtils = new HashMap<>();


    /**
     * 邮箱工具标志
     */
    private String EmailUtils = "EmailUtils";


    /**
     * 创建 EmailUtils 邮箱工具
     *
     * @return IUtils 工具接口
     */
    @Override
    public IUtils createEmailUtils() {
        IUtils utils = hashMapUtils.get(EmailUtils);

        if (utils == null) {
            EmailUtils emailUtils = new EmailUtils();
            hashMapUtils.put(EmailUtils, emailUtils);
            return emailUtils;
        } else {
            return utils;
        }
    }
}
