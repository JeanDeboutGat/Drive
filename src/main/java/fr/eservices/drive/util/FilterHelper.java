package fr.eservices.drive.util;

import java.util.ArrayList;
import java.util.List;

import fr.eservices.drive.model.User;

public class FilterHelper {

	
	public static Iterable<User> filterUserByFisrtName(String name, Iterable<User> list) {
		if (Util.isEmpty(name)) {return list;}
		List<User> res = new ArrayList<User>();
		for (User element : list) {
			if (name.equals(element.getFirstName())) {res.add(element);}
		}
		return res;
	}
	
	public static Iterable<User> filterUserByLastName(String name, Iterable<User> list) {
		if (Util.isEmpty(name)) {return list;}
		List<User> res = new ArrayList<User>();
		for (User element : list) {
			if (name.equals(element.getLastName())) {res.add(element);}
		}
		return res;
	}
	
	public static Iterable<User> filterUserByID(String id, Iterable<User> list) {
		if (Util.isEmpty(id)) {return list;}
		List<User> res = new ArrayList<User>();
		for (User element : list) {
			if (id.equals(""+element.getId())) {res.add(element);}
		}
		return res;
	}
	
	public static Iterable<User> filterUserByHisBanismentState(Boolean state, Iterable<User> list) {
		List<User> res = new ArrayList<User>();
		for (User element : list) {
			if (state == element.getIsBanned()) {res.add(element);}
		}
		return res;
	}

}
