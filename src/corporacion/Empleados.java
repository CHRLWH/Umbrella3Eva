package corporacion;

import java.time.LocalDate;
import utilidades.*;
/**
 * @author Aitor Pascual Jiménez
 * @author Carlos Hernández Herrador
 * @version 0.1
 * @since Java 8.0
 */

public class Empleados {
    private String codEmpleado;
    private Dni dniEmpleado;
    private String nombre;
    private String apellido;
    private String departamento;
    private double sueldo;
    private LocalDate fechaNacimiento;
    private LocalDate fechaContrato;
    
    private static final String NOMBRE_EMPRESA = "Umbrella Corporation";
    private static final LocalDate FECHA_CREACION_EMPRESA = LocalDate.of(1998,2,25);
    private static double ayudaComida;
    private static double vigencia = 20;
    private static int contadorEmpleado = 0; //aumenta con cada instancia

    Empleados (String codEmpleado){}

}
