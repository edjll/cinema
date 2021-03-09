<#import "../macro/template.ftlh" as templateFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/login.css">
</#assign>

<@templateFtl.default
    links = links
    title = 'Вход'
>
    <main>
        <div class="background-image">
            <img src="/static/image/home_background.jpg">
            <div class="background-shadow"></div>
        </div>
        <div class="login-wrapper">
            <h1>Вход</h1>
            <#if RequestParameters.error??>
                <div class="alert alert-danger" role="alert">
                    Логин или пароль неверны
                </div>
            </#if>
            <form method="post">
                <div class="form-group">
                    <label>Псевдноим</label>
                    <input type="text" class="form-control" aria-describedby="usernameHelp" name="username">
                </div>
                <div class="form-group">
                    <label>Пароль</label>
                    <input type="password" class="form-control" name="password">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <button type="submit" class="btn btn-primary">Войти</button>
            </form>
        </div>
    </main>
</@templateFtl.default>


