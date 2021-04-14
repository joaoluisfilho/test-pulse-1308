package package_controle_estoque;

import java.util.ArrayList;

public class Classe_pedido {


	public String usuario;
	public String cliente;
	public String observacoes;
	public enum_tipo_pedido tipo_pedido;
	public enum_tipo_pagamento tipo_pagamento;
	public int num_pedido;
	public float valor_total_pedido;
	ArrayList<Classe_produto> itens_do_pedido = new ArrayList<Classe_produto>();
	/**
	 * Constutor padrão para a classe pedido  
	* @param user 	paramentro de entrada do nome do usuário
	* @param client paramentro de entrada do nome do cliente
	* @param observ paramentro de entrada para as observações
	* @param tipo	paramentro do tipo de pedido 
	*/
	public Classe_pedido(String user, String client, String observ, int num,enum_tipo_pedido tipo) {
		this.usuario = user;
		this.cliente = client;
		this.observacoes = observ;
		this.num_pedido = num;
		this.tipo_pedido=tipo;
		this.valor_total_pedido=0;
	}
	public Classe_pedido() {
		this.usuario = null;
		this.cliente = null;
		this.observacoes = null;
		this.num_pedido = 0;
		this.tipo_pedido=null;
		this.valor_total_pedido=0;
	}
	/**
	 * Método de atualização do valor total do pedido
	 */
	public void atualiza_valor_total(){
		for(int i=0;i<itens_do_pedido.size();i++) {
			itens_do_pedido.get(i).Atualiza_preco_total();
			valor_total_pedido+=itens_do_pedido.get(i).preco_total;
		}
	}

}