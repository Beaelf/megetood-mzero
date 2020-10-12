package spring.controller;

import org.springframework.stereotype.Controller;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/09/10 21:17
 */
@Controller
public class Welcome {
    public void hello(String str){
        System.out.println(str);
    }
}
