<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<img src="./images/pets.png" align="right" style="position:relative;right:30px;">
<h2>Hello World</h2>

<ul>
  <li><a href="<c:url value="/main/owner/find"/>">Find owner</a></li>
  <li><a href="<c:url value="/main/vet/list"/>">Display all veterinarians</a></li>
  <li><a href="<c:url value="/html/petclinic.html"/>">Tutorial</a></li>
  <li><a href="<c:url value="/docs/index.html"/>">Documentation</a></li>
</ul>

<p>&nbsp;</p>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
