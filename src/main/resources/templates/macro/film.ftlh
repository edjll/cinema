<#include "security.ftlh">

<#macro ratingValue rating>
    <#if rating?size == 0>
        нет голосов
    <#else>
        <#assign value = 0>
        <#list rating as r>
            <#assign value = value + r.value>
        </#list>
        <#assign value = value / rating?size>
        ${value}
    </#if>
</#macro>

<#macro card film>
    <div class="card">
        <#if film.preview??><img src="/preview/${film.preview}" class="card-img-top" alt="${film.getTitle()}"></#if>
        <div class="card-body">
            <h5 class="card-title">${film.title}</h5>
            <p class="card-text text-truncate">${film.description}</p>
        </div>
        <ul class="list-group list-group-flush">
            <#if film.access.type.getTitle() == 'Бесплатно'>
                <li class="list-group-item">${film.access.type.getTitle()}</li>
            <#else>
                <li class="list-group-item">${film.access.type.getTitle()}${film.hasActiveSubscribes()?then(', Подписка', '')}</li>
            </#if>
            <li class="list-group-item">${(film.releaseDate??)?then(film.releaseDate?string('dd.MM.yyyy'), 'Скоро')}, ${film.ageLimit}+</li>
            <li class="list-group-item">Рейтинг: <@ratingValue film.rating></@ratingValue></li>
        </ul>
        <div class="card-body d-flex justify-content-between align-items-center">
            <a href="/film/${film.id}" class="card-link">Подробнее</a>
        </div>
    </div>
</#macro>