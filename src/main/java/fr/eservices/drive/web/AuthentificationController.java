package fr.eservices.drive.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import fr.eservices.drive.repository.UserRepository;
import fr.eservices.drive.util.PasswordChecker;
import fr.eservices.drive.util.SHA256Checker;
import fr.eservices.drive.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eservices.drive.model.User;
import fr.eservices.drive.model.UserCreationToken;
import fr.eservices.drive.repository.LoginRepository;
import fr.eservices.drive.repository.UserCreationTokenRepository;

@Controller
public class AuthentificationController {
    
    private static final String JSPVIEW = "authentification_and_admin";
	
	@Autowired
	LoginRepository loginRepo;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserCreationTokenRepository tokenRepository;
	
	PasswordChecker pwdCheck;
	
	public AuthentificationController() {
		this.pwdCheck = new SHA256Checker();
	}
	
	
	@GetMapping(path="/register.html")
	public String getRegisterPage(Model model) {
		return String.format("%s/register", JSPVIEW);
	}
	
	@RequestMapping(path="/register.html", method= RequestMethod.POST)
	public String registerUser(String firstname, String lastname, String email, String address, String password, String passwordConfirm, Model model) throws ServletException, IOException {
		if (firstname == null || firstname.isBlank() || lastname == null || lastname.isBlank() || email == null || email.isBlank() ||
			address == null || address.isBlank() || password == null || password.isBlank() || passwordConfirm == null || passwordConfirm.isBlank()) {
			model.addAttribute("error", "You must fill all fields to continue");
			return String.format("%s/register", JSPVIEW);
		}
		User user = this.userRepository.findByEmail(email);
		if (user != null) {
			model.addAttribute("error", "email is already in use");
			return String.format("%s/register", JSPVIEW);
		}
		
		if (password.equals(passwordConfirm)) {
			User newUser = new User(firstname, lastname, email, address, password);
			newUser = this.userRepository.save(newUser);
			UserCreationToken token = new UserCreationToken(newUser.getId());
			token = this.tokenRepository.save(token);
			Util.sendEmail(email, token.getToken());
			model.addAttribute("creationOk", true);
			return String.format("%s/register", JSPVIEW);
		}
		model.addAttribute("error", "Your passwords does not match");
		return String.format("%s/register", JSPVIEW);
	}
	
	@RequestMapping(path="/login.html", method = RequestMethod.GET)
	public String getLoginPage(Model model) {
	    return String.format("%s/login", JSPVIEW);
	}

	@RequestMapping(path="/login.html", method = RequestMethod.POST)
	public String loginUser(String email, String password, Model model) throws ServletException, IOException {
		HttpSession session = Util.session();
		if (email == null || email.isBlank() || password == null || password.isBlank()) {
			model.addAttribute("error", "You must enter your email and password in order to login");
			return String.format("%s/login", JSPVIEW);
		}
		User user = this.loginRepo.findByEmailAndPassword(email, this.pwdCheck.encode(password));
		if (user == null) {
			model.addAttribute("error", "Wrong username or wrong password");
			return String.format("%s/login", JSPVIEW);
		} else if (!user.getIsActive()) {
			model.addAttribute("error", "Your account is not activated yet.");
			return String.format("%s/login", JSPVIEW);
		}  else if (user.getIsBanned()) {
			model.addAttribute("error", "Your account have been suspended.");
			return String.format("%s/login", JSPVIEW);
		}
		session.setAttribute("user", user);
		model.addAttribute("isactif", user.getIsActive());
		return "redirect:/index.html";
	}
	
	@RequestMapping(path="/logout.html", method = RequestMethod.GET)
	public String logoutUser(Model model) {
		HttpSession session = Util.session();
		session.removeAttribute("user");
		session.removeAttribute("cart");
		return "redirect:/index.html";
	}
	
	@RequestMapping(path="/confirmRegistration.html", method = RequestMethod.GET)
	public String confirmRegistration(Model model, @RequestParam(value="token") String token) {
		UserCreationToken Token = this.tokenRepository.findByToken(token);
		if (Token == null) {
			model.addAttribute("error", "token does not exists");
			return String.format("%s/register", JSPVIEW);
		} else if (Token.isUsed()) {
			model.addAttribute("error", "token has already been used");
			return String.format("%s/register", JSPVIEW);
		}
		User user = this.userRepository.findById(Token.getUserId());
		if (user == null) {
			model.addAttribute("error", "Something went wrong");
			return String.format("%s/register", JSPVIEW);
		}
		user.setIsActive(true);
		Token.setIsUsed(true);
		this.userRepository.save(user);
		this.tokenRepository.save(Token);
		model.addAttribute("registerOk", true);
		return String.format("%s/login", JSPVIEW);
	}

}
