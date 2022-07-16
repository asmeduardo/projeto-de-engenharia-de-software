package entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import conexaobanco.ConexaoMySQL;
import entities.enums.GeneroUsuario;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Administrador extends Usuario {

    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    static Scanner in = new Scanner(System.in);

    public Administrador(String nome, String cpf, GeneroUsuario sexo, String dataNascimento, String email, String senha, Integer tipoUsuario, Double salario) {
        super(nome, cpf, sexo, dataNascimento, email, senha, tipoUsuario, salario);
    }

    public static void salvarAdm(Administrador a) {

        try {
            String query = "INSERT INTO Usuario_Administrador_Cliente(ID_Usuario,"
                    + "Nome,CPF,Sexo,Data_Nascimento,Email,Senha,Tipo_Usuario,Salario) "
                    + "values (?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStmt = ConexaoMySQL.getConexaoMySQL().prepareStatement(query);
            preparedStmt.setInt(1, a.getIdUsuario());
            preparedStmt.setString(2, a.getNome());
            preparedStmt.setString(3, a.getCpf());
            preparedStmt.setString(4, a.getSexo().name());
            preparedStmt.setString(5, a.getDataNascimento());
            preparedStmt.setString(6, a.getEmail());
            preparedStmt.setString(7, a.getSenha());
            preparedStmt.setInt(8, a.getTipoUsuario());
            preparedStmt.setDouble(9, a.getSalario());

            preparedStmt.execute();

            ConexaoMySQL.getConexaoMySQL().close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void funcoesAdm() {
        int opcao;

        do {
            System.out.println();
            System.out.println("##ESCOLHA UMA OPÇÃO##\n");
            System.out.println("1 - Cadastrar filme (primeira sprint)");
            System.out.println("2 - Excluir filme (primeira sprint)");
            System.out.println("3 - Listar filmes cadastrados (primeira sprint)");
            System.out.println("4 - Sair");
            opcao = in.nextInt();
            in.nextLine();

            if (opcao == 1) {

                cadastrarFilme();
            }

            if (opcao == 2) {

                System.out.print("Qual o ID do filme? ");
                int idFilme = in.nextInt();

                boolean excluido = excluirFilme(idFilme);

                if (excluido) {

                    System.out.println("O filme foi excluído com sucesso.");

                } else {

                    System.out.print("Não há filme cadastrado no sistema com este ID");
                    System.out.println();
                }
            }

            if (opcao == 3) {
                System.out.println();
                System.out.println("Filmes cadastrados:");

                listarFilmes();
                System.out.println();
            }

        } while (opcao != 4);

    }

    public static void cadastrarFilme() {
        System.out.println();
        System.out.println("Cadastrar novo filme:");

        System.out.print("Título: ");
        String titulo = in.nextLine();

        System.out.print("Gênero: ");
        String genero = in.nextLine();

        System.out.print("Ano de lançamento: ");
        Integer anoLancamento = in.nextInt();

        System.out.print("Duração: ");
        String duracao = in.next();

        System.out.print("Preço para compra: ");
        Double precoCompra = in.nextDouble();

        System.out.print("Preço para aluguel: ");
        Double precoAluguel = in.nextDouble();

        Filme filme = new Filme(titulo, genero, anoLancamento, duracao, precoCompra, precoAluguel);

        Filme.SalvarFilme(filme);

        GerenciamentoFilme gerenciaFilmes = new GerenciamentoFilme("Adicionou um filme ao sistema.", Usuario.getIdUsuarioLogado(), filme.getIdFilme());

        GerenciamentoFilme.salvarGerenciamento(gerenciaFilmes);
    }

    public static boolean excluirFilme(int idFilme) {

        try {
            String queryExcluir = "DELETE FROM Filme WHERE ID_Filme = " + idFilme + ";";
            String querySelect = "SELECT ID_Filme FROM Filme;";

            Statement st = ConexaoMySQL.getConexaoMySQL().createStatement();

            ResultSet rs = st.executeQuery(querySelect);

            while (rs.next()) {
                if (rs.getInt("ID_Filme") == idFilme) {
                    st.executeUpdate(queryExcluir);
                    return true;
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        GerenciamentoFilme gerenciaFilmes = new GerenciamentoFilme("Excluiu um filme do sistema.", Usuario.getIdUsuarioLogado(), idFilme);

        GerenciamentoFilme.salvarGerenciamento(gerenciaFilmes);

        return true;

    }

    public static void listarFilmes() {

        try {
            String query = "SELECT * FROM Filme;";

            Statement st = ConexaoMySQL.getConexaoMySQL().createStatement();

            ResultSet rs = st.executeQuery(query);

            if (!rs.isBeforeFirst()) {
                System.out.println("Nenhum filme cadastrado.");
            } else {
                while (rs.next()) {
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
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
}
