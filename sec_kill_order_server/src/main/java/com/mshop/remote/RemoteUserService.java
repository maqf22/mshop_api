package com.mshop.remote;

import com.mshop.commons.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("UserServer")
public interface RemoteUserService {
    @GetMapping("/getUserId")
    JsonResult<Long> getUserId(@RequestParam String token);
}
