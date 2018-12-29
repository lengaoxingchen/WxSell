<html>
<head>
    <meta charset="UTF-8">
    <title>卖家商品列表</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/sell/css/style.css">
</head>
<body>
<div class="container-fluid">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered table-condensed">
                <thead>
                <tr>
                    <th>订单id</th>
                    <th>姓名</th>
                    <th>手机号</th>
                    <th>地址</th>
                    <th>金额</th>
                    <th>订单状态</th>
                    <th>支付方式</th>
                    <th>创建时间</th>
                    <th colspan="2">操作</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDtoPage.content as orderDto>
                    <tr>
                        <td>${orderDto.orderId}</td>
                        <td>${orderDto.buyerName}</td>
                        <td>${orderDto.buyerPhone}</td>
                        <td>${orderDto.buyerAddress}</td>
                        <td>${orderDto.orderAmount}</td>
                        <td>${orderDto.getOrderStatusEnum().message}</td>
                        <td>${orderDto.getPayStatusEnum().message}</td>
                        <td>${orderDto.createTime}</td>
                        <td><a href="/sell/seller/order/detail?orderId=${orderDto.orderId}">详情</a></td>
                        <td>
                            <#if orderDto.getOrderStatusEnum().message == "新订单">
                                <a href="/sell/seller/order/cancel?orderId=${orderDto.orderId}">取消</a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <#--分页-->
            <ul class="pagination pull-right">
                <#if currentPage  lte 1>
                    <li class="disabled"><a href="#">上一页</a></li>
                <#else>
                    <li><a href="/sell/seller/order/list?size=${size}o&page=${currentPage-1}">上一页</a></li>
                </#if>
                <#list 1..<orderDtoPage.getTotalPages()+1 as index>
                    <#if currentPage == index>
                        <li class="disabled"><a href="#">${index}</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?size=${size}&page=${index}">${index}</a></li>
                    </#if>
                </#list>
                <#if currentPage gte orderDtoPage.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                <#else>
                    <li><a href="/sell/seller/order/list?size={size}&page=${currentPage + 1}">下一页</a></li>
                </#if>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
