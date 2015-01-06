 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="template/header.jsp" %>

    <c:if test="${noresult!=null}">
        <div class="show_validity none_display" element="p" ><spring:message code="${noresult}" /> "${errorinput}"</div>
    </c:if>
       <c:if test="${errormsg!=null}">
            <div class="show_validity none_display" element="p" ><spring:message code="${errormsg}" />    ${errorinput}</div>
        </c:if>
    <div class="info" id="company_info">
        <div id="title">
            <h2><spring:message code="page.title.searchAd" /></h2>
        </div>
        <fieldset>
        <legend><spring:message code="page.title.searchAd" /></legend>
        <%@ include file="template/form_search_ad.jsp" %>
        </fieldset>
    </div>

<%@ include file="template/footer.jsp" %>

