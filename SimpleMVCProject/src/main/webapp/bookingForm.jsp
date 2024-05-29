<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Allotment Details</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .allotment-form {
            max-width: 500px;
            margin: auto;
            padding: 20px;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="allotment-form bg-light">
                <h2 class="text-center">Allotment Details</h2>
                <form action="allotmentServlet" method="post">
                    <div class="mb-3">
                        <label for="customer_id" class="form-label">Customer ID</label>
                        <input type="number" class="form-control" id="customer_id" name="customer_id" required>
                    </div>
                    <div class="mb-3">
                        <label for="RoomNo" class="form-label">Room Number</label>
                        <input type="number" class="form-control" id="RoomNo" name="RoomNo" required>
                    </div>
                    <div class="mb-3">
                        <label for="Entry_date" class="form-label">Entry Date</label>
                        <input type="date" class="form-control" id="Entry_date" name="Entry_date" required>
                    </div>
                    <div class="mb-3">
                        <label for="vacate_date" class="form-label">Vacate Date</label>
                        <input type="date" class="form-control" id="vacate_date" name="vacate_date" required>
                    </div>
                    <div class="mb-3">
                        <label for="Amount_to_pay" class="form-label">Amount to Pay</label>
                        <input type="number" step="0.01" class="form-control" id="Amount_to_pay" name="Amount_to_pay" required>
                    </div>
                    <div class="mb-3">
                        <label for="Payed_Amount" class="form-label">Payed Amount</label>
                        <input type="number" step="0.01" class="form-control" id="Payed_Amount" name="Payed_Amount" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Submit</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Include Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>