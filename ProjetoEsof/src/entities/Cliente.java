package entities;

import java.sql.PreparedStatement;
import conexaobanco.ConexaoMySQL;
import static entities.Administrador.in;
import entities.enums.GeneroUsuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Cliente extends Usuario {

    static Scanner sc = new Scanner(System.in);

    public Cliente(String nome, String cpf, GeneroUsuario sexo, String dataNascimento, String email, String senha, Integer tipoUsuario) {
        super(nome, cpf, sexo, dataNascimento, email, senha, tipoUsuario);
    }

    public static void salvarCliente(Cliente c) {

        try {
            String query = "INSERT INTO Usuario_Administrador_Cliente(ID_Usuario,"
                    + "Nome,CPF,Sexo,Data_Nascimento,Email,Senha,Tipo_Usuario)"
                    + " values (?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStmt = ConexaoMySQL.getConexaoMySQL().prepareStatement(query);
            preparedStmt.setInt(1, c.getIdUsuario());
            preparedStmt.setString(2, c.getNome());
            preparedStmt.setString(3, c.getCpf());
            preparedStmt.setString(4, c.getSexo().name());
            preparedStmt.setString(5, c.getDataNascimento());
            preparedStmt.setString(6, c.getEmail());
            preparedStmt.setString(7, c.getSenha());
            preparedStmt.setInt(8, c.getTipoUsuario());

            preparedStmt.execute();

            ConexaoMySQL.getConexaoMySQL().close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void funcoesCliente() {
        int opcao;

        do {
            System.out.println("");
            System.out.println("##ESCOLHA UMA OPÇÃO##\n");
            System.out.println("1 - Listar os filmes por categoria (segunda sprint)");
            System.out.println("2 - Comprar um filme (segunda sprint)");
            System.out.println("3 - Alugar um filme (segunda sprint)");
            System.out.println("4 - Sair");
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1 ->
                    listarFilmesPorCategoria();
                case 2 ->
                    comprarFilme();
                case 3 ->
                    alugarFilme();
                default -> {
                }
            }

        } while (opcao != 4);

    }

    public static void listarFilmesPorCategoria() {
        try {

            String query = "SELECT * FROM Filme ORDER BY Genero" + ";";

            Statement st = ConexaoMySQL.getConexaoMySQL().createStatement();

            ResultSet rs = st.executeQuery(query);

            String generoAtual = "";

            while (rs.next()) {
                if (!generoAtual.equals(rs.getString("Genero"))) {
                    generoAtual = rs.getString("Genero");
                    System.out.println(generoAtual + ":");
                }
                System.out.println("ID = " + rs.getInt("ID_Filme")
                        + ", Título = " + rs.getString("Titulo")
                        + ", Gênero = " + rs.getString("Genero")
                        + ", Ano de lançamento = "
                        + rs.getInt("Ano_Lancamento")
                        + ", Duração = "
                        + rs.getString("Duracao")
                        + ", Preço para compra = "
                        + rs.getDouble("Preco_Compra")
                        + ", Preço para aluguel = "
                        + rs.getDouble("Preco_Aluguel"));
            }

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void comprarFilme() {
        System.out.print("Qual o ID do filme: ");
        int idFilme = sc.nextInt();

        Pedido pedido = new Pedido();

        pedido.setidCliente(Usuario.getIdUsuarioLogado());
        pedido.setProdutos(new ItemPedido(pedido.getIdPedido(), idFilme));
        pedido.setTipoPedido(0);
        
        Pedido.salvarPedido(pedido);
        
        System.out.println(pedido);
    }

    public static void alugarFilme() {
        System.out.print("Qual o ID do filme: ");
        int idFilme = sc.nextInt();

        Pedido pedido = new Pedido();

        pedido.setidCliente(Usuario.getIdUsuarioLogado());
        pedido.setProdutos(new ItemPedido(pedido.getIdPedido(), idFilme));
        pedido.setTipoPedido(1);
        
        System.out.println(pedido);
    }

}
