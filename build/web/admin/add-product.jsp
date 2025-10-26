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
<!--        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            label {
                display: block;
                margin-top: 10px;
            }
            input, select, textarea {
                width: 300px;
                padding: 5px;
                margin-top: 5px;
            }
            .color-block {
                border: 1px solid #ccc;
                padding: 10px;
                margin-top: 15px;
                background: #f9f9f9;
            }
            .size-row, .image-row {
                display: flex;
                gap: 10px;
                margin-top: 5px;
            }
            button {
                margin-top: 10px;
            }
            .remove-btn {
                background: #e74c3c;
                color: white;
                border: none;
                padding: 4px 8px;
                cursor: pointer;
            }
            .remove-btn:hover {
                background: #c0392b;
            }
        </style>-->
    </head>
    <body>

        <%@include file="includes/sidebar_header.jsp" %>

        <!-- MAIN BEGIN-->
        <div class="main">

            <div class="breadcrumb">
                Home <span>/</span> E-commerce <span>/</span> Products
            </div>

            <h1>Nhập thông tin Product</h1>

            <form action="add" method="get" onsubmit="return prepareForm()">
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

                <input type="hidden" id="productColorsJson" name="productColorsJson" value="">


                <button type="submit">Thêm sản phẩm</button>
            </form>

        </div>
        <!-- MAIN END-->


        <script src="js/myscript.js"></script>
    </body>
</html>

