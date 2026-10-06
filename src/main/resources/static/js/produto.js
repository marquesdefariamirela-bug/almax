const form = document.getElementById("produtoForm");

const descricao = document.getElementById("descricao");
const descricaoCounter = document.getElementById("descricaoCounter");

const valorInput = document.getElementById("valor");


// ==========================================
// CONTADOR DA DESCRIÇÃO
// ==========================================

descricao.addEventListener("input", () => {

    const tamanho = descricao.value.length;

    descricaoCounter.textContent = `${tamanho} / 500`;

});


// ==========================================
// MÁSCARA DE VALOR
// ==========================================

valorInput.addEventListener("input", function () {

    let valor = this.value;

    valor = valor.replace(/\D/g, "");

    if (!valor) {
        this.value = "";
        return;
    }

    valor = (parseInt(valor, 10) / 100).toFixed(2);

    this.value = valor
        .replace(".", ",")
        .replace(/\B(?=(\d{3})+(?!\d))/g, ".");

});


// ==========================================
// CONVERTE VALOR BRASILEIRO PARA BIGDECIMAL
// ==========================================

function converterValor(valor) {

    if (!valor) {
        return null;
    }

    return valor
        .replace(/\./g, "")
        .replace(",", ".");
}


// ==========================================
// MONTA OBJETO DO PRODUTO
// ==========================================

function obterDadosFormulario() {

    const ambienteId =
        document.getElementById("ambiente").value;

    const tagId =
        document.getElementById("tag").value;

    return {

        numeroPatrimonio:
            document.getElementById("numeroPatrimonio").value.trim(),

        nome:
            document.getElementById("nome").value.trim(),

        descricao:
            document.getElementById("descricao").value.trim(),

        marca:
            document.getElementById("marca").value.trim(),

        modelo:
            document.getElementById("modelo").value.trim(),

        numeroSerie:
            document.getElementById("numeroSerie").value.trim(),

        valor:
            converterValor(
                document.getElementById("valor").value
            ),

        dataAquisicao:
            document.getElementById("dataAquisicao").value || null,

        status:
        document.getElementById("status").value,

        ambiente:
            ambienteId
                ? { id: Number(ambienteId) }
                : null,

        tag:
            tagId
                ? { id: Number(tagId) }
                : null
    };
}


// ==========================================
// SUBMIT
// ==========================================

form.addEventListener("submit", async function (event) {

    event.preventDefault();

    if (!form.checkValidity()) {

        form.reportValidity();

        return;
    }

    const produto = obterDadosFormulario();

    console.log("Produto:", produto);

    /*
     * Quando o endpoint Spring Boot estiver pronto:
     *
     * const response = await fetch("/api/produtos", {
     *
     *     method: "POST",
     *
     *     headers: {
     *         "Content-Type": "application/json"
     *     },
     *
     *     body: JSON.stringify(produto)
     * });
     *
     * if (!response.ok) {
     *     throw new Error("Erro ao cadastrar patrimônio");
     * }
     */

    mostrarToast(
        "Sucesso",
        "Patrimônio cadastrado com sucesso!"
    );

});


// ==========================================
// SALVAR E VOLTAR
// ==========================================

function salvarERetornar() {

    if (!form.checkValidity()) {

        form.reportValidity();

        return;
    }

    const produto = obterDadosFormulario();

    console.log("Produto:", produto);

    /*
     * Aqui você pode chamar a mesma API:
     *
     * POST /api/produtos
     */

    mostrarToast(
        "Sucesso",
        "Patrimônio cadastrado. Redirecionando..."
    );

    setTimeout(() => {

        // window.location.href = "/produtos";

    }, 1200);
}


// ==========================================
// LIMPAR
// ==========================================

function limparFormulario() {

    const confirmar = confirm(
        "Deseja realmente limpar todos os campos?"
    );

    if (!confirmar) {
        return;
    }

    form.reset();

    document.getElementById("status").value = "ATIVO";

    descricaoCounter.textContent = "0 / 500";

}


// ==========================================
// VOLTAR
// ==========================================

function voltar() {

    // Altere conforme sua rota.
    window.location.href = "/produtos";
}


// ==========================================
// TOAST
// ==========================================

function mostrarToast(titulo, mensagem) {

    const toast = document.getElementById("toast");

    document.getElementById("toastTitle")
        .textContent = titulo;

    document.getElementById("toastMessage")
        .textContent = mensagem;

    toast.classList.add("show");

    setTimeout(() => {

        fecharToast();

    }, 4000);
}


function fecharToast() {

    document
        .getElementById("toast")
        .classList.remove("show");

}