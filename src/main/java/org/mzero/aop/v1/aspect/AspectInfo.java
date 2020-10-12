package org.mzero.aop.v1.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 切面相关信息
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 22:48
 */
@AllArgsConstructor
@Getter
@Setter
public class AspectInfo {
    /**
     * 优先级
     */
    private int orderIndex;
    /**
     * 切面类
     */
    private DefaultAspect aspectObject;
}
