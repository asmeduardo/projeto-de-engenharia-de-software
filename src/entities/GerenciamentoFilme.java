package entities;

import conexaobanco.ConexaoMySQL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GerenciamentoFilme {

    private Integer idGerenciamento;
    private final String dataGerenciamento;
    private String descricao;
    private Integer idUsuario;
    private Integer idFilme;

    public GerenciamentoFilme(String descricao, Integer idUsuario, Integer idFilme) {
        this.descricao = descricao;
        this.idUsuario = idUsuario;
        this.idFilme = idFilme;
        this.dataGerenciamento = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        
        iniciaParametrosSistema();
    }

    public Integer getIdGerenciamento() {
        return idGerenciamento;
    }

    public String getDataGerenciamento() {
        return dataGerenciamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Integer idFilme) {
        this.idFilme = idFilme;
    }
    
    public void iniciaParametrosSistema() {
        Random gerador = new Random();
        this.idGerenciamento = Integer.parseInt(("" + System.currentTimeMillis() + ""
                + gerador.nextInt(100)).substring(0, 10));
    }
    
    public static void salvarGerenciamento(GerenciamentoFilme g) {
    try {
            String query = "INSERT INTO Gerencia(Data,Descricao,ID_Usuario,ID_Filme) values (?,?,?,?)";

            PreparedStatement preparedStmt = ConexaoMySQL.getConexaoMySQL().prepareStatement(query);
            preparedStmt.setInt(1, g.getIdGerenciamento());
            preparedStmt.setString(2, g.getDataGerenciamento());
            preparedStmt.setString(3, g.getDescricao());
            preparedStmt.setInt(4, g.getIdUsuario());
            preparedStmt.setInt(5, g.getIdFilme());
            
            preparedStmt.execute();

            ConexaoMySQL.getConexaoMySQL().close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
