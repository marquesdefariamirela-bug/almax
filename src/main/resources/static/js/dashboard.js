document.addEventListener("DOMContentLoaded", () => {
    atualizarData();
});

function atualizarData() {
    const elemento = document.getElementById("currentDate");

    if (!elemento) {
        return;
    }

    const agora = new Date();

    const dataFormatada = agora.toLocaleDateString("pt-BR", {
        weekday: "long",
        day: "2-digit",
        month: "long",
        year: "numeric",
    });

    elemento.textContent =
        dataFormatada.charAt(0).toUpperCase() + dataFormatada.slice(1);
}
