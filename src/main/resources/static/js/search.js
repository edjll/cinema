const filmsWrapper = document.getElementById('films');
const pageSize = 11;

const page = () => Math.floor(document.getElementsByClassName('film').length / pageSize);

function getFilmsByCategory(btn) {
    const request = new XMLHttpRequest();
    request.open("GET", `${window.location.pathname}/${page()}`, true);
    request.onload = () => {
        const films = JSON.parse(request.response);
        films.forEach(film => {
            filmsWrapper.lastElementChild.before(generateFilmCard(film));
        });
        if (films.length < pageSize) {
            btn.parentNode.remove();
        } else {
            btn.firstElementChild.firstElementChild.classList.remove('hide');
            btn.firstElementChild.lastElementChild.classList.add('hide');
        }
    }
    request.send();
}

function getDesiredFilms(btn) {
    changeState(btn, true);

    const request = new XMLHttpRequest();
    request.open("GET", `${window.location.pathname}/${page()}${window.location.search}`, true);
    request.onload = () => {
        const films = JSON.parse(request.response);
        films.forEach(film => filmsWrapper.lastElementChild.before(generateFilmCard(film)));
        if (films.length < pageSize) btn.parentNode.remove();
        else changeState(btn, false);
    }
    request.send();
}

function generateFilmCard(film) {
    const filmWrapper = document.createElement('div');
    filmWrapper.className = document.getElementsByClassName('film')[0].className;
    filmWrapper.appendChild(filmCardBuilder(film));
    
    return filmWrapper;
}

function changeState(btn, state) {
    if (state) {
        btn.disabled = true;
        btn.firstElementChild.firstElementChild.classList.add('hide');
        btn.firstElementChild.lastElementChild.classList.remove('hide');
    } else {
        btn.disabled = false;
        btn.firstElementChild.firstElementChild.classList.remove('hide');
        btn.firstElementChild.lastElementChild.classList.add('hide');
    }
}