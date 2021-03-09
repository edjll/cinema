<#import "jquery.ftlh" as jqueryFtl>
<#import "header.ftlh" as headerFtl>

<#macro default
    links = ""
    title = "Cinema"
    login = true
    panel = false
    error = false
>
    <!doctype html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="csrf" content="${_csrf.token}">
        <@jqueryFtl.css></@jqueryFtl.css>
        <link rel="stylesheet" href="/static/css/carousel.css">
        ${links}
        <title>${title}</title>
    </head>
    <body class="d-flex flex-column justify-content-between">
        <@headerFtl.default
            login = login
            panel = panel
            error = error
        ></@headerFtl.default>
        <#nested>
    </body>
    <@jqueryFtl.js></@jqueryFtl.js>
    </html>
</#macro>