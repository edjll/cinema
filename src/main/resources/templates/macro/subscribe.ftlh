<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/subcribePeriod.ftlh" as subcribePeriodFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
</#assign>

<#assign itemsInRow = 4>

<@templateFtl.default
links = links
title = 'Купленные фильмы'
>
    <main>
        <style>

            #main {
                background-color: #f2f2f2;
                padding: 20px;
                -webkit-border-radius: 4px;
                -moz-border-radius: 4px;
                -ms-border-radius: 4px;
                -o-border-radius: 4px;
                border-radius: 4px;
                border-bottom: 4px solid #ddd;
            }
            #real-estates-detail #author img {
                -webkit-border-radius: 100%;
                -moz-border-radius: 100%;
                -ms-border-radius: 100%;
                -o-border-radius: 100%;
                border-radius: 100%;
                border: 5px solid #ecf0f1;
                margin-bottom: 10px;
            }
            #real-estates-detail .sosmed-author i.fa {
                width: 30px;
                height: 30px;
                border: 2px solid #bdc3c7;
                color: #bdc3c7;
                padding-top: 6px;
                margin-top: 10px;
            }
            .panel-default .panel-heading {
                background-color: #fff;
            }
            #real-estates-detail .slides li img {
                height: 450px;
            }
        </style>
        <div class="container">
            <div id="main">
                <div class="">
                    <div class="panel">
                        <div class="panel-body">
                            <ul id="myTab" class="nav nav-tabs">
                                <li class="nav-item"><a href="/profile" class="nav-link">Информация</a></li>
                                <li class="nav-item"><a href="/profile/films" class="nav-link">Фильмы</a></li>
                                <li class="nav-item"><a href="/profile/subscribes" class="nav-link active">Подписки</a></li>
                                <li class="nav-item"><a href="/profile/promo" class="nav-link">Промо</a></li>
                            </ul>
                            <div id="myTabContent" class="tab-content">
                                <div id="films">
                                    <#if subscribes??>
                                        <#list subscribes as subscribe>
                                            <div class="col-${12 / itemsInRow} film">
                                                <div class="card">
                                                    <div class="card-body">
                                                        <h5 class="card-title">${subscribe.name}</h5>
                                                    </div>
                                                    <ul class="list-group list-group-flush">
                                                        <li class="list-group-item">
                                                            <div class="progress">
                                                                <div
                                                                        class="progress-bar progress-bar-striped progress-bar-animated"
                                                                        role="progressbar"
                                                                        aria-valuenow="${subscribe.diffDate}"
                                                                        aria-valuemin="0"
                                                                        aria-valuemax="${subscribe.period}"
                                                                        style="width: 75%"
                                                                >
                                                                </div>
                                                            </div>
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
</@templateFtl.default>