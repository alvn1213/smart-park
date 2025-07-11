package cn.dtransfer.system.controller;

import com.google.common.collect.Lists;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.common.utils.file.FileUploadUtils;
import cn.dtransfer.common.utils.file.FileUtils;
import cn.dtransfer.system.config.DfsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.wf.jwtp.annotation.Logical;
import org.wf.jwtp.annotation.RequiresPermissions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 通用请求处理
 *
 */
@Slf4j
@RestController
@RequestMapping("dfs")
public class FileController {

    @Autowired
    private DfsConfig dfsConfig;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("download")
    public void download(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = dfsConfig.getPath() + fileName;
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @RequiresPermissions(value = {"system:account:center","member:center:view"},logical = Logical.OR)
    @PostMapping("upload")
    public R upload(MultipartFile file, String originalFilename) {
        try {
            Long size = file.getSize();
            // 上传文件路径
            String filePath = dfsConfig.getPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = dfsConfig.getDomain() + fileName;
            return R.ok().put("originalFilename", originalFilename).put("fileSize", size).put("fileName", fileName).put("url", url);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求
     */
    @RequiresPermissions("system:account:center")
    @PostMapping("upload_files")
    public R uploadFiles(List<MultipartFile> files) {
        try {
            // 上传文件路径
            String filePath = dfsConfig.getPath();
            List<String> fileUrls = Lists.newArrayList();
            // 上传并返回新文件名称
            for(MultipartFile file : files){
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = dfsConfig.getDomain() + fileName;
                fileUrls.add(url);
            }
            return R.ok().put("errno", "0").put("data", fileUrls);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.error(e.getMessage()).put("errno", "1");
        }
    }

}
