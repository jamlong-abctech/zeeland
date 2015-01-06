<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="template/header.jsp" %>
    <div id="title">
        <h2><spring:message code="page.title.searchAd" /></h2>
    </div>
    <fieldset>
        <legend><spring:message code="page.title.searchAd" /></legend>
        <%@ include file="template/form_search_ad.jsp" %>
    </fieldset>
    <section>
    <div class="topic_line_gray">Resultater</div>
    <div class="tab_gradient"></div>
    <div class="info_group">
    <c:set var="counter" value="0" />
    <table>
        <thead>
            <tr>
                <th class="col16">
                    <spring:message code="ad.field.adId" />
                </th>
                <th>
                    <spring:message code="ad.field.adTitle" />
                </th>
                <th class="col16">
                    <spring:message code="ad.field.companyId" />
                </th>
                <th class="col15">
                    <spring:message code="ad.field.companyName" />
                </th>
                <th class="col9">
                    <spring:message code="ad.field.importFormat" />
                </th>
                <th class="col9">
                    <spring:message code="ad.field.extRef" />
                </th>
                <th class="col9">
                    <spring:message code="ad.field.pubStatus" />
                </th>
                <th class="col9">
                    <spring:message code="ad.field.linkZettSite" />
                </th>
                <th class="col9">
                    <spring:message code="ad.field.linkZeelandSite" />
                </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="zettAdObject" items="${zettAdObjectList}">
                <tr>
                    <td>
                        ${zettAdObject.objectId}
                    </td>
                    <td>
                        ${zettAdObject.title}
                    </td>
                    <td>
                        ${zettAdObject.company.objectId}
                    </td>
                    <td>
                        ${zettAdObject.company.title}
                    </td>
                    <td>
                        ${zettAdObject.createdBy}
                        <!--
                        <c:forEach var="zettObjectAttribute" items="${zettAdObject.company.attributes}">
                            <c:if test="${zettObjectAttribute.label=='ImportFormat'}">
                                ${zettObjectAttribute.value}
                            </c:if>
                        </c:forEach>
                        -->
                    </td>
                    <td>
                        ${zettAdObject.externalReferences[0].reference}
                    </td>
                    <td>
                        <c:forEach var="publishingStatus" items="${adObjectPublishingStatusValues}" varStatus="status">
                            <c:if test="${zettAdObject.publishingStatus==(status.index)}">
                                ${publishingStatus}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                         <!-- <a href="${zettUrl}${zettAdObject.objectId}&noCheck=true" target="_blank">
                            View

                            if (category.startsWith("Motor")) {
                                          category = "motorprospekt";
                                      } else if (category.startsWith("Båt")) {
                                          category = "baatprospekt";
                                      } else if (category.startsWith("Småttogstort")) {
                                          category = "torgprospekt";
                                      } else if (category.startsWith("Eiendom")) {
                                          category = "eiendomsprospekt";
                                      } else if (category.startsWith("Stilling")) {
                                          category = "jobbprospekt";
                                      } else if (category.startsWith("Frivillig")) {
                                          category = "frivilligprospekt";

                                      }

                                  }
                                  log.debug("This ad does not have category");


                                  String zettUrl = zeelandProperties.getZettUrl() + "/vis/rubrikk/" + category + "/" + objectId + ".html";
                        </a>  -->


                     <c:set var="category" value="${zettAdObject.category}"/>
                      <c:choose>
                          <c:when test="${fn:startsWith(category, 'Motor')==true}">
                                   <a href="${zettAdUrl}/vis/rubrikk/motorprospekt/${zettAdObject.objectId}.html" +  target="_blank">

                                                               View
                                   </a>
                          </c:when>

                          <c:when test="${fn:startsWith(category, 'Båt')==true}">
                                    <a href="${zettAdUrl}/vis/rubrikk/baatprospekt/${zettAdObject.objectId}.html" +  target="_blank">

                                            View
                                    </a>
                          </c:when>

                           <c:when test="${fn:startsWith(category, 'Småttogstort')==true}">
                                     <a href="${zettAdUrl}/vis/rubrikk/baatprospekt/${zettAdObject.objectId}.html" +  target="_blank">

                                                                      View
                                     </a>
                           </c:when>

                           <c:when test="${fn:startsWith(category, 'Eiendom')==true}">
                                      <a href="${zettAdUrl}/vis/rubrikk/eiendomsprospekt/${zettAdObject.objectId}.html" +  target="_blank">

                                                                      View
                                      </a>
                           </c:when>

                             <c:when test="${fn:startsWith(category, 'Stilling')==true}">
                                      <a href="${zettAdUrl}/vis/rubrikk/jobbprospekt/${zettAdObject.objectId}.html" +  target="_blank">

                                                                      View
                                      </a>
                             </c:when>

                              <c:when test="${fn:startsWith(category, 'Frivillig')==true}">
                                       <a href="${zettAdUrl}/vis/rubrikk/frivilligprospekt/${zettAdObject.objectId}.html" +  target="_blank">

                                                                      View
                                       </a>
                              </c:when>

                          <c:otherwise>
                          </c:otherwise>
                      </c:choose>

                    </td>
                    <td>
                        <a href="${zeelandUrl}${zettAdObject.objectId}">
                            View
                        </a>
                    </td>
                </tr>
                <c:set var="counter" value="${counter +1}" />
            </c:forEach>
        </tbody>
    </table>

    <br class="clear" />
    <p class="align_left">Totalt <c:out value="${total}"/> resultater</p>
    <br/><br/>

    <c:if test="${type == 'search'}">
        <div class="align_center">

            <c:if test="${currentPage != 1}">
                <a href="searchadresultbypage.html?keyword=${keyword}&page=${currentPage - 1}" class="btn_gray">&#171; <spring:message code="page.show.page.previous"/></a>
            </c:if>

                &#160;&#160;<spring:message code="page.show.page"/>&#160;&#160;:&#160;&#160;
                <input type="text" id="txtpage" class="paging" value="${currentPage}" />&#160;&#160;/&#160;&#160; ${totalPage} &#160;&#160;
                <a class="btn_gray" onclick="jumpToAdSearchResultPage('${keyword}')"><spring:message code="page.show.page.go"/></a>&#160;&#160;

            <c:if test="${currentPage != totalPage}">
                <a href="searchadresultbypage.html?keyword=${keyword}&page=${currentPage + 1}" class="btn_gray"><spring:message code="page.show.page.next"/> &#187;</a>
            </c:if>
            <br class="clear" />
        </div>
    </c:if>

    </div>
    </section>

<%@ include file="template/footer.jsp" %>