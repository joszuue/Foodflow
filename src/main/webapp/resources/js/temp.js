// Función para formatear el tiempo en formato MM:SS
function formatTime(seconds) {
    var minutes = Math.floor(seconds / 60);
    var secs = seconds % 60;

    return (
        (minutes < 10 ? '0' : '') + minutes + ':' +
        (secs < 10 ? '0' : '') + secs
    );
}

// Función para actualizar el temporizador cada segundo
function actualizarTemporizador() {
    tiempoTranscurrido--;
    document.getElementById('temporizador').innerText = formatTime(tiempoTranscurrido);

    if (tiempoTranscurrido === 0) {
        clearInterval(temporizadorInterval);
        alert('¡Tiempo agotado!');
    }
}

// Configurar el tiempo en segundos
var tiempoTotal = 1600; // Cambia este valor al tiempo deseado en segundos
var tiempoTranscurrido = tiempoTotal;

// Mostrar el tiempo inicial
document.getElementById('temporizador').innerText = formatTime(tiempoTotal);

// Iniciar temporizador
var temporizadorInterval = setInterval(actualizarTemporizador, 1000);