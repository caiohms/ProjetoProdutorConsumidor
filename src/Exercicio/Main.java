package Exercicio;

import java.util.concurrent.Semaphore;

// Versão 3

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
        Loja lojaB = new Loja("B", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaC = new Loja("C", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaD = new Loja("D", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaE = new Loja("E", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaF = new Loja("F", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaG = new Loja("G", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaH = new Loja("H", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);

        Fabricante fabricanteA = new Fabricante("A", filaVendas, filaEntregas, mutexVendas, itensFilaVendas,
                espacosFilaVendas, mutexEntregas, itensFilaEntregas, espacosFilaEntregas);
        Fabricante fabricanteB = new Fabricante("B", filaVendas, filaEntregas, mutexVendas, itensFilaVendas,
                espacosFilaVendas, mutexEntregas, itensFilaEntregas, espacosFilaEntregas);
        Fabricante fabricanteC = new Fabricante("C", filaVendas, filaEntregas, mutexVendas, itensFilaVendas,
                espacosFilaVendas, mutexEntregas, itensFilaEntregas, espacosFilaEntregas);
        Fabricante fabricanteD = new Fabricante("D", filaVendas, filaEntregas, mutexVendas, itensFilaVendas,
                espacosFilaVendas, mutexEntregas, itensFilaEntregas, espacosFilaEntregas);

        Transportadora transportadoraA = new Transportadora("A", mutexEntregas, itensFilaEntregas,
                espacosFilaEntregas, filaEntregas);
        Transportadora transportadoraB = new Transportadora("B", mutexEntregas, itensFilaEntregas,
                espacosFilaEntregas, filaEntregas);

        lojaA.start();
        lojaB.start();
        lojaC.start();
        lojaD.start();
        lojaE.start();
        lojaF.start();
        lojaG.start();
        lojaH.start();

        fabricanteA.start();
        fabricanteB.start();
        fabricanteC.start();
        fabricanteD.start();

        transportadoraA.start();
        transportadoraB.start();
    }
}
