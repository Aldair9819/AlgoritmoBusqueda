

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;



// https://itculiacanedu-my.sharepoint.com/:x:/g/personal/aldair_gr_culiacan_tecnm_mx/EVOGzezI621OngeqLRtPw18BiJOkfieipOM9d1F4xpOYQQ?e=pn70H0
public class MetodoBusqueda {
    private Tablero tablero;
    private Casilla EA;
    boolean solucion = false;

    public MetodoBusqueda(Tablero tablero){
        this.tablero = tablero;
    }

    

    public void BFS(Stack<Casilla> F){
        if(F.isEmpty()){
            System.out.println("No hay solucion");
            return;
        }else{
            this.EA = F.pop();
            if(GoalTest(EA)){
                System.out.println("Solucion encontrada");
                solucion = true;
                F.clear();
                return;
            }else{
                Stack<Casilla> OS = Expand(EA);
                removeRepeate(OS,F);
                F = insert(F,OS);
                BFS(F);
                
            }
        }
    }
    
    public void DFS(Stack<Casilla> F){
        if(F.isEmpty()){
            System.out.println("No hay solucion");
            return;
        }else{
            this.EA = F.pop();
            if(GoalTest(EA)){
                System.out.println("Solucion encontrada");
                solucion = true;
                F.clear();
                return;
            }else{
                Stack<Casilla> OS = Expand(EA);
                removeRepeate(OS,F);
                F = insert(OS,F);
                DFS(F);
                
            }
        }
    }

    
    public void Greed(Stack<Casilla> F){
        if(F.isEmpty()){
            System.out.println("No hay solucion");
            return;
        }else{
            this.EA = F.pop();
            if(GoalTest(EA)){
                System.out.println("Solucion encontrada");
                solucion = true;
                F.clear();
                return;
            }else{
                Stack<Casilla> OS = Expand(EA);
                OS = EvaluateGreed(OS);
                if(!OS.isEmpty()){
                    OS = sort(OS);
                    F.push(OS.pop());
                }
                Greed(F);
                
            }
        }
    }
    
    public void Estrella(Stack<Casilla> F){
        if(F.isEmpty()){
            System.out.println("No hay solucion");
            return;
        }else{
            this.EA = F.pop();
            if(GoalTest(EA)){
                System.out.println("Solucion encontrada");
                solucion = true;
                return;
            }else{
                Stack<Casilla> OS = Expand(EA);
                 OS = EvaluateH(OS);
                F = insert(F,OS);
                F = sort(F);
                Estrella(F);
                
            }
        }
    }

    public void imprimeTableroEstrella(){
        TableroConMatriz tableroG = new TableroConMatriz(this.tablero.getTablero());
        if(solucion){
            this.retroceder(this.tablero.getCasillaFinal());
        }
        tableroG.initComponents();
        tableroG.setVisible(true);
        Scanner sc = new Scanner (System.in);
        sc.nextLine();
        tableroG.setVisible(false);
    }
  
    private Stack<Casilla> EvaluateH(Stack<Casilla> OS){
        for(int i=0; i<OS.size(); i++){
            Casilla casillaEvaluada = OS.get(i);

            double recorrido = getDistanciaLineal(this.tablero.getCasillaInicial(), casillaEvaluada);
            double valorH = getDistanciaRecorrida(casillaEvaluada, this.tablero.getCasillaFinal());
            

            casillaEvaluada.setDistanciaFinal((recorrido+ valorH));
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
    
    
    private Stack<Casilla> EvaluateGreed(Stack<Casilla> OS){
        for(int i=0; i<OS.size(); i++){
            Casilla casillaEvaluada = OS.get(i);
            
            double valorH = getDistanciaLineal(casillaEvaluada, this.tablero.getCasillaFinal());

            casillaEvaluada.setDistanciaFinal(valorH);
            OS.set(i, casillaEvaluada);
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
                if(this.tablero.getCasilla(EA.getPosX()+i, EA.getPosY()).getEstado().equals(ESTADO.NOVISITADO.toString())){
                    OS.add(this.tablero.getCasilla(EA.getPosX()+i, EA.getPosY()));
                }
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }

        for(int i=-1;i<=1;i+=2){
            try{
                if(this.tablero.getCasilla(EA.getPosX(), EA.getPosY()+i).getEstado().equals(ESTADO.NOVISITADO.toString())){
                    OS.add(tablero.getCasilla(EA.getPosX(), EA.getPosY()+i));
                }
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }

        for(int i=0;i<OS.size();i++){
            OS.get(i).setPadre(EA);
        }

        return OS;
    }


    private  boolean GoalTest(Casilla EA){
        EA.setEstado(ESTADO.VISITADO);
        return this.tablero.isCasillaFinal(EA);
    }


    

    private Stack<Casilla> removeRepeate(Stack<Casilla> OS, Stack<Casilla> F){
        Stack<Casilla> aux = new Stack<Casilla>();
        while(!OS.isEmpty()){
            Casilla casillaActual = OS.pop();
            if(!isCasillaRepeat(F, casillaActual)){
                aux.add(casillaActual);
            }
        }

        while(!aux.isEmpty()){
            OS.push(aux.pop());
        }
        return OS;
    }

    private boolean isCasillaRepeat(Stack<Casilla> F, Casilla casillaActual){
        for(Casilla casilla : F){
            if(casilla.getPosX() == casillaActual.getPosX() && casilla.getPosY() == casillaActual.getPosY()){
                return true;
            }
        }
        return false;
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
            if(this.tablero.isCasillaInicial(actual)){
                actual.setEstado(ESTADO.INICIO);
                System.out.println("Solucion encontrada");
                return;
            }else{
                retroceder(actual);
            }
        } 
        
    }

    public void setSolucion(boolean solucion) {
        this.solucion = solucion; }


    }



    


    //*/

    

    

