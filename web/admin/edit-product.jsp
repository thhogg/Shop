<%-- 
    Document   : edit-product
    Created on : Oct 29, 2025, 8:57:52 AM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Chỉnh sửa Product</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css" />   
        <link rel="stylesheet" href="css/add-product.css" /> 
    </head>
    <body>

        <%@include file="includes/sidebar_header.jsp" %>

        <!-- MAIN BEGIN-->
        <div class="main">

            <div class="breadcrumb">
                <a href="../home">Home</a> <span>/</span> <a href="products">Products</a> <span>/</span> Edit Product
            </div>

            <h1>Chỉnh sửa thông tin Product</h1>

            <!-- productId là id sản phẩm được truyền vào -->
            <form id="productForm" 
                  enctype="multipart/form-data"
                  method="post"
                  action="edit">

                <input type="hidden" name="productId" value="${product.productID}" />

                <label>
                    <span>Tên sản phẩm:</span>
                    <input type="text" name="productName" required value="${product.productName}" />
                </label>

                <label>
                    <span>Category:</span>
                    <select id="categorySelect" onchange="updateSubcategories()">
                        <c:forEach items="${categories}" var="ca">
                            <option value="${ca.categoryId}" ${ca.categoryId == product.categoryID ? 'selected' : ''}>
                                ${ca.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </label>

                <label>
                    <span>Subcategory:</span>
                    <select id="subcategorySelect" name="subcategoryId">
                        <!-- update by js -->
                    </select>
                </label>             

                <label>
                    <span>Giá:</span>
                    <input type="number" name="price" required value="${product.price}" />
                </label>           

                <label>
                    <span>Mô tả:</span>
                    <textarea name="description">${product.description}</textarea>
                </label>             

                <hr/>

                <h2>Product Colors</h2>
                <div id="colorsContainer">
                    <!-- Khối màu được tạo bởi JS dựa trên productColors -->
                </div>

                <button type="button" onclick="addColor()">+ Thêm Màu Mới</button>
                <br/>
                <div id="submit-btn">
                    <button type="button" onclick="handleAjaxSubmission()">Cập nhật sản phẩm</button>
                </div>
            </form>

        </div>
        <!-- MAIN END-->

        <script>
            // Biến toàn cục chứa dữ liệu màu sắc, kích cỡ, và biến thể sản phẩm hiện tại
            let productColors = ${productColorsJson};
        </script>
        <script src="js/edit-product.js"></script>

    </body>
</html>
