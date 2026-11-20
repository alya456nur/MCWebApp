<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Shopping</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

        <!-- Custom CSS -->
        <link rel="stylesheet" href="assets/css/global.css">
        <link rel="stylesheet" href="assets/css/dashboard.css">

        <!-- Sidebar CSS -->
        <link rel="stylesheet" href="assets/css/sidebar.css">

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    </head>
    <body>

        <!-- Sidebar -->
        <nav class="navbar navbar-dark bg-dark fixed-top">
            <div class="container-fluid">
                <h2>Madeline Cosmetic</h2>
                <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasDarkNavbar">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="offcanvas offcanvas-end text-bg-dark" id="offcanvasDarkNavbar">
                    <div class="offcanvas-header">
                        <h5 class="offcanvas-title">Madeline Cosmetic</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
                    </div>

                    <div class="offcanvas-body">
                        <ul class="navbar-nav">

                            <!-- Navigation -->
                            <li class="nav-item">
                                <p>Navigation</p>
                                <ul class="navbar-nav">
                                    <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/CustDashboard"><i class="fa-solid fa-shop"></i> Shop</a></li>
                                    <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/Cart"><i class="fa-solid fa-cart-arrow-down"></i> Cart</a></li>
                                    <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/Order"><i class="fa-solid fa-truck"></i> Your Orders</a></li>
                                    <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/History"><i class="fa-solid fa-clock"></i> Shopping History</a></li>
                                </ul>
                            </li>

                        </ul>
                    </div>

                    <div class="offcanvas-footer">
                        <ul class="navbar-nav">

                            <!-- Footer -->
                            <li class="nav-item">
                                <ul class="navbar-nav footer">
                                    <a href="${pageContext.request.contextPath}/Logout">
                                        <i class="fa-solid fa-arrow-right-from-bracket"></i> Logout
                                    </a>
                                </ul>
                            </li>

                        </ul>
                    </div>

                </div>
            </div>
        </nav>

        <!-- MAIN CONTENT AREA -->
        <div id="content" style="margin-top: 80px; padding: 20px;">    
            <div class="container my-4">

                <!-- Page Title -->
                <div class="title-container mb-5 text-center">
                    <h2>Shop</h2>
                    <div class="container mb-5">
                        <div class="row justify-content-center g-2 dashboard-navigate">

                            <div class="col-6 col-md-3 quick-container">
                                <a class="btn btn-primary w-100 quick-btn"
                                   href="${pageContext.request.contextPath}/Cart">
                                    <i class="fa-solid fa-cart-arrow-down"></i> Cart
                                </a>
                            </div>

                            <div class="col-6 col-md-3 quick-container">
                                <a class="btn btn-primary w-100 quick-btn"
                                   href="${pageContext.request.contextPath}/Order">
                                    <i class="fa-solid fa-truck"></i> Orders
                                </a>
                            </div>

                            <div class="col-6 col-md-3 quick-container">
                                <a class="btn btn-primary w-100 quick-btn"
                                   href="${pageContext.request.contextPath}/History">
                                    <i class="fa-solid fa-clock"></i> History
                                </a>
                            </div>

                            <div class="col-6 col-md-3 quick-container">
                                <a class="btn btn-primary w-100 quick-btn"
                                   href="${pageContext.request.contextPath}/EditCust">
                                    <i class="fa-solid fa-user"></i> Edit Info
                                </a>
                            </div>

                        </div>
                    </div>

                    <!-- Eyeshadows Section -->
                    <div class="products-card p-3 mb-5 border rounded shadow-sm">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h3>Eyeshadows</h3>
                        </div>

                        <div class="row g-4">
                            <c:forEach var="item" items="${eyesProducts}">
                                <div class="col-md-3">
                                    <div class="card h-100">
                                        <img src="${pageContext.request.contextPath}${item.imgPath}" 
                                             class="card-img-top" alt="${item.prodName}">
                                        <div class="card-body text-center">
                                            <h5 class="card-title">${item.prodName}</h5>
                                            <p class="card-text">${item.varName}</p>
                                            <p class="card-text price">RM${item.varPrice}</p>
                                            <button type="button" class="btn addCart" data-variant-id="${item.varId}">Add To Cart</button>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- Lip Tints Section -->
                    <div class="products-card p-3 mb-5 border rounded shadow-sm">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h3>Lip Tints</h3>
                        </div>

                        <div class="row g-4">
                            <c:forEach var="item" items="${lipsProducts}">
                                <div class="col-md-3">
                                    <div class="card h-100">
                                        <img src="${pageContext.request.contextPath}${item.imgPath}" class="card-img-top" alt="${item.prodName}">

                                        <div class="card-body text-center">
                                            <h5 class="card-title">${item.prodName}</h5>

                                            <c:if test="${not empty item.variations}">
                                                <div class="me-3 mb-2">
                                                    <select class="form-select form-select-sm variantSelect">
                                                        <c:forEach var="var" items="${item.variations}">
                                                            <option value="${var.varId}">
                                                                ${var.varName} - RM${var.varPrice}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </c:if>

                                            <!-- ✅ button reads value from the select in JS -->
                                            <button type="button" class="btn addCart addCartLips" >Add To Cart</button>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>


                </div>
            </div>
            <!-- ✅ Added-to-Cart / Message Modal -->
            <div class="modal fade" id="msgModal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h5 class="modal-title" id="msgModalTitle">Message</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>

                        <div class="modal-body" id="msgModalBody">
                            ...
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">OK</button>
                        </div>

                    </div>
                </div>
            </div>

            <script src="${pageContext.request.contextPath}/main.js"></script>
            <script src="${pageContext.request.contextPath}/chart.js"></script>

            <!-- Bootstrap JS -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
