<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Product</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="p-4">

        <div class="container">
            <h2 class="mb-4">Add Product</h2>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <div class="alert alert-danger"><%= error%></div>
            <%
                }
            %>

            <form action="<%=request.getContextPath()%>/AddProduct" method="post" enctype="multipart/form-data">
                <div class="row g-3">

                    <div class="col-md-6">
                        <label class="form-label">Product Name</label>
                        <input type="text" class="form-control" name="prodName" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Product Code</label>
                        <input type="text" class="form-control" name="prodCode" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">SKU</label>
                        <input type="text" class="form-control" name="sku" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Category</label>
                        <select class="form-select" name="category" required>
                            <option value="">Select Category</option>
                            <option value="Eyes">Eyes</option>
                            <option value="Lips">Lips</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Variation</label>
                        <input type="text" class="form-control" name="varName">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Price (RM)</label>
                        <input type="number" step="0.01" class="form-control" name="varPrice" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Initial Stock</label>
                        <input type="number" class="form-control" name="varQty" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Image</label>
                        <input type="file" class="form-control" name="imgPath" accept="image/*">
                    </div>

                </div>

                <div class="mt-4 d-flex gap-2">
                    <button type="submit" class="btn btn-primary">Add Product</button>
                    <a class="btn btn-secondary" href="<%=request.getContextPath()%>/Inventory">Cancel</a>
                </div>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
