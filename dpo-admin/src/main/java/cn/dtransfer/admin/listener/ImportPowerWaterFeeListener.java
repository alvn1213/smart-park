package cn.dtransfer.admin.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import cn.dtransfer.admin.vo.ImportPowerWaterFeeVO;
import cn.dtransfer.common.utils.GuavaCacheUtil;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 导入验证监听
 * @author dtransfer
 */
public class ImportPowerWaterFeeListener extends AnalysisEventListener<ImportPowerWaterFeeVO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportPowerWaterFeeListener.class);

    private String cacheKey;

    List<ImportPowerWaterFeeVO> importPowerWaterFeeVOS = Lists.newArrayList();


    public ImportPowerWaterFeeListener(String cacheKey){
            this.cacheKey = cacheKey;
    }

    @Override
    public void invoke(ImportPowerWaterFeeVO importPowerWaterFeeVO, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(importPowerWaterFeeVO));
        if (importPowerWaterFeeVO.getPowerFee() == null) {
            throw new RuntimeException("电费必填!");
        }
        if (importPowerWaterFeeVO.getWaterFee() == null) {
            throw new RuntimeException("水费必填!");
        }
        importPowerWaterFeeVOS.add(importPowerWaterFeeVO);
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
        LOGGER.info("{}条数据，存储数据数据！", importPowerWaterFeeVOS.size());
        GuavaCacheUtil.put(cacheKey, importPowerWaterFeeVOS);
        LOGGER.info("存储数据成功！");
    }
}
