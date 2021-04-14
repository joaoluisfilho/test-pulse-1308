## Documentação da sua Solução

	O sistema de implementação do controle de estoque funciona diretamente no Prompt de comando, onde o usuário 
entra com a opção desejada nos menus para a execução das fucionaliadades.Para executar o peograma basta ter 
em seu computador uma distribuição do Java atualizada e executar o script "Executar.bat" presente na pasta de implementação.

	O menu prinipal se encotra abaixo.

 ** MENU PRINCIPAL** entre com o numero
1-Nova Filial 
2-Novo Pedido 
3-Encerra Pedido
4-Mostra Todos os Pedidos "
5-Estoque 
6-Adiciona Item
7-Cancela Item   
0-Encerra Programa";

Opção 1:Nova Filial:
O sistema é capaz de lidar com mais de uma filial utilizando o mesmo registro de produtos, sem precisar criar outro registro para
cada filial. Assim esta opção adciona mais filiais para controle do sistema;

Opção 2:Novo Pedido :
Esta opção cria um pedido novo na filial selecionada, e adiciona itens ao pedido , caso o produto a ser adicionado não seja encontrado
no banco de dados um novo produto pode ser criado. A procura pode ser feita por :numero de sequencia , descrição e cod de barra. Como o
numero de sequencêia é a chave para loccalizarmos é apenas feita uma verificação se este numero de sequencia informado corresponde a um 
item  já  presente no banco de dados de produtos, caso afirmativo é registrado juntamente com sua quantidade no pedido caso contrario é
criado um novo item e registrado no sua quantidade no pedido.

Opção 3 :Encerra Pedido :
Esta opção encerra um pedido em aberto , registrando o metodo de pagamento e  mudando o status dos itens para processado , adicionando ou 
removendo do estoque suas quantidades;

Opção 4: Mostra todos os pedidos:
Imprime em tela todos os pedidos de uma filial 

Opção 5: Estoque:
Imprime todos os itens do estoque de uma filial

Opção 6: Adiciona Item
Adiciona item a um pedido;

Opção 7: 7-Cancela Item   
Cancela item de um pedido mudando seu status para Cancelado, removendo o da contogem do preço total;