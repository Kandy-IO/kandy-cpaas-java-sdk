<html>
<head>
  <title>2FA</title>
  <link rel="stylesheet" type="text/css" href="public/stylesheets/main.css">
</head>
<body>
<%@ include file="alert.jsp" %>
<form action="/login" method="post" class="box centered-box">
  <h2 class="text-center">Login</h2>
  <div class="input-group">
    <label for="email">Email</label>
    <input type="text" id="email" name="email" />
  </div>
  <div class="input-group">
    <label for="password">Password</label>
    <input type="password" id="password" name="password" />
  </div>
  <button type="submit">Login</button>
</form>

</body>
</html>