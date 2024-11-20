import java.awt.*;
import javax.swing.*;


public class VentanaMenu extends JFrame {
    public static GestorMemoria gestorMemoria = new GestorMemoria(32);
    public static GestorRecursos gestorRecursos = new GestorRecursos();
    public static Planificador planificador = new Planificador(gestorMemoria, gestorRecursos);
    public static Simulador simulador = new Simulador(planificador);
    public static SistemaPrincipalVista vista = new SistemaPrincipalVista();;
    private JButton iniciarBoton;

    public VentanaMenu() {
        // Configurar ventana principal
        setTitle("Bienvenido al Sistema Operativo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        setLayout(new BorderLayout());

        // Fondo con GIF redimensionable
        JLabel fondoGif = new JLabel(new ImageIcon("ProyectoFinalSO\\src\\gif1.gif")) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = ((ImageIcon) getIcon()).getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        fondoGif.setLayout(new BorderLayout());
        add(fondoGif, BorderLayout.CENTER);

        // Panel para el botón
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false); // Fondo transparente
        iniciarBoton = new JButton("Iniciar Sistema");
        iniciarBoton.setFont(new Font("Arial", Font.BOLD, 20));
        iniciarBoton.setForeground(Color.WHITE);
        iniciarBoton.setBackground(new Color(0, 102, 204));
        iniciarBoton.setFocusPainted(false);
        iniciarBoton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        iniciarBoton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBoton.add(iniciarBoton);
        fondoGif.add(panelBoton, BorderLayout.SOUTH);

        // Añadir acción al botón
        iniciarBoton.addActionListener(e -> {
            vista.iniciarEjecucion(); // Iniciar el simulador
            this.dispose(); // Cerrar el menú principal
            App.vista.setVisible(true); // Mostrar la ventana principal de la tabla
            
        });
    }

    public void mostrarVentana() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}

