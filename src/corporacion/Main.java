package corporacion;

import excepcionesPersonalizadas.*;
import utilidades.Dni;
import utilidades.Escaneres;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

        public static void main(String[] args) {
            System.out.println("[+] BIENVENIDO A LA GESTIÓN DE EMPLEADOS DE LA CORPORACIÓN UMBRELLA");
            List<Empleados> empleadosList = new ArrayList<>();
            List<Empleados> empleadosAntiguosList = new ArrayList<>();

            //empleados de inicio ejemplo
            empleadosList.add(new Empleados("16233336M","Pepe","Pérez","Informatica",50000,LocalDate.of(2000,1,1),LocalDate.now()));
            empleadosList.add(new Empleados("57997939N","Juana","González","Informatica",50000,LocalDate.of(2000,4,1),LocalDate.now()));

            menu(empleadosList,empleadosAntiguosList);
        }

    /**
     * Menú principal de opciones
     * @param empleadosList lista de empleados actuales
     * @param empleadosAntiguosList lista de empleados antiguos
     */
    private static void menu(List<Empleados> empleadosList, List<Empleados> empleadosAntiguosList) {
           // departamento;
            int opcionMinima = 0, opcionMaxima = 7; //modificar estos valores si aumenta el número de opciones del menú

            int opcion;
            do {
                System.out.print(
                        "\n[+] Opciones:\n" +
                                "\t0 - Salir\n" +
                                "\t1 - Mostrar todos los empleados (formato reducido)\n" +
                                "\t2 - Dar de alta a un nuevo empleado\n" +
                                "\t3 - Buscar a un empleado por su código\n" +
                                "\t4 - Buscar todos los empleados de un departamento\n" +
                                "\t5 - Borrar a un empleado por su código\n" +
                                "\t6 - Subir el sueldo de un empleado (solo se admiten subidas)\n" +
                                "\t7 - Mostrar el salario mensual detallado de un empleado por su código \n" +
                                "\n[?] Escoge una opción -> ");
                try {
                    opcion = new Scanner(System.in).nextInt();
                } catch (InputMismatchException _) {
                    opcion = -1; //ejecuta el default del switch
                }
                switch (opcion) {
                    case 0:
                        System.out.println("[+] Saliendo...");
                        break;
                    case 1:
                        mostrarEmpleadosReducido(empleadosList);
                        break;
                    case 2:
                        darDeAltaEmpleado(empleadosList);
                        break;
                    case 3:
                        buscarEmpleadoPorCodigoYMostrarTodo(
                                empleadosList,
                                Escaneres.pedirCodigoEmpleado("[?] Introduce el codigo del empleado a buscar --> "));
                        break;
                    case 4:
                        buscarEmpleadosPorDepartamentoYMostrarReducido(
                                empleadosList,
                                Escaneres.pedirDepartamento("[?] Introduce el nombre del departamento a buscar --> "));
                        break;
                    case 5:
                        borrarEmpleadoPorCodigo(
                                empleadosList,
                                empleadosAntiguosList,
                                Escaneres.pedirCodigoEmpleado("[?] Introduce el codigo del empleado a buscar --> "));
                        break;
                    case 6:
                        subirSueldoEmpleadoPorCodigo(
                                empleadosList,
                                Escaneres.pedirCodigoEmpleado("[?] Introduce el codigo del empleado a buscar --> "));
                        break;
                    case 7:
                        mostrarSalarioEmpleadoPorCodigo(
                                empleadosList,
                                Escaneres.pedirCodigoEmpleado("[?] Introduce el codigo del empleado a buscar --> "));
                        break;
                    default:
                        System.out.println("[!] Se debe escoger un número entre "+opcionMinima+" y "+opcionMaxima+"\n");
                }
            } while (opcion != 0);
        }

        /**
         * Hace un forEach de la lista y a cada empleado le pasa el método {@link Empleados#mostrarReducido()}
         * @param empleadosList lista de empleados que recorre
         */
        private static void mostrarEmpleadosReducido(List<Empleados> empleadosList) {
            empleadosList.forEach(Empleados::mostrarReducido);
        }

        /**
         * Busca a un empleado usando el método {@link Main#buscarEmpleadoPorCodigo(List, String)}, si encuentra a un empleado en la lista, ejecuta el método {@link Empleados#mostrarTodosDatos()}
         * @param empleadosList lista de empleados sobre la que buscar
         * @param codigoEmpleado codigo del empleado a buscar
         */
        private static void buscarEmpleadoPorCodigoYMostrarTodo(List<Empleados> empleadosList, String codigoEmpleado) {
            Empleados miEmpleado = buscarEmpleadoPorCodigo(empleadosList,codigoEmpleado);

            if (miEmpleado == null) System.out.println("[!] No se ha encontrado un empleado con ese código");
            else miEmpleado.mostrarTodosDatos();
        }

        /**
         * Busca a todos los empleados pertenecientes a un departamento en concreto<br>
         * Sobre estos empleados se hace uso del método<br> {@link Empleados#mostrarReducido()}
         *
         * @param empleadosList lista de empleados sobre la que se efectúa la búsqueda
         * @param departamento departamento de los empleados a buscar
         */
        private static void buscarEmpleadosPorDepartamentoYMostrarReducido(List<Empleados> empleadosList, String departamento) {
            List<Empleados> empleadosDelDepartamento =
                    empleadosList.stream().filter(i -> departamento.equalsIgnoreCase(i.getDepartamento())) //filtrado por departamento
                            .collect(Collectors.toList());

            if (empleadosDelDepartamento.isEmpty())
                System.out.println("[!] No se han encontrado empleados del departamento de " + departamento);
            else empleadosDelDepartamento.forEach(Empleados::mostrarReducido);
        }

        /**
         * Busca a un empleado haciendo uso de la función: {@link Main#buscarEmpleadoPorCodigo(List, String)} <br>
         * Muestra el salario detallado de ese empleado <br>
         * Si no se encuentra un empleado con ese código, muestra un mensaje indicando que no se ha encontrado al empleado
         *
         * @param empleadosList lista de empleados
         * @param codigoEmpleado codigo del empleado a buscar
         */
        private static void mostrarSalarioEmpleadoPorCodigo(List<Empleados> empleadosList, String codigoEmpleado) {
            Empleados miEmpleado = buscarEmpleadoPorCodigo(empleadosList,codigoEmpleado);

            if (miEmpleado == null) System.out.println("[!] No se ha encontrado un empleado con ese código");
            else {
                miEmpleado.calcularSueldoMensual(true);
            }
        }

        /**
         * Busca a un empleado haciendo uso de la función: {@link Main#buscarEmpleadoPorCodigo(List, String)} <br>
         * Cambia ese objeto empleado de la lista de empleados actuales a la lista de empleados antiguos para después eliminarlo de la lista actuales<br>
         * Si no se encuentra un empleado muestra un mensaje indicando que no se ha encontrado al empleado
         *
         * @param empleadosList lista de empleados actuales
         * @param empleadosAntiguosList lista de empleados antiguos
         * @param codigoEmpleado codigo del empleado a borrar
         */
        private static void borrarEmpleadoPorCodigo(List<Empleados> empleadosList,List<Empleados> empleadosAntiguosList, String codigoEmpleado) {
            Empleados miEmpleado = buscarEmpleadoPorCodigo(empleadosList,codigoEmpleado); //
            if (miEmpleado != null){ //se encuentra un empleado
                empleadosAntiguosList.add(miEmpleado);
                empleadosList.remove(miEmpleado);
                System.out.println("[+] El empleado "+miEmpleado.getNombre()+" "+miEmpleado.getApellido()+" ha sido borrado con éxito");
            } else System.out.println("[!] No se ha encontrado un empleado con ese código");
        }

        /**
         * Busca a un empleado haciendo uso de la función: {@link Main#buscarEmpleadoPorCodigo(List, String)} <br>
         * Sobre ese empleado se aplica una posible subida de salario anual
         *
         * @param empleadosList lista de empleados sobre la que buscar
         * @param codigoEmpleado codigo de empleado a buscar
         */
        private static void subirSueldoEmpleadoPorCodigo(List<Empleados> empleadosList, String codigoEmpleado){
            Empleados miEmpleado = buscarEmpleadoPorCodigo(empleadosList,codigoEmpleado);
            if (miEmpleado == null) System.out.println("[!] No se ha encontrado un empleado con ese código");
            else { //se encuentra a un empleado con ese código
                double antiguoSalarioAnual = miEmpleado.getSueldoAnual();
                try {
                    double porcentajeDeSubida = Escaneres.pedirPorcentajeDeSubida("[?] Introduce el porcentaje que quieres subir --> "); //pide el porcentaje de subida
                    miEmpleado.subirSueldoEmpleado(porcentajeDeSubida); //puede lanzar una excepcion si tu jefe es un explotador
                    System.out.printf("[+] El antiguo salario anual de %s %s era de %.2f%n", miEmpleado.getNombre(), miEmpleado.getApellido(), antiguoSalarioAnual);
                    System.out.printf("[+] El nuevo salario anual de %s %s es de %.2f%n", miEmpleado.getNombre(), miEmpleado.getApellido(), miEmpleado.getSueldoAnual());
                } catch (JefeBajaSueldosException exc) {
                    System.out.println(exc.getMessage());
                }
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
                    .filter(empleado -> codigoEmpleado.equals(empleado.getCodEmpleado())) //compara el código proporcionado con el código del empleado
                    .findFirst() //se detiene al encontrar el primero
                    .orElse(null); //si no lo encuentra devuelve null
        }

    /**
     * Crea un nuevo objeto {@link Empleados} y lo añade a la lista de empleados<br><br>
     * Este método comprueba que el DNI proporcionado sea correcto con el método {@link Dni#pedirDniHastaRecibirUnoValido()}, además se comprueba que el DNI no exista en la lista<br><br>
     * Usa el método {@link Escaneres#pedirNombreOApellido(String)} para que los datos del empleado no contengan números y su inicial sea mayúscula<br><br>
     * Comprueba que el salario no sea inferior a 10000 y sea válido con {@link Escaneres#pedirSalarioAnual(String)} y que la edad del empleado en el momento de contrato sea igual o superior a los 18 años
     *
     * @param empleadosList lista de empleados a la que se añadirá el empleado
     */
    private static void darDeAltaEmpleado(List<Empleados> empleadosList) {
            boolean salirBucleFinal = false;
            do {
                String dni = null;
                boolean dniYaExistente;
                do {
                    dniYaExistente = false;
                    String dniTemp = Dni.pedirDniHastaRecibirUnoValido(); //solicita un dni

                    if (empleadosList.stream()
                            .anyMatch(i -> i.getDniEmpleado().getNumeroNIF()
                                    .equals(dniTemp))){ //comprueba si hay un empleado con este dni en la lista
                        System.out.println("[!] Este DNI ya existe en la lista de empleados");
                        dniYaExistente = true;
                    } else { //si el dni no existe en la lista lo asigna
                        dni = dniTemp;
                    }
                } while (dniYaExistente);

                String nombre = Escaneres.pedirNombreOApellido("[?] Dame un nombre -> ");
                String apellido = Escaneres.pedirNombreOApellido("[?] Dame un Apellido -> ");
                String departamento = Escaneres.pedirString("[?] Dame un Departamento -> ");
                double sueldoAnual = Escaneres.pedirSalarioAnual("[?] Dame el Sueldo Anual -> ");

                boolean salirBucle = false;
                LocalDate fechaNacimiento = null;
                LocalDate fechaContrato = null;

                do {
                    try {
                        fechaNacimiento = Escaneres.pedirFechas("[?] Dame la Fecha de Nacimiento (d/M/yyyy) -> ");
                        fechaContrato = Escaneres.pedirFechas("[?] Dame la fecha de inicio del contrato (d/M/yyyy) -> ");
                        //al explotar en algún punto el booleano no cambia :)
                        salirBucle = true;
                    } catch (DateTimeParseException a) {
                        System.out.println("[!] Recuerda introducir la fecha en formato d/M/yyyy");
                    }
                } while (!salirBucle);

                try {
                    Empleados empleado = new Empleados(dni, nombre, apellido, departamento, sueldoAnual, fechaNacimiento, fechaContrato);
                    empleadosList.add(empleado);
                    System.out.println("\n[+] Se ha añadido con éxito al empleado "+nombre+" "+apellido);
                    System.out.println("[+] El empleado "+nombre+" ahora forma parte del departamento de "+departamento);

                    salirBucleFinal = true; //si el Builder no explota sale del bucle
                } catch (DniNoValidoException | MenorDeEdadException | NoHaNacidoEsteEmpleadoException |
                         SalarioDemasiadoBajoException | ContratacionPreviaALaCreacionDeLaEmpresaException exc) { //el Builder devuelve excepciones heredadas de IllegalArgumentException
                    System.out.println(exc.getMessage()); //muestra el error proporcionado por el Builder
                }
            } while (!salirBucleFinal); //bucle externo final, el empleado se crea con éxito
        }
}