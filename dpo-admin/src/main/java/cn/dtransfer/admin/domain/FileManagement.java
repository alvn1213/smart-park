package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 文件管理对象 dpo_file_management
 *
 * @author dtransfer
 * @date 2024-05-24
 */
@Data
@TableName("dpo_file_management")
public class FileManagement extends BaseEntity<FileManagement> {
    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件备注
     */
    private String fileRemarks;

}
