public class App {
    public static GestorMemoria gestorMemoria = new GestorMemoria(32);
    public static GestorRecursos gestorRecursos = new GestorRecursos();
    public static Planificador planificador = new Planificador(gestorMemoria, gestorRecursos);
    public static Simulador simulador = new Simulador(planificador);
    public static SistemaPrincipalVista vista = new SistemaPrincipalVista(); // Ventana principal
    public static VentanaMenu interfazVista = new VentanaMenu(); // Menú principal

    public static void main(String[] args) {
        interfazVista.mostrarVentana(); // Mostrar solo el menú principal
        vista.setVisible(false); // Ocultar la ventana principal inicialmente
        
    }
}
