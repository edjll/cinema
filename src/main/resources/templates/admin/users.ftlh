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
                                <li class="nav-item"><a href="/admin/films" class="nav-link">Фильмы</a></li>
                                <li class="nav-item"><a href="/admin/categories" class="nav-link">Категории</a></li>
                                <li class="nav-item"><a href="/admin/subscribes" class="nav-link">Подписки</a></li>
                                <li class="nav-item"><a href="/admin/users" class="nav-link active">Пользователи</a></li>
                                <li class="nav-item"><a href="/admin/promo" class="nav-link">Промо</a></li>
                            </ul>
                            <div class="table-wrapper">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Псевдоним</th>
                                        <th scope="col">Почта</th>
                                        <th scope="col">Дата рождения</th>
                                        <th scope="col">Страна</th>
                                        <th scope="col">Админ</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if 0 < users?size>
                                        <#list users as user>
                                            <tr>
                                                <th scope="row">${user.id}</th>
                                                <td>${user.username}</td>
                                                <td>${user.email}</td>
                                                <td>${user.birthday?string('dd.MM.yyyy')}</td>
                                                <td>${user.country.name}</td>
                                                <td>
                                                    <form class="w-auto">
                                                        <input
                                                                type="checkbox"
                                                                name="admin"
                                                                value="admin"
                                                                <#if user.roles?seq_contains(roleAdmin)>checked</#if>
                                                                onclick="{changeRole(this)}"
                                                        >
                                                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                                                        <input type="hidden" name="id" value="${user.id}">
                                                    </form>
                                                </td>
                                            </tr>
                                        </#list>
                                    <#else>
                                        <tr>
                                            <td colspan="6" class="text-center">
                                                На сайте кроме вас никого нет
                                            </td>
                                        </tr>
                                    </#if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="polite" aria-live="polite" aria-atomic="true" style="position: fixed; right: 5px; bottom: 5px;"></div>
    </main>
    <script src="/static/js/user/role.js"></script>
    <script src="/static/js/infoCard.js"></script>
</@templateFtl.default>