package com.mshop.controller;

import com.mshop.commons.Code;
import com.mshop.commons.JsonResult;
import com.mshop.commons.PageBean;
import com.mshop.domain.Evaluate;
import com.mshop.service.EvaluateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class EvaluateController {
    @Resource
    private EvaluateService evaluateService;

    @GetMapping("/getEvaluateList")
    public Object getEvaluateList(Long goodsId, Integer evaluateLevel, Long pageNo, Long pageSize) {
        PageBean<List<Evaluate>> pageBean = evaluateService.getEvaluateList(goodsId, evaluateLevel, pageNo, pageSize);
        return new JsonResult<PageBean>(Code.OK, pageBean);
    }
}
