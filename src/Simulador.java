import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulador {
    private Planificador planificador;
    private List<Proceso> procesos = new ArrayList<>(); // Lista de procesos si se necesita almacenar localmente
    private List<Proceso> procesosT = new ArrayList<>(); // Otra lista si es necesario (verificar su propósito)

    public Simulador(Planificador planificador) {
        this.planificador = planificador;
    }

    public void leerArchivo() {
        String archivo = "procesos.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                if (valores.length == 8) {
                    int tiempoLlegada = Integer.parseInt(valores[0]);
                    int prioridad = Integer.parseInt(valores[1]);
                    int tiempoProcesador = Integer.parseInt(valores[2]);
                    int mbytes = Integer.parseInt(valores[3]);
                    int cantImpresora = Integer.parseInt(valores[4]);
                    int cantEscan = Integer.parseInt(valores[5]);
                    int cantModems = Integer.parseInt(valores[6]);
                    int cantCD = Integer.parseInt(valores[7]);

                    // Crear un nuevo proceso con todos los atributos necesarios
                    Proceso proceso = new Proceso(
                        tiempoLlegada, "Creado", tiempoLlegada, prioridad, prioridad,
                        tiempoProcesador, tiempoProcesador, mbytes, "Sin asignar",
                        cantImpresora, 0, cantEscan, 0, cantModems, 0, cantCD, 0
                    );

                    procesos.add(proceso);  // Añadir a la lista local
                    procesosT.add(proceso); // Añadir a otra lista si es necesario
                    planificador.agregarProceso(proceso); // Añadir al planificador
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarProcesos(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int idProceso = 1;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length != 8) {
                    System.out.println("Línea inválida (se esperan 8 campos): " + linea);
                    continue;
                }
                try {
                    Proceso proceso = new Proceso(
                        idProceso++, Integer.parseInt(partes[0]), Integer.parseInt(partes[1]),
                        Integer.parseInt(partes[2]), Integer.parseInt(partes[3]),
                        Integer.parseInt(partes[4]), 0, Integer.parseInt(partes[5]), 0,
                        Integer.parseInt(partes[6]), 0, Integer.parseInt(partes[7]), 0
                    );
                    planificador.agregarProceso(proceso);
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir un número en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de procesos: " + nombreArchivo);
            e.printStackTrace();
        }
    }

    public void ejecutar() {
        while (true) {
            planificador.ejecutar();
            try {
                Thread.sleep(1000); // Quantum de 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
