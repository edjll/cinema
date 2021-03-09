<#import "../macro/template.ftlh" as templateFtl>
<#setting locale="en">

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
    <link rel="stylesheet" href="/static/css/user/user.css">
    <link rel="stylesheet" href="/static/css/user/subscribes.css">
</#assign>

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
                                <li class="nav-item"><a href="/profile/subscribes" class="nav-link active">Подписки</a></li>
                                <li class="nav-item"><a href="/profile/promo" class="nav-link">Промо</a></li>
                            </ul>
                            <div class="table-wrapper">
                                <div id="films">
                                    <#if boughtSubscribes??>
                                        <#list boughtSubscribes as subscribe>
                                            <div class="col-${12 / itemsInRow} film">
                                                <div class="card">
                                                    <div class="card-body">
                                                        <h5 class="card-title"><a href="/films/subscribe/${subscribe.subscribePeriod.subscribe.id}">${subscribe.getSubscribePeriod().getSubscribe().getName()}</a></h5>
                                                    </div>
                                                    <ul class="list-group list-group-flush">
                                                        <li class="list-group-item">Стоимость: ${subscribe.getPrice()} рублей</li>
                                                        <li class="list-group-item">Период: ${subscribe.getPeriod()} дней</li>
                                                        <li class="list-group-item">
                                                            <div>
                                                                <div class="progress">
                                                                    <#assign dateToEnd = subscribe.getDateToEnd() / 1000 / 60 / 60 / 24>
                                                                    <div
                                                                            class="progress-bar progress-bar-striped progress-bar-animated"
                                                                            role="progressbar"
                                                                            aria-valuenow="${subscribe.getDateToEnd() / 1000 / 60 / 60 / 24}"
                                                                            aria-valuemin="0"
                                                                            aria-valuemax="${subscribe.getPeriod()}"
                                                                            style="width: ${(dateToEnd * 100 / subscribe.getPeriod())?string("00.###")}%"
                                                                    >
                                                                    </div>
                                                                    <span class="date-to-end">${dateToEnd?floor} дней</span>
                                                                </div>
                                                                <div class="date-wrapper">
                                                                    <div class="date">
                                                                        <span>${subscribe.getDateStart()?string("dd.MM.yyyy")}</span>
                                                                        <span>${subscribe.getDateStart()?string("HH:mm")}</span>
                                                                    </div>
                                                                    <div class="date align-items-end">
                                                                        <span>${subscribe.getDateEnd()?string("dd.MM.yyyy")}</span>
                                                                        <span>${subscribe.getDateEnd()?string("HH:mm")}</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </li>
                                                        <#if !subscribe.subscribePeriod.active>
                                                            <li class="list-group-item"><small>*Данный тариф больше не будет поддерживаться. После истечения тарифа, данную подписку нельзя будет продлить по данному тарифу.</small></li>
                                                        <#else>
                                                            <#if subscribe.period != subscribe.subscribePeriod.period><li class="list-group-item"><small>*Период данного тарифа изменился. Со следующего платежа период будет: ${subscribe.subscribePeriod.period}</small></li></#if>
                                                            <#if subscribe.price != subscribe.subscribePeriod.price><li class="list-group-item"><small>*Цена данного тарифа изменилась. Следующий платёж составит: ${subscribe.subscribePeriod.price}</small></li></#if>
                                                        </#if>
                                                        <#if subscribe.subscribePeriod.active>
                                                            <li class="list-group-item">
                                                                <form onsubmit="{return false;}">
                                                                    <button class="btn btn-primary" onclick="{changeRenewal(this)}">
                                                                        ${subscribe.renewal?then('Отписаться', 'Подписаться')}
                                                                    </button>
                                                                    <input type="hidden" name="renewal" value="${subscribe.renewal?c}">
                                                                    <input type="hidden" name="userSubscribeId" value="${subscribe.id}">
                                                                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                                                                </form>
                                                            </li>
                                                        </#if>
                                                    </ul>
                                                </div>
                                            </div>
                                        </#list>
                                    </#if>
                                    <#if subscribes??>
                                        <#list subscribes as subscribe>
                                            <div class="col-${12 / itemsInRow} film">
                                                <div class="card">
                                                    <div class="card-body">
                                                        <h5 class="card-title">${subscribe.getName()}</h5>
                                                    </div>
                                                    <ul class="list-group list-group-flush">
                                                        <li class="list-group-item">
                                                            Период:
                                                            <select onchange="{changePeriod(this)}">
                                                                <#list subscribe.getActiveSubscribePeriods() as subscribePeriod>
                                                                    <option
                                                                            name="period"
                                                                            value="${subscribePeriod.id}"
                                                                    >${subscribePeriod.getPeriod()}</option>
                                                                </#list>
                                                            </select> дней
                                                        </li>
                                                        <li class="list-group-item">Стоимость: ${subscribe.getPeriods()?first.getPrice()} рублей</li>
                                                        <li class="list-group-item">
                                                            <form action="/subscribe/buy" method="post">
                                                                <input type="hidden" name="subscribePeriodId" value="${subscribe.getPeriods()?first.getId()}">
                                                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                                                <button class="btn btn-primary">Приобрести</button>
                                                            </form>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </#list>
                                    </#if>
                                </div>
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