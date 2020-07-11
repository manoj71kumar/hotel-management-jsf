package com.hotels.servlet.jsf;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.hotels.domain.Booking;
import com.hotels.domain.Users;
import com.paypal.api.payments.Capture;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;


@ViewScoped
@ManagedBean
public class PayPalController {
	
    Transaction transaction = new Transaction();
    PayerInfo payerInfo =  new PayerInfo();
   
    public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public PayerInfo getPayerInfo() {
		return payerInfo;
	}

	public void setPayerInfo(PayerInfo payerInfo) {
		this.payerInfo = payerInfo;
	}
	
    public void init() throws IOException {
    	executePayment();
    }
    
    public void AuthorizePayment(List<Booking> bookingLst) throws IOException {
    	
    	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Users user = (Users)request.getSession().getAttribute("user");
    	String product = "Room";
    	BigDecimal shipping = BigDecimal.ZERO;
    	BigDecimal subtotal = BigDecimal.ZERO;
    	BigDecimal tax = BigDecimal.ZERO;
    	BigDecimal total = BigDecimal.ZERO;
    	
    	for(Booking b: bookingLst) {
    		subtotal = subtotal.add(BigDecimal.valueOf(b.getTotalAmount()));
    	}  	
    	
    	total = total.add(subtotal).add(shipping).add(tax);
    	
		BookingDetails bookingDetails = new BookingDetails(product, subtotal, shipping, tax, total);

		try {
			PayPalService paymentServices = new PayPalService();
			String approvalLink = paymentServices.authorizePayment(bookingDetails, user);

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    externalContext.redirect(approvalLink);
			
		} catch (PayPalRESTException ex) {
			ex.printStackTrace();
		}
    }
    
    public void executePayment() throws IOException {
    	Map<String, String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    	String paymentId = params.get("paymentId");
    	String payerID = params.get("PayerID");
    	
		try {
			PayPalService paymentServices = new PayPalService();
			Payment payment = paymentServices.executePayment(paymentId, payerID);
			
			this.payerInfo = payment.getPayer().getPayerInfo();
			this.transaction = payment.getTransactions().get(0);			

			
		} catch (PayPalRESTException ex) {
			ex.printStackTrace();
		}
    }
}