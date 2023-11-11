$(document).ready(function () {

    // MENU MOBILE
    $('[data-toggle="toggle-nav"]').on('click', function () {
        $(this).closest('nav').find($(this).attr('data-target')).toggleClass('hidden');
        return false;
    });

    // ICONOS
    feather.replace();

    // SCROLL SUAVE
    var scroll = new SmoothScroll('a[href*="#"]');

    // SLIDER PEQUEÑO
    $('#slider-1').slick({
        infinite: true,
        prevArrow: $('.prev'),
        nextArrow: $('.next'),
    });


    //MENU PRINCIPAL PLATOS
    $('#slider-2').slick({
        dots: false,
        arrows: false,
        infinite: true,
        slidesToShow: 2,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        centerMode: true,
        customPaging: function (slider, i) {
            return '<div class="bg-white br-round w-1 h-1 opacity-50 mt-5" id=' + i + '> </div>'
        },
        responsive: [{
            breakpoint: 768,
            settings: {
                slidesToShow: 1
            }
        },]
    });


    $('#slider-3').slick({
        dots: true,
        arrows: false,
        infinite: true,
        slidesToShow: 9,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 3000,
        centerMode: true,
        customPaging: function (slider, i) {
            return '<div class="bg-white br-round w-1 h-1 opacity-50 mt-10" id=' + i + '> </div>'
        },
        responsive: [{
            breakpoint: 768,
            settings: {
                slidesToShow: 1
            }
        },]
    });

    $(document).on('click', '[data-toggle=modal]', function () {
        $($(this).attr('data-target')).toggleClass('hidden');
    });
    $(document).on('click', '[data-dismiss=modal]', function () {
        $(this).closest('.modal').toggleClass('hidden');
    });
});