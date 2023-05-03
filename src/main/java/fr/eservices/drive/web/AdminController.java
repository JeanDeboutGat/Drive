package fr.eservices.drive.web;

import fr.eservices.drive.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eservices.drive.repository.UserRepository;
import fr.eservices.drive.util.FilterHelper;
import fr.eservices.drive.util.Role;
import fr.eservices.drive.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    
    private static final String JSPVIEW = "authentification_and_admin";

    @Autowired
    UserRepository userRepository;
    
    private static final String ROLE_SELECT_PREFIX = "RoleSelect-";
    private static final String BAN_SELECT_PREFIX = "BanSelect-";

    @GetMapping("/admin.html")
    public String getAdminPage(Model model) {
        HttpSession session = Util.session();
        User currentUser = Util.getMyself();
        if (currentUser == null || currentUser.getRole() == Role.USER) {
            session.setAttribute("error", "Vous n'avez pas les droits pour accéder à cette page");
            return "redirect:/index.html";
        }
        if (Util.iAmSuspended(userRepository)) {return Util.applyBanishment(model);}
        model.addAllAttributes(getModelBasedParams());
        return String.format("%s/admin", JSPVIEW);
    }
    
    @RequestMapping(path="/admin.html", method= RequestMethod.POST)
    public String postAdminPage(@RequestParam Map<String,String> requestParams, Model model) throws ServletException, IOException {
    	String action = requestParams.get("POST-ACTION");
    	if (Util.iAmSuspended(userRepository)) {return Util.applyBanishment(model);}
    	if ("FILTER".equals(action)) {return applyFilter(requestParams ,model);}
    	if ("UPDATE".equals(action)) {return editUser(requestParams, model);}
    	return String.format("%s/admin", JSPVIEW);
    }

	private  String editUser(Map<String,String> requestParams, Model model) throws ServletException, IOException {
		updateUsers(requestParams);
		model.addAllAttributes(getModelBasedParams());
		return String.format("%s/admin", JSPVIEW);
	}
    
	private String applyFilter(Map<String,String> requestParams, Model model) throws ServletException, IOException {
		FilterWrapper filters = new FilterWrapper(requestParams);
    	Map<String, Object> modelAttribute = getModelBasedParams();
    	Iterable<User> user = applyFilter((Iterable<User>)modelAttribute.get("users"), filters );
    	modelAttribute.put("users", user);
    	model.addAllAttributes(modelAttribute);
    	return String.format("%s/admin", JSPVIEW);
	}
    
    private Iterable<User> applyFilter(Iterable<User> collection, FilterWrapper filters) {
    	Iterable<User> res = FilterHelper.filterUserByFisrtName(filters.firstname, collection);
    	res = FilterHelper.filterUserByLastName(filters.lastname, res);
    	res = FilterHelper.filterUserByID(filters.id, res);
    	if (!"BOTH".equals(filters.banState)) {
    		Boolean userShouldBeBan = "YES".equals(filters.banState);
    		res = FilterHelper.filterUserByHisBanismentState(userShouldBeBan, res);
    	}
    	return res;
    }
    
    private Map<String,Object> getModelBasedParams() {
    	Map<String, Object> model = new HashMap<String, Object>();
    	Iterable<User> allUsers = this.userRepository.findAll();
        List<String> userRoles = Stream.of(Role.values()).map(Role::name).collect(Collectors.toList());
        allUsers = removeMyselfFromUserList(allUsers);
        if (Util.getMyself().getRole() == Role.ADMIN) {
        	userRoles.remove("SUPERADMIN");
        	allUsers =  keepOnlyUserFromList(allUsers);
        }
        model.put("roles", userRoles);
        model.put("users", allUsers);
        model.put("ROLE_SELECT_PREFIX", ROLE_SELECT_PREFIX);
        model.put("BAN_SELECT_PREFIX", BAN_SELECT_PREFIX);
        return model;
    }
    
    private Iterable<User> removeMyselfFromUserList(Iterable<User> users) {
    	User myself = Util.getMyself();
    	List<User> myList = new ArrayList<User>();
    	for (User element : users) {
    		if (!element.getId().equals(myself.getId())) {myList.add(element);}
    	}
    	return myList;	
    }
    
    private Iterable<User> keepOnlyUserFromList(Iterable<User> users) {
    	List<User> myList = new ArrayList<User>();
    	for (User element : users) {
    		if (element.getRole() == Role.USER) {myList.add(element);}
    	}
    	return myList;	
    }
    
    private void updateUsers(Map<String,String> requestParams) {
    	Map<String, User> mappedUsers = getMappedUserByHisId();
    	Map<String, User> updatedUsers = new HashMap<String, User>();
    	for (String params : requestParams.keySet()) {
    		if (params.startsWith(ROLE_SELECT_PREFIX)) {
    			String userId = params.replace(ROLE_SELECT_PREFIX, "");
    			User s = getUserToUpdateById(userId, mappedUsers, updatedUsers);
    			if (s != null) {
    				s.setRole(Role.valueOf(requestParams.get(params)));
    				updatedUsers.put(""+s.getId(), s);
    			}
    		} 
    		if (params.startsWith(BAN_SELECT_PREFIX)) {
    			String userId = params.replace(BAN_SELECT_PREFIX, "");
    			User s = getUserToUpdateById(userId, mappedUsers, updatedUsers);
    			if (s != null) {
    				s.setIsBanned("YES".equals(requestParams.get(params)));
    				updatedUsers.put(""+s.getId(), s);
    			}
    		}
    	}
    	this.userRepository.save(updatedUsers.values());
    }
    
    private User getUserToUpdateById(String id, Map<String, User> mappedUsers,Map<String, User> updatedUsers) {
    	User res = updatedUsers.get(id);
    	return res == null ? mappedUsers.get(id) : res;
    }
    
    private Map<String, User> getMappedUserByHisId() {
    	Map<String, User> res = new HashMap<String, User>();
    	for (User s : this.userRepository.findAll()) {
    		res.put(""+s.getId(), s);
    	}
    	return res;
    }

    private class FilterWrapper {
    	public String lastname;
    	public String firstname;
    	public String id;
    	public String banState;
    	
    	public FilterWrapper(Map<String,String> requestParams) {
    		this.lastname = requestParams.get("lastnameFilter");
    		this.firstname = requestParams.get("firstnameFilter");
    		this.id = requestParams.get("idFilter");
    		this.banState = requestParams.get("banstateFilter");
    	}
    }
}
