
import java.util.Scanner;
import Package_controle_estoque.*;
import java.io.IOException;
public class Menu {

	public static void main(String[] args) throws IOException, InterruptedException {

		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		int choice;
		while (true) {

			System.out.println(
					"Menu -Selecione uma Opcao \n 1-Adicionar Filial \n 2-Registrar/Editar Pedido de Entrada \n 3-Registrar/Editar Pedido de Saida \n 0-finaliza programa");

			Scanner input_menu = new Scanner(System.in);
			choice = input_menu.nextInt();

			if (choice == 0) {
				break;
			}else {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			}

		}
	}
}
