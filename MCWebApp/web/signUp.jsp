<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sign Up - Madeline Cosmetic</title>

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- Login/Signup CSS -->
  <link rel="stylesheet" href="assets/css/loginstyle.css">

  <style>
    body, html {
      background-color: #f85c70 !important;
    }
    .container-big {
      margin: 20px;
    }
  </style>
</head>
<body>

<div class="container-big">
  <div class="container-1 text-center">
    <div class="title mb-4">
      <h2>MADELINE<br>COSMETIC<br>Web App</h2>
    </div>

    <div class="login-form">

      <!-- JSP FORM -->
      <form action="${pageContext.request.contextPath}/Register" method="post">

            <!-- Name Input -->
            <div class="mb-3">
              <input type="text" name="name" class="form-control"
                     placeholder="Enter your full name" required>
            </div>

            <!-- Email Input -->
            <div class="mb-3">
              <input type="email" name="email" class="form-control"
                     placeholder="Enter your email" required>
            </div>

            <!-- Password Input -->
            <div class="mb-3">
              <input type="password" name="pass" class="form-control"
                     placeholder="Enter your password" required>
            </div>

            <!-- Address Input -->
            <div class="mb-3">
              <input type="text" name="address" class="form-control"
                     placeholder="Enter your address" required>
            </div>


        <!-- Error Message from Servlet -->
        <%
          String error = (String) request.getAttribute("error");
          if (error != null) {
        %>
          <div class="alert alert-danger">
            <%= error %>
          </div>
        <%
          }
        %>

        <!-- Submit Button -->
        <button type="submit"
                class="btn btn-primary w-100 main-login-button">
          Sign Up
        </button>

        <!-- Footer Links -->
        <nav class="links mt-3">
          Already have an account?
          <a href="login.jsp">Login here</a>
        </nav>
        
        <% String msg = request.getParameter("msg");
            if ("registered".equals(msg)) { %>
         <div class="alert alert-success">
           Registration successful! Please log in.
         </div>
         <% } %>


      </form>
    </div>
  </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
