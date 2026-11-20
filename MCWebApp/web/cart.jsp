<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="assets/css/global.css">
    <link rel="stylesheet" href="assets/css/cartStyle.css">
    
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
    <div class="container">

        <h2 class="title">Cart</h2>
        
        <c:if test="${not empty sessionScope.success}">
            <div class="alert alert-success">
                ${sessionScope.success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-danger">
                ${sessionScope.error}
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <c:if test="${param.success == '1'}">
            <div class="alert alert-success">
                ✅ Checkout success! Receipt ID: ${param.receiptId}
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <div id="cart-items">

            <c:if test="${empty cartItems}">
                <p class="text-center text-muted">Your cart is empty.</p>
            </c:if>

            <c:forEach var="item" items="${cartItems}">
                <div class="cart-item d-flex align-items-center mb-3">

                    <!-- ✅ IMPORTANT: checkbox has orderVarId + price -->
                    <input type="checkbox"
                           class="select-item me-3"
                           data-ordervar-id="${item.orderVarId}"
                           data-price="${item.totalPrice}" />

                    <img src="${pageContext.request.contextPath}${item.imgPath}"
                         alt="${item.prodName}"
                         class="me-3"
                         style="width:80px; height:auto;">

                    <div class="item-info flex-grow-1">
                        <p class="name mb-1">${item.prodName}</p>
                        <p class="shade mb-1">Shade: ${item.varName}</p>
                        <p class="qty mb-1">Qty: ${item.prodQty}</p>
                        <p class="price fw-bold">RM${item.totalPrice}</p>
                    </div>

                    <form action="${pageContext.request.contextPath}/Cart" method="post">
                        <input type="hidden" name="orderVarId" value="${item.orderVarId}">
                        <button class="btn btn-sm btn-outline-danger remove-btn" type="submit">
                            Remove
                        </button>
                    </form>

                </div>
            </c:forEach>

        </div>

        <!-- Checkout -->
        <div class="checkout-section mt-4 d-flex justify-content-between align-items-center">
            <p id="total" class="fw-bold mb-0">Total: RM0.00</p>

            <!-- ✅ disabled until at least 1 item checked -->
            <button id="checkoutBtn"
                    class="btn btn-primary checkout-btn"
                    data-bs-toggle="modal"
                    data-bs-target="#paymentModal"
                    disabled>
                Checkout
            </button>
        </div>

    </div>
</div>

<!-- Payment Modal -->
<div class="modal fade" id="paymentModal" tabindex="-1" aria-labelledby="paymentModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title" id="paymentModalLabel">Payment</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <!-- ✅ form posts to Checkout -->
                <form id="paymentForm" action="${pageContext.request.contextPath}/Checkout" method="post">
                    <div class="row g-3">

                        <div class="col-md-12">
                            <label class="form-label">Select Bank</label>
                            <select class="form-select" name="payType" id="bankOption" required>
                                <option value="">Choose Bank</option>
                                <option value="Maybank">Maybank</option>
                                <option value="CIMB Bank">CIMB Bank</option>
                                <option value="Public Bank">Public Bank</option>
                            </select>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Bank Username</label>
                            <input type="text" class="form-control" name="bankUsername" required>
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Account Number</label>
                            <input type="text" class="form-control" name="accountNumber" required>
                        </div>

                        <div class="col-md-12">
                            <label class="form-label">Total Amount</label>
                            <!-- ✅ JS will update this value -->
                            <input type="text" class="form-control" id="modalTotal" value="RM 0.00" readonly>
                        </div>

                        <!-- ✅ JS will inject hidden inputs: orderVarIds -->
                        <div id="selectedHiddenInputs"></div>

                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-primary btn-pay" form="paymentForm">Pay</button>
            </div>

        </div>
    </div>
</div>

    <script src="main.js"></script>
    <script src="chart.js"></script>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>