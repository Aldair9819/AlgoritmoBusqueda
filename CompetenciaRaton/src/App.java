import java.util.Scanner;
import java.util.Stack;

public class App {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args){
        System.out.println("Hello, World!");
        Tablero tablero = tableroMatriz();

        if(tablero.isTresCasillasLibres()){
            tablero.setCasillaInicial1(0, 0);
            tablero.setCasillaInicial2(tablero.getFila()-1, tablero.getColumna()-1);
            tablero.setCasillaFinal((tablero.getFila()-1)/2, (tablero.getColumna()-1)/2);
        }else{
            System.out.println("No hay dos casillas libres");
            return;
        }

        TableroConMatriz tableroConMatriz = new TableroConMatriz(tablero.getTablero());
        tableroConMatriz.initComponents();
        
        System.out.println(tablero);
        tableroConMatriz.setVisible(false);

        iniciarCompetencia(tablero);
    }

    public static Tablero tableroMatriz(){
        int f = 0; int c = 0; int p = 0;
        while(true){
        try{
            System.out.print("Numero de Filas:");
            f = Integer.parseInt(System.console().readLine());
            System.out.print("Numero de Columnas:");
            c = Integer.parseInt(System.console().readLine());
            System.out.print("Porcentaje de obstaculos (1%-100%):");
            p = Integer.parseInt(System.console().readLine());
            if(p < 1 || p > 100){
                System.out.println("El porcentaje debe estar entre 1 y 100");
            } else if((f*c)<3){
                System.out.println("El tablero debe tener al menos 3 casillas");
            }
            else{
                break;
            }
            }catch(NumberFormatException e){
                System.out.println("Error en el formato de entrada. Reintroducir");
            }
        }
        
        return new Tablero(f, c, p);
    }

    public static void iniciarCompetencia(Tablero tablero){

        Stack<Casilla> F = new Stack<Casilla>();
        Stack<Casilla> F2 = new Stack<Casilla>();

        TableroConMatriz tableroConMatriz = new TableroConMatriz(tablero.getTablero());
        tableroConMatriz.initComponents();

        F.push(tablero.getCasillaInicial1());
        F2.push(tablero.getCasillaInicial2());

        MetodoBusqueda recorrido1 = new MetodoBusqueda();
        MetodoBusqueda recorrido2 = new MetodoBusqueda();
        recorrido1.insertDatos(tablero, F, tablero.getCasillaInicial1(), "A");
        recorrido2.insertDatos(tablero, F2, tablero.getCasillaInicial2(), "B");

        recorrido1.start();
        recorrido2.start();

        F.clear();
        F2.clear();







        }
}
