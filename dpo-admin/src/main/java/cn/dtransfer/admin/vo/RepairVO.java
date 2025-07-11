package cn.dtransfer.admin.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.admin.domain.BannerImage;
import cn.dtransfer.admin.domain.RepairLog;
import cn.dtransfer.admin.handler.BannerImageHandler;
import cn.dtransfer.system.domain.vo.UserVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报修VO
 *
 * @author dtransfer
 */
@Data
public class RepairVO {

    /**
     * 最大Banner图片数量
     */
    public static final int MAX_REPAIR_IMAGE_SIZE = 4;

    /**
     * Id
     */
    private Long id;

    /**
     * 报修单号
     */
    private String sn;

    /**
     * 报修人
     */
    private String name;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 报修区域
     */
    private String area;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 描述
     */
    private String content;

    /**
     * 报修时间
     */
    private Date repairTime;

    /**
     * 维修费
     */
    private Long repairFee;

    /**
     * 评价
     */
    private Long repairScore;

    /**
     * 备注
     */
    private String remark;

    /**
     * 维修材料
     */
    private String repairMateriel;

    /**
     * 开始时间
     */
    private Date beginDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 评价工人
     */
    private Long workerScore;

    /**
     * 工人ID
     */
    private Long workerId;

    /**
     * 公司ID
     */
    private Long customerId;

    /**
     * 公司
     */
    private CustomerVO customerVO;

    /**
     * 园区ID
     */
    private Long parkId;

    /**
     * 园区
     */
    private ParkVO parkVO;

    /**
     * 用户
     */
    private UserVO userVO;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 工单记录
     */
    private List<RepairLog> repairLogs = new ArrayList<>();


    /**
     * 报修图片
     */
    @TableField(typeHandler = BannerImageHandler.class)
    private List<BannerImage> repairImages = new ArrayList<>();

}
