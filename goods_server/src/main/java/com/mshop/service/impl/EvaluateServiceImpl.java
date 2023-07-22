package com.mshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mshop.commons.PageBean;
import com.mshop.domain.Evaluate;
import com.mshop.mapper.EvaluateMapper;
import com.mshop.service.EvaluateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Resource
    private EvaluateMapper evaluateMapper;

    @Override
    public PageBean<List<Evaluate>> getEvaluateList(Long goodsId, Integer evaluateLevel, Long pageNo, Long pageSize) {
        // 创建pageBean
        PageBean<List<Evaluate>> pageBean = new PageBean<>(pageNo, pageSize);
        // 设置查询条件
        QueryWrapper<Evaluate> evaluateQW = new QueryWrapper<>();
        if (-1 == evaluateLevel) {
            evaluateQW.eq("goods_id", goodsId);
        } else {
            evaluateQW.eq("goods_id", goodsId).eq("score", evaluateLevel);
        }
        // 根据条件查询总条数并设置pageBean.totalNum
        Long totalNum = evaluateMapper.selectCount(evaluateQW);
        pageBean.setTotalNum(totalNum);
        // 创建page定义查询第几页和每页大小
        Page<Evaluate> page = Page.of(pageNo, pageSize);
        evaluateMapper.selectPage(page, evaluateQW);
        // 获取查询结果
        List<Evaluate> resultData = page.getRecords();
        pageBean.setData(resultData);
        return pageBean;
    }
}
