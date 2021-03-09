function changeFile(input) {
    if (input.value == null) {
        input.nextElementSibling.innerText = 'Выберите файл';
    } else {
        input.nextElementSibling.innerText = input.value.match(/[^\\]*$/)[0];
    }
}