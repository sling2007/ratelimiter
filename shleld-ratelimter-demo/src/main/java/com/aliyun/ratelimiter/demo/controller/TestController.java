package com.aliyun.ratelimiter.demo.controller;

import com.aliyun.shield.ratelimiter.core.annotation.RateLimiter;
import com.aliyun.shield.ratelimiter.core.cache.CacheProxy;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private static final String MESSAGE = "{\"code\":\"400\",\"msg\":\"FAIL\",\"desc\":\"触发限流\"}";

    final static String testKey = "myResourceKey";

    @Autowired
    CacheProxy cacheProxy;

    /**
     * 资源请求。
     * 用RateLimiterHandler限制请求QPS
     */
    @ResponseBody
    @RequestMapping("myResource")
    @RateLimiter(key = testKey, limit = 5, expire = 5, message = MESSAGE)
    public String resource(HttpServletRequest request) throws Exception {
        return "正常请求";
    }

    /**
     * 获取资源的(当前的)请求量计数
     */
    @ResponseBody
    @RequestMapping("myResource/rate")
    public String rate(HttpServletRequest request) throws Exception {
        return "rate: " + cacheProxy.getLong(testKey);
    }


}
