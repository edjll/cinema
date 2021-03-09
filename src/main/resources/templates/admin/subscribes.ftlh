<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/films.css">
    <link rel="stylesheet" href="/static/css/admin/admin.css">
    <link rel="stylesheet" href="/static/css/admin/films.css">
</#assign>

<#macro subscribersCount periods>
    <#assign value = 0>
    <#list periods as period><#assign value = value + period.users?size></#list>
    ${value}
</#macro>

<#assign itemsInRow = 6>

<@templateFtl.default
links = links
title = 'Панель администратора - фильмы'
panel = true
>
    <main>
        <div class="container-custom">
            <div class="wrapper">
                <div class="">
                    <div class="panel">
                        <div class="panel-body">
                            <ul id="myTab" class="nav nav-tabs">
                                <li class="nav-item"><a href="/admin/films" class="nav-link">Фильмы</a></li>
                                <li class="nav-item"><a href="/admin/categories" class="nav-link">Категории</a></li>
                                <li class="nav-item"><a href="/admin/subscribes" class="nav-link active">Подписки</a></li>
                                <li class="nav-item"><a href="/admin/users" class="nav-link">Пользователи</a></li>
                                <li class="nav-item"><a href="/admin/promo" class="nav-link">Промо</a></li>
                            </ul>
                            <div class="table-wrapper">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th></th>
                                        <th></th>
                                        <th scope="col">Название</th>
                                        <th scope="col">Фильмы</th>
                                        <th scope="col">Подписчиков</th>
                                        <th scope="col">Активных подписчиков</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if 0 < subscribes?size>
                                        <#list subscribes as subscribe, activeSubscribersCount>
                                            <tr>
                                                <th scope="row">
                                                    <a href="/films/subscribe/${subscribe.id}">${subscribe.id}</a>
                                                </th>
                                                <td>
                                                    <a href="/admin/subscribe/${subscribe.id}/update">
                                                        <img src="/static/image/icon/edit.svg" alt="edit" title="Редактировать">
                                                    </a>
                                                </td>
                                                <#assign subscribersCount = 0>
                                                <#list subscribe.periods as period><#assign subscribersCount = subscribersCount + period.users?size></#list>
                                                <td>
                                                    <#if subscribersCount == 0>
                                                        <a href="/admin/subscribe/${subscribe.id}/delete">
                                                            <img src="/static/image/icon/delete.svg" alt="delete" title="Удалить">
                                                        </a>
                                                    </#if>
                                                </td>
                                                <td>${subscribe.name}</td>
                                                <td>${subscribe.films?map(film -> film.id)?join(", ")}</td>
                                                <td>${subscribersCount}</td>
                                                <td>${activeSubscribersCount}</td>
                                            </tr>
                                        </#list>
                                    <#else>
                                        <tr>
                                            <td colspan="7" class="text-center">
                                                На сайте на данный момент нет подписок
                                            </td>
                                        </tr>
                                    </#if>
                                    </tbody>
                                </table>
                            </div>
                            <a href="/admin/subscribe/create" class="ml-2">Создать</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="/static/js/user/role.js"></script>
</@templateFtl.default>