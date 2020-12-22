package com.poec.servlets.Beans;

import java.time.LocalDate;
import java.util.ArrayList;

public class Contrat {
	private String code;
	private Client client;
	private ArrayList<SinistreCouvert> sinistresCouverts;
	private LocalDate signature;
	
	public Contrat () {
		
	}
	
	public Contrat (String code, Client client, String it, String pe, String ia, String mt, String ch, String av, int frit, int frpe, int fria, int frmt, int frch, String jourSignature, String moisSignature, String anneeSignature)
	{
		this.code = code;
		this.client = client;
		this.signature = LocalDate.of(Integer.parseInt(anneeSignature), Integer.parseInt(moisSignature), Integer.parseInt(jourSignature));
		this.sinistresCouverts = new ArrayList<SinistreCouvert>();
		if (it.equals("1")) this.sinistresCouverts.add(new SinistreCouvert ("IT", frit));
		if (pe.equals("1")) this.sinistresCouverts.add(new SinistreCouvert ("PE", frpe));
		if (ia.equals("1")) this.sinistresCouverts.add(new SinistreCouvert ("IA", fria));
		if (mt.equals("1")) this.sinistresCouverts.add(new SinistreCouvert ("MT", frmt));
		if (ch.equals("1")) this.sinistresCouverts.add(new SinistreCouvert ("CH", frch));
	}

	public String getCode() {
		return code;
	}

	public ArrayList<SinistreCouvert> getSinistresCouverts() {
		return sinistresCouverts;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public LocalDate getSignature() {
		return signature;
	}

	public void setSignature(LocalDate signature) {
		this.signature = signature;
	}

	public void setSinistresCouverts(ArrayList<SinistreCouvert> sinistresCouverts) {
		this.sinistresCouverts = sinistresCouverts;
	}
}
