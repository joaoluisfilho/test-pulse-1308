/** 
* @author Joao Luis
* @version 1 
*/

//todo fazer item pra cancelar o pedido

import package_controle_estoque.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_class {

	public static void main(String[] args) throws IOException, InterruptedException {
		int input = 1;
		int add_pedidos = 1;
		int inx_filial = 0;
		int num_seq = 0;
		int idx_n_pedido=0;
		String desc = null;
		long cod_barra = 0;
		boolean isEnd_Selected = false;
		ArrayList<Classe_Estoque_filial> filiais = new ArrayList<Classe_Estoque_filial>();
		String Main_menu = " ** MENU PRINCIPAL** entre com o numero\n"
				+ "1-Nova Filial \n2-Novo Pedido \n3-Encerra Pedido\n4-Mostra Todos os Pedidos "
				+ "\n5-Estoque \n6-Cancela Item \n7-Adiona Item   \n0-Encerra Programa";

		do {
			flush_cmd();
			System.out.println(Main_menu);
			input = scan_int(7);
			if((input>1)&&(filiais.size()==0)) {
				flush_cmd();
				System.out.println("por favor cadastre uma filial\n entre com o nome da filial");
				String nome = scan_string();
				filiais.add(new Classe_Estoque_filial(nome));
			}
			switch (input) {
			case 0:
				isEnd_Selected = true;
				break;
			case 1:
				flush_cmd();
				System.out.println("Cadastrando nova filial\n entre com o nome da filial");
				String nome = scan_string();
				filiais.add(new Classe_Estoque_filial(nome));
				break;
			case 2:
				// novo pedido
				flush_cmd();
				System.out.println(
						"Cadastrando novo pedido , Selecione a Filial\nentre 0 para cancelar\nOpcao\t\tNome Filial");
				for (int i = 0; i < filiais.size(); i++) {
					System.out.println((i + 1) + "\t\t" + filiais.get(i).get_nome());
				}
				input = scan_int(filiais.size() + 1);
				inx_filial = input - 1;
				if (input != 0) {
					System.out.println("Entre com o nome do Usuario");
					String user = scan_string();
					System.out.println("Entre com o nome do Cliente");

					String client = scan_string();
					System.out.println("Entre com as observacoes");

					String obs = scan_string();
					System.out.println("Entre com o tipo do pedido: 0-Entrada 1-Saida");

					int tipo_int = scan_int(1);
					enum_tipo_pedido tipo = tipo_int == 0 ? enum_tipo_pedido.ENTRADA : enum_tipo_pedido.SAIDA;
					 idx_n_pedido = filiais.get(inx_filial).Novo_pedido(user, client, obs, tipo);
					System.out.println("Pedido numero " + idx_n_pedido + " Criado \n");
					do {
						System.out.println("Entre como quer selecionar os produtos \n0-Numero de Sequencia"
								+ "\n1-codigo de barras " + "\n2-descricao ");
						input = scan_int(2);
						 num_seq = 0;
						 desc = null;
						 cod_barra = 0;
						if (input == 1) {
							System.out.println("Entre com o codigo de barras:");
							cod_barra = scan_long(true);
							num_seq = filiais.get(inx_filial).procura_produto(cod_barra);
						} else if (input == 2) {
							System.out.println("Entre com a descricao:");
							desc = scan_string();
							num_seq = filiais.get(inx_filial).procura_produto(desc);
						} else if (input == 0) {
							System.out.println("Entre com o numero de sequencia:");
							num_seq = (int) scan_long(true);
							if (!filiais.get(inx_filial).procura_produto(num_seq))
								num_seq = 0;
						}

						if (num_seq == 0) {
							System.out.println("Produto não encontrado\n deseja cadastrar novo produto?0-não 1-sim");
							input = scan_int(1);
							if (input == 1) {
								System.out.println("Entre com preço do produto");
								float preco = scan_float();
								System.out.println("Entre com a descricao:");
								desc = scan_string();
								System.out.println("Entre com o codigo de barras:");
								cod_barra = scan_long(true);
								num_seq = filiais.get(inx_filial).cadastra_produto(preco, cod_barra, desc);
								System.out.println("Produto cadastrado com o numero de sequencia :" + num_seq);
							} else
								break;
						}
						System.out.println("Entre com o quantidade");
						float qtdade = scan_float();
						if (filiais.get(inx_filial).Add_Item_pedido(idx_n_pedido, num_seq, qtdade)) {
							System.out.println("Produto adiocionado com sucesso");
						} else {
							System.out.println("Produto não foi adicionado for falta de estoque ");
						}

						System.out.println("Deseja adicionar mais pedidos : 1-sim 0-não");
						add_pedidos = scan_int(1);
					} while (add_pedidos == 1);
				}

				break;
			case 3:
				// mostrar valor total
				flush_cmd();
				System.out.println(
						"Finalizando pedido , Selecione a Filial\nentre 0 para cancelar\nOpcao\t\tNome Filial");
				for (int i = 0; i < filiais.size(); i++) {
					System.out.println((i + 1) + "\t\t" + filiais.get(i).get_nome());
				}
				input = scan_int(filiais.size() + 1);
				inx_filial = input - 1;
				System.out.println("Entre com o numero do pedido: (para listar os pedidos entre 0)");
				input = scan_int(filiais.get(inx_filial).get_num_pedidos());
				if (input == 0) {
					for (int i = 0; i < filiais.get(inx_filial).get_num_pedidos(); i++) {
						filiais.get(inx_filial).print_dados_pedido(i);
					}
					System.out.println("Entre com o numero do pedido:");
					input = scan_int(filiais.get(inx_filial).get_num_pedidos());
					if (input == 0) {
						break;
					}
				}
				int num_pedido_cancela = input - 1;
				System.out.println("Selecione o tipo de Pagamento: \n0-VISTA\n1-BOLETO\n2-CARTAO;");
				int pagamento = scan_int(2);
				enum_tipo_pagamento tipo_pagamento = pagamento == 0 ? enum_tipo_pagamento.VISTA
						: pagamento == 1 ? enum_tipo_pagamento.BOLETO : enum_tipo_pagamento.CARTAO;
				filiais.get(inx_filial).finaliza_pedido(num_pedido_cancela, tipo_pagamento);
				// encerra pedido
				break;
			case 4:
				// mostra pedido
				flush_cmd();
				System.out.println(" Selecione a Filial\nentre 0 para cancelar\nOpcao\t\tNome Filial");
				for (int i = 0; i < filiais.size(); i++) {
					System.out.println((i + 1) + "\t\t" + filiais.get(i).get_nome());
				}
				input = scan_int(filiais.size() + 1);
				inx_filial = input - 1;
				int pedido = 0;

				for (int i = 1; i <= (filiais.get(inx_filial).get_num_pedidos()); i++) {
					filiais.get(inx_filial).print_dados_pedido(i);
				}
				System.out.println("para retornar entre com -0");
				input = scan_int(0);
				if (input == 0) {
					break;
				}
				pedido = input;

				break;
			case 5:
				flush_cmd();
				System.out.println(" Selecione a Filial\nentre 0 para cancelar\nOpcao\t\tNome Filial");
				for (int i = 0; i < filiais.size(); i++) {
					System.out.println((i + 1) + "\t\t" + filiais.get(i).get_nome());
				}
				input = scan_int(filiais.size() + 1);
				inx_filial = input - 1;
				
				filiais.get(inx_filial).print_estoque();
				break;
			case 6:
				flush_cmd();
				System.out.println(
						" Selecione a Filial\nentre 0 para cancelar\nOpcao\t\tNome Filial");
				for (int i = 0; i < filiais.size(); i++) {
					System.out.println((i + 1) + "\t\t" + filiais.get(i).get_nome());
				}
				input = scan_int(filiais.size() +1);
				inx_filial=input-1;
				
				System.out.println("Entre com o numero do pedido: (para listar os pedidos entre 0)");
				input = scan_int(filiais.get(inx_filial).get_num_pedidos());
				if (input == 0) {
					for (int i = 1; i <=filiais.get(inx_filial).get_num_pedidos(); i++) {
						filiais.get(inx_filial).print_dados_pedido(i);
					}
					System.out.println("Entre com o numero do pedido:");
					input = scan_int(filiais.get(inx_filial).get_num_pedidos());
					if (input == 0) {
						break;
					}
				}
				int n_pedido=input-1;
				filiais.get(inx_filial).print_itens_pedido(n_pedido);
		
			
				System.out.println("entre com o numero de sequencia");
				 num_seq = (int) scan_long(true);
				if (!filiais.get(inx_filial).procura_produto(num_seq)) {
					num_seq = 0;
					break;
				}
				
				filiais.get(inx_filial).Cancela_Item_pedido(n_pedido, num_seq);
				
				break;
			case 7:
				flush_cmd();
				System.out.println(
						" Selecione a Filial\nentre 0 para cancelar\nOpcao\t\tNome Filial");
				for (int i = 0; i < filiais.size(); i++) {
					System.out.println((i + 1) + "\t\t" + filiais.get(i).get_nome());
				}
				input = scan_int(filiais.size() + 1);
				inx_filial=input-1;
				
				System.out.println("Entre com o numero do pedido: (para listar os pedidos entre 0)");
				input = scan_int(filiais.get(inx_filial).get_num_pedidos()+1);
				if (input == 0) {
					for (int i = 0; i <=filiais.get(inx_filial).get_num_pedidos(); i++) {
						filiais.get(inx_filial).print_dados_pedido(i);
					}
					System.out.println("Entre com o numero do pedido:");
					input = scan_int(filiais.get(inx_filial).get_num_pedidos());
					if (input == 0) {
						break;
					}
				}
				int n_pedido_add=input-1;
				
				
				System.out.println("Entre como quer selecionar os produtos \n0-Numero de Sequencia"
						+ "\n1-codigo de barras " + "\n2-descricao ");
				input = scan_int(2);
			
				if (input == 1) {
					System.out.println("Entre com o codigo de barras:");
					cod_barra = scan_long(true);
					num_seq = filiais.get(inx_filial).procura_produto(cod_barra);
				} else if (input == 2) {
					System.out.println("Entre com a descricao:");
					desc = scan_string();
					num_seq = filiais.get(inx_filial).procura_produto(desc);
				} else if (input == 0) {
					System.out.println("Entre com o numero de sequencia:");
					num_seq = (int) scan_long(true);
					if (!filiais.get(inx_filial).procura_produto(num_seq))
						num_seq = 0;
				}

				if (num_seq == 0) {
					System.out.println("Produto não encontrado\n deseja cadastrar novo produto?0-não 1-sim");
					input = scan_int(1);
					if (input == 1) {
						System.out.println("Entre com preço do produto");
						float preco = scan_float();
						System.out.println("Entre com a descricao:");
						desc = scan_string();
						System.out.println("Entre com o codigo de barras:");
						cod_barra = scan_long(true);
						num_seq = filiais.get(inx_filial).cadastra_produto(preco, cod_barra, desc);
						System.out.println("Produto cadastrado com o numero de sequencia :" + num_seq);
					} else
						break;
				}
				System.out.println("Entre com o quantidade");
				float qtdade = scan_float();
				if (filiais.get(inx_filial).Add_Item_pedido(idx_n_pedido, num_seq, qtdade)) {
					System.out.println("Produto adiocionado com sucesso");
				} else {
					System.out.println("Produto não foi adicionado for falta de estoque ");
				}
				
				break;
			default:
			}
		} while (!isEnd_Selected);
	}

	public static void flush_cmd() throws IOException, InterruptedException {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}

	public static int scan_int(int valor_maximo) {

		int input = 0;
		boolean wenttocatch = false;
		Scanner input_menu = new Scanner(System.in);
		do {
			if (input_menu.hasNextInt()) {
				input = input_menu.nextInt();
				if ((input <= valor_maximo) && (input >= 0)) {
					wenttocatch = true;
				} else {
					input_menu.nextLine();
					System.out.println("Entre um valor Valido\n");
				}

			} else {
				input_menu.nextLine();
				System.out.println("Entre um valor Valido");
			}
		} while (!wenttocatch);

		return input;
	}

	public static long scan_long(boolean isonlypositive) {

		long input = 0;
		boolean wenttocatch = false;
		Scanner input_menu = new Scanner(System.in);
		do {
			if (input_menu.hasNextLong()) {
				input = input_menu.nextLong();
				if ((isonlypositive) && (input < 0)) {
					input_menu.nextLine();
					System.out.println("Entre um valor Valido");
				} else {
					wenttocatch = true;
				}

			} else {
				input_menu.nextLine();
				System.out.println("Entre um valor Valido");
			}
		} while (!wenttocatch);

		return input;
	}

	public static float scan_float() {

		float input = 0;
		boolean wenttocatch = false;
		Scanner input_menu = new Scanner(System.in);
		do {
			if (input_menu.hasNextFloat()) {
				input = input_menu.nextFloat();
				wenttocatch = true;
			} else {
				input_menu.nextLine();
				System.out.println("Entre um valor Valido");
			}
		} while (!wenttocatch);

		return input;
	}

	public static String scan_string() {
		String input = null;
		boolean wenttocatch = false;
		Scanner input_menu = new Scanner(System.in);
		do {
			if (input_menu.hasNext()) {
				input = input_menu.next();
				wenttocatch = true;
			} else {
				input_menu.nextLine();
				System.out.println("Entre um valor Valido");
			}
		} while (!wenttocatch);

		return input;

	}

}
