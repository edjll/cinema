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
                                <li class="nav-item"><a href="/admin/subscribes" class="nav-link">Подписки</a></li>
                                <li class="nav-item"><a href="/admin/users" class="nav-link">Пользователи</a></li>
                                <li class="nav-item"><a href="/admin/promo" class="nav-link active">Промо</a></li>
                            </ul>
                            <div class="table-wrapper">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th></th>
                                        <th></th>
                                        <th scope="col">Название</th>
                                        <th scope="col">Активаций</th>
                                        <th scope="col">Дата окончания</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if 0 < promos?size>
                                        <#list promos as promo, activation>
                                            <tr>
                                                <th scope="row">
                                                    <span>${promo.id}</span>
                                                </th>
                                                <td>
                                                    <a href="/admin/promo/${promo.id}/update">
                                                        <img src="/static/image/icon/edit.svg" alt="edit" title="Редактировать">
                                                    </a>
                                                </td>
                                                <td>
                                                    <a href="/admin/promo/${promo.id}/delete">
                                                        <img src="/static/image/icon/delete.svg" alt="delete" title="Удалить">
                                                    </a>
                                                </td>
                                                <td>${promo.name}</td>
                                                <td>${activation} / ${promo.activationCount}</td>
                                                <td>${promo.dateEnd?string('dd.MM.yyyy')}</td>
                                            </tr>
                                        </#list>
                                    <#else>
                                        <tr>
                                            <td colspan="7" class="text-center">
                                                На сайте на данный момент нет промо
                                            </td>
                                        </tr>
                                    </#if>
                                    </tbody>
                                </table>
                            </div>
                            <a href="/admin/promo/create" class="ml-2">Создать</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="/static/js/user/role.js"></script>
</@templateFtl.default>