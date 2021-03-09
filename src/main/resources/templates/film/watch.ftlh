<#import "../macro/template.ftlh" as templateFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
    <link rel="stylesheet" href="/static/css/film.css">
</#assign>

<#assign title = film.title>

<@templateFtl.default
    links = links
    title = title
>
    <main>
        <video class="film-video" autoplay preload="auto" muted controls>
            <source src="/filmVideo/${film.video}" type="video/mp4">
        </video>
    </main>
</@templateFtl.default>