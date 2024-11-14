import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class Tabla extends AbstractTableModel {
    private ArrayList<Proceso> procesos;
    private String[] columnas = {
            "ID", "Estatus", "Tiempo Llegada", "Prioridad Inicial", "Prioridad Actual",
            "Tiempo Procesador Requerido", "Tiempo Procesador Restante", "Memoria Requerida",
            "Ubicación Memoria", "Impresoras Solicitadas", "Impresoras Asignadas",
            "Escáneres Solicitados", "Escáneres Asignados", "Modems Solicitados",
            "Modems Asignados", "CDs Solicitados", "CDs Asignados"
    };

    public Tabla(ArrayList<Proceso> procesos) {
        this.procesos = procesos;
    }

    @Override
    public int getRowCount() {
        return procesos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Proceso proceso = procesos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return proceso.getIdProceso();
            case 1:
                return proceso.getEstado();
            case 2:
                return proceso.getTiempoLlegada();
            case 3:
                return proceso.getPrioridadInicial();
            case 4:
                return proceso.getPrioridadActual();
            case 5:
                return proceso.getTiempoCPURequerido();
            case 6:
                return proceso.getTiempoCPURestante();
            case 7:
                return proceso.getMemoriaRequerida();
            case 8:
                int ubicacionMemoria = proceso.getUbicacionMemoria();
                return ubicacionMemoria == -1 ? "Sin asignar" : ubicacionMemoria;
            case 9:
                return proceso.getImpresorasSolictadas();
            case 10:
                return proceso.getImpresonasAsignadas();
            case 11:
                return proceso.getEscaneresSolicitados();
            case 12:
                return proceso.getEscaneresAsignados();
            case 13:
                return proceso.getModemsSolicitados();
            case 14:
                return proceso.getModemsAsignados();
            case 15:
                return proceso.getCdsSolicitados();
            case 16:
                return proceso.getCdsAsignados();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    public void updateTable() {
        fireTableDataChanged();
    }
}
