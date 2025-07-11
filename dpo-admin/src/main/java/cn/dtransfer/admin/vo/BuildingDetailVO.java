package cn.dtransfer.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * 楼层VO
 *
 * @author dtransfer
 */
@Data
public class BuildingDetailVO {

    /** 楼层编号 */
    private Integer floorNum;

    /** 房间列表 */
    private List<RoomVO> roomVOList;
}
