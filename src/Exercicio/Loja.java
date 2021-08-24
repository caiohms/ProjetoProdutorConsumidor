package Exercicio;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Loja extends Thread {

    private String name;
    private long numeroVendas;
    private FilaVenda filaVenda;
    private Semaphore mutex, itens, espacos;
    public int[] itensVendidos = {0}; // GUI

    public Loja(String name, FilaVenda filaVenda, Semaphore mutex, Semaphore itens, Semaphore espacos) {
        this.name = name;
        this.filaVenda = filaVenda;
        this.mutex = mutex;
        this.itens = itens;
        this.espacos = espacos;
    }

    public void run() {
        while (true) {
            // Intervalo de tempo entre duas vendas consecutivas na mesma loja: entre 10 e 150 minutos.
            // 1 minuto = 10ms

            Random random = new Random();
            try {
                // delay entre cada venda gerada -> 100ms - 1500ms
                Thread.sleep(100 + random.nextInt(1400));
                realizarVenda();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void realizarVenda() throws InterruptedException {
        numeroVendas++;

        long inicioFabricacao = System.nanoTime();
        Venda venda = new Venda(name, numeroVendas, "A", inicioFabricacao);

        System.out.println("Loja " + name + " gerou venda " + name + numeroVendas);

        itensVendidos[0]++;

        espacos.acquire();
        mutex.acquire(); // talvez esses semáforos devam ficar dentro da própria funcao insertVenda()
        filaVenda.insertVenda(venda);
        mutex.release();
        itens.release();
    }
}
