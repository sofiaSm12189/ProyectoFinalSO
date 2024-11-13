public class GestorRecursos {
    private int impresoras = 2;
    private int escaneres = 1;
    private int modems = 1;
    private int cds = 1;

    public boolean asignarRecursos(Proceso proceso) {
        if (proceso.getImpresorasSolictadas() <= impresoras &&
            proceso.getEscaneresSolicitados() <= escaneres &&
            proceso.getModemsSolicitados() <= modems &&
            proceso.getCdsSolicitados() <= cds) {
            
            impresoras -= proceso.getImpresorasSolictadas();
            escaneres -= proceso.getEscaneresSolicitados();
            modems -= proceso.getModemsSolicitados();
            cds -= proceso.getCdsSolicitados();
            return true;
        }
        return false;
    }

    public void liberarRecursos(Proceso proceso) {
        impresoras += proceso.getImpresorasSolictadas();
        escaneres += proceso.getEscaneresSolicitados();
        modems += proceso.getModemsSolicitados();
        cds += proceso.getCdsSolicitados();
    }
}
