<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tjenester.api.no/obscura" prefix="obscura" %>

<%@ include file="template/header.jsp" %>

<div class="info">

    <form>
        <div id="title">
            <h2><spring:message code="showad.field.id"/>  ${adId} </h2>
        </div>


        <!-- Print ad section -->


            <fieldset>
                <legend><spring:message code="show.printad.id"/> :  ${zettPrintAdObject.objectId}      <spring:message code = "show.printad.from"/>  ${newapapaerurl}</legend>
                <div class="tab_gradient"></div>
                <div class="info_group">

                    <div class="col_2col align_left">
                        <label><spring:message code="show.printad.headline"/>  :</label><br/><br/>
                        <div class="align_left">
                        <span>${zettPrintAdObject.title}</span>
                        </div>
                        <br class="clear"/><br/>

                        <label><spring:message code="show.printad.adtext"/>  :</label><br/><br/>
                        <div class="align_left">
                        <span>${adtext}</span>
                        </div>
                        <br class="clear"/><br/>

                        <label><spring:message code="show.printad.category"/> :</label><br/><br/>
                        <div class="align_left">
                        <span>${zettPrintAdObject.category}</span>
                        </div>
                        <br class="clear"/><br/>

                        <label><spring:message code="show.printad.transactiontype"/> :</label><br/><br/>
                        <div class="align_left">
                        <span>${zettPrintAdObject.transactionType}</span>
                        </div>
                        <br class="clear"/><br/>

                        <label><spring:message code="show.printad.contact"/> :</label><br/><br/>
                        <div class="align_left">
                         <c:forEach var="contact" items="${zettPrintAdObject.contacts}">
                                <span style="font-style: italic;"><spring:message code="showad.field.name"/> </span>${contact.name}<br/>
                                <span style="font-style: italic;"><spring:message code="showad.field.mobile"/></span>${contactno}<br/>
                                <span style="font-style: italic;"><spring:message code="showad.field.email"/></span>${contact.email}
                         </c:forEach>
                         </div>
                         <br class="clear"/><br/>

                        <label><spring:message code="show.printad.publisingdate"/> :</label><br/><br/>
                        <div class="align_left">
                        <span>${activedates}</span>
                        </div>
                        <br class="clear"/><br/>


                            <c:if test="${bookingOrderDetailSize != 0 }">
                                <label><spring:message code="show.printad.paymentinformation"/> :</label><br/><br/>


                                    <table class="table_trans">
                                    <thead>
                                      <tr>
                                          <th  class="col13">Dato</th>
                                          <th  class="col13">OrdreID</th>
                                          <th>Betalt</th>
                                          <th  class="col14">Innhold</th>

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
                                      </tr>
                                    </c:forEach>
                                      </tbody>
                                    </table>

                             </c:if>
                     </div>


                        <div class="col_right">
                          <label><spring:message code="show.printad.papirannonse"/>  :</label><br/><br/>
                          <c:choose>
                                <c:when test="${fn:length(zettPrintAdObject.media) == 0}">
                                       <img src="images/no-ad.gif" class="border_gray" alt="No Print Ad"  />
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${zettPrintAdObject.media}" var="media">
                                        <obscura:createImageUrl backend="zett" version="${imageVersion}"
                                            revision="${zettPrintAdObjectModifiedTime}" uid="${media.reference}" var="imageUrl" />
                                          <img src="${imageUrl}" alt="Print Ad" />
                                    </c:forEach>
                                </c:otherwise>

                          </c:choose>
                        </div>

                </div>
            </fieldset>

             <a href="showad.html?adId=${adId}">&laquo;<spring:message code="show.printad.message.backtonetad"/>  </a>
        <br class="clear" />
    </form>
</div>
<%@ include file="template/footer.jsp" %>
