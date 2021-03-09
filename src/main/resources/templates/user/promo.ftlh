<#import "../macro/template.ftlh" as templateFtl>
<#setting locale="en">

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/user/user.css">
    <link rel="stylesheet" href="/static/css/user/promo.css">
</#assign>

<#macro subscribersCount periods>
    <#assign value = 0>
    <#list periods as period>
        <#assign value = value + period.users?size>
    </#list>
    ${value}
</#macro>

<#assign itemsInRow = 4>

<@templateFtl.default
links = links
title = 'Купленные фильмы'
>
    <main>
        <div class="container-custom">
            <div class="wrapper">
                <div class="">
                    <div class="panel">
                        <div class="panel-body">
                            <ul id="myTab" class="nav nav-tabs">
                                <li class="nav-item"><a href="/profile" class="nav-link">Информация</a></li>
                                <li class="nav-item"><a href="/profile/films" class="nav-link">Фильмы</a></li>
                                <li class="nav-item"><a href="/profile/subscribes" class="nav-link">Подписки</a></li>
                                <li class="nav-item"><a href="/profile/promo" class="nav-link active">Промо</a></li>
                            </ul>
                            <div class="table-wrapper">
                                <#if subscribes??>
                                    <#list subscribes as subscribe>
                                        <div class="col-${12 / itemsInRow} film">
                                            <div class="card">
                                                <#if 0 < addedSubscribes?size>
                                                    <#if !addedSubscribes?seq_contains(subscribe)><div class="already-activate">Данная подписка уже была активна, поэтому активация этой подписки не прошла</div></#if>
                                                <#else>
                                                    <div class="already-activate">Данная подписка уже была активна, поэтому активация этой подписки не прошла</div>
                                                </#if>
                                                <div class="card-body">
                                                    <h5 class="card-title"><a href="/films/subscribe/${subscribe.subscribe.id}">${subscribe.getSubscribe().getName()}</a></h5>
                                                </div>
                                                <ul class="list-group list-group-flush">
                                                    <li class="list-group-item">Стоимость: ${subscribe.getPrice()} рублей</li>
                                                    <li class="list-group-item">Период: ${subscribe.getPeriod()} дней</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </#list>
                                <#else>
                                    <form action="/profile/promo" method="post">
                                        <div class="input-group <#if error??>is-invalid</#if>">
                                            <input
                                                    type="text"
                                                    name="name"
                                                    class="form-control <#if error??>is-invalid</#if>"
                                                    placeholder="Введите промо"
                                                    <#if name??>value="${name}"</#if>
                                            >
                                            <div class="input-group-append">
                                                <button class="btn btn-outline-primary">Проверить</button>
                                            </div>
                                        </div>
                                        <#if error??>
                                            <div class="invalid-feedback">
                                                ${error}
                                            </div>
                                        </#if>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                                    </form>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="/static/js/film.js"></script>
    <script src="/static/js/search.js"></script>
    <script src="/static/js/user/subscribe.js"></script>
</@templateFtl.default>