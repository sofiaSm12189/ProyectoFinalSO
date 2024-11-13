import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ArrayList<Proceso> listaProcesos = new ArrayList<>();


        // Crear la ventana y pasar la lista de procesos
        SistemaPrincipalVista vista = new SistemaPrincipalVista("Simulador del Sistema Operativo", listaProcesos);
    }
}

