package Exercicio;

import java.util.concurrent.Semaphore;

// Versão 1

public class Main {

    public static void main(String[] args) {

        FilaVenda filaVenda = new FilaVenda();

        Semaphore semaphoreFilaVendas = new Semaphore(1);

        Loja lojaA = new Loja("A", filaVenda, semaphoreFilaVendas);

        Fabricante fabricanteA = new Fabricante("A", filaVenda, semaphoreFilaVendas);

    }
}
