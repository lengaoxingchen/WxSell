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
                    <th>商品id</th>
                    <th>名称</th>
                    <th>图片</th>
                    <th>单价</th>
                    <th>库存</th>
                    <th>描述</th>
                    <th>类目</th>
                    <th>创建时间</th>
                    <th>修改时间</th>
                    <th colspan="2">操作</th>
                </tr>
                </thead>
                <tbody>
                <#list productInfoPage.content as productInfo>
                    <tr>
                        <td>${productInfo.productId}</td>
                        <td>${productInfo.productName}</td>
                        <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                        <td>${productInfo.productPrice}</td>
                        <td>${productInfo.productStock}</td>
                        <td>${productInfo.productDescription}</td>
                        <td>${productInfo.categoryType}</td>
                        <td>${productInfo.createTime}</td>
                        <td>${productInfo.updateTime}</td>
                        <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a></td>
                        <td>
                            <#if productInfo.getProductStatusEnum().message == "在架">
                                <a href="/sell/seller/product/offSale?productId=${productInfo.productId}">下架</a>
                            <#else>
                                <a href="/sell/seller/product/onSale?productId=${productInfo.productId}">上架</a>
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
                    <li><a href="/sell/seller/product/index?size=${size}o&page=${currentPage-1}">上一页</a></li>
                </#if>
                <#list 1..< productInfoPage.getTotalPages()+1 as index>
                    <#if currentPage == index>
                        <li class="disabled"><a href="#">${index}</a></li>
                    <#else>
                        <li><a href="/sell/seller/product/index?size=${size}&page=${index}">${index}</a></li>
                    </#if>
                </#list>
                <#if currentPage gte productInfoPage.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                <#else>
                    <li><a href="/sell/seller/product/index?size={size}&page=${currentPage + 1}">下一页</a></li>
                </#if>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
