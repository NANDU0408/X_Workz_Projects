<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=yes">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
    <title>Resort Booking Form</title>
    <link rel="icon" href="/Wisdom/images/imageFiles/xworkz_logo.jpeg">
    <link href="/Wisdom/images/css/bootstrap.css" rel="stylesheet">
    <style>
        .error-message {
            color: red;
        }

        .full-width {
            width: 100%;
            box-sizing: border-box;
        }

        .custom-alert {
            color: green;
            background-color: transparent;
            border: none;
            padding: 0;
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
                    <a class="nav-link" href="matrimony.jsp">Matrimony Form</a>
                </li>
                <li class="nav-item">
                     <a class="nav-link" href="contact.jsp">Contact</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h2 class="text-center mb-4 mt-4">Resort Booking Form</h2>
            <form action="resortBooking" method="POST">

                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" onblur="nameValidation()" >
                <span id="nameError" class="error-message"></span><br>

                 <label for="email">Email</label>
                 <input type="text" class="form-control" id="email" name="email" onblur="emailValidation()">
                 <span id="emailError" class="error-message"></span><br>

                <label for="resort">Select Resort</label>
                <select type ="" class="form-control" id="resort" name="resort" onchange="resortValidation()">
                    <option value="">Select</option>
                    <option value="Resort A">Resort A</option>
                    <option value="Resort B">Resort B</option>
                    <option value="Resort C">Resort C</option>
                </select>
                <span id="resortError" class="error-message"></span><br>

                <label>Room Type</label><br>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="roomType" id="roomType1" value="Deluxe" onchange="roomTypeValidation()">
                    <label class="form-check-label" for="roomType1">Deluxe</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="roomType" id="roomType2" value="Suite" onchange="roomTypeValidation()">
                    <label class="form-check-label" for="roomType2">Suite</label>
                </div>
                <span id="roomTypeError" class="error-message"></span><br>

                <label for="checkin">Check-In Date</label>
                <input type="date" class="form-control" id="checkin" name="checkin" onchange="checkinValidation()">
                <span id="checkinError" class="error-message"></span><br>

                <label for="checkout">Check-Out Date</label>
                <input type="date" class="form-control" id="checkout" name="checkout" onchange="checkoutValidation()">
                <span id="checkoutError" class="error-message"></span><br>

                <div class="mb-1 d-flex justify-content-center">
                <input type="submit" id="submitButton" class="btn btn-primary" disabled/>
                <button type="button" class="btn btn-secondary ms-2" onclick="refreshPage()">Refresh</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="/Wisdom/images/javaScript/resortBooking.js"></script>
</body>
</html>