    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>X-Workz</title>
        <link rel="icon" href="/SpringProjectApplication/images/imageFiles/xworkz_logo.jpeg">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <base href="http://localhost:8080/SpringProjectApplication/">

        <style>
            .error-message {
                color: red;
            }
            body {
                padding-top: 60px;
            }
            .profile-picture {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                margin-right: 10px;
                vertical-align: middle;
            }
        </style>
    </head>
    <body>

    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img src="/SpringProjectApplication/images/imageFiles/xworkz_logo.jpeg" alt="Logo" style="width: 50px;">
                X-Workz
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
        </ul>
    </div>



    <!-- Main content -->
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h2 class="text-center mb-4">Edit Profile</h2>
                <form id="combinedForm" action="uploadFile" method="post" enctype="multipart/form-data">
                    <c:if test="${not empty message}">
                        <div class="alert alert-info">${message}</div>
                    </c:if>

                    <!-- Display user details -->
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" value="${userData.firstName}" required>
                        <span id="firstNameError" class="error-message"></span>
                    </div>

                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" value="${userData.lastName}" required>
                        <span id="lastNameError" class="error-message"></span>
                    </div>

                    <div class="mb-3">
                        <label for="emailAddress" class="form-label">Email</label>
                        <input type="email" class="form-control" id="emailAddress" name="emailAddress" value="${userData.emailAddress}" required readonly>
                        <span id="emailAddressError" class="error-message"></span>
                    </div>

                    <div class="mb-3">
                        <label for="mobileNumber" class="form-label">Mobile Number</label>
                        <input type="text" class="form-control" id="mobileNumber" name="mobileNumber" value="${userData.mobileNumber}" required>
                        <span id="mobileNumberError" class="error-message"></span>
                    </div>

                    <!-- Input field for profile picture -->
                    <div class="mb-3">
                        <label for="profilePicture" class="form-label">Profile Picture</label>
                        <div class="input-group">
                            <input type="file" class="form-control" id="profilePicture" name="file" accept="image/*">
                        </div>
                        <!-- Preview image -->
                        <img id="profilePicturePreview" src="#" alt="Preview" style="max-width: 100%; max-height: 200px; margin-top: 10px; display: none;">
                    </div>

                    <button type="submit" class="btn btn-primary">Update Profile</button>
                </form>
            </div>
        </div>
    </div>

    <!-- JavaScript for previewing uploaded image -->
    <script>
        $(document).ready(function() {
            $('#profilePicture').change(function() {
                const file = this.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        $('#profilePicturePreview').attr('src', e.target.result).show();
                    };
                    reader.readAsDataURL(file);
                }
            });
        });
    </script>

    </body>
    </html>