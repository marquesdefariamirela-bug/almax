/* =========================================================
   ALMAX — NAVBAR
   ========================================================= */

document.addEventListener("DOMContentLoaded", () => {


    /* =====================================================
       RELÓGIO / DATA
       ===================================================== */

    const dateElement =
        document.querySelector("[data-current-date]");

    if (dateElement) {

        updateDate(dateElement);

        setInterval(() => {
            updateDate(dateElement);
        }, 60000);

    }


    /* =====================================================
       NOTIFICAÇÕES
       ===================================================== */

    const notificationButton =
        document.querySelector("[data-notifications]");

    if (notificationButton) {

        notificationButton.addEventListener("click", () => {

            console.log("Abrir notificações");

        });

    }


    /* =====================================================
       FUNÇÃO DE DATA
       ===================================================== */

    function updateDate(element) {

        const now = new Date();

        const date = now.toLocaleDateString("pt-BR", {
            weekday: "short",
            day: "2-digit",
            month: "short"
        });

        element.textContent = date;

    }

});