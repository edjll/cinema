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
    <main>
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
                            <div class="table-wrapper">
                                <table class="table table-th-block">
                                    <tbody>
                                        <tr><td class="active">Псевдоним</td><td>${user.username}</td></tr>
                                        <tr><td class="active">Почта</td><td>${user.email}</td></tr>
                                        <tr><td class="active">Страна</td><td>${user.country.name}</td></tr>
                                        <tr><td class="active">Дата рождения</td><td>${user.birthday?string('dd.MM.yyyy')}</td></tr>
                                    </tbody>
                                </table>
                            </div>
                            <a href="/profile/update" class="ml-2">Редактировать</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</@templateFtl.default>