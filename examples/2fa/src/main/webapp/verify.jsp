<html>
  <head>
    <title>2FA | Verify</title>
    <link rel="stylesheet" type="text/css" href="public/stylesheets/main.css">
  </head>
  <body>
    <%@ include file="alert.jsp" %>
    <div class="box centered-box">
      <form action="/verify" method="post">
        <h2 class="text-center">Verify</h2>
        <div class="input-group">
          <label for="code">Verification code</label>
          <input type="text" id="code" name="code" />
          <button type="submit">Verify</button>
        </div>
      </form>
      <hr>
      <form action="/verify/sendcode" method="post">
        <div class="input-group:nowrap">
          <input type="radio" name="codeType" value="sms" checked/> 2FA via sms
          <input type="radio" name="codeType" value="email" /> 2FA via email
          <button class='verify-button' type="submit">Send 2FA</button>
        </div>
      </form>
    </div>
  </body>
</html>