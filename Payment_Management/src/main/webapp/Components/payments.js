$(document).on("click", "#btnSave", function(event)
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 

// Form validation-------------------
var status = validatePaymentForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 

// If valid------------------------
var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "PaymentsAPI", 
 type : type, 
 data : $("#formPayment").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onPaymentSaveComplete(response.responseText, status); 
 } 
 }); 
});

function onPaymentSaveComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 }
$("#hidPaymentIDSave").val(""); 
$("#formPayment")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
		{ 
		$("#hidPaymentIDSave").val($(this).data("payment_id")); 
		 $("#payment_code").val($(this).closest("tr").find('td:eq(0)').text()); 
		 $("#customer_id").val($(this).closest("tr").find('td:eq(1)').text()); 
		 $("#month_of_bill").val($(this).closest("tr").find('td:eq(2)').text()); 
		 $("#bill_amount").val($(this).closest("tr").find('td:eq(3)').text()); 
		$("#date").val($(this).closest("tr").find('td:eq(4)').text()); 
		});




$(document).on("click", ".btnRemove", function(event)
		{ 
		 $.ajax( 
		 { 
		 url : "PaymentsAPI", 
		 type : "DELETE", 
		 data : "payment_id=" + $(this).data("paymentid"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onPaymentDeleteComplete(response.responseText, status); 
		 } 
		 }); 
		});
		
function onPaymentDeleteComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divPaymentsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}


// CLIENT-MODEL================================================================
function validatePaymentForm()
{
	// CODE
	if ($("#payment_code").val().trim() == "")
	{
	return "Insert Payment Code.";
	}
	
	// CUSTOMER
	if ($("#customer_id").val().trim() == "")
	{
	return "Insert Customer ID.";
	}

	//MONTH
	if ($("#month_of_bill").val().trim() == "")
	{
	return "Insert Month of Bill.";
	}
	
	// AMOUNT-------------------------------
	if ($("#bill_amount").val().trim() == ""){
		return "Insert Bill Amount.";
	}
		// is numerical value
		var tmpPrice = $("#bill_amount").val().trim();
		if (!$.isNumeric(tmpPrice))
	{
	return "Insert a numerical value for Bill Amount.";
	}
		
// convert to decimal price
$("#bill_amount").val(parseFloat(tmpPrice).toFixed(2));

// Date------------------------
if ($("#date").val().trim() == ""){
	
	return "Insert Payment Date.";
}
	return true;
}