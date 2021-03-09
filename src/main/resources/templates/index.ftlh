<#import "macro/template.ftlh" as templateFtl>
<#import "macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
</#assign>

<@templateFtl.default
    links = links
    login = false
>
    <main>
        <div class="background-image">
            <img src="/static/image/home_background.jpg">
            <div class="background-shadow"></div>
        </div>
        <div class="registration">
            <p>Регистрируйся и смотри лучшие фильмы</p>
            <a href="/registration" class="btn btn-primary">Зарегистрироваться</a>
        </div>
    </main>
</@templateFtl.default>