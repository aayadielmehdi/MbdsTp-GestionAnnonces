<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'illustration.label', default: 'Illustration')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-illustration" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>



<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
%{--        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--}%
    </ul>
</div>



<div id="list-illustration" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>



%{--    <f:table collection="${illustrationList}" />--}%

    <table>
        <thead>
        <tr>
            <th class="sortable sorted asc" >
                Filename
            </th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${illustrationList}" var="illustration" status="i">
            <tr class="${ (i%2) == 0 ? 'even' : 'odd' }">
                <td>
                    <g:link controller="illustration" action="show"  id="${illustration.id}">
                        <g:img width="50" file="${illustration.filename}"/>
                    </g:link>
                </td>
            </>
        </g:each>

        </tbody>
    </table>


    <div class="pagination">
        <g:paginate total="${illustrationCount ?: 0}" />
    </div>
</div>
</body>
</html>