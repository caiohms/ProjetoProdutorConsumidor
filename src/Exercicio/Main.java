package Exercicio;

import javax.swing.plaf.multi.MultiInternalFrameUI;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

// Versão 4

public class Main {

    public static long startTime;

    public static void main(String[] args) {

        ArrayList<Long> tFabricacaoLog = new ArrayList<>();
        ArrayList<Long> tEntregaLog = new ArrayList<>();

        startTime = System.nanoTime();

        // 1 minuto = 10ms

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

        Semaphore mutexLogFab = new Semaphore(1);
        Semaphore mutexLogEnt = new Semaphore(1);

        Loja lojaA = new Loja("A", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaB = new Loja("B", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaC = new Loja("C", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaD = new Loja("D", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaE = new Loja("E", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaF = new Loja("F", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaG = new Loja("G", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);
        Loja lojaH = new Loja("H", filaVendas, mutexVendas, itensFilaVendas, espacosFilaVendas);

        Fabricante fabricanteA = new Fabricante("A", filaVendas, filaEntregas, mutexVendas, itensFilaVendas,
                espacosFilaVendas, mutexEntregas, itensFilaEntregas, espacosFilaEntregas, tFabricacaoLog, mutexLogFab);
        Fabricante fabricanteB = new Fabricante("B", filaVendas, filaEntregas, mutexVendas, itensFilaVendas,
                espacosFilaVendas, mutexEntregas, itensFilaEntregas, espacosFilaEntregas, tFabricacaoLog, mutexLogFab);
        Fabricante fabricanteC = new Fabricante("C", filaVendas, filaEntregas, mutexVendas, itensFilaVendas,
                espacosFilaVendas, mutexEntregas, itensFilaEntregas, espacosFilaEntregas, tFabricacaoLog, mutexLogFab);
        Fabricante fabricanteD = new Fabricante("D", filaVendas, filaEntregas, mutexVendas, itensFilaVendas,
                espacosFilaVendas, mutexEntregas, itensFilaEntregas, espacosFilaEntregas, tFabricacaoLog, mutexLogFab);

        Transportadora transportadoraA = new Transportadora("A", mutexEntregas, itensFilaEntregas,
                espacosFilaEntregas, filaEntregas, tEntregaLog, mutexLogEnt);
        Transportadora transportadoraB = new Transportadora("B", mutexEntregas, itensFilaEntregas,
                espacosFilaEntregas, filaEntregas, tEntregaLog, mutexLogEnt);

        GUI myFrame = new GUI(filaVendas, filaEntregas, startTime, tFabricacaoLog, tEntregaLog,
                fabricanteA.itensFabricados, fabricanteB.itensFabricados, fabricanteC.itensFabricados,
                fabricanteD.itensFabricados, transportadoraA.itensTransportados, transportadoraB.itensTransportados,
                lojaA.itensVendidos, lojaB.itensVendidos, lojaC.itensVendidos, lojaD.itensVendidos, lojaE.itensVendidos,
                lojaF.itensVendidos, lojaG.itensVendidos, lojaH.itensVendidos, mutexLogFab, mutexLogEnt);

        Thread userGui = new Thread(myFrame);

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

        userGui.start();
    }
}
