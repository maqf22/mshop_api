package com.example.service.remote;

import com.example.domain.Goods;
import com.mshop.commons.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "SecKillGoodsServer")
public interface RemoteSecKillGoodsServer {
    @RequestMapping("/getGoodsList")
    JsonResult<List<Goods>> getGoodsList();
}
