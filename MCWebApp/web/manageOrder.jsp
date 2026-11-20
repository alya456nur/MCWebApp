<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Order</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="assets/css/global.css">
    <link rel="stylesheet" href="assets/css/manageOrderStyle.css">
    
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
            
            <div class="offcanvas offcanvas-end text-bg-dark"
                id="offcanvasDarkNavbar"
                aria-labelledby="offcanvasDarkNavbarLabel">

            <div class="offcanvas-header">
                <h5 class="offcanvas-title" id="offcanvasDarkNavbarLabel">
                    Madeline Cosmetic
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
            </div>

            <div class="offcanvas-body">
                <ul class="navbar-nav">

                    <!-- Navigation -->
                    <li class="nav-item">
                        <p>Navigation</p>
                        <ul class="navbar-nav">
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/StaffDashboard"><i class="fa-solid fa-shop"></i> Summary</a></li>
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/Ledger"><i class="fa-solid fa-boxes-packing"></i> Ledger</a></li>
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/Inventory"><i class="fa-solid fa-cart-arrow-down"></i> Inventory</a></li>
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/ManageOrder"><i class="fa-solid fa-truck"></i> Manage Order</a></li>
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/Staff"><i class="fa-solid fa-id-card-clip"></i> Staff</a></li>
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
        <div class="main-container mb-4 text-center">
            <h2>Manage Orders</h2>
        </div>
        
        <div class="row mb-4 g-3 filter-container">
            <!-- Status filter dropdown -->
            <form action="ManageOrder" method="get" class="variation-container">

                <select name="status"
                        class="form-select form-select-sm variation-select"
                        onchange="this.form.submit()">

                    <option value="">All Orders</option>

                    <option value="Pending"
                        ${filterStatus == 'Pending' ? 'selected' : ''}>
                        Pending
                    </option>

                    <option value="Completed"
                        ${filterStatus == 'Completed' ? 'selected' : ''}>
                        Completed
                    </option>

                    <option value="Cancelled"
                        ${filterStatus == 'Cancelled' ? 'selected' : ''}>
                        Cancelled
                    </option>

                </select>

            </form>

        </div>

        <c:if test="${not empty sessionScope.success}">
            <div class="alert alert-success">
                ${sessionScope.success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>

        <!-- Order Cards List -->
        <div class="row g-3 card-list">
            <c:choose>  
                <c:when test="${empty orders}">
                    <div class="col-12">
                        <div class="text-center py-5 text-muted">
                            There's no orders
                        </div>
                    </div>
                </c:when>

                <c:otherwise>
                    <c:forEach var="order" items="${orders}">
                        <div class="col-md-6">
                            <div class="order-card h-100">

                                <!-- Header -->
                                <div class="d-flex justify-content-between order-header mb-2">
                                    <div class="fw-bold">${order.orderId}</div>
                                    <c:set var="orderStatus" value="${order.orderItem[0].status}" />
                                    <div class="order-status-${orderStatus.toLowerCase()}">${orderStatus}</div>
                                </div>

                                <!-- Order Details -->
                                <div class="order-details mb-3">
                                    <div><strong>Date:</strong> ${order.receiptDate}</div>
                                    <div><strong>Payment type:</strong> ${order.payType}</div>
                                    <div><strong>Grand total:</strong> RM${order.grandTotal}</div>
                                    <div><strong>Items:</strong></div>

                                    <c:forEach var="item" items="${order.orderItem}">
                                        <div>${item.sku} x${item.prodQty} ${item.varName}</div>
                                    </c:forEach>
                                </div>

                                <!-- Buttons -->
                                <div class="d-flex justify-content-between mt-2">
                                    <a href="${pageContext.request.contextPath}/ViewOrder?orderId=${order.orderId}"
                                       class="order-btn order-btn-view">
                                        View
                                    </a>
                                </div>

                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

    <script src="main.js"></script>
    <script src="chart.js"></script>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
