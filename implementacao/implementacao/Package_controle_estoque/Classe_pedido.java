
package Package_controle_estoque;
import java.util.ArrayList;

public class Classe_pedido {

	static public enum enum_tipo_pedido {
		ENTRADA, SAIDA
	};
	static public enum enum_tipo_pagamento {
		VISTA,BOLETO,CARTAO
	};

	public String usuario;
	public String cliente;
	public String observacoes;
	public enum_tipo_pedido tipo_pedido;
	public enum_tipo_pagamento tipo_pagamento;
	public int num_pedido;
	ArrayList<Classe_produto> itens_do_pedido = new ArrayList<Classe_produto>();
	
	public Classe_pedido(String user, String client, String observ, int num,enum_tipo_pedido tipo) {
		this.usuario = user;
		this.cliente = client;
		this.observacoes = observ;
		this.num_pedido = num;
		this.tipo_pedido=tipo;
	}

}