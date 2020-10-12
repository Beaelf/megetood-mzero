package org.mzero.aop;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mzero.aop.v2.AspectListExecutor;
import org.mzero.aop.v2.aspect.AspectInfo;
import org.mzero.aop.mock.*;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 23:02
 */
public class AspectListExecutorTest {
    @DisplayName("aspectinfo sort")
    @Test
    public void sortTest(){
        List<AspectInfo> list = new ArrayList<AspectInfo>(){
            {
                add(new AspectInfo(1, new mock1(), null));
                add(new AspectInfo(5, new mock5(),null));
                add(new AspectInfo(2, new mock2(),null));
                add(new AspectInfo(4, new mock4(),null));
                add(new AspectInfo(3, new mock3(),null));
            }
        };
        AspectListExecutor aspectListExecutor = new AspectListExecutor(AspectListExecutorTest.class, list);
        aspectListExecutor.getSortedAspectInfoList().forEach((e)->{
            System.out.println(JSON.toJSONString(e,true));
        });
    
    }
}
