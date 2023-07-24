package com.mshop.service;

import com.mshop.commons.PageBean;
import com.mshop.domain.Evaluate;

import java.util.List;
import java.util.Map;

public interface EvaluateService {
    PageBean<List<Map<String, Object>>> getEvaluateList(Long goodsId, String evaluateLevel, Long pageNo, Long pageSize);

    Map<String, Long> getCountEvaluateNum(Long goodsId);
}
