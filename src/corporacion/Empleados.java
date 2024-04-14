package corporacion;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import excepcionesPersonalizadas.*;
import utilidades.*;

/**
 * @author Aitor Pascual Jiménez
 * @author Carlos Hernández Herrador
 * @version 0.1
 * @since Java 8.0
 */

public class Empleados implements ParaEmpleado {
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

    /**
     * Constructor estandar de la clase
     * @param dniEmpleado DNI del empleado (8 digitos y una letra)
     * @param nombre nombre del empleado
     * @param apellido apellido/os del empleado
     * @param departamento departamento del empleado
     * @param sueldoAnual salario anual en euros del empleado
     * @param fechaNacimiento fecha de nacimiento del empleado
     * @param fechaContrato fecha de contratación del empleado <br><br>
     *
     * La generacion del <b>código de empleado</b> se realiza de forma automática con {@link Empleados#generarCodEmpleado()}
     *
     *
     * @throws IllegalArgumentException excepciones heredadas de esta
     * <ul>
     * @throws DniNoValidoException
     *     <li>Si el DNI no es válido</li>
     * @throws SalarioDemasiadoBajoException
     *     <li>Si el salario es inferior a 10000</li>
     * @throws MenorDeEdadException
     *     <li>Si el empleado es menor de edad <br> (diferencia entre la fecha de nacimiento y la fecha de contrato en años menor de 18)</li>
     * @throws NoHaNacidoEsteEmpleadoException
     *     <li>Si la fecha de contrato es posterior al nacimiento</li>
     * @throws ContratacionPreviaALaCreacionDeLaEmpresaException
     *     <li>Si la fecha de contrato es anterior a la fecha de creacion de la empresa</li>
     * </ul>
     * @see Dni se hace uso de esta clase para validar y generar el DNI <br><br>
     */
    //TODO: AÑADIR EXCEPCIONES PERSONALIZADAS :)
    Empleados (String dniEmpleado, String nombre, String apellido, String departamento, double sueldoAnual, LocalDate fechaNacimiento, LocalDate fechaContrato) throws IllegalArgumentException{

        this.codEmpleado = generarCodEmpleado();

        if (Dni.validarNIF(dniEmpleado)) {
            this.dniEmpleado = new Dni(Integer.parseInt(dniEmpleado.substring(0,8)));
        } else {
            throw new DniNoValidoException("[!] El Dni no es válido");
        }

        this.nombre = nombre;
        this.apellido = apellido;
        this.departamento = departamento;

        if (sueldoAnual > 10000) {
            this.sueldoAnual = sueldoAnual;
        } else throw new SalarioDemasiadoBajoException("[!] Los empleados deben poder comer");

        if (ChronoUnit.YEARS.between(fechaNacimiento,fechaContrato) < 18) { //se usa la fecha de contrato ya que por algún error de inserción se podría dar de alta a un menor de edad en el pasado o por diferencia de días
            throw new MenorDeEdadException("[!] No puedes contratar a un menor de edad");
        } else this.fechaNacimiento = fechaNacimiento;

        if (fechaContrato.isBefore(fechaNacimiento)){
            throw new NoHaNacidoEsteEmpleadoException("[!] No puedes contratar a alguien que no ha nacido aún");
        } else if (fechaContrato.isBefore(FECHA_CREACION_EMPRESA)) {
            throw new ContratacionPreviaALaCreacionDeLaEmpresaException("[!] No puedes contratar a alguien cuando la empresa no existía");
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

    /**
     * <h4>Muestra estos datos:</h4>
     * <ul>
     *     <li>Dni</li>
     *     <li>Nombre</li>
     *     <li>Apellido</li>
     *     <li>Departamento</li>
     *     <li>Años en la empresa</li>
     *     <li>Edad</li>
     *     <li>fecha de contrato</li>
     * </ul>
     */
    public void mostrarTodosDatos(){
        System.out.println(
                "\nCodigo empleado = "+codEmpleado+
                        "\nDNI = "+dniEmpleado.getNumeroNIF()+ //TODO: comprobar que se añaden los ceros en DNI menores a 8 números y comprobar que imprime la letra
                        "\nNombre = "+nombre+
                        "\nApellido = "+apellido+
                        "\nDepartamento = "+departamento+
                        "\nAños en la empresa = "+ChronoUnit.YEARS.between(fechaContrato,LocalDate.now())+
                        "\nEdad actual = "+ChronoUnit.YEARS.between(fechaNacimiento,LocalDate.now())+
                        "\nfecha de contrato = "+fechaContrato.format(DateTimeFormatter.ofPattern("E',' d 'de' MMMM 'de' yyyy"))+"\n" //TODO: comprobar formato (creo que está bien pero no tengo tiempo ahora - Aitor)

        );
    }

    /**
     *<h4>Este método muestra:</h4>
     * <ul>
     *     <li>Codigo del empleado</li>
     *     <li>Nombre</li>
     *     <li>Apellido</li>
     *     <li>Departamento</li>
     * </ul>
     */
    public void mostrarReducido () {
        System.out.println(
                "\nCodigo empleado = "+codEmpleado+
                "\nNombre = "+nombre+
                "\nApellido = "+apellido+
                "\nDepartamento = "+departamento
        );
    }

    /**
     * genera el <b>código de empleado</b>
     * @return UMBRE0001, UMBRE0002...
     */
    private String generarCodEmpleado () {
        contadorEmpleado++;
        String cabecera = "UMBRE";
        String codigo = String.format("%04d", contadorEmpleado); //añade 0 a la izquierda hasta llegar a 4 dígitos en caso de necesitarlo
        return cabecera+codigo;
    }
    public void subirSueldoEmpleado(double porcentaje) throws IllegalArgumentException {
        if (porcentaje < 0) throw new IllegalArgumentException("[!] No puedes bajar el salario");
        else sueldoAnual += sueldoAnual*porcentaje/100;
    }

    public String getCodEmpleado() {
        return codEmpleado;
    }

    public Dni getDniEmpleado() {
        return dniEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDepartamento() {
        return departamento;
    }

    public double getSueldoAnual() {
        return sueldoAnual;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public LocalDate getFechaContrato() {
        return fechaContrato;
    }

    public static double getAyudaComida() {
        return ayudaComida;
    }

    public static double getVigencia() {
        return vigencia;
    }

    public static int getContadorEmpleado() {
        return contadorEmpleado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleados empleados = (Empleados) o;
        return Objects.equals(codEmpleado, empleados.codEmpleado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codEmpleado);
    }
}
