package Conexao;

import java.sql.DriverManager;
import java.sql.Connection;

public class Conexao {

	final static String NOME_DO_BANCO = "helpdesk";
    public static Connection conectar() {
    	try {

    		try {
    			Class.forName("com.mysql.jdbc.Driver");
    		} catch (ClassNotFoundException e1) {

    			try {
    				Class.forName("com.mysql.cj.jdbc.Driver");
    			} catch (ClassNotFoundException e2) {
    				System.out.println("ERRO Conexao: Nenhum driver MySQL encontrado!");
    				System.out.println("Por favor, baixe o mysql-connector-java-5.1.49.jar ou mysql-connector-java-8.x.jar");
    				System.out.println("e coloque na pasta lib/ do projeto.");
    				throw new ClassNotFoundException("Driver MySQL não encontrado");
    			}
    		}
    		
            String url = "jdbc:mysql://localhost/" + NOME_DO_BANCO + "?autoReconnect=true&useSSL=false&connectTimeout=10000&serverTimezone=UTC";
            System.out.println("DEBUG Conexao: Tentando conectar em " + url);
            Connection conn = DriverManager.getConnection(url,"root","");
            System.out.println("DEBUG Conexao: Conexão OK!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("ERRO Conexao: Driver MySQL não encontrado!");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
        	System.out.println("ERRO Conexao: Falha ao conectar ao banco de dados!");
        	System.out.println("Verifique se o MySQL está rodando e se as credenciais estão corretas.");
        	e.printStackTrace();
            return null;
        }
    }
}

