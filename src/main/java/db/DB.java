package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    // Método abrir conexão banco dados
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbExceptions(e.getMessage());
            }
        }
        return conn;
    }

    // Método para fechar conexão banco dados
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbExceptions(e.getMessage());

            }
        }
    }

    private static Properties loadProperties() {
        try {
            String path = "src/main/resources/db.properties";
            Properties props = new Properties();
            try (FileInputStream fs = new FileInputStream(path)) {
                props.load(fs);
                return props;
            }
        } catch (IOException e) {
            throw new DbExceptions("Erro ao carregar arquivo de propriedades: " + e.getMessage());
        }
    }


    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbExceptions(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet  rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbExceptions(e.getMessage());
            }
        }
    }

}