<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastrar Serviço</title>
        <link rel="stylesheet" href="visao/css/cadastro.css">
        <script src="visao/js/sistemaPontos.js"></script>
    </head>
    <body>
        <div class="container">
            <form action="CadServico.do" method="post">
                <h2>Cadastrar Serviço</h2>

                <label for="nome">Nome do Serviço:</label>
                <input type="text" id="nome" name="nome" required>

                <label for="descricao">Descrição:</label>
                <textarea id="descricao" name="descricao" required></textarea>

                <label for="preco">Preço (R$):</label>
                <input type="number" id="preco" name="preco" step="0.01" required oninput="calcularPontos()">

                <label for="categoria">Categoria:</label>
                <select id="categoria" name="categoria" required>
                    <option value="" selected disabled>Selecione</option>
                    <option value="cabelo">Cabelos</option>
                    <option value="unha">Unhas</option>
                    <option value="maquiagem">Maquiagem</option>
                    <option value="sobrancelhas">Sobrancelhas</option>
                    <option value="depilacao">Depilação</option>
                </select>

                <label for="funcionario_id">Funcionários Disponíveis</label>
                <input type="number" id="funcionario_id" name="funcionario_id" required min="1" step="1">

                <label for="status">Status:</label>
                <select id="status" name="status">
                    <option value="" selected disabled>Selecione</option>
                    <option value="ativo">Ativo</option>
                    <option value="inativo">Inativo</option>
                </select>

                <label for="pontos_ganho">Pontos Ganhos:</label>
                <input type="number" id="pontos_ganho" name="pontos_ganho" min="0" required readonly>

                <label for="pontos_resgate">Pontos para Resgate:</label>
                <input type="number" id="pontos_resgate" name="pontos_resgate" min="0" required readonly>

                <div class="button-group">
                    <button type="submit">Cadastrar</button>
                    <a href="PaginaADM/pag-servicos.jsp">Voltar</a>
                </div>
            </form>
        </div>
    </body>
</html>
