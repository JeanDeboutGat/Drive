package fr.eservices.drive.web;

import fr.eservices.drive.util.Role;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.User;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InitController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ArticleRepository articleRepository;
	
    private void buildUser(String firstName, String lastName) {
        User checkIfUser = userRepository.findByEmail(firstName+"@"+lastName+".com");
        if (checkIfUser == null) {
            User user = new User(firstName, lastName, firstName+"@"+lastName+".com", firstName, firstName);
            user.setIsActive(true);
            this.userRepository.save(user);
        }
    }

	@RequestMapping(path="/init.html", method = RequestMethod.GET)
	public String init() {
		User checkIfAdmin = userRepository.findByEmail("admin@admin.com");
		if (checkIfAdmin == null) {
			User adminUser = new User("admin", "admin", "admin@admin.com", "admin", "admin");
			adminUser.setRole(Role.SUPERADMIN);
			adminUser.setIsActive(true);
			this.userRepository.save(adminUser);
		}
		User checkIfUser = userRepository.findByEmail("user@user.com");
		if (checkIfUser == null) {
			User user = new User("user", "user", "user@user.com", "user", "user");
			user.setIsActive(true);
			this.userRepository.save(user);
		}
		
		User checkIfSuperAdmin = userRepository.findByEmail("superadmin@superadmin.com");
        if (checkIfSuperAdmin == null) {
            User superAdmin = new User("superadmin", "superadmin", "superadmin@superadmin.com", "superadmin", "superadmin");
            superAdmin.setRole(Role.SUPERADMIN);
            superAdmin.setIsActive(true);
            this.userRepository.save(superAdmin);
        }
        buildUser("Jack", "Dutronc");
        buildUser("Pierre" ,"Dupont");
        buildUser("Paul", "Dupont");

		Article checkIfArt1 = articleRepository.findById(1);
		if (checkIfArt1 == null) {
			Article art1 = new Article(1,"Boisson énergétique","https://static1.chronodrive.com/img/PM/P/0/76/0P_61276.gif",299);
			this.articleRepository.save(art1);
		}
		Article checkIfArt2 = articleRepository.findById(2);
		if (checkIfArt2 == null) {
			Article art2 = new Article(2,"Papier Cadeau","https://static1.chronodrive.com/img/PM/P/0/72/0P_348972.gif",150);
			this.articleRepository.save(art2);
		}

		Article checkIfArt3 = articleRepository.findById(3);
		if (checkIfArt3 == null) {
			Article art3 = new Article(3,"Pur jus d'orange","https://static1.chronodrive.com/img/PM/P/0/42/0P_40042.gif",235);
			this.articleRepository.save(art3);
		}

		Article checkIfArt4 = articleRepository.findById(4);
		if (checkIfArt4 == null) {
			Article art4 = new Article(4,"420g Fromage à raclette","https://static1.chronodrive.com/img/PM/P/0/20/0P_195420.gif",450);
			this.articleRepository.save(art4);
		}

		Article checkIfArt5 = articleRepository.findById(5);
		if (checkIfArt5 == null) {
			Article art5 = new Article(5,"6 tranches Jambon Serrano","https://static1.chronodrive.com/img/PM/P/0/09/0P_165609.gif",174);
			this.articleRepository.save(art5);
		}

		Article checkIfArt6 = articleRepository.findById(6);
		if (checkIfArt6 == null) {
			Article art6 = new Article(6,"2,5 kg Pomme de terre Cat 1","https://static1.chronodrive.com/img/PM/P/0/74/0P_120574.gif",169);
			this.articleRepository.save(art6);
		}
		return "redirect:/index.html";
	}
}
