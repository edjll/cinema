function formGroupBuilder(title, formElementBuilder = null) {
    const formGroup = document.createElement('div');
    formGroup.classList.add('form-group');

    const formGroupTitle = document.createElement('label');
    formGroupTitle.innerText = title;
    formGroup.appendChild(formGroupTitle);

    if (formElementBuilder) formGroup.appendChild(formElementBuilder);

    return formGroup;
}

function inputBuilder(name, type, value = '') {
    const input = document.createElement('input');
    input.name = name;
    input.type = type;
    input.value = value;
    input.classList.add(`form-control${type == 'file' ? '-file' : ''}`);
    return input;
}

function selectBuilder(name) {
    const select = document.createElement('select');
    select.name = name;
    select.classList.add('form-control');
    return select;
}

function optionBuilder(title, value) {
    const option = document.createElement('option');
    option.innerText = title;
    option.value = value;
    return option;
}

function checkBoxBuilder(title, name) {
    const label = document.createElement('label');

    const checkBox = document.createElement('input');
    checkBox.type = 'checkBox';
    checkBox.name = name;
    label.appendChild(checkBox);

    const span = document.createElement('span');
    span.innerText = title;
    label.appendChild(span);

    return label;
}

function formButtonBuilder(title, action, form) {
    const button = document.createElement('button');
    button.classList.add('btn', 'btn-primary');
    button.innerText = title;
    button.onclick = () => action(form);
    return button;
}