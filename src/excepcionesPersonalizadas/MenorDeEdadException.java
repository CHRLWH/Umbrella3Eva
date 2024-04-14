package excepcionesPersonalizadas;

public class MenorDeEdadException extends IllegalArgumentException{
    public MenorDeEdadException(String s) {
        super(s);
    }
}
