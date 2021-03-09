function playOrPauseVideo() {
    const player = document.getElementsByClassName('film-trailer')[0];
    const play = document.getElementsByClassName('video-controls-play')[0];
    const pause = document.getElementsByClassName('video-controls-pause')[0];

    if (player.paused) {
        player.play();
        play.classList.remove('active');
        pause.classList.add('active');
    } else {
        player.pause();
        pause.classList.remove('active');
        play.classList.add('active');
    }
}

function changeVolume(volume) {
    const player = document.getElementsByClassName('film-trailer')[0];

    player.volume = volume / 100;

    if (volume == 0) {
        if (!player.muted) changeMute();
    } else {
        if (player.muted) changeMute();
    }
}

function changeMuteIcons() {
    const mute = document.getElementsByClassName('video-controls-mute')[0];
    const unmute = document.getElementsByClassName('video-controls-unmute')[0];

    if (mute.classList.contains('active')) {
        mute.classList.remove('active');
        unmute.classList.add('active');
    } else {
        unmute.classList.remove('active');
        mute.classList.add('active');
    }
}

function changeMute() {
    const player = document.getElementsByClassName('film-trailer')[0];
    if (player.muted) {
        player.muted = false;
        changeMuteIcons();
    } else {
        player.muted = true;
        changeMuteIcons();
    }
}