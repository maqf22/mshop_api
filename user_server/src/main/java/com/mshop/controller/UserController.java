package com.mshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.mshop.commons.Code;
import com.mshop.commons.JsonResult;
import com.mshop.domain.Address;
import com.mshop.domain.User;
import com.mshop.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录接口
     *
     * @param user
     * @return JsonResult
     */
    @PostMapping("/login")
    public JsonResult<Object> login(User user) {
        if (null == user.getPhone() || null == user.getPassword()) {
            return new JsonResult<>(Code.ERROR, "传入参数错误", "");
        }
        Long errorNum = 0L; // 登录失败次数
        String loginErrNumStr = stringRedisTemplate.opsForValue().get("loginErrNum:" + user.getPhone());
        if (null != loginErrNumStr && Long.parseLong(loginErrNumStr) >= 5) {
            return new JsonResult<>(Code.ERROR, "登录过于频繁，账号已锁定", "");
        }
        // 返回登录状态，0-登录成功 1-账号错误 2-密码错误
        int result = userService.login(user);
        if (0 != result) {
            errorNum = stringRedisTemplate.opsForValue().increment("loginErrNum:" + user.getPhone());
            if (null != errorNum && 1 == errorNum) {
                lockUser(user.getPhone());
            }
            return new JsonResult<>(Code.ERROR, "账号与密码不匹配", result);
        }
        // 登录成功后删除用户登录失败记数器
        stringRedisTemplate.delete("loginErrNum:" + user.getPhone());
        String token = createToken(user);
        HashMap<String, Object> resultData = getUserInfo(user, token);
        return new JsonResult<>(Code.OK, "登录成功", resultData);
    }

    /**
     * 锁定账号
     *
     * @param phone
     */
    private void lockUser(String phone) {
        // 定义日历对象
        Calendar calendar = Calendar.getInstance();
        // 设置日历的是分秒为0时0分0秒
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        // 在当前日历的时间基础上+1天
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        // 计算当前时间到明天的0时0分0秒剩多少秒
        Duration timeout = Duration.ofSeconds((calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000);
        // 设置登录失败次数的超时时间
        stringRedisTemplate.expire("loginErrNum:" + phone, timeout);
    }

    /**
     * 注册接口
     *
     * @param user
     * @return JsonResult
     */
    @PostMapping("/register")
    public JsonResult<Object> register(User user) {
        if (user.getNickName().equals("")) {
            return new JsonResult<>(Code.ERROR, "昵称不能为空", "");
        }
        if (user.getPhone().equals("")) {
            return new JsonResult<>(Code.ERROR, "手机号不能为空", "");
        }
        if (user.getPassword().equals("")) {
            return new JsonResult<>(Code.ERROR, "密码不能为空", "");
        }
        // 注册用户，返回0表示注册成功，返回1表示注册失败用户重复
        int result = userService.regUser(user);
        if (1 == result) {
            return new JsonResult<>(Code.ERROR, "手机号已存在，请更换或直接登录", "");
        }
        String token = createToken(user);
        HashMap<String, Object> resultData = getUserInfo(user, token);
        return new JsonResult<>(Code.OK, "注册成功", resultData);
    }

    /**
     * 通过token获取userId
     *
     * @param token
     * @return
     */
    @GetMapping("/getUserId")
    public JsonResult<Long> getUserId(String token) {
        String userJson = checkUser(token);
        if (null == userJson) {
            return new JsonResult<>(Code.NOT_LOGIN, null);
        }
        Long userId = JSONObject.parseObject(userJson).getBigInteger("id").longValue();
        return new JsonResult<>(Code.OK, userId);
    }

    /**
     * 跟据用户id获取用户的收货地址
     *
     * @param token
     * @return
     */
    @GetMapping("/confirmUserAddress")
    public JsonResult<List<Address>> confirmUserAddress(String token) {
        String userJson = checkUser(token);
        if (null == userJson) {
            return new JsonResult<>(Code.NOT_LOGIN, null);
        }
        Long userId = JSONObject.parseObject(userJson).getBigInteger("id").longValue();
        List<Address> list = userService.getUserAddressByUserId(userId);
        return new JsonResult<>(Code.OK, list);
    }

    /**
     * 检查用户是否登录
     *
     * @param token
     * @return
     */
    private String checkUser(String token) {
        String userJson = stringRedisTemplate.opsForValue().get("userToken:" + token);
        if (null == userJson) {
            return null;
        }
        Duration timeout = Duration.ofSeconds(60 * 30);
        stringRedisTemplate.expire("userToken:" + token, timeout);
        return userJson;
    }

    /**
     * 创建token
     *
     * @param user
     * @return
     */
    private String createToken(User user) {
        // token是用户登录成功后的身份令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        Duration timeout = Duration.ofSeconds(60 * 30);
        stringRedisTemplate.opsForValue().set("userToken:" + token, JSONObject.toJSONString(user), timeout);
        return token;
    }

    /**
     * 组织要返回用户信息
     *
     * @param user
     * @param token
     * @return
     */
    private HashMap<String, Object> getUserInfo(User user, String token) {
        // 要返回的用户信息
        HashMap<String, Object> resultData = new HashMap<>();
        resultData.put("token", token);
        resultData.put("phone", user.getPhone());
        resultData.put("nick_name", user.getNickName());
        resultData.put("id", user.getId());
        return resultData;
    }
}
