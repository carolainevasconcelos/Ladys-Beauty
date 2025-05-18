<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login | Lady's Beauty</title>
        <link rel="stylesheet" href="visao/css/login.css">
    </head>

    <body>
        <!-- Modal de Login -->
        <div class="auth-container">
            <div id="loginSection">
                <h2>Login</h2>
                <form id="signupForm" method="POST" action="Login.do">
                    <label for="loginEmail">Email:</label>
                    <input type="email" id="loginEmail" name="email" required>

                    <label for="loginPassword">Senha:</label>
                    <input type="password" id="loginPassword" name="senha" required>

                    <button type="submit">Entrar</button>
                </form>
                <p>Não tem conta? <a href="#" id="showSignup">Cadastre-se aqui</a></p>
                <p>Esqueceu a senha? <a href="TrocarSenha"  id="showForgotPassword">Clique aqui</a></p>
            </div>

            <div id="signupSection" style="display: none;">
                <h2>Cadastro</h2>
                <form id="signupForm" method="POST" action="CadCliente.do">
                    <label for="signupName">Nome:</label>
                    <input type="text" id="signupName" name="name" required>

                    <label for="signupEmail">Email:</label>
                    <input type="email" id="signupEmail" name="email" required>

                    <label for="signupPhone">Telefone:</label>
                    <input type="tel" id="signupPhone" name="phone" required>

                    <label for="signupPassword">Senha:</label>
                    <input type="password" id="signupPassword" name="password" required>

                    <label for="signupConfirmPassword">Confirmar Senha:</label>
                    <input type="password" id="signupConfirmPassword" name="confirmPassword" required>

                    <button type="submit">Cadastrar</button>
                </form>
                <p>Já tem uma conta? <a href="#" id="showLogin">Faça login aqui</a></p>
            </div>

            <!-- Botão para voltar para o index -->
            <p><a href="index.jsp">Voltar para a Página Inicial</a></p>
        </div>

        <script>
            // Alternar entre Login e Cadastro
            document.addEventListener('click', function (event) {
                if (event.target.id === 'showSignup') {
                    document.getElementById('loginSection').style.display = "none";
                    document.getElementById('signupSection').style.display = "block";
                } else if (event.target.id === 'showLogin') {
                    document.getElementById('signupSection').style.display = "none";
                    document.getElementById('loginSection').style.display = "block";
                }
            });
        </script>

    </body>

</html>