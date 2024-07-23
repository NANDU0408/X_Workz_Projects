<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="custom-table">
    <thead>
        <tr>
            <th>History ID</th>
            <th>Complaint ID</th>
            <th>Department ID</th>
            <th>Complaint Status</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="history" items="${historyTable}">
            <tr>
                <td>${history.id}</td>
                <td>${history.complaintId}</td>
                <td>${history.departmentName}</td>
                <td>${history.complaintStatus}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>