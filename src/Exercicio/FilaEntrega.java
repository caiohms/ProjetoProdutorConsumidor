package Exercicio;

import javax.swing.*;
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

    public DefaultListModel<String> getListModel() {

        DefaultListModel<String> model = new DefaultListModel<>();

        // copiamos a queue para nao a modificarmos enquanto a iteramos.. ainda assim pode acontecer de modificarmos
        // enquanto estamos copiando, resultando em ArrayIndexOutOfBoundsException. entao apenas ignoramos a exception
        try {
            Queue<Entrega> q = new LinkedList<>(filaEntregas);
            for (Entrega e : q) {
                model.addElement(e.codigoVenda);
            }
        } catch (Exception ignored) {}
        return model;
    }

    public int getSize() {
        return filaEntregas.size();
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
