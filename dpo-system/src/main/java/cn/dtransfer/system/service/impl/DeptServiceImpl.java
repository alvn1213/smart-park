package cn.dtransfer.system.service.impl;

import cn.dtransfer.common.constant.UserConstants;
import cn.dtransfer.common.core.domain.Ztree;
import cn.dtransfer.common.exception.BusinessException;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.system.domain.Dept;
import cn.dtransfer.system.mapper.DeptMapper;
import cn.dtransfer.system.service.ICurrentUserService;
import cn.dtransfer.system.service.IDeptService;
import cn.dtransfer.system.domain.Role;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 部门管理 服务实现
 *
 */
@Service
public class DeptServiceImpl implements IDeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private ICurrentUserService currentUserService;





    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<Dept> selectDeptList(Dept dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询部门管理树
     *
     * @param dept 部门信息
     * @return 所有部门信息
     */
    @Override
    public List<Ztree> selectDeptTree(Dept dept) {
        List<Dept> deptList = deptMapper.selectDeptList(dept);
        List<Ztree> ztrees = initZtree(deptList);
        return ztrees;
    }

    /**
     * 根据角色ID查询部门（数据权限）
     *
     * @param role 角色对象
     * @return 部门列表（数据权限）
     */
    @Override
    public List<Ztree> roleDeptTreeData(Role role) {
        Long roleId = role.getId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<Dept> deptList = selectDeptList(new Dept());
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        } else {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Dept> deptList) {
        return initZtree(deptList, null);
    }

    /**
     * 对象转部门树
     *
     * @param deptList     部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Dept> deptList, List<String> roleDeptList) {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (Dept dept : deptList) {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getDeptName());
                ztree.setTitle(dept.getDeptName());
                if (isCheck) {
                    ztree.setChecked(roleDeptList.contains(dept.getId() + dept.getDeptName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 查询部门人数
     *
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(Long parentId) {
        Dept dept = new Dept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(Dept dept) {
        if (dept.getParentId() > 0) {
            Dept info = deptMapper.selectDeptById(dept.getParentId());
            // 如果父节点不为"正常"状态,则不允许新增子节点
            if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
                throw new BusinessException("部门停用，不允许新增");
            }
            dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        }
        if (BooleanUtils.isTrue(dept.getIsDefault())) {
            deptMapper.clearDefault();
        }
        int result = deptMapper.insertDept(dept);
        return result;
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDept(Dept dept) {
        Dept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        Dept oldDept = selectDeptById(dept.getId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getId(), newAncestors, oldAncestors);
        }
        if (BooleanUtils.isTrue(dept.getIsDefault())) {
            deptMapper.clearDefault();
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(Dept dept) {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectDeptById(dept.getId());
        dept.setUpdateBy(updateBy);
        deptMapper.updateDeptStatus(dept);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<Dept> children = deptMapper.selectChildrenDeptById(deptId);
        for (Dept child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public Dept selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(Dept dept) {
        Long deptId = StringUtils.isNull(dept.getId()) ? -1L : dept.getId();
        Dept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != deptId.longValue()) {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    @Override
    public Set<String> roleDeptIds(Long roleId) {
        return deptMapper.selectRoleDeptIds(roleId);
    }


    /**
     * 查找默认部门
     *
     * @return 默认部门，若不存在则返回null
     */
    @Override
    public Dept findDefault() {
        return deptMapper.findDefault();
    }


    /**
     * 新增保存园区信息
     *
     * @param dept 园区信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPark(Dept dept) {
        Long currentDeptId = currentUserService.getCurrentUser().getDeptId();
        dept.setParentId(currentDeptId);
        Dept info = deptMapper.selectDeptById(currentDeptId);
        // 如果父节点不为"正常"状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new BusinessException("停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());

        return deptMapper.insertDept(dept);
    }

    /**
     * 查询子部门ids
     * @param ancestors
     * @return
     */
    @Override
    public List<Dept> selectChildrenIds(String ancestors) {
        return deptMapper.selectChildrenIds(ancestors);
    }

    /**
     * 根据部门id查询园区id
     * @return
     */
    @Override
    public Long selectParent(Long tenantId) {
        return deptMapper.selectParent(tenantId);
    }
}
