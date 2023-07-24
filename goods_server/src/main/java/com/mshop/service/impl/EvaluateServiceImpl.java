package com.mshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
// import com.baomidou.mybatisplus.core.metadata.IPage;
// import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mshop.commons.PageBean;
import com.mshop.domain.Evaluate;
import com.mshop.domain.EvaluateImg;
import com.mshop.mapper.EvaluateImgMapper;
import com.mshop.mapper.EvaluateMapper;
import com.mshop.service.EvaluateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Resource
    private EvaluateMapper evaluateMapper;
    @Resource
    private EvaluateImgMapper evaluateImgMapper;

    @Override
    public PageBean<List<Map<String, Object>>> getEvaluateList(Long goodsId, String evaluateLevel, Long pageNo, Long pageSize) {
        // 创建pageBean
        PageBean<List<Map<String, Object>>> pageBean = new PageBean<>(pageNo, pageSize);
        Long totalNum = 0L;
        if (evaluateLevel.equals("img")) {
            totalNum = evaluateImgMapper.getHasImageEvaluateListCount(goodsId);
            System.out.println("img => " + totalNum);
        } else {
            // 设置查询条件
            QueryWrapper<Evaluate> evaluateQW = new QueryWrapper<>();
            switch (evaluateLevel) {
                case "a":
                    evaluateQW.ge("score", 4);
                    break;
                case "b":
                    evaluateQW.eq("score", 3);
                    break;
                case "c":
                    evaluateQW.le("score", 2);
                    break;
            }
            evaluateQW.eq("goods_id", goodsId);
            // 根据条件查询总条数并设置pageBean.totalNum
            totalNum = evaluateMapper.selectCount(evaluateQW);
        }
        pageBean.setTotalNum(totalNum);
        List<Map<String, Object>> resultData = evaluateMapper.getEvaluateList(goodsId, evaluateLevel, pageBean.getSkipNum(), pageSize);
        for (Map<String, Object> r : resultData) {
            QueryWrapper<EvaluateImg> evaluateImgQW = new QueryWrapper<>();
            evaluateImgQW.eq("evaluate_id", r.get("id"));
            List<EvaluateImg> evaluateImgs = evaluateImgMapper.selectList(evaluateImgQW);
            r.put("evaluate_imgs", evaluateImgs);
        }
        /*
        // 创建page定义查询第几页和每页大小
        Page<Evaluate> page = Page.of(pageNo, pageSize);
        evaluateMapper.selectPage(page, evaluateQW);
        // 获取查询结果
        List<Evaluate> resultData = page.getRecords();
        */
        pageBean.setData(resultData);
        return pageBean;
    }

    @Override
    public Map<String, Long> getCountEvaluateNum(Long goodsId) {
        Map<String, Long> evaluateNum = evaluateMapper.getCountEvaluateNum(goodsId);
        return evaluateNum;
    }
}
