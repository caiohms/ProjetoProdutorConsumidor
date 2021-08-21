package Exercicio;

import java.util.concurrent.Semaphore;

public class Fabricante extends Thread {

    String name;
    FilaVenda filaVenda;
    Semaphore semaphore;

    public Fabricante(String name, FilaVenda filaVenda, Semaphore semaphore) {
        this.name = name;
        this.filaVenda = filaVenda;
        this.semaphore = semaphore;
    }

    public void run() {
        while (true) {

        }
    }
}
