<#macro day period>
    <#if 11 <= period % 100 && period % 100 <= 19>
        дней
    <#else>
        <#if period % 10 == 1>
            день
        <#else>
            <#if 2 <= period % 10 && period % 10 <= 4>дня</#if>
        </#if>
    </#if>
</#macro>

<#macro card
subscribePeriod
>
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">${subscribePeriod.subscribe.name}</h5>
        </div>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">
                <div class="progress">
                    <div
                            class="progress-bar progress-bar-striped progress-bar-animated"
                            role="progressbar"
                            aria-valuenow="${subscribePeriod}"
                            aria-valuemin="0"
                            aria-valuemax="${subscribePeriod.period}"
                            style="width: 75%">

                    </div>
                </div>
            </li>
        </ul>
        <#nested>
    </div>
</#macro>