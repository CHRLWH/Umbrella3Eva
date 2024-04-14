package corporacion;

import excepcionesPersonalizadas.DniNoValidoException;
import utilidades.Dni;
import utilidades.Escaneres;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

        public static void main(String[] args) {
            System.out.println("[+] BIENVENIDO A LA GESTIÓN DE EMPLEADOS DE LA CORPORACIÓN UMBRELLA\n");
            List<Empleados> empleadosList = new ArrayList<>();
            List<Empleados> empleadosAntiguosList = new ArrayList<>();
            menu(empleadosList,empleadosAntiguosList);
        }

        private static void menu(List<Empleados> empleadosList, List<Empleados> empleadosAntiguosList) {
           // departamento;
            int opcion;
            do {
                System.out.print(
                        "[+] Opciones:\n" +
                                "\t0 - Salir\n" +
                                "\t1 - Mostrar todos los empleados (formato reducido)\n" +
                                "\t2 - Dar de alta a un nuevo empleado\n" +
                                "\t3 - Buscar a un empleado por su código\n" +
                                "\t4 - Buscar todos los empleados de un departamento\n" +
                                "\t5 - Borrar a un empleado por su código\n" +
                                "\t6 - Subir el sueldo de un empleado (solo se admiten subidas)\n" +
                                "\t7 - Mostrar el salario mensual detallado de un empleado por su código \n" +
                                "\n[?] Escoge una opción -> ");
                opcion = new Scanner(System.in).nextInt();
                switch (opcion) {
                    case 0:
                        System.out.println("[+] Saliendo...");
                        break;
                    case 1:
                        mostrarEmpleadosReducido(empleadosList);
                        break;
                    case 2:
                        darDeAltaEmpleado();
                        break;
                    case 3:
                        buscarEmpleadoPorCodigoYMostrarTodo(
                                empleadosList,
                                Escaneres.pedirCodigoEmpleado("Introduce el codigo del empleado a buscar --> "));
                        break;
                    case 4:
                        buscarEmpleadosPorDepartamentoYMostrarReducido(
                                empleadosList,
                                Escaneres.pedirDepartamento("Introduce el nombre del departamento a buscar --> "));
                        break;
                    case 5:
                        borrarEmpleadoPorCodigo(
                                empleadosList,
                                empleadosAntiguosList,
                                Escaneres.pedirCodigoEmpleado("Introduce el codigo del empleado a buscar --> "));
                        break;
                    case 6:
                        subirSueldoEmpleadoPorCodigo(
                                empleadosList,
                                Escaneres.pedirCodigoEmpleado("Introduce el codigo del empleado a buscar --> "),
                                Escaneres.pedirPorcentajeDeSubida("Introduce el porcentaje que quieres subir --> "));
                        break;
                    case 7:
                        mostrarSalarioEmpleadoPorCodigo(
                                empleadosList,
                                Escaneres.pedirCodigoEmpleado("Introduce el codigo del empleado a buscar --> "));
                }
            } while (opcion != 0);
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
         * Busca a todos los empleados pertenecientes a un departamento en concreto<br>
         * Sobre estos empleados se hace uso del método<br> {@link Empleados#mostrarReducido()}
         *
         * @param empleadosList lista de empleados sobre la que se efectúa la búsqueda
         * @param departamento departamento de los empleados a buscar
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
                System.out.print("[+] El salario del mes de " + miEmpleado.getNombre() + " " + miEmpleado.getApellido() + " es de -> ");
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
         * Sobre ese empleado se aplica una posible subida de salario anual, en caso de que el dato no sea un double o una subida, lanza una excepción
         *
         * @param empleadosList lista de empleados sobre la que buscar
         * @param codigoEmpleado codigo de empleado a buscar
         * @param porcentajeDeSubida porcentaje de subidad aplicar
         * @throws IllegalArgumentException excepción por subida negativa de salario
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
                    .filter(empleado -> codigoEmpleado.equals(empleado.getCodEmpleado())) //compara el código proporcionado con el código del empleado
                    .findFirst() //se detiene al encontrar el primero
                    .orElse(null); //si no lo encuentra devuelve null
        }

        private static void darDeAltaEmpleado() {
            boolean salirBucleFinal = false;
            do {

                String dni = Dni.pedirDniHastaRecibirUnoValido();

                String nombre = Escaneres.pedirString("Dame un nombre -> ");
                String apellido = Escaneres.pedirString("Dame un Apellido -> ");
                String departamento = Escaneres.pedirString("Dame un Departamento -> ");
                double sueldoAnual = Escaneres.pedirNumeros("Dame el Sueldo Anual -> ");
                boolean salirBucle = false;
                LocalDate fechaNacimiento = null;
                LocalDate fechaContrato = null;

                do {
                    try {
                        fechaNacimiento = Escaneres.pedirFechas("Dame la Fecha de Nacimiento (d/M/yyyy) -> ");
                        fechaContrato = Escaneres.pedirFechas("Dame la fecha de inicio del contrato (d/M/yyyy) -> ");
                        //al explotar en algún punto el booleano no cambia :)
                        salirBucle = true;
                    } catch (DateTimeParseException a) {
                        System.out.println("[!] Recuerda introducir la fecha en formato d/M/yyyy");
                    }
                } while (!salirBucle);
                //te quiero y he tocado tú código, no me mates Carlos <3
                //He puesto mi fecha de nacimiento y pone que soy menor de edad

                try {
                    Empleados empleado = new Empleados(dni, nombre, apellido, departamento, sueldoAnual, fechaNacimiento, fechaContrato);
                    empleado.mostrarTodosDatos();
                    salirBucleFinal = true; //si el Builder no explota sale del bucle
                } catch (
                        IllegalArgumentException exc) { //el Builder devuelve excepciones heredadas de IllegalArgumentException
                    System.out.println(exc.getMessage()); //muestra el error proporcionado por el Builder
                }
            } while (!salirBucleFinal); //bucle externo final, el empleado se crea con éxito
        }
}