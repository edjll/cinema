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
                                    <li class="nav-item"><a href="/admin/categories" class="nav-link">Категории</a></li>
                                    <li class="nav-item"><a href="/admin/subscribes" class="nav-link">Подписки</a></li>
                                    <li class="nav-item"><a href="/admin/users" class="nav-link">Пользователи</a></li>
                                    <li class="nav-item"><a href="/admin/promo" class="nav-link active">Промо</a></li>
                                </ul>
                                <div class="content">
                                    <form
                                            <#if create>action="/admin/promo/create"</#if>
                                            <#if update>action="/admin/promo/update"</#if>
                                            <#if delete>action="/admin/promo/delete"</#if>
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
                                                                    class="form-control ${(errors?? && errors["promoName"]??)?then('is-invalid', '')}"
                                                                    <#if promo?? && promo.name??>value="${promo.name}"</#if>
                                                                    <#if errors?? && errors["promoName"]??>value="${promo.name}"</#if>
                                                                    <#if delete>readonly</#if>
                                                            >
                                                            <#if errors?? && errors["promoName"]??>
                                                                <div class="invalid-feedback">
                                                                    ${errors["promoName"]}
                                                                </div>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title-padding">Активаций*</td>
                                                    <td>
                                                        <div>
                                                            <input
                                                                    type="number"
                                                                    name="activationCount"
                                                                    class="form-control ${(errors?? && errors["promoActivationCount"]??)?then('is-invalid', '')}"
                                                                    <#if promo?? && promo.activationCount??>value="${promo.activationCount}"</#if>
                                                                    <#if delete>readonly</#if>
                                                            >
                                                            <#if errors?? && errors["promoActivationCount"]??>
                                                                <div class="invalid-feedback">
                                                                    ${errors["promoActivationCount"]}
                                                                </div>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title-padding">Дата окончания*</td>
                                                    <td>
                                                        <div>
                                                            <input
                                                                    type="date"
                                                                    name="dateEnd"
                                                                    class="form-control ${(errors?? && errors["promoDateEnd"]??)?then('is-invalid', '')}"
                                                                    <#if promo?? && promo.dateEnd??>value="${promo.dateEnd?string('yyyy-MM-dd')}"</#if>
                                                                    <#if delete>readonly</#if>
                                                            >
                                                            <#if errors?? && errors["promoDateEnd"]??>
                                                                <div class="invalid-feedback">
                                                                    ${errors["promoDateEnd"]}
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
                                                    <th scope="col">Фильмы</th>
                                                    <th scope="col">Период</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <#if errors?? && errors['promoSubscribes']??>
                                                    <tr>
                                                        <td colspan="5" class="text-center error">
                                                            ${errors['promoSubscribes']}
                                                        </td>
                                                    </tr>
                                                </#if>
                                                <#if promo?? && promo.subscribes??>
                                                    <#list promo.subscribes as subscribePeriod>
                                                        <tr>
                                                            <td>
                                                                <input
                                                                        type="checkbox"
                                                                        name="subscribePeriod[]"
                                                                        <#if delete>onclick="{return false;}"</#if>
                                                                        value="${subscribePeriod.id}"
                                                                        checked
                                                                >
                                                            </td>
                                                            <th scope="row">
                                                                <a href="/films/subscribe/${subscribePeriod.subscribe.id}">${subscribePeriod.subscribe.id}</a>
                                                            </th>
                                                            <td>${subscribePeriod.subscribe.name}</td>
                                                            <td>
                                                                <#list subscribePeriod.subscribe.films as film>
                                                                    <#if 0 < film?index>, </#if>
                                                                    <a href="/film/${film.id}">${film.id}</a>
                                                                </#list>
                                                            </td>
                                                            <td>
                                                                <#if delete>
                                                                    <input type="text" class="form-control" readonly value="${subscribePeriod.period}">
                                                                <#else>
                                                                    <select class="form-control" onchange="{changeSubscribePeriod(this)}">
                                                                        <#list subscribePeriod.subscribe.getActiveSubscribePeriods() as period>
                                                                            <option
                                                                                    value="${period.id}"
                                                                                    <#if promo?? && promo.subscribes?? && promo.subscribes?seq_contains(period)>selected</#if>
                                                                            >${period.period}</option>
                                                                        </#list>
                                                                    </select>
                                                                </#if>
                                                            </td>
                                                        </tr>
                                                    </#list>
                                                </#if>
                                                <#if subscribes??>
                                                    <#list subscribes as subscribe>
                                                        <tr>
                                                            <td>
                                                                <input
                                                                        type="checkbox"
                                                                        name="subscribePeriod[]"
                                                                        value="${subscribe.periods?first.id}"
                                                                >
                                                            </td>
                                                            <th scope="row">
                                                                <a href="/films/subscribe/${subscribe.id}">${subscribe.id}</a>
                                                            </th>
                                                            <td>${subscribe.name}</td>
                                                            <td>
                                                                <#list subscribe.films as film>
                                                                    <#if 0 < film?index>, </#if>
                                                                    <a href="/film/${film.id}">${film.id}</a>
                                                                </#list>
                                                            </td>
                                                            <td>
                                                                <select class="form-control" onchange="{changeSubscribePeriod(this)}">
                                                                    <#list subscribe.getActiveSubscribePeriods() as period>
                                                                        <option
                                                                                value="${period.id}"
                                                                                <#if promo?? && promo.subscribes?? && promo.subscribes?seq_contains(period)>selected</#if>
                                                                        >${period.period}</option>
                                                                    </#list>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                    </#list>
                                                <#else>
                                                    <#if !delete>
                                                        <tr>
                                                            <td colspan="5" class="text-center">В данный момент на сайте нет подписок</td>
                                                        </tr>
                                                    </#if>
                                                </#if>
                                                </tbody>
                                            </table>
                                        </div>
                                        <#if update || delete><input type="hidden" name="id" value="${promo.id}"></#if>
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
        <script src="/static/js/promo/subscribe.js"></script>
    </@templateFtl.default>
</#macro>