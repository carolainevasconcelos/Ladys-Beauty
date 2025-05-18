function calcularPontos() {
    var preco = parseFloat(document.getElementById("preco").value);
    if (!isNaN(preco) && preco > 0) {
        var pontosGanho = preco;
        var pontosResgate = preco * 10;

        document.getElementById("pontos_ganho").value = pontosGanho;
        document.getElementById("pontos_resgate").value = pontosResgate;
    } else {
        document.getElementById("pontos_ganho").value = "";
        document.getElementById("pontos_resgate").value = "";
    }
}