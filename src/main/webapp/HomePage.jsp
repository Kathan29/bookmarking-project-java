<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Bookmark Manager</title>
    <style>
        :root {
            --primary: #4f46e5;
            --bg: #f8fafc;
            --text: #1e293b;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--bg);
            color: var(--text);
            margin: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            min-height: 100vh;
        }

        header {
            width: 100%;
            padding: 2rem 0;
            text-align: center;
            background: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            margin-bottom: 3rem;
        }
        
        .logout-btn {
		    background: #ef4444;
		    color: white;
		    border: none;
		    padding: 8px 16px;
		    border-radius: 6px;
		    cursor: pointer;
		    font-size: 0.9rem;
		    margin-top: 10px;
		}
		.logout-btn:hover {
		    background: #dc2626;
		}

        .welcome-msg {
            font-size: 1.5rem;
            font-weight: 600;
        }

        .user-name {
            color: var(--primary);
            text-decoration: underline;
        }

        .grid-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 2rem;
            width: 90%;
            max-width: 1000px;
        }

        .card {
            background: white;
            padding: 2rem;
            border-radius: 12px;
            text-align: center;
            text-decoration: none;
            color: inherit;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s, box-shadow 0.2s;
            border: 1px solid #e2e8f0;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
            border-color: var(--primary);
        }

        .icon {
            font-size: 3rem;
            margin-bottom: 1rem;
            display: block;
        }

        .card h3 {
            margin: 0.5rem 0;
            color: var(--primary);
        }

        .card p {
            font-size: 0.9rem;
            color: #64748b;
        }
    </style>
</head>
<body>

    <header>
        <div class="welcome-msg">
            Welcome back, <span class="user-name">
                <c:out value="${sessionScope.userId}" default="Guest" />
            </span>!
        </div>
        <a href="<%=request.getContextPath() %>/auth/logout" style="font-family:garamond;font-size:25px;margin-left:700px;color:black;text-decoration: none;">Logout</a></b>				
    </header>

    <main class="grid-container">
        <a href="<%=request.getContextPath() %>/book" class="card">
            <img src="${pageContext.request.contextPath}/image/book_image.jpg" alt="book image" height="250px" width="230px"><br>
            ### Books
            <p>Save your favorite reads and authors.</p>
        </a>

        <a href="<%=request.getContextPath() %>/movie" class="card">
            <img src="${pageContext.request.contextPath}/image/movie_image.jpg" alt="movie image" height="250px" width="230px"><br>
            ### Movies
            <p>Keep track of films and documentaries.</p>
        </a>

        <a href="<%=request.getContextPath() %>/weblink" class="card">
            <img src="${pageContext.request.contextPath}/image/weblink_image.jpg" alt="weblink image" height="250px" width="230px"><br>
            ### Web Links
            <p>Save articles, tools, and websites.</p>
        </a>
    </main>

</body>
</html>