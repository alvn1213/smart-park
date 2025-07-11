package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Park;
import cn.dtransfer.system.domain.Dept;

import java.util.List;

/**
 * 园区管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
public interface IParkService  extends IService<Park> {
    /**
     * 查询园区管理
     *
     * @param id 园区管理ID
     * @return 园区管理
     */
    Park selectParkById(Long id);

    /**
     * 查询园区管理列表
     *
     * @param park 园区管理
     * @return 园区管理集合
     */
    List<Park> selectParkList(Park park);

    /**
     * 新增园区管理
     *
     * @param park 园区管理
     * @return 结果
     */
    int insertPark(Park park);

    /**
     * 修改园区管理
     *
     * @param park 园区管理
     * @return 结果
     */
    int updatePark(Park park);

    /**
     * 批量删除园区管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteParkByIds(String ids);

    /**
     * 判断是否存在
     *
     * @param property 属性名称
     * @param value    属性值
     * @return 是否存在
     */
    boolean exists(String property, Object value);


    /**
     * 判断是否唯一
     *
     * @param id       ID
     * @param property 属性名称
     * @param value    属性值
     * @return 是否唯一
     */
    boolean unique(Long id, String property, Object value);

    /**
     * 查询园区管理
     *
     * @return 园区管理
     */
    Park selectCurrentParkByDeptId(Park park);

    /**
     * 查询所有园区管理列表
     *
     * @param park 园区管理
     * @return 园区管理集合
     */
    List<Park> selectAllParkList(Park park);


    /**
     * 通过部门初始化园区名称
     * @param dept
     * @return
     */
    int initPark(Dept dept);
}
