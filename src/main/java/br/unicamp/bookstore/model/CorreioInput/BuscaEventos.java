package br.unicamp.bookstore.model.CorreioInput;

public class BuscaEventos {
	
	private String usuario;
	private String senha;
	private String tipo;
	private String resultado;
	private String lingua;
	private String objetos;
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getLingua() {
		return lingua;
	}
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	public String getObjetos() {
		return objetos;
	}
	public void setObjetos(String objetos) {
		this.objetos = objetos;
	}
	
	public String getXml(){
		
		String xml ="<soapenv:Envelope "
				+ "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
				+ "xmlns:res=\"http://resource.webservice.correios.com.br/>\""
				+ "<soapenv:Header/>"
                +"<soapenv:Body>"
                +"<res:buscaEventos>"
                +"<usuario>" + this.usuario + "</usuario>"
                +"<senha>" + this.senha + "</senha>"
                 +"<tipo>" + this.tipo + "</tipo>"
                +"<resultado>" + this.resultado + "</resultado>"
                 +"<lingua>" + this.lingua + "</lingua>"
                +"<objetos>" + this.objetos + "</objetos>"
                +"</res:buscaEventos>"
                 +"</soapenv:Body>"
                +"</soapenv:Envelope>";	
		return xml;
	}

}
