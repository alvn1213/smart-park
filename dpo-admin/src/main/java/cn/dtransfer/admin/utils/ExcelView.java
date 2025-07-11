package cn.dtransfer.admin.utils;

import cn.dtransfer.common.utils.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Excel视图
 *
 * @author dtransfer
 */
public class ExcelView {
    private static final Logger log = LoggerFactory.getLogger(ExcelView.class);

    private static final String CONTENT_DISPOSITION = "Content-disposition";

    public ExcelView() {
    }

    /**
     * 构造方法
     *
     * @param templatePath     模板路径
     * @param downloadFilename 文件名称
     */
    public ExcelView(String templatePath, String downloadFilename, Context context) {


        HttpServletResponse response = ServletUtils.getResponse();
        if (StringUtils.isEmpty(response.getContentType())) {
            response.setContentType("application/force-download");
        }
        // 指定到resource目录
        ClassPathResource classPathResource = new ClassPathResource(templatePath);
        try (InputStream inputStream = classPathResource.getInputStream()) {
            if (!response.containsHeader(CONTENT_DISPOSITION)) {
                if (StringUtils.isNotEmpty(downloadFilename)) {
                    response.setHeader(CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(downloadFilename, "UTF-8"));
                } else {
                    response.setHeader(CONTENT_DISPOSITION, "attachment");
                }
            }

            OutputStream outputStream = response.getOutputStream();
            JxlsHelper.getInstance().processTemplate(inputStream, response.getOutputStream(), context);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 构造方法(保存到指定服务器路径)
     *
     * @param templatePath 模板路径
     */
    public ExcelView(String templatePath, Context context, File file) {
        // 指定到resource目录
        ClassPathResource classPathResource = new ClassPathResource(templatePath);
        try (InputStream inputStream = classPathResource.getInputStream()) {
            FileOutputStream fos = new FileOutputStream(file);
            JxlsHelper.getInstance().processTemplate(inputStream, fos, context);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


}
