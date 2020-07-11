package com.hotels.servlet.jsf;

import java.util.ArrayList;
import java.util.List;

import com.hotels.domain.Users;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Authorization;
import com.paypal.api.payments.Capture;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class PayPalService {
	private static final String CLIENT_ID = "AcSavx4CZ8aPVP4gXhQ5k2ecR8NUAA8YKsNYfMqsYoaey-KEFzmQrKu14NtLEQuQ74HHnlhBDkF6KlwX";
	private static final String CLIENT_SECRET = "EGumAHCHDGissRRs8NbIEl0RgLoZ0hbY9j3tL_JgGJvBHJnrXRXtvj6x56gwMxG-E4DQQ-M4kgHAj7rP";
	private static final String MODE = "sandbox";

	public String authorizePayment(BookingDetails orderDetail, Users user)			
			throws PayPalRESTException {		

		Payer payer = getPayerInformation(user);
		RedirectUrls redirectUrls = getRedirectURLs();
		List<Transaction> listTransaction = getTransactionInformation(orderDetail);
		
		Payment requestPayment = new Payment();
		requestPayment.setTransactions(listTransaction);
		requestPayment.setRedirectUrls(redirectUrls);
		requestPayment.setPayer(payer);
		requestPayment.setIntent("authorize");

		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

		Payment approvedPayment = requestPayment.create(apiContext);

		System.out.println("=== CREATED PAYMENT: ====");
		System.out.println(approvedPayment);

		return getApprovalLink(approvedPayment);

	}
	
	private Payer getPayerInformation(Users usr) {
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		PayerInfo payerInfo = new PayerInfo();
		
		payerInfo.setFirstName(usr.getFirstName()).setLastName(usr.getLastName()).setEmail(usr.getEmailAddress());
		
//      for local host only 
//		payerInfo.setFirstName("John")
//				 .setLastName("Doe")
//				 .setEmail("sb-6m2a32520210@personal.example.com");
//		
		payer.setPayerInfo(payerInfo);
		
		return payer;
	}
	
	private RedirectUrls getRedirectURLs() {
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:8080/hotel_management/cancel.xhtml");
		redirectUrls.setReturnUrl("http://localhost:8080/hotel_management/review_payment.xhtml");
		
		return redirectUrls;
	}
	
	private List<Transaction> getTransactionInformation(BookingDetails orderDetail) {
		Details details = new Details();
		details.setShipping(orderDetail.getShipping().toString());
		details.setSubtotal(orderDetail.getSubtotal().toString());
		details.setTax(orderDetail.getTax().toString());

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(orderDetail.getTotal().toString());
		amount.setDetails(details);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(orderDetail.getProductName());
		
		ItemList itemList = new ItemList();
		List<Item> items = new ArrayList<>();
		
		Item item = new Item();
		item.setCurrency("USD");
		item.setName(orderDetail.getProductName());
		item.setPrice(orderDetail.getSubtotal().toString());
		item.setTax(orderDetail.getTax().toString());
		item.setQuantity("1");
		
		items.add(item);
		itemList.setItems(items);
		transaction.setItemList(itemList);

		List<Transaction> listTransaction = new ArrayList<>();
		listTransaction.add(transaction);	
		
		return listTransaction;
	}
	
	private String getApprovalLink(Payment approvedPayment) {
		List<Links> links = approvedPayment.getLinks();
		String approvalLink = null;
		
		for (Links link : links) {
			if (link.getRel().equalsIgnoreCase("approval_url")) {
				approvalLink = link.getHref();
				break;
			}
		}		
		
		return approvalLink;
	}

	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		Payment payment = new Payment().setId(paymentId);

		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

		return payment.execute(apiContext, paymentExecution);
	}
	
	public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		return Payment.get(apiContext, paymentId);
	}
}

