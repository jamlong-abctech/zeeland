<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body>
<%@ include file="template/header.jsp" %>

    <div class="info">
        <form:form modelAttribute="searchAd" action="batchdeletead.html" id="batchdeleteadForm" onsubmit="return confirm('Are you sure you want to delete ad(s)?')">
            <c:if test="${result!=null}">
                <div class="show_info" element="p" >${result}</div>
            </c:if>
            <form:errors path="*" cssClass="show_validity none_display" element="p"/>
            <div id="title">
                <h2>Slette annonser</h2>
            </div>

            <fieldset>
                <legend>Slette annonser</legend>
                <div class="tab_gradient"></div>
                <div class="info_group">
                    <div class="col_2col align_left">
                        <label><spring:message code="delad.ad.list"/></label><br/>
                        <form:input type="hidden" path="batchAction" id="batchAction"/>
                        <form:textarea path="adIds" class="text_col2" rows="5" />
                        <spring:message code="delad.explain" />

                    </div>

                    <div class="col_right">
                        <br/>
                        <input type="button" onclick="performBatchForAds('delete');" class="btn_red" value="Slett alle&nbsp&nbsp&nbsp" />
                        <br/>
                        <input type="button" onclick="performBatchForAds('inactive');" class="btn_red" value="Inaktiv alls" />
                        <br/>
                        <input type="button" onclick="performBatchForAds('sold');" class="btn_red" value="Solgt alls&nbsp&nbsp" />
                    </div>

                    <br class="clear"/><br/>


                </div>
            </fieldset>

        </form:form>

        <br class="clear" />

    </div>

<%@ include file="template/footer.jsp" %>