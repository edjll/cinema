function changePeriod(select) {
    const request = new XMLHttpRequest();
    request.open('get', `/subscribe/period/${select.value}`, true);
    request.onload = () => {
        const subscribePeriod = JSON.parse(request.response);
        console.log(subscribePeriod);
        select.parentNode.nextElementSibling.innerText = `Стоимость: ${subscribePeriod.price} рублей`;
    }
    request.send();
}

function changeRenewal(button) {
    const form = new FormData(button.form);
    const request = new XMLHttpRequest();
    request.open('post', `/profile/subscribe/renewal`, true);
    request.onload = () => {
        const result = JSON.parse(request.response);
        if (result.renewal) {
            button.innerText = 'Отписаться';
        } else {
            button.innerText = 'Подписаться';
        }
        button.nextElementSibling.value = result.renewal;
    }
    request.send(form);
}