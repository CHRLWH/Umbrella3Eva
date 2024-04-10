package corporacion;

import corporacion.Empleados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Empleados> empleadosActuales = new ArrayList<>();
        List<Empleados> empleadosAntiguos = new ArrayList<>();
        mostrarEmpleados(empleadosActuales);

        Empleados emp1 = new Empleados("54481447J", "Carlos","Hernandez","Informatica",
                40000, LocalDate.of(2002,12,21),LocalDate.of(2023,11,20));

        empleadosActuales.add(emp1);
    }

    private static void mostrarEmpleados(List<Empleados>empleadosActuales) {
        empleadosActuales.forEach(Empleados::mostrarReducido);
    }
}