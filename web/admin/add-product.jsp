<%-- 
    Document   : manage-products
    Created on : Oct 25, 2025, 5:42:31 PM
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
        <title>Form nhập Product</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">   
        <link rel="stylesheet" href="css/add-product.css"> 
    </head>
    <body>

        <%@include file="includes/sidebar_header.jsp" %>

        <!-- MAIN BEGIN-->
        <div class="main">

            <div class="breadcrumb">
                <a href="../home">Home</a> <span>/</span> <a href="dashboard.jsp">Admin</a> <span>/</span> Add Product
            </div>

            <h1>Nhập thông tin Product</h1>

            <form id="productForm" 
                  enctype="multipart/form-data"
                  method="post"
                  action="add">

                <label>
                    <span>Tên sản phẩm:</span>
                    <input type="text" name="productName" required />
                </label>

                <label>
                    <span>Category:</span>
                    <select id="categorySelect" onchange="updateSubcategories()">
                        <c:forEach items="${categories}" var="ca">
                            <option value="${ca.categoryId}" >
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
                    <input type="number" name="price" required />
                </label>           

                <label>
                    <span>Mô tả:</span>
                    <textarea name="description"></textarea>
                </label>             

                <hr/>

                <h2>Product Colors</h2>
                <div id="colorsContainer">
                    <!<!-- update by js -->
                </div>

                <button type="button" onclick="addColor()">+ Thêm Màu Mới</button>
                <br/>
                <div id="submit-btn">
                    <button type="button" onclick="handleAjaxSubmission()">Thêm sản phẩm</button>
                </div>
            </form>

        </div>
        <!-- MAIN END-->

        <script src="js/add-product.js"></script>
    </body>
</html>

