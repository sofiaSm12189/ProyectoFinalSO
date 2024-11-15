import java.util.LinkedList;
import java.util.Queue;

public class Planificador {
    private Queue<Proceso> colaTiempoReal = new LinkedList<>();
    private Queue<Proceso> colaUsuario1 = new LinkedList<>();
    private Queue<Proceso> colaUsuario2 = new LinkedList<>();
    private Queue<Proceso> colaUsuario3 = new LinkedList<>();
    private GestorMemoria gestorMemoria;
    private GestorRecursos gestorRecursos;

    public Planificador(GestorMemoria gestorMemoria, GestorRecursos gestorRecursos) {
        this.gestorMemoria = gestorMemoria;
        this.gestorRecursos = gestorRecursos;
    }

    public void agregarProceso(Proceso proceso) {
        switch (proceso.getPrioridadActual()) {
            case 0 -> colaTiempoReal.add(proceso);
            case 1 -> colaUsuario1.add(proceso);
            case 2 -> colaUsuario2.add(proceso);
            case 3 -> colaUsuario3.add(proceso);
        }
    }

    public void ejecutar() {
        if (!colaTiempoReal.isEmpty()) {
            ejecutarProceso(colaTiempoReal.poll());
        } else if (!colaUsuario1.isEmpty()) {
            ejecutarProceso(colaUsuario1.poll());
        } else if (!colaUsuario2.isEmpty()) {
            ejecutarProceso(colaUsuario2.poll());
        } else if (!colaUsuario3.isEmpty()) {
            ejecutarProceso(colaUsuario3.poll());
        }
    }

    private void ejecutarProceso(Proceso proceso) {
        int bloqueAsignado = gestorMemoria.asignarMemoria(proceso); // Asignamos memoria
        proceso.setUbicacionMemoria(bloqueAsignado);

        System.out.println(
                "Proceso ID: " + proceso.getIdProceso() + ", Ubicación Memoria: " + proceso.getUbicacionMemoria());

        if (bloqueAsignado != -1 && gestorRecursos.asignarRecursos(proceso)) {
            System.out.println("Ejecutando proceso: " + proceso.getIdProceso());
            App.vista.actualizarVista();
            App.vista.actualizarBloqueMemoria(bloqueAsignado, true, proceso.getIdProceso()); // Actualiza color y ID

            proceso.reducirTiempoCPU();

            if (proceso.estaCompleto()) {
                gestorMemoria.liberarMemoria(proceso);
                App.vista.actualizarBloqueMemoria(bloqueAsignado, false, -1); // Liberamos el bloque y lo ponemos en
                                                                              // gris
                gestorRecursos.liberarRecursos(proceso);
            } else {
                agregarProceso(proceso); // Volver a agregar a la cola si no ha terminado
            }
        } else {
            System.out.println("No se pudo asignar memoria o recursos al proceso: " + proceso.getIdProceso());
        }
    }

}
