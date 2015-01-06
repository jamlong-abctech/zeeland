<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="template/header.jsp" %>

    <div class="info" id="company_info">
        <div id="title">
            <h2><spring:message code="index.title" /></h2>
        </div>
        <fieldset>
            <legend><spring:message code="page.title.searchAd" /></legend>
            <%@ include file="template/form_search_ad.jsp" %>
        </fieldset>
        <fieldset>
            <legend><spring:message code="page.title.searchUser" /></legend>
            <%@ include file="template/form_search_user.jsp" %>
        </fieldset>
        <fieldset>
            <legend><spring:message code="page.title.searchCompany" /></legend>
            <%@ include file="template/form_search_company.jsp" %>
        </fieldset>
    </div>
<%@ include file="template/footer.jsp" %>