public class GestorMemoria {
    public static final int MEMORIA_TOTAL = 2048; // en MB
    private int memoriaDisponible = MEMORIA_TOTAL;

    public boolean asignarMemoria(Proceso proceso) {
        if (proceso.getMemoriaRequerida() <= memoriaDisponible) {
            memoriaDisponible -= proceso.getMemoriaRequerida();
            return true;
        }
        return false; // No se pudo asignar memoria
    }

    public void liberarMemoria(Proceso proceso) {
        memoriaDisponible += proceso.getMemoriaRequerida();
    }

    public int getMemoriaDisponible() { return memoriaDisponible; }
}
