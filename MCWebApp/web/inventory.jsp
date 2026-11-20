<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventory</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="assets/css/global.css">
    <link rel="stylesheet" href="assets/css/inventory.css">
    
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
            <h2>Inventory</h2>
                <c:if test="${not empty filterStart}">
                    <p class="filterDate">
                        Filter applied: ${filterStart} to ${filterEnd}
                    </p>
                </c:if>
        
            <div class="row g-4 bigger-container">

                <!-- Product in Inventory -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container custom-card">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <!-- Label with icon -->
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                              <i class="fa-solid fa-boxes-stacked me-2 card-icon"></i>
                              Product In Inventory
                          </p>
                        </div>

                        <!-- Value -->
                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title data-value text-break">${productCount}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Total Stock In -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container custom-card">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                              <i class="fa-solid fa-boxes-stacked me-2 card-icon"></i>
                              Total Stock In
                          </p>
                        </div>
                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title data-value text-break">${stockInCount}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Total Stock Out -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container custom-card">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                              <i class="fa-solid fa-boxes-stacked me-2 card-icon"></i>
                              Total Stock Out
                          </p>
                        </div>
                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title data-value text-break">${stockOutCount}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Low Stock Items -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container custom-card">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                              <i class="fa-solid fa-triangle-exclamation me-2 card-icon"></i>
                              Low Stock Items
                          </p>
                        </div>
                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title data-value text-break">${lowStockCount}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Inventory Value -->
                <div class="col-6 col-md-3">
                  <div class="card h-100 analytics-container custom-card">
                    <div class="card-body analytics d-flex flex-column justify-content-center">
                      <div class="d-flex flex-column flex-md-row align-items-center">
                        <div class="me-md-2 text-center text-md-start">
                          <p class="card-title">
                              <i class="fa-solid fa-coins me-2 card-icon"></i>
                              Inventory Value
                          </p>
                        </div>
                        <div class="ms-md-2 d-flex justify-content-center align-items-center">
                          <p class="card-title data-value text-break">RM${inventoryValue}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
            </div>
                        
        </div>
                        
        <!-- Inventory Table -->
        <div class="table-container p-3 mb-5 border rounded shadow-sm">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3>Product List</h3>
            </div>

            <div class="table-responsive">
                <table class="table striped table-hover align-middle">
                    <thead class="table custom-header">
                        <tr>
                            <th></th>
                            <th>Image</th>
                            <th>Product Code</th>
                            <th>Product Name</th> 
                            <th>SKU</th>
                            <th>Variation</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Total Stock</th>
                            <th>Status</th>
                            <th>Create Date</th>
                            <th>Last Update</th>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody id="table-content">
                        <c:forEach var="item" items="${products}" varStatus="loop">
                            <tr class="product-table-content" data-id="${item.varId}">
                                <th scope="row">${loop.index + 1}</th>
                                <td>
                                    <img src="${pageContext.request.contextPath}${item.imgPath}" 
                                    class="card-img-top" 
                                    alt="${item.prodName}" 
                                    style="width: 80px; height: 80px; object-fit: cover;">
                                </td>
                                <td>${item.prodCode}</td>
                                <td>${item.prodName}</td>
                                <td>${item.sku}</td>
                                <td>${item.varName}</td>
                                <td>${item.category}</td>
                                <td>RM${item.varPrice}</td>
                                <td class="totalStock" id="all-total-stock-${item.varId}">${item.varQty}</td>
                                <td class="stockStatus" id="all-stock-status-${item.varId}">${item.varStatus}</td>
                                <td>${item.createDate}</td>
                                <td>${item.lastUpdate}</td>
                                <td>
                                    <div class="dropdown">
                                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton-${item.varId}" data-bs-toggle="dropdown" aria-expanded="false">
                                            <i class="fa-solid fa-ellipsis-vertical"></i>
                                        </button>
                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton-${item.varId}">
                                            <li>
                                                <a class="dropdown-item" href="${pageContext.request.contextPath}/EditProduct?varId=${item.varId}">Edit</a>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <!-- Button -->
                <div class="col-6 col-md-2 btn-add">
                    <button class="btn btn-primary w-100 btn-add"
                            data-bs-toggle="modal"
                            data-bs-target="#addProductModal">
                        Add Product
                    </button>
                </div>
            </div>
        </div>
        
        <form action="Inventory" method="get" class="row mb-4 g-3 filter-container">
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

        <!-- Stock In / Stock Out Table -->
        <div class="table-container p-3 mb-5 border rounded shadow-sm">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3>Stock Movement</h3>
                <a href="${pageContext.request.contextPath}/Ledger" data-page="ledger">                            
                    <i class="fa-solid fa-circle-chevron-right"></i>
                </a>
            </div>

            <div class="table-responsive">
                <table class="table striped table-hover align-middle">
                    <thead class="table custom-header">
                        <tr>
                            <th></th>
                            <th>Product</th>
                            <th>SKU</th>
                            <th>Stock In</th>
                            <th>Stock Out</th>
                            <th>Total Stock</th>
                            <th>Status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="stock-table-content">
                        <c:forEach var="item" items="${stock}" varStatus="loop">
                            <tr data-id="${item.varId}">
                                <th scope="row">${loop.index + 1}</th>
                                <td>${item.varName}</td>
                                <td>${item.sku}</td>
                                <td id="stock-in-${item.varId}">${item.stockIn}</td>
                                <td id="stock-out-${item.varId}">${item.stockOut}</td>
                                <td id="total-stock-${item.varId}">${item.varQty}</td>
                                <td id="stock-status-${item.varId}">${item.varStatus}</td>
                                <td>
                                    <div class="dropdown">
                                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton-${item.varId}" data-bs-toggle="dropdown" aria-expanded="false">
                                            <i class="fa-solid fa-ellipsis-vertical"></i>
                                        </button>
                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton-${item.varId}">
                                            <li>
                                                <a class="dropdown-item" href="${pageContext.request.contextPath}/StockInProduct?varId=${item.varId}">
                                                    Stock In
                                                </a>
                                            </li>
                                            <li>
                                                <a class="dropdown-item" href="${pageContext.request.contextPath}/StockOutProduct?varId=${item.varId}">
                                                    Stock Out
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        
    </div>
    
    <!-- Add Product Modal -->
    <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">

          <div class="modal-header">
            <h5 class="modal-title" id="addProductModalLabel">Add New Product</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <div class="modal-body">
            <form id="addProductForm" method="post" action="${pageContext.request.contextPath}/AddProduct" enctype="multipart/form-data">
              <div class="row g-3">

                <!-- Product Name -->
                <div class="col-md-6">
                  <label for="productName" class="form-label">Product Name</label>
                  <input type="text" class="form-control" id="productName" name="prodName" required>
                </div>

                <!-- Product Code -->
                <div class="col-md-6">
                  <label for="productCode" class="form-label">Product Code</label>
                  <input type="text" class="form-control" id="productCode" name="prodCode" required>
                </div>

                <!-- SKU -->
                <div class="col-md-6">
                  <label for="productSKU" class="form-label">SKU</label>
                  <input type="text" class="form-control" id="productSKU" name="sku" required>
                </div>

                <!-- Category -->
                <div class="col-md-6">
                  <label for="productCategory" class="form-label">Category</label>
                  <select class="form-select" id="productCategory" name="category" required>
                    <option value="">Select Category</option>
                    <option value="Eyes">Eye</option>
                    <option value="Lips">Lip</option>
                  </select>
                </div>

                <!-- Variation -->
                <div class="col-md-6">
                  <label for="productVariation" class="form-label">Variation</label>
                  <input type="text" class="form-control" id="productVariation" name="varName">
                </div>

                <!-- Price -->
                <div class="col-md-6">
                  <label for="productPrice" class="form-label">Price (RM)</label>
                  <input type="number" step="0.01" class="form-control" id="productPrice" name="varPrice" required>
                </div>

                <!-- Image -->
                <div class="col-md-6">
                  <label for="productImage" class="form-label">Image</label>
                  <input type="file" class="form-control" id="productImage" name="imgPath" accept="image/*">
                </div>

                <!-- Total Stock -->
                <div class="col-md-6">
                  <label for="productStock" class="form-label">Initial Stock</label>
                  <input type="number" class="form-control" id="productStock" name="varQty" required>
                </div>

              </div>
            </form>
          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Cancel</button>
            <button type="submit" class="btn btn-add" form="addProductForm">Add Product</button>
          </div>

        </div>
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
