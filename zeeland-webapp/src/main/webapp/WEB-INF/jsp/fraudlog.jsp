<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="template/header.jsp" %>

    <div class="info">
        <form>
            <div id="title">
                <h2>Svindeladministrator</h2>
            </div>

            <div class="box_gray2">
                <a class="btn_gray" onclick="goToFraudEdit()" /><spring:message code="fraudlog.add.badword.label"/></a>
                &nbsp;<a class="btn_gray" onclick="goToCategoryEdit()" /><spring:message code="fraudlog.add.category.label"/></a>
                <spring:message code="fraudlog.description"/>
            </div>

            <!-- Waiting list -->
            <fieldset>
                <legend>Venter</legend>
                    <div class="tab_gradient"></div>
                    <div class="info_group">

                    <!-- show table -->
                        <table>
                            <thead>
                                <tr>
                                    <th><spring:message code="fraudlog.title"/></th>
                                    <th class="col11"><spring:message code="fraudlog.adId"/></th>
                                    <th class="col17"><spring:message code="fraudlog.email"/></th>
                                    <th class="col11"><spring:message code="fraudlog.createDate"/></th>
                                    <th class="col13"><spring:message code="fraudlog.message"/></th>
                                    <th class="col18"><spring:message code="fraudlog.block.user"/></th>
                                    <th class="col16"><spring:message code="fraudlog.approve"/></th>
                                    <th class="col12"><spring:message code="fraudlog.deny"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="fraudLogObject" items="${fraudLogObjectList}" begin="0" end="19" step="1">
                                    <tr>
                                        <td>
                                            ${fraudLogObject.title}
                                        </td>
                                        <td>
                                            <a class="mouse-pointer" onclick="openerRedirect('showad.html?adId=${fraudLogObject.adObjectId}')">${fraudLogObject.adObjectId}</a>
                                        </td>
                                        <td>
                                            <a class="mouse-pointer" onclick="openerRedirect('showuserbyemail.html?email=${fraudLogObject.email}')">${fraudLogObject.email}</a>
                                        </td>
                                        <td>
                                            ${fraudLogObject.addDateString}
                                        </td>
                                        <td>
                                            ${fraudLogObject.fraudMessage}
                                        </td>
                                        <td class="align_center">
                                            <!--
                                                <input type="button" class="greybutton" name="btnblockuser" value="<spring:message code="fraudlog.block.user"/>" onclick="blockUser('${fraudLogObject.email}')" />
                                            -->
                                            <input type="checkbox" name="cbblockuser" value="${fraudLogObject.email}" />
                                        </td>
                                        <td class="align_center">
                                            <input type="radio" name="${fraudLogObject.logId}" value="approve">
                                        </td>
                                        <td class="align_center">
                                            <input type="radio" name="${fraudLogObject.logId}" value="deny">
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </div>
            </fieldset>

            <!-- summary -->
            <br class="clear" />
            <p>Totalt ${totalFraudList} resultater</p>
            <br/><br/>

            <div class="align_center">

                <c:if test="${currentPage != 1}">
                    <a href="fraudlog.html?page=${currentPage - 1}" class="btn_gray">&#171; <spring:message code="page.show.page.previous"/></a>
                </c:if>

                    &#160;&#160;<spring:message code="page.show.page"/>&#160;&#160;:&#160;&#160;
                    <input type="text" id="txtpage" class="paging" value="${currentPage}" />&#160;&#160;/&#160;&#160; ${totalPage} &#160;&#160;
                    <a class="btn_gray" onclick="jumpToFraudLogPage()"><spring:message code="page.show.page.go"/></a>&#160;&#160;

                <c:if test="${currentPage != totalPage}">
                    <a href="fraudlog.html?page=${currentPage + 1}" class="btn_gray"><spring:message code="page.show.page.next"/> &#187;</a>
                </c:if>

                <br class="clear" />
                <br/><br/><br/><br/>
                <input class="btn_save" type="button" name="btnsubmit" onclick="fraudLogProcess()" value='<spring:message code="showad.btn.save"/>' /><br/><br/>
            </div>

        </form>

        <form id="frmfraudadprocess" name="frmfraudadprocess" method="post" action="fraudadprocess.html">
            <input type="hidden" id="hdaprrovelist" name="hdaprrovelist" value="" />
            <input type="hidden" id="hddenylist" name="hddenylist" value="" />
            <input type="hidden" id="hdblocklist" name="hdblocklist" value="" />
        </form>

    </div>

<%@ include file="template/footer.jsp" %>