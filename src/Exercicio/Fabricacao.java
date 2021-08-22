package Exercicio;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Fabricacao extends Thread {
    // thread que corresponde a fabricacao de um produto vendido.
    // uma instancia dessa classe e criada pelo codigo da thread Fabricante para cada objeto removido da fila de vendas.
    // depois de dormir por um tempo aleatorio para simular um tempo de fabricacao, a thread deve inserir uma nova
    // instancia da classe Entrega na fila de entregas.

    Venda venda;
    String nomeFabricante;
    FilaEntrega filaEntregas;
    Semaphore mutex, itens, espacos;
    int[] producaoDisponivel;

    int[][][] tabalaIntervalos = {
            {{6000, 10000}, {2000, 4000}, {10000, 12000}, {4000, 6000}, {8000, 10000}, {14000, 16000}, {4000, 6000},
                    {8000, 10000}},
            {{4000, 6000}, {8000, 10000}, {12000, 14000}, {8000, 10000}, {2000, 4000}, {10000, 12000}, {10000, 12000},
                    {6000, 8000}},
            {{10000, 12000}, {12000, 14000}, {4000, 6000}, {6000, 8000}, {4000, 6000}, {4000, 6000}, {10000, 12000},
                    {4000, 6000}},
            {{8000, 10000}, {6000, 8000}, {4000, 6000}, {10000, 12000}, {12000, 14000}, {8000, 10000}, {6000, 8000},
                    {12000, 14000}}};

    public Fabricacao(Venda venda, String nomeFabricante, FilaEntrega filaEntregas, Semaphore mutex, Semaphore itens,
                      Semaphore espacos, int[] producaoDisponivel) {
        this.venda = venda;
        this.nomeFabricante = nomeFabricante;
        this.filaEntregas = filaEntregas;
        this.mutex = mutex;
        this.itens = itens;
        this.espacos = espacos;
        this.producaoDisponivel = producaoDisponivel;
    }

    public void run() {

        System.out.println("Fabricante " + nomeFabricante + " iniciou producao: " + venda.codigoVenda);
        aguardarProducao(nomeFabricante, venda.produto); // delay

        // apos fabricado, cria entrega e insere na fila de entregas
        System.out.println("Fabricante " + nomeFabricante + " terminou: " + venda.codigoVenda);

        producaoDisponivel[0]++;

        Entrega entrega = new Entrega(venda.codigoVenda, venda.produto);
        entregar(entrega);
    }

    public void entregar(Entrega entrega) {
        try {
            espacos.acquire();
            mutex.acquire();
            filaEntregas.insertEntrega(entrega);
            mutex.release();
            itens.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void aguardarProducao(String nomeFabricante, String produto) {
        int min, max;

        try {
            min = tabalaIntervalos
                    [(int) nomeFabricante.charAt(0) - (int) "A".charAt(0)]
                    [(int) produto.charAt(0) - (int) "A".charAt(0)]
                    [0];
            max = tabalaIntervalos
                    [(int) nomeFabricante.charAt(0) - (int) "A".charAt(0)]
                    [(int) produto.charAt(0) - (int) "A".charAt(0)]
                    [1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Produto/Fabricante inexistente");
            e.printStackTrace();
            return;
        }

        Random r = new Random();

        try {
            Thread.sleep(min + r.nextInt(max - min));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
