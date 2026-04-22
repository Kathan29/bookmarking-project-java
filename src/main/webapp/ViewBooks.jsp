<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>thrill.io</title>
<style>
.book-container {
	display: flex;
	flex-direction: column;
	gap: 20px;
	padding: 20px;
	max-width: 900px;
	margin: auto;
}

.book-card {
	display: flex;
	background: #fff;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	overflow: hidden;
	transition: transform 0.2s;
	border: 1px solid #eee;
}

.book-card:hover {
	transform: translateY(-5px);
	box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.book-image {
	width: 160px;
	height: 220px;
	object-fit: cover;
}

.book-details {
	padding: 20px;
	flex: 1;
}

.book-title {
	font-size: 24px;
	color: #DB5227;
	margin: 0 0 10px 0;
	font-weight: bold;
}

.meta-info {
	font-size: 16px;
	color: #555;
	margin-bottom: 8px;
}

.status-badge {
	display: inline-block;
	padding: 4px 10px;
	border-radius: 4px;
	font-size: 14px;
	font-weight: bold;
	margin-top: 10px;
}

.btn-action {
	display: inline-block;
	margin-top: 15px;
	padding: 8px 16px;
	background-color: #0058A6;
	color: white !important;
	border-radius: 4px;
	text-decoration: none;
	font-size: 14px;
}
</style>
</head>
<body>
	<div
		style="height: 65px; align: center; background: #DB5227; font-family: Arial; color: white;">
		<br>
		<b> <a href=""
			style="font-family: garamond; font-size: 34px; margin: 0px 0px 0px 10px; color: white; text-decoration: none;">thrill.io</a></b>

		<div
			style="height: 25px; background: #DB5227; font-family: Arial; color: white;">
			<b> <!-- Previously we were using:  %=request.getContextPath()% -->

				<a href="${pageContext.request.contextPath}/home"
				style="font-family: garamond; font-size: 16px; margin-left: 1150px; color: white; text-decoration: none;">Home</a>
				<a href="${pageContext.request.contextPath}/myBook"
				style="font-family: garamond; font-size: 16px; margin-left: 10px; color: white; text-decoration: none;">My
					Books</a> <a href="${pageContext.request.contextPath}/auth/logout"
				style="font-family: garamond; font-size: 16px; margin-left: 20px; color: white; text-decoration: none;">Logout</a>
			</b>
		</div>
	</div>
	<br>
	<br>



	<div class="book-container">
		<c:forEach var="book" items="${books}">
			<div class="book-card">
				<img src="${book.imageUrl}" class="book-image" alt="${book.title}">

				<div class="book-details">
					<h2 class="book-title">${book.title}</h2>
					<div class="meta-info">
						By: <strong>${book.authors[0]}</strong>
					</div>
					<div class="meta-info">
						Rating: <span style="color: #f39c12;">${book.amazonRating}</span>
					</div>
					<div class="meta-info">Published: ${book.publicationYear}</div>

					<hr style="border: 0; border-top: 1px solid #eee; margin: 15px 0;">

					<%-- Kid Friendly Logic --%>
					<c:if test="${userType==1 or userType==2}">
						<div style="font-size: 14px; color: #777;">Kid Friendly
							Eligibility:</div>
						<c:choose>
							<c:when test="${book.kidFriendlyStatus=='UNKNOWN'}">
								<a
									href="${pageContext.request.contextPath}/approveBookKF?bid=${book.id}"
									class="btn-action" style="background: #27ae60;">Approve</a>
								<a
									href="${pageContext.request.contextPath}/rejectBookKF?bid=${book.id}"
									class="btn-action" style="background: #c0392b;">Reject</a>
							</c:when>
							<c:when test="${book.kidFriendlyStatus=='APPROVED'}">
								<span class="status-badge"
									style="background: #e8f5e9; color: #2e7d32;">Approved</span>
							</c:when>
							<c:otherwise>
								<span class="status-badge"
									style="background: #ffebee; color: #c62828;">Rejected</span>
							</c:otherwise>
						</c:choose>
					</c:if>

					<c:if test="${userType==0 and book.kidFriendlyStatus=='APPROVED'}">
						<span class="status-badge"
							style="background: #e1f5fe; color: #0277bd;">Kid Friendly</span>
					</c:if>

					<br> <a
						href="${pageContext.request.contextPath}/saveBook?bid=${book.id}"
						class="btn-action">Save to My Books</a>
				</div>
			</div>
		</c:forEach>
	</div>

</body>
</html>