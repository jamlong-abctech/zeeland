<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="template/header.jsp" %>

    <form:form commandName="extendedUserForm" action="saveuser.html" name="showuser">

        <div class="info" id="user_data">
            <div id="title">
                <h2><spring:message code="showuser.field.id"/> ${zettUser.userId}</h2>
            </div>

            <form:errors path="*" cssClass="show_validity none_display" element="p" />
            <fieldset>
                <legend><spring:message code="showuser.user.name"/></legend>
                <div class="tab_gradient"></div>
                <div class="info_group">
                    <div class="col_left">
                        <label><spring:message code="showuser.field.name"/></label>
                        <form:hidden path="userId" value="${zettUser.userId}"/>
                        <form:input path="name" /><span class="mark_red"> *</span><br/><br/>
                    </div>
                    <div class="col_center">
                        <label><spring:message code="showuser.field.email"/></label>
                        <form:input path="email" /><span class="mark_red"> *</span><br/><br/>
                    </div>
                    <br class="clear" />
                    <div class="col_left">
                        <label><spring:message code="showuser.field.telephone"/></label>
                        <form:input path="phone"/><br/><br/>
                    </div>
                    <div class="col_center">
                        <label><spring:message code="showuser.field.mobile"/></label>
                        <form:input path="mobile"/>

                    </div>
                    <div class="col_right">
                        <label><spring:message code="showuser.field.fax"/></label>
                        <form:input path="fax"/>
                    </div>

                    <br class="clear" />

                    <c:if test="${registerTime!=null}" >
                    <div class="col_left">
                         <label><spring:message code="showuser.field.registered"/></label>&nbsp;&nbsp; ${registerTime}
                    </div>
                    <div class="col_center">
                         <label><spring:message code="showuser.field.modified"/></label>&nbsp;&nbsp; ${modifiedTime}
                    </div>
                    </c:if>
                    <br class="clear" />
                    <div class="align_right">
                        <input class="btn_save_sm" type="submit" name="btnsubmit" value="LAGRE" />
                    </div>
               </div>
            </fieldset>

            <fieldset>
                <legend><spring:message code="showuser.user.data"/></legend>
                <div class="tab_gradient"></div>
                <div class="info_group">

                    <div class="col_left">
                        <label><spring:message code="showuser.field.type"/></label>
                        <form:select path="userTypeValue" id="slcusertype" onchange="displayUserTypeProperty()">
                         <form:option value="2"  label="PROUSER" />
                         <form:option value="1" label="PRIVATEUSER" />
                        </form:select><br/><br/>

                    </div>
                    <div class="col_center">
                        <label><spring:message code="showuser.field.fraud.status"/></label>
                        <form:select path="fraudstatus" id="slcfraudstatus">
                            <form:option value="NOT_SET" label="NOT_SET" />
                            <form:option value="BLACKLISTED" label="BLACKLISTED" />
                            <form:option value="WHITELISTED" label="WHITELISTED" />
                        </form:select>
                    </div>
                    <div class="col_right">
                        <label><spring:message code="showuser.field.user.status"/></label>
                        <form:select path="statusValue" id="slcuserstatus">
                            <form:option value="0" label="INACTIVE" />
                            <form:option value="1" label="ACTIVE" />
                            <form:option value="2" label="NEVER_LOGGED_IN" />
                            <form:option value="3" label="DISABLED" />
                            <form:option value="4" label="UNAUTHORIZED" />
                        </form:select><br/>

                        <input type="button" class="btn_gray align_right2 " onclick="findTextAndSelect('slcuserstatus','INACTIVE')" value=<spring:message code="showuser.btn.inactive"/> />
                        &#160;<input type="button" class="btn_gray align_right2" onclick="findTextAndSelect('slcuserstatus','ACTIVE')" value=<spring:message code="showuser.btn.active"/> />

                    </div>

                    <br class="clear" />

                    <c:if test="${extendedUserForm.userTypeValue != '1'}">
                        <div id="table_user_type" class="box_gray">
                            <div class="col_left_inner">
                                <label><spring:message code="showuser.field.company.id"/></label>
                                <form:input path="company.objectId" id="txtcompanyobjectid"/><br/>
                            </div>
                            <div class="col_center">
                            <label><spring:message code="showuser.field.company.title"/></label><br/><br/>
                                <c:choose>
                                    <c:when test="${zettUser.company!=null&&zettUser.company.title!=null}">
                                        ${zettUser.company.title}
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="showuser.field.n.a"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="col_right">
                                <label><spring:message code="showuser.field.company.access"/></label>
                                <form:input path="companyAccess" id="txtcompanyaccess"/><br/>
                            </div>
                            <br class="clear" />
                        </div>
                    </c:if>


                    <div class="align_right">
                        <input class="btn_save_sm" type="submit" name="btnsubmit" value="LAGRE" />
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <legend><spring:message code="showuser.user.access"/></legend>
                <div class="tab_gradient"></div>
                <div class="info_group">
                    <div class="col_left">
                        <label><spring:message code="showuser.field.user.access"/></label><br/>
                        <form:checkboxes items="${userAccessMap}" delimiter="<br/>"  path="rightArray" />
                    </div>
                    <div class="col_center">
                        <label><spring:message code="showuser.field.new.password"/></label>
                        <form:input path="password" id="txtpassbox" name="txtpassbox" tabindex="1"/><span class="mark_red"> *</span>

                        <input type="button"  class="btn_gray align_right3" onclick="createPassword()"  value=<spring:message code="showuser.btn.generating"/> />

                        <c:if test="${extendedUserForm.userId!=null}">
                            &#160;<input type="button"  class="btn_gray align_right2" onclick="savePassword()"    value=<spring:message code="showuser.btn.save.password"/> />
                        </c:if>

                    </div>

                    <br class="clear" />

                    <div class="align_right">
                        <input class="btn_save_sm" type="submit" name="btnsubmit" value="LAGRE" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <legend><spring:message code="showuser.user.ads"/></legend>
                <div class="tab_gradient"></div>
                <div class="info_group">

                    <table id="userAdsTable">
                        <thead>
                            <tr>
                                <th class="col9">
                                    <spring:message code="ad.field.adId" />
                                </th>
                                <th>
                                    <spring:message code="ad.field.adTitle" />
                                </th>
                                <th class="col10">
                                    <spring:message code="ad.field.pubStatus" />
                                </th>
                                <th class="col10">
                                    <spring:message code="ad.field.linkZeelandSite" />
                                </th>
                                <th class="col10">
                                    <spring:message code="ad.field.linkZettSite" />
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                           <c:forEach var="userAd" items="${zettUser.userAds}" begin="0" end="4" varStatus="rowUserAds">
                           <tr id="initial" style="visibility:visible;">
                                <td>
                                    ${userAd.adObjectId}
                                </td>
                                <td>
                                    ${userAd.title}
                                </td>
                                <td>



                                     <c:if test="${userAd.status != null}">
                                         <c:if test="${userAd.status =='0'}">
                                           <c:out value="Inaktiv" />
                                         </c:if>

                                         <c:if test="${userAd.status =='1'}">
                                           <c:out value="Aktiv" />
                                         </c:if>
                                          <c:if test="${userAd.status =='2'}">
                                           <c:out value="Slettet" />
                                         </c:if>
                                          <c:if test="${userAd.status =='3'}">
                                           <c:out value="Blokkert" />
                                         </c:if>

                                         <c:if test="${userAd.status =='4'}">
                                           <c:out value="Solgt" />
                                         </c:if>

                                         <c:if test="${userAd.status =='5'}">
                                           <c:out value="Påbegynt" />
                                         </c:if>

                                         <c:if test="${userAd.status =='6'}">
                                           <c:out value="Påvent" />
                                         </c:if>

                                         <c:if test="${userAd.status =='7'}">
                                           <c:out value="Utgått" />
                                         </c:if>
                                         <c:if test="${userAd.status =='8'}">
                                           <c:out value="Ikke autorisert" />
                                         </c:if>

                                     </c:if>
                                </td>
                                <td>
                                   <a href="${zeelandUrl}${userAd.adObjectId}"><spring:message code="showuser.link.view"/></a>
                                </td>
                                <td>
                                    <a href="${zettUrl}${userAd.adObjectId}" target="_blank"><spring:message code="showuser.link.view"/></a>
                                </td>
                            </tr>

                        </c:forEach>
                        <c:forEach var="userAd" items="${zettUser.userAds}" begin="5" end="${fn:length(zettUser.userAds)}" varStatus="rowUserAds">
                           <tr class="detail" style="display:none;">
                                <td>
                                    ${userAd.adObjectId}
                                </td>
                                <td>
                                    ${userAd.title}
                                </td>
                                <td>


                                     <c:if test="${userAd.status != null}">
                                         <c:if test="${userAd.status =='0'}">
                                           <c:out value="Utgått" />
                                         </c:if>

                                         <c:if test="${userAd.status =='1'}">
                                           <c:out value="Publisert" />
                                         </c:if>
                                          <c:if test="${userAd.status =='2'}">
                                           <c:out value="Påvent" />
                                         </c:if>
                                          <c:if test="${userAd.status =='3'}">
                                           <c:out value="Administrativf Fjernet" />
                                         </c:if>

                                         <c:if test="${userAd.status =='4'}">
                                           <c:out value="Påbegynt" />
                                         </c:if>

                                         <c:if test="${userAd.status =='5'}">
                                           <c:out value="Slettet" />
                                         </c:if>

                                         <c:if test="${userAd.status =='6'}">
                                           <c:out value="Vill Utløpe" />
                                         </c:if>

                                         <c:if test="${userAd.status =='7'}">
                                           <c:out value="Solgt" />
                                         </c:if>
                                         <c:if test="${userAd.status =='8'}">
                                           <c:out value="Inaktiv" />
                                         </c:if>
                                         <c:if test="${userAd.status =='9'}">
                                           <c:out value="Ikke autorisert" />
                                         </c:if>
                                     </c:if>
                                </td>
                                <td>
                                   <a href="${zeelandUrl}${userAd.adObjectId}"><spring:message code="showuser.link.view"/></a>
                                </td>
                                <td>
                                    <a href="${zettUrl}${userAd.adObjectId}" target="_blank"><spring:message code="showuser.link.view"/></a>
                                </td>
                            </tr>

                        </c:forEach>
                        </tbody>
                    </table>

                    <div class="align_right">
                        <a id="btnReadMore" class="btn_gray width_50"><spring:message code="showuser.btn.show.readmore"/></a> <a id="btnHidden" class="btn_gray width_50"><spring:message code="showuser.btn.hide.readmore"/></a>
                    </div>
                </div>
            </fieldset>
        </div>
        <br class="clear" />
        <span class="txt_red"><spring:message code="validation.feild.required"/></span>
        <br/><br/><br/><br/>
        <div class="align_center">
            <input class="btn_save" type="submit" name="btnsubmit" value=<spring:message code="showuser.btn.save"/> /><br/><br/>
        </div>


    </form:form>

    <form id="submitpasswordform" action ="savepassword.html" method="post">
        <input type="hidden" id = "userid" name = "userId"  value="${extendedUserForm.userId}"/>
        <input type="hidden" id = "password" name = "password" value=""/>

    </form>
    <br class="clear"/><br/><br/>
      <form id="deleteuserform" action ="deleteuser.html" method="post">

        <div class="col_borderred align_right">
                       <img src="images/bin.png" class="bin" />
                       <input type="submit" class="btn_red" value="Slett denne brukeren" onclick="deleteUser()" /><br/>
                       <input type="hidden" id = "delid" name = "delid"  value="${extendedUserForm.userId}"/>
                       <p class="txt_red space_top">Hvis du vil slette denne brukeren.</p>

         </div>


       </form>



<%@ include file="template/footer.jsp" %>