package cn.dtransfer.admin.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import cn.dtransfer.admin.vo.ImportRentVO;
import cn.dtransfer.common.utils.GuavaCacheUtil;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 导入验证监听
 * @author dtransfer
 */
public class ImportRentListener extends AnalysisEventListener<ImportRentVO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportRentListener.class);

    private String cacheKey;

    List<ImportRentVO> importRentVOS = Lists.newArrayList();


    public ImportRentListener(String cacheKey){
            this.cacheKey = cacheKey;
    }

    @Override
    public void invoke(ImportRentVO importRentVO, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(importRentVO));
        if (StringUtils.isEmpty(importRentVO.getSn())) {
            throw new RuntimeException("账单号必填!");
        }
        if (importRentVO.getReceiveRent() == null) {
            throw new RuntimeException("实收租金必填!");
        }
        if (importRentVO.getReceiveManagementTotalFee() == null) {
            throw new RuntimeException("物业管理费必填!");
        }
        importRentVOS.add(importRentVO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveDataCache(cacheKey);
        LOGGER.info("所有数据解析完成！");
    }


    /**
     * 加上存储数据库
     */
    private void saveDataCache(String cacheKey) {
        LOGGER.info("{}条数据，存储数据数据！", importRentVOS.size());
        GuavaCacheUtil.put(cacheKey, importRentVOS);
        LOGGER.info("存储数据成功！");
    }
}
