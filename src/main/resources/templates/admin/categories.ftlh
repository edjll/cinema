<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/films.css">
    <link rel="stylesheet" href="/static/css/admin/admin.css">
    <link rel="stylesheet" href="/static/css/admin/categories.css">
</#assign>

<#assign itemsInRow = 6>

<@templateFtl.default
links = links
title = 'Панель администратора - категории'
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
                                <li class="nav-item"><a href="/admin/categories" class="nav-link active">Категории</a></li>
                                <li class="nav-item"><a href="/admin/subscribes" class="nav-link">Подписки</a></li>
                                <li class="nav-item"><a href="/admin/users" class="nav-link">Пользователи</a></li>
                                <li class="nav-item"><a href="/admin/promo" class="nav-link">Промо</a></li>
                            </ul>
                            <div class="table-wrapper">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col"></th>
                                        <th scope="col"></th>
                                        <th scope="col">Название</th>
                                        <th scope="col">Фильмы</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if 0 < categories?size>
                                        <#list categories as category, films>
                                            <tr>
                                                <th scope="row">
                                                    <a href="/films/category/${category.id}">${category.id}</a>
                                                </th>
                                                <td>
                                                    <a href="/admin/category/${category.id}/update">
                                                        <img src="/static/image/icon/edit.svg" alt="edit" title="Редактировать">
                                                    </a>
                                                </td>
                                                <td>
                                                    <#if films?size == 0>
                                                        <a href="/admin/category/${category.id}/delete">
                                                            <img src="/static/image/icon/delete.svg" alt="delete" title="Удалить">
                                                        </a>
                                                    </#if>
                                                </td>
                                                <td>${category.name}</td>
                                                <td>
                                                    <#list category.films as film>
                                                        <#if 0 < film?index>, </#if><a
                                                            href="/film/${film.id}"
                                                            <#if films?seq_contains(film)>class="with-one"</#if>
                                                        >${film.id}</a>
                                                    </#list>
                                                </td>
                                            </tr>
                                        </#list>
                                    <#else>
                                        <tr>
                                            <td colspan="5" class="text-center">
                                                На сайте на данный момент нет категорий
                                            </td>
                                        </tr>
                                    </#if>
                                    </tbody>
                                </table>
                            </div>
                            <a href="/admin/category/create" class="ml-2">Создать</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="/static/js/film.js"></script>
</@templateFtl.default>