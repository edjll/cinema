<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/user/user.css">
</#assign>

<@templateFtl.default
links = links
login = false
panel = true
title = 'Профиль'
>
    <main class="justify-content-center">
        <div class="container-custom">
            <div class="wrapper">
                <div class="">
                    <div class="panel">
                        <div class="panel-body">
                            <ul id="myTab" class="nav nav-tabs">
                                <li class="nav-item"><a href="/profile" class="nav-link active">Информация</a></li>
                                <li class="nav-item"><a href="/profile/films" class="nav-link">Фильмы</a></li>
                                <li class="nav-item"><a href="/profile/subscribes" class="nav-link">Подписки</a></li>
                                <li class="nav-item"><a href="/profile/promo" class="nav-link">Промо</a></li>
                            </ul>
                            <form class="w-100" action="/profile/update" method="post">
                                <div class="table-wrapper">
                                    <table class="table table-th-block">
                                        <tbody>
                                            <tr>
                                                <td class="active">Псевдоним</td>
                                                <td>
                                                    <div>
                                                        <input
                                                                type="text"
                                                                class="form-control rounded ${(errors?? && errors['userUsername']??)?then('is-invalid', '')}"
                                                                placeholder="Ваш новый псевдоним"
                                                                name="username"
                                                                value="${user.username}"
                                                        >
                                                        <#if errors?? && errors["userUsername"]??>
                                                            <div class="invalid-feedback">
                                                                ${errors["userUsername"]}
                                                            </div>
                                                        </#if>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="active">Почта</td>
                                                <td>
                                                    <input
                                                            type="email"
                                                            class="form-control rounded ${(errors?? && errors['userEmail']??)?then('is-invalid', '')}"
                                                            placeholder="Ваша новая почта"
                                                            name="email"
                                                            value="${user.email}"
                                                    >
                                                    <#if errors?? && errors["userEmail"]??>
                                                        <div class="invalid-feedback">
                                                            ${errors["userEmail"]}
                                                        </div>
                                                    </#if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="active">Страна</td>
                                                <td>
                                                    <select class="form-control rounded" name="country">
                                                        <#list countries as country>
                                                            <option
                                                                    value="${country.id}"
                                                                    <#if country.id == user.country.id>selected</#if>
                                                            >${country.name}</option>
                                                        </#list>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="active">Дата рождения</td>
                                                <td>
                                                    <div>
                                                        <input
                                                                type="date"
                                                                class="form-control rounded ${(errors?? && errors['userBirthday']??)?then('is-invalid', '')}"
                                                                name="birthday"
                                                                <#if user.birthday??>value="${user.birthday?string('yyyy-MM-dd')}"</#if>
                                                        >
                                                        <#if errors?? && errors["userBirthday"]??>
                                                            <div class="invalid-feedback">
                                                                ${errors["userBirthday"]}
                                                            </div>
                                                        </#if>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="form-group mx-2 my-0">
                                    <button type="submit" class="btn btn-primary" data-original-title="" title="">Сохранить</button>
                                </div>
                                <input type="hidden" name="id" value="${user.id}">
                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</@templateFtl.default>