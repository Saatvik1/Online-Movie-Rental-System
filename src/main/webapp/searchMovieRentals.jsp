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
	This is the Search Movies page
	This page displays fields to search an movie by type or by name 
	The movie type details are fetched from "movies" request attribute
-->

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width:device-width, initial-scale=1">
		<link href="webjars/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" />
	</head>
	<body>
	
		<h1>Search movie rantals:</h1>
		<div class="container">
			<h2>Search Options:</h2>
			<div class="row">
				<c:if test="${empty movies}">
				</c:if>
				<c:if test="${not empty movies}">						
					<div class="col">
						<div class="card">
						  <div class="card-body">
						    <h5 class="card-title">Search by Movie Type</h5>
	    					<div class="container">
								<form method="POST" action="searchMovieRentalsByType">
									<label for="movieType">Select Movie Type:</label>
				   				    <select class="form-control" name="movieType">
										<c:forEach items="${movies}" var="cd">
											<option value="${cd.movieType}"> <c:out value = "${cd.movieType}"/></option>
										</c:forEach>
										
								    </select>
									<input type="submit" value="Search" class="btn btn-primary"/>
								</form>
							</div>
						  </div>
						</div>
					</div>
				</c:if>
				<div class="col">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title">Search by Movie Name</h5>
    					<div class="container">
							<form method="POST" action="searchMovieRentalsByName">
								<label for="movieName">Movie Name:</label>
	    						<input type="text" class="form-control" id="movieName" name="movieName" placeholder="Type Movie Name or a Part of it">
								<input type="submit" value="Search" class="btn btn-primary"/>
							</form>
						</div>
					  </div>
					</div>
				</div>
				<div class="col">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title">Search by Customer</h5>
    					<div class="container">
							<form method="POST" action="searchMovieRentalsByCustomer">
								<label for="customerName">Customer Name:</label>
	    						<input type="text" class="form-control" id="customerName" name="customerName" placeholder="Type Customer Name or a Part of it">
								<input type="submit" value="Search" class="btn btn-primary"/>
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