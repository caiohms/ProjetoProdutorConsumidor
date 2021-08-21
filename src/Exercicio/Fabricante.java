package Exercicio;

import java.util.concurrent.Semaphore;

public class Fabricante extends Thread {

    String name;
    FilaVenda filaVenda;
    Semaphore mutex;
    Semaphore itens;
    Semaphore espacos;

    public Fabricante(String name, FilaVenda filaVenda, Semaphore mutex, Semaphore itens, Semaphore espacos) {
        this.name = name;
        this.filaVenda = filaVenda;
        this.mutex = mutex;
        this.itens = itens;
        this.espacos = espacos;
    }

    public void run() {
        while (true) {
            // o fabricante deve remover itens da fila de vendas
            // e esperar um tempo ate concluir

            // manipulacao do array filaVenda deve estar em mutex

            try {

                itens.acquire();
                mutex.acquire();
                Venda vendaSendoProcessada = filaVenda.removeVenda();
                mutex.release();
                espacos.release();

                System.out.println("Fabricante " + name + " processando venda " + vendaSendoProcessada.codigoVenda);

                Thread.sleep(2000);

            } catch (InterruptedException e) {
                System.out.println("Ocorreu um erro");
                e.printStackTrace();
            }
        }
    }

}
