<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #ffe0e0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            width: 400px;
            background-color: #ffd6d6;
            text-align: center;
            border-radius: 10px;
            overflow: hidden;
            align-items: center;
            align-content: center;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);

        }

        .header {
            background-color: #fc6c7c;
            padding: 40px 20px;
            color: white;
            font-size: 24px;
            font-weight: bold;
            border-bottom-left-radius: 50px;
            border-bottom-right-radius: 50px;
        }

        .content {
            padding: 30px 25px;
        }

        .content h2 {
            font-size: 18px;
            margin-bottom: 10px;
            font-weight: bold;
            color: #000;
        }

        .content p {
            font-size: 14px;
            color: #555;
            margin-bottom: 20px;
        }

        .input-box input {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 25px;
            font-size: 14px;
            box-shadow: 0 0 3px rgba(0, 0, 0, 0.2);
            box-sizing: border-box;
            display: block;
            margin: 0 0 20px 0;
        }


        .btn-reset {
            width: 100%;
            padding: 12px;
            background-color: #fc6c7c;
            color: white;
            border: none;
            border-radius: 25px;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
        }

        .footer {
            background-color: #fc6c7c;
            padding: 15px;
            color: white;
            font-weight: bold;
            margin-top: 20px;
            cursor: pointer;
        }

        .footer:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>
    <div class="container">

        <div class="header">
            FORGOT<br>PASSWORD
        </div>

        <div class="content">
            <h2>Trouble Logging in?</h2>
            <p>Enter your email and insert the new password.</p>
            
            <div class="resetPass-form">

            <!-- JSP FORM -->
            <form action="ResetPassServlet" method="post">

              <!-- Email Input -->
              <div class="input-box">
                <input type="email" name="email" class="form-control"
                       placeholder="Enter your email" required>
              </div>
              
              <!-- Password Input -->
              <div class="input-box">
                <input type="password" name="password" class="form-control"
                       placeholder="Enter your new password" required>
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
              
              <!-- Success Message from Servlet -->
              <%
                String success = (String) request.getAttribute("success");
                if (success != null) {
              %>
                <div class="alert alert-success">
                  <%= success %>
                </div>
              <%
                }
              %>

              <!-- Submit Button -->
              <button type="submit"
                      class="btn-reset" >
                Reset Password
              </button>
              
            </form>
            </div>
              
        </div>
                    
        <div class="footer" onclick="window.location.href='login.jsp'">
            Return to Login Page
        </div>

    </div>
</body>

</html>
