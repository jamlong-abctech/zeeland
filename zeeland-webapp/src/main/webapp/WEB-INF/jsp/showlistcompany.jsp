<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ include file="template/header.jsp" %>
    <div class="info">
        <div id="title">
            <h2><spring:message code="page.title.searchCompany" /></h2>
        </div>
        <fieldset>
            <legend><spring:message code="page.title.searchCompany" /></legend>

        <%@ include file="template/form_search_company.jsp" %>
        </fieldset>
        <section>
            <div class="topic_line_gray">Resultater</div>
            <div class="tab_gradient"></div>
            <div class="info_group">
                <table>
                    <thead>
                        <tr>
                            <th class="col16"><spring:message code="company.field.company.id"/></th>
                            <th ><spring:message code="company.field.name"/></th>
                            <th class="col13"><spring:message code="company.field.status"/></th>
                            <th class="col10"><spring:message code="company.field.category"/></th>
                            <th class="col13"><spring:message code="company.field.linkZeelandSite"/></th>
                            <th class="col13"><spring:message code="showcompany.view.ads"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="searchItem" items="${zettSearchItems}">

                            <td>
                                ${searchItem.values[0]}
                            </td>
                            <td>
                                ${searchItem.values[1]}
                            </td>
                             <td>
                               <!-- ${searchItem.values[2]}  -->
                               <c:if test="${searchItem.values[2] != null}">

                                <c:if test="${searchItem.values[2]=='0'}">

                                    <spring:message code= "showcompany.status.inactive"/>
                                </c:if>

                                <c:if test="${searchItem.values[2]=='1'}">
                                   <spring:message code= "showcompany.status.active"/>
                                </c:if>

                                 <c:if test="${searchItem.values[2]=='2'}">
                                    <spring:message code= "showcomapny.status.deleted"/>
                                 </c:if>

                                 <c:if test="${searchItem.values[2]=='3'}">
                                   <spring:message code= "showcomapny.status.blocked"/>
                                 </c:if>

                                  <c:if test="${searchItem.values[2]=='4'}">
                                   <spring:message code= "showcomapny.status.sold"/>
                                 </c:if>

                                  <c:if test="${searchItem.values[2]=='5'}">
                                   <spring:message code= "showcomapny.status.pending"/>
                                 </c:if>

                                 <c:if test="${searchItem.values[2]=='5'}">
                                   <spring:message code= "showcomapny.status.tobepublished"/>
                                 </c:if>

                                 <c:if test="${searchItem.values[2]=='6'}">
                                   <spring:message code= "showcomapny.status.expired"/>
                                 </c:if>

                                 <c:if test="${searchItem.values[2]=='7'}">
                                   <spring:message code= "showcomapny.status.unauthorized"/>
                                 </c:if>
                           </c:if>
                            </td>

                             <td>
                             <!-- ${searchItem.values[3]}  -->
                               <c:set var = "category" value = "${searchItem.values[3]}" />
                                <c:choose>
                                <c:when test = "${fn:contains(category,'Firmaer')}" >


                                 <c:set var="replacectegory" value="${fn:replace(category,
                                                                'Firmaer/', '')}" />

                                     ${replacectegory}
                               </c:when>

                                <c:otherwise>
                                        ${searchItem.values[3]}
                                 </c:otherwise>

                                </c:choose>
                            </td>

                            <td>
                                <a href="getcompany.html?companyId=${searchItem.values[0]}" target="_blank">View</a>
                            </td>

                             <td>

                              <c:set var = "category" value="${searchItem.values[3]}"/>
                               <c:choose>
                                <c:when test = "${fn:contains(category,'Eiendomsmeglere')}" >
                               <a href="http://www.eiendomsnett.no/vis/rubrikk/eiendom.html?compId=${searchItem.values[0]}" target="_blank">View</a>

                              </c:when>
                                <c:otherwise>
                                <a href="http://www.zett.no/vis/rubrikk/sok.html?localGeo=false&compId=${searchItem.values[0]}" target="_blank">View</a>
                                </c:otherwise>
                              </c:choose>
                            </td>
                          </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <br class="clear" />
                <p class="align_left">Totalt ${totalResult} resultater</p>
                <br/>
                <br/>
                <div class="align_center">
                     <c:if test="${currentPage != 1}">
                                    <a class="btn_gray" onclick="showDataPrePage()">&#171; <spring:message code="page.show.page.previous"/></a>
                     </c:if>
                        &#160;&#160;<spring:message code="page.show.page"/>&#160;&#160;:&#160;&#160;
                         <input type="text" id="searchCompanypage" class="paging" value="${currentPage}" />&#160;&#160;/&#160;&#160; ${totalPage} &#160;&#160;
                         <a class="btn_gray" onclick="showDataGoToPage()"><spring:message code="page.show.page.go"/></a>&#160;&#160;

                      <c:if test="${currentPage != totalPage}">
                                     <a class="btn_gray" onclick="showDataNextPage()"><spring:message code="page.show.page.next"/> &#187;</a>
                      </c:if>

             </div>
         </section>
         <br class="clear" />
    </div>

       <form id="searchresultbypage" action="searchresultbypage.html" method="post">
            <input type="hidden" id="totalPage" name= "totalPage" value= "${totalPage}"/>
            <input type="hidden" id="companyTitleSearch" name="companyTitleSearch" value=""/>
            <input type="hidden" id="pageSearch" name="pageSearch" value=""/>
            <input type="hidden" id="status" name="status" value=""/>
            <input type="hidden" id="totalResult" name="totalResult" value="${totalResult}"/>
       </form>

<%@ include file="template/footer.jsp" %>


