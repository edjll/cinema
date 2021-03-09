<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
    <link rel="stylesheet" href="/static/css/film.css">
    <link rel="stylesheet" href="/static/css/film/profile.css">
</#assign>

<@templateFtl.default
    links = links
    title = 'Фильм'
>
    <main>
        <div class="video-controls">
            <div class="video-controls-state">
                <button onclick="{playOrPauseVideo()}">
                    <img src="/static/image/icon/play.png" class="video-controls-play">
                    <img src="/static/image/icon/pause.png" class="video-controls-pause active">
                </button>
            </div>
            <div class="video-controls-volume">
                <button onclick="{changeMute()}">
                    <img src="/static/image/icon/volume.png" class="video-controls-unmute">
                    <img src="/static/image/icon/mute.png" class="video-controls-mute active">
                </button>
                <input type="range" name="volume" value="100" min="0" max="100" oninput="{changeVolume(this.value)}">
            </div>

        </div>
        <video class="film-trailer" autoplay id="trailer" preload="auto" loop muted>
            <source src="/trailer/${film.trailer}" type="video/mp4">
        </video>
        <div class="film-info">
            <h5 class="card-title">${film.title}</h5>
            <p class="card-text">${film.description}</p>
            <p class="card-text">${(film.releaseDate??)?then(film.releaseDate?string('dd.MM.yyyy'), 'Скоро')}, ${film.ageLimit}+</p>
            <p>${film.categories?map(category -> category.name)?join(", ")}</p>
            <p id="rating">Рейтинг: <@filmFtl.ratingValue film.rating></@filmFtl.ratingValue></p>
            <div class="rating-buttons">
                <form class="w-auto" onsubmit="{return false;}">
                    <input type="hidden" name="value" value="1">
                    <button onclick="{changeRating(this)}">☆</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="hidden" name="id" value="${film.id}">
                </form>
                <form class="w-auto" onsubmit="{return false;}">
                    <input type="hidden" name="value" value="2">
                    <button onclick="{changeRating(this)}">☆</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="hidden" name="id" value="${film.id}">
                </form>
                <form class="w-auto" onsubmit="{return false;}">
                    <input type="hidden" name="value" value="3">
                    <button onclick="{changeRating(this)}">☆</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="hidden" name="id" value="${film.id}">
                </form>
                <form class="w-auto" onsubmit="{return false;}">
                    <input type="hidden" name="value" value="4">
                    <button onclick="{changeRating(this)}">☆</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="hidden" name="id" value="${film.id}">
                </form>
                <form class="w-auto" onsubmit="{return false;}">
                    <input type="hidden" name="value" value="5">
                    <button onclick="{changeRating(this)}">☆</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="hidden" name="id" value="${film.id}">
                </form>
            </div>
            <#if film.video??>
                <#if access>
                    <a href="/film/${film.id}/watch" class="btn btn-primary">Смотреть</a>
                <#else>
                    <button class="btn btn-primary" onclick="{buyFilm()}">Купить</button>
                    <#if activeSubscribes?? && 0 < activeSubscribes?size>
                        <a href="/film/${film.id}/subscribes" class="btn btn-primary">
                            Подписка
                        </a>
                    </#if>
                </#if>
            <#else>
                <p class="btn btn-primary">Скоро</p>
            </#if>
        </div>
    </main>
    <script src="/static/js/film/buy.js"></script>
    <script src="/static/js/video.js"></script>
    <script src="/static/js/film/rating.js"></script>
</@templateFtl.default>