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
     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="/SpringProjectApplication/images/css/bootstrap.css" rel="stylesheet">
    <base href="http://localhost:8080/SpringProjectApplication/">

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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js" integrity="sha384-Q5Pl1rC/oStfi2M1kCzKJwECcpOd+kdmPLWjszkcft5Sa+h7sc6LBN7sLk2kPvh5" crossorigin="anonymous"></script>
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
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h2 class="text-center mb-4 mt-4">Sign-Up Form</h2>
            <form action="signUp" method="POST">
            <c:if test="${not empty successMessage}">
                <div class="alert alert-info">${successMessage}</div>
            </c:if>
            <c:if test="${not empty failureMessage}">
                <div class="alert alert-info">${failureMessage}</div>
            </c:if>
                <span style="color : red;">
                    <c:forEach items="${errors}" var="objectError">
                        ${objectError.defaultMessage}</br>
                    </c:forEach>
                </span>

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
                    <label for="emailAddress" class="form-label">Email</label>
                    <input type="emailAddress" class="form-control" id="emailAddress" name="emailAddress" value="${dto.emailAddress}" onblur="emailAddressValidation()">
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

    <script>
        function refreshPage() {
            window.location.reload();
        }
    </script>
</div>

</body>
</html>