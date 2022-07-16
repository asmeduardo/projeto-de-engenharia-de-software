package entities;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import conexaobanco.ConexaoMySQL;
import entities.enums.GeneroUsuario;
import java.sql.SQLException;
import java.util.Random;

public class Usuario {

    static Scanner in = new Scanner(System.in);

    private Integer idUsuario;
    private String nome;
    private final String cpf;
    private GeneroUsuario sexo;
    private final String dataNascimento;
    private final String email;
    private String senha;
    private Integer tipoUsuario;
    private Double salario;
    private static Integer idUsuarioLogado;

    public Usuario(String nome, String cpf, GeneroUsuario sexo, String dataNascimento, String email, String senha, Integer tipoUsuario) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;

        iniciaParametrosSistema();
    }
    
    public Usuario(String nome, String cpf, GeneroUsuario sexo, String dataNascimento, String email, String senha, Integer tipoUsuario, Double salario) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.salario = salario;

        iniciaParametrosSistema();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public GeneroUsuario getSexo() {
        return sexo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }
    
    public Double getSalario() {
        return salario;
    }

    public static Integer getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    public static void setIdUsuarioLogado(Integer idUsuarioLogado) {
        Usuario.idUsuarioLogado = idUsuarioLogado;
    }

    private void iniciaParametrosSistema() {
        Random gerador = new Random();
        this.idUsuario = Integer.parseInt(("" + System.currentTimeMillis() + ""
                + gerador.nextInt(100)).substring(0, 10));
    }

    public static void cadastrarUsuario(int tipoUsuario) {

        Scanner in = new Scanner(System.in);

        System.out.println("\nFazer cadastro:");

        System.out.print("Nome: ");
        String nome = in.nextLine();

        System.out.print("CPF: ");
        String cpf = in.next();

        System.out.print("Sexo (M/F): ");
        GeneroUsuario sexo = GeneroUsuario.valueOf(in.next().toUpperCase());

        System.out.print("Data de nascimento: ");
        String dataNascimento = in.next();

        System.out.print("E-mail: ");
        String email = in.next();

        System.out.print("Senha: ");
        String senha = in.next();

        if (tipoUsuario == 0) {
            Cliente.salvarCliente(new Cliente(nome, cpf, sexo, dataNascimento, email, senha, tipoUsuario));
        } else {
            System.out.print("Salário: ");
            Double salario = in.nextDouble();
            
            Administrador.salvarAdm(new Administrador(nome, cpf, sexo, dataNascimento, email, senha, tipoUsuario, salario));
        }

        System.out.print("Usuário cadastrado com sucesso.");

    }

    public static boolean fazerLogin(int tipoUsuario) {
        System.out.println();
        System.out.println("\nFazer login:");

        System.out.print("E-mail: ");
        String email = in.next();

        System.out.print("Senha: ");
        String senha = in.next();

        boolean verificaDados = autenticarLogin(email, senha, tipoUsuario);

        return verificaDados;
    }

    public static boolean autenticarLogin(String email, String senha, Integer tipoUsuario) {

        try {
            String query = null;

            if (tipoUsuario == 0) {
                query = "SELECT id_usuario,email,senha FROM Usuario_Administrador_Cliente "
                        + "WHERE Tipo_Usuario = " + tipoUsuario + ";";
            } else {
                query = "SELECT id_usuario,email,senha FROM Usuario_Administrador_Cliente "
                        + "WHERE Tipo_Usuario = " + tipoUsuario + ";";
            }

            Statement st = ConexaoMySQL.getConexaoMySQL().createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String emailUsuario = rs.getString("Email");
                String senhaUsuario = rs.getString("Senha");

                if (emailUsuario.equals(email) && senhaUsuario.equals(senha)) {
                    Usuario.setIdUsuarioLogado(rs.getInt("ID_Usuario"));
                    st.close();
                    return true;
                }
            }
            st.close();
            return false;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}
