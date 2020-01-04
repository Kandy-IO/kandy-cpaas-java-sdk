<% if (request.getSession().getAttribute("alertType") != null) {%>
  <div class="alert alert-<%= request.getSession().getAttribute("alertType") %>">
    <p><%= request.getSession().getAttribute("alertMessage") %></p>
  </div>
<% } %>
<%
  request.getSession().setAttribute("alertType", null);
  request.getSession().setAttribute("alertMessage", null);
%>