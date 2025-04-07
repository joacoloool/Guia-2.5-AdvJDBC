package org.joaco.service;

import org.joaco.model.entities.CuentaEntity;
import org.joaco.repository.classes.CuentaRepository;
import org.joaco.service.I.Service;

import java.sql.SQLException;
import java.util.List;

public class CuentaService implements Service<CuentaEntity> {

    private final static CuentaService instance = new CuentaService();
    private final static CuentaRepository instanceRepo = CuentaRepository.getInstance();

    private CuentaService() {
    }

    public static CuentaService getInstance()
    {
        return instance;
    }

    @Override
    public void save(CuentaEntity cuentaEntity) {
        if (cuentaEntity.getTipoCuenta() != null) {
            try {
                instanceRepo.save(cuentaEntity);

            }catch (SQLException e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }
        throw new IllegalArgumentException("El tipo de cuenta no esta siendo definido");
    }

    @Override
    public CuentaEntity searchById(int id) {
        if (id >= 0)
        {
            try {
                return instanceRepo.searchById(id);
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }
        throw new IllegalArgumentException("El id no es valido");
    }

    @Override
    public List<CuentaEntity> searchAll() {
       try {
         return instanceRepo.searchAll();
       }
       catch (SQLException e)
       {
           throw new RuntimeException(e.getMessage());
       }
    }

    @Override
    public void update(CuentaEntity cuentaEntity) {
        if (searchAll().contains(cuentaEntity)) {
            try {
                instanceRepo.update(cuentaEntity);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        throw new IllegalArgumentException("El usuario no existe");
    }

    @Override
    public void delete(int id) {
        if (searchAll().contains(searchById(id))) {
            try {
                instanceRepo.delete(id);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        throw new IllegalArgumentException("El usuario no existe");
    }
}
