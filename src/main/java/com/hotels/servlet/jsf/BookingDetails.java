package com.hotels.servlet.jsf;

import java.math.BigDecimal;

public class BookingDetails {
	private String productName;
	private BigDecimal subtotal;
	private BigDecimal shipping;
	private BigDecimal tax;
	private BigDecimal total;

	public BookingDetails(String productName, BigDecimal subtotal, 
			BigDecimal shipping, BigDecimal tax, BigDecimal total) {
		this.productName = productName;
		this.subtotal = subtotal;
		this.shipping = shipping;
		this.tax = tax;
		this.total = total;
	}

	public String getProductName() {
		return productName;
	}
	
	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public BigDecimal getShipping() {
		return shipping;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
