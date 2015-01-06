 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<form:form commandName="searchCompany" action="searchcompany.html">

    <form:errors path="*" cssClass="show_validity none_display" element="p" />
        <div class="tab_gradient"></div>
        <div class="info_group">
            <div class="col_left">
                <label><spring:message code="company.field.companyId" /></label>
                <form:input path="companyId" cssClass="input_text" onfocus="disable_textbox(this)"  />
            </div>
            <div class="col_center">
                <label><spring:message code="company.field.title" /></label>
                <form:input path="title" id="companytitle" cssClass="input_text" onfocus="disable_textbox(this)"  />
            </div>
            <div class="col_right">
                <label>Status :</label>
                <form:select path="status" id="filterStatus">
                    <form:option value="all">Vis alle</form:option>
                    <form:option value="1">Aktiv</form:option>
                    <form:option value="0">Inaktiv</form:option>
                    <form:option value="2">Slett</form:option>
                </form:select>
            </div>
            <div class="col_left">&nbsp;</div><div class="col_center">&nbsp;</div>
            <div class="col_right">
                <br/>
                <input type="submit" class="btn_gray align_right2 strong" value=<spring:message code="btn.search" />/>
            </div>
            <br class="clear" />
            <br class="clear" />
        </div>

</form:form>            