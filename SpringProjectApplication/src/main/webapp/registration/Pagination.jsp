
<div class="d-flex justify-content-end pe-4" >

  <nav aria-label="" class="d-flex  align-items-center" >
    <ul class="pagination">

      <c:choose>

        <c:when test="${currentPage <= 1}">
          <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
        </c:when>

        <c:otherwise>
          <li class="page-item"><a class="page-link" href="${pageURL}/${currentPage - 1}/${pageSize}">Previous</a></li>
        </c:otherwise>
      </c:choose>

      <!-- <c:choose>

      <c:when test="${currentPage == pages}">
        <c:forEach var="i" begin="1" end="${pages}">
          <li class="page-item

          <c:if test=" ${currentPage==i}">
            active
          </c:if>

          "><a class="page-link" href="${pageURL}/${i}/${pageSize}">${i}</a></li>
        </c:forEach>
      </c:when>

      <c:otherwise>
        <c:forEach var="i" begin="${currentPage}" end="${pages}">
          <li class="page-item

          <c:if test=" ${currentPage==i}">
            active
          </c:if>

          "><a class="page-link" href="${pageURL}/${i}/${pageSize}">${i}</a></li>
        </c:forEach>
      </c:otherwise>
    </c:choose> -->


      <c:choose>

        <c:when test="${currentPage == pages}">
          <li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
        </c:when>

        <c:otherwise>
          <li class="page-item"><a class="page-link" href="${pageURL}/${currentPage + 1}/${pageSize}">Next</a></li>
        </c:otherwise>
      </c:choose>

      <div class="ms-3 d-flex justify-content-center  align-items-center" >
        ${((currentPage-1) * pageSize) + 1} - ${(pageSize * (currentPage - 1)) + currentPageRecordSize} of ${totalRecordCount}
      </div>
    </ul>
  </nav>
</div>

<!-- ${currentPage} current page
${pages} total pages
${pageURL} page url
${pageSize} page size -->



























<td>${complaint.raiseComplaintDTO.complaintStatus}</td>
                                    <td>
                                        <label for="status-${complaint.raiseComplaintDTO.complaintId}">Change Status:</label>
                                        <select name="complaintStatus" id="status-${complaint.raiseComplaintDTO.complaintId}" class="form-select">
                                            <option value="" disabled selected>Status</option>
                                            <option value="Resolved">Resolved</option>
                                            <option value="NotResolved">Not Resolved</option>
                                            <option value="Pending">Pending</option>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="hidden" name="complaintId" value="${complaint.raiseComplaintDTO.complaintId}">
                                        <input type="hidden" name="deptAssign" value="${complaint.raiseComplaintDTO.deptAssign}">
                                        <input type="submit" value="Update" class="btn btn-primary">
                                    </td>