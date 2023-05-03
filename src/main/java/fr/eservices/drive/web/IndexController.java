package fr.eservices.drive.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.eservices.drive.exception.DataException;
import fr.eservices.drive.mock.CartMock;
import fr.eservices.drive.model.Cart;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.UserRepository;
import fr.eservices.drive.util.Util;
import fr.eservices.drive.web.dto.CartEntry;

@Controller
public class IndexController {
	
	@Autowired
	CartMock cartMock;
	
	@Autowired
	ArticleRepository articleRepo;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(path="/index.html", method = RequestMethod.GET)
	public String getHomePage(Model model) {
		if (Util.iAmSuspended(userRepository)) {return Util.applyBanishment(model);}
		HttpSession session = Util.session();
		if (session.getAttribute("error") != null) {
			model.addAttribute("error", session.getAttribute("error"));
			session.removeAttribute("error");
		}
		if (session.getAttribute("responseOk") != null) {
            model.addAttribute("responseOk", session.getAttribute("responseOk"));
            session.removeAttribute("responseOk");
        }
		CartMock mockCart = new CartMock();
		int idcart = 2;
		mockCart.setCart(idcart, new Cart());
		/*try {
			//session.setAttribute("cartsession", cartMock.getCartContent(idcart));
		} catch (DataException e) {
			throw new RuntimeException("Error cart");
		}*/
		session.setAttribute("idcart", idcart);
		
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("articles", articleRepo.findAll());
		model.addAttribute("art", new CartEntry());
		model.addAttribute("idcart", idcart);
		model.addAttribute("cartmap", session.getAttribute("cart"));
		
		return "index";
	}
	

}
