
import java.util.Scanner;
import Package_controle_estoque.*;
import java.io.IOException;

public class Menu {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Classe_Estoque_filial Filial_1= new Classe_Estoque_filial("Filial A");
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		int input;
		int input_submenu;
		String input_string;

		while (true) {

			System.out.println(
					"Menu -Selecione uma Opcao  \n 1-Registrar Pedido de Entrada "
					+ "\n 2-Registrar Pedido de Saida " + "\n 3-Mostra Todo o estoque" + "\n 0-finaliza programa");

		
			input=scan_int();
			
			switch (input) {
				
				case 1:
					System.out.println(" Entre com o nome do usuário");
					String Nome=scan_string();
					System.out.println(" Entre com o nome do Cliente:");
					String Cliente=scan_string();
					System.out.println(" Entre com Observações:");
					String obs=scan_string();
					System.out.println(" O pedido é de : 1- Entrada ou 2-Saida");
					int tipo=scan_int();
					if(tipo==1) {
						Filial_1.Novo_pedido(
								Nome,Cliente,obs,Package_controle_estoque.Classe_pedido.enum_tipo_pedido.ENTRADA);
					}else if(tipo==2) {
						Filial_1.Novo_pedido(
								Nome,Cliente,obs,Package_controle_estoque.Classe_pedido.enum_tipo_pedido.SAIDA);
					}
					
					System.out.println("Deseja adicionar itens no pedido por :"
							+ "\n1-Numero de Seq \n2-Cod de Barra\n3- Descricao");
							int search =scan_int();
							if(search==1) {
								System.out.println("Entre com o numéro de sequencia:");
								int num_seq=scan_int();
								Classe_produto produto=Filial_1.procura_produto(num_seq);
								if(produto.nSeq_produto==0) {
									System.out.println("Produto não cadastrado, informe a descricao:");
									String Desc=scan_string();
									System.out.println("informe o cod de barra");
									long cod_barra=(long)scan_int();
									System.out.println("informe o preco");
									float preco=scan_float();
									Filial_1.cadastra_produto(preco,cod_barra,Desc);
								}else {
									
								}
							}else if(search==2) {
								
							}else if(search==3) {
								
							}
					
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				default:
					
				
			}
			
			
			if (input == 0) break;
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}
	}
	
	public static  int scan_int() {
		
		int input=0;
		boolean wenttocatch=false;
		Scanner input_menu = new Scanner(System.in);

		 if(input_menu.hasNextInt()){
			 input = input_menu.nextInt();
	        	if(input<3) {
	        		wenttocatch = true;
	        	}else {
	        		input_menu.nextLine();
		            System.out.println("Entre um valor Valido\n");
	        	}
	            
	        }else {
	        	input_menu.nextLine();
	            System.out.println("Entre um valor Valido");
	        }
		 return input;
	}
	
public static  float scan_float() {
		
		float input=0;
		boolean wenttocatch=false;
		Scanner input_menu = new Scanner(System.in);

		 if(input_menu.hasNextFloat()){
			 input = input_menu.nextFloat();
	        	if(input<3) {
	        		wenttocatch = true;
	        	}else {
	        		input_menu.nextLine();
		            System.out.println("Entre um valor Valido\n");
	        	}
	            
	        }else {
	        	input_menu.nextLine();
	            System.out.println("Entre um valor Valido");
	        }
		 return input;
	}
	
	public static String scan_string() {
		String input=null;
		boolean wenttocatch=false;
		Scanner input_menu = new Scanner(System.in);
		
		 if(input_menu.hasNext()){
			input = input_menu.next();
	        wenttocatch = true;	        	
	        }else {
	        	input_menu.nextLine();
	            System.out.println("Entre um valor Valido");
	        }
		 return input;
		
	}
  
}
