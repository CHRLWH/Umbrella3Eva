package utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class FuncionesSecundarias {

    //Fecha de nacimiento y fecha de contrato del empleado, se piden en formato
    //español. Si una fecha introducida es incorrecta, se volverá a pedir la fecha indicando
    //un mensaje de error

    /**
     * Pide una fecha de nacimiento en <b>FORMATO ESPAÑOL (d/M/yyyy)</b>
     *
     * @return devuelve siempre una fecha válida
     */
    public static LocalDate pedirFechaNacimiento() {
        LocalDate fechaNacimiento = null;
        boolean salirBucle = false;
        do {
            System.out.print("[?] Dame una fecha de nacimiento en formato español -> ");
            try {
                fechaNacimiento = LocalDate.parse(new Scanner(System.in).next());
                salirBucle = true;
            } catch (DateTimeParseException e){
                System.out.println("[!] El formato debe ser d/M/yyyy");
            }
        } while (!salirBucle);
        return fechaNacimiento;


    }
}
