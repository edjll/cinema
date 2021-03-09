<#import "logout.ftlh" as logout>
<#import "admin.ftlh" as admin>
<#include "security.ftlh">

<#macro default
    login
    panel
    error
>
    <#if error>
        <header>
            <div class="header-wrapper py-3 px-5">
                <a class="header-logo" href="/">Cinema</a>
            </div>
        </header>
    <#else>
        <#if isAuthorized>
            <header>
                <div class="header-wrapper py-3 px-5">
                    <a class="header-logo" href="/">Cinema</a>
                    <#if !panel>
                        <form class="form-inline my-2 my-lg-0" action="/films/search">
                            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="searchParam" value="${(RequestParameters.searchParam??)?then(RequestParameters.searchParam, '')}">
                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                        </form>
                    </#if>
                    <div class="dropdown ml-3">
                        <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${name}
                        </button>
                        <div class="dropdown-menu dropdown-menu-right mt-2" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="/profile">Профиль</a>
                            <#if isAdmin>
                                <a class="dropdown-item" href="/admin/films">Панель администратора</a>
                            </#if>
                            <@logout.form></@logout.form>
                        </div>
                    </div>
                </div>
            </header>
        <#else>
            <header>
                <div class="header-wrapper py-3 px-5">
                    <a class="header-logo" href="/">Cinema</a>
                    <#if login>
                        <a href="/registration" class="btn btn-primary">Зарегистрироваться</a>
                    <#else>
                        <a href="/login" class="btn btn-primary">Войти</a>
                    </#if>
                </div>
            </header>
        </#if>
    </#if>
</#macro>