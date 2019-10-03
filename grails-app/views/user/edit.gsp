<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<a href="#edit-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="edit-user" class="content scaffold-edit" role="main">
    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${this.user}">
        <ul class="errors" role="alert">
            <g:eachError bean="${this.user}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form resource="${this.user}" method="POST" enctype='multipart/form-data' >

%{--        pour connaitre l'ecran edition ou creation--}%
        <g:hiddenField name="methode" value="edition" />


        <g:hiddenField name="version" value="${this.user?.version}" />
    %{--                <fieldset class="form">--}%
    %{--                    <f:all bean="user"/>--}%
    %{--                </fieldset>--}%

        <f:field bean="user" property="username"/>
        <f:field bean="user" property="thumbnail">
            <g:img width="100" file="${user.thumbnail.filename}" />
        </f:field>

        <f:field bean="user" property="annonces">
            <div class="property-value" aria-labelledby="annonces-label">
                <ul>
                    <g:each in="${user.annonces}" var="ann">
                        <li><g:link controller="annonce" action="show" id="${ann.id}">${ann.title}</g:link></li>
                    </g:each>
                </ul>
            </div>
        </f:field>

        <div class='fieldcontain required'>
            <label for='thumbnail'>Thumbnail Update
            </label>
            <input type="file" name="fileUpload" id="thumbnail" accept="image/png" />
        </div>
        <br>


        <fieldset class="buttons">
            <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
        </fieldset>

    </g:form>
</div>
</body>
</html>
