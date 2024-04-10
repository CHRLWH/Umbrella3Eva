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
    private final String nombre;
    private final String apellido;
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

    Empleados (String dniEmpleado, String nombre, String apellido, String departamento, double sueldoAnual, LocalDate fechaNacimiento, LocalDate fechaContrato){

        this.codEmpleado = generarCodEmpleado();

        if (Dni.validarNIF(dniEmpleado)) {
            this.dniEmpleado = new Dni(Integer.parseInt(dniEmpleado));
        } else {
            throw new IllegalArgumentException("[!] El Dni no es válido");
        }

        this.nombre = nombre;
        this.apellido = apellido;
        this.departamento = departamento;

        if (sueldoAnual > 10000) {
            this.sueldoAnual = sueldoAnual;
        } else throw new IllegalArgumentException("[!] Los empleados deben poder comer");

        this.fechaNacimiento = fechaNacimiento;

        if (fechaContrato.isBefore(fechaNacimiento)){
            throw new IllegalArgumentException("[!] No puedes contratar a alguien que no ha nacido aún");
        } else if (fechaContrato.isBefore(FECHA_CREACION_EMPRESA)) {
            throw new IllegalArgumentException("[!] No puedes contratar a alguien cuando la empresa no existía");
        } else this.fechaContrato = fechaContrato;

    }

    /**
     * <h4>Calcula el salario mensual del empleado siguiendo este criterio:</h4> <br>
     *
     * salario mensual en 12 pagas<br>
     * 20 euros extra por año<br>
     * 110 euros extra por ayuda alimentaria<br>
     * 50 euros extra si este mes es el cumpleaños del trabajador<br>
     *
     * @param mostrar si es true muestra los detalles por pantalla
     *
     * @return devuelve el salario mensual
     */
    @Override
    public double calcularSueldoMensual(boolean mostrar) {
        double salarioMensualBase = sueldoAnual / 12;
        double plusAnios = vigencia * calcularAntiguedadAnios();
        int plusCumpleanios = esteMesCumpleAniosEmpleado() ? 50 : 0; //50 euros extra si este mes el empleado cumple años

        double salarioEsteMes = salarioMensualBase + plusAnios + plusCumpleanios + ayudaComida;

        if (mostrar) { //si se elige mostrar, se muestran todos los cálculos por pantalla
            System.out.println("[+] Mostrando detalles del salario:\n");
            System.out.printf("\tSalario mensual base = %2d" + salarioMensualBase + "%n");
            System.out.printf("\tPlus por antigüedad = %2d" + plusAnios + "%n");
            System.out.printf("\tPlus por ayuda alimentaria = %2d" + ayudaComida + "%n");
            if (plusCumpleanios > 0) {
                System.out.printf("\tPlus por cumpleaños = %2d" + plusCumpleanios + "%n");
            }
            System.out.printf("\n\nSalario mensual total del empleado = %2d"+salarioEsteMes);
        }

        return salarioEsteMes;
    }

    /**
     * Calcula la diferencia en años entre la fecha de contrato y el día de hoy
     * @return diferencia de años
     */
    @Override
    public int calcularAntiguedadAnios() {
        return (int) ChronoUnit.YEARS.between(fechaContrato, LocalDate.now());
    }

    /**
     * Comprueba si estamos en el mes de cumpleaños de un empleado
     * @return devuelve true si el mes de ncaimiento es el actuál
     */
    @Override
    public boolean esteMesCumpleAniosEmpleado() {
        return (LocalDate.now().getMonth() == fechaNacimiento.getMonth());
    }

    public void mostrarDatos(){
        System.out.println(
                "Codigo= "+dniEmpleado.getNumeroDNI()+
                "\nNombre= "+nombre+"\nApellido= "+apellido +
                "\nDepartamento= "+departamento+
                "\nAños en la empresa= "+calcularAntiguedadAnios()+
                "\nEdad= "+ ChronoUnit.YEARS.between(fechaNacimiento,LocalDate.now()) +
                "\nFecha de contrato= "+ fechaContrato.format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy")));
    }

    /**
     * genera el código de empleado
     * @return UMBRE0001, UMBRE0002...
     */
    private String generarCodEmpleado () {
        contadorEmpleado++;
        String cabecera = "UMBRE";
        String codigo = String.format("%04d", contadorEmpleado); //añade 0 a la izquierda hasta llegar a 4 dígitos en caso de necesitarlo
        return cabecera+codigo;
    }
}
