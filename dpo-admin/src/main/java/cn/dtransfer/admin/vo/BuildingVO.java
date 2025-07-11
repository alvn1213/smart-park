package cn.dtransfer.admin.vo;


import lombok.Data;

import java.util.List;

/**
 * 楼宇VO
 *
 * @author dtransfer
 */
@Data
public class BuildingVO {

    /** 楼宇id */
    private Long buildingId;

    /** 楼宇名称 */
    private String buildingName;

    /** 楼层列表 */
    private List<BuildingDetailVO> buildingDetailVOList;

}
