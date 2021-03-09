function changeRating(button) {
    const form = new FormData(button.parentNode);
    const request = new XMLHttpRequest();
    request.open('POST', `${window.location.pathname}/rating/update`, true);
    request.onload = () => {
        const rating = JSON.parse(request.response);
        console.log(rating);
        changeAverageRating(rating.filmRating);
    }
    request.send(form);
}

function changeAverageRating(rating) {
    document.getElementById('rating').innerText = `Рейтинг: ${rating}`;
}