/* =========================================================
   ALMAX — GLOBAL
   ========================================================= */

document.addEventListener("DOMContentLoaded", () => {


    /* =====================================================
       ALERTS
       ===================================================== */

    initAlerts();


    /* =====================================================
       CONFIRMAÇÃO DE EXCLUSÃO
       ===================================================== */

    initDeleteConfirmation();


    /* =====================================================
       LOADING DOS FORMULÁRIOS
       ===================================================== */

    initFormLoading();


    /* =====================================================
       AUTOFOCUS
       ===================================================== */

    initAutoFocus();

});


/* =========================================================
   ALERTS
   ========================================================= */

function initAlerts() {

    const alerts =
        document.querySelectorAll(".alert");


    alerts.forEach(alert => {

        const closeButton =
            alert.querySelector(".alert-close");


        if (closeButton) {

            closeButton.addEventListener("click", () => {

                closeAlert(alert);

            });

        }

    });

}


/* =========================================================
   FECHAR ALERT
   ========================================================= */

function closeAlert(alert) {

    if (!alert) {
        return;
    }


    alert.style.opacity = "0";

    alert.style.transform =
        "translateY(-5px)";


    alert.style.transition =
        "opacity 0.18s ease, transform 0.18s ease";


    setTimeout(() => {

        alert.remove();

    }, 180);

}


/* =========================================================
   ALERT AUTOMÁTICO
   ========================================================= */

function autoCloseAlerts() {

    const alerts =
        document.querySelectorAll(
            ".alert[data-auto-close='true']"
        );


    alerts.forEach(alert => {

        const duration =
            parseInt(
                alert.dataset.duration || "4000",
                10
            );


        setTimeout(() => {

            closeAlert(alert);

        }, duration);

    });

}


document.addEventListener(
    "DOMContentLoaded",
    autoCloseAlerts
);


/* =========================================================
   CONFIRMAÇÃO DE EXCLUSÃO
   ========================================================= */

function initDeleteConfirmation() {

    const deleteButtons =
        document.querySelectorAll(
            "[data-confirm-delete]"
        );


    deleteButtons.forEach(button => {

        button.addEventListener("click", event => {

            const message =
                button.dataset.confirmDelete ||
                "Deseja realmente excluir este registro?";


            const confirmed =
                window.confirm(message);


            if (!confirmed) {

                event.preventDefault();

            }

        });

    });

}


/* =========================================================
   LOADING DOS FORMULÁRIOS
   ========================================================= */

function initFormLoading() {

    const forms =
        document.querySelectorAll(
            "form[data-loading]"
        );


    forms.forEach(form => {

        form.addEventListener("submit", () => {

            const submitButton =
                form.querySelector(
                    "button[type='submit']"
                );


            if (!submitButton) {
                return;
            }


            submitButton.disabled = true;


            const originalContent =
                submitButton.innerHTML;


            submitButton.dataset.originalContent =
                originalContent;


            submitButton.innerHTML = `

<span class="button-spinner"></span>

<span>Processando...</span>

    `;

        });

    });

}


/* =========================================================
   AUTOFOCUS
   ========================================================= */

function initAutoFocus() {

    const element =
        document.querySelector(
            "[data-autofocus]"
        );


    if (element) {

        element.focus();

    }

}


/* =========================================================
   DEBOUNCE
   ========================================================= */

function debounce(callback, delay = 300) {

    let timeout;


    return function (...args) {

        clearTimeout(timeout);


        timeout = setTimeout(() => {

            callback.apply(this, args);

        }, delay);

    };

}


/* =========================================================
   FORMATAÇÃO DE TEXTO
   ========================================================= */

function capitalize(value) {

    if (!value) {
        return "";
    }


    return value.charAt(0).toUpperCase()
        + value.slice(1);

}


/* =========================================================
   CONFIRMAÇÃO GENÉRICA
   ========================================================= */

function confirmAction(
    message = "Deseja continuar?"
) {

    return window.confirm(message);

}


/* =========================================================
   LOADING MANUAL
   ========================================================= */

function setButtonLoading(
    button,
    loading = true
) {

    if (!button) {
        return;
    }


    if (loading) {

        if (!button.dataset.originalContent) {

            button.dataset.originalContent =
                button.innerHTML;

        }


        button.disabled = true;


        button.innerHTML = `

<span class="button-spinner"></span>

<span>Processando...</span>

    `;

    } else {

        button.disabled = false;


        if (button.dataset.originalContent) {

            button.innerHTML =
                button.dataset.originalContent;

        }

    }

}