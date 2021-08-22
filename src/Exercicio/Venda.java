package Exercicio;

public class Venda {
    String codigoVenda;
    String produto;

    public Venda(String nomeLoja, long numeroVenda, String produto) {
        this.codigoVenda = nomeLoja + numeroVenda;
        this.produto = produto;
    }
}
