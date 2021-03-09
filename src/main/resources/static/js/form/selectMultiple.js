function expandOrCollapseSelect(button) {
    const select = button.parentNode.previousElementSibling.firstElementChild;
    if (select.classList.contains('expand')) {
        select.classList.remove('expand');
        button.innerText = 'Раскрыть';
    } else {
        select.classList.add('expand');
        button.innerText = 'Скрыть';
    }
}