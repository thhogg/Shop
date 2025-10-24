<%-- 
    Document   : Product
    Created on : Oct 21, 2025, 5:23:09 PM
    Author     : Leo
--%>

<%-- 
    Document   : Home
    Created on : Oct 13, 2025, 9:51:37 AM
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
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Home</title>

        <!-- Google Font -->
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
    </head>

    <body>
        <!-- Header Section Begin -->        
        <%@include file="includes/Header.jsp" %>      
        <!-- Header Section End -->


        <!-- Breadcrumb Begin -->
        <div class="breadcrumb-option">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="breadcrumb__links">
                            <a href="./index.html"><i class="fa fa-home"></i> Home</a>
                            <a href="#">Women’s </a>
                            <span>${product.productName}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Breadcrumb End -->

        <!-- Product Details Section Begin -->
        <section class="product-details spad">
            <div class="container">
                <div class="row">
                    <!--Product Image Begin -->
                    <div class="col-lg-6">
                        <div class="product__details__pic">
                            <div class="product__details__pic__left product__thumb nice-scroll">                             
                                <c:forEach items="${images}" var="i">
                                    <a class="pt active" href="#product-${i.imageID}">
                                        <img src="${i.imageUrl}" alt="" />
                                    </a>
                                </c:forEach>
                            </div>
                            <div class="product__details__slider__content">
                                <div class="product__details__pic__slider owl-carousel">
                                    <c:forEach items="${images}" var="i">
                                        <img
                                            data-hash="product-${i.imageID}"
                                            class="product__big__img"
                                            src="${i.imageUrl}"
                                            alt=""
                                            />
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--Product Image End -->

                    <!--Product Content Begin -->
                    <div class="col-lg-6">
                        <div class="product__details__text">
                            <h3>
                                ${product.productName}
                            </h3>
                            <div class="rating">
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <span>( 138 reviews )</span>
                            </div>
                            <div class="product__details__price">
                                <fmt:setLocale value="en_US" />
                                <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>
                                <span>
                                    <fmt:formatNumber value="${product.price*1.2}" type="number" groupingUsed="true"/>
                                </span>
                            </div>
                            <p>
                                ${product.description}
                            </p>

                            <c:forEach items="${images}" var="i">
                                <c:if test="${i.main==true}">
                                    <c:set var="mainImage" value="${i.imageUrl}"/>
                                </c:if>
                            </c:forEach>

                            <form action="cart" method="post">
                                <input type="text" name="cartItem_image" value="${mainImage}" hidden="">
                                <input type="number" name="cartItem_price" value="${product.price}" hidden="">
                                <input type="text" name="cartItem_productName" value="${product.productName}" hidden="">
                                <input type="text" name="cartItem_productVariantID" value="${productVariantIdRadio}" hidden="">

                                <div class="product__details__button">
                                    <div class="quantity">
                                        <span>Quantity:</span>
                                        <div class="pro-qty">
                                            <input type="text" value="1" name="cartItem_quantity"/>
                                        </div>
                                    </div>
                                    <button type="submit" class="cart-btn"><span class="icon_bag_alt"></span> Add to cart</button>
                                    <ul>
                                        <li>
                                            <a href="#"><span class="icon_heart_alt"></span></a>
                                        </li>
                                        <li>
                                            <a href="#"><span class="icon_adjust-horiz"></span></a>
                                        </li>
                                    </ul>
                                </div>

                            </form>            

                            <div class="product__details__widget">
                                <ul>
                                    <li>
                                        <span>Availability:</span>
                                        <div class="stock__checkbox">
                                            <label for="stockin">
                                                In Stock
                                                <input type="checkbox" id="stockin" />
                                                <span class="checkmark"></span>
                                            </label>
                                        </div>
                                    </li>
                                    <form action="product" method="get" id="product__colorSizeForm">
                                        <input type="hidden" name="id" value="${product.productID}" />
                                        <li>
                                            <span>Available color:</span>
                                            <div class="color__checkbox">

                                                <c:forEach items="${product.productColors}" var="pc">
                                                    <label for="color_${pc.colorID}">
                                                        <input type="radio" name="color_radio" 
                                                               id="color_${pc.colorID}" value="${pc.productColorID}"
                                                               onchange="submitProductForm()"
                                                               ${productColorIdRadio==pc.productColorID?"checked":""}/>
                                                        <span class="checkmark" style="background: ${pc.color.hexCode};"></span>
                                                    </label>
                                                </c:forEach>

                                            </div>
                                        </li>
                                        <li>
                                            <span>Available size:</span>
                                            <div class="size__btn">
                                                <c:forEach items="${variants}" var="v">
                                                    <label for="${v.size.name}-btn" class="${productVariantIdRadio==v.productVariantID?'active':''}">
                                                        <input type="radio" id="${v.size.name}-btn"
                                                               name="size_radio"
                                                               value="${v.productVariantID}"
                                                               ${productVariantIdRadio==v.productVariantID?'checked':''}
                                                               onchange="submitProductForm()"/>
                                                        ${v.size.name}
                                                    </label>
                                                </c:forEach>
                                            </div>
                                        </li>
                                    </form>
                                    <li>
                                        <span>Quantity:</span>
                                        <p>${availability}</p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <!--Product Content End -->

                    <!--Description - Specificaion - Reviews Begin -->
                    <div class="col-lg-12">
                        <div class="product__details__tab">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="nav-item">
                                    <a
                                        class="nav-link active"
                                        data-toggle="tab"
                                        href="#tabs-1"
                                        role="tab"
                                        >Description</a
                                    >
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" data-toggle="tab" href="#tabs-2" role="tab"
                                       >Specification</a
                                    >
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" data-toggle="tab" href="#tabs-3" role="tab"
                                       >Reviews ( 2 )</a
                                    >
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="tabs-1" role="tabpanel">
                                    <h6>Description</h6>
                                    <p>
                                        Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit
                                        aut loret fugit, sed quia consequuntur magni dolores eos qui
                                        ratione voluptatem sequi nesciunt loret. Neque porro lorem
                                        quisquam est, qui dolorem ipsum quia dolor si. Nemo enim ipsam
                                        voluptatem quia voluptas sit aspernatur aut odit aut loret
                                        fugit, sed quia ipsu consequuntur magni dolores eos qui ratione
                                        voluptatem sequi nesciunt. Nulla consequat massa quis enim.
                                    </p>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean
                                        commodo ligula eget dolor. Aenean massa. Cum sociis natoque
                                        penatibus et magnis dis parturient montes, nascetur ridiculus
                                        mus. Donec quam felis, ultricies nec, pellentesque eu, pretium
                                        quis, sem.
                                    </p>
                                </div>
                                <div class="tab-pane" id="tabs-2" role="tabpanel">
                                    <h6>Specification</h6>
                                    <p>
                                        Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit
                                        aut loret fugit, sed quia consequuntur magni dolores eos qui
                                        ratione voluptatem sequi nesciunt loret. Neque porro lorem
                                        quisquam est, qui dolorem ipsum quia dolor si. Nemo enim ipsam
                                        voluptatem quia voluptas sit aspernatur aut odit aut loret
                                        fugit, sed quia ipsu consequuntur magni dolores eos qui ratione
                                        voluptatem sequi nesciunt. Nulla consequat massa quis enim.
                                    </p>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean
                                        commodo ligula eget dolor. Aenean massa. Cum sociis natoque
                                        penatibus et magnis dis parturient montes, nascetur ridiculus
                                        mus. Donec quam felis, ultricies nec, pellentesque eu, pretium
                                        quis, sem.
                                    </p>
                                </div>
                                <div class="tab-pane" id="tabs-3" role="tabpanel">
                                    <h6>Reviews ( 2 )</h6>
                                    <p>
                                        Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit
                                        aut loret fugit, sed quia consequuntur magni dolores eos qui
                                        ratione voluptatem sequi nesciunt loret. Neque porro lorem
                                        quisquam est, qui dolorem ipsum quia dolor si. Nemo enim ipsam
                                        voluptatem quia voluptas sit aspernatur aut odit aut loret
                                        fugit, sed quia ipsu consequuntur magni dolores eos qui ratione
                                        voluptatem sequi nesciunt. Nulla consequat massa quis enim.
                                    </p>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean
                                        commodo ligula eget dolor. Aenean massa. Cum sociis natoque
                                        penatibus et magnis dis parturient montes, nascetur ridiculus
                                        mus. Donec quam felis, ultricies nec, pellentesque eu, pretium
                                        quis, sem.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--Description - Specificaion - Reviews End -->
                </div>

                <!--Related Products Section Begin-->
                <div class="row">
                    <div class="col-lg-12 text-center">
                        <div class="related__title">
                            <h5>RELATED PRODUCTS</h5>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="product__item">
                            <div
                                class="product__item__pic set-bg"
                                data-setbg="img/product/related/rp-1.jpg"
                                >
                                <div class="label new">New</div>
                                <ul class="product__hover">
                                    <li>
                                        <a href="img/product/related/rp-1.jpg" class="image-popup"
                                           ><span class="arrow_expand"></span
                                            ></a>
                                    </li>
                                    <li>
                                        <a href="#"><span class="icon_heart_alt"></span></a>
                                    </li>
                                    <li>
                                        <a href="#"><span class="icon_bag_alt"></span></a>
                                    </li>
                                </ul>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">Buttons tweed blazer</a></h6>
                                <div class="rating">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </div>
                                <div class="product__price">$ 59.0</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="product__item">
                            <div
                                class="product__item__pic set-bg"
                                data-setbg="img/product/related/rp-2.jpg"
                                >
                                <ul class="product__hover">
                                    <li>
                                        <a href="img/product/related/rp-2.jpg" class="image-popup"
                                           ><span class="arrow_expand"></span
                                            ></a>
                                    </li>
                                    <li>
                                        <a href="#"><span class="icon_heart_alt"></span></a>
                                    </li>
                                    <li>
                                        <a href="#"><span class="icon_bag_alt"></span></a>
                                    </li>
                                </ul>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">Flowy striped skirt</a></h6>
                                <div class="rating">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </div>
                                <div class="product__price">$ 49.0</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="product__item">
                            <div
                                class="product__item__pic set-bg"
                                data-setbg="img/product/related/rp-3.jpg"
                                >
                                <div class="label stockout">out of stock</div>
                                <ul class="product__hover">
                                    <li>
                                        <a href="img/product/related/rp-3.jpg" class="image-popup"
                                           ><span class="arrow_expand"></span
                                            ></a>
                                    </li>
                                    <li>
                                        <a href="#"><span class="icon_heart_alt"></span></a>
                                    </li>
                                    <li>
                                        <a href="#"><span class="icon_bag_alt"></span></a>
                                    </li>
                                </ul>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">Cotton T-Shirt</a></h6>
                                <div class="rating">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </div>
                                <div class="product__price">$ 59.0</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="product__item">
                            <div
                                class="product__item__pic set-bg"
                                data-setbg="img/product/related/rp-4.jpg"
                                >
                                <ul class="product__hover">
                                    <li>
                                        <a href="img/product/related/rp-4.jpg" class="image-popup"
                                           ><span class="arrow_expand"></span
                                            ></a>
                                    </li>
                                    <li>
                                        <a href="#"><span class="icon_heart_alt"></span></a>
                                    </li>
                                    <li>
                                        <a href="#"><span class="icon_bag_alt"></span></a>
                                    </li>
                                </ul>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">Slim striped pocket shirt</a></h6>
                                <div class="rating">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </div>
                                <div class="product__price">$ 59.0</div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--Related Products Section End-->
            </div>
        </section>
        <!-- Product Details Section End -->




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

