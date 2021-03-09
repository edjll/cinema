function getFilmCreatePage() {
    let categories, accessTypes;
    getInfoForFunctionalPage('/category/all').then(
        response => {
            categories = JSON.parse(response);
            console.log(categories);
            return getInfoForFunctionalPage('/accessType/all');
        }
    ).then(
        response => {
            accessTypes = JSON.parse(response);
            console.log(accessTypes);
            createFunctionalPage(filmCreatePageBuilder(categories, accessTypes, createFilm));
        }
    )
}

function getInfoForFunctionalPage(url) {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest();
        request.open("GET", url, true);
        request.onload = () => resolve(request.response);
        request.send();
    });
}

function createFilm(form) {
    const formData = new FormData(form);
    const request = new XMLHttpRequest();
    request.open("POST", `/film/create`, true);
    request.onload = () => {
        const res = JSON.parse(request.response);
        if (res.error) formErrorHandler(res.error, 'createFilm');
        console.log(res);
    }
    request.send(formData);
}

function createFunctionalPage(pageBuilder) {
    document.body.appendChild(pageBuilder);
    document.body.classList.add('overflow-hidden');
}

function formErrorHandler(errors, methodName) {
    for (let key in errors) {
        const selectorName = key.replace(methodName + '.', '');
        const errorValue = errors[key];
        document.getElementsByName(selectorName)[0].classList.add('is-invalid');
    }
}