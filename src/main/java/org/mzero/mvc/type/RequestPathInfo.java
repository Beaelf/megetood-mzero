package org.mzero.mvc.type;

import lombok.*;

/**
 * 存储http请求路径和请求方法
 *
 * @author chengdong.lei@hand-china.com 2020/08/16 19:44
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RequestPathInfo {
    /**
     * http请求方法
     */
    private String httpMethod;

    /**
     * http请求路径
     */
    private String httpPath;
}
