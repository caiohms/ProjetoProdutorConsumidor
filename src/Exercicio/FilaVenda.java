package Exercicio;

import javax.swing.*;
import java.util.*;

public class FilaVenda {

    private Queue<Venda> filaVendas;
    private int maxSize;

    public FilaVenda(int maxSize) {
        this.filaVendas = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public DefaultListModel<String> getListModel() {

        DefaultListModel<String> model = new DefaultListModel<>();

        // copiamos a queue para nao a modificarmos enquanto a iteramos.. ainda assim pode acontecer de modificarmos
        // enquanto estamos copiando, resultando em ArrayIndexOutOfBoundsException. entao apenas ignoramos a exception
        try {
            Queue<Venda> q = new LinkedList<>(filaVendas);
            for (Venda v : q) {
                model.addElement(v.codigoVenda);
            }
        } catch (Exception ignored) {
        }
        return model;
    }

    public int getSize() {
        return filaVendas.size();
    }

    public void insertVenda(Venda venda) {
        while (true) {
            // talvez seja desnecessario pois o semaphore deve controlar o limite

            // na verdade esse while true provavelmente vai travar sem o semaphore
            if (filaVendas.size() < maxSize) {
                filaVendas.add(venda);
                return;
            }
        }
    }

    public Venda removeVenda() {
        while (true) {
            // talvez seja desnecessario pois o semaphore deve controlar o limite
            if (!filaVendas.isEmpty()) {
                return filaVendas.remove();
            }
        }
    }
}
