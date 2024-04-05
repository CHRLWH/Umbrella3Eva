package utilidades;

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

    Dni(int numeroDNI){
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

    private static boolean validarNIF(String numeroNIF){

        String nifRegex = "[0-9]{9}[A-Z]";

        return (numeroNIF.matches(nifRegex));
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
}