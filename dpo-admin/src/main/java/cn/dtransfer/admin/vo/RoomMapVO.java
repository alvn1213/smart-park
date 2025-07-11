package cn.dtransfer.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * 园区房间VO
 *
 * @author dtransfer
 */
@Data
public class RoomMapVO {

    /** 租户id */
    private Long tenantId;

    /** 园区名称 */
    private String parkName;

    /** 楼宇列表 */
    private List<BuildingVO> buildingVOList;

    /** 是否删除 */
    private Integer deleteFlag;

}
