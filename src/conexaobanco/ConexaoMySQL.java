//Nome do nosso pacote //
package conexaobanco;

//Classes necessárias para uso de Banco de dados //
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//Início da classe de conexão//
public class ConexaoMySQL {

             public static String status = "Não conectou...";

//Método Construtor da Classe//
        public ConexaoMySQL() {

    }



//Método de Conexão//
public static java.sql.Connection getConexaoMySQL() {

        Connection connection = null;          //atributo do tipo Connection



try {

// Configurando a nossa conexão com um banco de dados//

        String serverName = "127.0.0.1";    //caminho do servidor do BD

        String mydatabase ="trabalhoesof";        //nome do seu banco de dados

        String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

        String username = "root";        //nome de um usuário de seu BD

        String password = "Farinha123@";      //sua senha de acesso

        connection = DriverManager.getConnection(url, username, password);



        //Testa sua conexão//
        if (connection != null) {

            status = ("STATUS BANCO DE DADOS--->Conectado com sucesso!");
            System.out.println();

        } else {

            status = ("STATUS--->Não foi possivel realizar conexão");

        }



        return connection;



        } catch (SQLException e) {

//Não conseguindo se conectar ao banco

            System.out.println("Não foi possivel conectar ao Banco de Dados.");

            return null;

        }



    }



    //Método que retorna o status da sua conexão//
    public static String statusConection() {

        return status;

    }



   //Método que fecha sua conexão//
    public static boolean FecharConexao() {

        try {

            ConexaoMySQL.getConexaoMySQL().close();

            return true;

        } catch (SQLException e) {

            return false;

        }



    }



   //Método que reinicia sua conexão//
    public static java.sql.Connection ReiniciarConexao() {

        FecharConexao();

        return ConexaoMySQL.getConexaoMySQL();

    }

}
