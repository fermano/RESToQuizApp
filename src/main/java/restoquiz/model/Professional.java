package restoquiz.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Professional {
	private String id;
	private String name;
	private boolean votouHoje;
	
	public Professional(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVotouHoje() {
		return votouHoje;
	}

	public void setVotouHoje(boolean votouHoje) {
		this.votouHoje = votouHoje;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
