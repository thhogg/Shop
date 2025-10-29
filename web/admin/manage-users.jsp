<%-- 
    Document   : manage-users
    Created on : Oct 29, 2025, 12:12:30 AM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Users</title>
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
                <a href="add" class="add-btn">+ Add user</a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th><input type="checkbox"></th>
                        <th>USER NAME</th>
                        <th>EMAIL</th>
                        <th>ROLE</th>
                        <th>DATE CREATED</th>
                        <th>ACTIONS</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="u">
                        <tr>
                            <td><input type="checkbox"></td>
                            <td>
                                <div class="product-name">${u.userName}</div>
                                <div class="subtext">ID: ${u.userID}</div>
                            </td>        
                            <td>${u.email}</td>
                            <td>${u.role==0?"Admin":"Customer"}</td>
                            <td><fmt:formatDate value="${u.createdAt}" pattern="dd/MM/yyyy" /></td>
                            <td>
                                <button class="btn btn-edit"><i class="fa-solid fa-pen"></i> Edit</button>
                                <button class="btn btn-delete"><i class="fa-solid fa-trash"></i> Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- MAIN END-->

    </body>
</html>
