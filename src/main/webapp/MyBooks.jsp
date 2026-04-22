<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>thrill.io</title>
<style>
.page-title {
	text-align: center;
	font-family: Arial, sans-serif;
	color: #333;
	margin: 30px 0;
	font-size: 28px;
}

.book-container {
	display: flex;
	flex-direction: column;
	gap: 20px;
	padding: 20px;
	max-width: 850px;
	margin: auto;
}

.book-card {
	display: flex;
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	border: 1px solid #eee;
	overflow: hidden;
}

.book-image {
	width: 150px;
	height: 200px;
	object-fit: cover;
}

.book-details {
	padding: 20px;
	flex: 1;
}

.book-title {
	font-size: 22px;
	color: #B13100;
	margin-bottom: 10px;
}

.empty-state {
	text-align: center;
	padding: 50px;
	background: #f9f9f9;
	border-radius: 10px;
	border: 2px dashed #ccc;
	color: #B13100;
	font-size: 20px;
}

.btn-delete {
	display: inline-block;
	margin-top: 15px;
	padding: 8px 15px;
	background-color: #c0392b;
	color: white !important;
	border-radius: 5px;
	text-decoration: none;
	font-weight: bold;
	font-size: 14px;
	transition: background 0.3s;
}

.btn-delete:hover {
	background-color: #e74c3c;
}
</style>
</head>
<body>
	<div
		style="height: 65px; align: center; background: #DB5227; font-family: Arial; color: white;"">
		<br>
		<b> <a href=""
			style="font-family: garamond; font-size: 34px; margin: 0px 0px 0px 10px; color: white; text-decoration: none;">thrill.io</a></b>

		<div
			style="height: 25px; background: #DB5227; font-family: Arial; color: white;"">
			<b> <a href="${pageContext.request.contextPath}/home"
				style="font-family: garamond; font-size: 16px; margin-left: 1150px; color: white; text-decoration: none;">Home</a>
				<a href="${pageContext.request.contextPath}/book"
				style="font-family: garamond; font-size: 16px; margin-left: 10px; color: white; text-decoration: none;">Browse
					Books</a> <a href="${pageContext.request.contextPath}/auth/logout"
				style="font-family: garamond; font-size: 16px; margin-left: 10px; color: white; text-decoration: none;">Logout</a>
			</b>
		</div>
	</div>
	<br>
	<br>

	<h1 class="page-title">My Saved Books</h1>

	<div class="book-container">
		<c:choose>
			<c:when test="${not empty books}">
				<c:forEach var="book" items="${books}">
					<div class="book-card">
						<img src="${book.imageUrl}" class="book-image" alt="Cover">
						<div class="book-details">
							<div class="book-title">${book.title}</div>
							<div style="color: #555;">
								By: <strong>${book.authors[0]}</strong>
							</div>
							<div style="color: #777; margin-top: 5px;">Rating:
								${book.amazonRating}</div>
							<div style="color: #777;">Published:
								${book.publicationYear}</div>

							<a
								href="${pageContext.request.contextPath}/deleteBook?bid=${book.id}"
								class="btn-delete"> Un-Bookmark it!! </a>
						</div>
					</div>
				</c:forEach>
			</c:when>

			<c:otherwise>
				<div class="empty-state">
					<p>You haven't saved any books yet!</p>
					<a href="${pageContext.request.contextPath}/book"
						style="color: #0058A6; font-size: 16px;">Go browse some books
						&rarr;</a>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

</body>
</html>