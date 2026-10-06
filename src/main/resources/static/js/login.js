/* =========================================================
   ALMAX — LOGIN
   ========================================================= */

document.addEventListener("DOMContentLoaded", () => {

    const loginForm =
        document.getElementById("loginForm");

    const passwordInput =
        document.getElementById("password");

    const togglePassword =
        document.getElementById("togglePassword");

    const eyeIcon =
        document.getElementById("eyeIcon");

    const loginButton =
        document.getElementById("loginButton");

    const currentYear =
        document.getElementById("currentYear");


    /* =====================================================
       ANO ATUAL
       ===================================================== */

    if (currentYear) {

        currentYear.textContent =
            new Date().getFullYear();
    }


    /* =====================================================
       MOSTRAR / OCULTAR SENHA
       ===================================================== */

    if (togglePassword && passwordInput) {

        togglePassword.addEventListener(
            "click",
            () => {

                const isPassword =
                    passwordInput.type === "password";

                passwordInput.type =
                    isPassword
                        ? "text"
                        : "password";


                togglePassword.setAttribute(
                    "aria-label",
                    isPassword
                        ? "Ocultar senha"
                        : "Mostrar senha"
                );


                /*
                 * Altera o ícone do olho.
                 */

                if (isPassword) {

                    eyeIcon.innerHTML = `
                        <path
                            d="M2.1 3.5
                               3.5 2.1
                               21.9 20.5
                               20.5 21.9
                               17.4 18.8
                               A11.7 11.7 0 0 1
                               12 20
                               C7 20 2.7 16.9 1 12
                               a12.8 12.8 0 0 1
                               4.1-5.2
                               L2.1 3.5ZM12 7
                               c-2.8 0-5 2.2-5 5
                               0 1.1.4 2.1 1 2.9
                               l2.1 2.1
                               A4.9 4.9 0 0 0
                               17 12
                               c0-2.8-2.2-5-5-5Z">
                        </path>
                    `;

                } else {

                    eyeIcon.innerHTML = `
                        <path
                            d="M12 5c-5 0-9.27 3.11-11 7
                               1.73 3.89 6 7 11 7
                               s9.27-3.11 11-7
                               c-1.73-3.89-6-7-11-7Zm0
                               12a5 5 0 1 1 0-10 5 5
                               0 0 1 0 10Zm0-8a3 3 0
                               1 0 0 6 3 3 0 0 0 0-6Z">
                        </path>
                    `;
                }
            }
        );
    }


    /* =====================================================
       SUBMIT
       ===================================================== */

    if (loginForm && loginButton) {

        loginForm.addEventListener(
            "submit",
            () => {

                /*
                 * Não fazemos fetch aqui.
                 *
                 * O formulário será enviado normalmente
                 * para o Spring Security através de POST /login.
                 */

                loginButton.classList.add("loading");

                loginButton.disabled = true;
            }
        );
    }

});