package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Clue;

/**
 * 线索管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
public interface IClueService extends IService<Clue> {


    /**
     * 新增线索管理
     *
     * @param clue 线索管理
     * @return 结果
     */
    int insertClue(Clue clue);


}
