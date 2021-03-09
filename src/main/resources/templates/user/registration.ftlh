<#import "../macro/template.ftlh" as templateFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/login.css">
</#assign>

<@templateFtl.default
    links = links
    title = 'Вход'
    login = false
>
    <main>
        <div class="background-image">
            <img src="/static/image/home_background.jpg">
            <div class="background-shadow"></div>
        </div>
        <div class="login-wrapper">
            <h1>Регистрация</h1>
            <form method="post" class="d-flex flex-column">
                <div class="form-group">
                    <label>Псевдоним</label>
                    <input type="text" class="form-control <#if user??>${(errors?? && errors["userUsername"]??)?then('is-invalid', 'is-valid')}</#if>"
                           name="username"
                           <#if user?? && user.username??>value="${user.username}"</#if>
                    >
                    <#if errors?? && errors["userUsername"]??>
                        <div class="invalid-feedback">
                            ${errors["userUsername"]}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <label>Электронная почта</label>
                    <input type="email" class="form-control <#if user??>${(errors?? && errors["userEmail"]??)?then('is-invalid', 'is-valid')}</#if>"
                           name="email"
                           <#if user?? && user.email??>value="${user.email}"</#if>
                    >
                    <#if errors?? && errors["userEmail"]??>
                        <div class="invalid-feedback">
                            ${errors["userEmail"]}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <label>Пароль</label>
                    <input type="password" class="form-control <#if user??>${(errors?? && errors["userPassword"]??)?then('is-invalid', 'is-valid')}</#if>"
                           name="password"
                           <#if user?? && user.password??>value="${user.password}"</#if>
                    >
                    <#if errors?? && errors["userPassword"]??>
                        <div class="invalid-feedback">
                            ${errors["userPassword"]}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <label>Повторите пароль</label>
                    <input type="password" class="form-control <#if user??>${(errors?? && errors["userRepeatPassword"]??)?then('is-invalid', 'is-valid')}</#if>"
                           name="repeatPassword"
                           <#if user?? && user.repeatPassword??>value="${user.repeatPassword}"</#if>
                    >
                    <#if errors?? && errors["userRepeatPassword"]??>
                        <div class="invalid-feedback">
                            ${errors["userRepeatPassword"]}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <label>День рождения</label>
                    <input type="date" class="form-control <#if user??>${(errors?? && errors["userBirthday"]??)?then('is-invalid', 'is-valid')}</#if>"
                           name="birthday"
                           <#if user?? && user.birthday??>value="${user.birthday?string('yyyy-MM-dd')}"</#if>
                    >
                    <#if errors?? && errors["userBirthday"]??>
                        <div class="invalid-feedback">
                            ${errors["userBirthday"]}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <label>Страна</label>
                    <select class="form-control" name="country">
                        <#if countries??>
                            <#list countries as country>
                                <option value="${country.getId()}">${country.getName()}</option>
                            </#list>
                        </#if>
                    </select>
                    <#if errors?? && errors["userCountry"]??>
                        <div class="invalid-feedback">
                            ${errors["userCountry"]}
                        </div>
                    </#if>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
            </form>
        </div>
    </main>
</@templateFtl.default>