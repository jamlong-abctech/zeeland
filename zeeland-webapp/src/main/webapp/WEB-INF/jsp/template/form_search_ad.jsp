 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form:form commandName="searchAd" action="searchad.html">

    <form:errors path="*" cssClass="show_validity none_display" element="p"/>
        <div class="tab_gradient"></div>
        <div class="info_group">
            <div class="col_left">
                <label><spring:message code="form.searchAd.adId" /></label>
                <form:input path="adId" cssClass="input_text" onfocus="disable_textbox(this)"  />
            </div>
            <div class="col_center">
                <label><spring:message code="form.searchAd.extRef" /></label>
                <form:input path="extRef" cssClass="input_text" onfocus="disable_textbox(this)"  />
            </div>
            <div class="col_right">
               <label><spring:message code="form.searchAd.clientEmail" /></label>
               <form:input path="clientEmail" cssClass="input_text" onfocus="disable_textbox(this)"  />
             </div>
              <br class="clear"/><br/>

                             <div class="col_left">
                                 <label><spring:message code="form.searchPrintAd.printAdId" /></label>
                                <form:input path="printAdId" cssClass="input_text" onfocus="disable_textbox(this)"  />
                             </div>
                             <div class="col_center">
                                 <label>OrderID :</label>
                                   <form:input path="orderId" cssClass="input_text" onfocus="disable_textbox(this)"  />
                             </div>
                             <div class="col_right">

                             </div>


            <br class="clear"/><br/>

            <div class="col_left">
                <label><spring:message code="form.searchAd.keyword" /></label>
                <form:input path="searchKeyword" cssClass="input_text" onfocus="disable_textbox(this)"  />
                <br/>
                <p class="space_top"><spring:message code="form.searchAd.keyword.description" /></p>
            </div>
            <div class="col_center">
                <label><spring:message code="showad.field.ad.status"/></label>
                <form:select path="statusValue" id="adstatusfilter">

                        <form:option label="Vis alle" value="-1"/>
                        <form:option label="Aktiv" value="1"/>
                        <form:option label="Inaktiv" value="0"/>
                        <form:option label="Slettet" value="2"/>
                        <form:option label="Blokkert" value="3"/>
                        <form:option label="Solgt" value="4"/>
                        <form:option label="Påbegynt" value="5"/>
                        <form:option label="Påvent" value="6"/>
                        <form:option label="Utgått" value="7"/>
                        <form:option label="Ikke autorisert" value="8"/>

                </form:select>

            </div>
            <div class="col_right">
                <br />
                <input type="submit" class="btn_gray align_right2 strong" value=<spring:message code="btn.search" />/>
            </div>

        </div>

</form:form>