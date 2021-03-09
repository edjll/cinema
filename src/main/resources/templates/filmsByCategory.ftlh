<#import "macro/template.ftlh" as templateFtl>
<#import "macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
</#assign>

<#assign itemsInRow = 6>

<#if category??>
    <#assign title = category.name>
<#else>
    <#assign title = 'Cinema'>
</#if>


<@templateFtl.default
    links = links
    title = title
>
    <main>
        <div id="films">
            <#if films??>
                <#list films as film>
                    <div class="col-${12 / itemsInRow} film">
                        <@filmFtl.card film></@filmFtl.card>
                    </div>
                </#list>
                <#if films?? && films?size == 11>
                    <div class="col-${12 / itemsInRow} film">
                        <button
                                <#if category??>
                                    onclick="{getFilmsByCategory(this)}"
                                <#else>
                                    onclick="{getDesiredFilms(this)}"
                                </#if>
                                class="btn-more h-100 m-0"
                        >
                            <div class="card more h-100">
                                <span>Смотреть больше</span>
                                <div class="spinner-border text-primary hide" role="status">
                                    <span class="sr-only">loading</span>
                                </div>
                            </div>
                        </button>
                    </div>
                </#if>
            </#if>
        </div>
    </main>
    <script src="/static/js/film.js"></script>
    <script src="/static/js/search.js"></script>
</@templateFtl.default>