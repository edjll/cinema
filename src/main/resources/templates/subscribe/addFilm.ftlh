<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/subscribe.ftlh" as subscribeFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
    <link rel="stylesheet" href="/static/css/addFilmToSubscribe.css">
</#assign>

<#assign itemsInRow = 6>

<@templateFtl.default
    links = links
    login = false
    panel = true
>
    <main>
        <div id="carousel-subscribes" class="carousel slide" data-ride="carousel" data-interval="false" data-wrap="false">
            <h4>Подписки</h4>
            <div class="carousel-inner">
                <#list subscribes?chunk(itemsInRow) as row>
                    <div class="carousel-item ${(row?index == 0)?then('active', '')}">
                        <div class="row">
                            <#list row as subscribe>
                                <label class="col-${12 / itemsInRow}">
                                    <@subscribeFtl.card
                                        subscribe
                                    ></@subscribeFtl.card>
                                    <input
                                            type="radio"
                                            name="subscribe_id"
                                            value="${subscribe.id}"
                                            class="subscribe-input"
                                            <#if row?index == 0 && subscribe?index == 0>checked</#if>
                                            onchange="{changeSubscribe(this.value)}"
                                    >
                                </label>
                            </#list>
                        </div>
                    </div>
                </#list>
            </div>
            <a class="carousel-control-prev" href="#carousel-subscribes" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Назад</span>
            </a>
            <a class="carousel-control-next" href="#carousel-subscribes" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Вперёд</span>
            </a>
        </div>
        <div id="films">
            <div id="carousel-films" class="carousel slide" data-ride="carousel" data-interval="false" data-wrap="false">
                <h4>Фильмы</h4>
                <div class="carousel-inner">
                    <#list films?chunk(itemsInRow) as row>
                        <div class="carousel-item ${(row?index == 0)?then('active', '')}">
                            <div class="row">
                                <#list row as film>
                                    <label class="col-${12 / itemsInRow}">
                                        <@filmFtl.card
                                            film
                                        ></@filmFtl.card>
                                        <input
                                                type="checkbox"
                                                name="film_id"
                                                value="${film.id}"
                                                class="subscribe-input"
                                        >
                                    </label>
                                </#list>
                            </div>
                        </div>
                    </#list>
                </div>
                <a class="carousel-control-prev" href="#carousel-films" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Назад</span>
                </a>
                <a class="carousel-control-next" href="#carousel-films" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Вперёд</span>
                </a>
            </div>
        </div>
    </main>
    <script src="/static/js/subscribe.js"></script>
</@templateFtl.default>