(() => {
    const storageKey = "almax-theme";
    const savedTheme = localStorage.getItem(storageKey);
    const initialTheme = savedTheme === "dark" ? "dark" : "light";

    document.documentElement.dataset.theme = initialTheme;

    document.addEventListener("DOMContentLoaded", () => {
        const options = document.querySelectorAll("[data-theme-choice]");

        options.forEach(option => {
            option.checked = option.value === initialTheme;
            option.addEventListener("change", () => {
                if (!option.checked) {
                    return;
                }

                document.documentElement.dataset.theme = option.value;
                localStorage.setItem(storageKey, option.value);
            });
        });
    });
})();
