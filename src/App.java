
public class App {
    public static GestorMemoria gestorMemoria = new GestorMemoria(32);
    public static GestorRecursos gestorRecursos = new GestorRecursos();
    public static Planificador planificador = new Planificador(gestorMemoria, gestorRecursos);
    public static Simulador simulador = new Simulador(planificador);
    public static SistemaPrincipalVista vista = new SistemaPrincipalVista();;

    public static void main(String[] args) {

        vista.iniciarEjecucion();
        vista.setVisible(true);
    }
}
