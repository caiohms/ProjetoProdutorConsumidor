package Exercicio;

import java.util.LinkedList;
import java.util.Queue;

public class FilaEntrega {
    // há somente 1 objeto dessa classe no main
    // implementa uma fila que armazena objetos Entrega

    Queue<Entrega> filaEntregas;
    public int maxSize;

    public FilaEntrega(int maxSize) {
        this.filaEntregas = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public void insertEntrega(Entrega entrega) {
        while (true) {
            // talvez seja desnecessario pois o semaphore deve controlar o limite
            if (filaEntregas.size() < maxSize) {
                filaEntregas.add(entrega);
                return;
            }
        }
    }

    public Entrega removeEntrega() {
        while (true) {
            // talvez seja desnecessario pois o semaphore deve controlar o limite
            if (!filaEntregas.isEmpty()) {
                return filaEntregas.remove();
            }
        }
    }
}
