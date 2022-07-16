package entities;

import java.sql.PreparedStatement;

import conexaobanco.ConexaoMySQL;
import java.sql.SQLException;
import java.util.Random;

public class Filme {

    private Integer idFilme;
    private final String titulo;
    private final String genero;
    private final Integer anoLancamento;
    private final String duracao;
    private Double precoCompra;
    private Double precoAluguel;

    public Filme(String titulo, String genero, Integer anoLancamento, String duracao, Double precoCompra, Double precoAluguel) {
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.duracao = duracao;
        this.precoCompra = precoCompra;
        this.precoAluguel = precoAluguel;

        iniciaParametrosSistema();
    }

    public Integer getIdFuncionario() {
        return idFilme;
    }

    public Integer getIdFilme() {
        return idFilme;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public String getDuracao() {
        return duracao;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public Double getPrecoAluguel() {
        return precoAluguel;
    }

    private void iniciaParametrosSistema() {
        Random gerador = new Random();
        this.idFilme = Integer.parseInt(("" + System.currentTimeMillis() + ""
                + gerador.nextInt(100)).substring(0, 9));
    }

    public static boolean SalvarFilme(Filme f) {

        try {
            String query = "INSERT INTO Filme(ID_Filme,Titulo,Genero,"
                    + "Ano_Lancamento,Duracao,Preco_Compra,Preco_Aluguel)"
                    + " values (?,?,?,?,?,?,?)";

            PreparedStatement preparedStmt = ConexaoMySQL.getConexaoMySQL().prepareStatement(query);
            preparedStmt.setInt(1, f.getIdFilme());
            preparedStmt.setString(2, f.getTitulo());
            preparedStmt.setString(3, f.getGenero());
            preparedStmt.setInt(4, f.getAnoLancamento());
            preparedStmt.setString(5, f.getDuracao());
            preparedStmt.setDouble(6, f.getPrecoCompra());
            preparedStmt.setDouble(7, f.getPrecoAluguel());

            preparedStmt.execute();

            ConexaoMySQL.getConexaoMySQL().close();
        } catch (SQLException e) {

            System.err.println(e.getMessage());
            return false;
        }

        return true;

    }

}
