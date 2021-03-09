<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
</#assign>

<#assign itemsInRow = 4>

<@templateFtl.default
    links = links
    login = false
    panel = false
>
    <main>
        <#list filmSubscribes as subscribe>
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
                        <li class="list-group-item">Стоимость: ${subscribe.getActiveSubscribePeriods()?first.getPrice()} рублей</li>
                        <li class="list-group-item">
                            <form action="/subscribe/buy" method="post">
                                <input type="hidden" name="subscribePeriodId" value="${subscribe.getActiveSubscribePeriods()?first.getId()}">
                                <input type="hidden" name="_csrf" value="${_csrf.token}">
                                <button class="btn btn-primary">Приобрести</button>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
        </#list>
    </main>
    <script src="/static/js/user/subscribe.js"></script>
</@templateFtl.default>