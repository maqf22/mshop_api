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
import java.util.Map;

@RestController
public class EvaluateController {
    @Resource
    private EvaluateService evaluateService;

    @GetMapping("/getEvaluateList")
    public JsonResult<PageBean> getEvaluateList(Long goodsId, String evaluateLevel, Long pageNo, Long pageSize) {
        PageBean<List<Map<String, Object>>> pageBean = evaluateService.getEvaluateList(goodsId, evaluateLevel, pageNo, pageSize);
        return new JsonResult<>(Code.OK, pageBean);
    }

    @GetMapping("/getCountEvaluateNum")
    public JsonResult<Map<String, Long>> getCountEvaluateNum(Long goodsId) {
        Map<String, Long> evaluateNum = evaluateService.getCountEvaluateNum(goodsId);
        return new JsonResult<>(Code.OK, evaluateNum);
    }
}
