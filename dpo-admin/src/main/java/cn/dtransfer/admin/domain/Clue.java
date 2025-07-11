package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import cn.dtransfer.system.domain.User;
import lombok.Data;

import java.util.Date;

/**
 * 线索管理对象 dpo_clue
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Data
@TableName("dpo_clue")
public class Clue extends BaseEntity<Clue> {
    private static final long serialVersionUID = 1L;

    /**
     * 主题
     */
    private String clueName;

    /**
     * 线索来源:0-现场接待，1-主动电访，2-邮件，3-客户来电，4-短信，5-上门拜访
     */
    private String source;

    /**
     * 渠道分类:0-中价公司，1-广告，2-合作推荐，3-自开发渠道
     */
    private String channelCategory;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 详细描述
     */
    private String remark;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 状态：0-激活，1-已关闭，2-已转客户
     */
    private String customerStatus;

    /**
     * 分派时间
     */
    private Date assignmentTime;

    /**
     * 分派状态:(0-待分配，1-已分派)
     */
    private String assignmentStatus;

    /**
     * 对接人id
     */
    private Long userId;

    /**
     * 关闭原因:0-电话打不通，1-客户暂无需求，2—客户需求已经满足，3—重复线索，4-其他
     */
    private String closeReason;

    /**
     * 关闭说明
     */
    private String closeExplain;

    /**
     * 客户类型(0-公司，1-个人)
     */
    private String customerType;

    /**
     * 纳税人识别号
     */
    private String creditNo;

    /**
     * 所属行业
     */
    private String sector;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 通讯地址
     */
    private String mailAddress;

    /**
     * 邮编
     */
    private String postalCode;

    /**
     * 用户
     */
    private User user;

    @TableField(exist = false)
    private ApplyRoom applyRoom;

}
