package Exercicio;

import java.util.LinkedList;
import java.util.Queue;

public class FilaVenda {

    public Queue<Venda> FilaVendas;
    public int maxSize;

    public FilaVenda(int maxSize) {
        this.FilaVendas = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public void insertVenda(Venda venda) {
        while (true) {
            // talvez seja desnecessario pois o semaphore deve controlar o limite
            if (FilaVendas.size() < maxSize) {
                FilaVendas.add(venda);
                return;
            }
        }
    }

    public Venda removeVenda() {
        while (true) {
            // talvez seja desnecessario pois o semaphore deve controlar o limite
            if (!FilaVendas.isEmpty()) {
                return FilaVendas.remove();
            }
        }
    }
}
