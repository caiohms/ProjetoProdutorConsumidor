package Exercicio;

import java.util.concurrent.Semaphore;

// Versão 1

public class Main {

    public static void main(String[] args) {

        // QUANTAS VENDAS CABEM NA FILA DE VENDAS?
        final int tamanhoFila = 100;

        FilaVenda filaVenda = new FilaVenda(tamanhoFila);

        Semaphore mutex = new Semaphore(1);
        Semaphore itens = new Semaphore(0);
        Semaphore espacos = new Semaphore(tamanhoFila);

        Loja lojaA = new Loja("A", filaVenda, mutex, itens, espacos);

        Fabricante fabricanteA = new Fabricante("A", filaVenda, mutex, itens, espacos);

        lojaA.start();
        fabricanteA.start();
    }
}
