package model.services;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class SellerService {

    private final SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {

        return dao.findAll();
    }

    public Seller saveOrUpdate(Seller obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
        return dao.findById(obj.getId());
    }

    public void remove(Seller obj) {

        dao.deleteById(obj.getId());
    }


}

