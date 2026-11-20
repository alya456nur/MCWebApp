<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Staff</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

        <!-- Custom CSS -->
        <link rel="stylesheet" href="assets/css/global.css">
        <link rel="stylesheet" href="assets/css/inventory.css">
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

                <div class="offcanvas offcanvas-end text-bg-dark" id="offcanvasDarkNavbar" aria-labelledby="offcanvasDarkNavbarLabel">
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
                    <h2>Staff</h2>
                </div>

                <!-- Staff Table -->
                <div class="table-container p-3 mb-5 border rounded shadow-sm">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h3>Staff List</h3>

                        <div class="col-6 col-md-2 date-filter">
                            <a class="btn btn-primary w-100 btn-add"
                               href="${pageContext.request.contextPath}/AddStaff">
                                Add Staff
                            </a>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table striped table-hover align-middle">
                            <thead class="table custom-header">
                                <tr>
                                    <th>#</th>
                                    <th>Staff ID</th>
                                    <th>Name</th>
                                    <th>Phone</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th></th>
                                </tr>
                            </thead>

                            <tbody id="staff-table-content">
                                <c:forEach var="s" items="${staff}" varStatus="loop">
                                    <tr>
                                        <th scope="row">${loop.index + 1}</th>
                                        <td>${s.staffId}</td>
                                        <td>${s.staffName}</td>
                                        <td>${s.staffPhone}</td>
                                        <td>${s.staffEmail}</td>
                                        <td>${s.userRole}</td>

                                        <td>
                                            <!-- ✅ Only show Edit option if THIS ROW is STAFF role -->
                                            <c:if test="${s.userRole == 'staff'}">
                                                <div class="dropdown">
                                                    <button class="btn btn-secondary dropdown-toggle" type="button"
                                                            id="dropdownMenuButton-${s.staffId}"
                                                            data-bs-toggle="dropdown" aria-expanded="false">
                                                        <i class="fa-solid fa-ellipsis-vertical"></i>
                                                    </button>

                                                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton-${s.staffId}">
                                                        <li>
                                                            <a class="dropdown-item"
                                                               href="${pageContext.request.contextPath}/EditStaff?staffId=${s.staffId}">
                                                                Edit
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>

        <script src="main.js"></script>
        <script src="chart.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
