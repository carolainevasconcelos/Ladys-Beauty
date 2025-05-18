<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastrar Serviço</title>
        <link rel="stylesheet" href="visao/css/cadastro.css">
    </head>
    <body>
        <div class="container">
            <form action="CadPPservlet.do" method="post">
                <h2>Cadastrar Funcionarios</h2>
                <label for="name">Nome:</label>
                <input type="text" id="name" name="name" required><br>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required><br>

                <label for="phone">Telefone:</label>
                <input type="text" id="phone" name="phone" required><br>

                <label for="cargo">Cargo:</label>
                <select id="cargo" name="cargo" required>
                    <option value="funcionario" selected>Funcionário</option>
                    <option value="administrador">Administrador</option>
                </select><br>

                <label for="especialidade">Especialidade:</label>
                <select id="especialidade" name="especialidade">
                    <option value="">Selecione</option>
                    <option value="cabelos">Cabelos</option>
                    <option value="unhas">Unhas</option>
                    <option value="maquiagem">Maquiagem</option>
                    <option value="sobrancelhas">Sobrancelhas</option>
                    <option value="depilacao">Depilação</option>
                </select><br>

                <label for="password">Senha:</label>
                <input type="password" id="password" name="password" required>

                <div class="button-group">
                    <button type="submit">Cadastrar</button>
                    <a href="PaginaADM/pag-Funcionarios.jsp">Voltar</a>
                </div>
            </form>
        </div>
    </body>
</html>