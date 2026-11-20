<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Profile</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

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
                                    <li class="nav-item sub-nav"><a href="${pageContext.request.contextPath}/EditCust"><i class="fa-solid fa-user"></i> Edit Info</a></li>
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

        <div id="content" style="margin-top: 80px; padding: 20px;">
            <div class="container my-4">

                <h2>Edit Info</h2>

                <!-- ✅ messages -->
                <c:if test="${param.success == '1'}">
                    <div class="alert alert-success">✅ Profile updated successfully!</div>
                </c:if>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">❌ ${error}</div>
                </c:if>

                <div class="table-container p-3 mb-5 border rounded shadow-sm">

                    <form action="${pageContext.request.contextPath}/EditCust" method="post">
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
                                        <td>
                                            <input type="text" name="cust_name" class="form-control"
                                                   value="${cust.custName}" required>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>Email</td>
                                        <td>
                                            <input type="email" class="form-control"
                                                   value="${cust.custEmail}" readonly>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>Phone</td>
                                        <td>
                                            <input type="text" name="cust_phone" class="form-control"
                                                   value="${cust.custPhone}" required>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>Address</td>
                                        <td>
                                            <textarea name="cust_address" class="form-control" rows="3">${cust.custAdd}</textarea>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="d-flex justify-content-end mt-3">
                            <button type="submit" id="saveChangeBtn" class="btn btn-primary btn-saveChange">
                                Save Changes
                            </button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/main.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
