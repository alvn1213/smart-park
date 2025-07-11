package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 楼层管理对象 dpo_building_detail
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Data
@TableName("dpo_building_detail")
public class BuildingDetail extends BaseEntity<BuildingDetail> {
    private static final long serialVersionUID = 1L;

    /**
     * 楼宇id
     */
    private Long buildingId;

    /**
     * 楼层编号
     */
    private Long floorNum;

    /**
     * 楼层名称
     */
    private String floorName;

    /**
     * 楼层平面图
     */
    private String floorPic;

}
