<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/views/common/_header.jsp" %>
<form action="admin.html" method="post">
	<div class="card text-center centerdiv">
	  <div class="card-header">
	    Filtre
	  </div>
	  <div class="card-body centerdiv m-t-lg">
	   		<div class="form-outline mb-4">
			<label class="form-label" for="firstnameFilter">Prénom</label>
			<input type="text" id="firstnameFilter" class="form-control" name="firstnameFilter" />
		</div>


		<div class="form-outline">
			<label class="form-label" for="password">Nom</label>
			<input type="text" id="lastnameFilter" class="form-control" name="lastnameFilter" />
		</div>
		
		<div class="form-outline m-b-lg">
			<label class="form-label" for="idFilter">id</label>
			<input type="text" id="idFilter" class="form-control" name="idFilter" />
		</div>
		
		<div class="form-outline m-b-md">
			<label class="form-label" for="banstateFilter">Banni ?</label>
			<select name="banstateFilter"  id= "banstateFilter" class="form-select">
				<option value="BOTH" selected="selected">Les deux</option>
				<option value="YES">Oui</option>
				<option value="NO">Non</option>
			</select>
		</div>
		
		
		<input style="display : none" type="text" id="POST-ACTION" class="form-control" name="POST-ACTION" value="FILTER" />
	  </div>
		<div class="text-muted centerdiv m-b-md">
	    	<!-- Submit button -->
			<button type="submit" class="btn btn-primary btn-block mb-4" style="margin-top: 10px; margin-bottom: 10px">Filter</button>
		</div>
	</div>
  </form>

<table class="table centerdiv">
  <thead>
    <tr>
      <th scope="col">#ID</th>
      <th scope="col">Nom</th>
      <th scope="col">Prénom</th>
      <th scope="col">Role</th>
      <th scope="col">Bannir</th>
      <th scope="col">Actions</th>
    </tr>
  </thead>
  <tbody>
  	<c:forEach items="${users}" var="user">
  		<form action="admin.html" method="POST">
 			<tr>
		      <th scope="row">${user.id}</th>
		      <td>${user.lastName}</td>
		      <td>${user.firstName}</td>
		      <td>
		      <select name="${ROLE_SELECT_PREFIX}${user.id}"  id= "${ROLE_SELECT_PREFIX}${user.id}" class="form-select" value="${user.getStringfiedRole()}">
				  <c:forEach items="${roles}" var="role">
					  <option value="${role}"  <c:if test = "${role.equals(user.getStringfiedRole())}">selected="selected"</c:if> >${role}</option>
				  </c:forEach>
				</select>
		      </td>
		      <td>
				<select name="${BAN_SELECT_PREFIX}${user.id}"  id= "${BAN_SELECT_PREFIX}${user.id}" class="form-select" value="${user.getStringfiedRole()}">
					<option value="YES"  <c:if test = "${user.isBanned}">selected="selected"</c:if> >Oui</option>
					<option value="NO"  <c:if test = "${!user.isBanned}">selected="selected"</c:if> >Non</option>
				</select>
		      </td>
		      <td>
		      	<input style="display : none" type="text" id="POST-ACTION" class="form-control" name="POST-ACTION" value="UPDATE" />
	    			<button class="btn btn-success" type="submit">Valider</button>
	    		  </td>
		    </tr>
  		</form>
  	</c:forEach>
  </tbody>
</table>
<%@include file="/WEB-INF/views/common/_footer.jsp" %>



