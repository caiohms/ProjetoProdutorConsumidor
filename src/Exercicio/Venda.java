package Exercicio;

public class Venda {
    String codigoVenda;
    String produto;
    long inicioFabricacao;

    public Venda(String nomeLoja, long numeroVenda, String produto, long inicioFabricacao) {
        this.codigoVenda = nomeLoja + numeroVenda;
        this.produto = produto;
        this.inicioFabricacao = inicioFabricacao;
    }
}
