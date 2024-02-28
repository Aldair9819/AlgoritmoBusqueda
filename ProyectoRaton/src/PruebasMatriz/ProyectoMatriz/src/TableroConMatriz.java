package PruebasMatriz.ProyectoMatriz.src;
import javax.swing.*;
import java.awt.*;


public class TableroConMatriz extends JFrame {

    private final int filas = 8;
    private final int columnas = 8;
    private int[][] matrizDatos;

    public TableroConMatriz(int[][] matrizDatos) {
        this.matrizDatos = matrizDatos;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tablero con Matriz");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(filas, columnas));

        // Rellenar el tablero con colores alternos y datos de la matriz
        Color color1 = Color.WHITE;
        Color color2 = Color.GRAY;
        //Color color3 = Color.GREEN;
        

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Color color = (i + j) % 2 == 0 ? color1 : color2;
                int valor = matrizDatos[i][j];

                CasillaPanel cuadro = new CasillaPanel(valor);
                cuadro.setBackground(color);
                panel.add(cuadro);
            }
        }

        add(panel);

        // Ajustar el tamaño de la fuente para que se ajuste a las casillas
        Font font = new Font("Arial", Font.PLAIN, 20);
        setFont(font);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static class CasillaPanel extends JPanel {
        private int valor;

        public CasillaPanel(int valor) {
            this.valor = valor;


        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dibujar el valor en el centro de la casilla
            g.drawString(String.valueOf(valor), getWidth() / 2, getHeight() / 2);
        }
    }

    public static void main(String[] args) {
        // Ejemplo de una matriz 8x8 con valores
        int[][] matrizDatos = new int[8][8];
        // Rellenar la matriz con valores, por ejemplo, números del 1 al 64
        int contador = 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matrizDatos[i][j] = contador++;
            }
        }

        SwingUtilities.invokeLater(() -> new TableroConMatriz(matrizDatos));
    }
}
