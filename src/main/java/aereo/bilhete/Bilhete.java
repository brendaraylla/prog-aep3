package aereo.bilhete;

import java.util.Date;

public class Bilhete {

	private Integer voo;
	private String origem;
	private String destino;
	private Date data;
	
	public Bilhete() {}
	
	public Bilhete(Integer voo, String origem, String destino, Date data2) {
		super();
		this.voo = voo;
		this.origem = origem;
		this.destino = destino;
		this.data = data2;
	}
	
	public Integer getVoo() {
		return voo;
	}
	public void setVoo(Integer voo) {
		this.voo = voo;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}	
	
}
