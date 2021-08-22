package Exercicio;

import java.util.concurrent.Semaphore;

public class Fabricante extends Thread {

    String name;
    FilaVenda filaVenda;
    FilaEntrega filaEntregas;
    Semaphore mutexVendas, itensVendas, espacosVendas, mutexEntregas, itensEntregas, espacosEntregas;

    public Fabricante(String name, FilaVenda filaVenda, FilaEntrega filaEntregas, Semaphore mutexVendas,
                      Semaphore itensVendas, Semaphore espacosVendas, Semaphore mutexEntregas, Semaphore itensEntregas,
                      Semaphore espacosEntregas) {
        this.name = name;
        this.filaVenda = filaVenda;
        this.filaEntregas = filaEntregas;
        this.mutexVendas = mutexVendas;
        this.itensVendas = itensVendas;
        this.espacosVendas = espacosVendas;
        this.mutexEntregas = mutexEntregas;
        this.itensEntregas = itensEntregas;
        this.espacosEntregas = espacosEntregas;
    }

    public void run() {
        while (true) {
            // o fabricante deve remover itens da fila de vendas
            // e esperar um tempo ate concluir

            // manipulacao do array filaVenda deve estar em mutex

            try {

                itensVendas.acquire();
                mutexVendas.acquire();
                Venda venda = filaVenda.removeVenda();
                mutexVendas.release();
                espacosVendas.release();

                System.out.println("Fabricante " + name + " processando venda " + venda.codigoVenda);

                Thread.sleep(2000); // delay da fabricacao

                // apos fabricado, cria entrega e insere na fila de entregas
                System.out.println("Fabricante " + name + " terminou " + venda.codigoVenda);

                Entrega entrega = new Entrega(venda.codigoVenda, venda.produto);

                espacosEntregas.acquire();
                mutexEntregas.acquire();
                filaEntregas.insertEntrega(entrega);
                mutexEntregas.release();
                itensEntregas.release();

            } catch (InterruptedException e) {
                System.out.println("Ocorreu um erro");
                e.printStackTrace();
            }
        }
    }

}
