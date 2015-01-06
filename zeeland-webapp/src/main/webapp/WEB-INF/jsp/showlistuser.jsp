<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="template/header.jsp" %>
    <div class="info">
        <div id="title">
            <h2><spring:message code="page.title.searchUser" /></h2>
        </div>
        <fieldset>
            <legend><spring:message code="page.title.searchUser" /></legend>
        </fieldset>
        <%@ include file="template/form_search_user.jsp" %>
        <section>
            <div class="topic_line_gray">Resultater</div>
            <div class="tab_gradient"></div>
            <div class="info_group">
                <table>
                    <thead>
                        <tr>
                            <th class="col9">
                                <spring:message code="user.field.userId" />
                            </th>
                            <th >
                                <spring:message code="user.field.name" />
                            </th>
                            <th class="col14">
                                <spring:message code="user.field.email" />
                            </th>
                            <th class="col10">
                                <spring:message code="user.field.type" />
                            </th>
                            <th class="col13">
                                <spring:message code="user.field.linkZeelandSite" />
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="zettUser" items="${zettUserList}">
                            <tr>
                                <td>
                                    ${zettUser.userId}
                                </td>
                                <td>
                                    ${zettUser.name}
                                </td>
                                <td>
                                    ${zettUser.email}
                                </td>
                                <td>
                                    ${zettUser.userType}
                                </td>
                                <td>
                                    <a href="${zeelandUrl}${zettUser.userId}">View</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>
        <br class="clear" />
    </div>
<%@ include file="template/footer.jsp" %>