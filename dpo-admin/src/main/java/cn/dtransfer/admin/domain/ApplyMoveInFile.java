package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 注册迁入申请上传文件对象 dpo_apply_move_in_file
 *
 * @author dtransfer
 * @date 2024-04-13
 */
@Data
@TableName("dpo_apply_move_in_file")
public class ApplyMoveInFile implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 注册迁入id */
    private Long moveInId;

    /** 文件名称 */
    private String fileName;

    /** 文件路径 */
    private String filePath;

}
