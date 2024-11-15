import java.util.ArrayList;
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
        ejecutarProcesoDeCola(colaTiempoReal);
        ejecutarProcesoDeCola(colaUsuario1);
        ejecutarProcesoDeCola(colaUsuario2);
        ejecutarProcesoDeCola(colaUsuario3);
    }

    private void ejecutarProcesoDeCola(Queue<Proceso> cola) {
        if (!cola.isEmpty()) {
            Proceso proceso = cola.poll();
            List<Integer> bloquesAsignados = gestorMemoria.asignarMemoria(proceso.getMemoriaRequerida());

            if (!bloquesAsignados.isEmpty() && gestorRecursos.asignarRecursos(proceso)) {
                proceso.setEstado("Ejecutando");
                proceso.setBloquesAsignados(bloquesAsignados);
                System.out.println("Proceso ID: " + proceso.getIdProceso() + ", Ubicación Memoria: "
                        + proceso.getUbicacionMemoria());
                App.vista.actualizarVista();

                proceso.reducirTiempoCPU();

                if (proceso.estaCompleto()) {
                    proceso.setEstado("Terminado");
                    gestorMemoria.liberarMemoria(proceso.getBloquesMemoria());
                    gestorRecursos.liberarRecursos(proceso);
                    App.vista.actualizarVista();
                } else {
                    agregarProceso(proceso);
                }
            } else {
                proceso.setEstado("Creado");
                agregarProceso(proceso);
            }
        }
    }

    // Método para obtener todos los procesos gestionados en las colas
    public List<Proceso> getProcesos() {
        List<Proceso> procesos = new ArrayList<>();
        procesos.addAll(colaTiempoReal);
        procesos.addAll(colaUsuario1);
        procesos.addAll(colaUsuario2);
        procesos.addAll(colaUsuario3);
        return procesos;
    }
}
