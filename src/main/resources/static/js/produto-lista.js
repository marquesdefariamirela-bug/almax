let produtoSelecionado = null;


// ==========================================
// ELEMENTOS
// ==========================================

const searchInput =
    document.getElementById("searchInput");

const statusFilter =
    document.getElementById("statusFilter");

const ambienteFilter =
    document.getElementById("ambienteFilter");


// ==========================================
// BUSCA
// ==========================================

searchInput.addEventListener("input", filtrarProdutos);

statusFilter.addEventListener("change", filtrarProdutos);

ambienteFilter.addEventListener("change", filtrarProdutos);


// ==========================================
// FILTROS
// ==========================================

function filtrarProdutos() {

    const busca =
        searchInput.value
            .toLowerCase()
            .trim();

    const status =
        statusFilter.value
            .toLowerCase();

    const ambiente =
        ambienteFilter.value
            .toLowerCase();


    const linhas =
        document.querySelectorAll(
            "#produtosTableBody tr"
        );


    let encontrados = 0;


    linhas.forEach(linha => {

        const texto =
            linha.textContent
                .toLowerCase();

        const statusLinha =
            obterStatus(linha);

        const ambienteLinha =
            obterAmbiente(linha);


        const correspondeBusca =
            !busca ||
            texto.includes(busca);


        const correspondeStatus =
            !status ||
            statusLinha === status;


        const correspondeAmbiente =
            !ambiente ||
            ambienteLinha.includes(ambiente);


        const mostrar =
            correspondeBusca &&
            correspondeStatus &&
            correspondeAmbiente;


        linha.style.display =
            mostrar ? "" : "none";


        if (mostrar) {
            encontrados++;
        }

    });


    document.getElementById("resultCount")
        .textContent =
        `${encontrados} registro${encontrados !== 1 ? "s" : ""} encontrado${encontrados !== 1 ? "s" : ""}`;

}


// ==========================================
// OBTÉM STATUS DA LINHA
// ==========================================

function obterStatus(linha) {

    const status =
        linha.querySelector(".status");

    if (!status) {
        return "";
    }

    return status.textContent
        .trim()
        .toLowerCase()
        .replace("em manutenção", "manutencao");

}


// ==========================================
// OBTÉM AMBIENTE
// ==========================================

function obterAmbiente(linha) {

    const ambiente =
        linha.querySelector(".location");

    if (!ambiente) {
        return "";
    }

    return ambiente.textContent
        .trim()
        .toLowerCase();

}


// ==========================================
// LIMPAR FILTROS
// ==========================================

function limparFiltros() {

    searchInput.value = "";

    statusFilter.value = "";

    ambienteFilter.value = "";

    filtrarProdutos();

}


// ==========================================
// VISUALIZAR
// ==========================================

function visualizarProduto(id) {

    window.location.href =
        `/produtos/${id}`;

}


// ==========================================
// EDITAR
// ==========================================

function editarProduto(id) {

    window.location.href =
        `/produtos/${id}/editar`;

}


// ==========================================
// EXCLUIR
// ==========================================

function excluirProduto(id) {

    produtoSelecionado = id;

    document
        .getElementById("deleteModal")
        .classList.add("show");

}


// ==========================================
// FECHAR MODAL
// ==========================================

function fecharModal() {

    document
        .getElementById("deleteModal")
        .classList.remove("show");

    produtoSelecionado = null;

}


// ==========================================
// CONFIRMAR EXCLUSÃO
// ==========================================

async function confirmarExclusao() {

    if (!produtoSelecionado) {
        return;
    }

    const id = produtoSelecionado;

    /*
     * Quando a API estiver pronta:
     *
     * const response = await fetch(
     *     `/api/produtos/${id}`,
     *     {
     *         method: "DELETE"
     *     }
     * );
     *
     * if (!response.ok) {
     *     mostrarToast(
     *         "Erro",
     *         "Não foi possível excluir o patrimônio."
     *     );
     *
     *     return;
     * }
     */

    console.log(
        "Excluir produto:",
        id
    );

    fecharModal();

    mostrarToast(
        "Sucesso",
        "Patrimônio excluído com sucesso."
    );

}


// ==========================================
// EXPORTAR CSV
// ==========================================

function exportarCSV() {

    const tabela =
        document.querySelector("table");

    const linhas =
        tabela.querySelectorAll("tr");

    let csv = [];


    linhas.forEach(linha => {

        if (linha.style.display === "none") {
            return;
        }

        const colunas =
            linha.querySelectorAll("th, td");

        const dados = [];

        colunas.forEach(coluna => {

            let texto =
                coluna.innerText
                    .replace(/\n/g, " ")
                    .trim();

            texto =
                `"${texto.replace(/"/g, '""')}"`;

            dados.push(texto);

        });

        csv.push(dados.join(";"));

    });


    const blob =
        new Blob(
            ["\ufeff" + csv.join("\n")],
            {
                type: "text/csv;charset=utf-8;"
            }
        );


    const url =
        URL.createObjectURL(blob);


    const link =
        document.createElement("a");

    link.href = url;

    link.download =
        "patrimonios.csv";

    link.click();

    URL.revokeObjectURL(url);

}


// ==========================================
// TOAST
// ==========================================

function mostrarToast(
    titulo,
    mensagem
) {

    const toast =
        document.getElementById("toast");

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