package application;

import db.DB;
import db.DbExceptions;

import java.sql.Connection;

public class Main2 {
    public static void main(String[] args) {
               try {
           Connection conn = DB.getConnection();
           System.out.println("Conexão estabelecida com sucesso!");
        } catch (DbExceptions e) {
           System.err.println("Erro ao conectar: " + e.getMessage());
        } finally {
           DB.closeConnection();
        }
   }
    }

