<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detailed Orders</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="assets/css/global.css">
    <link rel="stylesheet" href="assets/css/orderStyle.css">
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
    <div class="container">

        <h2 class="title">Detailed Orders</h2>

        <div class="row g-3 order-list align-items-stretch">

            <c:if test="${empty orders}">
                <p class="text-center text-muted">There's no selected order.</p>
            </c:if>

            <c:forEach var="order" items="${orders}">
                <div class="col-12 col-md-6 d-flex">
                    <div class="order-card h-100 w-100">

                        <!-- Order Header -->
                        <div class="order-header">
                            <h3>${order.orderNo}</h3>
                            <span class="status-text ${order.status.toLowerCase()}">${order.status}</span>
                        </div>

                        <!-- Customer info -->
                        <div class="customer-row">
                            <div class="cust-id">
                                <p class="cust-name">Customer name: ${order.custName}</p>
                                <p class="cust-email">Customer email: ${order.custEmail}</p>
                                <p class="cust-add">Customer address: ${order.custAdd}</p>
                                <p class="cust-phone">Customer phone:${order.custPhone}</p>
                            </div>
                            <div class="order-divider"></div>
                        </div>

                        <!-- Order Items -->
                        <c:forEach var="item" items="${order.orderItem}">
                            <div class="item-row">
                                <div class="order-id">
                                    <p class="item-name">${item.prodName} - ${item.varName}</p>
                                    <p>Qty: ${item.prodQty}</p>
                                    <p>Total: RM${item.totalPrice}</p>
                                </div>
                                <div class="order-divider"></div>
                            </div>
                        </c:forEach>

                        <!-- Totals -->
                        <div class="order-totals">
                            <p class="GST">GST: RM${order.gst}</p>
                            <p class="sub_total">Subtotal: RM${order.subTotal}</p>
                            <p class="grand_total">Grand total: RM${order.grandTotal}</p>
                        </div>

                        <!-- Action Buttons -->
                        <c:choose>
                            <c:when test="${order.status == 'Pending'}">
                                <div class="order-action-buttons">
                                    <form action="${pageContext.request.contextPath}/ViewOrder" method="post">
                                        <input type="hidden" name="orderId" value="${orders[0].orderId}">
                                        <input type="hidden" name="action" value="cancel">
                                        <button type="submit" class="btn btn-cancel">
                                            Cancel Order
                                        </button>
                                    </form>

                                    <form action="${pageContext.request.contextPath}/ViewOrder" method="post">
                                        <input type="hidden" name="orderId" value="${orders[0].orderId}">
                                        <input type="hidden" name="action" value="process">
                                        <button type="submit" class="btn btn-small">
                                            Process Order
                                        </button>
                                    </form>
                                </div>
                            </c:when>           
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<script src="main.js"></script>
<script src="chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
