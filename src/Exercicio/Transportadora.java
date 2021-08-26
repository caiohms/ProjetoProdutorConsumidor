package Exercicio;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Transportadora extends Thread {

    String name;
    Semaphore mutex,itens,espacos, mutexLog;
    FilaEntrega filaEntregas;
    int[] transportesSimultaneos = {10, 20};
    int[] transportesDisponiveis;
    ArrayList<Long> tEntregaLog;
    public int[] itensTransportados = {0};

    public Transportadora(String name, Semaphore mutex, Semaphore itens, Semaphore espacos, FilaEntrega filaEntregas,
                          ArrayList<Long> tEntregaLog, Semaphore mutexLog) {
        this.name = name;
        this.mutex = mutex;
        this.itens = itens;
        this.espacos = espacos;
        this.filaEntregas = filaEntregas;
        this.transportesDisponiveis = new int[]{0};
        this.transportesDisponiveis[0] = getTransportesSimultaneos();
        this.tEntregaLog = tEntregaLog;
        this.mutexLog = mutexLog;
    }

    public void run() {

        Semaphore scoreboardMutex = new Semaphore(1);

        while (true) {
            // a transportadora remove itens da fila de entregas e leva um delay até efetuar a entrega.
            // array da fila de entregas deve ser regulado por mutex.

            try {
                System.out.println(transportesDisponiveis[0]);
                scoreboardMutex.acquire();
                {
                    while (transportesDisponiveis[0] == 0) {
                        Thread.sleep(10);
                    }
                    transportesDisponiveis[0]--;
                }
                scoreboardMutex.release();

                itens.acquire();
                mutex.acquire();
                Entrega entrega = filaEntregas.removeEntrega();
                mutex.release();
                espacos.release();

                //System.out.println("Transportadora " + name + " entregando pacote da venda " + entrega.codigoVenda);

                Transporte transporte = new Transporte(name, entrega, transportesDisponiveis,
                        tEntregaLog, mutexLog);

                transporte.start();

                itensTransportados[0]++;

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
