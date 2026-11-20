<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Product</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

        <link rel="stylesheet" href="assets/css/global.css">
        <link rel="stylesheet" href="assets/css/manageOrderStyle.css">
        <link rel="stylesheet" href="assets/css/sidebar.css">
    </head>
    <body>

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
                                    <a href="${pageContext.request.contextPath}/Logout"><i class="fa-solid fa-arrow-right-from-bracket"></i> Logout</a>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </nav>

        <div id="content" style="margin-top: 80px; padding: 20px;">
            <div class="container my-4">

                <div class="main-container mb-4 text-center">
                    <h2>Edit Product</h2>
                </div>

                <c:if test="${not empty param.success}">
                    <div class="alert alert-success">✅ Updated successfully!</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">❌ ${error}</div>
                </c:if>

                <div class="table-container p-3 mb-5 border rounded shadow-sm">

                    <!-- ✅ IMPORTANT: action must match servlet mapping /EditProduct -->
                    <form action="${pageContext.request.contextPath}/EditProduct" method="post" enctype="multipart/form-data">

                        <!-- ✅ MUST include varId -->
                        <input type="hidden" name="varId" value="${product.varId}">
                        <input type="hidden" name="prodId" value="${product.prodId}">
                        <input type="hidden" name="existingImgPath" value="${product.imgPath}">

                        <div class="row g-3">

                            <div class="col-md-12">
                                <label class="form-label">Current Image</label><br>
                                <img src="${pageContext.request.contextPath}${product.imgPath}"
                                     style="width:120px;height:120px;object-fit:cover;border:1px solid #ddd;">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Product Name</label>
                                <input type="text" class="form-control" name="prodName" value="${product.prodName}" required>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Product Code</label>
                                <input type="text" class="form-control" name="prodCode" value="${product.prodCode}" required>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">SKU</label>
                                <input type="text" class="form-control" name="sku" value="${product.sku}" required>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Category</label>
                                <select class="form-select" name="category" required>
                                    <option value="">Select</option>
                                    <option value="Eyes" <c:if test="${product.category=='Eyes'}">selected</c:if>>Eyes</option>
                                    <option value="Lips" <c:if test="${product.category=='Lips'}">selected</c:if>>Lips</option>
                                    </select>
                                </div>

                                <div class="col-md-6">
                                    <label class="form-label">Variation Name</label>
                                    <input type="text" class="form-control" name="varName" value="${product.varName}">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Price (RM)</label>
                                <input type="number" step="0.01" class="form-control" name="varPrice" value="${product.varPrice}" required>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Total Stock</label>
                                <input type="number" class="form-control" name="varQty" value="${product.varQty}" required>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Status</label>
                                <select class="form-select" name="varStatus" required>
                                    <option value="">Select</option>
                                    <option value="In-stock" <c:if test="${product.varStatus=='In-stock'}">selected</c:if>>In-stock</option>
                                    <option value="Out-stock" <c:if test="${product.varStatus=='Out-stock'}">selected</c:if>>Out-stock</option>
                                    </select>
                                </div>

                                <div class="col-md-12">
                                    <label class="form-label">Upload New Image (optional)</label>
                                    <input type="file" class="form-control" name="productImage" accept="image/*">
                                </div>

                            </div>

                            <div class="d-flex justify-content-end mt-3">
                                <button type="submit" class="btn btn-success">Save changes</button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>

            <script src="${pageContext.request.contextPath}/main.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
