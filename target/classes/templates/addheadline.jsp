<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--

  descript

  @author Administrator 2020/08/17 4
5 
--><html>
  <head>
    <title>add head line</title>
  </head>
  <body>
    <table>
      <h2 style="color: deepskyblue">表单提交：</h2>
      <form id="headlineInfo" method="post" action="/megetood-spring/headline/add">
        头条说明：<input type="text" name="lineName"></br>
        头条连接：<input type="text" name="lineName"></br>
        头条图片：<input type="text" name="lineName"></br>
        头条优先级：<input type="text" name="lineName"></br>
        结果：<h3>状态玛：${result.code}</h3></br>
        <input type="submit" value="提交">
      </form>
    </table>
  </body>
</html>
