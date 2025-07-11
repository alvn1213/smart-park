package cn.dtransfer.system.service;


import cn.dtransfer.system.domain.Sn;

/**
 * 序列号Service接口
 *
 * @author ryuser
 * @date 2024-05-12
 */
public interface ISnService {

    /**
     * 生成序列号
     *
     * @param type
     *            类型
     * @return 序列号
     */
    String generate(Sn.Type type);
}
