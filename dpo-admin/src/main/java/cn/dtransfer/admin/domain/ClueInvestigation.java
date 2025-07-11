package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.common.core.domain.BaseEntity;
import cn.dtransfer.system.domain.User;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 线索跟进对象 dpo_clue_investigation
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Data
@TableName("dpo_clue_investigation")
public class ClueInvestigation extends BaseEntity<ClueInvestigation> {
    private static final long serialVersionUID = 1L;

    /**
     * 线索id
     */
    private Long clueId;

    /**
     * 意向状态（0-强烈，1-一般，2-无，3-未知）
     */
    private String intentionState;

    /**
     * 跟进时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date investigationTime;

    /**
     * 跟进方式（0-客户上门，1-主动拜访，2-客户来电，3-主动电访，4-短信，5-微信，6-邮件，7-其他）
     */
    private String investigationMode;

    /**
     * 跟进记录
     */
    private String investigationRecord;

    /**
     * 计划跟进时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planTime;

    /**
     * 计划跟进内容
     */
    private String planDetailed;

    /**
     * 线索来源（搜索条件）
     */
    @Transient
    private String source;

    /**
     * 信息检索（搜索条件）
     */
    @Transient
    private String retrieval;

    /**
     * 用户id（搜索条件）
     */
    @Transient
    private Long userId;

    /**
     * 状态（搜索条件）
     */
    @Transient
    private String customerStatus;

    /**
     * 分派状态（搜索条件）
     */
    @Transient
    private String assignmentStatus;

    /**
     * 用户表
     */
    private User user;

    /**
     * 线索管理表
     */
    private Clue clue;

}
