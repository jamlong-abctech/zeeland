<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="template/header.jsp" %>
    <h3 class="page_title"><spring:message code="fraudedit.add"/></h3>
    <form:form name="newbadword" commandName="fraudBadWord" action="savebadword.html" enctype="multipart/form-data" method="post">
        <form:errors path="*" cssClass="error" element="p" />
        <table class="table_info" width="100%">
            <tr>
                <td width="200" border="0"><spring:message code="fraudedit.word"/></td>
                <td border="0">
                    <form:input path="newBadWord"/>
                </td>
            </tr>
            <tr>
                <td>
                    <form:hidden path="badWord"/>
                </td>
                <td>
                    <input class="submitbtnmini" type="submit" name="btnsubmit" value='<spring:message code="showad.btn.save"/>'/>
                </td>
            </tr>
        </table>
    </form:form>

<%@ include file="template/footer.jsp" %>