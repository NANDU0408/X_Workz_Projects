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
        .custom-table {
            width: 100%;
            border-collapse: collapse;
            border: 1px solid #ddd;
        }
        .custom-table th,
        .custom-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        .custom-table th {
            background-color: #f2f2f2;
        }
        .custom-table tbody tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .edit-btn {
            padding: 5px 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js" integrity="sha384-Q5Pl1rC/oStfi2M1kCzKJwECcpOd+kdmPLWjszkcft5Sa+h7sc6LBN7sLk2kPvh5" crossorigin="anonymous"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="resources/imageFiles/xworkz_logo.jpeg" alt="Logo" style="width: 50px;">
            System Issue Management
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="registration/Home.jsp">User Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        View Complaints
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="viewComplaints">All Complaints</a></li>
                                <li><a class="dropdown-item" href="viewActiveComplaints">Active Complaints</a></li>
                                <li><a class="dropdown-item" href="viewInactiveComplaints">Inactive Complaints</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>



<div class="container mt-4">
    <h2>Raise Complaint Details</h2>
    <c:choose>
        <c:when test="${!complaintLists.isEmpty()}">
            <table class="custom-table">
                <thead>
                <tr>
                    <th>Complaint ID</th>
                    <th>Complaint Type</th>
                    <th>Country</th>
                    <th>State</th>
                    <th>City</th>
                    <th>Address</th>
                    <th>Description</th>
                    <th>Agree Terms</th>
                    <th>User ID</th>
                    <th>Created Date</th>
                    <th>Created By</th>
                    <th>Updated Date</th>
                    <th>Updated By</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="complaint" items="${complaintLists}">
                        <tr>
                            <td>${complaint.complaintId}</td>
                            <td>${complaint.complaintType}</td>
                            <td>${complaint.country}</td>
                            <td>${complaint.state}</td>
                            <td>${complaint.city}</td>
                            <td>${complaint.address}</td>
                            <td>${complaint.description}</td>
                            <td>${complaint.agreeTerms}</td>
                            <td>${complaint.userId}</td>
                            <td>${complaint.createdDate}</td>
                            <td>${complaint.createdBy}</td>
                            <td>${complaint.updatedDate}</td>
                            <td>${complaint.updatedBy}</td>
                            <td>${complaint.status}</td>
                            <td>
                                <form action="editComplaint/${complaint.complaintId}" method="get">
                                    <button type="submit" class="edit-btn">Edit</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No complaints found.</p>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        // Your custom dropdown toggle script (if any)
        // Example: $('.dropdown-toggle').dropdown();
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>