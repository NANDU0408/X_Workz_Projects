<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>System Issue Management</title>
    <link rel="icon" href="resources/imageFiles/xworkz_logo.jpeg">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js" integrity="sha384-Q5Pl1rC/oStfi2M1kCzKJwECcpOd+kdmPLWjszkcft5Sa+h7sc6LBN7sLk2kPvh5" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <link href="resources/css/bootstrap.css" rel="stylesheet">
    <base href="http://localhost:8080/SpringProjectApplication/">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5/ieIJ6gIFhvjaCln2gq5oWeFf3vSUcIB+q5VR7j" crossorigin="anonymous">
    <script src="https://kit.fontawesome.com/de5723d334.js" crossorigin="anonymous"></script>

    <style>
        .error-message {
            color: red;
        }
        body {
            padding-top: 60px;
        }
        .captcha-container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        #captchaPreview {
            font-size: 1.5rem;
            letter-spacing: 0.1rem;
            margin-bottom: 10px;
            filter: blur(2px);
        }

        .captcha-btn {
            background-color: #0d6efd;
            color: #fff;
        }

        .custom-card-header {
            background-color: #0d6efd;
            color: #fff;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp" style="margin-right: 200px;">
            <img src="resources/imageFiles/xworkz_logo.jpeg" alt="Logo" style="width: 50px;">
            System Issue Management
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto" style="margin-right: 10px;">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <div class="row justify-content-center">
        <c:choose>
            <c:when test="${param.role == 'admin'}">
                <!-- Admin Login View -->
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header text-center custom-card-header">
                            <h2 class="mb-0">Admin Login</h2>
                        </div>
                        <div class="card-body">
                            <form action="admin" method="POST">
                                <c:if test="${not empty errorMsg}">
                                    <div class="alert alert-danger">${errorMsg}</div>
                                </c:if>
                                <div class="mb-3">
                                    <label for="adminEmail" class="form-label">Email ID</label>
                                    <input type="text" class="form-control" id="adminEmail" name="emailAddress" value="${adminLoginForm.emailAddress}" onblur="adminEmailValidation()">
                                    <span id="adminEmailError" class="error-message"></span>
                                </div>
                                <div class="mb-3">
                                    <label for="adminPassword" class="form-label">Password</label>
                                    <input type="password" class="form-control" id="adminPassword" name="password" value="${adminLoginForm.password}" onblur="adminPasswordValidation()">
                                    <span id="adminPasswordError" class="error-message"></span>
                                </div>
                                <div class="d-flex justify-content-center">
                                    <input type="submit" id="adminSubmitButton" class="btn btn-primary" value="Login" name="submit"/>
                                    <button type="button" class="btn btn-secondary ms-2" onclick="refreshPage()">Refresh</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:when test="${param.role == 'deptadmin'}">
                <!-- Department Admin Login View -->
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header text-center custom-card-header">
                            <h2 class="mb-0">Department Admin Login</h2>
                        </div>
                        <div class="card-body">
                            <form action="deptAdmin" method="POST">
                                <c:if test="${not empty errorMsg}">
                                    <div class="alert alert-danger">${errorMsg}</div>
                                </c:if>
                                <div class="mb-3">
                                    <label for="deptAdminEmail" class="form-label">Email ID</label>
                                    <input type="text" class="form-control" id="deptAdminEmail" name="emailAddress" value="${deptAdminLoginForm.emailAddress}" onblur="deptAdminEmailValidation()">
                                    <span id="deptAdminEmailError" class="error-message"></span>
                                </div>
                                <div class="mb-3">
                                    <label for="deptAdminPassword" class="form-label">Password</label>
                                    <input type="password" class="form-control" id="deptAdminPassword" name="password" value="${deptAdminLoginForm.password}" onblur="deptAdminPasswordValidation()">
                                    <span id="deptAdminPasswordError" class="error-message"></span>
                                </div>
                                <div class="d-flex justify-content-center">
                                    <input type="submit" id="deptAdminSubmitButton" class="btn btn-primary" value="Login" name="submit"/>
                                    <button type="button" class="btn btn-secondary ms-2" onclick="refreshPage()">Refresh</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:when test="${param.role == 'deptemployee'}">
                <!-- Department Employee Login View -->
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header text-center custom-card-header">
                            <h2 class="mb-0">Department Employee Login</h2>
                        </div>
                        <div class="card-body">
                            <form action="deptemployee" method="POST">
                                <c:if test="${not empty errorMsg}">
                                    <div class="alert alert-danger">${errorMsg}</div>
                                </c:if>
                                <div class="mb-3">
                                    <label for="deptEmployeeEmail" class="form-label">Email ID</label>
                                    <input type="text" class="form-control" id="deptEmployeeEmail" name="emailAddress" value="${deptEmployeeLoginForm.emailAddress}" onblur="deptEmployeeEmailValidation()">
                                    <span id="deptEmployeeEmailError" class="error-message"></span>
                                </div>
                                <div class="mb-3">
                                    <label for="deptEmployeePassword" class="form-label">Password</label>
                                    <input type="password" class="form-control" id="deptEmployeePassword" name="password" value="${deptEmployeeLoginForm.password}" onblur="deptEmployeePasswordValidation()">
                                    <span id="deptEmployeePasswordError" class="error-message"></span>
                                </div>
                                <div class="d-flex justify-content-center">
                                    <input type="submit" id="deptEmployeeSubmitButton" class="btn btn-primary" value="Login" name="submit"/>
                                    <button type="button" class="btn btn-secondary ms-2" onclick="refreshPage()">Refresh</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <!-- User Login View -->
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header text-center custom-card-header">
                            <h2 class="mb-0">User Login</h2>
                        </div>
                        <div class="card-body">
                            <form id="userLoginForm" action="login" method="POST" onsubmit="return validateCaptcha()">
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger">${errorMessage}</div>
                                </c:if>
                                <c:if test="${not empty attemptMessage}">
                                    <div class="alert alert-danger">${attemptMessage}</div>
                                </c:if>
                                <c:if test="${not empty successMessage}">
                                    <div class="alert alert-success">${successMessage}</div>
                                </c:if>
                                <c:if test="${signUpDTO.accountBlocked}">
                                    <script>
                                        document.getElementById("submitButton").disabled = true;
                                    </script>
                                </c:if>
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger">${errorMessage}</div>
                                </c:if>
                                <span class="text-danger">
                                    <c:forEach items="${errors}" var="objectError">
                                        ${objectError.defaultMessage}<br/>
                                    </c:forEach>
                                </span>
                                <div class="mb-3">
                                    <label for="emailAddress" class="form-label">Email ID</label>
                                    <input type="text" class="form-control" id="emailAddress" name="emailAddress" value="${dto.emailAddress}" onblur="emailAddressValidation()">
                                    <span id="emailAddressError" class="error-message"></span>
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" class="form-control" id="password" name="password" value="${dto.password}" onblur="passwordValidation()">
                                    <span id="passwordError" class="error-message"></span>
                                </div>
                               <div class="mb-3 captcha-container">
                                   <label for="captcha-input" class="form-label">Enter Captcha</label>
                                   <div id="captchaPreview" class="mb-2 bg-white p-2 text-center border"></div>
                                   <div class="d-flex">
                                       <input type="text" name="captcha" id="captcha" placeholder="Enter captcha text" class="form-control me-2">
                                       <button type="button" class="btn btn-secondary captcha-btn" onclick="generateCaptcha()">
                                           <i class="fas fa-sync-alt"></i>
                                           &#8635;
                                       </button>
                                   </div>
                                   <span id="captchaError" class="text-danger"></span>
                               </div>
                                <div class="d-flex justify-content-center">
                                    <button type="submit" class="btn btn-primary me-2">Submit</button>
                                    <button type="button" class="btn btn-secondary" onclick="refreshPage()">Refresh</button>
                                </div>
                                 <div class="d-flex justify-content-center mt-2">
                                    <a href="registration/ForgotPassword.jsp" class="btn btn-link">Forgot Password?</a>
                                 </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script>
    function refreshPage() {
        window.location.reload();
    }

    function emailAddressValidation() {
        const emailAddress = document.getElementById('emailAddress').value;
        const emailAddressError = document.getElementById('emailAddressError');
        if (emailAddress === '') {
            emailAddressError.textContent = 'Email ID is required';
        } else {
            emailAddressError.textContent = '';
        }
    }

    function passwordValidation() {
        const password = document.getElementById('password').value;
        const passwordError = document.getElementById('passwordError');
        if (password === '') {
            passwordError.textContent = 'Password is required';
        } else {
            passwordError.textContent = '';
        }
    }

    function adminEmailValidation() {
        const emailAddress = document.getElementById('adminEmail').value;
        const emailAddressError = document.getElementById('adminEmailError');
        if (emailAddress === '') {
            emailAddressError.textContent = 'Email ID is required';
        } else {
            emailAddressError.textContent = '';
        }
    }

    function adminPasswordValidation() {
        const password = document.getElementById('adminPassword').value;
        const passwordError = document.getElementById('adminPasswordError');
        if (password === '') {
            passwordError.textContent = 'Password is required';
        } else {
            passwordError.textContent = '';
        }
    }

    function deptAdminEmailValidation() {
        const emailAddress = document.getElementById('deptAdminEmail').value;
        const emailAddressError = document.getElementById('deptAdminEmailError');
        if (emailAddress === '') {
            emailAddressError.textContent = 'Email ID is required';
        } else {
            emailAddressError.textContent = '';
        }
    }

    function deptAdminPasswordValidation() {
        const password = document.getElementById('deptAdminPassword').value;
        const passwordError = document.getElementById('deptAdminPasswordError');
        if (password === '') {
            passwordError.textContent = 'Password is required';
        } else {
            passwordError.textContent = '';
        }
    }

    function deptEmployeeEmailValidation() {
        const emailAddress = document.getElementById('deptEmployeeEmail').value;
        const emailAddressError = document.getElementById('deptEmployeeEmailError');
        if (emailAddress === '') {
            emailAddressError.textContent = 'Email ID is required';
        } else {
            emailAddressError.textContent = '';
        }
    }

    function deptEmployeePasswordValidation() {
        const password = document.getElementById('deptEmployeePassword').value;
        const passwordError = document.getElementById('deptEmployeePasswordError');
        if (password === '') {
            passwordError.textContent = 'Password is required';
        } else {
            passwordError.textContent = '';
        }
    }

    // This function generates a new captcha
    function generateCaptcha() {
        const captchaPreview = document.getElementById('captchaPreview');
        const captchaText = Math.random().toString(36).substring(2, 7);
        captchaPreview.textContent = captchaText;
        captchaPreview.dataset.captchaText = captchaText;
    }

    // This function validates the captcha input
    function validateCaptcha() {
        const captchaInput = document.getElementById('captcha').value;
        const captchaPreview = document.getElementById('captchaPreview').dataset.captchaText;
        const captchaError = document.getElementById('captchaError');
        if (captchaInput === '') {
            captchaError.textContent = 'Captcha is required';
            return false;
        } else if (captchaInput !== captchaPreview) {
            captchaError.textContent = 'Captcha is incorrect';
            return false;
        } else {
            captchaError.textContent = '';
            return true;
        }
    }

    // Initial captcha generation
    window.onload = function() {
        generateCaptcha();
    };
</script>
<script src="/SpringProjectApplication/images/javaScript/recaptcha.js"></script>
</body>
</html>