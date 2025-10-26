<%-- 
    Document   : dashboard
    Created on : Oct 25, 2025, 5:35:21 PM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>All Products - Admin Dashboard</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />
        <link rel="stylesheet" href="css/mystyle.css">

    </head>
    <body>
        
        <%@include file="includes/sidebar_header.jsp" %>

        <!-- MAIN BEGIN-->
        <div class="main">
            <div class="breadcrumb">
                Home <span>/</span> E-commerce <span>/</span> Products
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
                        <th>TECHNOLOGY</th>
                        <th>ID</th>
                        <th>PRICE</th>
                        <th>ACTIONS</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input type="checkbox"></td>
                        <td>
                            <div class="product-name">Education Dashboard</div>
                            <div class="subtext">Html templates</div>
                        </td>
                        <td>Angular</td>
                        <td>#194556</td>
                        <td>$149</td>
                        <td>
                            <button class="btn btn-edit"><i class="fa-solid fa-pen"></i> Edit</button>
                            <button class="btn btn-delete"><i class="fa-solid fa-trash"></i> Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"></td>
                        <td>
                            <div class="product-name">React UI Kit</div>
                            <div class="subtext">Html templates</div>
                        </td>
                        <td>React JS</td>
                        <td>#623232</td>
                        <td>$129</td>
                        <td>
                            <button class="btn btn-edit"><i class="fa-solid fa-pen"></i> Edit</button>
                            <button class="btn btn-delete"><i class="fa-solid fa-trash"></i> Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"></td>
                        <td>
                            <div class="product-name">Education Dashboard</div>
                            <div class="subtext">Html templates</div>
                        </td>
                        <td>Angular</td>
                        <td>#194356</td>
                        <td>$149</td>
                        <td>
                            <button class="btn btn-edit"><i class="fa-solid fa-pen"></i> Edit</button>
                            <button class="btn btn-delete"><i class="fa-solid fa-trash"></i> Delete</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- MAIN END-->

    </body>
</html>

