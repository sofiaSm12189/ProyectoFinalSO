import java.util.ArrayList;

public class GestorMemoria {
    private ArrayList<Integer> bloques;

    public GestorMemoria(int numBloques) {
        bloques = new ArrayList<>();
        for (int i = 0; i < numBloques; i++) {
            bloques.add(-1); // Inicializar todos los bloques como libres (-1 significa libre)
        }
    }

    public int asignarMemoria(Proceso proceso) {
        for (int i = 0; i < bloques.size(); i++) {
            if (bloques.get(i) == -1) { // Si el bloque está libre
                bloques.set(i, proceso.getIdProceso()); // Asignar el bloque al ID del proceso
                proceso.setUbicacionMemoria(i); // Guardar la ubicación del bloque en el proceso
                return i; // Retornar el índice del bloque asignado
            }
        }
        return -1; // Retornar -1 si no hay bloques libres
    }

    public void liberarMemoria(Proceso proceso) {
        int bloqueAsignado = proceso.getUbicacionMemoria(); // Obtener el bloque asignado al proceso
        if (bloqueAsignado >= 0 && bloqueAsignado < bloques.size()) {
            bloques.set(bloqueAsignado, -1); // Liberar el bloque
            proceso.setUbicacionMemoria(-1); // Reiniciar la ubicación de memoria en el proceso
        }
    }

    // Método para obtener el ID del proceso que ocupa un bloque específico
    public int obtenerIdProcesoEnBloque(int index) {
        if (index >= 0 && index < bloques.size()) {
            return bloques.get(index); // Retorna el ID del proceso o -1 si está libre
        }
        return -1; // Retorna -1 si el índice está fuera de rango
    }

    public boolean isBloqueOcupado(int index) {
        if (index >= 0 && index < bloques.size()) {
            return bloques.get(index) != -1; // Retorna true si el bloque está ocupado (no es -1)
        }
        return false; // Retorna false si el índice está fuera de rango o el bloque está libre
    }

}