function buyFilm() {
    const request = new XMLHttpRequest();
    request.open('GET', `${window.location.pathname}/buy`, true);
    request.onload = () => {
        const film = JSON.parse(request.response);
        buyFilmCard(film);
    }
    request.send();
}

function buyFilmCard(film) {
    const cardWrapper = document.createElement('div');
    cardWrapper.classList.add('card-wrapper');

    const button = document.createElement('button');
    button.classList.add('button-close');
    button.innerText = '×';
    button.onclick = () => {
        cardWrapper.remove();
        document.body.classList.remove('overflow-hidden');
    }
    cardWrapper.appendChild(button);

    const card = document.createElement('div');
    card.classList.add('card', 'col-4');
    cardWrapper.appendChild(card);

    const cardBody = document.createElement('div');
    cardBody.classList.add('card-body');
    card.appendChild(cardBody);

    const title = document.createElement('h4');
    title.innerText = film.title;
    cardBody.appendChild(title);

    const cardUl = document.createElement('ul');
    cardUl.classList.add('list-group', 'list-group-flush');
    card.appendChild(cardUl);

    const categories = document.createElement('li');
    categories.classList.add('list-group-item');
    categories.innerText = film.categories.join(', ');
    cardUl.appendChild(categories);

    const price = document.createElement('li');
    price.classList.add('list-group-item');
    price.innerText = film.price + ' рублей';
    cardUl.appendChild(price);

    const cardBodyForm = document.createElement('div');
    cardBodyForm.classList.add('card-body');
    card.appendChild(cardBodyForm);

    const form = document.createElement('form');
    form.method = 'POST';
    form.action = `/film/buy`;
    cardBodyForm.appendChild(form);

    const buttonForm = document.createElement('button');
    buttonForm.classList.add('btn', 'btn-primary');
    buttonForm.innerText = 'Купить';
    form.appendChild(buttonForm);

    const csrf = document.createElement('input');
    csrf.type = 'hidden';
    csrf.name = '_csrf';
    csrf.value = document.querySelector('meta[name="csrf"]').content;
    form.appendChild(csrf);

    const id = document.createElement('input');
    id.type = 'hidden';
    id.name = 'id';
    id.value = film.id;
    form.appendChild(id);

    document.body.classList.add('overflow-hidden');
    document.body.appendChild(cardWrapper);
}