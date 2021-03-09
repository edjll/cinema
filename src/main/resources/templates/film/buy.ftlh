<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
</#assign>

<@templateFtl.default
    links = links
    login = false
    panel = false
>
    <main>
        <div class="card" style="width: 18rem;">
            <div class="card-header">
                ${film.title}
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">${film.access.price}</li>
            </ul>
            <div class="card-body">
                <form method="post" action="/film/${film.id}/buy">
                    <button class="btn btn-primary">Купить</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                </form>
            </div>
        </div>
    </main>
</@templateFtl.default>