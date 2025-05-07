package model.services;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private final DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        return dao.findAll();
    }

    public Department saveOrUpdate(Department obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
        return dao.findById(obj.getId());
    }

    public void remove(Department obj) {

        dao.deleteById(obj.getId());
    }


}

