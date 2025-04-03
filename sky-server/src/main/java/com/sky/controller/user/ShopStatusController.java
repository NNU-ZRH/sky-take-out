package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author zrh
 * @version 1.0.0
 * @title ShopStatusController
 * @description <>
 * @create 2025/4/3 23:15
 **/
@RestController("UserShopStatusController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags = "店铺状态接口")
public class ShopStatusController {
    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @GetMapping("/status")
    @ApiOperation("获取设置店铺状态")
    public Result<Integer> status() {
        Integer status = redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取到店铺状态为{}", status == null ? "打样中" : status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }

    @PutMapping("/{status}")
    @ApiOperation("设置店铺状态")
    public Result<String> setStatus(@PathVariable("status") Integer status) {
        log.info("设置店铺状态为{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set("SHOP_STATUS", status);
        return Result.success("店铺状态设置成功");
    }
}
