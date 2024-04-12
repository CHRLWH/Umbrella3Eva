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
}
