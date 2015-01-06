<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
    <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>Zeeland</title>
        <link rel="stylesheet" type="text/css" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.css" />
        <link rel="stylesheet" type="text/css" href="css/styles.css" />
        <link rel="stylesheet" type="text/css" href="css/tinybox.css" />

        <script type="text/javascript" src="js/jqueryscript.js"></script>
        <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.10.4.js"></script>
        <script type="text/javascript" src="js/tinybox.js"></script>
        <script type="text/javascript" src="js/tinyboxscript.js"></script>
        <script type="text/javascript" src="js/default.js"></script>
        <script type="text/javascript" src="js/popup.js"></script>

        <!--[if gte IE 9]>
          <style type="text/css">
            nav, #show_status, .btn_gray, .btn_gray:hover, .btn_red, .btn_red:hover, .btn_save, .btn_save:hover, .btn_save_sm, .btn_save_sm:hover {
               filter: none;
            }
          </style>
        <![endif]-->

    </head>

    <body>
        <div id="page">

            <header>
                <div id="admin_head">
                    <div id="top_menu_logo"></div>
                    <h1><spring:message code="system.title"/></h1>
                </div>
            </header>

            <nav>
                <a href="home.html">Hjem</a>
                <a href="searchad.html">Søk annonse</a>
                <a href="searchuser.html">Søk bruker</a>
                <a href="searchcompany.html">Søk firma</a>
                <a href="adduser.html">Legg til bruker</a>
                <a href="addcompany.html">Legg til firma</a>
                <a href="delad.html">Slette annonser</a>
                <a href="fraudlog.html" target="_blank">Gå til svindellog</a>
                <a href="bookingadmin.html" target="_blank">Gå til bookingadmin</a>
            </nav>

            <div class="show_updatemessage">
                <c:if test="${updateMessage!=null}">
                    <spring:message code="${updateMessage}"/>
                </c:if>
            </div>

