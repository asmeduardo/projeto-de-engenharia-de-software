package application;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import conexaobanco.ConexaoMySQL;
import entities.Administrador;
import entities.Cliente;
import entities.Usuario;

public class Program {

    public static void main(String[] args) throws IOException, ParseException {

        Scanner in = new Scanner(System.in);

        ConexaoMySQL.getConexaoMySQL();

        System.out.println(ConexaoMySQL.statusConection());

        int opcao = 0;
        boolean flag = false;

        do {
            System.out.println();
            System.out.println("##ESCOLHA UMA OPÇÃO##\n");
            System.out.println("1 - Administrador");
            System.out.println("2 - Cliente");
            System.out.println("3 - Sair");
            opcao = in.nextInt();

            switch (opcao) {

                case 1 -> {
                    System.out.print("Você já possui um cadastro (s/n)? ");
                    char possuiCadastro = in.next().charAt(0);

                    if (possuiCadastro == 's' || possuiCadastro == 'S') {

                        boolean logado = Usuario.fazerLogin(1);

                        if (logado) {

                            System.out.println("Você está logado no sistema");
                            System.out.println();

                        } else {

                            do {
                                System.out.println("Seus dados estão incorretos, tente novamente:");

                                logado = Usuario.fazerLogin(1);

                            } while (logado != true);

                            System.out.println("Você está logado no sistema");
                        }

                        Administrador.funcoesAdm();

                        flag = true;

                    } else {

                        Usuario.cadastrarUsuario(1);

                        boolean logado = Usuario.fazerLogin(1);

                        if (logado) {

                            System.out.println("Você está logado no sistema");
                            System.out.println();

                        } else {

                            do {
                                System.out.println("Seus dados estão incorretos, tente novamente:");

                                logado = Usuario.fazerLogin(1);

                            } while (logado != true);

                            System.out.println("Você está logado no sistema");
                        }

                        Administrador.funcoesAdm();

                        flag = true;
                    }
                }

                case 2 -> {
                    System.out.print("Você já possui um cadastro (s/n)? ");
                    char possuiCadastro = in.next().charAt(0);
                    if (possuiCadastro == 's' || possuiCadastro == 'S') {

                        boolean logado = Usuario.fazerLogin(0);

                        if (logado) {
                            
                            System.out.println("Você está logado no sistema");
                            System.out.println();
                            
                        } else {

                            do {
                                System.out.println("Seus dados estão incorretos, tente novamente:");

                                logado = Usuario.fazerLogin(0);

                            } while (logado != true);

                            System.out.println("Você está logado no sistema");

                        }
                        
                        Cliente.funcoesCliente();

                        flag = true;

                    } else {

                        Usuario.cadastrarUsuario(0);

                        boolean logado = Usuario.fazerLogin(0);

                        if (logado) {

                            System.out.println("Você está logado no sistema");
                            System.out.println();

                        } else {

                            do {
                                System.out.println("Seus dados estão incorretos, tente novamente:");

                                logado = Usuario.fazerLogin(0);

                            } while (logado != true);

                            System.out.println("Você está logado no sistema");

                        }
                        
                        Cliente.funcoesCliente();

                        flag = true;
                        
                    }
                }
            }

        } while (opcao != 3 && flag == false);

        System.out.println("O programa foi finalizado!");

        in.close();

    }
}
