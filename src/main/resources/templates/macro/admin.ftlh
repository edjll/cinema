<#macro menu>
    <div class="btn-group dropleft">
        <button class="btn dropdown-toggle" type="button" id="dropdownAdminPanel" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Панель администратора
        </button>
        <div class="dropdown-menu dropdown-menu-right m-0" aria-labelledby="dropdownAdminPanel">
            <div class="btn-group dropleft">
                <button class="btn dropdown-toggle" type="button" id="dropdownFilm" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Фильм
                </button>
                <div class="dropdown-menu dropdown-menu-right m-0" aria-labelledby="dropdownFilm">
                    <a class="dropdown-item" href="/film/create">Создать</a>
                    <a class="dropdown-item" href="/film/update">Изменить</a>
                    <a class="dropdown-item" href="/film/delete">Удалить</a>
                </div>
            </div>
            <div class="btn-group dropleft">
                <button class="btn dropdown-toggle" type="button" id="dropdownCategory" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Категория
                </button>
                <div class="dropdown-menu dropdown-menu-right m-0" aria-labelledby="dropdownAdminPanel">
                    <a class="dropdown-item" href="/category/create">Создать</a>
                    <a class="dropdown-item" href="/category/update">Изменить</a>
                    <a class="dropdown-item" href="/category/delete">Удалить</a>
                </div>
            </div>
            <div class="btn-group dropleft">
                <button class="btn dropdown-toggle" type="button" id="dropdownSubscribe" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Подписка
                </button>
                <div class="dropdown-menu dropdown-menu-right m-0" aria-labelledby="dropdownAdminPanel">
                    <a class="dropdown-item" href="/subscribe/create">Создать</a>
                    <a class="dropdown-item" href="/subscribe/update">Изменить</a>
                    <a class="dropdown-item" href="/subscribe/delete">Удалить</a>
                    <div class="btn-group dropleft">
                        <button class="btn dropdown-toggle" type="button" id="dropdownSubscribeFilms" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Фильмы
                        </button>
                        <div class="dropdown-menu dropdown-menu-right m-0" aria-labelledby="dropdownAdminPanel">
                            <a class="dropdown-item" href="/subscribe/film/add">Добавить</a>
                            <a class="dropdown-item" href="/subscribe/film/remove">Удалить</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>