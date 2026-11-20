<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="assets/css/global.css">
    <link rel="stylesheet" href="assets/css/dashboard.css">
    
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
                            <li class="nav-item sub-nav"><a href="custDashboard.jsp" data-page="home"><i class="fa-solid fa-shop"></i> Shop</a></li>
                            <li class="nav-item sub-nav"><a href="cart.jsp" data-page="cart"> <i class="fa-solid fa-cart-arrow-down"></i> Cart</a></li>
                            <li class="nav-item sub-nav"><a href="order.jsp" data-page="order"><i class="fa-solid fa-truck"></i> Your Orders</a></li>
                            <li class="nav-item sub-nav"><a href="history.jsp" data-page="history"><i class="fa-solid fa-clock"></i> Shopping History</a></li>
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

        <!-- Page Title -->
        <div class="title-container mb-5 text-center">
            <h2>Shop</h2>
            <div class="container mb-5">
                <div class="row justify-content-center g-2 dashboard-navigate">

                    <div class="col-6 col-md-3 quick-container">
                        <a class="btn btn-primary w-100 quick-btn"
                            href="cart.jsp">
                            <i class="fa-solid fa-cart-arrow-down"></i> Cart
                        </a>
                    </div>

                    <div class="col-6 col-md-3 quick-container">
                        <a class="btn btn-primary w-100 quick-btn"
                            href="order.jsp">
                            <i class="fa-solid fa-truck"></i> Orders
                        </a>
                    </div>

                    <div class="col-6 col-md-3 quick-container">
                        <a class="btn btn-primary w-100 quick-btn"
                            href="history.jsp">
                            <i class="fa-solid fa-clock"></i> History
                        </a>
                    </div>

                    <div class="col-6 col-md-3 quick-container">
                        <a class="btn btn-primary w-100 quick-btn"
                            href="profile.jsp">
                            <i class="fa-solid fa-user"></i> Edit Info
                        </a>
                    </div>

                </div>
            </div>


        <!-- Eyeshadows Section -->
        <div class="products-card p-3 mb-5 border rounded shadow-sm">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3>Eyeshadows</h3>
                <i class="fa-solid fa-circle-chevron-right"></i>
            </div>

            <div class="row g-4">
                <div class="col-md-3">
                    <div class="card h-100">
                        <img src="assets/images/st_pastel_knit.jpg" class="card-img-top" alt="Pastel Knit">
                        <div class="card-body text-center">
                            <h5 class="card-title">Seasonal Theme</h5>
                            <p class="card-text">Pastel Knit</p>
                            <p class="card-text price">RM48</p>
                            <button type="button" class="btn addCart">Add To Cart</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card h-100">
                        <img src="assets/images/st_milk_tea_summer.jpg" class="card-img-top" alt="Milk Tea Summer">
                        <div class="card-body text-center">
                            <h5 class="card-title">Seasonal Theme</h5>
                            <p class="card-text">Milk Tea Summer</p>
                            <p class="card-text price">RM48</p>
                            <button type="button" class="btn addCart">Add To Cart</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card h-100">
                        <img src="assets/images/st_autumn_nuts.jpg" class="card-img-top" alt="Autumn Nuts">
                        <div class="card-body text-center">
                            <h5 class="card-title">Seasonal Theme</h5>
                            <p class="card-text">Autumn Nuts</p>
                            <p class="card-text price">RM48</p>
                            <button type="button" class="btn addCart">Add To Cart</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card h-100">
                        <img src="assets/images/st_cosmic_berry.jpg" class="card-img-top" alt="Cosmic Berry">
                        <div class="card-body text-center">
                            <h5 class="card-title">Seasonal Theme</h5>
                            <p class="card-text">Cosmic Berry</p>
                            <p class="card-text price">RM48</p>
                            <button type="button" class="btn addCart">Add To Cart</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Lip Tints Section -->
        <div class="products-card p-3 mb-5 border rounded shadow-sm">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3>Lip Tints</h3>
                <i class="fa-solid fa-circle-chevron-right"></i>
            </div>

            <div class="row g-4">
                <div class="col-md-3">
                    <div class="card h-100">
                        <img src="assets/images/cbs.jpg" class="card-img-top" alt="Cream Butter">
                        <div class="card-body text-center">
                            <h5 class="card-title">Lip Tint</h5>
                            <p class="card-text">Cream Butter Series</p>
                            <p class="card-text price">RM28</p>
                            <!-- Labels as Dropdown -->
                            <div class="me-3">
                              <select class="form-select form-select-sm">
                                <option selected>Cream Butter Series</option>
                                <option>CBS-01 Mauve Cream</option>
                                <option>CBS-02 Salted Butter</option>
                                <option>CBS-03 Caramel Brick</option>
                                <option>CBS-04 Cotton Plum</option>
                              </select>
                            </div>
                            <button type="button" class="btn addCart">Add To Cart</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card h-100">
                        <img src="assets/images/wbt.jpg" class="card-img-top" alt="Water Blur Tint">
                        <div class="card-body text-center">
                            <h5 class="card-title">Lip Tint</h5>
                            <p class="card-text">Water Blur Tint</p>
                            <p class="card-text price">RM28</p>
                            <!-- Labels as Dropdown -->
                            <div class="me-3">
                              <select class="form-select form-select-sm">
                                <option selected>Water Blur Tint</option>
                                <option>WBT-01 Berry Ade</option>
                                <option>WBT-02 Ice Berry</option>
                                <option>WBT-03 Lavender Cream</option>
                              </select>
                            </div>
                            <button type="button" class="btn addCart">Add To Cart</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card h-100">
                        <img src="assets/images/icc.jpg" class="card-img-top" alt="Ice Cream Collection">
                        <div class="card-body text-center">
                            <h5 class="card-title">Lip Tint</h5>
                            <p class="card-text">Ice Cream Collection</p>
                            <p class="card-text price">RM28</p>
                            <!-- Labels as Dropdown -->
                            <div class="me-3">
                              <select class="form-select form-select-sm">
                                <option selected>Ice Cream Collection</option>
                                <option>ICC-01 Papaya Milk</option>
                                <option>ICC-02 Peach Pudding</option>
                                <option>ICC-03 Fig Plum</option>
                              </select>
                            </div>
                            <button type="button" class="btn addCart">Add To Cart</button>
                        </div>
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
