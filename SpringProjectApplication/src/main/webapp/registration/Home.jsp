<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>System Issue Management</title>
    <link rel="icon" href="resources/imageFiles/xworkz_logo.jpeg">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap.css" rel="stylesheet">
    <base href="http://localhost:8080/SpringProjectApplication/">

    <!-- Font Awesome -->
    <script src="https://kit.fontawesome.com/de5723d334.js" crossorigin="anonymous"></script>

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
        .profile-picture {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            margin-right: 5px;
            display: inline-block;
            vertical-align: middle;
        }
        .nav-link {
            display: flex;
            align-items: center;
        }
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            height: 100%;
            width: 250px;
            background-color: #f8f9fa;
            padding-top: 60px;
        }
        .sidebar .nav-link {
            padding: 10px 20px;
            display: block;
            color: #000;
            text-decoration: none;
        }
        .sidebar .nav-link:hover {
            background-color: #e9ecef;
        }
        .content-wrapper {
            margin-left: 250px;
            padding: 20px;
        }
        .custom-navbar {
            background-color: #4CAF50;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            $('.dropdown-toggle').on('click', function(e) {
                var $el = $(this).next('.dropdown-menu');
                $('.dropdown-menu').not($el).hide();
                $el.toggle();
                e.stopPropagation();
            });
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
            <a class="navbar-brand" href="#">
                <img src="resources/imageFiles/xworkz_logo.jpeg" alt="Logo" style="width: 50px;">
                System Issue Management
            </a>
            <!-- Navbar toggler button -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <!-- Navbar items -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="download" alt="Profile Picture" class="rounded-circle profile-picture">
                            <span>${userData.firstName} ${userData.lastName}</span>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <!-- Dropdown menu links -->
                            <li><a class="dropdown-item" href="#">Profile</a></li>
                            <li><a class="dropdown-item" href="#">Settings</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <form id="logoutForm" action="<c:url value="/logout" />" method="post" class="dropdown-item">
                                    <button type="submit" class="btn btn-link">Logout</button>
                                </form>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

<!-- Sidebar -->
<div class="sidebar">
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link" href="index.jsp">Home</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="registration/Edit.jsp">Edit Profile</a>
        </li>
          <li class="nav-item dropdown">
                             <a class="nav-link dropdown-toggle" href="#" id="complaintDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                 Complaints
                             </a>
                             <ul class="dropdown-menu" aria-labelledby="complaintDropdown">
                                 <li><a class="dropdown-item" href="registration/RaiseComplaint.jsp">Raise Complaint</a></li>
                                 <li><a class="dropdown-item" href="viewComplaints">View Complaints</a></li>
                             </ul>
                         </li>
    </ul>
</div>

<!-- Page Content -->
<div class="content-wrapper">
    <div class="container mt-5">
        <div class="col-md-6">
            <div class="card-body">
                <!-- Your profile content or other content -->
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap and JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>