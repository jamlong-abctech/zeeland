<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="template/header.jsp" %>

    <h3 class="page_title"><spring:message code="showzettcommon.title" /></h3>
    <br />
    <table>
        <thead>
            <tr>
                <th class="width_200"><spring:message code="showzettcommon.header.name"/></th>
                <th class="width_200"><spring:message code="showzettcommon.header.type"/></th>
                <th class="width_200"><spring:message code="showzettcommon.header.label"/></th>
            </tr>
        </thead>
        <c:forEach var="type" items="${mapAttribute}">
                <tr>
                    <td>${type.name}</td>
                    <td>${type.type}</td>
                    <td>${type.label}</td>
                </tr>
        </c:forEach>
    </table>

<%@ include file="template/footer.jsp" %>