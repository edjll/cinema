<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/films.css">
    <link rel="stylesheet" href="/static/css/admin/admin.css">
    <link rel="stylesheet" href="/static/css/admin/films.css">
</#assign>

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
                                <li class="nav-item"><a href="/admin/films" class="nav-link active">Фильмы</a></li>
                                <li class="nav-item"><a href="/admin/categories" class="nav-link">Категории</a></li>
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
                                        <th scope="col">Описание</th>
                                        <th scope="col">Дата выхода</th>
                                        <th scope="col">Ограничение</th>
                                        <th scope="col">Рейтинг</th>
                                        <th scope="col">Превью</th>
                                        <th scope="col">Трейлер</th>
                                        <th scope="col">Видео</th>
                                        <th scope="col">Доступ</th>
                                        <th scope="col">Категории</th>
                                        <th scope="col">Подписки</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if 0 < films?size>
                                        <#list films as film, subsWithOneFilm>
                                            <tr>
                                                <th scope="row">
                                                    <a href="/film/${film.id}">${film.id}</a>
                                                </th>
                                                <td>
                                                    <a href="/admin/film/${film.id}/update">
                                                        <img src="/static/image/icon/edit.svg" alt="edit" title="Редактировать">
                                                    </a>
                                                </td>
                                                <td>
                                                    <#if subsWithOneFilm?size == 0>
                                                        <#list filmHasUsers as key, value>
                                                            <#if key == film.id && !value>
                                                                <a href="/admin/film/${film.id}/delete">
                                                                    <img src="/static/image/icon/delete.svg" alt="delete" title="Удалить">
                                                                </a>
                                                            </#if>
                                                        </#list>
                                                    </#if>
                                                </td>
                                                <td><span class="text-truncate truncate">${film.title}</span></td>
                                                <td><span class="text-truncate truncate">${film.description}</span></td>
                                                <td class="text-center"><#if film.releaseDate??>${film.releaseDate?string('dd.MM.yyyy')}</#if></td>
                                                <td class="text-center"><#if film.ageLimit??>${film.ageLimit}+</#if></td>
                                                <td class="text-center"><#if 0 < film.rating?size><@filmFtl.ratingValue film.rating></@filmFtl.ratingValue></#if></td>
                                                <td class="text-center">
                                                    <a href="/preview/${film.preview}" target="_blank">
                                                        <img src="/static/image/icon/link.svg">
                                                    </a>
                                                </td>
                                                <td class="text-center">
                                                    <a href="/trailer/${film.trailer}" target="_blank">
                                                        <img src="/static/image/icon/link.svg">
                                                    </a>
                                                </td>
                                                <td class="text-center">
                                                    <#if film.video??>
                                                        <a href="/filmVideo/${film.video}" target="_blank">
                                                            <img src="/static/image/icon/link.svg">
                                                        </a>
                                                    </#if>
                                                </td>
                                                <td>${film.access.type.getTitle()}${(film.access.type.getTitle() == 'Покупка')?then(', ' + film.access.price, '')}</td>
                                                <td>
                                                    <#list film.categories as category>
                                                        <#if 0 < category?index>, </#if>
                                                        <a
                                                                href="/films/category/${category.id}"
                                                        >${category.id}</a>
                                                    </#list>
                                                </td>
                                                <td>
                                                    <#list film.subscribes as sub>
                                                        <#if 0 < sub?index>, </#if>
                                                        <a
                                                            href="/films/subscribe/${sub.id}"
                                                            <#if subsWithOneFilm?seq_contains(sub)>class="with-one"</#if>
                                                        >${sub.id}</a>
                                                    </#list>
                                                </td>
                                            </tr>
                                        </#list>
                                    <#else>
                                        <tr>
                                            <td colspan="14" class="text-center">
                                                На сайте на данный момент нет фильмов
                                            </td>
                                        </tr>
                                    </#if>
                                    </tbody>
                                </table>
                            </div>
                            <a href="/admin/film/create" class="ml-2">Создать</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="/static/js/film.js"></script>
</@templateFtl.default>