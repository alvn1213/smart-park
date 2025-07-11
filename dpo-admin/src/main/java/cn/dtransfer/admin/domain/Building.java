package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 楼宇管理对象 dpo_building
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Data
@TableName("dpo_building")
public class Building extends BaseEntity<Building> {
    private static final long serialVersionUID = 1L;

    /**
     * 楼宇名称
     */
    private String buildingName;

    /**
     * 楼宇平面图
     */
    private String buildingPic;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关联园区
     */
    private Park park;

}
