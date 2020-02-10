/*
 * MIT License
 *
 * Copyright (c) 2019 everythingbest
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dubbo.postman.config;

import com.dubbo.postman.repository.RedisRepository;
import com.dubbo.postman.service.dubboinvoke.TemplateBuilder;
import com.dubbo.postman.util.Constant;
import com.dubbo.postman.util.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author everythingbest
 * 启动的时候systemInit()加载需要的zk地址和已经创建的服务
 */
@Configuration
public class AppConfig {

    @Value("${dubbo.api.jar.dir}")
    String apiJarPath;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private TemplateBuilder templateBuilder;

    @Bean
    Initializer initializer() throws Exception {

        Initializer initializer = new Initializer();

        //统一设置路径入口,其他地方通过System.getProperty获取
        System.setProperty(Constant.USER_HOME, apiJarPath);

        initializer.loadZkAddress(redisRepository);

        initializer.loadCreatedService(redisRepository, RedisKeys.DUBBO_MODEL_KEY,templateBuilder);

        return initializer;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "GET")
                        // 响应的缓存持续时间的最大年龄，简单来说就是Cookie的有效期 单位为秒
                        .maxAge(3600)
                        // 用户是否可以处理发送cookie
                        .allowCredentials(false);
            }
        };
    }
}
