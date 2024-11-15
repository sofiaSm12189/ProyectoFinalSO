import java.util.ArrayList;

public class App {
    public static GestorMemoria gestorMemoria = new GestorMemoria(32);
    public static GestorRecursos gestorRecursos = new GestorRecursos();
    public static Planificador planificador = new Planificador(gestorMemoria, gestorRecursos);
    public static Simulador simulador = new Simulador(planificador);
    public static SistemaPrincipalVista vista;

    public static void main(String[] args) {

        vista = new SistemaPrincipalVista();
        vista.setVisible(true);
        vista.iniciarEjecucion();

    }
}
