<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.controle.FrequenciaCliente" %>

<html>
<head>
    <title>Frequência dos Clientes</title>
    <link rel="stylesheet" href="visao/css/style.css"/>
    <link rel="stylesheet" href="visao/css/lista.css">
</head>
<body>
    <!-- Botão de Voltar -->
    <div class="auth-buttons">
        <a href="PaginaADM/pag-Agendamentos.jsp"><button>Voltar</button></a>
    </div>
    
    <h2>Frequência dos Clientes</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID Cliente</th>
                <th>Nome</th>
                <th>Quantidade de Agendamentos</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<FrequenciaCliente> lista = (List<FrequenciaCliente>) request.getAttribute("frequenciaLista");
                if (lista != null && !lista.isEmpty()) {
                    for (FrequenciaCliente fc : lista) {
            %>
                <tr>
                    <td><%= fc.getIdCliente() %></td>
                    <td><%= fc.getNomeCliente() %></td>
                    <td><%= fc.getFrequencia() %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="3">Nenhum dado encontrado.</td></tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
