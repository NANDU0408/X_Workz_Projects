<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>X-Workz</title>
    <link rel="icon" href="/SpringProjectApplication/images/imageFiles/xworkz_logo.jpeg">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=yes">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js" integrity="sha384-Q5Pl1rC/oStfi2M1kCzKJwECcpOd+kdmPLWjszkcft5Sa+h7sc6LBN7sLk2kPvh5" crossorigin="anonymous"></script>
    <link href="/SpringProjectApplication/images/css/bootstrap.css" rel="stylesheet">
    <base href="http://localhost:8080/SpringProjectApplication/">

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
            text-align: left;
        }
        .navbar-nav {
            flex-direction: row;
        }
        .navbar-nav .nav-item {
            margin-left: 10px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            // Handle click event for all dropdown toggles
            $('.dropdown-toggle').on('click', function(e) {
                var $el = $(this).next('.dropdown-menu');
                $('.dropdown-menu').not($el).hide();
                $el.toggle();
                e.stopPropagation();
            });

            // Close dropdown when clicking outside of it
            $(document).on('click', function(e) {
                if (!$(e.target).closest('.dropdown-toggle').length) {
                    $('.dropdown-menu').hide();
                }
            });
        });
    </script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="#" style="margin-right: 200px;">
            <img src="/SpringProjectApplication/images/imageFiles/xworkz_logo.jpeg" alt="Logo" style="width: 50px;">
            X-Workz
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto" style="margin-right: 120px;">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                        SignIn/SignUp
                    </a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="registration/SignIn.jsp">SignIn</a>
                        <a class="dropdown-item" href="registration/SignUp.jsp">SignUp</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<br>
<!-- Tech info -->
<div class="container mt-3">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card rounded-3 bg-light shadow">
                <div class="card-body">
                    <h5 class="card-title" style="font-family: Georgia, 'Times New Roman', Times, serif; font-weight: bold;">Tech Info</h5>
                    <div class="card-text" style="font-family: Georgia, 'Times New Roman', Times, serif;">
                        <div class="mb-3 card-body">
                             <h5 class="card-title">Tech Stack:</h5>
                             <p class="card-text">Java, Spring, JSP, Servlet</p>
                        </div>
                        <div class="mb-3 card-body">
                            <h5 class="card-title">Start Date: </h5>
                                <p class="card-text">Wednesday, 12 June 2024</p>
                        </div>
                        <div class="mb-3 card-body">
                            <h5 class="card-title">Version Control System:</h5>
                            <p class="card-text">Github: <a href="https://github.com/Sahil-123/Xworkz-Comman-Module/tree/main/Common-Modules" target="_blank"> Common Modules</a></p>
                        </div>
                        <div class="mb-3 card-body">
                        <h5 class="card-title">Description:</h5>
                        <p class="card-text">------</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="content" class="container mt-5">
    <!-- Content will be loaded here dynamically -->
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>