<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'annonce.label', default: 'Annonce')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<a href="#edit-annonce" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="edit-annonce" class="content scaffold-edit" role="main">
    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${this.annonce}">
        <ul class="errors" role="alert">
            <g:eachError bean="${this.annonce}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form resource="${this.annonce}" method="POST" enctype="multipart/form-data">
        <g:hiddenField name="version" value="${this.annonce?.version}" />

    %{--                <fieldset class="form">--}%
    %{--                    <f:all bean="annonce"/>--}%
    %{--                </fieldset>--}%

        <f:field bean="annonce" property="title"/>
        <f:field bean="annonce" property="description"/>
        <f:field bean="annonce" property="validTill"/>
        <f:field property="state" bean="annonce"/>
%{--        <f:field bean="annonce" property="author">--}%
%{--            <select name="author"  id="author">--}%
%{--                <g:each in="${gestionannonces.User.all}" var="user">--}%
%{--                    <option value="${user.id}">${user.username}</option>--}%
%{--                </g:each>--}%
%{--            </select>--}%
%{--        </f:field>--}%

        <f:field bean="annonce" property="illustrations">
            <input multiple="multiple" type="file" name="illus" accept="image/png" />
            <div class="property-value" aria-labelledby="illustrations-label">
                <g:each in="${annonce.illustrations}" var="illustrationsAnnonce">
                    <br><br>
                    <g:img width="100" src="${illustrationsAnnonce.filename}" />

                    <g:link controller="annonce" action="deleteIllustrationAnnonce"
                            params="[ann_id: annonce.id ,
                                     illustration_id : illustrationsAnnonce.id]"> delete </g:link>
                </g:each>
            </div>
        </f:field>

        <br>

        <fieldset class="buttons">
            <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
