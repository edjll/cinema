function hideControl(id) {
    $activeSlide = $(`#${id} .active`);
    $prevControl = $(`#${id} .carousel-control-prev`);
    $nextControl = $(`#${id} .carousel-control-next`);

    console.log($activeSlide);

    if ($activeSlide[0].previousElementSibling == null) {
        $prevControl.addClass('hide');
    } else if ($prevControl.hasClass('hide')) {
        $prevControl.removeClass('hide');
    }
    if ($activeSlide[0].nextElementSibling == null) {
        $nextControl.addClass('hide');
    } else if ($nextControl.hasClass('hide')) {
        $nextControl.removeClass('hide');
    }
}

$('.carousel').each((i, e) => {
    hideControl(e.id);
    $(`#${e.id}`).on('slid.bs.carousel', () => hideControl(e.id));
})