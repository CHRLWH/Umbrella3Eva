package corporacion;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import utilidades.*;

/**
 * @author Aitor Pascual Jiménez
 * @author Carlos Hernández Herrador
 * @version 0.1
 * @since Java 8.0
 */

public class Empleados implements paraEmpleado {
    //atribs empleado
    private String codEmpleado;
    private Dni dniEmpleado;
    private String nombre;
    private String apellido;
    private String departamento;
    private double sueldoAnual;
    private LocalDate fechaNacimiento;
    private LocalDate fechaContrato;

    //atribs globales
    private static final String NOMBRE_EMPRESA = "UMBRELLA CORP";
    private static final LocalDate FECHA_CREACION_EMPRESA = LocalDate.of(2016,5,2);
    private static double ayudaComida = 110; //ayuda mensual a la comida
    private static double vigencia = 20; //20 por año
    private static int contadorEmpleado = 0; //aumenta con cada instancia

    Empleados (Dni dniEmpleado, String nombre, String apellido, String departamento, double sueldoAnual, LocalDate fechaNacimiento, LocalDate fechaContrato){

        this.codEmpleado = generarCodEmpleado();
        this.dniEmpleado = dniEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.departamento = departamento;
        this.sueldoAnual = sueldoAnual;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaContrato = fechaContrato;

    }




    @Override
    public float calcularSueldo() {
        return 0;
    }


    @Override
    public int calcularAntiguedadAnios() {
        return (int) ChronoUnit.YEARS.between(fechaContrato, LocalDate.now());
    }

    /**
     *
     * @return devuelve true si el mes de ncaimiento es el actuál
     */
    @Override
    public boolean esteMesCumpleAniosEmpleado() {
        return (LocalDate.now().getMonth() == fechaNacimiento.getMonth());
    }

    public String mostrarDatos(){
        return "Codigo= "+dniEmpleado.getNumeroDNI()+
                "\nNombre= "+nombre+"\nApellido= "+apellido +
                "\nDepartamento= "+departamento+
                "\nAños en la empresa= "+calcularAntiguedadAnios()+
                "\nEdad= "+ Period.between(fechaNacimiento, LocalDate.now()).getYears() +
                "\nFecha de contrato= "+ fechaContrato.format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy"));
    }

    private String generarCodEmpleado () {
        contadorEmpleado++;
        String cabecera = "UMBRE";
        String codigo = String.format("%04d", contadorEmpleado); //añade 0 a la izquierda hasta llegar a 4 dígitos en caso de necesitarlo
        return cabecera+codigo;
    }
}
