<%-- 
    Document   : sidebar_header
    Created on : Oct 25, 2025, 5:48:03 PM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- SIDEBAR BEGIN-->
        <div class="sidebar">
            <h2>Fashion</h2>
            <ul>
                <li><a href="dashboard.jsp"><i class="fa-solid fa-gauge"></i> Dashboard</a></li>
                <li><a href="users"><i class="fa-solid fa-users"></i> Users</a></li>
                <li><a href="#"><i class="fa-solid fa-layer-group"></i> Product Attributes</a></li>
                <li><a href="products"><i class="fa-solid fa-box"></i> Products</a></li>
                <li><a href="#"><i class="fa-solid fa-right-to-bracket"></i> Sign In</a></li>
                <li><a href="#"><i class="fa-solid fa-user-plus"></i> Sign Up</a></li>
                <li><a href="#"><i class="fa-solid fa-circle-question"></i> Help</a></li>
            </ul>
        </div>
        <!-- SIDEBAR END-->

        <!-- HEADER BEGIN -->
        <div class="header">
            <div class="search">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" placeholder="Search">
            </div>
            <div class="icons">
                <i class="fa-solid fa-bell"></i>
                <i class="fa-solid fa-gear"></i>
                <div class="user-info">
                    <i class="fa-solid fa-user"></i>
                    <span>Admin</span>
                </div>
            </div>
        </div>
        <!-- HEADER END -->
    </body>
</html>
