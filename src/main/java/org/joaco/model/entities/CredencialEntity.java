package org.joaco.model.entities;
import lombok.*;
import org.joaco.model.enums.Permiso;
@Getter
@Setter
@ToString
@Builder
public class CredencialEntity {
    private int id;
    private int id_usuario;
    private String usuario;
    private String password;
    private Permiso permiso;

}
