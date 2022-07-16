package entities;

import conexaobanco.ConexaoMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Pedido {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private static Integer idPedido;
    private Integer idCliente;
    private static ItemPedido produtos;
    private static Double valorPedido;
    private static String dataPedido;
    private final String metodoPagamento;
    private static Integer tipoPedido;

    public Pedido() {
        this.metodoPagamento = "Cartão de Crédito";

        iniciaParametrosSistema();
    }

    public Pedido(Integer idCliente, ItemPedido produtos, Integer tipoPedido) {
        this.idCliente = idCliente;
        Pedido.produtos = produtos;
        this.metodoPagamento = "Cartão de Crédito";
        this.tipoPedido = tipoPedido;

        iniciaParametrosSistema();
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        Pedido.idPedido = idPedido;
    }

    public Integer getidCliente() {
        return idCliente;
    }

    public void setidCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public ItemPedido getProdutos() {
        return produtos;
    }

    public void setProdutos(ItemPedido produtos) {
        Pedido.produtos = produtos;
    }

    public Double getValorPedido() {
        return valorPedido;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public Integer getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(Integer tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    private void iniciaParametrosSistema() {
        Random gerador = new Random();
        Pedido.idPedido = Integer.parseInt(("" + System.currentTimeMillis() + ""
                + gerador.nextInt(100)).substring(0, 10));

        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        this.dataPedido = sdf.format(cal.getTime());
    }

    public static void salvarPedido(Pedido p) {

        try {
            String query = "INSERT INTO Pedido(ID_Pedido,"
                    + "Data_Pedido,Valor_Pedido,Tipo_Pedido,ID_Usuario) "
                    + "values (?,?,?,?,?)";

            PreparedStatement preparedStmt = ConexaoMySQL.getConexaoMySQL().prepareStatement(query);
            preparedStmt.setInt(1, p.getIdPedido());
            preparedStmt.setString(2, p.getDataPedido());
            preparedStmt.setDouble(3, valorPedido(tipoPedido));
            preparedStmt.setInt(4, p.getTipoPedido());
            preparedStmt.setInt(5, p.getidCliente());

            preparedStmt.execute();

            ConexaoMySQL.getConexaoMySQL().close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static double valorPedido(Integer tipoPedido) {

        double precoFilme = 0.0;

        try {
            String query = null;

            if (tipoPedido == 0) {
                query = "SELECT Preco_Compra FROM Filme where ID_Filme = " + produtos.getIdFilme() + ";";

                Statement st = ConexaoMySQL.getConexaoMySQL().createStatement();

                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    precoFilme = rs.getDouble("Preco_Compra");
                }
            } else {
                query = "SELECT Preco_Aluguel FROM Filme where ID_Filme = " + produtos.getIdFilme() + ";";

                Statement st = ConexaoMySQL.getConexaoMySQL().createStatement();

                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    precoFilme = rs.getDouble("Preco_Aluguel");
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        Pedido.valorPedido = produtos.getQuantidade() * precoFilme;
        return precoFilme;
    }

    public static StringBuilder mostraItensPedido(Integer tipoPedido) {
        StringBuilder sb = new StringBuilder();

        try {
            String query = "SELECT * FROM Filme where ID_Filme = " + produtos.getIdFilme() + ";";

            Statement st = ConexaoMySQL.getConexaoMySQL().createStatement();

            ResultSet rs = st.executeQuery(query);

            if (tipoPedido == 0) {
                while (rs.next()) {
                    sb.append("Título = ");
                    sb.append(rs.getString("Titulo"));
                    sb.append(", Gênero = ");
                    sb.append(rs.getString("Genero"));
                    sb.append(", Ano de lançamento = ");
                    sb.append(rs.getInt("Ano_Lancamento"));
                    sb.append(", Duração = ");
                    sb.append(rs.getString("Duracao"));
                    sb.append(", Preço de compra = ");
                    sb.append(String.format("%.2f", rs.getDouble("Preco_Compra")));
                }
            } else {
                while (rs.next()) {
                    sb.append("Título = ");
                    sb.append(rs.getString("Titulo"));
                    sb.append(", Gênero = ");
                    sb.append(rs.getString("Genero"));
                    sb.append(", Ano de lançamento = ");
                    sb.append(rs.getInt("Ano_Lancamento"));
                    sb.append(", Duração = ");
                    sb.append(rs.getString("Duracao"));
                    sb.append(", Preço de aluguel = ");
                    sb.append(String.format("%.2f", rs.getDouble("Preco_Aluguel")));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return sb;
    }

    public static String pegaNomeCliente(Integer idCliente) {
        String nomeCliente = "";

        try {
            String query = "Select Nome from Usuario_Administrador_Cliente where ID_Usuario = " + idCliente + ";";

            Statement st = ConexaoMySQL.getConexaoMySQL().createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                nomeCliente = rs.getString("Nome");
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return nomeCliente;
    }

    public static StringBuilder pegaDadosTipoPedido(Integer tipoPedido) {
        StringBuilder sb = new StringBuilder();

        if (tipoPedido == 0) {
            sb.append("Compra");
            sb.append("\n");
            sb.append("Data do pedido: ");
            sb.append(dataPedido);
            sb.append("\n");
        } else {
            sb.append("Aluguel");
            sb.append("\n");
            sb.append("Data do pedido: ");
            sb.append(dataPedido);
            sb.append("\n");
            sb.append("Vencimento do aluguel: ");
            sb.append(produtos.getDataVencimentoAluguel());
            sb.append("\n");
        }

        return sb;
    }

//    public static String pegaDataVencimentoAluguel() {
//        String dataVencimentoAluguel = null;
//
//        try {
//            String query = "Select Data_Vencimento_Aluguel from Contem where ID_Pedido = " + idPedido + ";";
//
//            Statement st = ConexaoMySQL.getConexaoMySQL().createStatement();
//
//            ResultSet rs = st.executeQuery(query);
//
//            while (rs.next()) {
//                dataVencimentoAluguel = rs.getString("Data_Vencimento_Aluguel");
//            }
//
//            st.close();
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
//
//        return dataVencimentoAluguel;
//    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Dados do pedido:\n");
        sb.append("ID do pedido: #");
        sb.append(idPedido);
        sb.append("\n");
        sb.append("Tipo do pedido: ");
        sb.append(pegaDadosTipoPedido(tipoPedido));
        sb.append("Nome do cliente: ");
        sb.append(pegaNomeCliente(Usuario.getIdUsuarioLogado()));
        sb.append("\n");
        sb.append("Itens do pedido: ");
        sb.append("\n");
        sb.append(mostraItensPedido(tipoPedido));
        sb.append("\n");
        sb.append("Valor do pedido: R$ ");
        sb.append(valorPedido(tipoPedido));
        sb.append("\n");
        sb.append("Método de pagamento: ");
        sb.append(metodoPagamento);
        sb.append("\n");

        return sb.toString();
    }

}
