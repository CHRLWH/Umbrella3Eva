package excepcionesPersonalizadas;

public class DniNoValidoException extends IllegalArgumentException{
    public DniNoValidoException(String s) {
        super(s);
    }
}
