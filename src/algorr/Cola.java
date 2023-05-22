package algorr;
import algorr.Proceso;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Crash
 */
public class Cola {

    //ATRIBUTOS
    private int cont; //Cantidad de nodos en la cola
    static int idSegundo = 0;
    private Proceso temp_node; //nodoTemporal apuntarÃ¡ al ultimo nodo agregado
    private Proceso first_node; //Apunta siempre al primer nodo de la cola, se actualiza siempre que se desencole algo
    private Proceso node_print; //Este nodo solo se usa en las impresiones, para evitar corrupciones
    private String myname;
    
    public Cola(){
        
    }
    
    //CONSTRUCTORES
    public Cola(String name) {
        this.myname = name;
        this.temp_node = new Proceso(); //Se inicializa con nodo siguiente igual a null
        this.first_node = this.temp_node;
        this.node_print = new Proceso();
        this.cont = 0;
    }

    //MÃ‰TODOS
    //Metodo para insertar un nodo a la cola
 
    public void insertar(Proceso nodo) {

        if (cont == 0) {
            //Cola vacÃ­a
        	first_node = nodo;
        } else {
        	temp_node.setSiguiente(nodo);
        }
        temp_node = nodo;
        cont++;
    }

    //Retorna el primer valor de la cola y lo quita
    public Proceso desencolar() {

        if (cont == 0) {
            return null;
        } else {

            Proceso headAntiguo = first_node;
            try {
            	first_node = first_node.getSiguiente();
            } catch (NullPointerException e) {
            }

            cont--;
            return headAntiguo;
        }
    }

    
    public void imprimirColaCompleta() {
        try {
        	node_print = first_node;

            System.out.println("\n\t\tMostrando cambios en la cola '" + myname);
            System.out.println("\t ___________________________________________________________________________");
            System.out.println("\t| ID | Nombre |  Memoria [kb] | Tiempo llegada [ms] | Tiempo ejecucion [ms] |");

            for (int i = 0; i < cont; i++) { 
                String showID = String.format("%03d", node_print.id ); 
                String showName = node_print.name.substring(0, Math.min(node_print.name.length(), 6));
                String showMemoSize    = String.format("%03d", node_print.tam); 
                String showArriveTime  = String.format("%03d", node_print.tiempoLlegada);
                //String showTimeService = String.format("%03d", node_print.serv_time); 
                String showTimeService = String.format("%03d", node_print.tiempoFaltante); 
                
                
                if (i == 0) {  //head
                    System.out.println("\t| " + showID + 
                                         "| " + showName +  
                                        " |\t" + showMemoSize + 
                                "         |   " + showArriveTime +
                          "               |   "  + showTimeService + 
                        "                 | \t <-------------------- Head");

                } else if (i == cont - 1) { //tail
                    System.out.println("\t| " + showID + 
                                         "| " + showName +  
                                        " |\t" + showMemoSize + 
                                "         |   " + showArriveTime +
                          "               |   "  + showTimeService + 
                        "                 | \t <-------------------- Tail");

                } else { //Entre head y tail
                    System.out.println("\t| " + showID + 
                                         "| " + showName +  
                                        " |\t" + showMemoSize + 
                                "         |   " + showArriveTime +
                          "               |   "  + showTimeService + 
                        "                 | \t  ");
                }

                node_print = node_print.getSiguiente();
            }

            System.out.println("\t ___________________________________________________________________________\n\n");

        } catch (NullPointerException e) {
        }
    }

    
    public void imprimirDatosFinales() {
        try {
        	node_print = first_node;

            int resp, esp, ejec;
            int respProm = 0;
            int espProm = 0;
            int ejecProm = 0;

            System.out.println("\n\n                      Resumen de la ejecucion de los procesos                  ");
            System.out.println("\t_______________________________________________________________________________");
            System.out.println("\t| Nombre | T. Ejecucion | T. Espera | T. Respuesta | T. Llegada | T. Servicio |");

            for (int i = 0; i < cont; i++) {
                String showName = node_print.name.substring(0, Math.min(node_print.name.length(), 8));
                ejec = node_print.tiempoTotal - node_print.tiempoLlegada;
                String showTejec = String.format("%03d", ejec); 

                esp = ejec - node_print.serv_time;
                String showTesp = String.format("%03d", esp); 

                resp = node_print.tiempoEntrada - node_print.tiempoLlegada;
                String showTresp = String.format("%03d", resp); 

                String showArriveTime = String.format("%03d", node_print.tiempoLlegada); 
                String showTimeService = String.format("%03d", node_print.tiempoLlegada);

                System.out.println("\t|   " + showName + 
                                  "   |        " + showTejec + 
                                  "   |     " + showTesp + 
                                  "   |     " + showTresp + 
                               "      |     " + showArriveTime + 
                                 "    |     " + showTimeService + "     |");

                ejecProm += ejec;
                espProm += esp;
                respProm += resp;

                node_print = node_print.getSiguiente();
            }
            System.out.println("\t_______________________________________________________________________________"); 

            System.out.println("\nEjecucion promedio: " + ejecProm / cont + "\nEspera promedio: "
                    + espProm / cont + "\nRespuesta promedio: " + respProm / cont);

        } catch (NullPointerException e) {
        }
    }

    public int getCantidad() {
        return this.cont;
    }


}

