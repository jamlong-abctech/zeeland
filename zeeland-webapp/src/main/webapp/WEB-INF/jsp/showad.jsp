<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tjenester.api.no/obscura" prefix="obscura" %>

<%@ include file="template/header.jsp" %>

<script language="javascript">

    function addNewRowToTable(tab) {
        $('#fileTab').append('<div><label><spring:message code="showad.field.add.photo"/></label> &nbsp;&nbsp;<input type="file" name="adImgs" onchange="showNewImg(this);" accept="image/*" /></div>');
    }

    function showNewImg(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                var newImg = '<div class="adimagelist"><img id="lastImg" />' +
                        '<p><input class="btn_gray" type="button" value="<spring:message code="showad.btn.set.main.media"/>"" />' +
                        '<input class="btn_red" type="button" value="<spring:message code="showad.btn.delete"/>" /></p></div>';
                $("#adimagelist").append(newImg);
                $('#lastImg').attr('src', e.target.result);
                $('#lastImg').removeAttr('id');
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    function generateAttribute(){
        var name = $("#slcattribute").val();
        $.getJSON("getattribute.html?name="+name,function(data) {
            var type;
            var label;
            $.each(data, function(key, value) {
                if(key == "type"){
                    type = value;
                    console.log("found type !!!! = "+type);
                }
                if(key == "label"){
                    label = value;
                    console.log("found label !!!! = "+label);
                }
            });
            $('#slcattributetype option').each(function(){
                if(this.text == type){
                    this.selected = true;
                }
            });
            $("#txtattributelabel").val(label);
        });
    }

    function deleteAttribute(attributeId){
        var status = confirm('<spring:message code="delete.attribute.warning"/>');
        if(status){
            $("#deleteAttributeId").val(attributeId)
            $("#deleteAttributeForm").submit();
        }
    }

    $(function () {
        $("#adimagelist").sortable({
//            connectWith:".connectedSortable"
            update: function(event, ui) {
                var order = $(this).sortable('toArray').toString();
                $("input#imageOrder").val(order);
            }
        });
    });


</script>

<form:form name="showad" commandName="extendedAdObject" action="savead.html" enctype="multipart/form-data"
method="post">
    <div class="info" id="ad_info">

            <div id="title">
                <h2><spring:message code="showad.field.id"/> ${extendedAdObject.objectId}</h2>
            </div>

            <form:errors path="*" cssClass="show_validity none_display" element="p"/>
            <c:if test="${!empty error}">
                <div class="show_validity none_display" element="p" ><spring:message code="${error}"/></div>
            </c:if>

        <form:hidden path="objectId"/>

            <fieldset>
                <legend><spring:message code="showad.data"/></legend>
                <div class="tab_gradient"></div>
                <div class="info_group">
                    <div class="col_2col align_left">
                        <label><spring:message code="showad.field.title"/></label>
                        <form:input path="title" class="forcol2"/><br/><br/>
                    </div>
                    <div class="col_right">
                        <label><spring:message code="showad.field.company.id"/></label><br/><br/>
                            ${zettAdObject.company.objectId} <br/><br/>
                    </div>

                    <br class="clear" />
                    <div class="col_left">
                        <label><spring:message code="showad.field.import.format"/></label><br/><br/>
                                ${zettAdObject.createdBy}<br/><br/>
                      <c:if test="${!empty fraudLogStatus}">
                         <label><spring:message code="fraudlog.status"/>:</label><br/><br/>
                                                        ${fraudLogStatus}<br/><br/>
                       </c:if>

                        <label><spring:message code="showad.field.transaction.status"/> (<spring:message code="showad.field.notInUse"/>)</label>
                        <form:select path="transactionStatusValue" id="slctransactionstatus" disabled="true" readonly="true">
                            <form:option value="0" label="Åpent"/>
                            <form:option value="1" label="Stengt"/>
                        </form:select>
                        <form:hidden path="transactionStatusValue"></form:hidden>
                        <br/><br/>

                        <label><spring:message code="showad.field.ad.status"/> (<spring:message code="showad.field.notInUse"/>)</label>
                        <form:select path="statusValue" id="slcadstatus" disabled="true" readonly="true">
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
                        <form:hidden path="statusValue"></form:hidden>
                        <br/><br/>

                        <label><spring:message code="showad.field.transition.type"/></label>
                        <form:select path="transactionTypeValue">
                            <form:option value="1" label="Til salgs"/>
                            <form:option value="2" label="Til leie"/>
                            <form:option value="3" label="Ønskes kjøpt"/>
                            <form:option value="4" label="Ønskes leid"/>
                            <form:option value="5" label="Byttes"/>
                            <form:option value="6" label="Gis bort"/>
                            <form:option value="7" label="Ønskes gitt bort"/>
                            <form:option value="8" label="Tjenester ønskes"/>
                            <form:option value="9" label="Tjenester utføres"/>
                            <form:option value="10" label="Tapt og funnet"/>
                            <form:option value="11" label="Stilling ledig"/>
                            <form:option value="12" label="Arbeid ønskes"/>
                            <form:option value="13" label="Arbeid utføres"/>
                            <form:option value="14" label="Annet"/>
                            <form:option value="15" label="Frivillig ledig"/>
                        </form:select>
                        <br/><br/>

                        <label><spring:message code="showad.field.publication.status"/></label>
                        <form:select path="publishingStatusValue" id="slcpublishingstatus">
                            <form:option label="Utgått" value="0"/>
                            <form:option label="Publisert" value="1"/>
                            <form:option label="Påvent" value="2"/>
                            <form:option label="Administrativf Fjernet" value="3"/>
                            <form:option label="Påbegynt" value="4"/>
                            <form:option label="Slettet" value="5"/>
                            <form:option label="Vill Utløpe" value="6"/>
                            <form:option label="Solgt" value="7"/>
                            <form:option label="Inaktiv" value="8"/>
                            <form:option label="Ikke autorisert" value="9"/>
                        </form:select>
                        <br/>

                        <input type="button" class="btn_gray align_right3" id="btnsold" name="btnsold"
                               onclick="changeAdObjectStatus('sold')" value=
                                <spring:message code="showad.btn.sold"/> />
                        <input type="button" class="btn_gray align_right2" id="btndeleted" name="btndeleted"
                                onclick="changeAdObjectStatus('deleted')" value=
                                     <spring:message code="showad.btn.delete"/> />
                        <input type="button" class="btn_gray align_right2" id="btninactive" name="btninactive"
                               onclick="changeAdObjectStatus('inactive')" value=
                                <spring:message code="showad.btn.inactive"/> />
                        <input type="button" class="btn_gray align_right2" id="btnactive" name="btnactive"
                               onclick="changeAdObjectStatus('active')" value=
                                <spring:message code="showad.btn.active"/> />
                    </div>
                    <div class="col_center">
                        <label><spring:message code="showad.field.external.referance"/></label><br/><br/>
                             <c:if test= "${fn:length(zettAdObject.externalReferences)>0}">
                                <c:choose>
                                    <c:when test= "${zettAdObject.externalReferences[0].reference != ''}">
                                        ${zettAdObject.externalReferences[0].reference}
                                    </c:when>
                                    <c:otherwise>
                                        N/A
                                    </c:otherwise>
                                </c:choose>
                             </c:if>
                       <br/><br/>
                        <label><spring:message code="showad.field.map"/></label><br/><br/>
                               <c:forEach var="attribute" items="${extendedAdObject.attributes}">
                                    <c:if test="${attribute.name=='mappreviewurl'}">
                                        <img src="${attribute.value}" class="border_gray" alt="map" width="274" height="220" />
                                    </c:if>
                               </c:forEach>
                        <br/>
                        <input type="button"  id="map" class="btn_gray align_right3" name="map" onclick="retrieveAdMap(${extendedAdObject.objectId})"
                         value=<spring:message code="showcompany.btn.map.lookup"/> />
                    </div>
                    <div class="col_right">
                        <label><spring:message code="showad.field.company.name"/></label><br/><br/>
                                ${zettAdObject.company.title}<br/><br/>

                        <label><spring:message code="showad.field.primary.address"/></label>
                                <form:hidden path="address.addressId"/>
                                <form:input path="address.primaryAddress"/><br/><br/>
                        <label><spring:message code="showad.field.secondary.address"/></label>
                                <form:input path="address.secondaryAddress"/><br/><br/>
                        <label><spring:message code="showad.field.zipcode"/></label>
                               <form:input path="address.postCode"/><br/><br/>
                        <label><spring:message code="showad.field.location"/></label>
                                <form:input path="address.postLocation"/><br/><br/>
                        <label><spring:message code="showad.field.geography"/></label>
                                <form:input path="address.geography"/>

                    </div>

                    <br class="clear" />

                    <div class="box_gray">
                        <div class="col_left_inner">
                           <label><spring:message code="showad.field.crated.date"/> </label>
                                ${extendedAdObject.createdTimeString}<br/><br/>
                           <label><spring:message code="showad.field.public.start"/> </label>
                               <form:input path="publishFromTimeString"/>
                        </div>
                         <div class="col_center">
                            <label><spring:message code="showad.field.changed.date"/> </label>
                                    ${extendedAdObject.modifiedTimeString}<br/><br/>
                            <label><spring:message code="showad.field.public.end"/> </label>
                                    <form:input id ="publishtotime" path="publishToTimeString"/>
                        </div>
                        <div class="col_right">
                            <label><spring:message code="showad.field.system.systemModifiedTime"/> </label> ${extendedAdObject.systemModifiedTimeString}
                            <br/><br/>
                            <label><spring:message code="showad.field.url"/> </label>
                            &nbsp;<img src="images/icon-arrow-link.png" alt="icon link" />&nbsp;<a href="${zettAdUrl}" target="_blank"><spring:message code="showad.link.zett"/></a>
                            &nbsp;&nbsp;&nbsp;<img src="images/icon-arrow-link.png" alt="icon link" />&nbsp;
                            <a href="${transitionURL}/admin_zettadinfo.html?zettID=${extendedAdObject.objectId}&token=${token}"
                               target="_blank"><spring:message code="showad.link.transition"/></a>
                            <br/><br/>
                                                        <label><spring:message code="show.dumpobject"/></label> <a href="${zservicesurl}xml/getAdObject?objectId=${extendedAdObject.objectId}" target="_blank"><img src="images/xml_icon.png" class="space_top2"/></a>
                        </div>
                        <br class="clear"/>
                    </div>
                    <div class="align_right">
                        <input class="btn_save_sm" type="submit" name="btnsubmit" value=<spring:message code="showad.btn.save"/> />
                    </div>
                    <br class="clear"/>
                </div>
            </fieldset>

               <c:if test="${bookingOrderDetailSize != 0 }">
               <section>

                <div class="topic_line_red"><spring:message code="show.field.orderinformation"/></div>
                <div class="tab_gradient"></div>
                <div class="info_group">
                <table>
                    <thead>
                        <tr>
                            <th  class="col13"><spring:message code="showad.panyment.orderdate"/></th>
                            <th  class="col13"><spring:message code="showad.payment.orderid"/></th>
                            <th  class="col13"><spring:message code="showad.payment.paid"/></th>
                            <th><spring:message code="showad.payment.content"/></th>
                            <th class="col11"><spring:message code="show.payment.paidby"/></th>
                            <th class="col13"><spring:message code="show.payment.discountcode"/></th>
                            <th class="col16">%</th>
                        </tr>
                    </thead>
                    <tbody>

                    <c:forEach var = "bookingOrder" items="${bookingOrderDetail}">
                        <tr>
                            <td >
                                ${bookingOrder.orderDate}
                            </td>
                            <td>
                                  ${bookingOrder.orderId}
                            </td>
                            <td>
                               ${bookingOrder.currencyType}  ${bookingOrder.totalCost}
                            </td>
                            <td>

                             <c:forEach var = "bookingOrderItem"   items="${bookingOrder.bookingOrderItemses}" >
                                ${bookingOrderItem.orderType}   :    ${bookingOrder.currencyType}    ${bookingOrderItem.price} <br/>
                             </c:forEach>
                            </td>
                              <td class="align_center">
                                <c:choose>
                                    <c:when test= '${bookingOrder.paidBy == "kort"}'>
                                       <img src="images/bycredit.gif" alt="Paid by credit card" title="Paid by credit card" />
                                    </c:when>

                                    <c:when test= '${bookingOrder.paidBy == "mobile"}'>
                                       <img src ="images/bymobile.gif" alt="Paid by mobile" title="Paid by mobile" />
                                    </c:when>

                                    <c:otherwise>
                                            ${bookingOrder.paidBy}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                  ${bookingOrder.generatedCode}
                            </td>
                            <td>
                                 ${bookingOrder.percent}
                            </td>
                        </tr>
                      </c:forEach>
                        </tbody>
                    </table>
                </div>
             </section>
            </c:if>

            <!-- Printad section -->

                     <c:if test="${mapsize !=0 }">
                            <fieldset>
                                <legend><spring:message code = "show.printad.listofprintad"/></legend>
                                <div class="tab_gradient"></div>
                                <div class="info_group">
                                <c:choose>
                                    <c:when test="${fn:length(printadobjectmap) < 7  }">
                                         <c:forEach items="${printadobjectmap}" var="map">
                                               <div style="width: 295px; float: left; margin-left: 30px;">
                                                    <img src="images/icon-arrow-link.png" alt="icon link" />&#160;  <a href="searchad.html?printAdId=${map.key}"> ID ${map.key} <spring:message code = "show.printad.from"/> ${map.value} </a>
                                               </div>
                                        </c:forEach>
                                    </c:when>


                                    <c:otherwise>
                                    <div id="first-group" style="float: left;">
                                         <c:forEach items="${printadobjectmap}" var="map"  varStatus="counter">
                                            <c:choose>
                                                <c:when test="${counter.count < 7}">
                                                    <div class="list_3col">
                                                     <!--  <div class="col_left">    -->
                                                        <img src="images/icon-arrow-link.png" alt="icon link" />&#160;  <a href="searchad.html?printAdId=${map.key}"> ID ${map.key} from ${map.value} </a>
                                                    </div>
                                                 </c:when>
                                             </c:choose>
                                         </c:forEach>
                                    </div>
                                        <!-- hide content  -->
                                        <div id="showhide-printad" style="display:none;float:left;">
                                              <c:forEach items="${printadobjectmap}" var="map" varStatus="counter" >
                                                <c:choose>
                                                    <c:when test="${counter.count >= 7}">
                                                  <div class="list_3col">
                                                     <img src="images/icon-arrow-link.png" alt="icon link" />&#160;  <a href="searchad.html?printAdId=${map.key}"> ID ${map.key} from ${map.value} </a>
                                                  </div>
                                                    </c:when>
                                                </c:choose>
                                              </c:forEach>
                                        </div>
                                        <!-- end hide content -->
                                        <br class="clear"/><br/>
                                        <a id="change-text" onclick="displayListPrintad();" class="align_right">More [+]</a>

                                    </c:otherwise>
                                </c:choose>

                            </fieldset>
                     </c:if>
            <fieldset>
                <legend><spring:message code="showad.contact.info"/></legend>
                <c:if test="${fn:length(extendedAdObject.contacts)>0}">
                    <c:forEach var="i" begin="0" end="${fn:length(extendedAdObject.contacts)-1}">
                        <div class="tab_gradient"></div>
                        <div class="info_group">
                             <div class="col_left">
                                <label><spring:message code="showad.field.name"/></label>
                                    <form:hidden path="contacts[${i}].contactId"/>
                                    <form:input path="contacts[${i}].name"/>
                                <br/><br/>
                                <label><spring:message code="showad.field.tlf"/></label>
                                    <form:hidden path="contacts[${i}].attributesRaw[2].attributeId"/>
                                    <form:hidden path="contacts[${i}].attributesRaw[2].typeValue"/>
                                    <form:input path="contacts[${i}].attributesRaw[2].value"/>
                             </div>
                             <div class="col_center">
                                 <label><spring:message code="showad.field.title"/></label>
                                    <form:hidden path="contacts[${i}].attributesRaw[0].attributeId"/>
                                    <form:hidden path="contacts[${i}].attributesRaw[0].typeValue"/>
                                    <form:input path="contacts[${i}].attributesRaw[0].value"/><br/><br/>
                                 <label><spring:message code="showad.field.mobile"/></label>
                                     <form:hidden path="contacts[${i}].attributesRaw[1].attributeId"/>
                                     <form:hidden path="contacts[${i}].attributesRaw[1].typeValue"/>
                                     <form:input path="contacts[${i}].attributesRaw[1].value"/>
                             </div>
                             <div class="col_right">
                                 <label><spring:message code="showad.field.email"/></label>
                                     <form:input path="contacts[${i}].email"/>
                             </div>

                             <br class="clear"/>

                             <div class="align_right">
                                 <input class="btn_save_sm" type="submit" name="btnsubmit" value=<spring:message code="showad.btn.save"/>
                             </div>
                        </div>
                    </c:forEach>
                </c:if>
            </fieldset>

            <fieldset>
                <legend><spring:message code="showad.category"/></legend>
                <div class="tab_gradient"></div>
                    <div class="info_group">
                        <div class="col_2col align_left">
                            <label><spring:message code="showad.field.category"/></label><br/>
                                <form:hidden id="hdcategoy" path="adObjectCategory.categoryId"/>
                                <input type="text" id="txtcategory" class="forcol2"
                                       value="${extendedAdObject.adObjectCategory.fullname}" readonly="true">
                        </div>
                        <div class="col_right">
                            <br/>
                            <input class="btn_gray align_right2" type="button" value="Endring" onclick="displayAdCategory()" />
                        </div>
                        <br class="clear" /><br/>

                        <div id="ad_category" class="nonedisplay">
                        <div id="ad_category_list" class="col_left">
                            <label>Velg kategori</label><br/>
                            <select onChange="getChildElementCategory(this.value);">
                                <option value="0"> --- Please Select --- </option>
                                <option value="13000">Båt</option>
                                <option value="16000">Eiendom</option>
                                <option value="10000">Motor</option>
                                <option value="4000">Småttogstort</option>
                                <option value="10">Stilling</option>
                            </select>
                        </div>
                        <br class="clear" />

                         <div class="align_right">
                            <input class="btn_save_sm" type="submit" value="Lagre" />
                         </div>
                    </div>

                </div>
            </fieldset>

            <fieldset>
                <legend><spring:message code="showad.text.content"/></legend>
                <div class="tab_gradient"></div>
                <div class="info_group">
                    <div class="info_padding">
                        ${zettAdObject.attributes[29].value}
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <legend><spring:message code="showad.thumbnail.image"/></legend>
                <div class="tab_gradient"></div>
                <div class="info_group">
                    <div class="img_gallery" id="adimagelist">
                        <c:forEach items="${extendedAdObject.objectMediaList}" var="images">
                            <obscura:createImageUrl backend="zett" version="${imageVersion}"
                                revision="${extendedAdObject.modifiedTime.time}" uid="${images.reference}" var="imgUrl"/>
                            <div id="${images.reference}">
                                <c:choose>
                                    <c:when test="${images.order == 1}">
                                        <div class="main_img" onclick="show_tinybox('${imgUrl}');" >
                                        </div>
                                          <img id="${images.reference}" src="${imgUrl}" onclick="show_tinybox('${imgUrl}');"/>
                                    </c:when>

                                    <c:otherwise>
                                        <img id="${images.reference}"
                                             src="${imgUrl}"
                                             onclick="show_tinybox('${imgUrl}');"/>
                                        <p>
                                              <input class="btn_red align_right2" type="button"
                                              value='<spring:message code="showad.btn.delete"/>'
                                              onclick="deleteMedia(${images.mediaId});"/>
                                              <input class="btn_gray align_right2" type="button"
                                              value='<spring:message code="showad.btn.set.main.media"/>'
                                              onclick="setNewMainMedia(${extendedAdObject.objectId},${images.mediaId})"/>

                                        </p>
                                    </c:otherwise>

                                </c:choose>
                            </div>
                        </c:forEach>
                    </div>
                    <br class="clear" /><br/>
                    <div>
                        <div class="box_gray">
                            <a name="addFile"></a>

                            <div id="fileTab">
                                <div><label><spring:message code="showad.field.add.photo"/></label> &nbsp;&nbsp;<input type="file" name="adImgs" onchange="showNewImg(this);" accept="image/*" /></div>
                            </div>
                            <br class="clear" />
                            <a href="#addFile"  class="btn_gray" onclick="addNewRowToTable();"><spring:message code="showad.text.add.image"/> </a>
                        </div>
                    </div>
                     <div class="align_right">
                         <input class="btn_save_sm" type="submit" name="btnsubmit" value=<spring:message code="showad.btn.save"/>
                     </div>
                </div>
            </fieldset>
            <input type="hidden" id="imageOrder" name="imageOrder"/>


        <%--<div align="center" style="line-height: 1.9em;">--%>
        <%--<input align="center" id="newFile" type='file' onchange="showNewImg(this);"/>--%>
        <%--<img align="center" id="blah" src="#" alt="your image" style="width: 80px;height: 80px"/>--%>
        <%--</div>--%>

        <fieldset>
            <legend><spring:message code="showad.set.adowner.headline"/></legend>
            <c:if test="${alertUserMessage!=null}">
                <div class="error">${alertUserMessage}</div>
            </c:if>
            <div class="tab_gradient"></div>
            <div class="info_group">


                <div class="col_left">
                   <label><spring:message code="showad.field.company"/></label><br/>
                   <input type="text" id="txtTemporaryCompanyId" name="txtTemporaryCompanyId"  value=""/>
                </div>
                <div class="col_2col align_left4">
                    <label><spring:message code="company.field.additionalCompany"/></label><br/>
                    <input type="text" id="txtAdditioanalCompanyIdList" name="txtAdditioanalCompanyIdList" class="forcol2" value="${AdditionalCompanyIdList}"/>
                    <span class="txt_red"><spring:message code="showad.text.warning.separator.additionalcompany"/></span>
                </div>

                <br class="clear" /><br/>
                <div class="col_left">
                    <label><spring:message code="show.adowner.headline"/></label><br/>
                    <input type="text" id="txtemailbox" name="txtemailbox" value="" />

                </div>

                <br class="clear" />
                <div class="align_right">
                    <input type="button" value=<spring:message code="showad.btn.set.owner"/> class="btn_save_sm"
                    onClick="createAdOwner()" />
                </div>

                <c:if test="${userView!=null}">
                    ${userView}
                </c:if>
                <a href="showuser.html?email=${emailForserach}&noCheck=true"> ${emailForserach} </a>
            </div>
        </fieldset>



        <fieldset>
            <legend><spring:message code="showad.ads.attributes"/></legend>
            <div class="tab_gradient"></div>
            <div class="info_group">
                <div class="align_right">
                   <a class="btn_gray" onclick="displayAttribute()"><img src="images/icon-plus.png" alt="icon plus" /><spring:message code="showad.btn.add.new.attr"/></a>
                </div>
                <div class="popup_form" id="add_attribute"<c:if test="${extendedAdObject.extendedAttribute.name==null}">class="nonedisplay"</c:if>>
                    <div class="col_1">
                        <label><spring:message code="showad.select.attribute"/></label>
                        <c:choose>
                            <c:when test="${attributeList == null}">
                                <form:input path="extendedAttribute.name" class="forcol4" />
                            </c:when>
                            <c:otherwise>

                                <form:select path="extendedAttribute.name" id="slcattribute" onchange="generateAttribute()" class="forcol4">
                                    <form:option value="" label="velg attributt"/>
                                    <form:options items="${attributeList}" />
                                </form:select>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col_2">
                        <label><spring:message code="showad.select.type"/></label>
                        <form:select path="extendedAttribute.typeValue" id="slcattributetype" class="forcol4">
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
                        <form:input path="extendedAttribute.label" id="txtattributelabel" class="forcol4"/>
                    </div>
                    <div class="col_2">
                        <label><spring:message code="showad.enter.value"/></label>
                        <form:input path="extendedAttribute.value" class="forcol4"/>
                    </div>
                    <br class="clear" />
                    <br/>
                        <a href='<c:url value="/showzettcommon.html"/>' id="zettCommon_link" target="_blank">
                            <spring:message code="showad.link.attribute.list"/>
                        </a>
                        <input class="btn_gray align_right2" type="submit" name="btnsubmit" onclick="displayAttribute()" value="Legg Til" />
                </div>
                <br class="clear" /><br/>
                <div class="equal">
                    <div class="head_row">
                        <div class="att_col1"><spring:message code="showad.field.label"/></div>
                        <div class="att_col2"><spring:message code="showad.field.value"/></div>
                        <div class="att_col3"><spring:message code="showad.field.delete"/></div>
                    </div>
                    <c:if test="${fn:length(extendedAdObject.attributes)>0}">
                        <c:forEach var="i" begin="0" end="${fn:length(extendedAdObject.attributes)-1}">
                            <div class="body_row">
                                <div class="att_col1">
                                    ${extendedAdObject.attributes[i].label}
                                    <form:hidden path="attributes[${i}].label"/>
                                    <form:hidden path="attributes[${i}].attributeId"/>
                                </div>
                                <c:if test="${extendedAdObject.attributes[i].name != 'text'}">
                                <div class="att_col2">
                                    <form:input path="attributes[${i}].value" size="100%"/>
                                </div>
                                </c:if>
                                <c:if test="${extendedAdObject.attributes[i].name == 'text'}">
                                <div class="att_col2">
                                    <form:textarea path="attributes[${i}].value" cols="100" rows="5"/>
                                </div>
                                </c:if>
                                <div class="att_col3 align_center">
                                    <a href="#" onclick="deleteAttribute('${extendedAdObject.attributes[i].attributeId}')" title="Slett attributt"><img src="images/icon-del.png" alt="Slett attributt" title="Slett attributt"/></a>
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

        <form id="assosicateUserForm" action="createaddowner.html" method="post">
            <input type="hidden" id="hdadId" name="hdadId" value="${extendedAdObject.objectId}"/><br/>
            <input type="hidden" id="hdtitle" name="hdtitle" value="${extendedAdObject.title}"/><br/>
            <input type="hidden" id="hduserEmail" name="hduserEmail" value=""/><br/>
        </form>

        <form id="assosicateCompanyForm" action="createcompanyowner.html" method="post">
                    <input type="hidden" id="currentAdId" name="currentAdId" value="${extendedAdObject.objectId}"/><br/>
                    <input type="hidden" id="companyOwnerId" name="companyOwnerId" value=""/><br/>
                    <input type="hidden" id="additionalcompanyId" name="additionalcompanyId" value=""/><br/>

        </form>

        <form id="setMainMedia" action="setmainmediaad.html" method="post">
            <input type="hidden" id="hdMediaId" name="mediaId" value="${extendedAdObject.objectId}"/><br/>
            <input type="hidden" id="hdAdId" name="adId" value=""/><br/>
        </form>

        <form id="deleteMediaAdForm" action="deleteMediaAd.html" method="post">
            <input type="hidden" id="adIdForDeleteImage" name="adIdForDeleteImage" value="${extendedAdObject.objectId}"/><br/>
            <input type="hidden" id="mediaIdForDelete" name="mediaIdForDelete" value=""/><br/>
        </form>



        <form id="retrieveAdMapForm" action="showAdMap.html" method="post">
            <input type="hidden" id="mapadid" name="mapadid" value=""/></br>
        </form>

        <form id="retrieveDumpObjectForm" action="retrieveDumpObject.html" method="post">
             <input type="hidden" id="dumpAdObjId" name="dumpAdObjId" value="${extendedAdObject.objectId}"/><br/>
        </form>

        <form id="deleteAttributeForm" action="deleteadattribute.html" method="post">
            <input type="hidden" id="deleteObjectId" name="adid" value="${extendedAdObject.objectId}" />
            <input type="hidden" id="deleteAttributeId" name="attributeid" value="" />
        </form>

<%@ include file="template/footer.jsp" %>
