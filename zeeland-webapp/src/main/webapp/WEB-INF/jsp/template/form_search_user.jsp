
<form:form commandName="extendedUser" action="showuser.html" method="post">

    <form:errors path="*" cssClass="show_validity none_display" element="p"/>
    <div class="tab_gradient"></div>
    <div class="info_group">
        <div class="col_left">
            <label><spring:message code="form.searchUser.userId" /></label>
            <form:input path="userId" cssClass="input_text" onfocus="disable_textbox(this)"  />
        </div>
        <div class="col_center">
            <label><spring:message code="form.searchUser.userEmail" /></label>
                <form:input path="email" cssClass="input_text" onfocus="disable_textbox(this)" />
        </div>
        <div class="col_right">
            <br/>
            <input type="submit" class="btn_gray align_right2 strong" value=<spring:message code="btn.search" />/>
        </div>
    </div>
</form:form>            