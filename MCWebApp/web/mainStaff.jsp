<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Sidebar CSS -->
    <link rel="stylesheet" href="assets/css/sidebar.css">
    
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

</head>

<body onload="loadContent('staffDashboard.html')">

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
                            <li class="nav-item sub-nav"><a href="#" onclick="loadContent('staffDashboard.html')" data-page="home"><i class="fa-solid fa-shop"></i> Summary</a></li>
                            <li class="nav-item sub-nav"><a href="#" onclick="loadContent('inventory.html')" data-page="inventory"><i class="fa-solid fa-cart-arrow-down"></i> Inventory</a></li>
                            <li class="nav-item sub-nav"><a href="#" onclick="loadContent('manageOrder.html')" data-page="order"><i class="fa-solid fa-truck"></i> Manage Order</a></li>
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
    <h1 style="color:white;">Loading...</h1>
</div>

<script src="main.js"></script>
<script src="chart.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
