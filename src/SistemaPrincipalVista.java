import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class SistemaPrincipalVista extends JFrame {
    private JTable tabla;
    private Tabla modeloTabla;

    public SistemaPrincipalVista(String tituloVentana, ArrayList<Proceso> procesos) {
        setTitle(tituloVentana);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(20, 20, 1800, 800);
        setLayout(new BorderLayout());

        modeloTabla = new Tabla(procesos);
        tabla = new JTable(modeloTabla);
        
        // Ajustar ancho de las columnas basado en el contenido
        for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
            TableColumn column = tabla.getColumnModel().getColumn(i);
            int width = obtenerAnchoPreferido(tabla, i);
            column.setPreferredWidth(width);
        }

        // Centrar el contenido de las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Agregar la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private int obtenerAnchoPreferido(JTable table, int columnIndex) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        Component headerComp = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, 0);
        int width = headerComp.getPreferredSize().width;

        for (int row = 0; row < table.getRowCount(); row++) {
            Component cellComp = table.getCellRenderer(row, columnIndex).getTableCellRendererComponent(table, table.getValueAt(row, columnIndex), false, false, row, columnIndex);
            width = Math.max(width, cellComp.getPreferredSize().width);
        }

        return width + 5;
    }
}
