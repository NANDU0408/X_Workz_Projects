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
                    <a class="nav-link" href="registration/AdminHome.jsp">Admin Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        View Complaints
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="adminViewComplaints">All Complaints</a></li>
                        <li><a class="dropdown-item" href="adminViewActiveComplaints">Active Complaints</a></li>
                        <li><a class="dropdown-item" href="adminViewInactiveComplaints">Inactive Complaints</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                        <button type="button" id="downloadCSVBtn" class="btn btn-primary">Download Complaints as CSV</button>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h2>Raise Complaint Details</h2>

    <!-- Search Bar -->
    <div class="col-md-6">
        <div class="d-flex justify-content-center mb-4 mt-3">
            <div class="card px-1 py-1 bg-light">
                <div class="card-body">
                    <form action="searchComplaintsAdmin" method="get">
                        <div class="row g-3">
                            <div class="col-auto">
                                <label for="type" class="form-label text-dark">Type</label>
                                <select class="form-select" id="type" name="type">
                                    <option value="" ${selectedType == null ? 'selected' : ''}>Choose...</option>
                                    <option value="Noise" ${selectedType == 'Noise Problem' ? 'selected' : ''}>Noise</option>
                                    <option value="Pollution" ${selectedType == 'Pollution' ? 'selected' : ''}>Pollution</option>
                                    <option value="Drainage Problem" ${selectedType == 'Drainage Problem' ? 'selected' : ''}>Drainage Problem</option>
                                    <option value="Electric Problem" ${selectedType == 'Electric Problem' ? 'selected' : ''}>Electric Problem</option>
                                    <option value="Water Leakage" ${selectedType == 'Water Leakage' ? 'selected' : ''}>Water Leakage</option>
                                    <option value="Wastage Problem" ${selectedType == 'Wastage Problem' ? 'selected' : ''}>Wastage Problem</option>
                                    <option value="Water Problem" ${selectedType == 'Water Problem' ? 'selected' : ''}>Water Problem</option>
                                </select>
                            </div>
                            <div class="col-auto">
                               <label for="type" class="form-label text-dark">Status</label>
                               <select class="form-select" id="status" name="status">
                                    <option value="" ${selectedType == null ? 'selected' : ''}>Choose...</option>
                                    <option value="Pending" ${selectedType == 'Pending' ? 'selected' : ''}>Pending</option>
                                    <option value="InAction" ${selectedType == 'InAction' ? 'selected' : ''}>InAction</option>
                                    <option value="Resolved" ${selectedType == 'Resolved' ? 'selected' : ''}>Resolved</option>
                               </select>
                            </div>
                            <div class="col-auto">
                                <label for="area" class="form-label text-dark">Area</label>
                                <input type="text" class="form-control" id="area" name="area" value="${selectedArea}">
                            </div>
                            <div class="col-auto align-self-end">
                                <input type="submit" value="Search" name="search" id="search" class="btn btn-primary">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Complaints Table -->
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
                        <th>User ID</th>
                        <th>Created Date</th>
                        <th>Created By</th>
                        <th>Updated Date</th>
                        <th>Updated By</th>
                        <th>Dept. Assign</th>
                        <th>Status</th>
                        <th>Complaint Status:</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="complaint" items="${adminComplaintLists}">
                        <tr>
                            <form action="assignToDept" method="post">
                                <td>${complaint.complaintId}</td>
                                <td>${complaint.complaintType}</td>
                                <td>${complaint.country}</td>
                                <td>${complaint.state}</td>
                                <td>${complaint.city}</td>
                                <td>${complaint.address}</td>
                                <td>${complaint.description}</td>
                                <td>${complaint.userId}</td>
                                <td>${complaint.createdDate}</td>
                                <td>${complaint.createdBy}</td>
                                <td>${complaint.updatedDate}</td>
                                <td>${complaint.updatedBy}</td>
                                <td>
                                    <label for="dept-${complaint.complaintId}">Assign Department:</label>
                                    <select name="departmentId" id="dept-${complaint.complaintId}" class="form-select">
                                        <option value="" disabled selected>Choose...</option>
                                        <c:forEach var="department" items="${departments}">
                                            <option value="${department.deptId}">${department.deptName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>${complaint.complaintStatus}</td>
                                <td>
                                    <label for="status-${complaint.complaintId}">Change Status:</label>
                                    <select name="complaintStatus" id="status-${complaint.complaintId}" class="form-select">
                                        <option value="" disabled selected>Choose...</option>
                                        <option value="Resolved">Resolved</option>
                                        <option value="Pending">Pending</option>
                                        <option value="InAction">InAction</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="hidden" name="complaintId" value="${complaint.complaintId}">
                                    <input type="submit" value="Update" class="btn btn-primary">
                                </td>
                            </form>
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

<script>
    document.getElementById('downloadCSVBtn').addEventListener('click', function() {
        // Function to escape CSV special characters
        function escapeCsvValue(value) {
            if (value == null) return '';
            if (typeof value !== 'string') value = String(value);
            if (value.includes('"') || value.includes(',') || value.includes('\n')) {
                value = '"' + value.replace(/"/g, '""') + '"';
            }
            return value;
        }

        // Prepare CSV content
        var csvContent = "Complaint ID,Complaint Type,Country,State,City,Address,Description,User ID,Created Date,Created By,Updated Date,Updated By,Department ID,Assign Employee,Status,Complaint Status\n";

        // Iterate over complaints data
        <c:forEach var="complaint" items="${adminComplaintLists}">
            csvContent += [
                escapeCsvValue("${complaint.complaintId}"),
                escapeCsvValue("${complaint.complaintType}"),
                escapeCsvValue("${complaint.country}"),
                escapeCsvValue("${complaint.state}"),
                escapeCsvValue("${complaint.city}"),
                escapeCsvValue("${complaint.address}"),
                escapeCsvValue("${complaint.description}"),
                escapeCsvValue("${complaint.userId}"),
                escapeCsvValue("${complaint.createdDate}"),
                escapeCsvValue("${complaint.createdBy}"),
                escapeCsvValue("${complaint.updatedDate}"),
                escapeCsvValue("${complaint.updatedBy}"),
                escapeCsvValue("${complaint.deptAssign}"),
                escapeCsvValue("${complaint.assignEmployee}"),
                escapeCsvValue("${complaint.status}"),
                escapeCsvValue("${complaint.complaintStatus}")
            ].join(",") + "\n";
        </c:forEach>

        // Create a Blob object containing the CSV file
        var blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });

        // Create a link element, set its href to the Blob object, and trigger download
        var link = document.createElement("a");
        if (link.download !== undefined) { // Feature detection
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", "complaints.csv");
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        } else {
            alert("Your browser doesn't support downloading files directly. Please try a different browser or download manually.");
        }
    });
</script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>