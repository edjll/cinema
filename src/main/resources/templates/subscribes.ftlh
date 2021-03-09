<#import "macro/template.ftlh" as templateFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/admin.css">
    <link rel="stylesheet" href="/static/css/films.css">
</#assign>

<@templateFtl.default
    links = links
    title = 'Панель администратора'
    panel = true
>
    <main>
        <form action="/user/subscribe/buy" method="post">
            <button>Купить</button>
            <input type="hidden" name="id" value="2">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>
    </main>
    <script src="/static/js/builder/form.js"></script>
    <script src="/static/js/builder/page.js"></script>
    <script src="/static/js/admin.js"></script>
</@templateFtl.default>