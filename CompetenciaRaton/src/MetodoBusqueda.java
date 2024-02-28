

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.FileHandler;



// https://itculiacanedu-my.sharepoint.com/:x:/g/personal/aldair_gr_culiacan_tecnm_mx/EVOGzezI621OngeqLRtPw18BiJOkfieipOM9d1F4xpOYQQ?e=pn70H0
public class MetodoBusqueda extends Thread {
    private Tablero tablero;
    private boolean solucion = false;
    private Stack<Casilla> F;
    private Casilla inicio ;
    private String nombreCasilla;
    private boolean win = false;
    private Scanner sc = new Scanner (System.in);
    private Stack<Casilla> OS = new Stack<Casilla>();
    private Casilla EA;

    public MetodoBusqueda(){

    }

    public void insertDatos(Tablero tablero, Stack<Casilla> F, Casilla inicio, String nombreCasilla){
        this.tablero = tablero;
        this.F = F;
        this.inicio = inicio;
        this.nombreCasilla = nombreCasilla;
    }


    @Override
    public void run(){
        Stack<Casilla> Fila = new Stack<Casilla>();
        Fila.add(this.inicio);
        System.out.println("La lista inicia en"+Fila.size());
        Estrella(Fila,this.inicio, this.nombreCasilla);
        if(this.win){
            System.out.println("Ganaste->"+nombreCasilla);
            imprimeTableroEstrella();
        }else{
            System.out.println("Perdiste->"+nombreCasilla);
        }
    }

    
    public void Estrella(Stack<Casilla> F, Casilla Inicio, String nombre){
        if(this.tablero.getCasillaFinal().getEstado().equals(ESTADO.VISITADO.toString())){
            return;
        }
        else if(F.isEmpty()){
            return;
        }else{
            this.EA = F.pop();
            if(GoalTest(EA)){
                this.win = true;
                return;
            }else{
                this.OS = Expand(EA);
                this.OS = EvaluateH(OS,Inicio);
                F = insert(F,OS);
                F = sort(F);
                Estrella(F,Inicio, nombre);
            }
        }
    }

    public void imprimeTableroEstrella(){
        TableroConMatriz tableroG = new TableroConMatriz(this.tablero.getTablero());
        if(win){
            this.retroceder(this.tablero.getCasillaFinal());
        }
        tableroG.initComponents();
        tableroG.setVisible(true);
        Scanner sc = new Scanner (System.in);
        sc.nextLine();
        tableroG.setVisible(false);
    }
  
    private Stack<Casilla> EvaluateH(Stack<Casilla> OS, Casilla casillaInicial){
        for(int i=0; i<OS.size(); i++){
            Casilla casillaEvaluada = OS.get(i);
            casillaEvaluada.setDistanciaFinal((getDistanciaLineal(casillaInicial, casillaEvaluada)+ getDistanciaRecorrida(casillaEvaluada, this.tablero.getCasillaFinal())));
            OS.set(i, casillaEvaluada);
        }
        return OS;
    }


    private Stack<Casilla> sort(Stack<Casilla> OS) {

        ArrayList<Casilla> Ordenar = new ArrayList<Casilla> ();
        while(!OS.isEmpty()){
            Ordenar.add(OS.pop());
        }

        // Ordenar el ArrayList utilizando un Comparator
        Collections.sort(Ordenar, new Comparator<Casilla>() {
            @Override
            public int compare(Casilla casilla2, Casilla casilla1) {
                // Comparar las distancias de menor a mayor
                return Double.compare(casilla1.getDistanciaFinal(), casilla2.getDistanciaFinal());
            }
        });

        for(Casilla casilla : Ordenar){
            if(casilla.getEstado().equals(ESTADO.NOVISITADO.toString())){
                OS.push(casilla);
            }
            
        }
     
        return OS;
    }
    
    
    private  Stack<Casilla> insert(Stack<Casilla> F, Stack<Casilla> OS){
        while(!OS.isEmpty()){
            Casilla ciudadActual = OS.pop();
            F.add(0,ciudadActual);
            }
        return F;
    }

    private  Stack<Casilla> Expand(Casilla EA){
        Stack<Casilla> OS = new Stack<Casilla>();
        for(int i=-1;i<=1;i+=2){
            try{
            OS.add(this.tablero.getCasilla(EA.getPosX()+i, EA.getPosY()));
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }

        for(int i=-1;i<=1;i+=2){
            try{
               OS.add(tablero.getCasilla(EA.getPosX(), EA.getPosY()+i));
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }

        OS.removeAll(Collections.singleton(null));

        for(int i=0;i<OS.size();i++){
            if(OS.get(i).getEstado().equals(ESTADO.NOVISITADO.toString())){
                OS.get(i).setPadre(EA);
            }
        }

        return OS;
    }


    private  boolean GoalTest(Casilla EA){
        EA.setEstado(ESTADO.VISITADO);
        EA.setDistanciaFinal((int)0);
        //sc.nextLine();
        return this.tablero.isCasillaFinal(EA);
    }

    private double getDistanciaLineal(Casilla casillaActual, Casilla casillaFinal){
        return Math.sqrt(Math.pow(casillaActual.getPosX()-casillaFinal.getPosX(),2)+Math.pow(casillaActual.getPosY()-casillaFinal.getPosY(),2));
        //return Math.abs(casillaActual.getPosX()-casillaFinal.getPosX())+Math.abs(casillaActual.getPosY()-casillaFinal.getPosY());
       
    }

    private double getDistanciaRecorrida(Casilla casillaActual, Casilla casillaFinal){
        //return Math.sqrt(Math.pow(casillaActual.getPosX()-casillaFinal.getPosX(),2)+Math.pow(casillaActual.getPosY()-casillaFinal.getPosY(),2));
        return Math.abs(casillaActual.getPosX()-casillaFinal.getPosX())+Math.abs(casillaActual.getPosY()-casillaFinal.getPosY());
       
    }
    
    public void retroceder(Casilla actual){
        if(actual.getPadre() == null){
            System.out.println("No hay solucion");
            return;
        }else{
            actual.setEstado(ESTADO.MEJORRECORRIDO);
            actual = actual.getPadre();
            if(this.tablero.isCasillaInicial1(actual)||this.tablero.isCasillaInicial2(actual)){
                actual.setEstado(ESTADO.INICIO);
                System.out.println("Solucion encontrada");
                return;
            }else{
                retroceder(actual);
            }
        } 
        
    }

    public void setSolucion(boolean solucion) {
        this.solucion = solucion; 
    }

    public boolean isWin() {
        return this.win;
    }

    }
