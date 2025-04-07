package org.joaco.service;

import org.joaco.model.entities.UsuarioEntity;
import org.joaco.repository.classes.UsuarioRepository;
import org.joaco.service.I.Service;

import java.sql.SQLException;
import java.util.*;

public class UsuarioService implements Service<UsuarioEntity> {

    private final static UsuarioService instance = new UsuarioService();
    private final static UsuarioRepository instanceRepoUs = UsuarioRepository.getInstance();

    private UsuarioService() {
    }

    public List<String> genereteUserPass(int id)
    {
        ArrayList<String> usuarioEntities = new ArrayList<>();
        String username;
        String password;
            if (searchById(id).getNombre() != null || !searchById(id).getNombre().isBlank())
            {
                username = searchById(id).getEmail();
               username = username.substring(0, username.indexOf("@"));
                int random = (int)(Math.random()*2324343);
                username = username + random;
                usuarioEntities.add(username);
                password = UUID.randomUUID().toString();
                System.out.println(password);

            }

        return usuarioEntities;
    }
    public static UsuarioService  getInstance()
    {
        return instance;
    }
    @Override
    public void save(UsuarioEntity usuarioEntity)
    {
        if (usuarioEntity.getNombre() != null && !usuarioEntity.getNombre().isBlank())
        {
            try {
                instanceRepoUs.save(usuarioEntity);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
        throw new IllegalArgumentException("El nombre no puede estar vacio");
    }

    @Override
    public UsuarioEntity searchById(int id) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
         if (id >= 0)
          {
              try {
                 usuarioEntity = instanceRepoUs.searchById(id);
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }
          }
        return usuarioEntity;
    }

    @Override
    public List<UsuarioEntity> searchAll() {
        try {
            return instanceRepoUs.searchAll();
        }
        catch (SQLException e)
        {
            System.err.println("No existen usuarios cargados "+ e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void update(UsuarioEntity usuarioEntity) {
        if (instance.searchById(usuarioEntity.getId()).getNombre()!= null)
        {
            try {
                instanceRepoUs.update(usuarioEntity);
            } catch (SQLException e) {
                System.err.println("No se ha actualizado al usuario" + e.getMessage());
                throw new RuntimeException();
            }
        }
        throw new IllegalArgumentException("Este usuario no existe");
    }

    @Override
    public void delete(int id) {
        if (instance.searchById(id).getNombre()!= null)
        {
            try {
                instanceRepoUs.delete(id);
            } catch (SQLException e) {
                System.err.println("No se ha borrado al usuario" + e.getMessage());
                throw new RuntimeException();
            }
        }
        throw new IllegalArgumentException("Este usuario no existe");
    }


}
