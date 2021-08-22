package Exercicio;

import java.util.concurrent.Semaphore;

public class Transportadora extends Thread {

    String name;
    Semaphore mutex;
    Semaphore itens;
    Semaphore espacos;
    FilaEntrega filaEntregas;
    int[] transportesSimultaneos = {10, 20};
    int[] transportesDisponiveis;

    public Transportadora(String name, Semaphore mutex, Semaphore itens, Semaphore espacos, FilaEntrega filaEntregas) {
        this.name = name;
        this.mutex = mutex;
        this.itens = itens;
        this.espacos = espacos;
        this.filaEntregas = filaEntregas;
        this.transportesDisponiveis = new int[]{0};
        this.transportesDisponiveis[0] = getTransportesSimultaneos();
    }

    public void run() {

        Semaphore scoreboardMutex = new Semaphore(1);

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

                Transporte transporte = new Transporte(name, entrega, scoreboardMutex, transportesDisponiveis);

                transporte.start();

            } catch (InterruptedException e) {
                System.out.println("Ocorreu um erro");
                e.printStackTrace();
            }
        }
    }

    public int getTransportesSimultaneos() {
        return transportesSimultaneos[(int) name.charAt(0) - (int) "A".charAt(0)];
    }

}
