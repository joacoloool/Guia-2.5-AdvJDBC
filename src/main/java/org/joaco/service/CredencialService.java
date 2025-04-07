package org.joaco.service;

import org.joaco.model.entities.CredencialEntity;
import org.joaco.repository.classes.CredencialRepository;
import org.joaco.service.I.Service;

import java.sql.SQLException;
import java.util.List;


public class CredencialService implements Service<CredencialEntity> {

    private static final CredencialService instance = new CredencialService();
    private static final CredencialRepository instanceRepo = new CredencialRepository();

    private CredencialService() {
    }


    @Override
    public void save(CredencialEntity credencialEntity) {
        if (credencialEntity.getPermiso() !=null) {
            List<String> strings = UsuarioService.getInstance().genereteUserPass(credencialEntity.getId_usuario());
            try {
                credencialEntity.setUsuario(strings.getFirst());
                credencialEntity.setPassword(strings.getLast());
                instanceRepo.save(credencialEntity);
            } catch (SQLException e) {
                System.err.println("No se ha podido añadir este usuario");
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public CredencialEntity searchById(int id) {
        if (id >= 0) {
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
    public List<CredencialEntity> searchAll() {
        try {
           return instanceRepo.searchAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(CredencialEntity credencialEntity) {
        if (searchAll().contains((credencialEntity)))
        {
            try {
                instanceRepo.update(credencialEntity);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("el usuario que intenta actualizar no existe");
    }

    @Override
    public void delete(int id) {
        if (searchAll().contains(searchById(id)))
        {
            try {
                instanceRepo.delete(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("el usuario que intenta borrar no existe");
    }
}
