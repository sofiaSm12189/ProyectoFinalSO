import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class SistemaPrincipalVista extends JFrame {

    private JTable tabla;
    private Tabla modeloTabla;
    private ArrayList<JPanel> bloquesMemoria;
    public static GestorMemoria gestorMemoria = new GestorMemoria(32);
    public static GestorRecursos gestorRecursos = new GestorRecursos();
    public static Planificador planificador = new Planificador(gestorMemoria, gestorRecursos);
    public static Simulador simulador = new Simulador(planificador);
    public static ArrayList<Proceso> procesos;

    public SistemaPrincipalVista() {
        simulador.cargarProcesos("src\\procesos.txt");
        procesos = new ArrayList<>(simulador.getProcesos());

        setTitle("Simulador de sistema operativo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(20, 20, 900, 600);
        setLayout(new BorderLayout());

        modeloTabla = new Tabla(procesos);
        tabla = new JTable(modeloTabla);

        ajustarAnchoColumnas();

        // Centrar el contenido de las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        crearPanelMemoria();

        setVisible(true);
    }

    private void ajustarAnchoColumnas() {
        for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
            TableColumn column = tabla.getColumnModel().getColumn(i);
            int width = obtenerAnchoPreferido(tabla, i);
            column.setPreferredWidth(width);
        }
    }

    private void crearPanelMemoria() {
        JPanel panelMemoria = new JPanel();
        panelMemoria.setLayout(new GridLayout(4, 8, 5, 5));
        bloquesMemoria = new ArrayList<>();

        for (int i = 0; i < 32; i++) {
            JPanel bloque = new JPanel();
            bloque.setPreferredSize(new Dimension(50, 50));
            bloque.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bloque.setBackground(Color.LIGHT_GRAY);
            bloquesMemoria.add(bloque);
            panelMemoria.add(bloque);
        }

        getContentPane().add(panelMemoria, BorderLayout.SOUTH);
    }

    public void actualizarBloqueMemoria(int indice, boolean ocupado, int idProceso) {
        SwingUtilities.invokeLater(() -> {
            if (indice >= 0 && indice < bloquesMemoria.size()) {
                JPanel bloque = bloquesMemoria.get(indice); // Obtener el bloque de memoria visual

                // Si el bloque está ocupado
                if (ocupado) {
                    bloque.setBackground(Color.GREEN); // Bloque ocupado, color verde
                    bloque.removeAll(); // Limpiar el bloque visual
                    bloque.add(new JLabel("ID: " + idProceso)); // Mostrar el ID del proceso
                } else {
                    bloque.setBackground(Color.LIGHT_GRAY); // Bloque libre, color gris
                    bloque.removeAll(); // Limpiar el bloque visual
                    bloque.add(new JLabel("Sin asignar")); // Mostrar "Sin asignar"
                }
                bloque.revalidate(); // Asegurarnos de que los cambios visuales se apliquen
                bloque.repaint(); // Redibujar el bloque
            }
        });
    }

    private int obtenerAnchoPreferido(JTable table, int columnIndex) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        Component headerComp = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table,
                column.getHeaderValue(), false, false, 0, 0);
        int width = headerComp.getPreferredSize().width;

        for (int row = 0; row < table.getRowCount(); row++) {
            Component cellComp = table.getCellRenderer(row, columnIndex).getTableCellRendererComponent(table,
                    table.getValueAt(row, columnIndex), false, false, row, columnIndex);
            width = Math.max(width, cellComp.getPreferredSize().width);
        }

        return width + 5;
    }

    // Método para iniciar la ejecución de los procesos en un hilo separado
    public void iniciarEjecucion() {
        Thread hiloEjecucion = new Thread(() -> {
            while (true) {
                simulador.ejecutar(procesos); // Usar el método de ejecutar del simulador
                actualizarVista();
                try {
                    Thread.sleep(1000); // Simula el tiempo entre ciclos de ejecución
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        hiloEjecucion.start();
    }

    public void actualizarVista() {
        SwingUtilities.invokeLater(() -> {
            modeloTabla.fireTableDataChanged();

            for (int i = 0; i < bloquesMemoria.size(); i++) {
                int idProceso = -1;

                for (Proceso p : procesos) {
                    if (p.getBloquesMemoria().contains(i)) {
                        idProceso = p.getIdProceso();
                        break;
                    }
                }

                actualizarBloqueMemoria(i, idProceso != -1, idProceso);
            }

            repaint();
        });
    }

}
