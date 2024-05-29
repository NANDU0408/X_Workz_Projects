<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" xmlns:jsp="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VNI Bookings</title>
    <link rel="icon" href="/Wisdom/images/imageFiles/xworkz_logo.jpeg">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" integrity="sha384-T4NXsFWeOar4O6OrnH67i4JfRlPqqSywEa6a/X9bL1RnEhJZ6sa/Axz3A5LAhs1A" crossorigin="anonymous">
    <link href="/Wisdom/images/css/bootstrap.css" rel="stylesheet">
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
            padding: 30px;
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
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="/Wisdom/images/imageFiles/xworkz_logo.jpeg" alt="Logo" style="width: 50px;">
            VNI Bookings
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
                     <a class="nav-link" href="matrimony.jsp">Matrimony</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="contact.jsp">Contact</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="resortBooking.jsp">Resort Booking</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="login-form mycard">
        <h2 class="text-center">Welcome to Spring</h2>
        <form action="wisdom" method="post">
        <span><h1>Hi,Nandish...!!</h1></span>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>
