<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>History</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="assets/css/global.css">
    <link rel="stylesheet" href="assets/css/history.css">
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
    <div class="container my-4">

        <div class="main-container mb-5 text-center">
            <h2>Shopping History</h2>

            <!-- Filter Section -->
            <form action="History" method="get" class="row mb-4 g-3 filter-container">
                <div class="col-6 col-md-3 date-filter">
                    <input type="date" name="startDate" class="form-control">
                </div>
                <div class="col-6 col-md-3 date-filter">
                    <input type="date" name="endDate" class="form-control">
                </div>
                <div class="col-6 col-md-2 date-filter">
                    <button type="submit" class="btn btn-primary w-100 btn-filter">
                        Filter
                    </button>
                </div>
            </form>
            <c:if test="${not empty filterStart}">
                <p class="filterDate">
                    Filter applied: ${filterStart} to ${filterEnd}
                </p>
            </c:if>

            <div class="row g-3 order-list align-items-stretch">

                <c:if test="${empty receipt}">
                    <p class="text-center text-muted">No shopping history found.</p>
                </c:if>

                <c:forEach var="receipt" items="${receipt}">
                    <div class="col-12 col-md-6 d-flex">
                        <div class="receipt-card h-100 w-100">

                            <h3>${receipt.custName}</h3>

                            <div class="receipt-info">
                                <span>RECEIPT ID: ${receipt.receiptId}</span>
                                <span>PAYMENT METHOD: ${receipt.payType}</span>
                                <span>RECEIPT DATE: ${receipt.receiptDate}</span>
                                <span>RECEIPT TOTAL: RM${receipt.grandTotal}</span>
                            </div>

                            <table>
                                <tr>
                                    <th>No.</th>
                                    <th>Description</th>
                                    <th>Qty.</th>
                                    <th>Unit Price</th>
                                    <th>Amount</th>
                                </tr>
                                <c:forEach var="item" items="${receipt.orderItems}" varStatus="status">
                                    <tr>
                                        <td>${status.count}.</td>
                                        <td>${item.prodName} - ${item.varName}</td>
                                        <td>${item.prodQty}</td>
                                        <td>RM${item.varPrice}</td>
                                        <td>RM${item.totalPrice}</td>
                                    </tr>
                                </c:forEach>
                            </table>

                            <div class="summary">
                                <p>Subtotal: RM${receipt.subTotal}</p>
                                <p>GST: RM${receipt.gst}</p>
                                <p><strong>Total: RM${receipt.grandTotal}</strong></p>
                            </div>

                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<script src="main.js"></script>
<script src="chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
