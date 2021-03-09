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

    <#if create><#assign title = 'Панель администратора - создание подписки'></#if>
    <#if update><#assign title = 'Панель администратора - обновление подписки'></#if>
    <#if delete><#assign title = 'Панель администратора - удаление подписки'></#if>

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
                                    <li class="nav-item"><a href="/admin/categories" class="nav-link">Категории</a></li>
                                    <li class="nav-item"><a href="/admin/subscribes" class="nav-link active">Подписки</a></li>
                                    <li class="nav-item"><a href="/admin/users" class="nav-link">Пользователи</a></li>
                                    <li class="nav-item"><a href="/admin/promo" class="nav-link">Промо</a></li>
                                </ul>
                                <div class="content">
                                    <form
                                            <#if create>action="/admin/subscribe/create"</#if>
                                            <#if update>action="/admin/subscribe/update"</#if>
                                            <#if delete>action="/admin/subscribe/delete"</#if>
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
                                                                    class="form-control ${(errors?? && errors["subscribeName"]??)?then('is-invalid', '')}"
                                                                    <#if errors?? && errors["subscribeName"]??>value="${subscribe.name}"</#if>
                                                                    <#if (update || delete)>value="${subscribe.name}"</#if>
                                                                    <#if delete>readonly</#if>
                                                            >
                                                            <#if errors?? && errors["subscribeName"]??>
                                                                <div class="invalid-feedback">
                                                                    ${errors["subscribeName"]}
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
                                                    <#if create || update><th scope="col"></th></#if>
                                                    <th scope="col">#</th>
                                                    <th scope="col">Период</th>
                                                    <th scope="col">Стоимость</th>
                                                    <#if update><th scope="col">Активных подписчиков</th></#if>
                                                    <#if update || delete><th scope="col">Активность</th></#if>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <#if errors?? && errors['subscribePeriods']??>
                                                    <tr>
                                                        <td colspan="13" class="text-center error">${errors['subscribePeriods']}</td>
                                                    </tr>
                                                </#if>
                                                <#if update || delete>
                                                    <#list subscribe.periods as period>
                                                        <tr>
                                                            <#if update>
                                                                <td>
                                                                    <#if countActiveSubscribers[period?index] == 0>
                                                                        <p class="m-0 delete" onclick="{deleteRowInTable(this)}">-</p>
                                                                    </#if>
                                                                    <input type="hidden" name="periodId[]" value="${period.id}">
                                                                </td>
                                                            </#if>
                                                            <th scope="row">${period?counter}</th>
                                                            <td>
                                                                <input
                                                                        type="number"
                                                                        class="form-control"
                                                                        name="period[]"
                                                                        value="${period.period?c}"
                                                                        <#if delete>readonly</#if>
                                                                >
                                                            </td>
                                                            <td>
                                                                <input
                                                                        type="number"
                                                                        class="form-control"
                                                                        name="price[]"
                                                                        value="${period.price?c}"
                                                                        <#if delete>readonly</#if>
                                                                >
                                                            </td>
                                                            <#if update>
                                                                <td>${countActiveSubscribers[period?index]}</td>
                                                            </#if>
                                                            <td>
                                                                <#if delete>
                                                                    <input class="form-control" readonly value="${period.active?then('Активен', 'Не активен')}">
                                                                <#else>
                                                                    <select class="form-control" name="periodActive[]">
                                                                        <option value="true">Активен</option>
                                                                        <option value="false" <#if !period.active>selected</#if>>Не активен</option>
                                                                    </select>
                                                                </#if>
                                                            </td>
                                                        </tr>
                                                    </#list>
                                                </#if>
                                                <#if create>
                                                    <tr>
                                                        <td>
                                                            <p class="m-0 delete" onclick="{deleteRowInTable(this)}">-</p>
                                                        </td>
                                                        <th scope="row">1</th>
                                                        <td><input type="number" class="form-control" name="period[]"></td>
                                                        <td><input type="number" class="form-control" name="price[]"></td>
                                                    </tr>
                                                </#if>
                                                <#if create || update>
                                                    <tr>
                                                        <td><p class="m-0 add" onclick="{addRowInTable(this${update?then(', true', '')})}">+</p></td>
                                                        <th scope="row"></th>
                                                        <td></td>
                                                        <td></td>
                                                        <#if update>
                                                            <td></td>
                                                            <td></td>
                                                        </#if>
                                                    </tr>
                                                </#if>
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
                                                <#if errors?? && errors['subscribeFilms']??>
                                                    <tr>
                                                        <td colspan="13" class="text-center error">${errors['subscribeFilms']}</td>
                                                    </tr>
                                                </#if>
                                                <#if !films?? && subscribe?? && subscribe.films?size == 0>
                                                    <tr>
                                                        <td colspan="13" class="text-center">У данной подписки нет фильмов</td>
                                                    </tr>
                                                </#if>
                                                <#if (update || delete) && subscribe.films??>
                                                    <#list subscribe.films as film>
                                                        <tr>
                                                            <td>
                                                                <input
                                                                        type="checkbox"
                                                                        name="film[]"
                                                                        value="${film.id}"
                                                                        checked
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
                                                <#if films?? && 0 < films?size>
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
                                                    <#if create>
                                                        <tr>
                                                            <td colspan="14" class="text-center">
                                                                На сайте на данный момент нет фильмов
                                                            </td>
                                                        </tr>
                                                    </#if>
                                                    <#if update && !subscribe.films??>
                                                        <tr>
                                                            <td colspan="14" class="text-center">
                                                                На сайте на данный момент нет фильмов
                                                            </td>
                                                        </tr>
                                                    </#if>
                                                </#if>
                                                </tbody>
                                            </table>
                                        </div>
                                        <#if (update || delete)><input type="hidden" name="id" value="${subscribe.id}"></#if>
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
            <div id="polite" aria-live="polite" aria-atomic="true" style="position: fixed; right: 5px; bottom: 5px;"></div>
        </main>
        <script src="/static/js/film/form.js"></script>
        <script src="/static/js/film.js"></script>
        <script src="/static/js/subscribe/periodsTable.js"></script>
        <script src="/static/js/infoCard.js"></script>
    </@templateFtl.default>
</#macro>