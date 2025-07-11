package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IExpenseSettingsService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ExpenseSettings;
import cn.dtransfer.admin.mapper.ExpenseSettingsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 费项配置Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Service
public class ExpenseSettingsServiceImpl extends ServiceImpl<ExpenseSettingsMapper, ExpenseSettings> implements IExpenseSettingsService {
    @Autowired
    private ExpenseSettingsMapper expenseSettingsMapper;

    /**
     * 查询费项配置
     *
     * @param id 费项配置ID
     * @return 费项配置
     */
    @Override
    public ExpenseSettings selectExpenseSettingsById(Long id) {
        return expenseSettingsMapper.selectById(id);
    }

    /**
     * 查询费项配置列表
     *
     * @param expenseSettings 费项配置
     * @return 费项配置
     */
    @Override
    public List<ExpenseSettings> selectExpenseSettingsList(ExpenseSettings expenseSettings) {
        QueryWrapper<ExpenseSettings> queryWrapper = new QueryWrapper<>();
        if (expenseSettings.getName() != null) {
            queryWrapper.like("name", expenseSettings.getName());
        }
        if (expenseSettings.getType() != null) {
            queryWrapper.eq("type", expenseSettings.getType());
        }
        return expenseSettingsMapper.selectList(queryWrapper);
    }

    /**
     * 新增费项配置
     *
     * @param expenseSettings 费项配置
     * @return 结果
     */
    @Override
    public int insertExpenseSettings(ExpenseSettings expenseSettings) {

        return expenseSettingsMapper.insert(expenseSettings);
    }

    /**
     * 修改费项配置
     *
     * @param expenseSettings 费项配置
     * @return 结果
     */
    @Override
    public int updateExpenseSettings(ExpenseSettings expenseSettings) {
        return expenseSettingsMapper.updateById(expenseSettings);
    }

    /**
     * 删除费项配置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExpenseSettingsByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return expenseSettingsMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除费项配置信息
     *
     * @param id 费项配置ID
     * @return 结果
     */
    @Override
    public int deleteExpenseSettingsById(Long id) {
        return expenseSettingsMapper.deleteById(id);
    }
}
