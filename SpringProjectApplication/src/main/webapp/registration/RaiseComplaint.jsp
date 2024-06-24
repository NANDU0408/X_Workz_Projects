<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>System Issue Management</title>
    <link rel="icon" href="resources/imageFiles/xworkz_logo.jpeg">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <base href="http://localhost:8080/SpringProjectApplication/">
    <script src="resources/javaScript/complaint.js"></script>

    <!-- Font Awesome -->
    <script src="https://kit.fontawesome.com/de5723d334.js" crossorigin="anonymous"></script>

    <style>
        .error-message {
            color: red;
        }
        body {
            padding-top: 60px;
        }
        .container {
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
                <li class="nav-item">
                    <a class="nav-link" href="registration/Home.jsp">User Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="registration/SignIn.jsp?role=user" style="text-align: center;">
                        <span style="display: block;"><i class="fa-solid fa-user"></i></span>
                        <span class="d-block"></span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h2 class="text-center mb-4 mt-4">Complaint Form</h2>
            <form action="submitComplaint" method="POST">
                <c:if test="${successMessage.length() > 0}">
                    <div class="alert alert-success">${successMessage}</div>
                </c:if>
                <c:if test="${failureMessage.length() > 0}">
                    <div class="alert alert-danger">${failureMessage}</div>
                </c:if>
                <span style="color : red;">
                    <c:forEach items="${errors}" var="objectError">
                        ${objectError.defaultMessage}</br>
                    </c:forEach>
                </span>

                <div class="mb-3">
                    <label for="complaintType" class="form-label">Complaint Type</label>
                    <select class="form-control" id="complaintType" name="complaintType" onblur="setGroup()">
                        <option value="">Select Complaint Type</option>
                        <option value="Noise">Noise</option>
                        <option value="Pollution">Pollution</option>
                        <option value="Water Leakage">Water Leakage</option>
                        <option value="Other">Other</option>
                    </select>
                    <span id="complaintTypeError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="country" class="form-label text-dark">Country</label>
                    <select class="form-select" id="country" name="country" onblur="validateCountry()">
                        <option value="0">Choose...</option>
                    </select>
                    <span id="countryError" class="text-danger"></span>
                </div>

                <div class="mb-3">
                    <label for="state" class="form-label text-dark">State</label>
                    <select class="form-select" id="state" name="state" onblur="validateState()">
                        <option value="0">Choose...</option>
                    </select>
                    <span id="stateError" class="text-danger"></span>
                </div>

                <div class="mb-3">
                    <label for="city" class="form-label text-dark">City</label>
                    <select class="form-select" id="city" name="city" onblur="validateCity()">
                        <option value="0">Choose...</option>
                    </select>
                    <span id="cityError" class="text-danger"></span>
                </div>

                <div class="mb-3">
                    <label for="address" class="form-label">Address</label>
                    <input type="text" class="form-control" id="address" name="address" value="${adminLoginForm.emailAddress}" onblur="setAddress()">
                    <span id="addressError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description" rows="4" oninput="updateDescriptionCount(); validateDescription();">${adminLoginForm.emailAddress}</textarea>
                    <span id="descriptionError" class="error-message"></span>
                    <small id="descriptionCount" class="form-text text-muted">300 characters remaining</small>
                </div>

                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="agreeTerms" name="agreeTerms">
                    <label class="form-check-label" for="agreeTerms">I agree to the terms and conditions</label>
                    <span id="termsError" class="error-message"></span>
                </div>

                <div class="mb-1 d-flex justify-content-center">
                    <input type="submit" id="submitButton" class="btn btn-primary" value="Submit" name="submit"/>
                    <button type="button" class="btn btn-secondary ms-2" onclick="refreshPage()">Refresh</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>