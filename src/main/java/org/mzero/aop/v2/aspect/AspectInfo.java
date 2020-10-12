package org.mzero.aop.v2.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mzero.aop.v2.PointcutLocator;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 22:48
 */
@AllArgsConstructor
@Getter
@Setter
public class AspectInfo {
    private int orderIndex;
    private DefaultAspect aspectObject;
    private PointcutLocator pointcutLocator;
}
