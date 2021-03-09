function deleteRowInTable(button) {
    const periods = document.getElementsByName('period[]');
    if (periods.length == 1) {
        createInfoCard(
            'Удаление строки в таблице периодов',
            'Невозможно удалить единственную строку таблицы периодов, так как подписка должна иметь минимум один период'
        );
    } else {
        button.parentNode.parentNode.remove();
        periods.forEach((period, index) => {
            period.parentNode.previousElementSibling.innerText = index + 1;
        });
    }
}

function addRowInTable(button, page = false) {
    const periods = document.getElementsByName('period[]');
    button.parentNode.parentNode.before(createRow(periods.length, page));
}

function createRow(index, page) {
    const tr = document.createElement('tr');

    const tdButton = document.createElement('td');
    const button = document.createElement('p');
    button.innerText = '-';
    button.classList.add('m-0', 'delete');
    button.onclick = () => deleteRowInTable(button);
    tdButton.appendChild(button);

    if (page) {
        const periodId = document.createElement('input');
        periodId.type = 'hidden';
        periodId.name = 'periodId[]';
        tdButton.appendChild(periodId);
    }

    const th = document.createElement('th');
    th.innerText = index + 1;
    th.scope = 'row';

    const tdPeriod = document.createElement('td');
    const period = document.createElement('input');
    period.type = 'number';
    period.name = 'period[]';
    period.classList.add('form-control');
    tdPeriod.appendChild(period);

    const tdPrice = document.createElement('td');
    const price = document.createElement('input');
    price.type = 'number';
    price.name = 'price[]';
    price.classList.add('form-control');
    tdPrice.appendChild(price);

    tr.appendChild(tdButton);
    tr.appendChild(th);
    tr.appendChild(tdPeriod);
    tr.appendChild(tdPrice);

    if (page) {
        const td1 = document.createElement('td');
        const td2 = document.createElement('td');
        tr.appendChild(td1);
        tr.appendChild(td2);
    }

    return tr;
}