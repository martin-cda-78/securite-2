package fr.greta94.cda;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// controleur pour apprendre la sécurité
@RestController
//@RequestMapping("/api")
public class EmployesController {
	@GetMapping("/api/defaut")
	public Employe getDefaut() {
		return new Employe("Palmer", "Jack", "monpdp", 0);
	}
	@GetMapping("/")
	public String redirige() {
		System.out.println("11111");
		String url="http://localhost:8080/api/bienvenue";
		RestTemplate restTemplate = new RestTemplate();
		String result  = restTemplate.getForObject(url, String.class);
		return result;
	}
	@GetMapping("/api/home")
	String getHome() {
		return "home";
	}

	@GetMapping("/api/bienvenue")
	String getBienvenue() {
		return "bienvenue";
	}

	
	@GetMapping("/api/admin")
	//@PreAuthorize("hasRole('ADMIN')")
	String getAdm() {
		return "admin";
	}

	@GetMapping("/api/adminbis")
	String getAdmBis() {
		return "adminbis";
	}

	@GetMapping("/api/employe")
	String getEmp() {
		return "employe";
	}

	@GetMapping("/api/manager")
	String getMgr() {
		return "manager";
	}

	@GetMapping("/api/common")
	String getCom() {
		return "common";
	}

	@GetMapping("/api/perso/name")
	public String getNomUtilisateur(Principal principal) {
		String nom = principal.getName();
		return nom;
	}

	@GetMapping("/api/perso/role")
	public List<String> getRoleUtilisateur(Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		ArrayList<String> nomsdesRoles = new ArrayList<String>();
		roles.forEach(r -> nomsdesRoles.add(r.getAuthority()));
		return nomsdesRoles;
	}
}
