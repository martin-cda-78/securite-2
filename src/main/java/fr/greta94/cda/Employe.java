package fr.greta94.cda;

import java.io.Serializable;

public class Employe implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String nom;
	private String prenom;
	private String motdepasse;
	private int role;

	public Employe() {
	}

	public Employe(String nom, String prenom, String motdepasse, int role) {
		this.nom = nom;
		this.prenom = prenom;
		this.setMotdepasse(motdepasse);
		this.role = role;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Employe [nom=" + nom + ", prenom=" + prenom + ", motdepasse=" + motdepasse + ", rôle=" + role + "]";
	}
}
