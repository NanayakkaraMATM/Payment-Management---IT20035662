<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payments Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/payments.js"></script>

<!-- add css styles -->
<style>
body {
  background-color: #01002a;
  color:white;
    font-family: Tahoma, Verdana, sans-serif;
}

h1 {
  color:#fed700 ;
  text-align:center;
  font-family: Tahoma, Verdana, sans-serif;
}


input[type=text]:focus {
  background-color: #ffffb3;
  border: 3px solid #fed700;

}

</style>
</head>

<body> 
<div class="container"><div class="row"><div class="col-6"> 
<h1>Payment Management</h1>
<form id="formPayment" name="formPayment" method="post" action="payments.jsp">
 
 Payment code: 
 <input id="payment_code" name="payment_code" type="text" 
 class="form-control form-control-sm">
 
 <br> Customer ID: 
 <input id="customer_id" name="customer_id" type="text" 
 class="form-control form-control-sm">
 
 <br> Month of Bill: 
 <input id="month_of_bill" name="month_of_bill" type="text" 
 class="form-control form-control-sm">
 
 <br> Bill Amount: 
 <input id="bill_amount" name="bill_amount" type="text" 
 class="form-control form-control-sm">
 
  <br> Date: 
 <input id="date" name="date" type="text" 
 class="form-control form-control-sm">
 <br>
 
 <input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
 
 <input type="hidden" id="hidPaymentIDSave" 
 name="hidPaymentIDSave" value="">
</form>

<!-- alerts -->
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divPaymentsGrid">
 <%
 Payment paymentObj = new Payment(); 
 out.print(paymentObj.readPayments()); 
 %>
</div>
</div> </div> </div> 
</body>
</html>