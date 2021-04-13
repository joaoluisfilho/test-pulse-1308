package Package_controle_estoque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Classe_Estoque_filial {

	private ArrayList<Classe_produto> produtos_estoque = new ArrayList<Classe_produto>();
	private ArrayList<Classe_pedido> pedidos_estoque = new ArrayList<Classe_pedido>();
	private String Nome_Filial;
	private static int last_seq_num = 1;
	private static int last_pedido_num = 1;

	private static ArrayList<Classe_produto> cadastro_produtos = new ArrayList<Classe_produto>();

	public int cadastra_produto( float preco_produto,
								 long cod_barra_produto,
								 String descricao_produto) {
		  
		 cadastro_produtos.add(new Classe_produto(preco_produto,last_seq_num++,cod_barra_produto,descricao_produto));
		  return last_seq_num;
	  }

	public Classe_produto procura_produto(int numero_seq) {
		Classe_produto resultado_proc = new Classe_produto();

		for (int i = 0; i < cadastro_produtos.size(); i++) {
			if (cadastro_produtos.get(i).nSeq_produto == numero_seq) {
				resultado_proc = cadastro_produtos.get(i);
			}
		}

		return resultado_proc;
	}

	public int procura_produto(long codigo_barra) {
		int seq=0;

		for (int i = 0; i < cadastro_produtos.size(); i++) {
			if (cadastro_produtos.get(i).cod_barra_produto == codigo_barra) {
				seq = cadastro_produtos.get(i).nSeq_produto;
			}
		}
		return seq;
	}

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

	public int Novo_pedido(String user, String client, String observacoes, Classe_pedido.enum_tipo_pedido tipo) {

		pedidos_estoque.add(new Classe_pedido(user, client, observacoes, last_pedido_num++, tipo));

		return last_pedido_num;

	}

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
		if (pedidos_estoque.get(idx_pedido).tipo_pedido == Classe_pedido.enum_tipo_pedido.SAIDA) {
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
				if (pedidos_estoque.get(idx_pedido).itens_do_pedido
						.get(i).status != Classe_produto.enum_status_produto.CANCELADO) {
					pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).qtdade_produto += qtdade;
					pedidos_estoque.get(idx_pedido).itens_do_pedido
							.get(i).status = Classe_produto.enum_status_produto.ATIVO;
					return true;
				}
			}
		}

		result_pesquisa.qtdade_produto = qtdade;
		result_pesquisa.status = Classe_produto.enum_status_produto.ATIVO;
		pedidos_estoque.get(idx_pedido).itens_do_pedido.add(result_pesquisa);

		return true;
	}

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
						.get(i).status = Classe_produto.enum_status_produto.CANCELADO;
			}
		}

	}

	public void finaliza_pedido(int num_pedido, Classe_pedido.enum_tipo_pagamento pagamento) {
		int idx_pedido=0;
		// localiza pedido
		for (int i = 0; i < pedidos_estoque.size(); i++) {
			if (pedidos_estoque.get(i).num_pedido == num_pedido) {
				idx_pedido = i;
			}
		}
		
		for (int i = 0; i < pedidos_estoque.get(idx_pedido).itens_do_pedido.size(); i++) {
			if(pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).status ==Classe_produto.enum_status_produto.ATIVO) {
				
				
				pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).status =Classe_produto.enum_status_produto.PROCESSADO;
				for (int j=0;j<produtos_estoque.size();j++) {
					if(produtos_estoque.get(j).nSeq_produto== pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).nSeq_produto) {
						if(pedidos_estoque.get(idx_pedido).tipo_pedido == Classe_pedido.enum_tipo_pedido.ENTRADA){
							produtos_estoque.get(j).qtdade_produto+=pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).qtdade_produto;
						}else if(pedidos_estoque.get(idx_pedido).tipo_pedido == Classe_pedido.enum_tipo_pedido.SAIDA) {
							produtos_estoque.get(j).qtdade_produto-=pedidos_estoque.get(idx_pedido).itens_do_pedido.get(i).qtdade_produto;
						}
						
						
					}
				}
				
			
			}
		
		}		
		
	}

}
