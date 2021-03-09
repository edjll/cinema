<#import "macro/template.ftlh" as templateFtl>
<#import "macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
</#assign>

<#assign itemsInRow = 6>

<@templateFtl.default
    links = links
    title = 'Фильмы'
>
    <main>
        <#if films??>
            <#list films as category, categoryFilms>
                <div id="carousel-${category.name}" class="carousel slide" data-ride="carousel" data-interval="false" data-wrap="false">
                    <a href="/films/category/${category.id}" class="category">${category.name}</a>
                    <div class="carousel-inner">
                        <#list categoryFilms?chunk(itemsInRow) as rowFilms>
                            <div class="carousel-item ${(rowFilms?index == 0)?then('active', '')}">
                                <div class="row">
                                    <#list rowFilms as film>
                                        <div class="col-${12 / itemsInRow}">
                                            <@filmFtl.card
                                                film = film
                                            ></@filmFtl.card>
                                        </div>
                                        <#if rowFilms?is_last && film?is_last && film?counter < itemsInRow>
                                            <div class="col-${12 / itemsInRow} film-card-link">
                                                <a href="/films/category/${category.id}">
                                                    <div class="card more h-100">
                                                        <span>Смотреть все</span>
                                                    </div>
                                                </a>
                                            </div>
                                            <#assign end = true>
                                        <#else>
                                            <#assign end = false>
                                        </#if>
                                    </#list>
                                </div>
                            </div>
                        </#list>
                        <#if !end>
                            <div class="carousel-item">
                                <div class="row">
                                    <div class="col-${12 / itemsInRow} film-card-link">
                                        <a href="/films/category/${category.id}">
                                            <div class="card more h-100">
                                                <span>Смотреть все</span>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </#if>
                    </div>
                    <a class="carousel-control-prev hide" href="#carousel-${category.name}" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Назад</span>
                    </a>
                    <a class="carousel-control-next hide" href="#carousel-${category.name}" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Вперёд</span>
                    </a>
                </div>
            </#list>
        </#if>
    </main>
    <script src="/static/js/film.js"></script>
</@templateFtl.default>