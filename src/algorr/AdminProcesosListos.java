package algorr;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Crash
 */
public class AdminProcesosListos extends Thread {

    //Atributos
    private final Cola loadedP_Q;
    private final Cola ready_q;
    private Proceso procesoTemp;
    private Proceso procesoTempCerrojo;
    private int time_t;
    private boolean avaible;
    boolean all_uploaded;
    int memoRAM;
    boolean proces_waiting;
    boolean ram_ready;

    //Constructores
    public AdminProcesosListos(Cola procesosCargados, Cola ready_proces, int RAM) {
        this.setName("HiloAdminProcesosListos");
        this.loadedP_Q = procesosCargados;
        this.ready_q = ready_proces;
        this.time_t = 0;
        this.avaible = true;
        this.all_uploaded = false;
        this.memoRAM = RAM;
        this.proces_waiting = false;
        this.ram_ready = false;
    }

    //Metodos
    @Override
    public void run() {
        int start_cont = loadedP_Q.getCantidad(); //cantidad fija
        for (int i = 0; i < start_cont; i++) {
            procesoTemp = loadedP_Q.desencolar();

            dormir(procesoTemp.tiempoLlegada - time_t); // duerme lo necesario para que el proceso se insete en su tiempo de llegada           
            time_t = time_t + (procesoTemp.tiempoLlegada - time_t); // Actualiza el tiempo que ha transcurrido en total

            if (memoRAM - procesoTemp.tam >= 0) {
                encolarProcesoListo(procesoTemp);
                System.out.println("Llega el proceso " + procesoTemp.name + " en el tiempo " + procesoTemp.tiempoLlegada + " [ms], tamanio "
                        + procesoTemp.tam + " [k]");
            } else {
            	proces_waiting = true;
                esperarRAM();
                encolarProcesoListo(procesoTemp);
                System.out.println("Llega el proceso " + procesoTemp.name + " con retraso al esperar espacio en RAM, tamanio "
                        + procesoTemp.tam + " [k]");
            }
        }
        all_uploaded = true;
    }

    private void dormir(int tiempo) {
        try {
            sleep(tiempo);
        } catch (InterruptedException ex) {
            System.out.println("Error al dormir");
        }
    }

    public synchronized void encolarProcesoListo(Proceso procesoListo) {
        while (avaible == false) {
            //Se mantiene en este while cuando otro hilo estÃ¡ ocupando este metodo.
            try {
                wait(); //se pone a dormir y cede el monitor
            } catch (InterruptedException e) {
            }
        }
        //Entra aqui cuando otro hilo ha dejado de ocupar este metodo
        avaible = false;//Cierra el cerrojo para que otro hilo no ocupe el metodo
        ready_q.insertar(procesoListo);
        ready_q.imprimirColaCompleta();
        avaible = true;//Abre el cerrojo cuando termina
        notifyAll();
    }

    public synchronized Proceso desencolarProcesoListo() {
        while (avaible == false) {
            //Se mantiene en este while cuando otro hilo estÃ¡ ocupando este metodo.
            try {
                wait(); //se pone a dormir y cede el monitor
            } catch (InterruptedException e) {
            }
        }
        //Entra aqui cuando otro hilo ha dejado de ocupar este metodo
        avaible = false;//Cierra el cerrojo para que otro hilo no ocupe el metodo
        procesoTempCerrojo = ready_q.desencolar();

        avaible = true;//Abre el cerrojo cuando termina
        notifyAll();
        return procesoTempCerrojo;
    }
    
    public synchronized void esperarRAM() { // Esperara hasta que CPU baje un proceso
        while (ram_ready == false) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        notifyAll();
    }


}
