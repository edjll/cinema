<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/films.css">
    <link rel="stylesheet" href="/static/css/admin/admin.css">
    <link rel="stylesheet" href="/static/css/admin/films.css">
    <link rel="stylesheet" href="/static/css/admin/form.css">
</#assign>

<@templateFtl.default
links = links
login = false
panel = true
title = 'Панель администратора - удаление фильма'
>
    <main>
        <div class="container-custom">
            <div class="wrapper">
                <div class="">
                    <div class="panel">
                        <div class="panel-body">
                            <ul class="nav nav-tabs">
                                <li class="nav-item"><a href="/admin/films" class="nav-link active">Фильмы</a></li>
                                <li class="nav-item"><a href="/admin/categories" class="nav-link">Категории</a></li>
                                <li class="nav-item"><a href="/admin/subscribes" class="nav-link">Подписки</a></li>
                                <li class="nav-item"><a href="/admin/users" class="nav-link">Пользователи</a></li>
                                <li class="nav-item"><a href="/admin/promo" class="nav-link">Промо</a></li>
                            </ul>
                            <div class="content">
                                <form action="/admin/film/delete" method="post" enctype="multipart/form-data">
                                    <div class="table-wrapper">
                                        <table class="table table-th-block">
                                            <tbody>
                                            <tr>
                                                <td>Название*</td>
                                                <td>
                                                    <div>
                                                        <input type="text"
                                                               name="title"
                                                               class="form-control"
                                                               value="${film.title}"
                                                               readonly
                                                        >
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Описание*</td>
                                                <td>
                                                    <div>
                                                        <input type="text"
                                                               name="description"
                                                               class="form-control"
                                                               value="${film.description}"
                                                               readonly
                                                        >
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Дата выхода</td>
                                                <td>
                                                    <div>
                                                        <input type="date"
                                                               name="releaseDate"
                                                               class="form-control"
                                                               <#if film.releaseDate??>value="${film.releaseDate?string('yyyy-MM-dd')}"</#if>
                                                               readonly
                                                        >
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Возрастное ограничение</td>
                                                <td>
                                                    <div>
                                                        <input type="number"
                                                               min="0"
                                                               name="ageLimit"
                                                               class="form-control"
                                                               oninput="{changeAgeLimit(this)}"
                                                               value="${film.ageLimit}"
                                                               readonly
                                                        >
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Доступ*</td>
                                                <td>
                                                    <div>
                                                        <input
                                                            class="form-control"
                                                            name="accessType"
                                                            onchange="{changeAccess(this)}"
                                                            value="${film.access.type.getTitle()}"
                                                            readonly
                                                        >
                                                    </div>
                                                </td>
                                            </tr>
                                            <#if film.access.type == 'BUY'>
                                                <tr>
                                                    <td>Стоимость</td>
                                                    <td>
                                                        <div>
                                                            <input type="text"
                                                                   name="price"
                                                                   class="form-control"
                                                                   <#if film.access??>value="${film.access.price?c}"</#if>
                                                                   readonly
                                                            >
                                                        </div>
                                                    </td>
                                                </tr>
                                            </#if>
                                            <tr>
                                                <td>Превью*</td>
                                                <td class="text-center">
                                                    <a href="/preview/${film.preview}" target="_blank">
                                                        <img src="/static/image/icon/link.svg">
                                                    </a>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Трейлер*</td>
                                                <td class="text-center">
                                                    <a href="/trailer/${film.trailer}" target="_blank">
                                                        <img src="/static/image/icon/link.svg">
                                                    </a>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Видео</td>
                                                <td class="text-center">
                                                    <#if film.video??>
                                                        <a href="/filmVideo/${film.video}" target="_blank">
                                                            <img src="/static/image/icon/link.svg">
                                                        </a>
                                                    </#if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Категории*</td>
                                                <td>
                                                    <div class="input-group input-group-custom-select">
                                                        <div class="select-multiple-wrapper">
                                                            <select class="custom-select custom-select-multiple" name="category[]" multiple>
                                                                <#list film.categories as category>
                                                                    <option
                                                                            value="${category.id}"
                                                                            selected
                                                                            onclick="{return false}"
                                                                            disabled
                                                                    >${category.name}</option>
                                                                </#list>
                                                            </select>
                                                        </div>
                                                        <div class="input-group-append">
                                                            <span class="btn btn-outline-primary select-multiple-button" onclick="{expandOrCollapseSelect(this)}">Раскрыть</span>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Подписки</td>
                                                <td>
                                                    <div class="input-group input-group-custom-select">
                                                        <div class="select-multiple-wrapper">
                                                            <select class="custom-select custom-select-multiple" name="subscribe[]" multiple>
                                                                <#list film.subscribes as subscribe>
                                                                    <option
                                                                            value="${subscribe.id}"
                                                                            selected
                                                                            onclick="{return false;}"
                                                                            disabled
                                                                    >${subscribe.name}</option>
                                                                </#list>
                                                            </select>
                                                        </div>
                                                        <div class="input-group-append">
                                                            <span class="btn btn-outline-primary select-multiple-button" onclick="{expandOrCollapseSelect(this)}">Раскрыть</span>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <input type="hidden" name="id" value="${film.id}">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                                    <button class="btn btn-primary">Удалить</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="/static/js/film/form.js"></script>
    <script src="/static/js/film.js"></script>
    <script src="/static/js/form/selectMultiple.js"></script>
    <script src="/static/js/form/file.js"></script>
</@templateFtl.default>