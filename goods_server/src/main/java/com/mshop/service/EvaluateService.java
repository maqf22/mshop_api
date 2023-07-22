package com.mshop.service;

import com.mshop.commons.PageBean;
import com.mshop.domain.Evaluate;

import java.util.List;

public interface EvaluateService {
    PageBean<List<Evaluate>> getEvaluateList(Long goodsId, Integer evaluateLevel, Long pageNo, Long pageSize);
}
