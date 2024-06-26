<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" xmlns:jsp="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
    <title>RH</title>
    <link rel="icon" href="HOTEL_LOGO.jpeg">
    <link rel="stylesheet" href="bootstrap.css">
    <style>
        body {
            padding-top: 60px;
            background-image: url('throne_baground.jpg');
            background-repeat: no-repeat;
            background-position: center center;
            background-attachment: fixed;
            background-size: cover;
            height: 100vh;
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
            max-width: 300px;
            margin: 30px auto;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            $('.nav-link').click(function(e) {
                e.preventDefault();
                var page = $(this).attr('href');
                $('#content').load(page);
            });

            $('#content').load('home.jsp');
        });
    </script>
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
                    <a class="nav-link" href="home.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="authority_login.jsp">Authority Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="customer_login.jsp">Customer Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="newRegistration.jsp">SIGN-IN</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<br>
<br>
<div id="content" class="container mt-5">
    <!-- Initial content loaded here -->
    <a href="throne_baground.jpg"></a>
</div>
</body>
</html>