<#macro template
    create = false
    update = false
    delete = false
>

    <#import "../macro/template.ftlh" as templateFtl>
    <#import "../macro/film.ftlh" as filmFtl>

    <#assign links>
        <link rel="stylesheet" href="/static/css/index.css">
        <link rel="stylesheet" href="/static/css/films.css">
        <link rel="stylesheet" href="/static/css/admin/admin.css">
        <link rel="stylesheet" href="/static/css/admin/films.css">
    </#assign>

    <#if create><#assign title = 'Панель администратора - создание категории'></#if>
    <#if update><#assign title = 'Панель администратора - обновление категории'></#if>
    <#if delete><#assign title = 'Панель администратора - удаление категории'></#if>

    <@templateFtl.default
        links = links
        login = false
        panel = true
        title = title
    >
        <main>
            <div class="container-custom">
                <div class="wrapper">
                    <div class="">
                        <div class="panel">
                            <div class="panel-body">
                                <ul class="nav nav-tabs">
                                    <li class="nav-item"><a href="/admin/films" class="nav-link">Фильмы</a></li>
                                    <li class="nav-item"><a href="/admin/categories" class="nav-link active">Категории</a></li>
                                    <li class="nav-item"><a href="/admin/subscribes" class="nav-link">Подписки</a></li>
                                    <li class="nav-item"><a href="/admin/users" class="nav-link">Пользователи</a></li>
                                    <li class="nav-item"><a href="/admin/promo" class="nav-link">Промо</a></li>
                                </ul>
                                <div class="content">
                                    <form
                                            <#if create>action="/admin/category/create"</#if>
                                            <#if update>action="/admin/category/update"</#if>
                                            <#if delete>action="/admin/category/delete"</#if>
                                            method="post"
                                    >
                                        <div class="table-wrapper">
                                            <table class="table table-th-block">
                                                <tbody>
                                                <tr>
                                                    <td class="title-padding">Название*</td>
                                                    <td>
                                                        <div>
                                                            <input
                                                                    type="text"
                                                                    name="name"
                                                                    class="form-control ${(errors?? && errors["categoryName"]??)?then('is-invalid', '')}"
                                                                    <#if errors?? && errors["categoryName"]??>value="${category.name}"</#if>
                                                                    <#if (update || delete)>value="${category.name}"</#if>
                                                                    <#if delete>readonly</#if>
                                                            >
                                                            <#if errors?? && errors["categoryName"]??>
                                                                <div class="invalid-feedback">
                                                                    ${errors["categoryName"]}
                                                                </div>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th></th>
                                                    <th scope="col">#</th>
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
                                                    <#if errors?? && errors['categoryFilms']??>
                                                        <tr>
                                                            <td colspan="13" class="text-center error">${errors['categoryFilms']}</td>
                                                        </tr>
                                                    </#if>
                                                    <#if !films?? && category?? && category.films?size == 0>
                                                        <tr>
                                                            <td colspan="13" class="text-center">У данной категории нет фильмов</td>
                                                        </tr>
                                                    </#if>
                                                    <#if (update || delete) && category.films??>
                                                        <#list category.films as film>
                                                            <tr>
                                                                <td>
                                                                    <input
                                                                            type="checkbox"
                                                                            name="film[]"
                                                                            value="${film.id}"
                                                                            <#if (update || delete) && category.films?seq_contains(film)>checked</#if>
                                                                            <#if delete>onclick="{return false;}"</#if>
                                                                    >
                                                                </td>
                                                                <th scope="row">
                                                                    <a href="/film/${film.id}">${film.id}</a>
                                                                </th>
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
                                                                        >${sub.id}</a>
                                                                    </#list>
                                                                </td>
                                                            </tr>
                                                        </#list>
                                                    </#if>
                                                    <#if films??>
                                                        <#if 0 < films?size>
                                                            <#list films as film>
                                                                <tr>
                                                                    <td>
                                                                        <input
                                                                                type="checkbox"
                                                                                name="film[]"
                                                                                value="${film.id}"
                                                                                <#if checkedFilms?? && checkedFilms?seq_contains(film)>checked</#if>
                                                                                <#if delete>onclick="{return false;}"</#if>
                                                                        >
                                                                    </td>
                                                                    <th scope="row">
                                                                        <a href="/film/${film.id}">${film.id}</a>
                                                                    </th>
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
                                                                            >${sub.id}</a>
                                                                        </#list>
                                                                    </td>
                                                                </tr>
                                                            </#list>
                                                        <#else>
                                                            <#if !category?? || !category.films??>
                                                                <tr>
                                                                    <td colspan="14" class="text-center">
                                                                        На сайте на данный момент нет фильмов
                                                                    </td>
                                                                </tr>
                                                            </#if>
                                                        </#if>
                                                    </#if>
                                                </tbody>
                                            </table>
                                        </div>
                                        <#if (update || delete)><input type="hidden" name="id" value="${category.id}"></#if>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                                        <button class="btn btn-primary">
                                            <#if create>Создать</#if>
                                            <#if update>Сохранить</#if>
                                            <#if delete>Удалить</#if>
                                        </button>
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
    </@templateFtl.default>
</#macro>