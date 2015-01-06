<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="template/header.jsp" %>

     <div class="info">

            <div id="title">
                <h2>Vis svindel ord</h2>
            </div>

            <div class="box_gray2">
                <a class="btn_gray" onclick="displayAttribute()">
                <img src="images/icon-plus.png" alt="icon plus" />Legg til svindel ord</a>
                <spring:message code="fraudedit.description"/>
                <div class="popup_form align_left2" id="add_attribute">
                    <form:form name="newbadword" commandName="fraudBadWord" action="savebadword.html" enctype="multipart/form-data" method="post">
                        <div class="col_left_inner">
                            <label><spring:message code="fraudedit.word"/></label><br/>
                            <form:input path="newBadWord"/>
                            <form:hidden path="badWord"/>
                        </div>
                        <div class="col_center">
                            <br/><input class="btn_gray" type="submit" onclick="displayAttribute()" value='<spring:message code="showad.btn.save"/>' />
                        </div>
                    </form:form>
                    <br />
                </div>
            </div>

            <br class="clear" />

            <!-- Waiting list -->
            <form>
            <fieldset>
                <legend>Liste over svindel ord</legend>
                <div class="tab_gradient"></div>
                <div class="info_group">

                    <!-- show table -->
                    <table class="fraudedit">
                        <thead>
                            <tr>
                                <th><spring:message code="fraudedit.word"/></th>
                                <th class="col14"><spring:message code="fraudedit.created_date"/></th>
                                <th class="col10"><spring:message code="fraudedit.edit"/></th>
                                <th class="col10"><spring:message code="fraudedit.delete"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="fraudBadWord" items="${fraudBadWordList}" begin="0" end="19" step="1">
                                <c:set var="itemId" value='${fn:replace(fraudBadWord.badWord, " ", "-")}' scope="page" />
                                <tr>
                                    <td>
                                        <span id="span${itemId}">${fraudBadWord.newBadWord}</span>
                                        <input type="hidden" name="hdoldword" id="hd${itemId}"  value="${fraudBadWord.badWord}"/>
                                        <input type="text" class="none_display"  name="txtnewword" id="txt${itemId}"  value="${fraudBadWord.badWord}"/>
                                    </td>
                                    <td >${fraudBadWord.createdTime}</td>
                                    <td >
                                        <input id="btnedit${itemId}" class="btn_gray" type="button" value='<spring:message code="fraudedit.edit"/>' onclick="editbadword('${itemId}')" />
                                        <input id="btnsave${itemId}" class="btn_gray none_display" type="button" value='Save' onclick="saveNewBadWord('${itemId}')" />
                                    </td>
                                    <td >
                                        <input class="btn_red" type="button" value='<spring:message code="fraudedit.delete"/>' onclick="deleteFraudBadWord('${fraudBadWord.badWord}')" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            <fieldset>

            <!-- summary -->
            <br class="clear" />
            <p>Totalt ${totalFraudList} resultater</p>
            <br/><br/>

            <div class="align_center">

                <c:if test="${currentPage != 1}">
                    <a href="fraudedit.html?page=${currentPage - 1}" class="btn_gray">&#171; <spring:message code="page.show.page.previous"/></a>
                </c:if>

                    &#160;&#160;<spring:message code="page.show.page"/>&#160;&#160;:&#160;&#160;
                    <input type="text" id="txtpage" class="paging"  value="${currentPage}"  />&#160;&#160;/&#160;&#160; ${totalPage} &#160;&#160;
                    <a class="btn_gray" onclick="jumpToFraudEditPage()"><spring:message code="page.show.page.go"/></a>&#160;&#160;

                <c:if test="${currentPage != totalPage}">
                    <a href="fraudedit.html?page=${currentPage + 1}" class="btn_gray"> <spring:message code="page.show.page.next"/> &#187;</a>
                </c:if>

                <br class="clear" />
            </div>

            </form>
     </div>


<%@ include file="template/footer.jsp" %>