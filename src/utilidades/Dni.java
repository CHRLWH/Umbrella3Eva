package utilidades;

import excepcionesPersonalizadas.DniNoValidoException;

import java.util.zip.DataFormatException;

public class Dni {




/*
-----------------------------------------------
|                                             |
|                    Atributos                |
|                                             |
-----------------------------------------------
*/


    //LA LETRA SE DEBE CALCULAR A PARTIR DEL NUMERO
    private int numeroDNI;

    private static final String DIGITOS_DE_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";

/*
-----------------------------------------------
|                                             |
|                    Builders                 |
|                                             |
-----------------------------------------------
*/

    public Dni(int numeroDNI){
        this.numeroDNI = numeroDNI;
    }


/*
-----------------------------------------------
|                                             |
|                    Métodos                  |
|                                             |
-----------------------------------------------
*/
    @Override
    public String toString() {
        return "utilidades= " + numeroDNI +
                ", LETRA= "+calcularLetraNIF(numeroDNI);
    }


    private static char calcularLetraNIF(int dni){

        return DIGITOS_DE_CONTROL.charAt(dni % 23);
    }

    public static boolean validarNIF(String numeroNIF){

        numeroNIF = numeroNIF.toUpperCase();
        String nifRegex = "[0-9]{8}[A-Z]";

        return (numeroNIF.matches(nifRegex) && numeroNIF.charAt(8) == calcularLetraNIF(Integer.parseInt(numeroNIF.substring(0,8))));
    }

    private static char extraerLetraNIF (String nif){

        return nif.charAt(8);
    }
    private static int extraerNumeroNIF(String nif){

        StringBuilder nifPartido = new StringBuilder();

        for (int i=0;i<nif.length()-1;i++){

            nifPartido.append(i);
        }
        return Integer.parseInt(String.valueOf(nifPartido));

    }


    /*                       GETTERS Y SETTERS                */

    public int getNumeroDNI() {
        return numeroDNI;
    }

    public String getNumeroNIF() {
        return String.valueOf(getNumeroDNI())+calcularLetraNIF(numeroDNI);
    }

    public void setDNI(String nif)  throws StringIndexOutOfBoundsException{

        if (! validarNIF(nif)){
            throw new StringIndexOutOfBoundsException("FORMATO DE NIF NO VALIDO");
        }else{
            this.numeroDNI = numeroDNI+calcularLetraNIF(numeroDNI);
        }
    }

    public void setDNI(int numeroDNI) throws StringIndexOutOfBoundsException{

        if (String.valueOf(numeroDNI).length()<2 || String.valueOf(numeroDNI).length()>8){
            throw new StringIndexOutOfBoundsException("FORMATO DE utilidades NO VALIDO.RECUERDA QUE TIENE QUE TENER UNA LONGITUD DE 8 CARACTERES");
        }else{
            this.numeroDNI = numeroDNI;
        }
    }

    /**
     * Añade ceros a la izquierda hasta llegar a 9<br><br>
     * Ejemplo: 4755652B -> 04755652B
     * @param dni dni proporcionado
     * @return dni con ceros a la izquierda para dnis menores a 9 char
     *
     * @throws DniNoValidoException si el DNI es más largo que 9 o el último caracter no es una letra
     */
    public static String aniadirCerosHasta9CharsDNI (String dni){
        //excepciones
        if (dni == null) throw new DniNoValidoException("[!] El Dni no puede ser nulo");
        if (dni.length() > 9) throw new DniNoValidoException("[!] Dni más largo de 9 caracteres");
        if (Character.isDigit(dni.charAt(dni.length()-1)))  throw new DniNoValidoException("[!] El último caracter del DNI no es una letra");

        StringBuilder dniATrabajar = new StringBuilder(dni);
        //añade ceros hasta llegar a 9 caracteres
        while (dniATrabajar.length() < 9){
            dniATrabajar.insert(0,0);
        }
        return dniATrabajar.toString();
    }

    /**
     * Pide por teclado un DNI, en caso de que no ser válido, suelta un error personalizado y vuelve a solicitarlo<br><br>
     * Este método hace uso de {@link Dni#aniadirCerosHasta9CharsDNI(String)} tanto para la comprobación de los errores, como para el formateo de cadenas válidas con menos de 9 caracteres<br><br>
     * Este método maneja sus erorres de manera interna sin lanzar ninguna excepción
     * @return DNI español válido
     */
    public static String pedirDniHastaRecibirUnoValido () {

        String dni = null;
        do {
            try {
                String dniTemporal = Escaneres.pedirString("[?] Dame un Dni -> ").toUpperCase();
                dniTemporal = Dni.aniadirCerosHasta9CharsDNI(dniTemporal); //añade ceros en caso de ser necesario
                if (Dni.validarNIF(dniTemporal)) {
                    dni = dniTemporal; //asigna y sale del bucle
                } else throw new DniNoValidoException("[!] El DNI no es válido");
            } catch (DniNoValidoException exc) {
                System.out.println(exc.getMessage());
            }
        } while (dni == null);
        return dni;
    }
}