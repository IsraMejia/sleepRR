/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorr;
import java.util.Scanner;

/**
 *
 * @author Crash
 */
public class AlgoRR {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) { 
        int ramSize , maxProcesSize, quantumSize, nProcess;
        Cola proces_q = new Cola("Cola de procesos cargados"); //Instancia e inicializa la cola de Procesos
        presentacion();         
        int[] parameters  = definicionParametros();  
        ramSize = parameters[0] ;
        maxProcesSize = parameters[1];
        quantumSize = parameters[2];
        nProcess = parameters[3] ;
        System.out.println("\t\tParametros del sistema guardados");

        Proceso[] procesosDesordenados = new Proceso[nProcess];
        procesosDesordenados = definicionProcesos( nProcess, maxProcesSize);
         
        boolean correctData = true;
        if(nProcess > 0){
            for (Proceso p : procesosDesordenados){
                if(p.tam > maxProcesSize){
                    correctData = false;
                    break;
                }
            }
        }
        

        if (correctData == true){
            System.out.println("Se ingresaron los datos correctamente");
            Proceso[] procesosOrdenados = new Proceso[nProcess];
            procesosOrdenados = ordenarProcesos( procesosDesordenados);
            for (int i = 0; i < nProcess; i++) { //ciclo que llena la cola de Procesos con los procesos ordenados
            	proces_q.insertar(procesosOrdenados[i]);
            }
            System.out.println("Estos han sido los procesos cargados al sistema:");
            proces_q.imprimirColaCompleta();
            /**
            Simulador simulador = new Simulador(      ); 
            simulador.iniciar(); 
            */   
            Cola ready_q = new Cola("Cola de procesos listos");          
            AdminProcesosListos llegadaP = new AdminProcesosListos(proces_q, ready_q, ramSize);            
            Cola finished_q = new Cola("Cola de procesos terminados");
            Cpu cpu = new Cpu(quantumSize, llegadaP, finished_q);

            llegadaP.start();
            cpu.start();
            try {
                cpu.join();
            } catch (InterruptedException ex) {
            } 
            finished_q.imprimirDatosFinales();
            System.out.println("\n\n\t\tFin de ejecucion");

        } else{
            System.out.println("\n\n\tAviso:Un proceso excede la capacidad de memoria disponible en RAM:"+ maxProcesSize);
            System.out.println("\t Debido a esto, termina el simulador. \n\t Hasta luego, ha sido un placer atenderlo :D");
        }
        

        
    }

    public static void presentacion(){ 
        System.out.println("\n\n");
        System.out.println("******************************************");
        System.out.println("          Facultad de Ingenieria          ");
        System.out.println("Materia: Sistemas Operativos              ");
        System.out.println("Profesora: Ing. Patricia Del Valle Morales");
        System.out.println("Alumnos:");
        System.out.println("Mejia Alba Israel Hipolito");
        System.out.println("ZuÃ±iga Salgado Luis Eduardo ");
    }

    public static int[] definicionParametros(){
        int ramSize , maxProcesSize, quantumSize, nProcess;
        Scanner scan = new Scanner(System.in);
        System.out.println("\nIniciemos definiendo ciertos parametros del programa");
        System.out.println("Cuanta memoria RAM (kb) tendra nuestro simualdor  :");
        ramSize = maxProcesSize = scan.nextInt();
        System.out.println("Definamos el valor de cada Quantom (ms) :");    
        quantumSize = scan.nextInt();
        System.out.println("Cuantos procesos tendra la simulacion del Algoritmo Round Robin?");
        nProcess = scan.nextInt();
        int[] parameters = {ramSize, maxProcesSize, quantumSize, nProcess};
        return parameters;
    }

    public static Proceso[] definicionProcesos(int nProcess, int maxProcesSize){
        Proceso[] procesosDesordenados = new Proceso[nProcess];
        String processName;
        int indiceNormal, horaLlegada, tiempoEjecucion, processMsize;
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < nProcess; i++) { //ciclo que solicita datos y guarda los procesos en procesosDesordenados
            indiceNormal = i + 1; 
            System.out.println("\n\t ¿Cual es el Nombre del proceso "+i + " ? ");
            processName = scan.next();
            System.out.println(" ¿Cuanta memoria ocupara el proceso " + processName + " en [kb]? ");
            processMsize = scan.nextInt(); 
            System.out.println(" ¿Cual es el tiempo de llegada del proceso " + processName + " en [ms]? ");
            horaLlegada = scan.nextInt();
            System.out.println(" ¿Cual es el tiempo de servicio del proceso " + processName + " en [ms]? ");
            tiempoEjecucion = scan.nextInt();   
                    
            
            procesosDesordenados[i] = new Proceso(
                indiceNormal, 
                processName, 
                processMsize, 
                horaLlegada, 
                tiempoEjecucion,
                processMsize
            );
        }
        return procesosDesordenados;

    }

    public static Proceso[] ordenarProcesos(Proceso[] procesosDesordenados) {
        for (int x = 0; x < procesosDesordenados.length; x++) {
            for (int i = 0; i < procesosDesordenados.length - x - 1; i++) {
                
                if (procesosDesordenados[i].tiempoLlegada > procesosDesordenados[i + 1].tiempoLlegada) {
                    Proceso tmp = procesosDesordenados[i + 1];
                    procesosDesordenados[i + 1] = procesosDesordenados[i];
                    procesosDesordenados[i] = tmp;
                }
            }
        }
        return procesosDesordenados;
    }
    
}
