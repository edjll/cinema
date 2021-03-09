<#import "macro/template.ftlh" as templateFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
</#assign>

<@templateFtl.default
    links = links
    title = 'Панель администратора'
    panel = true
>
    <main>
        <div class="col-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Фильмы</h5>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><a class="card-link" href="/admin/film/create">Создать</a></li>
                    <li class="list-group-item"><a class="card-link" href="/admin/films">Изменить</a></li>
                </ul>
            </div>
        </div>
        <div class="col-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Категории</h5>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><a class="card-link" href="/admin/category/create">Создать</a></li>
                    <li class="list-group-item"><a class="card-link" href="/admin/categories">Изменить</a></li>
                </ul>
            </div>
        </div>
        <div class="col-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Подписки</h5>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><a class="card-link" href="/admin/subscribe/create">Создать</a></li>
                    <li class="list-group-item"><a class="card-link" href="/admin/subscribes">Изменить</a></li>
                </ul>
            </div>
        </div>
        <div class="col-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Пользователи</h5>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><a class="card-link" href="/admin/users">Изменить</a></li>
                </ul>
            </div>
        </div>
        <div class="col-2">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Промокоды</h5>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><a class="card-link" href="/admin/promo/create">Создать</a></li>
                    <li class="list-group-item"><a class="card-link">Изменить</a></li>
                </ul>
            </div>
        </div>
    </main>
    <script src="/static/js/builder/form.js"></script>
    <script src="/static/js/builder/page.js"></script>
    <script src="/static/js/admin.js"></script>
</@templateFtl.default>