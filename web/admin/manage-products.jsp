<%-- 
    Document   : dashboard
    Created on : Oct 25, 2025, 5:35:21 PM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Products</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">

    </head>
    <body>

        <%@include file="includes/sidebar_header.jsp" %>

        <!-- MAIN BEGIN-->
        <div class="main">
            <div class="breadcrumb">
                Home <span>/</span> Admin <span>/</span> Products
            </div>

            <h1>All products</h1>

            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                <div class="search-bar">
                    <input type="text" placeholder="Search for products">
                </div>
                <a href="add" class="add-btn">+ Add product</a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th><input type="checkbox"></th>
                        <th>PRODUCT NAME</th>
                        <th>CATEGORY</th>
                        <th>SUBCATEGORY</th>
                        <th>PRICE</th>
                        <th>ACTIONS</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${products}" var="p">
                        <tr>
                            <td><input type="checkbox"></td>
                            <td>
                                <div class="product-name">${p.productName}</div>
                                <div class="subtext">ID: ${p.productID}</div>
                            </td>

                            <c:set var="category" value="${categoryDao.getCategoryById(p.categoryID)}"/>        
                            <td>${categoryDao.getCategoryById(category.parentId).categoryName}</td>
                            <td>${category.categoryName}</td>
                            <td>
                                <fmt:setLocale value="en_US" />
                                <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>
                            </td>
                            <td>
                                <a href="edit?productId=${p.productID}" class="btn btn-edit">
                                    <i class="fa-solid fa-pen"></i> Edit
                                </a>
                                <a href="#" class="btn btn-delete">
                                    <i class="fa-solid fa-trash"></i> Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- MAIN END-->

    </body>
</html>

