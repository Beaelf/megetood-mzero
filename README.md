# MZERO

**A Bean Container Framework Project**

## overview

* Bean Container
  1. bean definition
  2. bean injection
  3. bean lookup

 MZERO will scan the package you provide, you can use `@Component` `@Controller` `@Repository` `@Service` to sign a class, which means such classes will be loaded in MZERO bean container.

The fields of objects which are managed by MZERO container sigh with   `@Autowired` will be injected the same type Object or value.

You can also lookup Bean exists in MZERO by the method MZERO container provide.

* AOP

MZERO supports you to implements AOP to enhance your method. To do a Aspect for your project, just only add a class extends DefaultAspect class, and sign the class with `@Aspect`. And you can also decide the aspect order  with `@Order`. Finally，you have to appoint the aspect scope. There are two ways for you: The ont way, scan the package you appoint; the another way, scan the object in the MZERO container with the annotation you appoint. 

* MVC

If you project is a web project, the MVC module is benefical to your project. you can sign a Controller with `@Controller` annotation. the `@RequestMapping` to mark a method in the controller as a request invoke method. the `@RequestParam` and `@RequestBody` to appoint  parameters of the method.

The MVC module of MZERO supports return types contain jsp，static source , view and json.

## Quick Start

* Step 1: Add the dependence in your project

* Step 2: Add your code

  use `MzeroApplication.run(packageName)` to run Mzero Container.

```java
package com.megetood;

import org.mzero.MzeroApplication;
import org.mzero.mvc.annotation.RequestMapping;
import org.mzero.mvc.annotation.RequestParam;
import org.mzero.mvc.type.RequestMethod;

@Controller
public class DemoApplication {
    public static void main(String[] args) {
        MzeroApplication.run("com/megetood/demo");
    }

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @RequestBody
    public String hello(@RequestParam(value = "name", required = true) String name) {
        return String.format("Hello %s!", name);
    }
}
```

## Other Simple example

* Add a bean to be manage by MZERO container

  ```java
  package com.megetood.service.solo.impl;
  
  import com.megetood.service.solo.HeadLineService;
  import org.mzero.core.annotation.Service;
  
  @Service
  public class HeadLineServiceImpl implements HeadLineService {
  	// do something
  }
  ```

  

* Add a Aspect 

  ```java
  package com.megetood.aspect;
  
  import lombok.extern.slf4j.Slf4j;
  import org.mzero.aop.v2.annotation.Aspect;
  import org.mzero.aop.v2.annotation.Order;
  import org.mzero.aop.v2.aspect.DefaultAspect;
  
  import java.lang.reflect.Method;
  
  @Slf4j
  @Aspect(pointcut = "within(com.megetood.controller.superadmin.*)")
  @Order(10)
  public class ServiceInfoRecordAspect extends DefaultAspect {
  
      private Long timestampCache;
  
      @Override
      public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
          log.info("ServiceInfoRecordAspect start, the class: [{}], the method: [{}], the args: [{}]",
                  targetClass.getName(), method.getName(), args);
          timestampCache = System.currentTimeMillis();
      }
  
      @Override
      public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
          log.info("ServiceInfoRecordAspect finished, the class: [{}], the method: [{}], the args: [{}]",
                  targetClass.getName(), method.getName(), args);
          return returnValue;
      }
  
      @Override
      public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {
          log.info("ServiceInfoRecordAspect throw exception, the class: [{}], the method: [{}], the args: [{}]",
                  targetClass.getName(), method.getName(), args,e.getMessage());
      }
  }
  ```

the `@Aspect(pointcut = "within(com.megetood.service.superadmin.*)")` could be replaced by `@Aspect(value = Service.class)` in package "org.mzero.aop.v1.annotation.Aspect"

* Bean Lookup

  ```java
  public void test(){
          BeanContainer container = BeanContainer.getInstance();
          HeadLineServiceImpl bean = (HeadLineServiceImpl) container.getBean(HeadLineService.class);
  }
  ```

  
