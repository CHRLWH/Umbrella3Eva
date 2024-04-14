package excepcionesPersonalizadas;

public class SalarioDemasiadoBajoException extends IllegalArgumentException {
    public SalarioDemasiadoBajoException(String s) {
        super(s);
    }
}
