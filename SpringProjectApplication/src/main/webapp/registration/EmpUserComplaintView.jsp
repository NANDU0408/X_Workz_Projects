<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
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

            <!-- Font Awesome -->
            <script src="https://kit.fontawesome.com/de5723d334.js" crossorigin="anonymous"></script>

            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script>
                $(document).ready(function () {
                    // Handle click event for all dropdown toggles
                    $('.dropdown-toggle').on('click', function (e) {
                        var $el = $(this).next('.dropdown-menu');
                        $('.dropdown-menu').not($el).hide();
                        $el.toggle();
                        e.stopPropagation();
                    });

                    // Close dropdown when clicking outside of it
                    $(document).on('click', function (e) {
                        if (!$(e.target).closest('.dropdown-toggle').length) {
                            $('.dropdown-menu').hide();
                        }
                    });
                });
            </script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.3/js/bootstrap.bundle.min.js"
                integrity="sha384-Q5Pl1rC/oStfi2M1kCzKJwECcpOd+kdmPLWjszkcft5Sa+h7sc6LBN7sLk2kPvh5"
                crossorigin="anonymous"></script>
        </head>

        <body>

            <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
                <div class="container-fluid">
                    <a class="navbar-brand" href="#">
                        <img src="resources/imageFiles/xworkz_logo.jpeg" alt="Logo" style="width: 50px;">
                        System Issue Management
                    </a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="registration/DeptEmpHome.jsp">Department Employee Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="deptEmpViewComplaints">All Complaint</a>
                            </li>
                            <li class="nav-item">
                                <button type="button" id="downloadCSVBtn" class="btn btn-primary">
                                    <i class="fas fa-file-csv"></i> Download CSV
                                </button>
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
                                <form action="searchComplaintsDeptEmp" method="get">
                                    <div class="row g-3">
                                        <div class="col-auto">
                                            <label for="type" class="form-label text-dark">Type</label>
                                            <select class="form-select" id="type" name="type">
                                                <option value="" ${selectedType==null ? 'selected' : '' }>Choose...
                                                </option>
                                                <option value="Noise" ${selectedType=='Noise' ? 'selected' : '' }>Noise
                                                </option>
                                                <option value="Pollution" ${selectedType=='Pollution' ? 'selected' : ''
                                                    }>Pollution</option>
                                                <option value="Drainage Problem" ${selectedType=='Drainage Problem'
                                                    ? 'selected' : '' }>Drainage Problem</option>
                                                <option value="Electric Problem" ${selectedType=='Electric Problem'
                                                    ? 'selected' : '' }>Electric Problem</option>
                                                <option value="Water Leakage" ${selectedType=='Water Leakage'
                                                    ? 'selected' : '' }>Water Leakage</option>
                                                <option value="Wastage Problem" ${selectedType=='Wastage Problem'
                                                    ? 'selected' : '' }>Wastage Problem</option>
                                                <option value="Water Problem" ${selectedType=='Water Problem'
                                                    ? 'selected' : '' }>Water Problem</option>
                                            </select>
                                        </div>
                                        <div class="col-auto">
                                            <label for="status" class="form-label text-dark">Status</label>
                                            <select class="form-select" id="status" name="status">
                                                <option value="" ${selectedStatus==null ? 'selected' : '' }>Choose...
                                                </option>
                                                <option value="Pending" ${selectedStatus=='Pending' ? 'selected' : '' }>
                                                    Pending</option>
                                                <option value="InAction" ${selectedStatus=='InAction' ? 'selected' : ''
                                                    }>InAction</option>
                                                <option value="Resolved" ${selectedStatus=='Resolved' ? 'selected' : ''
                                                    }>Resolved</option>
                                            </select>
                                        </div>
                                        <div class="col-auto">
                                            <label for="area" class="form-label text-dark">Area</label>
                                            <input type="text" class="form-control" id="area" name="area"
                                                value="${selectedArea}">
                                        </div>
                                        <div class="col-auto align-self-end">
                                            <input type="submit" value="Search" name="search" id="search"
                                                class="btn btn-primary">
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
                                    <th>Country</th>
                                    <th>State</th>
                                    <th>City</th>
                                    <th>Address</th>
                                    <th>Description</th>
                                    <th>Status</th>
                                    <th>Complaint Status:</th>
                                    <th>Actions</th>
                                    <th>History</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="complaint" items="${assignedEmpComplaints}">
                                    <tr>
                                        <form id="updateComplaintStatus">
                                            ${complaint.raiseComplaintDTO}
                                            <td>${complaint.raiseComplaintDTO.country}</td>
                                            <td>${complaint.raiseComplaintDTO.state}</td>
                                            <td>${complaint.raiseComplaintDTO.city}</td>
                                            <td>${complaint.raiseComplaintDTO.address}</td>
                                            <td>${complaint.raiseComplaintDTO.description}</td>
                                            <td>${complaint.raiseComplaintDTO.complaintStatus}</td>
                                            <td>
                                                <label for="status-${complaint.raiseComplaintDTO.complaintId}">Change
                                                    Status:</label>
                                                <select name="complaintStatus"
                                                    id="status-${complaint.raiseComplaintDTO.complaintId}"
                                                    class="form-select">
                                                    <option value="" disabled selected>Status</option>
                                                    <option value="Resolved">Resolved</option>
                                                    <option value="NotResolved">Not Resolved</option>
                                                    <option value="Pending">Pending</option>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="hidden" name="complaint_Id"
                                                    value="${complaint.raiseComplaintDTO.complaintId}">
                                                <input type="hidden" name="deptAssign"
                                                    value="${complaint.raiseComplaintDTO.deptAssign}">
                                                <button type="button" class="btn btn-primary update-btn">Update</button>
                                            </td>
                                        </form>
                                        <td>
                                            <form action="history" method="post">
                                                <input type="hidden" name="complaintId"
                                                    value="${complaint.raiseComplaintDTO.complaintId}">
                                                <button type="button" class="btn btn-primary"
                                                    onclick="openComplaintHistory('${complaint.raiseComplaintDTO.complaintId}','${complaint.raiseComplaintDTO.deptAssign}')">View</button>
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
                <script>
                    // Function to handle Ajax request for complaint history
                    function openComplaintHistory(complaintId, departmentId) {
                        $.ajax({
                            type: "POST",
                            url: "history",  // Replace with your actual URL for fetching history
                            data: {
                                complaintId: complaintId,
                                departmentId: departmentId
                            },
                            success: function (response) {
                                // Populate modal with history data
                                $('#historyModalBody').html(response);
                                $('#historyModal').modal('show');  // Show modal after data is loaded
                            },
                            error: function (e) {
                                alert('Error fetching complaint history.');
                                console.log(e);
                            }
                        });
                    }
                </script>


                <script>
                    var currentComplaintId = '';
                    var currentStatus = '';

                    // Function to set the modal data for other statuses
                    function setModelDataOtherStatus(modal) {
                        // Set the necessary data for the modal here
                        // Example:
                        $('#otherModelComplaintId').val(currentComplaintId);
                        console.log('Modal data set for complaint ID:', currentComplaintId);
                    }

                    $(document).ready(function () {
                        // Variable to store the current complaint ID

                        // Function to open the modal based on complaint status
                        function handleComplaintUpdate(complaintId, complaintStatus) {
                            currentComplaintId = complaintId;  // Set the current complaint ID
                            currentStatus = complaintStatus;
                            console.log('Handling complaint update:', { complaintId, complaintStatus });

                            if (complaintStatus === 'Resolved') {
                                // Show the modal for 'Resolved' status
                                var resolveModal = new bootstrap.Modal(document.getElementById('resolveModel1'));
                                resolveModal.show();
                            } else {
                                // Show the modal for other statuses
                                var otherModal = new bootstrap.Modal(document.getElementById('otherModel'));
                                setModelDataOtherStatus(otherModal);  // Call the function to set the modal data
                                otherModal.show();
                            }
                        }



                        // Handle click event for the "Update" button
                        $('.update-btn').on('click', function (e) {
                            e.preventDefault();  // Prevent form submission

                            let currentform = document.getElementById("updateComplaintStatus");

                            console.log(currentform);

                            let complaintId = currentform.complaint_Id.value;
                            let complaintStatus = currentform.complaintStatus.value;

                            console.log(complaintStatus, complaintStatus);

                            /*
                            var $form = $(this).closest('form');
                            var complaintId = $form.find('input[name="complaint_Id"]').val();
                            var complaintStatus = $form.find('select[name="complaintStatus"]').val();
                    
                            console.log('Form HTML:', $form.html());
                            console.log('Complaint ID:', $form.find('input[name="complaint_Id"]').val());
                            console.log('Complaint Status:', $form.find('select[name="complaintStatus"]').val());
                    */

                            console.log('Update button clicked:', { complaintId, complaintStatus });

                            // Call function to handle opening the correct modal
                            handleComplaintUpdate(complaintId, complaintStatus);
                        });

                        // Handle click event for the "Submit" button in the modal
                        $('#submitOtherStatus').on('click', function () {
                            var comment = $('#otherTextArea').val();
                            console.log('Submit button clicked in modal:', { currentComplaintId, comment });

                            if (comment.trim() === '') {
                                $('#otherTextAreaError').text('Comment is required.');
                                return;
                            } else {
                                $('#otherTextAreaError').text('');
                            }

                            // Make AJAX request to update the complaint status
                            /*  $.ajax({
                                  url: 'assignedEmpComplaints',  // Ensure this matches your controller mapping
                                  type: 'POST',
                                  data: {
                                      "complaintId": currentComplaintId,
                                      "comment": comment,
                                      "complaintStatus": currentStatus  // Assuming 'Other' is the status being set
                                  },
                                  success: function(response) {
                                      console.log('AJAX success:', response);
                                      alert('Complaint status updated successfully.');
                                      var otherModal = bootstrap.Modal.getInstance(document.getElementById('otherModel'));
                                      otherModal.hide();
                                      location.reload();  // Reload the page to see changes
                                  },
                                  error: function(xhr, status, error) {
                                      console.log('AJAX error:', { xhr, status, error });
                                      $('#resolverOtherModelAlert').text('Error updating complaint status.');
                                      console.error('Error:', error);
                                  }
                              });
                      
                              */

                            console.log("data", JSON.stringify({  // Convert data to JSON string
                                    "complaintId": currentComplaintId,
                                    "comment": comment,
                                    "complaintStatus": currentStatus  // Assuming 'Other' is the status being set
                                }));

                            $.ajax({
                                url: 'assignedEmpComplaints',  // Ensure this matches your controller mapping
                                type: 'POST',
                                contentType: 'application/json',  // Set the content type to JSON
                                data: JSON.stringify({  // Convert data to JSON string
                                    "complaintId": currentComplaintId,
                                    "comment": comment,
                                    "complaintStatus": currentStatus  // Assuming 'Other' is the status being set
                                }),
                                success: function (response) {
                                    console.log('AJAX success:', response);
                                    alert('Complaint status updated successfully.');
                                    var otherModal = bootstrap.Modal.getInstance(document.getElementById('otherModel'));
                                    otherModal.hide();
                                    location.reload();  // Reload the page to see changes
                                },
                                error: function (xhr, status, error) {
                                    console.log('AJAX error:', { xhr, status, error });
                                    $('#resolverOtherModelAlert').text('Error updating complaint status.');
                                    console.error('Error:', error);
                                }
                            });

                        });
                    });
                </script>

                <script>
                    // Function to escape CSV special characters
                    function escapeCsvValue(value) {
                        if (value == null) return '';
                        if (typeof value !== 'string') value = String(value);
                        if (value.includes('"') || value.includes(',') || value.includes('\n')) {
                            value = '"' + value.replace(/"/g, '""') + '"';
                        }
                        return value;
                    }

                    // Function to trigger CSV download
                    document.getElementById('downloadCSVBtn').addEventListener('click', function () {
                        // Prepare CSV content
                        var csvContent = "Complaint ID,Complaint Type,Country,State,City,Address,Description,User ID,Created Date,Created By,Updated Date,Updated By,Department ID,Assign Employee,Status,Complaint Status\n";

                        // Iterate over complaints data
                        <c:forEach var="complaint" items="${assignedEmpComplaints}">
                            csvContent += [
                            escapeCsvValue("${complaint.raiseComplaintDTO.complaintId}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.complaintType}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.country}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.state}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.city}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.address}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.description}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.userId}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.createdDate}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.createdBy}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.updatedDate}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.updatedBy}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.deptAssign}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.assignEmployee}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.status}"),
                            escapeCsvValue("${complaint.raiseComplaintDTO.complaintStatus}")
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
                <script>
                    $(document).ready(function () {
                        // Custom dropdown toggle script (if any)
                        // Example: $('.dropdown-toggle').dropdown();
                    });
                </script>

                <!-- Modal for Complaint History -->
                <div class="modal fade" id="historyModal" tabindex="-1" aria-labelledby="historyModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="historyModalLabel">Complaint History Details</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="modal-body" id="historyModalBody">
                                <!-- History details will be loaded here dynamically -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>




            <!-- Modal for Other Status -->
            <div class="modal fade" id="otherModel" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-content">
                        <div class="modal-body">
                            <div id="resolverOtherModelAlert" class="text-danger"></div>
                            <div class="mb-3">
                                <textarea class="form-control" rows="5" id="otherTextArea"
                                    placeholder="Leave a comment here"></textarea>
                                <span class="text-danger" id="otherTextAreaError"></span>
                            </div>
                        </div>
                        <div class="modal-footer d-flex justify-content-between">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="submitOtherStatus"
                                onclick="setModelDataOtherStatus(otherModel)">Submit</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal for Resolved Status -->
            <div class="modal fade" id="resolveModel1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-content">
                        <div class="modal-body">
                            <div id="resolverModelAlert"></div>
                            <div class="mb-3">
                                <textarea class="form-control" id="resolveTextArea1" rows="5"
                                    placeholder="Leave a comment here"></textarea>
                                <span class="text-danger" id="textareaError"></span>
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="basic-addon1">OTP:</span>
                                <input type="number" class="form-control numberCenterOtp" id="otp" max="6"
                                    placeholder="6 digit OTP" aria-label="Username" aria-describedby="basic-addon1">
                                <button type="button" class="btn btn-primary input-group-text"
                                    onclick="resendOTP()">Resend</button>
                            </div>
                            <span class="text-danger" id="optError"></span>
                        </div>
                        <div class="modal-footer d-flex justify-content-between">
                            <button type="submit" class="btn btn-secondary"
                                onclick="dismisResolveModel('resolveModel1')">Close</button>
                            <button type="submit" class="btn btn-primary"
                                onclick="setModelDataForResolve('resolveModel1')">Submit</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal for Loading Spinner -->
            <div class="modal fade" id="spinner" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-body p-5 bg-transparent text-white d-flex justify-content-center">
                        <div class="d-flex justify-content-center">
                            <div class="spinner-border text-light"></div>
                            <h3 class="ms-3">Loading</h3>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal for Success Message -->
            <c:if test="${successMessage.length() > 0}">
                <div class="modal fade" id="exampleModal" tabindex="0" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="d-flex flex-row-reverse p-3">
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="d-flex flex-column align-items-center p-5 text-white">
                                <img src="resources/images/check.png" class="imgHight" alt="Check Icon"></img>
                            </div>
                            <div class="d-flex flex-column align-items-center p-5">
                                <h5>${successMessage}</h5>
                            </div>
                            <div class="d-flex flex-row-reverse p-3">
                                <button type="submit" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>

                <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        var exampleModal = new bootstrap.Modal(document.getElementById('exampleModal'));
                        exampleModal.show();
                    });
                </script>
            </c:if>

            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
                crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
                integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
                crossorigin="anonymous"></script>
        </body>

        </html>