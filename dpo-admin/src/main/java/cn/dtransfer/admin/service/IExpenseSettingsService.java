package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ExpenseSettings;

import java.util.List;

/**
 * 费项配置Service接口
 *
 * @author dtransfer
 * @date 2024-03-24
 */
public interface IExpenseSettingsService extends IService<ExpenseSettings> {
    /**
     * 查询费项配置
     *
     * @param id 费项配置ID
     * @return 费项配置
     */
    ExpenseSettings selectExpenseSettingsById(Long id);

    /**
     * 查询费项配置列表
     *
     * @param expenseSettings 费项配置
     * @return 费项配置集合
     */
    List<ExpenseSettings> selectExpenseSettingsList(ExpenseSettings expenseSettings);

    /**
     * 新增费项配置
     *
     * @param expenseSettings 费项配置
     * @return 结果
     */
    int insertExpenseSettings(ExpenseSettings expenseSettings);

    /**
     * 修改费项配置
     *
     * @param expenseSettings 费项配置
     * @return 结果
     */
    int updateExpenseSettings(ExpenseSettings expenseSettings);

    /**
     * 批量删除费项配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteExpenseSettingsByIds(String ids);

    /**
     * 删除费项配置信息
     *
     * @param id 费项配置ID
     * @return 结果
     */
    int deleteExpenseSettingsById(Long id);
}
