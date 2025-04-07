package org.joaco.model.entities;
import lombok.*;
import org.joaco.model.enums.TipoCuenta;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private Date fechaCreacion;
    private HashMap<TipoCuenta, HashSet<CuentaEntity>> cuentas;




}
