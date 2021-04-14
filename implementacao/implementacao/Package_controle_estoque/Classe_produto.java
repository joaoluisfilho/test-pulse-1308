package package_controle_estoque;

public class Classe_produto {



	public float preco_produto;
	public float qtdade_produto;
	public int nSeq_produto;
	public long cod_barra_produto;
	public enum_status_produto status;
	public String descricao_produto;
	public float preco_total;


	public Classe_produto(float preco, int nSeq, long cod_barra, String descricao) {

		this.preco_produto = preco;
		this.nSeq_produto = nSeq;
		this.cod_barra_produto = cod_barra;
		this.descricao_produto = descricao;
		this.qtdade_produto=0;
		this.preco_total=0;
	}

	public Classe_produto() {

		this.preco_produto = 0;
		this.nSeq_produto = 0;
		this.cod_barra_produto = 0;
		this.descricao_produto = null;
		this.preco_total=0;

	}
	/**
	 * Método de atualização do valor total do produto
	 */
	public void Atualiza_preco_total() {
		preco_total=qtdade_produto*preco_produto;
	}
}