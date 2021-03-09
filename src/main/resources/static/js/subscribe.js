function changeSubscribe(id) {
    const filmsWrapper = document.getElementById('films');
    filmsWrapper.childNodes.forEach(child => child.remove());

    const films = getFilmsBySubscribe(id, filmsWrapper);
}

function getFilmsBySubscribe(id, wrapper) {
    const request = new XMLHttpRequest();
    request.open("GET", `/films/subscribe_${id}`, true);
    request.onload = () => {
        const films = JSON.parse(request.response);
        if (films.length) {
            wrapper.appendChild(
                createCarousel(
                    'films',
                    'Фильмы',
                    films,
                    filmCardBuilder,
                    filmInputBuilder,
                    6
                )
            );
        }
    }
    request.send();
}

function createCarousel(id, title, list, cardBuilder, inputBuilder = null, itemsInRow = 3) {
    const carousel = document.createElement('div');
    carousel.setAttribute('id', `carousel-${id}`);
    carousel.classList.add('carousel');
    carousel.classList.add('slide');
    carousel.setAttribute('data-ride', 'carousel');
    carousel.setAttribute('data-interval', 'false');
    carousel.setAttribute('data-wrap', 'false');

    const carouselTitle = document.createElement('h4');
    carouselTitle.innerText = title;
    carousel.appendChild(carouselTitle);

    const innerCarousel = document.createElement('div');
    innerCarousel.className = 'carousel-inner';
    carousel.appendChild(innerCarousel);

    for (let i = 0; i < Math.ceil(list.length / itemsInRow); i++) {
        const carouselItem = document.createElement('div');
        carouselItem.classList.add('carousel-item');
        if (i == 0) carouselItem.classList.add('active');

        const row = document.createElement('div');
        row.classList.add('row');

        for (let j = 0; j < itemsInRow && i + j < list.length; j++) {
            const label = document.createElement('label');
            label.classList.add(`col-${12 / itemsInRow}`);
            label.appendChild(cardBuilder(list[i + j]));
            if (inputBuilder) label.appendChild(inputBuilder(list[i + j]));

            row.appendChild(label);
        }
        carouselItem.appendChild(row);
        innerCarousel.appendChild(carouselItem);
    }

    const carouselControlPrev = document.createElement('a');
    carouselControlPrev.classList.add('carousel-control-prev');
    carouselControlPrev.classList.add('hide');
    carouselControlPrev.href = `#${carousel.id}`;
    carouselControlPrev.setAttribute('role', 'button');
    carouselControlPrev.setAttribute('data-slide', 'prev');
    carousel.appendChild(carouselControlPrev);

    const carouselControlPrevIcon = document.createElement('span');
    carouselControlPrevIcon.classList.add('carousel-control-prev-icon');
    carouselControlPrevIcon.setAttribute('aria-hidden', 'true');
    carouselControlPrev.appendChild(carouselControlPrevIcon);

    const carouselControlNext = document.createElement('a');
    carouselControlNext.classList.add('carousel-control-next');
    if (list.length <= itemsInRow) carouselControlNext.classList.add('hide');
    carouselControlNext.href = `#${carousel.id}`;
    carouselControlNext.setAttribute('role', 'button');
    carouselControlNext.setAttribute('data-slide', 'next');
    carousel.appendChild(carouselControlNext);

    const carouselControlNextIcon = document.createElement('span');
    carouselControlNextIcon.classList.add('carousel-control-next-icon');
    carouselControlNextIcon.setAttribute('aria-hidden', 'true');
    carouselControlNext.appendChild(carouselControlNextIcon);

    carousel.addEventListener('slid.bs.carousel', () => hideControl(carousel.id));

    return carousel;
}

function filmInputBuilder(film) {
    const input = document.createElement('input');
    input.type = 'checkbox';
    input.name = 'film_id';
    input.value = film.id;
    input.classList.add('subscribe-input');

    return input;
}