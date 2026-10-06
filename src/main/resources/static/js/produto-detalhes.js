function imprimir() {

    window.print();

}


function abrirExclusao() {

    document
        .getElementById("deleteModal")
        .classList.add("show");

}


function fecharExclusao() {

    document
        .getElementById("deleteModal")
        .classList.remove("show");

}


async function excluirProduto() {

    /*
     * Quando sua API estiver pronta:
     *
     * const response = await fetch(
     *     "/api/produtos/1",
     *     {
     *         method: "DELETE"
     *     }
     * );
     *
     * if (!response.ok) {
     *     alert("Erro ao excluir patrimônio.");
     *     return;
     * }
     */

    console.log("Excluindo patrimônio #1");

    window.location.href = "/produtos";

}