import java.util.Scanner;
import java.util.Stack;

public class App {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args){
        System.out.println("Hello, World!");
        Tablero tablero = tableroMatriz();
        System.out.println(tablero);
        TableroConMatriz tableroConMatriz = new TableroConMatriz(tablero.getTablero());
        tableroConMatriz.mapaCoordenadas();
        if(tablero.isDosCasillasLibres()){
            while(!tablero.isCasillaInicialExist()){
                try{
                    System.out.print("Posicion de la casilla inicial (x,y):");
                    String[] pos = sc.nextLine().split(",");
                    tablero.setCasillaInicial(Integer.parseInt(pos[1]), Integer.parseInt(pos[0]));
                    tablero.getCasillaInicial().setDistanciaFinal(0);
                }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("No se puede colocar el inicio en esa posicion");
                }
            }
            while(!tablero.isCasillaFinalExist()){
                try{
                    System.out.print("Posicion de la casilla final (x,y):");
                    String[] pos = sc.nextLine().split(",");
                    tablero.setCasillaFinal(Integer.parseInt(pos[1]), Integer.parseInt(pos[0]));
                }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("No se puede colocar el final en esa posicion");
                }
            }
        }else{
            System.out.println("No hay dos casillas libres");
            return;
        }
        
        System.out.println(tablero);
        tableroConMatriz.setVisible(false);
        MetodoBusqueda metodo = new MetodoBusqueda(tablero);
        tipoDeBusqueda(metodo, tablero);
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
            }else{
                break;
            }
            }catch(NumberFormatException e){
                System.out.println("Error en el formato de entrada. Reintroducir");
            }
        }
        
        return new Tablero(f, c, p);
    }

    public static void tipoDeBusqueda(MetodoBusqueda metodo, Tablero tablero){
        Stack<Casilla> F = new Stack<Casilla>();
        TableroConMatriz tableroConMatriz = new TableroConMatriz(tablero.getTablero());
        tableroConMatriz.initComponents();

        while(true){
        tableroConMatriz.setVisible(true);
        F.push(tablero.getCasillaInicial());
        System.out.println("1. BFS");
        System.out.println("2. DFS");
        System.out.println("3. A*");
        System.out.println("4. Greedy");
        System.out.println("5. Salir");
        System.out.print("Opcion:");
        
        try{
        metodo.setSolucion(false);
        int opcion = Integer.parseInt(System.console().readLine());
        switch(opcion){
            case 1:
                metodo.BFS(F);
                F.clear();
                metodo.imprimeTableroEstrella();
                tablero.resetTablero();
                break;
            case 2:
                metodo.DFS(F);
                F.clear();
                metodo.imprimeTableroEstrella();
                tablero.resetTablero();
                break;
            case 3:
                metodo.Estrella(F);
                F.clear();
                metodo.imprimeTableroEstrella();
                tablero.resetTablero();
                break;
            case 4:
                metodo.Greed(F);
                F.clear();
                metodo.imprimeTableroEstrella();
                tablero.resetTablero();
                break;
            case 5:
                tableroConMatriz.setVisible(false);
                return;
            default:
                System.out.println("Opcion no valida");

                    }
            

            }catch(NumberFormatException e){
            System.out.println("Error en el formato de entrada. Reintroducir");
                }
            }
        }
}
