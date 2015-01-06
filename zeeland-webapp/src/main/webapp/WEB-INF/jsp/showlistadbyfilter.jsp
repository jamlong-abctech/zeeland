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

                <th class="col15">
                    <spring:message code="ad.field.extRef" />
                </th>
                <th class="col13">
                    <spring:message code="showad.field.ad.status" />
                </th>
                <th class="col9">
                    <spring:message code="ad.field.linkZettSite" />
                </th>
                <th class="col9">
                    <spring:message code="ad.field.linkZeelandSite" />
                </th>
            </tr>
        </thead>
        <c:forEach var="searchItem" items="${adSearchItems}">
                        <tr>
                            <td>
                                  ${searchItem.values[0]}
                            </td>
                            <td>
                                  ${searchItem.values[1]}
                            </td>
                            <td>
                                 ${searchItem.values[2]}
                            </td>
                            <td>
                                  ${searchItem.values[3]}
                            </td>

                             <td>
                                 ${searchItem.values[4]}
                             </td>
                             <!--
                              <td>
                               ${searchItem.values[5]}
                              </td>     -->

                              <td>
                                <c:if test= "${searchItem.values[5]== 0}">
                                        Inaktiv
                                </c:if>

                                <c:if test= "${searchItem.values[5]== 1}">
                                       Aktiv
                                </c:if>

                                <c:if test= "${searchItem.values[5]== 2}">
                                     Slettet
                                </c:if>

                                <c:if test= "${searchItem.values[5]== 3}">
                                    Blokkert
                                </c:if>

                                <c:if test= "${searchItem.values[5]== 4}">
                                    Solgt
                                </c:if>

                                <c:if test= "${searchItem.values[5]== 5}">
                                    Påbegynt
                                </c:if>

                                <c:if test= "${searchItem.values[5]== 6}">
                                   Påvent
                                </c:if>

                                <c:if test="${searchItem.values[5]== 7}">
                                    Utgått
                                </c:if>

                                <c:if test="${searchItem.values[5]== 8}">
                                    Ikke autorisert
                                </c:if>

                              </td>



                            <td>


                             <c:set var="category" value="${searchItem.values[6]}"/>
                              <c:choose>
                                  <c:when test="${fn:startsWith(category, 'Motor')==true}">
                                           <a href="${zettAdUrl}/vis/rubrikk/motorprospekt/${searchItem.values[0]}.html" +  target="_blank">

                                                                       View
                                           </a>
                                  </c:when>

                                  <c:when test="${fn:startsWith(category, 'Båt')==true}">
                                            <a href="${zettAdUrl}/vis/rubrikk/baatprospekt/${searchItem.values[0]}.html" +  target="_blank">

                                                    View
                                            </a>
                                  </c:when>

                                   <c:when test="${fn:startsWith(category, 'Småttogstort')==true}">
                                             <a href="${zettAdUrl}/vis/rubrikk/baatprospekt/${searchItem.values[0]}.html" +  target="_blank">

                                                                              View
                                             </a>
                                   </c:when>

                                   <c:when test="${fn:startsWith(category, 'Eiendom')==true}">
                                              <a href="${zettAdUrl}/vis/rubrikk/eiendomsprospekt/${searchItem.values[0]}.html" +  target="_blank">

                                                                              View
                                              </a>
                                   </c:when>

                                     <c:when test="${fn:startsWith(category, 'Stilling')==true}">
                                              <a href="${zettAdUrl}/vis/rubrikk/jobbprospekt/${searchItem.values[0]}.html" +  target="_blank">

                                                                              View
                                              </a>
                                     </c:when>

                                      <c:when test="${fn:startsWith(category, 'Frivillig')==true}">
                                               <a href="${zettAdUrl}/vis/rubrikk/frivilligprospekt/${searchItem.values[0]}.html" +  target="_blank">

                                                                              View
                                               </a>
                                      </c:when>

                                  <c:otherwise>
                                  </c:otherwise>
                              </c:choose>

                            </td>
                            <td>
                                <a href="${zeelandUrl}${searchItem.values[0]}">
                                    View
                                </a>
                            </td>
                        </tr>
                        <c:set var="counter" value="${counter +1}" />
                    </c:forEach>
                </tbody>
            </table>



            <br class="clear" />
                <p class="align_left">Totalt <c:out value="${totalResult}"/> resultater</p>
                <br/><br/>

                <c:if test="${type == 'search'}">
                    <div class="align_center">

                        <c:if test="${currentPage != 1}">
                            <a href="searchAdByPageByFilter.html?keyword=${keyword}&status=${status}&page=${currentPage - 1}" class="btn_gray">&#171; <spring:message code="page.show.page.previous"/></a>
                        </c:if>

                            &#160;&#160;<spring:message code="page.show.page"/>&#160;&#160;:&#160;&#160;
                            <input type="text" id="searchpage" class="paging" value="${currentPage}" />&#160;&#160;/&#160;&#160; ${totalPage} &#160;&#160;
                            <a class="btn_gray" onclick="goToAdSearchResultByFilter('${keyword}','${status}')"><spring:message code="page.show.page.go"/></a>&#160;&#160;

                        <c:if test="${currentPage != totalPage}">
                            <a href="searchAdByPageByFilter.html?keyword=${keyword}&status=${status}&page=${currentPage + 1}" class="btn_gray"><spring:message code="page.show.page.next"/> &#187;</a>
                        </c:if>
                        <br class="clear" />
                    </div>
                </c:if>

                </div>
                </section>

            <%@ include file="template/footer.jsp" %>








