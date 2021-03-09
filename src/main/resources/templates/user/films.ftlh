<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
    <link rel="stylesheet" href="/static/css/user/user.css">
</#assign>

<#assign itemsInRow = 4>

<@templateFtl.default
    links = links
    title = 'Купленные фильмы'
>
    <main>
        <div class="container-custom">
            <div class="wrapper">
                <div class="">
                    <div class="panel">
                        <div class="panel-body">
                            <ul id="myTab" class="nav nav-tabs">
                                <li class="nav-item"><a href="/profile" class="nav-link">Информация</a></li>
                                <li class="nav-item"><a href="/profile/films" class="nav-link active">Фильмы</a></li>
                                <li class="nav-item"><a href="/profile/subscribes" class="nav-link">Подписки</a></li>
                                <li class="nav-item"><a href="/profile/promo" class="nav-link">Промо</a></li>
                            </ul>
                            <div class="table-wrapper">
                                <div id="films">
                                    <#if user.films??>
                                        <#list user.films as film>
                                            <div class="col-${12 / itemsInRow} film">
                                                <@filmFtl.card film></@filmFtl.card>
                                            </div>
                                        </#list>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</@templateFtl.default>