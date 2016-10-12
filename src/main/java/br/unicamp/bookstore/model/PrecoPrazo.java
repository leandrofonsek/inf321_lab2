package br.unicamp.bookstore.model;

public class PrecoPrazo {

    private String codigo;
    private String valor;
    private String prazoEntrega;
    private String valorMaoPropria;
    private String valorAvisoRecebimento;
    private String valorDeclarado;
    private String entregaDomiciliar;
    private String entregaSabado;
    private String mensagem;
    private String erro;
    
    public String getMensagem() {
        if (erro.equals("0")) {
            return valor + ";" + prazoEntrega;
        } else {
            return mensagem;
        }
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public String getPrazoEntrega() {
        return prazoEntrega;
    }
    public void setPrazoEntrega(String prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }
    public String getValorMaoPropria() {
        return valorMaoPropria;
    }
    public void setValorMaoPropria(String valorMaoPropria) {
        this.valorMaoPropria = valorMaoPropria;
    }
    public String getValorAvisoRecebimento() {
        return valorAvisoRecebimento;
    }
    public void setValorAvisoRecebimento(String valorAvisoRecebimento) {
        this.valorAvisoRecebimento = valorAvisoRecebimento;
    }
    public String getValorDeclarado() {
        return valorDeclarado;
    }
    public void setValorDeclarado(String valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }
    public String getEntregaDomiciliar() {
        return entregaDomiciliar;
    }
    public void setEntregaDomiciliar(String entregaDomiciliar) {
        this.entregaDomiciliar = entregaDomiciliar;
    }
    public String getEntregaSabado() {
        return entregaSabado;
    }
    public void setEntregaSabado(String entregaSabado) {
        this.entregaSabado = entregaSabado;
    }
    public String getErro() {
        return erro;
    }
    public void setErro(String erro) {
        this.erro = erro;
    }

}
