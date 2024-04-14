package utilidades;

import excepcionesPersonalizadas.NombreOApellidoConNumerosException;
import excepcionesPersonalizadas.SalarioDemasiadoBajoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Escaneres {
    public static String pedirString (String mensaje){
        System.out.print(mensaje);
        return new Scanner(System.in).next();
    }
    public static int pedirNumeros(String mensaje){
        System.out.print(mensaje);
        return new Scanner(System.in).nextInt();
    }
    public static LocalDate pedirFechas (String mensaje){
        System.out.print(mensaje);
        return LocalDate.parse(new Scanner(System.in).next(), DateTimeFormatter.ofPattern("d/M/yyyy"));
    }
    public static String pedirCodigoEmpleado (String mensaje) {

        String codigoEmpleado ;
        String codEmpleadoRegex = "[U][M][B][R][E][0-9]{4}";
        do {
            System.out.print(mensaje);
            codigoEmpleado = new Scanner(System.in).next();
            if (!codigoEmpleado.matches(codEmpleadoRegex)) {
                System.out.println("[!] Codigo de empleado NO valido");
            } else {
                System.out.print("[+] Codigo de empleado valido");

            }
        }while (!codigoEmpleado.matches(codEmpleadoRegex));
        return codigoEmpleado;

    }
    public static String pedirDepartamento(String mensaje){

        System.out.print(mensaje);
        String departamento =  new Scanner(System.in).next();

        return departamento;
    }
    public static double pedirPorcentajeDeSubida(String mensaje){

        System.out.print(mensaje);
        double porcentajeDeSubida =  new Scanner(System.in).nextInt();

        return porcentajeDeSubida;
    }

    /**
     * Este método pide un nombre o apellido válido
     * @param mensaje mensaje a mostrar
     * @return Devuelve un nombre o apellido, sin números, sin espacios al principio o final y con la primera letra en mayúscula
     */
    public static String pedirNombreOApellido (String mensaje) {
        String aDevolver = null;
        do { //no sale del bucle hasta recibir un nombre o apellido que solo contenga letras
            try {
                String temporal = pedirString(mensaje).trim();
                if (Pattern.matches(".*\\d.*", temporal)) //comprueba que no contenga números
                    throw new NombreOApellidoConNumerosException("[!] Un nombre o apellido no puede contener números");
                else aDevolver = Character.toUpperCase(temporal.charAt(0)) + temporal.substring(1).toLowerCase(); //convierte la primera letra en mayúscula, el resto de la cadena en minúscula y asigna la cadena restante para devolver
            } catch (NombreOApellidoConNumerosException exc) {
                System.out.println(exc.getMessage());
            }
        } while (aDevolver == null);
        return aDevolver;
    }
    public static double pedirSalarioAnual (String mensaje) {
        double aDevoler = 0;
        do {
            try {
                System.out.print(mensaje);
                double temporal = new Scanner(System.in).nextDouble(); //pide el salario
                if (temporal < 10000) throw new SalarioDemasiadoBajoException("[!] El salario anual es demasiado bajo");
                aDevoler = temporal;
            } catch (InputMismatchException _) {
                System.out.println("[!] Se debe proporcionar un salario válido");
            } catch (SalarioDemasiadoBajoException exc) {
                System.out.println(exc.getMessage());
            }
        } while (aDevoler == 0);
        return aDevoler;
    }
}
