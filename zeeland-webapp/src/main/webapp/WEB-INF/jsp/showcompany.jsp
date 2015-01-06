<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tjenester.api.no/obscura" prefix="obscura"%>

<%@ include file="template/header.jsp" %>
<script type="text/javascript">
    function deleteAttribute(attributeId){
        var status = confirm('<spring:message code="delete.attribute.warning"/>');
        if(status){
            $("#deleteAttributeId").val(attributeId)
            $("#deleteAttributeForm").submit();
        }
    }
</script>

    <form:form commandName="company" id="companyForm" name="companyForm" action="savecompany.html" enctype="multipart/form-data" method="POST">
        <div class="info" id="company_info">

            <div id="title">
                <c:if test = "${company.objectId != null}">
                    <c:choose>

                    <c:when test ='${company.status == "ACTIVE"}' >
                        <div id="show_status"><span>Status : </span>
                            <span class="status_green">Active</span>
                            <img src="images/icon-status-green.png" alt="status green" />
                        </div>
                    </c:when>

                     <c:otherwise>
                         <div id="show_status"><span>Status : </span>
                             <span class="status_red">Inactive</span>
                             <img src="images/icon-status-red.png" alt="status red" />
                         </div>
                     </c:otherwise>
                     </c:choose>
                </c:if>
                <h2><spring:message code="showcompany.id"/> : ${zettCompanyObject.objectId}</h2>
            </div>
            <div>
                <form:errors path="*" cssClass="show_validity none_display" element="p"  ></form:errors>
                <c:if test ="${!empty overrideValidator}" >
                    <div class="line_red">
                    <a href="#" class="btn_gray" onclick="submitWithNoValidation();"><spring:message code="showcompany.save.anyway"/></a>
                    </div>
                    <input type="hidden" name="skipValidator" id="skipValidator" value="">
                </c:if>
                <c:if test ="${!empty deleteMessage}" >
                        <div class="show_validity none_display" element="p" ><spring:message code="${deleteMessage}"/></div>
                </c:if>
            </div>

            <fieldset>
                <legend><spring:message code="showcompany.company"/></legend>
                <div class="tab_gradient"></div>
                <div class="info_group">
                    <div class="col_left">
                        <label><spring:message code="showcompany.field.title"/></label>
                                <form:input path="title" />
                                <form:hidden path="createdBy" />
                                <span class="mark_red"> *</span><br/><br/>
                           <label><spring:message code="showcompany.field.primary.addr"/></label>
                                <form:input path="address.primaryAddress"  />
                                <span class="mark_red"> *</span><br/><br/>
                           <label><spring:message code="showcompany.field.second.addr"/></label>
                                <form:input path="address.secondaryAddress"  />
                                <br/><br/>
                           <label><spring:message code="showcompany.field.zip.code"/></label>
                                <form:input path="address.postCode"  />
                                <span class="mark_red"> *</span><br/><br/>
                           <label><spring:message code="showcompany.field.city"/></label>
                                    <form:input path="address.postLocation"  />
                                <span class="mark_red"> *</span><br/><br/>
                           <label><spring:message code="showcompany.field.geography"/></label>
                                    <form:input path="address.geography"  />
                                    <br/><br/>
                    </div>
                    <div class="col_center">
                       <label><spring:message code="showcompany.field.home"/></label><br/><br/>
                            <c:if test="${!empty company.attributes}">
                                <c:choose>
                                    <c:when test="${fn:length(company.attributes)>0}">
                                        <c:forEach var="i" begin="0" end="${fn:length(company.attributes)-1}">
                                            <c:if test="${company.attributes[i].name == 'homepage'}">
                                                <c:if test="${!empty company.attributes[i].value}">
                                                    <a href="${company.attributes[i].value}" id="company_url_link" target="_blank">
                                                         ${company.attributes[i].value}
                                                    </a>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                         <spring:message code="showcompany.field.n.a"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <c:if test="${empty company.attributes}">
                                <spring:message code="showcompany.field.n.a"/>
                            </c:if>
                            <br/><br/>
                       <label><spring:message code="showcompany.field.map.lookup"/></label><br/><br/>
                            <c:forEach var="attribute" items="${company.attributes}">
                               <c:if test="${attribute.name =='mappreviewurl'}">
                                   <img src="${attribute.value}" class="border_gray width="274" height="220"" alt="map" />
                               </c:if>
                            </c:forEach><br/><br/>
                       <label><input type="button"  id="map" class="btn_gray align_right3" name="map" onclick="retrieveMap('${zettCompanyObject.objectId}')" value=<spring:message code="showcompany.btn.map.lookup"/> /></label>
                    </div>
                    <div class="col_right_bggray">
                       <label><spring:message code="showcompany.field.logo"/></label>
                       <br/><br/>
                       <c:forEach var="media" items="${company.objectMediaList}">
                           <c:if test="${media.typeValue == 2}">
                               <obscura:createImageUrl backend="zett" version="${imageLogoVersion}"
                                    revision="${company.modifiedTime.time}" uid="${media.reference}" var="companyLogoUrl" />
                               <img id="company_logo" src="${companyLogoUrl}" class="border_gray max_logo" alt="company logo"/>
                               <c:set var="logoMediaId" value="${media.mediaId}" scope="page" />
                           </c:if>
                       </c:forEach><br/>
                       <c:if test="${!empty logoMediaId}">
                            <input class="btn_red" type="button" value=<spring:message code="showad.btn.delete"/> onclick="deleteCompanyLogo('${logoMediaId}')" />
                       </c:if><br/><br/>
                       <label><spring:message code="showcompany.upload.logo"/></label>
                        <input id="fileToUpload" type="file"  name="fileCompanyLogo" accept="image/gif"/>
                        <br/>
                        <br/><br/>
                          <label><spring:message code="showcompany.dumpobject"/></label> <a href="${zservicesurl}xml/getCompanyObject?objectId=${company.objectId}" target="_blank"><img src="images/xml_icon.png" class="space_top2"/></a>
                                                <br/><br/>
                        <label><spring:message code="showcompany.field.created"/></label>
                                <c:choose>
                                    <c:when test="${company.createdTime!=null}">
                                        ${company.createdTimeString}
                                    </c:when>
                                    <c:otherwise>
                                        N/A
                                    </c:otherwise>
                                </c:choose>
                                <br/><br/>
                        <label><spring:message code="showcompany.field.modified"/></label>
                                <c:choose>
                                    <c:when test="${company.modifiedTime!=null}">
                                        ${company.modifiedTimeString}
                                    </c:when>
                                    <c:otherwise>
                                        N/A
                                    </c:otherwise>
                                </c:choose>
                                <br/><br/>
                        <label><spring:message code="showcompany.field.system.systemModifiedTime"/></label>
                                <c:choose>
                                    <c:when test="${company.systemModifiedTime!=null}">
                                        ${company.systemModifiedTimeString}
                                    </c:when>
                                    <c:otherwise>
                                        N/A
                                    </c:otherwise>
                                </c:choose>
                                <br/><br/>
                    </div>
                    <br class="clear" />

                    <div class="align_right">
                        <input class="btn_save_sm" type="submit" name="btnsubmit" value=<spring:message code="showcompany.btn.save"/> />
                    </div>
                     <!-- ${zettCompanyObject.objectId}-->
                    <form:hidden path="objectId" value="${zettCompanyObject.objectId}" />

                    <form:hidden path="packageDealTypeValue" value="${company.packageDealTypeValue}" />
                </div>
            </fieldset>
            <fieldset>
                <legend>Firma instillinger</legend>
                <div class="tab_gradient"></div>
                <div class="info_group">
                    <div class="col_left">
                       <label><spring:message code="showcompany.field.category"/></label>
                                <form:select path="category">
                                    <form:option value="Firmaer/Aviser" label="Aviser" />
                                    <form:option value="Firmaer/Bilforhandlere" label="Bilforhandlere" />
                                    <form:option value="Firmaer/B&aring;tforhandlere" label="Båtforhandlere" />
                                    <form:option value="Firmaer/Eiendomsmeglere" label="Eiendomsmeglere" />
                                    <form:option value="Firmaer/Frivillighetsportalen" label="Frivillighetsportalen" />
                                    <form:option value="Firmaer/Innholdspartnere" label="Innholdspartnere" />
                                    <form:option value="Firmaer/Internt" label="Internt" />
                                    <form:option value="Firmaer/Motorsykkelforhandlere" label="Motorsykkelforhandlere" />
                                    <form:option value="Firmaer/PBL" label="PBL" />
                                    <form:option value="Firmaer/Sm&aring;ttogstortforhandlere" label="Småttogstort" />
                                    <form:option value="Firmaer/Stillingsutlysere" label="Stillingsutlysere" />
                                </form:select><br/><br/>
                    </div>
                    <div class="col_center">
                       <label><spring:message code="showcompany.field.status"/></label><br/>
                                <form:select path="statusValue" id="slcstatus">
                                    <form:option value="1" label="Aktiv"/>
                                    <form:option value="0" label="Inaktiv"/>
                                    <form:option value="2" label="Slettet"/>
                                    <form:option value="3" label="Blokkert"/>
                                    <form:option value="4" label="Solgt"/>
                                    <form:option value="5" label="Påbegynt"/>
                                    <form:option value="6" label="Påvent"/>
                                    <form:option value="7" label="Utgått"/>
                                    <form:option value="8" label="Ikke autorisert"/>
                                </form:select><br/><br/>
                    </div>

                    <div class="col_right">
                        <input type="button" class="btn_gray" onclick="reindexAds('${zettCompanyObject.objectId}')"
                         value="<spring:message code="showcompany.btn.reindex"/>" />
                        <img id="img_preloadReindex" src="images/loading.gif" class="nonedisplay" /> <br/>
                        <p class="space_top"><strong><spring:message code="showcompany.note.note"/> </strong><spring:message code="showcompany.note.reindex.description"/></p>
                    </div>
                    <br class="clear" /><br/>

                    <div class="col_left">
                       <label><spring:message code="showcompany.field.main.company.id"/></label>
                               <form:input path="parentId" />
                    </div>
                    <div class="col_center">
                        <label><spring:message code="showcompany.field.billing.company"/></label>
                               <form:input path="billingCompanyId" />
                    </div>

                    <div class="col_borderred">
                        <input type="button" class="btn_red" onclick="deactivateAds('${zettCompanyObject.objectId}')"
                         value="<spring:message code="showcompany.btn.deactive"/>" />
                        <img id="img_preloadDeactive" src="images/loading.gif" class="nonedisplay" /><br/>
                        <p class="txt_red space_top"><strong><spring:message code="showcompany.note.note"/> </strong><spring:message code="showcompany.note.deactive.description"/></p>
                    </div>

                    <br class="clear" />
                    <c:if test="${company.objectId != null}">
                    <div class="box_gray">
                        <label>Link :</label>
                        <c:if test="${transitionURL != null}">
                            &#160;<img src="images/icon-arrow-link.png" alt="icon link" />&#160;
                            <a href="${transitionURL}/viewAd.html?zClientId=${zettCompanyObject.objectId}&token=${token}" target="_blank">
                                <spring:message code="showcompany.link.transition"/></a>
                        </c:if>
                        &#160;
                        <c:if test="${adURL != null}">
                            &#160;<img src="images/icon-arrow-link.png" alt="icon link" />&#160;
                            <a href="${adURL}" target="_blank">
                                 <spring:message code="showcompany.view.ads"/></a>
                        </c:if>
                    </div>
                    </c:if>
                    <div class="align_right">
                        <input class="btn_save_sm" type="submit" name="btnsubmit" value=<spring:message code="showcompany.btn.save"/> />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <legend><spring:message code="showcompany.contact"/></legend>
                <div class="tab_gradient"></div>
                <div class="info_group">
                    <div class="align_right">
                        <a onclick="displayContact()" class="btn_gray"><img src="images/icon-plus.png" alt="icon plus" /><spring:message code="showcompany.btn.addnewcontact"/></a>
                    </div>
                    <div id="add_contact" <c:if test="${extendedContact.name==null}">class="popup_form"</c:if>>
                         <div class="col_left_inner">
                         <label><spring:message code="showcompany.field.title"/></label>
                            <form:hidden path="extendedContact.attributesRaw[0].attributeId"  />
                            <form:hidden path="extendedContact.attributesRaw[0].typeValue"  />
                            <form:hidden path="extendedContact.attributesRaw[0].section"  />
                            <form:input path="extendedContact.attributesRaw[0].value"  /><br/><br/>
                         <label><spring:message code="showcompany.field.telephone"/></label>
                            <form:hidden path="extendedContact.attributesRaw[1].attributeId"  />
                            <form:hidden path="extendedContact.attributesRaw[1].typeValue"  />
                            <form:hidden path="extendedContact.attributesRaw[1].section"  />
                            <form:input path="extendedContact.attributesRaw[1].value"  />
                         </div>
                         <div class="col_center">
                         <label><spring:message code="showcompany.contact.name"/></label>
                            <form:hidden path="extendedContact.contactId"  />
                            <form:input path="extendedContact.name" /><br/><br/>
                         <label><spring:message code="showcompany.field.mobil"/></label>
                             <form:hidden path="extendedContact.attributesRaw[2].attributeId"  />
                             <form:hidden path="extendedContact.attributesRaw[2].typeValue"  />
                             <form:hidden path="extendedContact.attributesRaw[2].section"  />
                             <form:input path="extendedContact.attributesRaw[2].value"  />
                         </div>
                         <div class="col_right">
                         <label><spring:message code="showcompany.field.email"/></label>
                            <form:input path="extendedContact.email"  />
                            <br/><br/><br/>
                            <input class="btn_gray align_right2" type="submit" name="btnsubmit" onclick="displayAttribute()" value="Legg Til" />
                         </div>
                    </div>
                    <br class="clear" />
                    <br/>
                    <c:if test="${deleteMessage!=null}">
                     <div class="error"><spring:message code="${deleteMessage}"/></div>
                    </c:if>
                    <div class="equal">
                         <div class="head_row">
                             <div class="com_col1"><spring:message code="showcompany.contact.title"/></div>
                             <div class="com_col2"><spring:message code="showcompany.contact.name"/></div>
                             <div class="com_col3"><spring:message code="showcompany.contact.email"/></div>
                             <div class="com_col4"><spring:message code="showcompany.contact.telephone"/></div>
                             <div class="com_col5"><spring:message code="showcompany.contact.mobil"/></div>
                             <div class="com_col6"><spring:message code="showcompany.delete"/></div>
                         </div>
                         <c:if test="${fn:length(company.contacts)>0}">
                             <c:forEach var="i" begin="0" end="${fn:length(company.contacts)-1}">
                             <div class="body_row">
                                 <div class="com_col1">
                                     <form:hidden path="contacts[${i}].attributesRaw[0].attributeId"  />
                                     <form:hidden path="contacts[${i}].attributesRaw[0].typeValue"  />
                                     <form:input path="contacts[${i}].attributesRaw[0].value"  />
                                 </div>
                                 <div class="com_col2">
                                     <form:hidden path="contacts[${i}].contactId"  />
                                     <form:input path="contacts[${i}].name" />
                                 </div>
                                 <div class="com_col3">
                                     <form:input path="contacts[${i}].email"  />
                                 </div>
                                 <div class="com_col4">
                                     <form:hidden path="contacts[${i}].attributesRaw[1].attributeId"  />
                                     <form:hidden path="contacts[${i}].attributesRaw[1].typeValue"  />
                                     <form:input path="contacts[${i}].attributesRaw[1].value"  />
                                 </div>
                                 <div class="com_col5">
                                     <form:hidden path="contacts[${i}].attributesRaw[2].attributeId"  />
                                     <form:hidden path="contacts[${i}].attributesRaw[2].typeValue"  />
                                     <form:input path="contacts[${i}].attributesRaw[2].value"  />
                                 </div>
                                 <div class="com_col6">
                                     <c:if test="${company.contacts[i].contactId!=null}">
                                         <img src="images/icon-del.png"class="btnmini" onClick="deleteContacts('${company.contacts[i].contactId}',${zettCompanyObject.objectId})" />
                                     </c:if>
                                 </div>
                             </div>
                             </c:forEach>
                         </c:if>
                    </div>
                    <div class="align_right">
                        <input class="btn_save_sm" type="submit" name="btnsubmit" value=<spring:message code="showcompany.btn.save"/> />
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <legend><spring:message code="showcompany.object.attr"/></legend>
                <div class="tab_gradient"></div>
                <div class="info_group">
                    <div class="align_right">
                        <a onclick="displayAttribute()" class="btn_gray"><img src="images/icon-plus.png" alt="icon plus" /><spring:message code="showcompany.btn.addnewattribute"/></a>
                    </div>
                    <div id="add_attribute" <c:if test="${company.extendedAttribute.name==null}">class="popup_form"</c:if>>
                        <div class="col_1">
                            <label><spring:message code="showad.select.attribute"/></label>
                            <form:input path="extendedAttribute.name" class="forcol4"/>
                        </div>
                        <div class="col_2">
                            <label><spring:message code="showad.select.type"/></label>
                            <form:select path="extendedAttribute.typeValue" class="forcol4">
                                <form:option value="1" label="STRING"/>
                                <form:option value="2" label="URL"/>
                                <form:option value="3" label="INTEGER"/>
                                <form:option value="4" label="PRICE_NOK"/>
                                <form:option value="5" label="EMAIL"/>
                                <form:option value="6" label="PREFORMATTED_STRING"/>
                                <form:option value="7" label="LENGTH_FEET"/>
                                <form:option value="8" label="AREA_SIZE"/>
                                <form:option value="9" label="DOUBLE"/>
                                <form:option value="10" label="PRICE_EUR"/>
                                <form:option value="11" label="PRICE_USD"/>
                            </form:select>
                        </div>
                        <div class="col_2">
                        <label><spring:message code="showad.select.label"/></label>

                               <form:input path="extendedAttribute.label" class="forcol4" />
                        </div>
                        <div class="col_2">
                            <label><spring:message code="showad.enter.value"/></label>
                            <form:input path="extendedAttribute.value" class="forcol4"/>
                        </div>
                        <br class="clear" />
                        <div class="align_right2">
                            <input class="btn_gray align_right2" type="submit" name="btnsubmit" onclick="displayAttribute()" value="Legg Til" />
                        </div>
                        <br class="clear" />
                    </div>
                    <br class="clear" /><br/>
                    <div class="equal">
                        <div class="head_row">
                           <div class="att_col1"><spring:message code="showad.field.label"/></div>
                           <div class="att_col2"><spring:message code="showad.field.value"/></div>
                           <div class="att_col3"><spring:message code="showcompany.delete"/></div>
                        </div>
                        <c:if test="${!empty company.attributes}">
                            <c:forEach var="i" begin="0" end="${fn:length(company.attributes)-1}">
                                <div class="body_row">
                                        <form:hidden path="attributes[${i}].attributeId" />
                                        <form:hidden path="attributes[${i}].name" />
                                        <form:hidden path="attributes[${i}].label" />
                                        <form:hidden path="attributes[${i}].typeValue" />
                                        <div class="att_col1">${company.attributes[i].label}</div>
                                        <c:if test="${company.attributes[i].name != 'text'}">
                                            <div class="att_col2"><form:input path="attributes[${i}].value"/></div>
                                        </c:if>
                                        <c:if test="${company.attributes[i].name == 'text'}">
                                            <div class="att_col2"><form:textarea path="attributes[${i}].value" rows="5" cols="100" /></div>
                                        </c:if>
                                        <div class="att_col3 align_center">
                                            <a href="#" onclick="deleteAttribute('${company.attributes[i].attributeId}')" title="Slett attributt"><img src="images/icon-del.png" alt="Slett attributt" title="Slett attributt"/></a>
                                        </div>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </fieldset>


            <br class="clear" />
            <span class="txt_red"><spring:message code="validation.feild.required"/></span>
            <br/><br/><br/><br/>

            <div class="align_center">
                <input class="btn_save" type="submit" name="btnsubmit" value="LAGRE" /><br/><br/>
            </div>
        </div>
    </form:form>

       <form id="formdeletecontact" action ="deletecompanycontact.html" method="post">
            <input type="hidden" id="hdcompanyid" name="companyid"  value=""/><br/>
            <input type="hidden" id="hdcontactid" name="contactid"  value=""/><br/>
       </form>

       <form id="formdeleteCompanyLogo" action ="deletecompanymedia.html" method="post">
            <input type="hidden" id="hdcompanymediaid" name="companymediaid" value="${zettCompanyObject.objectId}"/><br/>
            <input type="hidden" id="hdmediaid" name="mediaid" value=""/><br/>
       </form>

       <form id="retrieveMapForm" action="showMap.html" method="post">
            <input type="hidden" id="mapcompanyid" name="mapcompanyid" value=""/></br>
       </form>

       <form id="reindexAds" action="reindex.html" method="post">
            <input type="hidden" id="reindexid" name="reindexid" value=""/></br>
       </form>

       <form id="deactivateAds" action="deactivatecompanyads.html" method="post">
            <input type="hidden" id="deactivateid" name="deactivateid" value=""/></br>
       </form>

        <form id="retrieveCompanyDumpObjectForm" action="retrieveCompDumpObject.html" method="post">
                    <input type="hidden" id="dumpCompanyObjId" name="dumpCompanyObjId" value="${company.objectId}"/><br/>
        </form>

        <form id="deleteAttributeForm" action="deletecompanyattribute.html" method="post">
            <input type="hidden" id="deleteObjectId" name="companyid" value="${company.objectId}" />
            <input type="hidden" id="deleteAttributeId" name="attributeid" value="" />
        </form>


<%@ include file="template/footer.jsp" %>
