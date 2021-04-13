package Package_controle_estoque;


public class Classe_produto {

	static public enum enum_status_produto {
		CANCELADO, ATIVO, PROCESSADO
	};

	public float preco_produto;
	public float qtdade_produto;
	public int nSeq_produto;
	public long cod_barra_produto;
	public enum_status_produto status;
	public String descricao_produto;

	public Classe_produto(float preco, int nSeq, long cod_barra, String descricao) {

		this.preco_produto = preco;
		this.nSeq_produto = nSeq;
		this.cod_barra_produto = cod_barra;
		this.descricao_produto = descricao;
		this.qtdade_produto=0;
		
	}

	public Classe_produto() {

		this.preco_produto = 0;
		this.nSeq_produto = 0;
		this.cod_barra_produto = 0;
		this.descricao_produto = null;
	}

}