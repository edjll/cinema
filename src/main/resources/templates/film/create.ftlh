<#import "../macro/template.ftlh" as templateFtl>
<#import "../macro/film.ftlh" as filmFtl>

<#assign links>
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/css/films.css">
    <link rel="stylesheet" href="/static/css/admin/admin.css">
    <link rel="stylesheet" href="/static/css/admin/films.css">
    <link rel="stylesheet" href="/static/css/admin/form.css">
</#assign>

<@templateFtl.default
    links = links
    login = false
    panel = true
    title = 'Панель администратора - создание фильма'
>
    <main>
        <div class="container-custom">
            <div class="wrapper">
                <div class="">
                    <div class="panel">
                        <div class="panel-body">
                            <ul class="nav nav-tabs">
                                <li class="nav-item"><a href="/admin/films" class="nav-link active">Фильмы</a></li>
                                <li class="nav-item"><a href="/admin/categories" class="nav-link">Категории</a></li>
                                <li class="nav-item"><a href="/admin/subscribes" class="nav-link">Подписки</a></li>
                                <li class="nav-item"><a href="/admin/users" class="nav-link">Пользователи</a></li>
                                <li class="nav-item"><a href="/admin/promo" class="nav-link">Промо</a></li>
                            </ul>
                            <div class="content">
                                <form action="/admin/film/create" method="post" enctype="multipart/form-data">
                                    <div class="table-wrapper">
                                        <table class="table table-th-block">
                                            <tbody>
                                                <tr>
                                                    <td>Название*</td>
                                                    <td>
                                                        <div>
                                                            <input type="text"
                                                                   name="title"
                                                                   class="form-control <#if errors??>${(errors["filmTitle"]??)?then('is-invalid', 'is-valid')}</#if>"
                                                                   <#if film??>value="${film.title}"</#if>
                                                            >
                                                            <#if errors?? && errors["filmTitle"]??>
                                                                <div class="invalid-feedback">
                                                                    ${errors["filmTitle"]}
                                                                </div>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Описание*</td>
                                                    <td>
                                                        <div>
                                                            <input type="text"
                                                                   name="description"
                                                                   class="form-control <#if errors??>${(errors["filmDescription"]??)?then('is-invalid', 'is-valid')}</#if>"
                                                                   <#if film??>value="${film.description}"</#if>
                                                            >
                                                            <#if errors?? && errors["filmDescription"]??>
                                                                <div class="invalid-feedback">
                                                                    ${errors["filmDescription"]}
                                                                </div>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Дата выхода</td>
                                                    <td>
                                                        <div>
                                                            <input type="date"
                                                                   name="releaseDate"
                                                                   class="form-control"
                                                            >
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Возрастное ограничение</td>
                                                    <td>
                                                        <div>
                                                            <input type="number"
                                                                   min="0"
                                                                   name="ageLimit"
                                                                   class="form-control"
                                                                   oninput="{changeAgeLimit(this)}"
                                                                   value="0"
                                                            >
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Доступ*</td>
                                                    <td>
                                                        <div>
                                                            <select class="form-control" name="accessType" onchange="{changeAccess(this)}">
                                                                <#list accessTypes as accessType>
                                                                    <option value="${accessType}">${accessType.getTitle()}</option>
                                                                </#list>
                                                            </select>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Стоимость</td>
                                                    <td>
                                                        <div>
                                                            <input type="text"
                                                                   name="price"
                                                                   class="form-control"
                                                                   value="0"
                                                            >
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Превью*</td>
                                                    <td>
                                                        <div class="custom-file">
                                                            <input type="file"
                                                                   name="previewFile"
                                                                   class="custom-file-input <#if errors??>${(errors["filmPreview"]??)?then('is-invalid', 'is-valid')}</#if>"
                                                                   id="preview"
                                                                   onchange="{changeFile(this)}"
                                                            >
                                                            <label
                                                                    class="custom-file-label"
                                                                    for="preview"
                                                                    data-browse="Выбрать"
                                                            >
                                                                Выберите файл
                                                            </label>
                                                            <#if errors?? && errors["filmPreview"]??>
                                                                <div class="invalid-feedback">
                                                                    ${errors["filmPreview"]}
                                                                </div>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Трейлер*</td>
                                                    <td>
                                                        <div class="custom-file">
                                                            <input type="file"
                                                                   name="trailerFile"
                                                                   class="custom-file-input <#if errors??>${(errors["filmTrailer"]??)?then('is-invalid', 'is-valid')}</#if>"
                                                                   id="trailer"
                                                                   onchange="{changeFile(this)}"
                                                            >
                                                            <label
                                                                    class="custom-file-label"
                                                                    for="trailer"
                                                                    data-browse="Выбрать"
                                                            >
                                                                Выберите файл
                                                            </label>
                                                            <#if errors?? && errors["filmTrailer"]??>
                                                                <div class="invalid-feedback">
                                                                    ${errors["filmTrailer"]}
                                                                </div>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Видео</td>
                                                    <td>
                                                        <div class="custom-file">
                                                            <input type="file"
                                                                   name="videoFile"
                                                                   class="custom-file-input <#if errors??>${(errors["filmVideo"]??)?then('is-invalid', '')}</#if>"
                                                                   id="video"
                                                                   onchange="{changeFile(this)}"
                                                            >
                                                            <label
                                                                    class="custom-file-label"
                                                                    for="video"
                                                                    data-browse="Выбрать"
                                                            >
                                                                Выберите файл
                                                            </label>
                                                            <#if errors?? && errors["filmVideo"]??>
                                                                <div class="invalid-feedback">
                                                                    ${errors["filmVideo"]}
                                                                </div>
                                                            </#if>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Категории*</td>
                                                    <td>
                                                        <div class="input-group input-group-custom-select <#if errors??>${(errors["filmCategories"]??)?then('is-invalid', 'is-valid')}</#if>">
                                                            <div class="select-multiple-wrapper">
                                                                <select class="custom-select custom-select-multiple" name="category[]" multiple>
                                                                    <#list categories as category>
                                                                        <option
                                                                                value="${category.id}"
                                                                                <#if checkedCategories?? && checkedCategories?seq_contains(category)>selected</#if>
                                                                        >${category.name}</option>
                                                                    </#list>
                                                                </select>
                                                            </div>
                                                            <div class="input-group-append">
                                                                <span class="btn btn-outline-primary select-multiple-button" onclick="{expandOrCollapseSelect(this)}">Раскрыть</span>
                                                            </div>
                                                        </div>
                                                        <#if errors?? && errors["filmCategories"]??>
                                                            <div class="invalid-feedback">
                                                                ${errors["filmCategories"]}
                                                            </div>
                                                        </#if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Подписки</td>
                                                    <td>
                                                        <div class="input-group input-group-custom-select">
                                                            <div class="select-multiple-wrapper">
                                                                <select class="custom-select custom-select-multiple" name="subscribe[]" multiple>
                                                                    <#list subscribes as subscribe>
                                                                        <option
                                                                                value="${subscribe.id}"
                                                                                <#if checkedSubscribes?? && checkedSubscribes?seq_contains(subscribe)>selected</#if>
                                                                        >${subscribe.name}</option>
                                                                    </#list>
                                                                </select>
                                                            </div>
                                                            <div class="input-group-append">
                                                                <span class="btn btn-outline-primary select-multiple-button" onclick="{expandOrCollapseSelect(this)}">Раскрыть</span>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                                    <button class="btn btn-primary">Создать</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="/static/js/film/form.js"></script>
    <script src="/static/js/film.js"></script>
    <script src="/static/js/form/selectMultiple.js"></script>
    <script src="/static/js/form/file.js"></script>
</@templateFtl.default>