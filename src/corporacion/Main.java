package corporacion;

import corporacion.Empleados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Empleados> empleadosActuales = new ArrayList<>();
        List<Empleados> empleadosAntiguos = new ArrayList<>();
        mostrarEmpleadosReducido(empleadosActuales);

        Empleados emp1 = new Empleados("54481447J", "Carlos", "Hernandez", "Informatica",
                40000, LocalDate.of(2002, 12, 21), LocalDate.of(2023, 11, 20));

        empleadosActuales.add(emp1);
    }

    /**
     *
     * @param empleadosList
     */
    private static void mostrarEmpleadosReducido(List<Empleados> empleadosList) {
        empleadosList.forEach(Empleados::mostrarReducido);
    }

    /**
     *
     * @param empleadosList
     * @param codigoEmpleado
     */
    private static void buscarEmpleadoPorCodigoYMostrarTodo(List<Empleados> empleadosList, String codigoEmpleado) {
        Empleados miEmpleado = buscarEmpleadoPorCodigo(empleadosList,codigoEmpleado);

        if (miEmpleado == null) System.out.println("[!] No se ha encontrado un empleado con ese código");
        else miEmpleado.mostrarTodosDatos();
    }

    /**
     *
     * @param empleadosList
     * @param departamento
     */
    private static void buscarEmpleadosPorDepartamentoYMostrarReducido(List<Empleados> empleadosList, String departamento) {
        List<Empleados> empleadosDelDepartamento =
                empleadosList.stream().filter(i -> departamento.equals(i.getDepartamento())) //filtrado por departamento
                .collect(Collectors.toList());

        if (empleadosDelDepartamento.isEmpty())
            System.out.println("[!] No se han encontrado empleados del departamento de " + departamento);
        else empleadosDelDepartamento.forEach(Empleados::mostrarReducido);
    }

    /**
     *
     * @param empleadosList
     * @param codigoEmpleado
     */

    private static void mostrarSalarioEmpleadoPorCodigo(List<Empleados> empleadosList, String codigoEmpleado) {
        Empleados miEmpleado = buscarEmpleadoPorCodigo(empleadosList,codigoEmpleado);

        if (miEmpleado == null) System.out.println("[!] No se ha encontrado un empleado con ese código");
        else {
            System.out.print("[+] El salario del mes de " + miEmpleado.getNombre() + " " + miEmpleado.getApellido() + " es de -> ");
            miEmpleado.calcularSueldoMensual(true);
        }
    }

    /**
     *
     * @param empleadosList
     * @param empleadosAntiguosList
     * @param codigoEmpleado
     */
    private static void borrarEmpleadoPorCodigo(List<Empleados> empleadosList,List<Empleados> empleadosAntiguosList, String codigoEmpleado) {
        Empleados miEmpleado = buscarEmpleadoPorCodigo(empleadosList,codigoEmpleado); //
        if (miEmpleado != null){
            empleadosAntiguosList.add(miEmpleado);
            empleadosList.remove(miEmpleado);
        } else System.out.println("[!] No se ha encontrado un empleado con ese código");
    }


    /**
     * Busca a un empleado haciendo uso de la función: {@link Main#buscarEmpleadoPorCodigo(List, String)} <br>
     * Sobre ese empleado
     *
     * @param empleadosList
     * @param codigoEmpleado
     * @param porcentajeDeSubida
     * @throws IllegalArgumentException
     */
    private static void subirSueldoEmpleadoPorCodigo(List<Empleados> empleadosList, String codigoEmpleado, double porcentajeDeSubida) throws IllegalArgumentException {
        Empleados miEmpleado = buscarEmpleadoPorCodigo(empleadosList,codigoEmpleado);
        if (miEmpleado == null) System.out.println("[!] No se ha encontrado un empleado con ese código");
        else {
            int antiguoSalarioAnual = (int) miEmpleado.getSueldoAnual();
            miEmpleado.subirSueldoEmpleado(porcentajeDeSubida); //puede lanzar una excepcion si tu jefe es un explotador
            System.out.println("[+] El antiguo salario anual de "+miEmpleado.getNombre()+" "+miEmpleado.getApellido()+" era de "+antiguoSalarioAnual);
            System.out.println("[+] El nuevo salario anual de "+miEmpleado.getNombre()+" "+miEmpleado.getApellido()+"es de "+miEmpleado.getSueldoAnual());
        }
    }
    //FUNCIONALIDADES INTERNAS

    /**
     * Busca un empleado en una lista según el código proporcionado
     * @param empleadosList lista en la que buscar al empleado
     * @param codigoEmpleado codigo del empleado a buscar
     * @return devuelve un empleado o null si no se ha encontrado
     */
    private static Empleados buscarEmpleadoPorCodigo(List<Empleados> empleadosList, String codigoEmpleado) {
        return empleadosList.stream()
                .filter(i -> codigoEmpleado.equals(i.getCodEmpleado())) //compara el código proporcionado con el código del empleado
                .findFirst() //se detiene al encontrar el primero
                .orElse(null); //si no lo encuentra devuelve null
    }
}