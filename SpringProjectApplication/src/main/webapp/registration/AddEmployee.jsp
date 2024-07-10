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

    <!-- Font Awesome -->
    <script src="https://kit.fontawesome.com/de5723d334.js" crossorigin="anonymous"></script>

    <style>
        .error-message {
            color: red;
        }
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

    <script>
        $(document).ready(function() {
            // Function to check if email exists
            $('#emailAddress').blur(function() {
                const email = $(this).val().trim();
                if (email !== '') {
                    $.ajax({
                        url: 'api/profile/email',
                        type: 'GET',
                        data: { email: email },
                        success: function(exists) {
                            if (exists) {
                                $('#emailAddressError').text('Email already exists');
                            } else {
                                $('#emailAddressError').text('');
                            }
                        },
                        error: function() {
                            $('#emailAddressError').text('Error checking email');
                        }
                    });
                }
            });

            // Function to check if mobile number exists
            $('#mobileNumber').blur(function() {
                const mobileNumber = $(this).val().trim();
                if (mobileNumber !== '') {
                    $.ajax({
                        url: 'api/profile/phone',
                        type: 'GET',
                        data: { phone: mobileNumber },
                        success: function(exists) {
                            if (exists) {
                                $('#mobileNumberError').text('Mobile number already exists');
                            } else {
                                $('#mobileNumberError').text('');
                            }
                        },
                        error: function() {
                            $('#mobileNumberError').text('Error checking mobile number');
                        }
                    });
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
                <li class="nav-item">
                    <a class="nav-link" href="registration/DeptAdminHome.jsp">Department Admin Home</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h2 class="text-center mb-4 mt-4">Employee-Registration Form</h2>
            <form action="empReg" method="POST">
                <c:if test="${successMessage.length() > 0}">
                    <div class="alert alert-info">${successMessage}</div>
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
                    <label for="deptId" class="form-label">Department</label>
                    <select class="form-control" id="deptId" name="dept_id" onblur="deptIdValidation()">
                        <option value="">Select Department</option>
                        <c:forEach var="department" items="${addDepartments}">
                            <option value="${department.deptId}">${department.deptName}</option>
                        </c:forEach>
                    </select>
                    <span id="deptIdError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="firstName" class="form-label">First Name</label>
                    <input type="text" class="form-control" id="firstName" name="firstName" value="${dto.firstName}" onblur="nameValidation()">
                    <span id="firstNameError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="lastName" class="form-label">Last Name</label>
                    <input type="text" class="form-control" id="lastName" name="lastName" value="${dto.lastName}" onblur="nameValidation()">
                    <span id="lastNameError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="designation" class="form-label">Designation</label>
                    <input type="text" class="form-control" id="designation" name="designation" value="${dto.designation}" onblur="designationValidation()">
                    <span id="designationError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="emailAddress" class="form-label">Email</label>
                    <input type="email" class="form-control" id="emailAddress" name="emailAddress" value="${dto.emailAddress}" onblur="emailAddressValidation()">
                    <span id="emailAddressError" class="error-message"></span>
                </div>

                <div class="mb-3">
                    <label for="mobileNumber" class="form-label">Mobile Number</label>
                    <input type="text" class="form-control" id="mobileNumber" name="mobileNumber" value="${dto.mobileNumber}" onblur="phoneValidation()">
                    <span id="mobileNumberError" class="error-message"></span>
                </div>

                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="agreeTerms" name="agreeTerms" onchange="termsValidation()" ${dto.agreeTerms == 'on' ? 'checked' : ''}>
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

<script>
    function refreshPage() {
        window.location.reload();
    }
</script>
</body>
</html>