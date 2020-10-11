## 引入页面
```html
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8"/>
    <title>Title</title>
</head>
<body>
    <!--引入indexInner页面-->
    <div th:include="indexInner"></div>
    
    <!--引入indexInner的top部分-->
    <!--indexInner.html
        <div th:fragment="top">我是头部</div>
    -->
    <div th:include="indexInner:top"></div>
</body>
</html>
```