<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Dashboard</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="assets/css/global.css">
    <link rel="stylesheet" href="assets/css/staffdashboard.css">
    
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

        <!-- Filter Section -->
        <div class="main-container mb-5 text-center">
            <h2>Dashboard</h2>
            <!-- Filter Section -->
                <form action="StaffDashboard" method="get" class="row mb-4 g-3 filter-container">
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
        
            <div class="row g-4 bigger-container">
                <!-- Product in Inventory -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <!-- Label with icon -->
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                            <i class="fa-solid fa-boxes-stacked me-1"></i> Product In Inventory
                          </p>
                        </div>

                        <!-- Value -->
                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title text-break" id="productCount">${productCount}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Total Orders -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                            <i class="fa-solid fa-list-check me-1"></i> Total Orders
                          </p>
                        </div>

                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title text-break" id="completedCount">${completedCount}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Total Pending Orders -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                            <i class="fa-solid fa-hourglass-half me-1"></i> Total Pending Orders
                          </p>
                        </div>

                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title text-break" id="pendingCount">${pendingCount}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Cancelled Orders -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                            <i class="fa-solid fa-xmark me-1"></i> Cancelled Orders
                          </p>
                        </div>

                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title text-break" id="cancelledCount">${cancelledCount}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Total Customers -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                            <i class="fa-solid fa-users me-1"></i> Total Customers
                          </p>
                        </div>

                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title text-break" id="customerCount">${customerCount}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Total Sales -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                            <i class="fa-solid fa-money-bill-trend-up me-1"></i> Total Sales
                          </p>
                        </div>

                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title text-break" id="grandTotalSum">${grandTotalSum}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>           
        </div>
         
        <!-- Charts -->
        <div class="chart-container p-3 mb-5 border rounded shadow-sm">     
            <!-- Revenue Chart -->
                    <div class="chart-container p-3 mb-5 border rounded shadow-sm">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h3>Revenue</h3>
                        </div>
                        <div class="row">
                            <div class="card h-100 p-3">
                                <canvas id="revenueChart" width="400" height="200"></canvas>
                            </div>
                        </div>
                    </div>

                    <!-- Serialize revenue map safely -->
                    <c:set var="revenueJson">
                        {
                        <c:forEach var="entry" items="${revenue}" varStatus="status">
                            "${entry.key}": ${entry.value}<c:if test="${!status.last}">,</c:if>
                        </c:forEach>
                        }
                    </c:set>

                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                    <script>
                        const revenueData = ${revenueJson};
                        const labels = Object.keys(revenueData);
                        const data = Object.values(revenueData);

                        const ctx = document.getElementById('revenueChart').getContext('2d');

                        new Chart(ctx, {
                            type: 'bar',
                            data: {
                                labels,
                                datasets: [{
                                    label: 'Revenue (RM)',
                                    data,
                                    backgroundColor: 'rgba(75, 192, 192, 0.5)',
                                    borderColor: 'rgba(75, 192, 192, 1)',
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                responsive: true,
                                plugins: {
                                    title: { display: true, text: 'Revenue' },
                                    legend: { display: false }
                                },
                                scales: { y: { beginAtZero: true } }
                            }
                        });
                    </script>                 
        </div>

        <!-- Sales Per Item Section -->
        <div class="sales-container p-3 mb-5 border rounded shadow-sm">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3>Sales Per Item</h3>
                <i class="fa-solid fa-circle-chevron-right"></i>
            </div>

            <div class="row g-2 justify-content-start" id="productDropdownCard">
                <c:forEach var="prod" items="${products}">
                    <div class="col-auto">
                        <div class="card h-100 item-container">
                            <div class="card-body d-flex align-items-center">

                                <!-- Icon -->
                                <div class="me-3">
                                    <img src="https://cdn-icons-png.flaticon.com/512/679/679922.png"
                                         alt="Inventory Icon"
                                         width="40" height="40">
                                </div>

                                <!-- Dropdown of variations -->
                                <div class="variation-container">

                                    <select class="form-select form-select-sm variation-select">
                                        <option value="">${prod.prodName}</option>
                                        <c:forEach var="var" items="${prod.variations}">
                                            <option value="${var.varId}">
                                                ${var.varName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                        
                                    <p>Total variation: ${prod.variations.size()}</p>

                                    <div class="variation-data">
                                        <c:forEach var="var" items="${prod.variations}">
                                            <div class="variation-item" data-var-id="${var.varId}" style="display:none;">
                                                <p class="mb-0">Item sold: ${var.soldQty}</p>
                                                <p class="mb-0">Item sold: ${var.varPrice}</p>                                              
                                                <p class="mb-0">RM ${var.sales}</p>
                                            </div>
                                        </c:forEach>
                                    </div>

                                </div>

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

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
