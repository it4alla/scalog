package com.java4all.scalog;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangzhongxiang
 * @date 2020年05月14日 11:37:30
 */
@RestController
@RequestMapping("user")
public class UserController {

    @RequestMapping("test1")
    public String test1(){
        return "test1";
    }


    @RequestMapping("test2")
    public String test2(){
        return "test2";
    }

    public static void main(String[]args){
        int i =0;
        while (true){
            i ++;
            System.out.println(i);
            if (i == 100){
                continue;
            }
            System.out.println("aaaa-"+i);
            if(i==1000){
                break;
            }
        }
    }
}
