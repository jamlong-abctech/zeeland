<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ include file="template/header.jsp" %>


<div class="info">

    <div id="title">
        <h2>Vis svindel kategori</h2>
    </div>

    <c:if test="${!empty errorMsg}">
        <div class="show_validity none_display" element="p">${errorMsg}</div>
    </c:if>

    <div class="box_gray2">
        <a class="btn_gray" onmouseover="clearPopUp();" onclick="clearPopUp() ;centerPopup(); loadPopup('new', '','','','','');">
            <img src="images/icon-plus.png" alt="icon plus"/><spring:message code="categoryedit.add.category"/></a>
            <spring:message code="categoryedit.description"/>
    </div>

    <br class="clear"/>

    <!-- Waiting list -->
    <c:if test="${!empty zettFraudBadCategoryTransactionsList}">
    <form>
        <fieldset>
            <legend>Liste over svindel kategori</legend>
            <div class="tab_gradient"></div>
            <div class="info_group">

                <!-- show table -->
                <table class="fraudedit">
                    <thead>
                    <tr>
                        <th><spring:message code="categoryedit.category"/></th>
                        <th class="col14"><spring:message code="categoryedit.transaction"/></th>
                        <th class="col10"><spring:message code="fraudedit.edit"/></th>
                        <th class="col10"><spring:message code="fraudedit.delete"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% int i = 0; %>
                    <c:forEach var="fraudCategory" items="${zettFraudBadCategoryTransactionsList}" begin="0" end="19"
                               step="1">

                        <tr>
                            <td>
                                <span>${fraudCategory.categoryName}</span>
                            </td>
                            <td>
                                <span>${fraudCategory.adObjectTransactionName}</span>
                            </td>

                            <td>
                                <input class="btn_gray" type="button"
                                       value='<spring:message code="fraudedit.edit"/>'
                                       onmouseover="clearPopUp();"
                                       onclick="clearPopUp();centerPopup(); loadPopup('edit',<%=i%>, '${fraudCategory.categoryName}', '${fraudCategory.adObjectTransactionName}','${fraudCategory.categoryId}','${fraudCategory.adObjectTransactionType}');"/>
                            </td>
                            <td>
                                <input class="btn_red" type="button" onclick="deleteCategory(<%=i%>)"
                                       value='<spring:message code="fraudedit.delete"/>'/>
                            </td>
                        </tr>
                        <% i++; %>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- summary -->
            <br class="clear"/>

            <p>Totalt ${totalFraudList} resultater</p>
            <br/><br/>

                <%--<div class="align_center">--%>

                <%--<c:if test="${currentPage != 1}">--%>
                <%--<a href="fraudedit.html?page=${currentPage - 1}" class="btn_gray">&#171; <spring:message--%>
                <%--code="page.show.page.previous"/></a>--%>
                <%--</c:if>--%>

                <%--&#160;&#160;<spring:message code="page.show.page"/>&#160;&#160;:&#160;&#160;--%>
                <%--<input type="text" id="txtpage" class="paging"--%>
                <%--value="${currentPage}"/>&#160;&#160;/&#160;&#160; ${totalPage} &#160;&#160;--%>
                <%--<a class="btn_gray" onclick="jumpToFraudEditPage()"><spring:message code="page.show.page.go"/></a>&#160;&#160;--%>

                <%--<c:if test="${currentPage != totalPage}">--%>
                <%--<a href="fraudedit.html?page=${currentPage + 1}" class="btn_gray"> <spring:message--%>
                <%--code="page.show.page.next"/> &#187;</a>--%>
                <%--</c:if>--%>
                <%--</div>--%>

        </fieldset>
        </c:if>
        <c:if test="${empty zettFraudBadCategoryTransactionsList}">
            <div class="col_center">
                Empty result
            </div>
        </c:if>
        <br class="clear"/>


    </form>
</div>
<form action="deletecategorytransaction.html" id="deletecategoryform">
    <input type="hidden" name="rowNumberForDelete" id="rowNumberForDelete">
</form>

<!-- popup -->
<div id="popupContact">
    <a id="popupContactClose" onclick="disablePopup();">Close [x]</a>

    <p id="contactArea">

    <div class="topic_line_red">Rediger dårlig kategori</div>

    <br class="clear"/>

    <div class="none_display" id="editCategory">
        <label class="col_left align_left3">
            <label>gjeldende kategori</label>
        </label>

        <div class="col_center">
        </div>
        <br class="clear"/>
        <br class="clear"/>

        <div class="col_left align_left3" id="oldCategoryName">
        </div>

        <div class="col_center" id="oldTransactionName">
        </div>
    </div>

    <div class="info_group">

        <form action="savecategory.html" name="savecategoryform" id="savecategoryform" method="post">
            <div id="ad_category">
                <div id="ad_category_list" class="col_left align_left3">
                    <input type="hidden" id="hdcategoy" name="hdcategoy"/>
                    <input type="hidden" id="rowNumber" name="rowNumber"/>
                    <label>Velg kategori</label><br/>
                    <select name="category_list" onChange="getChildElementCategory(this.value);">
                        <option value="">-- Please Select Type --</option>
                        <option value="13000" selected>Båt</option>
                        <option value="16000">Eiendom</option>
                        <option value="10000">Motor</option>
                        <option value="4000">Småttogstort</option>
                        <option value="10">Stilling</option>
                    </select>
                </div>
            </div>

            <div class="col_center">
                <label>Type transaksjon</label><br/>
                <select name="transactionId" id="transactionId">
                    <option value="">-- Please Select Type --</option>
                    <c:forEach var="transactionItemPopup" items="${adObjectTransactionTypeItemsList}" begin="0" end="19"
                               step="1">
                        <option value="${transactionItemPopup.value}">${transactionItemPopup.textValue}</option>
                    </c:forEach>
                </select>

                <br/><br/>
                <input class="btn_save_sm align_top align_right3" type="button" value="Lagre"
                       onclick="submitForm();disablePopup();"/>
            </div>
        </form>
    </div>
    </p>
</div>
<div id="backgroundPopup"></div>
<!-- end -->

<%@ include file="template/footer.jsp" %>
