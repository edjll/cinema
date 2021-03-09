function buttonCloseBuilder(closingNode) {
    const button = document.createElement('button');
    button.classList.add('button-close');
    button.onclick = () => {
        closingNode.remove();
        document.body.classList.remove('overflow-hidden');
    }
    return button;
}

function filmCreatePageBuilder(categories, accessTypes, action) {
    const formWrapper = document.createElement('div');
    formWrapper.classList.add('form-wrapper');

    formWrapper.appendChild(buttonCloseBuilder(formWrapper));

    const form = document.createElement('form');
    form.onsubmit = () => false;

    form.appendChild(formGroupBuilder('Название', inputBuilder('title', 'text')));
    form.appendChild(formGroupBuilder('Описание', inputBuilder('description', 'text')));
    form.appendChild(formGroupBuilder('Дата выхода', inputBuilder('releaseDate', 'date')));
    form.appendChild(formGroupBuilder('Возрастное ограничение', inputBuilder('ageLimit', 'number')));
    form.appendChild(formGroupBuilder('Превью', inputBuilder('preview', 'file')));
    form.appendChild(formGroupBuilder('Трейлер', inputBuilder('trailer', 'file')));
    form.appendChild(formGroupBuilder('Видео', inputBuilder('video', 'file')));

    const access = formGroupBuilder('Доступ', selectBuilder('accessType'));
    for (let key in accessTypes) access.appendChild(optionBuilder(accessTypes[key], key));
    form.appendChild(access);

    form.appendChild(formGroupBuilder('Стоимость', inputBuilder('price', 'number')));

    const categoriesFormGroup = formGroupBuilder('Категории');
    categories.forEach(category => categoriesFormGroup.appendChild(checkBoxBuilder(category.name, category.name)));
    form.appendChild(categoriesFormGroup);

    form.appendChild(formButtonBuilder('Создать', action, form));

    formWrapper.appendChild(form);

    return formWrapper;
}