package clv;

public class Report {

	private int nbrLances;
	private int failMax;
	private int portefeuille;
	
	
	public Report(int nbrLances, int failMax, int portefeuille) {
		super();
		this.nbrLances = nbrLances;
		this.failMax = failMax;
		this.portefeuille = portefeuille;
	}


	public int getNbrLances() {
		return nbrLances;
	}


	public int getFailMax() {
		return failMax;
	}


	public int getPortefeuille() {
		return portefeuille;
	}
	
	
}
