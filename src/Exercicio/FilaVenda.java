package Exercicio;

import java.util.LinkedList;
import java.util.Queue;

public class FilaVenda {

    public Queue<Venda> qFilaVendas;
    public int maxSize;

    public FilaVenda(int maxSize) {
        this.qFilaVendas = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public void insertVenda(Venda venda) {
        while (true) {
            // talvez seja desnecessario pois o semaphore deve controlar o limite
            if (qFilaVendas.size() < maxSize) {
                qFilaVendas.add(venda);
                return;
            }
        }
    }

    public Venda removeVenda() {
        while (true) {
            // talvez seja desnecessario pois o semaphore deve controlar o limite
            if (!qFilaVendas.isEmpty()) {
                return qFilaVendas.remove();
            }
        }
    }
}
