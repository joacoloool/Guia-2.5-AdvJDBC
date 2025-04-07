package org.joaco.model.entities;
import lombok.*;
import org.joaco.model.enums.TipoCuenta;
import java.sql.Date;
@Getter
@Setter
@ToString
@Builder
public class CuentaEntity {

    private int id;
    private int id_usuario;
    private Date fechaCreacion;
    private Double saldo;
    private TipoCuenta tipoCuenta;
}
