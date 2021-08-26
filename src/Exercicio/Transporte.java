package Exercicio;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Transporte extends Thread {
    // thread que corresponde à entrega de um produto fabricado
    // uma instância dessa classe é criada pelo código da thread Transportadora para cada objeto removido
    // da fila de entregas
    // a thread deve dormir por um tempo aleatório para simular um tempo de entrega

    String nomeTransportadora;
    Entrega entrega;
    Semaphore mutexLog;
    ArrayList<Long> tEntregaLog;
    int[] transportesDisponiveis;

    int[][] temposDeEntrega = {
            {1000, 2000},
            {4000, 6000}
    };

    public Transporte(String nomeTransportadora, Entrega entrega, int[] transportesDisponiveis,
                      ArrayList<Long> tEntregaLog, Semaphore mutexLog) {
        this.nomeTransportadora = nomeTransportadora;
        this.entrega = entrega;
        this.transportesDisponiveis = transportesDisponiveis;
        this.tEntregaLog = tEntregaLog;
        this.mutexLog = mutexLog;
    }

    public void run() {
        System.out.println("Transportadora " + nomeTransportadora + " iniciou entrega: " + entrega.codigoVenda);
        aguardarEntrega(nomeTransportadora); // delay
        System.out.println("Transportadora " + nomeTransportadora + " entregou " + entrega.codigoVenda);
        transportesDisponiveis[0]++;

        long tempoConsumido = System.nanoTime() - entrega.inicioEntrega;

        try {
            mutexLog.acquire();
            tEntregaLog.add(tempoConsumido);
            mutexLog.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void aguardarEntrega(String nomeTransportadora) {
        int min, max;

        try {
            min = temposDeEntrega
                    [(int) nomeTransportadora.charAt(0) - (int) "A".charAt(0)]
                    [0];
            max = temposDeEntrega
                    [(int) nomeTransportadora.charAt(0) - (int) "A".charAt(0)]
                    [1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("nomeTransportadora inexistente");
            e.printStackTrace();
            return;
        }

        try {
            Random r = new Random();
            Thread.sleep(min + r.nextInt(max - min));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}