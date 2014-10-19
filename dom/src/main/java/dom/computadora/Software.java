package dom.computadora;

public class Software {

	private String sistemaoperativo;

	@javax.jdo.annotations.Column(allowsNull = "false")
	public String getSistemaoperativo() {
		return sistemaoperativo;
	}

	public void setSistemaoperativo(String sistemaoperativo) {
		this.sistemaoperativo = sistemaoperativo;
	}

}
