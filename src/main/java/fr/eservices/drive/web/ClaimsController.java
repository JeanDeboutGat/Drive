package fr.eservices.drive.web;

import fr.eservices.drive.util.ClaimStatus;
import fr.eservices.drive.util.Role;
import fr.eservices.drive.model.Claim;
import fr.eservices.drive.model.Message;
import fr.eservices.drive.model.Order;
import fr.eservices.drive.model.User;
import fr.eservices.drive.repository.ClaimRepository;
import fr.eservices.drive.repository.MessageRepository;
import fr.eservices.drive.repository.OrderRepository;
import fr.eservices.drive.repository.UserRepository;
import fr.eservices.drive.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/claims")
public class ClaimsController {
    private static final String JSPVIEW = "claim";
    
    @Autowired
    ClaimRepository claimRepo;
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    MessageRepository messageRepo;
    
    @Autowired 
    UserRepository userRepository;

    @GetMapping
    public String getClaims(Model model) {
        User user = (User) Util.session().getAttribute("user");
        if (user == null) {
            Util.session().setAttribute("error", "You are not connected. Please log in.");
            return "redirect:/index.html";
        }
        List<Claim> claims;
        if (user.getRole() == Role.USER) claims = claimRepo.findByCustomerId(user.getId());
        else claims = claimRepo.findByStatus(ClaimStatus.OPEN);
        
        List<Order> orders;
        orders = this.orderRepo.findByCustomerIdOrderByCreatedOnDesc(user.getId());
        List<Long> ordersId = new ArrayList<>();
        orders.stream().forEach(order -> { ordersId.add(order.getId()); });
        model.addAttribute("claims", claims);
        model.addAttribute("ordersId", ordersId);
        return String.format("%s/claims", JSPVIEW);
    }

    @PostMapping
    public String setClaim(long orderId, String object, Model model) {
        User user = (User) Util.session().getAttribute("user");
		if (Util.iAmSuspended(userRepository)) {return Util.applyBanishment(model);}
        if (user == null) {
            Util.session().setAttribute("error", "You are not connected. Please log in.");
            return "redirect:/index.html";
        }
        Order order = orderRepo.findByIdAndCustomerId(orderId, user.getId());
        if (order == null) {
            model.addAttribute("error", "The order id provided is not valid");
            List<Claim> claims = claimRepo.findByCustomerId(user.getId());
            model.addAttribute("claims", claims);
            return String.format("%s/claims", JSPVIEW);
        }
        Claim claim = new Claim();
        claim.setCustomerId(user.getId());
        claim.setOrderId(orderId);
        claim.setObject(object);
        claim.setStatus(ClaimStatus.OPEN);
        claimRepo.save(claim);

        return "redirect:/claims.html";
    }

    @GetMapping(path = "/{id}")
    public String getClaimHistory(@PathVariable long id, Model model) {
    	if (Util.iAmSuspended(userRepository)) {return Util.applyBanishment(model);}
        Claim claim = claimRepo.findByOrderId(id);
        model.addAttribute("claim", claim);
        model.addAttribute("THEID", id);
        return String.format("%s/claim_history", JSPVIEW);
    }

    @PostMapping(path = "/{id}")
    public String updateClaimObject(@PathVariable long id, String modifiedObject, Model model) {
    	if (Util.iAmSuspended(userRepository)) {return Util.applyBanishment(model);}
        Claim claim = claimRepo.findByOrderId(id);
        claim.setObject(modifiedObject);
        claimRepo.save(claim);
        model.addAttribute("claim", claim);
        return String.format("%s/claim_history", JSPVIEW);
    }

    @PostMapping(path = "/{id}/message")
    public String addMessage(@PathVariable long id, String messageContent, Model model) {
    	if (Util.iAmSuspended(userRepository)) {return Util.applyBanishment(model);}
        Claim claim = claimRepo.findByOrderId(id);
        if (claim.getStatus() == ClaimStatus.CLOSED) {
            model.addAttribute("error", "Impossible to add a message, the object is closed.");
            model.addAttribute("claim", claim);
            return String.format("%s/claim_history", JSPVIEW);
        }
        User user = (User) Util.session().getAttribute("user");

        Message message = new Message();
        message.setSource(user.getRole());
        message.setContent(messageContent);
        message.setDate(new Date());
        messageRepo.save(message);
        claim.addMessage(message);
        this.claimRepo.save(claim);
        model.addAttribute("claim", claim);
        return "redirect:/claims/{id}.html";
    }
    
    @GetMapping(path= "/{id}/close")
    public String closeClaim(@PathVariable long id, Model model) {
    	if (Util.iAmSuspended(userRepository)) {return Util.applyBanishment(model);}
        User user = (User) Util.session().getAttribute("user");
        if (user == null) {
            Util.session().setAttribute("error", "You are not connected. Please log in.");
            return "redirect:/index.html";
        }
        if (user.getRole() == Role.USER) {
            Util.session().setAttribute("error", "You cannot do that, i will call the police !");
            return "redirect:/index.html"; 
        }
        Claim claim = this.claimRepo.findByOrderId(id);
        claim.setStatus(ClaimStatus.CLOSED);
        claim = this.claimRepo.save(claim);
        return "redirect:/claims.html";
    }
}
