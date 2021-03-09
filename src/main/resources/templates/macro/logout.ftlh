<#macro form>
    <form action="/logout" method="post" class="dropdown-item logout">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn p-0" type="submit">Выйти</button>
    </form>
</#macro>