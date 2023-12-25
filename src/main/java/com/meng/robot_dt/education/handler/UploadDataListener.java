package com.meng.robot_dt.education.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.meng.robot_dt.education.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：cola
 * @description：
 * @date ：2023/7/24 9:43
 */
public class UploadDataListener<T> extends AnalysisEventListener<T> {

    /**
     * 数据集
     */
    private final List<T> list = new ArrayList<>();

    public List<T> getList() {
        return this.list;
    }

    /**
     * 每条数据都会进入
     *
     * @param object：
     * @param analysisContext：
     */
    @Override
    public void invoke(T object, AnalysisContext analysisContext) {
        this.list.add(object);
    }

    /**
     * 数据解析完调用
     *
     * @param analysisContext：
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    /**
     * 异常时调用
     *
     * @param exception：
     * @param context：
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        // 数据解析异常
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            throw new BusinessException("第" + excelDataConvertException.getRowIndex() + "行" + excelDataConvertException.getColumnIndex() + "列" + "数据解析异常");
        }
        // 其他异常...
    }
}
