function changeAccess(select) {
    if (select.value == 'FREE') {
        select.parentNode.parentNode.parentElement.nextElementSibling.classList.add('hide');
    } else {
        select.parentNode.parentNode.parentElement.nextElementSibling.classList.remove('hide');
    }
}

changeAccess(document.getElementsByName('accessType')[0]);

function changeAgeLimit(input) {
    if (input.value.length > 1 && input.value[0] == 0) input.value = input.value.slice(1);
    if (!input.value.length) input.value = 0;
}