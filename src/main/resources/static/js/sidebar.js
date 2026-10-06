/* =========================================================
   ALMAX — SIDEBAR
   ========================================================= */

document.addEventListener("DOMContentLoaded", () => {

    const sidebar = document.querySelector(".app-sidebar");
    const overlay = document.querySelector(".sidebar-overlay");
    const toggle = document.querySelector(".sidebar-toggle");

    if (!sidebar) {
        return;
    }


    /* =====================================================
       ABRIR SIDEBAR
       ===================================================== */

    function openSidebar() {

        sidebar.classList.add("open");

        if (overlay) {
            overlay.classList.add("active");
        }

        if (toggle) {
            toggle.setAttribute("aria-expanded", "true");
        }

        document.body.classList.add("sidebar-open");
    }


    /* =====================================================
       FECHAR SIDEBAR
       ===================================================== */

    function closeSidebar() {

        sidebar.classList.remove("open");

        if (overlay) {
            overlay.classList.remove("active");
        }

        if (toggle) {
            toggle.setAttribute("aria-expanded", "false");
        }

        document.body.classList.remove("sidebar-open");
    }


    /* =====================================================
       TOGGLE
       ===================================================== */

    if (toggle) {

        toggle.addEventListener("click", () => {

            if (sidebar.classList.contains("open")) {
                closeSidebar();
            } else {
                openSidebar();
            }

        });

    }


    /* =====================================================
       OVERLAY
       ===================================================== */

    if (overlay) {

        overlay.addEventListener("click", () => {
            closeSidebar();
        });

    }


    /* =====================================================
       ESC
       ===================================================== */

    document.addEventListener("keydown", (event) => {

        if (event.key === "Escape") {
            closeSidebar();
        }

    });


    /* =====================================================
       FECHAR AO CLICAR EM UM LINK — MOBILE
       ===================================================== */

    const menuLinks = sidebar.querySelectorAll(".menu-item");

    menuLinks.forEach(link => {

        link.addEventListener("click", () => {

            if (window.innerWidth <= 900) {
                closeSidebar();
            }

        });

    });


    /* =====================================================
       RESPONSIVIDADE
       ===================================================== */

    window.addEventListener("resize", () => {

        if (window.innerWidth > 900) {
            closeSidebar();
        }

    });

});