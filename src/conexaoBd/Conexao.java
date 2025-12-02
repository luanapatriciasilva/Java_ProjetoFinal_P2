package conexaoBd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {


    private static final String URL = "jdbc:mysql://localhost:3306/sage_db";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static Connection GeraConexao() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco: " + e.getMessage());
        }
    }
}