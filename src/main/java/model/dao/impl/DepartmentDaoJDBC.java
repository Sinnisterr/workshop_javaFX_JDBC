package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbExceptions;
import db.DbIntegrityException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO department (Name) VALUES (?)",
                    java.sql.Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
                conn.commit(); // Adicione o commit aqui
            } else {
                throw new DbExceptions("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Em caso de erro, faça rollback
                throw new DbExceptions("Transaction rolled back! Caused by: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbExceptions("Error trying to rollback! Caused by: " + e1.getMessage());
            }
        } finally {
            DB.closeStatement(st);
        }
    }


    public void update(Department obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE department SET Name = ? WHERE Id = ?");

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            st.executeUpdate();
            conn.commit(); // Adicione o commit aqui

        } catch (SQLException e) {
            try {
                conn.rollback(); // Em caso de erro, faça rollback
                throw new DbExceptions("Transaction rolled back! Caused by: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbExceptions("Error trying to rollback! Caused by: " + e1.getMessage());
            }
        } finally {
            DB.closeStatement(st);
        }
    }


    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM department WHERE Id = ? ");

            st.setInt(1, id);
            st.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer departmentId) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT * FROM department WHERE Id = ?");

            st.setInt(1, departmentId);
            rs = st.executeQuery();

            if(rs.next()) {

                Department dep = instantiateDepartment(rs);
                return dep;
            }
            return null;

        } catch (SQLException e) {
            throw new DbExceptions(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM department");
            rs = st.executeQuery();

            List<Department> departments = new ArrayList<>();
            while (rs.next()) {
                Department dep = instantiateDepartment(rs);
                departments.add(dep);
            }
            return departments;
        } catch (SQLException e) {
            throw new DbExceptions(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {

        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));

        return dep;
    }

}