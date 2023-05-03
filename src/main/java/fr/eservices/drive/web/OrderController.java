package fr.eservices.drive.web;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eservices.drive.model.Order;
import fr.eservices.drive.model.OrderEntry;
import fr.eservices.drive.model.User;
import fr.eservices.drive.repository.OrderEntryRepository;
import fr.eservices.drive.repository.OrderRepository;
import fr.eservices.drive.repository.UserRepository;
import fr.eservices.drive.util.Role;
import fr.eservices.drive.util.Util;

@Controller
@RequestMapping(path="/order")
public class OrderController {
    
    private static final String JSPVIEW = "cart_and_order";

	@Autowired
	OrderRepository repoOrder;

	@Autowired
	OrderEntryRepository repoEntryOrder;
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(path="/ofCustomer/{custId}.html")
	public String list(@PathVariable UUID custId, Model model) {
		if (Util.iAmSuspended(userRepository)) {return Util.applyBanishment(model);}
	    User currentUser = Util.getMyself();
	    HttpSession session = Util.session();
	    if (currentUser == null || !currentUser.getId().equals(custId)) {
	        session.setAttribute("error", "You can't access this page, you are not allowed to see an other customers' order");
	        return "redirect:/index.html";
	    }
		// use repo to get orders of a customer
		List<Order> orders = repoOrder.findByCustomerIdOrderByCreatedOnDesc(custId);
		// assign in model as "orders"
		model.addAttribute("orders", orders);
		// return order list view
		return String.format("%s/order_list", JSPVIEW);
	}

	@RequestMapping(path="/ofCustomer/{custId}/detailsOrder/{orderId}.html")
	public String getDetailsOrder(@PathVariable UUID custId,@PathVariable long orderId, Model model) {
		if (Util.iAmSuspended(userRepository)) {return Util.applyBanishment(model);}
	    User currentUser = Util.getMyself();
        HttpSession session = Util.session();
        if (currentUser == null || (!currentUser.getId().equals(custId) && currentUser.getRole() == Role.USER)) {
            session.setAttribute("error", "You can't access this page, you are not allowed to see an other customers' order");
            return "redirect:/index.html";
        }
		Order order = repoOrder.findById(orderId);
		List<OrderEntry> orderentries = repoEntryOrder.findByOrder(order);
		model.addAttribute("order_details", orderentries);
		return String.format("%s/_order_details", JSPVIEW);
	}


}
