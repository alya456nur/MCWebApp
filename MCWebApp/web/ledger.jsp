<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ledger</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="assets/css/global.css">
    <link rel="stylesheet" href="assets/css/ledger.css">
    
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
        <div class="offcanvas offcanvas-end text-bg-dark" id="offcanvasDarkNavbar"
             aria-labelledby="offcanvasDarkNavbarLabel">

            <div class="offcanvas-header">
                <h5 class="offcanvas-title" id="offcanvasDarkNavbarLabel">Madeline Cosmetic</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
            </div>

            <div class="offcanvas-body">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <p>Navigation</p>
                        <ul class="navbar-nav">
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/StaffDashboard"><i class="fa-solid fa-shop"></i> Summary</a></li>
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/Ledger" data-page="home"><i class="fa-solid fa-boxes-packing"></i> Ledger</a></li>
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/Inventory"><i class="fa-solid fa-cart-arrow-down"></i> Inventory</a></li>
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/ManageOrder"><i class="fa-solid fa-truck"></i> Manage Order</a></li>
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/Staff"><i class="fa-solid fa-id-card-clip"></i> Staff</a></li>
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

        <!-- Filter Section -->
        <div class="main-container mb-5 text-center">
            <h2>Ledger</h2>
            <!-- Filter Section -->
                <form action="Ledger" method="get" class="row mb-4 g-3 filter-container">
                    <div class="col-6 col-md-3 date-filter">
                        <input type="date" name="startDate" class="form-control">
                    </div>
                    <div class="col-6 col-md-3 date-filter">
                        <input type="date" name="endDate" class="form-control">
                    </div>
                    <div class="col-6 col-md-2 date-filter">
                        <button type="submit" class="btn btn-primary w-100 btn-filter">
                            <i class="fa-solid fa-filter me-2"></i> Filter
                        </button>
                    </div>
                </form>
                <c:if test="${not empty filterStart}">
                    <p class="filterDate">
                        Filter applied: ${filterStart} to ${filterEnd}
                    </p>
                </c:if>
            
            <!-- Ledger Table -->
            <div class="table-container p-3 mb-5 border rounded shadow-sm">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h3>Stock Movement</h3>
                </div>

                <div class="table-responsive">
                    <table class="table striped table-hover align-middle">
                        <thead class="table custom-header">
                            <tr>
                                <th></th>
                                <th>Date</th>
                                <th>Order Id</th>
                                <th>Staff Name</th>
                                <th>Product Code</th>
                                <th>Product Name</th>
                                <th>Variation</th>
                                <th>Action</th>
                                <th>Quantity</th>
                                <th>Remark</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty record}">
                                    <tr>
                                        <td colspan="10" class="text-center">No ledger records found</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="r" items="${record}" varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td>${r.stockDate}</td>
                                            <td>${r.orderId}</td>                                          
                                            <td>${r.staffName}</td>
                                            <td>${r.prodCode}</td>
                                            <td>${r.prodName}</td>
                                            <td>${r.varName}</td>
                                            <td>${r.stockType}</td>
                                            <td>${r.stockQty}</td>
                                            <td>${r.remark}</td>
                                            
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="main.js"></script>
<script src="chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
