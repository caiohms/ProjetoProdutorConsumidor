package Exercicio;

import java.util.concurrent.Semaphore;

// Versão 2

public class Main {

    public static void main(String[] args) {

        // QUANTAS VENDAS CABEM NA FILA DE VENDAS/ENTREGAS?
        final int tamanhoFila = 100;

        FilaVenda filaVendas = new FilaVenda(tamanhoFila);
        FilaEntrega filaEntregas = new FilaEntrega(tamanhoFila);

        Semaphore mutexVendas = new Semaphore(1);
        Semaphore mutexEntregas = new Semaphore(1);

        /*
         nota: decidir a limitação entre os semáforos ou lógica nas funções insert e remove nas classes FilaEntrega e
         FilaVenda. ambas estão implementadas.
        */
        Semaphore itensFilaVendas = new Semaphore(0);
        Semaphore espacosFilaVendas = new Semaphore(tamanhoFila);

        Semaphore itensFilaEntregas = new Semaphore(0);
        Semaphore espacosFilaEntregas = new Semaphore(tamanhoFila);

        Loja lojaA = new Loja("A", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);

        Fabricante fabricanteA = new Fabricante("A", filaVendas, filaEntregas, mutexVendas, itensFilaVendas,
                espacosFilaVendas, mutexEntregas, itensFilaEntregas, espacosFilaEntregas);

        Transportadora transportadoraA = new Transportadora("A", mutexEntregas, itensFilaEntregas,
                espacosFilaEntregas, filaEntregas);

        lojaA.start();
        fabricanteA.start();
        transportadoraA.start();
    }
}
