package package_controle_estoque;

import java.util.ArrayList;

public class Classe_Estoque_filial {

	private ArrayList<Classe_produto> produtos_estoque = new ArrayList<Classe_produto>();
	private ArrayList<Classe_pedido> pedidos_estoque = new ArrayList<Classe_pedido>();
	private static ArrayList<Classe_produto> cadastro_produtos = new ArrayList<Classe_produto>();
	private String Nome_Filial;
	private static int last_seq_num = 0;
	private static int last_pedido_num = 0;

	/**
	 * Construtor da classe estoque filial
	 * 
	 * @param Nome Nome da filial
	 */
	public Classe_Estoque_filial(String Nome) {
		Nome_Filial = Nome;
	}

	/**
	 * Metodo Geter do nome da filial 
	 * @return nome da filial
	 */
	public String get_nome() {
		return Nome_Filial;
	}
	/**
	 * Metodo Geter do total de produtos cadastrados
	 * @return valor inteiro de produtos cadastrados
	 */
	public int get_num_produtos_cadastrados() {
		return cadastro_produtos.size();
	}
/**
 *  Metodo Geter do total de pedidos cadastrados
 * @return valor inteiro de pedidos cadastrados
 */
	public int get_num_pedidos() {
		return last_pedido_num;
	}
	/**
	 * 
	 * @param num_pedido		 Numero do pedido que é a chave da busca
	 * @return obejeto da classe Classe_pedido requisitado de acordo com o numero de sequencia caso falhe em encontrar retorna null
	 */
	public Classe_pedido get_pedido(int num_pedido) {
		for (int i = 0; i < pedidos_estoque.size(); i++) {
			if (pedidos_estoque.get(i).num_pedido == num_pedido) {
				return pedidos_estoque.get(i);
			}
		}
		return null;
	}
	/**
	 * 
	 * @param preco_produto     valor do preco do  produto, deve ser float 
	 * @param cod_barra_produto	valo do codigo de barras do produto 
	 * @param descricao_produto	string de descrição do produto
	 * @return retorna o numero de sequencia do produto que acabou de ser cadastrado
	 */
	public int cadastra_produto(float preco_produto, long cod_barra_produto, String descricao_produto) {
		cadastro_produtos.add(new Classe_produto(preco_produto, ++last_seq_num, cod_barra_produto, descricao_produto));
		return last_seq_num;
	}
	/**
	 * Procura produto no cadastro 
	 * @param numero_seq	Chave para procura
	 * @return retorna true caso a busca foi ben sucedida e false caso não 
	 */
	public boolean procura_produto(int numero_seq) {

		for (int i = 0; i < cadastro_produtos.size(); i++) {
			if (cadastro_produtos.get(i).nSeq_produto == numero_seq) {
				return true;
			}
		}

		return false;
	}
/**
 *  Metodo sobrecarregado de procura produto no estoque 
 * @param codigo_barra Codigo de barras do produto
 * @return numero de sequencia do produto
 */
	public int procura_produto(long codigo_barra) {
		int seq = 0;

		for (int i = 0; i < cadastro_produtos.size(); i++) {
			if (cadastro_produtos.get(i).cod_barra_produto == codigo_barra) {
				seq = cadastro_produtos.get(i).nSeq_produto;
			}
		}
		return seq;
	}
/**
 *	Metodo sobrecarregado de procura produto no estoque 
 * @param descricao entra com descrisção do produto
 * @return numero de sequencia do produto
 */
	public int procura_produto(String descricao) {
		int num_seq = 0;
		for (int i = 0; i < cadastro_produtos.size(); i++) {
			if (cadastro_produtos.get(i).descricao_produto == descricao) {
				num_seq = cadastro_produtos.get(i).nSeq_produto;
			}
		}

		if (num_seq == 0) {
			System.out.println(
					"A descricao do produto pode estar incompleta, veja  os produtos que talvez voce esteja procurando");
			System.out.println("NSEQ\tDESCRICAO");
			for (int i = 0; i < cadastro_produtos.size(); i++) {
				if (cadastro_produtos.get(i).descricao_produto.contains(descricao)) {
					System.out.println(
							cadastro_produtos.get(i).nSeq_produto + "\t" + cadastro_produtos.get(i).descricao_produto);
				}
			}
		}

		return num_seq;
	}
	/**
	 * Cadastra novo pedido no estoque da filial
	 * @param user 			nome do usuário 	
	 * @param client		nome do cliente 
	 * @param observacoes	obevervações
	 * @param tipo			tipo de pedido podendo ser enum_tipo_pedido.ENTRADA ou enum_tipo_pedido.SAIDA
	 * @return				numero do pedido recém criado
	 */
	public int Novo_pedido(String user, String client, String observacoes, enum_tipo_pedido tipo) {

		pedidos_estoque.add(new Classe_pedido(user, client, observacoes, ++last_pedido_num, tipo));

		return last_pedido_num;

	}
	/**
	 * Metodo que adiciona itens no pedido verifica a quantidade existente no estoque 
	 * @param num_pedido	numero do peido a ser editado
	 * @param seq_produto	numero de sequencia no produto a ser incluido
	 * @param qtdade		quantidade do produto a ser incluido
	 * @return				true : caso a adição dos itens seja bem sucedida e false caso  falhe por falta quantidade insficiente no estoque
	 */
	public boolean Add_Item_pedido(int num_pedido, int seq_produto, float qtdade) {
		Classe_produto result_pesquisa = new Classe_produto();
		int idx_pedido = 0;
		int idx_produto = 0;

		// localiza pedido
		for (int i = 0; i < pedidos_estoque.size(); i++) {
			if (pedidos_estoque.get(i).num_pedido == num_pedido) {
				idx_pedido = i;
			}
		}
		// localiza o produto
		for (int i = 0; i < cadastro_produtos.size(); i++) {
			if (cadastro_produtos.get(i).nSeq_produto == seq_produto) {
				result_pesquisa = cadastro_produtos.get(i);
			}
		}

		// verifica quantidade no estoque
		if (pedidos_estoque.get(idx_pedido).tipo_pedido == enum_tipo_pedido.SAIDA) {
			for (int i = 0; i < produtos_estoque.size(); i++) {
				if (produtos_estoque.get(i).nSeq_produto == seq_produto) {
					if ((produtos_estoque.get(i).qtdade_produto <= 0)
							|| (produtos_estoque.get(i).qtdade_produto < qtdade))
						return false;
				}
			}
		}

		// verificar se o item é inédito no pedido, se não for adiciona a quantidade ao
		// pedido existente
		for (int i = 0; i < pedidos_estoque.get(idx_pedido).itens_do_pedido.size(); i++) {
			if (pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).nSeq_produto == seq_produto) {
				if (pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).status != enum_status_produto.CANCELADO) {
					pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).qtdade_produto += qtdade;
					pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).status = enum_status_produto.ATIVO;
					return true;
				}
			}
		}

		result_pesquisa.qtdade_produto = qtdade;
		result_pesquisa.status = enum_status_produto.ATIVO;
		result_pesquisa.Atualiza_preco_total();
		pedidos_estoque.get(idx_pedido).itens_do_pedido.add(result_pesquisa);
		pedidos_estoque.get(idx_pedido).atualiza_valor_total();
		return true;
	}
	 /**
	  * Cancela o item do pedido
	  * @param num_pedido 	numero do pedido a ser editado
	  * @param seq_produto	numero de sequencia do pedido a ser cancelado
	  */
	public void Cancela_Item_pedido(int num_pedido, int seq_produto) {
		int idx_pedido = 0;
		;
		// localiza pedido
		for (int i = 0; i < pedidos_estoque.size(); i++) {
			if (pedidos_estoque.get(i).num_pedido == num_pedido)
				idx_pedido = i;

		}

		for (int i = 0; i < pedidos_estoque.get(idx_pedido).itens_do_pedido.size(); i++) {
			if (pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).nSeq_produto == seq_produto) {
				pedidos_estoque.get(idx_pedido).itens_do_pedido
						.get(i).status = package_controle_estoque.enum_status_produto.CANCELADO;
			}
		}

	}
	/**
	 * Finaliza o pedido mudando o estado de todos os itens ativos para  processado e registrando a forma de pagamento
	 * @param num_pedido	Chave para a localização do peido
	 * @param pagamento		VISTA,BOLETO,CARTAO;
	 */
	public void finaliza_pedido(int num_pedido, enum_tipo_pagamento pagamento) {
		int idx_pedido = 0;
		// localiza pedido
		for (int i = 0; i < pedidos_estoque.size(); i++) {
			if (pedidos_estoque.get(i).num_pedido == num_pedido) {
				idx_pedido = i;
			}
		}

		for (int i = 0; i < pedidos_estoque.get(idx_pedido).itens_do_pedido.size(); i++) {
			if (pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).status == enum_status_produto.ATIVO) {

				pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).status = enum_status_produto.PROCESSADO;
				for (int j = 0; j < produtos_estoque.size(); j++) {
					if (produtos_estoque.get(j).nSeq_produto == pedidos_estoque.get(idx_pedido).itens_do_pedido
							.get(i).nSeq_produto) {
						if (pedidos_estoque.get(idx_pedido).tipo_pedido == enum_tipo_pedido.ENTRADA) {
							produtos_estoque.get(j).qtdade_produto += pedidos_estoque.get(idx_pedido).itens_do_pedido
									.get(i).qtdade_produto;
						} else if (pedidos_estoque.get(idx_pedido).tipo_pedido == enum_tipo_pedido.SAIDA) {
							produtos_estoque.get(j).qtdade_produto -= pedidos_estoque.get(idx_pedido).itens_do_pedido
									.get(i).qtdade_produto;
						}

					}
				}

			}

		}

	}
