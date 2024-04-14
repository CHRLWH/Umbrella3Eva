package excepcionesPersonalizadas;

public class NoHaNacidoEsteEmpleadoException extends IllegalArgumentException{
    public NoHaNacidoEsteEmpleadoException(String s) {
        super(s);
    }
}
