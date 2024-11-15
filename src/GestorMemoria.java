import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GestorMemoria {
    private ArrayList<Boolean> bloques;
    private Random random;

    public GestorMemoria(int numBloques) {
        bloques = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < numBloques; i++) {
            bloques.add(false); // Inicializar todos los bloques como libres
        }
    }

    public List<Integer> asignarMemoria(int tamanioRequerido) {
        List<Integer> bloquesAsignados = new ArrayList<>();
        int bloquesNecesarios = (int) Math.ceil(tamanioRequerido / 32.0); // Ajuste según tamaño de bloque

        while (bloquesAsignados.size() < bloquesNecesarios) {
            int indice = random.nextInt(bloques.size());
            if (!bloques.get(indice)) {
                bloques.set(indice, true);
                bloquesAsignados.add(indice);
            }
        }

        return bloquesAsignados.size() == bloquesNecesarios ? bloquesAsignados : new ArrayList<>();
    }

    public void liberarMemoria(List<Integer> bloquesAsignados) {
        for (int indice : bloquesAsignados) {
            if (indice >= 0 && indice < bloques.size()) {
                bloques.set(indice, false);
            }
        }
    }

    public boolean isBloqueOcupado(int indice) {
        return bloques.get(indice);
    }
}
