package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Dj;

import java.util.List;

/**
 * 党建管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
public interface IDjService extends IService<Dj> {

    /**
     * 查询党建管理列表
     *
     * @param dj 党建管理
     * @return 党建管理集合
     */
    List<Dj> selectDjList(Dj dj);


}
