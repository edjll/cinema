function changeRole(input) {
    const form = new FormData(input.parentNode);
    const request = new XMLHttpRequest();
    request.open('post', '/admin/user/update', true);
    request.onload = () => {
        const user = JSON.parse(request.response);
        createInfoCard('Изменение роли', `Роль пользователя с id равным ${user.id} теперь ${user.roles.join(', ')}`);
    }
    request.send(form);
}