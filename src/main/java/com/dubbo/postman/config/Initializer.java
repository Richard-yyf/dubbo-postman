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

import com.dubbo.postman.domain.DubboModel;
import com.dubbo.postman.repository.RedisRepository;
import com.dubbo.postman.service.appfind.zk.ZkServiceFactory;
import com.dubbo.postman.service.dubboinvoke.TemplateBuilder;
import com.dubbo.postman.util.CommonUtil;
import com.dubbo.postman.util.JSON;
import com.dubbo.postman.util.RedisKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author everythingbest
 * 执行系统启动的时候需要加载的配置
 */
public class Initializer {

    private Logger logger = LoggerFactory.getLogger(Initializer.class);


    public void loadCreatedService(RedisRepository redisRepository,
                                   String dubboModelRedisKey,
                                   TemplateBuilder templateBuilder){

        Set<Object> keys = redisRepository.mapGetKeys(dubboModelRedisKey);

        logger.info("已经创建的服务数量:" + keys.size());

        for (Object key : keys) {

            String zk = CommonUtil.getZk((String) key);

            String serviceName = CommonUtil.getServiceName((String) key);

            Object object = redisRepository.mapGet(dubboModelRedisKey, key);

            String dubboModelString = (String) object;

            DubboModel dubboModel = JSON.parseObject(dubboModelString, DubboModel.class);

            dubboModel.setServiceName(serviceName);

            dubboModel.setZkAddress(zk);

            templateBuilder.buildTemplateByDubboModel(dubboModel);
        }
    }


    void loadZkAddress(RedisRepository redisRepository) {

        Set serverList = redisRepository.members(RedisKeys.ZK_REDIS_KEY);

        if (serverList == null || serverList.isEmpty()) {

            //系统第一次使用
            logger.info("没有配置任何zk地址,请通过web-ui添加zk地址");
            return;
        }

        ZkServiceFactory.ZK_SET.addAll(serverList);

        logger.info("系统当前已经添加的zk地址:" + ZkServiceFactory.ZK_SET);

        for (String zk : ZkServiceFactory.ZK_SET) {

            ZkServiceFactory.get(zk);
        }

    }
}
