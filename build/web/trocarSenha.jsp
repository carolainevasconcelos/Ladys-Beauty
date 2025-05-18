<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <title>Trocar Senha | Lady's Beauty</title>
        <link rel="stylesheet" href="visao/css/login.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>

        <div class="auth-container">
            <h2>Trocar Senha</h2>

            <form method="POST" action="TrocarSenha">

                <label for="email">Email cadastrado:</label>
                <input type="email" id="email" name="email" required>

                <label for="novaSenha">Nova Senha:</label>
                <input type="password" id="novaSenha" name="novaSenha" required>

                <label for="confirmarSenha">Confirmar Nova Senha:</label>
                <input type="password" id="confirmarSenha" name="confirmarSenha" required>

                <button type="submit">Atualizar Senha</button>
            </form>

            <p><a href="login.jsp">Voltar para o Login</a></p>
        </div>

        <script>
            const form = document.querySelector('form');
            form.addEventListener('submit', function (e) {
                const novaSenha = document.getElementById('novaSenha').value;
                const confirmarSenha = document.getElementById('confirmarSenha').value;
                if (novaSenha !== confirmarSenha) {
                    e.preventDefault();
                    alert('As senhas não coincidem!');
                }
            });
        </script>

    </body>
</html>