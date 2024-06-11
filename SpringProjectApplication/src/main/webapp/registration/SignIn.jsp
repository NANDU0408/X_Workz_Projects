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
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js" integrity="sha384-Q5Pl1rC/oStfi2M1kCzKJwECcpOd+kdmPLWjszkcft5Sa+h7sc6LBN7sLk2kPvh5" crossorigin="anonymous"></script>
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
        <a class="navbar-brand" style="margin-right: 200px;">
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
                    <a class="nav-link dropdown-toggle" role="button" aria-haspopup="true" aria-expanded="false">
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
            <div class="card">
                <div class="card-header text-center">
                    <h2 class="mb-0">Login Form</h2>
                </div>
                <div class="card-body">
                    <form action="login" method="POST">
                        <span style="color : red;">
                            <c:forEach items="${errors}" var="objectError">
                                ${objectError.defaultMessage}</br>
                            </c:forEach>
                        </span>

                        <div class="mb-3">
                            <label for="emailAddress" class="form-label">User Name</label>
                            <input type="text" class="form-control" id="emailAddress" name="emailAddress" value="${dto.emailAddress}" onblur="emailAddressValidation()">
                            <span id="emailAddressError" class="error-message"></span>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" value="${dto.password}" onblur="passwordValidation()">
                            <span id="passwordError" class="error-message"></span>
                        </div>

                        <div class="d-flex justify-content-center">
                            <input type="submit" id="submitButton" class="btn btn-primary" value="Login" name="submit"/>
                            <button type="button" class="btn btn-secondary ms-2" onclick="refreshPage()">Refresh</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        function refreshPage() {
            window.location.reload();
        }

        function userNameValidation() {
            const userName = document.getElementById('userName').value;
            const userNameError = document.getElementById('userNameError');
            if (userName === '') {
                userNameError.textContent = 'User Name is required';
            } else {
                userNameError.textContent = '';
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
    </script>
</div>

</body>
</html>