<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--
	This is the Set Account page
-->

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width:device-width, initial-scale=1">
		<link href="webjars/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" />
	</head>
	<body>
	
		<h1>Set Account</h1>
		<div class="container">
			<h2>Account Options:</h2>
			<div class="row">					
					<div class="col">
						<div class="card">
						  <div class="card-body">
	    					<div class="container">
								<form method="POST" action="setAccount">
									<label for="accountType">Select Account Type:</label>
				   				    <select class="form-control" name="accountType">
											<option value="Limited">Limited</option>
											<option value="Unlimited-1">Unlimited-1</option>
											<option value="Unlimited-2">Unlimited-2</option>
											<option value="Unlimited-3">Unlimited-3</option>		
								    </select>
									<input type="submit" value="Set" class="btn btn-primary"/>
								</form>
							</div>
						  </div>
						</div>
					</div>
			</div>				
		</div>
		<div class="container pt-1">
			<form action="home.jsp">
				<input type="submit" value="Home" class="btn btn-success"/>
			</form>
		</div>
		
		<script src="webjars/jquery/3.3.1/jquery.min.js"></script>
		<script src="webjars/bootstrap/4.1.3/bootstrap.min.js"></script>
	</body>
</html>