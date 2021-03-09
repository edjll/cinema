function filmCardBuilder(film) {
    const card = document.createElement('div');
    card.classList.add('card');

    const image = document.createElement('img');
    image.src = `/preview/${film.preview}`;
    image.classList.add('card-img-top');
    image.alt = film.title;
    card.appendChild(image);

    const firstCardBody = document.createElement('div');
    firstCardBody.classList.add('card-body');
    card.appendChild(firstCardBody);

    const filmTitle = document.createElement('h5');
    filmTitle.classList.add('card-title');
    filmTitle.innerText = film.title;
    firstCardBody.appendChild(filmTitle);

    const filmDescription = document.createElement('p');
    filmDescription.classList.add('card-text');
    filmDescription.classList.add('text-truncate');
    filmDescription.innerText = film.description;
    firstCardBody.appendChild(filmDescription);

    const ul = document.createElement('ul');
    ul.classList.add('list-group');
    ul.classList.add('list-group-flush');
    card.appendChild(ul);

    const access = document.createElement('li');
    access.classList.add('list-group-item');
    access.innerText = film.access;
    ul.appendChild(access);

    const dateValue = (film.date == null) ? 'Скоро' : film.date.match(/[^-T]*/g)[4] + '.' + film.date.match(/[^-T]*/g)[2] + '.' + film.date.match(/[^-T]*/g)[0];

    const date = document.createElement('li');
    date.classList.add('list-group-item');
    date.innerText = dateValue + ', ' + film.ageLimit + '+';
    ul.appendChild(date);

    const rating = document.createElement('li');
    rating.classList.add('list-group-item');
    rating.innerText = 'Рейтинг: ' + film.rating;
    ul.appendChild(rating);

    const secondCardBody = document.createElement('div');
    secondCardBody.classList.add('card-body');
    card.appendChild(secondCardBody);

    const link = document.createElement('a');
    link.href = `/film/${film.id}`;
    link.classList.add('card-link');
    link.innerText = 'Подробнее';
    secondCardBody.appendChild(link);

    return card;
}