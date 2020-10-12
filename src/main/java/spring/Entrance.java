package spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.controller.Welcome;

import java.nio.file.attribute.UserPrincipal;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/09/10 21:10
 */
@Configuration
@ComponentScan("spring")
public class Entrance {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Entrance.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        System.out.println("------ start to learn spring design ------");
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        Welcome welcome = context.getBean(Welcome.class);
        welcome.hello("hello megetood,its a good night.");
    }
}
