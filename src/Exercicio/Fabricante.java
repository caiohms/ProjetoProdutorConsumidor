package Exercicio;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Fabricante extends Thread {

    String name;
    FilaVenda filaVenda;
    FilaEntrega filaEntregas;
    Semaphore mutexVendas, itensVendas, espacosVendas, mutexEntregas, itensEntregas, espacosEntregas;
    int[] producaoSimultanea = {4, 1, 4, 4};
    int[] producaoDisponivel;
    ArrayList<Long> tFabricacaoLog;

    public int[] itensFabricados = {0};

    public Fabricante(String name, FilaVenda filaVenda, FilaEntrega filaEntregas, Semaphore mutexVendas,
                      Semaphore itensVendas, Semaphore espacosVendas, Semaphore mutexEntregas, Semaphore itensEntregas,
                      Semaphore espacosEntregas, ArrayList<Long> tFabricacaoLog) {
        this.name = name;
        this.filaVenda = filaVenda;
        this.filaEntregas = filaEntregas;
        this.mutexVendas = mutexVendas;
        this.itensVendas = itensVendas;
        this.espacosVendas = espacosVendas;
        this.mutexEntregas = mutexEntregas;
        this.itensEntregas = itensEntregas;
        this.espacosEntregas = espacosEntregas;
        this.producaoDisponivel = new int[]{0};
        this.producaoDisponivel[0] = getProducaoSimultanea();
        this.tFabricacaoLog = tFabricacaoLog;
    }

    public void run() {

        // padrao scoreboard
        // https://greenteapress.com/semaphores/LittleBookOfSemaphores.pdf
        // secao 5.1
        //
        // nao faz sentido, assim que o numero maximo de threads for atingido, a thread que zerou o contador ira
        // sinalizar para o gerenciador que o limite foi atingido e este liberará mais 4 threads sem saber se todas
        // as threads finalizaram execução? usar um semaphore só com 4 permits parece resolver... (nao testei).
        // controlar acesso com uma variavel + mutex parece funcionar.

        Semaphore scoreboardMutex = new Semaphore(1);
        int c = 0;
        while (true) {


            try {
                // limitador padrao scoreboard... acho que eh isso
                scoreboardMutex.acquire();
                {
                    while (producaoDisponivel[0] == 0) {
                        Thread.sleep(10);
                    }
                    producaoDisponivel[0]--;
                }
                scoreboardMutex.release();

                itensVendas.acquire();
                mutexVendas.acquire();
                Venda venda = filaVenda.removeVenda();
                mutexVendas.release();
                espacosVendas.release();

                //System.out.println("Fabricante " + name + " processando venda " + venda.codigoVenda);

                Fabricacao fabricacao = new Fabricacao(venda, name, filaEntregas, mutexEntregas, itensEntregas,
                        espacosEntregas, producaoDisponivel, tFabricacaoLog);

                fabricacao.start();

                itensFabricados[0]++;

            } catch (InterruptedException e) {
                System.out.println("Ocorreu um erro");
                e.printStackTrace();
            }
        }
    }

    public int getProducaoSimultanea() {
        return producaoSimultanea[(int) name.charAt(0) - (int) "A".charAt(0)];
    }
}
