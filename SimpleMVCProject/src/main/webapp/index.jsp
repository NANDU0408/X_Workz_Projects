
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" xmlns:jsp="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=yes">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

    <title>IPL</title>
    <link rel="icon" href="iplLogo.jpg">
    <link rel="stylesheet" href="bootstrap.css">
     <style>
            body {
                padding-top: 60px;
            }
            .myContainer {
                max-width: 800px;
            }
            .mycard {
                border-radius: 15px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            }
            .navbar-brand {
                flex-grow: 1;
                text-align: center;
            }
            .navbar-nav {
                flex-direction: row;
            }
            .navbar-nav .nav-item {
                margin-left: 10px;
            }
            .login-form {
                max-width: 300px; /* Reduce the max-width for a smaller card */
                margin: 30px auto; /* Adjust margin for spacing */
                padding: 15px; /* Adjust padding if needed */
                border: 1px solid #ddd;
                border-radius: 10px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            }
        </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
    <div class="container-fluid">
    <img src="HOTEL_LOGO.jpeg" alt="Logo" style="width: 100px; margin-right: -290px;">
        <a class="navbar-brand d-flex align-items-center justify-content-center" href="#" style="width: 100%;">
            <span>WELCOME TO ROYAL LODGING AND RESTAURANT</span>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="iplForm.jsp">LOGIN</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="iplForm.jsp">SIGN-IN</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<br>
<br>
<br>
<br>
<div class="container">
    <div class="login-form mycard">
        <h2 class="text-center">Login Details</h2>
        <form action="loginServlet" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div>
            <button type="submit" class="btn btn-primary w-100">Login</button>
            </div>
            <br>
            <div>
            <button type="submit" class="btn btn-primary w-100">New Registration</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>