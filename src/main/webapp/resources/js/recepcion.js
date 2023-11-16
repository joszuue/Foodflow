function toggleCustomModal() {
    var customModal = document.getElementById('customModal');
    var overlay = document.getElementById('overlay');

    if (customModal.style.right === '0px') {
        customModal.style.right = '-30%'; /* Esconde completamente en el lado derecho cuando se cierra */
        overlay.style.opacity = '0';
        setTimeout(function() {
            customModal.style.display = 'none';
            overlay.style.display = 'none';
        }, 500); /* Asegura que la visibilidad se establezca después de la transición */
    } else {
        customModal.style.display = 'block';
        overlay.style.display = 'block';
        setTimeout(function() {
            customModal.style.right = '0';
            overlay.style.opacity = '1';
        }, 50); /* Asegura que la visibilidad se establezca antes de la transición */
    }
}