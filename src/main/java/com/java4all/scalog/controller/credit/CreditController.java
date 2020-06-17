package com.java4all.scalog.controller.credit;

import com.java4all.scalog.annotation.LogInfo;
import com.java4all.scalog.controller.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangzhongxiang
 * @date 2020年05月14日 11:37:30
 */
@RestController
@RequestMapping("credit")
public class CreditController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            CreditController.class);

    public String test0(String name,Integer age){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "未查询到此用户，name="+name+",age="+age;
    }

    @RequestMapping("test1")
    public String test1(){
        return "test1";
    }


    @PostMapping("test2")
    public String test2(String name,String age){
        return "test2";
    }

    @GetMapping("decrease")
    @LogInfo(companyName = "兰亮网络科技",projectName = "新金融",moduleName = "授信模块",functionName = "扣减授信",remark = "用于借款时扣减授信")
    public User decrease(String name,Integer age){
        String s = this.test0(name,age);
        LOGGER.info("test0调用结果为 = {}",s);
        return new User("张三",88);
    }
    //http://192.168.158.80:8888/user/test3?name=%E7%BA%A2%E7%8B%AE&age=18

}
