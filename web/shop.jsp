<%-- 
    Document   : Shop
    Created on : Oct 21, 2025, 10:18:56 AM
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Shop</title>

        <!--  Google Font -->
        <link href="https://fonts.googleapis.com/css2?family=Cookie&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700;800;900&display=swap"
              rel="stylesheet">

        <!-- Css Styles -->
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
        <link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
        <link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
        <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
        <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
        <link rel="stylesheet" href="css/style.css" type="text/css">
        <link rel="stylesheet" href="css/mystyle.css" type="text/css">
    </head>

    <body>
        <!-- Header Section Begin  -->
        <%@include file="includes/Header.jsp" %>       
        <!-- Header Section End -->

        <!-- Breadcrumb Begin -->
        <div class="breadcrumb-option">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="breadcrumb__links">
                            <a href="./index.html"><i class="fa fa-home"></i> Home</a>
                            <span>Shop</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Breadcrumb End -->

        <!-- Shop Section Begin -->
        <section class="shop spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-3 col-md-3">
                        <div class="shop__sidebar">

                            <form action="shop" method="post" id="priceForm"> 

                                <div class="sidebar__categories">
                                    <div class="section-title">
                                        <h4>Categories</h4>
                                    </div>
                                    <div class="categories__accordion">
                                        <div class="accordion" id="accordionExample">
                                            <c:forEach items="${categories}" var="ca">
                                                <div class="card">
                                                    <div class="card-heading active">
                                                        <a data-toggle="collapse" data-target="#collapse_${ca.categoryId}" href="shop?categoryId=${ca.categoryId}">
                                                            ${ca.categoryName}
                                                        </a>
                                                    </div>
                                                    <div id="collapse_${ca.categoryId}" class="collapse ${ca.categoryId==1?'show':''}" data-parent="#accordionExample">
                                                        <div class="card-body filter__subCategory">
                                                            <ul>
                                                                <c:forEach items="${subCategoriesMap[ca.categoryId]}" var="sc">
                                                                    <li>
                                                                        <label for="subCat_${sc.categoryId}">
                                                                            <input type="checkbox"  name="chkSubCategory" value="${sc.categoryId}" id="subCat_${sc.categoryId}"
                                                                                   ${categoriesChkList != null && categoriesChkList.contains(sc.categoryId.toString()) ? "checked" : ""}/>
                                                                            <span>${sc.categoryName}</span>
                                                                        </label>
                                                                    </li>
                                                                </c:forEach>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>



                                <!-- Price Filter Begin -->
                                <div class="sidebar__sizes">
                                    <div class="section-title">
                                        <h4>Shop by price</h4>
                                    </div>
                                    <div class="size__list">
                                        <c:forEach items="${prices}" var="p">
                                            <label for="chkPrice_${p.key}">
                                                ${p.value}
                                                <input type="checkbox" id="chkPrice_${p.key}" value="${p.key}" name="chkPrice"
                                                       ${pricesChkList != null && pricesChkList.contains(p.key) ? "checked" : ""}>
                                                <span class="checkmark"></span>
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>
                                <!-- Price Filter End -->
                            </form>
                            <div class="filter-btn-container">
                                <button onclick="submitForm()">Filter</button>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-9 col-md-9">
                        <div class="row">
                            <c:forEach items="${productCards}" var="pc">
                                <div class="col-lg-4 col-md-6">

                                    <!--                                    <form action="product" method="get" id="shop_product_form">
                                                                            <input type="hidden" name="id" value="${pc.productID}" />
                                                                            <input type="hidden" name="color_radio" value="${pc.productColorId}" />-->

                                    <div class="product__item">
                                        <div class="product__item__pic set-bg" data-setbg="${pc.mainImage}">
                                            <ul class="product__hover">
                                                <li><a href="${pc.mainImage}" class="image-popup"><span class="arrow_expand"></span></a></li>
                                                <li><a href="#"><span class="icon_heart_alt"></span></a></li>
                                                <li><a href="#"><span class="icon_bag_alt"></span></a></li>
                                            </ul>
                                        </div>
                                        <div class="product__item__text">
                                            <h6><a href="product?id=${pc.productID}&color_radio=${pc.productColorId}">${pc.productName}</a></h6>
                                            <div class="rating">
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                            </div>
                                            <div class="product__price">
                                                <fmt:setLocale value="en_US" />
                                                <fmt:formatNumber value="${pc.price}" type="number" groupingUsed="true"/>
                                            </div>
                                        </div>
                                    </div>
                                    <!--                                    </form>-->
                                </div>  
                            </c:forEach>

                            <div class="col-lg-12 text-center">
                                <div class="pagination__option">
                                    <a href="#">1</a>
                                    <a href="#">2</a>
                                    <a href="#">3</a>
                                    <a href="#"><i class="fa fa-angle-right"></i></a>
                                </div>
                            </div>



                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Shop Section End -->


        <!-- Footer Section Begin -->   
        <%@include file="includes/Footer.jsp" %>   
        <!-- Footer Section End -->

        <!-- Js Plugins -->
        <script src="js/jquery-3.3.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.magnific-popup.min.js"></script>
        <script src="js/jquery-ui.min.js"></script>
        <script src="js/mixitup.min.js"></script>
        <script src="js/jquery.countdown.min.js"></script>
        <script src="js/jquery.slicknav.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/jquery.nicescroll.min.js"></script>
        <script src="js/main.js"></script>
        <script src="js/myscript.js"></script>
    </body>
</html>

