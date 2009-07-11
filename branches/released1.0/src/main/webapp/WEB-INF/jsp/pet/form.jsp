<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<link type="text/css" href="<c:url value='/styles/base/ui.all.css'/>" rel="stylesheet" />
<script type="text/javascript" src="<c:url value='/js/jquery-1.3.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/ui/ui.core.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/ui/ui.datepicker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/ui/ui.datepicker-th.js'/>"></script>

<script type="text/javascript">
    $(function() {
        $.datepicker.setDefaults($.extend({showMonthAfterYear: false}, $.datepicker.regional['th']));

        $("#birthDate").datepicker({
            dateFormat:'yy-mm-dd',
            changeMonth: true,
            changeYear: true
        });
       
    });
</script>

<h2><c:if test="${pet.new}">New </c:if>Pet</h2>

<b>Owner:</b> ${pet.owner.firstName} ${pet.owner.lastName}
<br/>

<form:form action="update" modelAttribute="pet">
    <table>
        <tr>
            <th>
                Name: <form:errors path="name" cssClass="errors"/>
                <br/>
                <form:input path="name" size="30" maxlength="30"/>
            </th>
        </tr>
        <tr>
            <th>
                Birth Date: <form:errors path="birthDate" cssClass="errors"/>
                <br/>
                <form:input path="birthDate" size="10" maxlength="10"/> (yyyy-mm-dd)
            </th>
        </tr>
        <tr>
            <th>
                Type: <form:errors path="type" cssClass="errors"/>
                <br/>
                <form:select path="type" items="${petTypeList}"/>
            </th>
        </tr>
        <tr>
            <td>
        <c:choose>
            <c:when test="${pet.new}">
                <p class="submit"><input type="submit" value="Add Pet"/></p>
            </c:when>
            <c:otherwise>
                <p class="submit"><input type="submit" value="Update Pet"/></p>
            </c:otherwise>
        </c:choose>
    </td>
</tr>
</table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
