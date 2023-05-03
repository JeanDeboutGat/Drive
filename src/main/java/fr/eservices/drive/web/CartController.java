package fr.eservices.drive.web;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import fr.eservices.drive.exception.DataException;
import fr.eservices.drive.mock.CartMock;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Cart;
import fr.eservices.drive.model.Order;
import fr.eservices.drive.model.OrderEntry;
import fr.eservices.drive.model.User;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.OrderEntryRepository;
import fr.eservices.drive.repository.OrderRepository;
import fr.eservices.drive.repository.UserRepository;
import fr.eservices.drive.util.Util;
import fr.eservices.drive.web.dto.CartEntry;
import fr.eservices.drive.web.dto.SimpleResponse;
import fr.eservices.drive.web.dto.SimpleResponse.Status;

@Controller
@RequestMapping(path="/cart")
public class CartController {

	private static final String JSPVIEW = "cart_and_order";

	@Autowired
	CartMock cartMock;

	@Autowired
	OrderRepository daoOrder;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ArticleRepository  articleRepo;

	@Autowired
	OrderEntryRepository orderEntryRepo;

	@ExceptionHandler(DataException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String dataExceptionHandler(Exception ex) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter w = new PrintWriter( out );
		ex.printStackTrace(w);
		w.close();
		return 
				"ERROR"
				+ "<!--\n" + out.toString() + "\n-->";
	}

	@GetMapping(path="/Cart.html", produces="text/html")
	public String getCart(Model model) throws DataException {

		HttpSession session = Util.session();
		model.addAttribute("cartmap", session.getAttribute("cart"));
		return String.format("%s/cart_details", JSPVIEW);
	}

	@PostMapping(path="/add.html",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String add(@RequestBody @ModelAttribute CartEntry art, Model model) {
		SimpleResponse res = new SimpleResponse();
		if (Util.iAmSuspended(userRepo)) {return Util.applyBanishment(model);}
		HttpSession session = Util.session();

		if (articleRepo.findById(art.getId()) == null || art.getQty() <= 0) {
			res.status = Status.ERROR;
			res.message = "Erreur to add product";
			session.setAttribute("error", res.message);
			return "redirect:/index.html";
		}

		Cart cart = (Cart) session.getAttribute("cart");		
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);        	
		}       	

		HashMap<Article, Integer> articlesMap = cart.getArticlesMap();
		Article article = articleRepo.findById(art.getId());
		if(!articlesMap.containsKey(article)) {
			System.out.println("add");
			articlesMap.put(article, art.getQty());
		}else {
			System.out.println("replace");
			articlesMap.replace(article, cart.getValue(article)+art.getQty());
		}
		cart.setArticlesMap(articlesMap);

		res.status = Status.OK;
		res.message = "Ajout OK";
		model.addAttribute("art", art);

		session.setAttribute("responseOk", res.message);
		return "redirect:/index.html";
	}

	@RequestMapping("/validate.html")
	public String validateCart(Model model) throws DataException {
		HttpSession session = Util.session();
		Cart cart = (Cart) session.getAttribute("cart");

		if (cart == null) {
			throw new DataException("Invalid cart id");
		}
		// create an order
		Order order = new Order();
		List<OrderEntry> OrderEntries = new ArrayList<>();
		User user = (User) session.getAttribute("user");
		if(user == null || !user.getIsActive()) {
			return "redirect:/login.html";
		}
		order.setCustomerId(user.getId());
		order.setCreatedOn(new java.util.Date());
		order.setCurrentStatus(fr.eservices.drive.util.Status.ORDERED);
		int amount = 0;

		List<Article> articles = new ArrayList<>();		
		for(Entry<Article, Integer> entry : cart.getArticlesMap().entrySet()){
			OrderEntry orderEntry = new OrderEntry(entry.getKey(),order,entry.getValue());
			articles.add(entry.getKey());
			OrderEntries.add(orderEntry);
			amount += entry.getKey().getPrice() * entry.getValue();
		}
		order.setOrderentry(OrderEntries);
		order.setArticles(articles);
		// set order amount (sum of each articles' price)
		order.setAmount(amount);
		// persist everything
		daoOrder.save(order);
		for (OrderEntry orderEntry : OrderEntries) {
			orderEntryRepo.save(orderEntry);
		}
		cart.getArticlesMap().clear();
		session.setAttribute("cartmap", cart);
		session.removeAttribute("cartsession");

		// redirect user to list of orders
		return "redirect:../order/ofCustomer/"+ order.getCustomerId() +".html";
	}
}
