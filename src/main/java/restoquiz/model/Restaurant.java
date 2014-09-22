package restoquiz.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Restaurant {
	private String id;
	private String name;
	private int votos;
	private boolean eleitoNaSemana;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVotos() {
		return votos;
	}

	public void setVotos(int votos) {
		this.votos = votos;
	}

	public boolean isEleitoNaSemana() {
		return eleitoNaSemana;
	}

	public void setEleitoNaSemana(boolean eleitoNaSemana) {
		this.eleitoNaSemana = eleitoNaSemana;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
