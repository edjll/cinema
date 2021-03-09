<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/subscribe.ftlh" as subscribeFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/addFilmToSubscribe.css">
</#assign>

<#assign itemsInRow = 6>

<@templateFtl.default
    links = links
    title = 'Подписки'
>
    <main>
        <#list subscribes?chunk(itemsInRow) as row>
            <#list row as subscribe>
                <form class="col-${12 / itemsInRow}">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${subscribe.name}</h5>
                        </div>
                        <ul class="list-group list-group-flush">
                            <select name="period">
                                <#list ></#list>
                            </select>
                            <li class="list-group-item"><b>Период:</b> ${subscribe.period} <@day subscribe.period></@day></li>
                            <li class="list-group-item"><b>Стоимость:</b> ${subscribe.price}</li>
                        </ul>
                    </div>
                </form>
            </#list>
        </#list>
    </main>
</@templateFtl.default>