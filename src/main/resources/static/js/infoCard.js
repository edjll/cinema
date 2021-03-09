function createInfoCard(title, text) {
    const toast = document.createElement('div');
    toast.classList.add('toast');
    toast.setAttribute('role', "alert");
    toast.setAttribute('aria-live', "assertive");
    toast.setAttribute('aria-atomic', "true");

    const toastHeader = document.createElement('div');
    toastHeader.classList.add('toast-header');
    toast.appendChild(toastHeader);

    const titleNode = document.createElement('strong');
    titleNode.classList.add('mr-auto');
    titleNode.innerText = title;
    toastHeader.appendChild(titleNode);

    const button = document.createElement('button');
    button.classList.add('ml-2', 'mb-1', 'close');
    button.setAttribute('type', 'button');
    button.setAttribute('data-dismiss', 'toast');
    button.setAttribute('aria-label', 'Close');
    button.onclick = () => toast.remove();
    toastHeader.appendChild(button);

    const span = document.createElement('span');
    span.setAttribute('aria-hidden', 'true');
    span.innerText = '×';
    button.appendChild(span);

    const toastBody = document.createElement('div');
    toastBody.classList.add('toast-body');
    toastBody.innerText = text;
    toast.appendChild(toastBody);

    document.getElementById('polite').appendChild(toast);

    setTimeout(() => toast.remove(), 3000);
}