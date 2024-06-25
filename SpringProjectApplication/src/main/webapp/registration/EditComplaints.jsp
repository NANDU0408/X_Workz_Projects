<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>System Issue Management</title>
    <link rel="icon" href="resources/imageFiles/xworkz_logo.jpeg">
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
            <br>
            <a class="nav-link" href="index.jsp">Home</a>
        </li>
    </ul>
</div>

<!-- Main content -->
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h2 class="text-center mb-4">Raise Complaint</h2>
            <form id="combinedForm" action="complaintEdit" method="post" enctype="multipart/form-data">
                <c:if test="${not empty message}">
                    <div class="alert alert-info">${message}</div>
                </c:if>

                <!-- Display complaint details -->
                <div class="mb-3">
                    <label for="complaintType" class="form-label">Complaint Type</label>
                    <input type="text" class="form-control" id="complaintType" name="complaintType" value="${raiseComplaintDTO.complaintType}" readonly>
                    <span id="complaintTypeError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="country" class="form-label">Country</label>
                    <input type="text" class="form-control" id="country" name="country" value="${raiseComplaintDTO.country}" readonly>
                    <span id="countryError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="state" class="form-label">State</label>
                    <input type="text" class="form-control" id="state" name="state" value="${raiseComplaintDTO.state}" readonly>
                    <span id="stateError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="city" class="form-label">City</label>
                    <input type="text" class="form-control" id="city" name="city" value="${raiseComplaintDTO.city}" readonly>
                    <span id="cityError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="address" class="form-label">Address</label>
                    <textarea class="form-control" id="address" name="address" rows="3" readonly>${raiseComplaintDTO.address}</textarea>
                    <span id="addressError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description" rows="3" required>${raiseComplaintDTO.description}</textarea>
                    <span id="descriptionError" class="error-message"></span>
                </div>

                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="agreeTerms" name="agreeTerms" checked="${raiseComplaintDTO.agreeTerms}" disabled>
                    <label class="form-check-label" for="agreeTerms">Agree to Terms and Conditions</label>
                    <span id="agreeTermsError" class="error-message"></span>
                </div>

                <button type="submit" class="btn btn-primary">Submit Complaint</button>
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