<#import "macro/template.ftlh" as templateFtl>
<#import "macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/message.css">
</#assign>

<#assign error = (errorCode??)?then(true, false)>

<@templateFtl.default
    links = links
    title = 'Cinema'
    error = error
    login = false
>
    <main>
        <div class="background-image">
            <img src="/static/image/home_background.jpg">
            <div class="background-shadow"></div>
        </div>
        <div class="container">
            <#if errorCode??><p class="error-code">${errorCode}</p></#if>
            <#if message??><p class="message">${message}</p></#if>
            <#if films??>
                <p class="message">Фильмы:<#list films as film> <a href="/film/${film.id}">${film.id}</a></#list></p>
            </#if>
        </div>
    </main>
</@templateFtl.default>