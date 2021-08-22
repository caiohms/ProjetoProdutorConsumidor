package Exercicio;

import java.util.concurrent.Semaphore;

public class Transportadora extends Thread {

    String name;
    Semaphore mutex;
    Semaphore itens;
    Semaphore espacos;
    FilaEntrega filaEntregas;

    public Transportadora(String name, Semaphore mutex, Semaphore itens, Semaphore espacos, FilaEntrega filaEntregas) {
        this.name = name;
        this.mutex = mutex;
        this.itens = itens;
        this.espacos = espacos;
        this.filaEntregas = filaEntregas;
    }

    public void run() {
        while (true) {
            // a transportadora remove itens da fila de entregas e leva um delay até efetuar a entrega.
            // array da fila de entregas deve ser regulado por mutex.

            try {
                itens.acquire();
                mutex.acquire();
                Entrega entrega = filaEntregas.removeEntrega();
                mutex.release();
                espacos.release();

                System.out.println("Transportadora " + name + " entregando pacote da venda " + entrega.codigoVenda);

                Thread.sleep(4000); // delay da entrega
                System.out.println("Transportadora " + name + " entregou " + entrega.codigoVenda);

            } catch (InterruptedException e) {
                System.out.println("Ocorreu um erro");
                e.printStackTrace();
            }
        }
    }

}
