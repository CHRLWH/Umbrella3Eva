package utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
                System.out.print("[!] Codigo de empleado valido");

            }
        }while (!codigoEmpleado.matches(codEmpleadoRegex));
        return codigoEmpleado;
    }
}
