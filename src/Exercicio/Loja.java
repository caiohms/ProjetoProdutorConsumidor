package Exercicio;

import java.util.concurrent.Semaphore;

public class Loja extends Thread{

    String name;
    int numeroVendas;
    FilaVenda filaVenda;
    Semaphore semaphore;

    public Loja(String name, FilaVenda filaVenda, Semaphore semaphore) {
        this.name = name;
        this.filaVenda = filaVenda;
        this.semaphore = semaphore;
    }

    public void run() {

    }
}
