import java.util.LinkedList;
import java.util.List;
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
        List<Integer> bloquesAsignados = gestorMemoria.asignarMemoria(proceso.getMemoriaRequerida());

        if (!bloquesAsignados.isEmpty() && gestorRecursos.asignarRecursos(proceso)) {
            proceso.setBloquesAsignados(bloquesAsignados);
            System.out.println(
                    "Proceso ID: " + proceso.getIdProceso() + ", Ubicación Memoria: " + proceso.getUbicacionMemoria());
            App.vista.actualizarVista();

            proceso.reducirTiempoCPU();

            if (proceso.estaCompleto()) {
                gestorMemoria.liberarMemoria(proceso.getBloquesMemoria());
                App.vista.actualizarVista(); // Actualiza visualmente la liberación
                gestorRecursos.liberarRecursos(proceso);
            } else {
                agregarProceso(proceso);
            }
        }
    }

}
