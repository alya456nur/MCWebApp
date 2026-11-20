<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Staff - Madeline Cosmetic</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="assets/css/global.css">
    <link rel="stylesheet" href="assets/css/profile.css">
    <link rel="stylesheet" href="assets/css/sidebar.css">

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
                            <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/Staff"><i class="fa-solid fa-id-card-clip"></i> Add Staff</a></li>
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

        <h2>Add New Staff</h2>
        <div class="table-container p-3 mb-5 border rounded shadow-sm">
            
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>


            <form action="${pageContext.request.contextPath}/AddStaff" method="post">            
                <div class="table-responsive">
                <table class="table striped table-hover align-middle">
                    <thead class="table custom-header">
                        <tr>
                            <th></th>
                            <th>Details</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Name</td>
                            <td><input type="text" name="staff_name" class="form-control" placeholder="Enter full name" required></td>
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td><input type="email" name="staff_email" class="form-control" placeholder="Enter email" required></td>
                        </tr>
                        <tr>
                            <td>Password</td>
                            <td><input type="password" name="staff_password" class="form-control" placeholder="Enter password" required></td>
                        </tr>
                        <tr>
                            <td>Phone</td>
                            <td><input type="text" name="staff_phone" class="form-control" placeholder="Enter phone number"></td>
                        </tr>
                        <tr>
                            <td>Role</td>
                            <td>
                                <select name="user_role" class="form-select">
                                    <option value="staff" selected>staff</option>
                                    <option value="admin">admin</option>
                                </select>
                            </td>
                        </tr>
                    </tbody>
                </table>
                </div>

                <!-- Button -->
                <div class="d-flex justify-content-end mt-3">
                    <button type="submit" class="btn btn-success btn-saveChange">Add Staff</button>
                </div>
            </form>

        </div>
    </div>
</div>

<script src="main.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
