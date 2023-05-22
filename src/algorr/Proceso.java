/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorr;

/**
 *
 * @author Crash
 */
public class Proceso {
     //ATRIBUTOS
    public int id; //Id del proceso (numÃ©rico)
    public String name; //nombre del proceso (alfanumÃ©rico)
    public int tam; //TamaÃ±o del proceso
    public int serv_time; //Tiempo que requiere el proceso para su ejecuciÃ³n
    public int prioridad; //Prioridad del proceso
    public int tiempoLlegada; //Tiempo de llegada del proceso
    private Proceso siguiente; //Apuntador hacia el siguiente Proceso

    //Variables que ocupa cpu
    public int tiempoFaltante;
    public int tiempoTotal;
    public boolean first_run;
    public int tiempoEntrada;

    //CONSTRUCTORES
    public Proceso() {
        //NODOS INVOLUCRADOS
        siguiente = null;
    }

    Proceso(int id, String name, int tam, int tiempoLlegada, int serv_time, int prioridad) {
        //NODOS INVOLUCRADOS
        siguiente = null;
        //Inicializacion de atributos
        this.id = id;
        this.name = name;
        this.tam = tam;
        this.serv_time = serv_time;
        this.prioridad = prioridad;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoFaltante = serv_time;
        this.tiempoTotal = tiempoLlegada;
        this.first_run = true;
        this.tiempoEntrada = 0;
    }

    Proceso(int id, String name, int tam, int tiempoLlegada, int serv_time) {
        //NODOS INVOLUCRADOS
        siguiente = null;
        //Inicializacion de atributos
        this.id = id;
        this.name = name;
        this.tam = tam;
        this.serv_time = serv_time;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoFaltante = serv_time;
        this.tiempoTotal = tiempoLlegada;
        this.first_run = true;
        this.tiempoEntrada = 0;
    }
    
    //MÃ‰TODOS
    public void setSiguiente(Proceso siguiente) {
        this.siguiente = siguiente;
    }

    public Proceso getSiguiente() {
        return this.siguiente;
    }
}