/**
 * Imprime dados do produto no terminal
 * @param produto Classe_produto 
 */
	public void print_dados_produto(Classe_produto produto) {
//		System.out.println("N.SEQ\t\tCOD_BARRA\t\tPRECO_uni\t\tQUANTIDADE\\t\tPRECO_TOt\\tDESC");
		System.out.println(produto.nSeq_produto + "\t\t" + produto.cod_barra_produto + "\t\t" + produto.preco_produto
				+ "\t\t" + produto.qtdade_produto + "\t\t" + produto.preco_total + "\t\t" + produto.descricao_produto);
	}
	/**
	 * Imprime dados do pedido no terminal
	 * @param num_pedido 
	 */
	public void print_dados_pedido(int num_pedido) {
		int idx_pedido = 0;
		for (int i = 0; i < pedidos_estoque.size(); i++) {
			if (pedidos_estoque.get(i).num_pedido == num_pedido) {
				idx_pedido = i;
			}
		}
		if (num_pedido == 0)
			return;
		System.out.println("N_pedidio\t\t\tusuario\t\t\tcliente\t\t\tn_de_itens");
		System.out.println(pedidos_estoque.get(idx_pedido).num_pedido + "\t\t\t"
				+ pedidos_estoque.get(idx_pedido).usuario + "\t\t\t" + pedidos_estoque.get(idx_pedido).cliente
				+ "\t\t\t" + pedidos_estoque.get(idx_pedido).itens_do_pedido.size());
	}
	/**
	 * Imprime itens do pedido no terminal
	 * @param num_pedido 
	 */
	public void print_itens_pedido(int num_pedido) {
		int idx_pedido = 0;

		for (int i = 0; i < pedidos_estoque.size(); i++) {
			if (pedidos_estoque.get(i).num_pedido == num_pedido) {
				idx_pedido = i;
			}
		}
		System.out.println("N.SEQ\t\tCOD_BARRA \t\tPRECO_uni \t\tQUANTIDADE \t\tPRECO_TO \t\tDESC");
		for (int i = 0; i < pedidos_estoque.get(idx_pedido).itens_do_pedido.size(); i++) {
			print_dados_produto(pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i));
		}
	}
	/**
	 * Imprime itens do estoque no terminal
	 */
	public void print_estoque() {
		int idx_pedido = 0;

		System.out.println("N.SEQ\t\tCOD_BARRA \t\tPRECO_uni \t\tQUANTIDADE \t\tPRECO_TO \t\tDESC");
		for (int i = 0; i < produtos_estoque.size(); i++) {
			print_dados_produto(produtos_estoque.get(i));
		}
	}
}
