package p1;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

// Cart and Transaction class combined 
public class Order {

	// Attributes
	private int orderID;
	private Customer customer;
	private Map<Product, Integer> items;
	private double tax;
	private double discount;
	private double totalCost;
	private LocalDate date;
	private boolean isCheckedOut;
	
	// Constructor
	public Order(int orderID, Customer customer) {
		this.orderID = orderID;
		this.customer = customer;
		this.items = new HashMap<>();
		this.tax = 7.62; // Average combined state and local sales tax rate
		this.discount = 0.0;
		this.totalCost = 0.0;
		this.isCheckedOut = false;
	}
	
	// Add item to cart
	public void addItem(Product product, int quantity) {
		if (isCheckedOut) return;
		
		items.put(product, items.getOrDefault(product, 0) + quantity);
	}
	
	
	// Remove item from cart
	public void removeItem(Product product) {
		if (isCheckedOut) return;
		
		items.remove(product);
	}
	

	// Automatically apply promotions based on cart contents
	private void checkPromotions() {
	    for (Map.Entry<Product, Integer> entry : items.entrySet()) {
	        Product product = entry.getKey();
	        int qty = entry.getValue();

	        // Example: Buy 3 or more books → 10% discount
	        if (product instanceof Book && qty >= 3) {
	            this.discount = 10.0;
	            return; // It will stop after applying one promotion
	        }
	    }
	}

	
	// Calculate total with tax and discount
	public double calculateTotal() {
		double subtotal = 0.0;
		
		for(Map.Entry<Product, Integer> entry : items.entrySet()) {
			subtotal += entry.getKey().getPrice() * entry.getValue();
		}
		
		double discountAmount = subtotal * (discount / 100);
		double taxedAmount = (subtotal - discountAmount) * (tax / 100);
		return subtotal - discountAmount + taxedAmount;
	}
	
	
	// Checkout logic
	public void checkout() {
		if (items.isEmpty() || isCheckedOut) return;
		
		checkPromotions();
		totalCost = calculateTotal();
		
		if (date == null) {
	        date = LocalDate.now();
	    }
		
		isCheckedOut = true;
			
		for (Map.Entry<Product, Integer> entry: items.entrySet()) {
			Product product = entry.getKey();
			int qty = entry.getValue();
			product.reduceStock(qty);
		}
			
		customer.addPurchaseHistory(this);
	}
		
		
	// If cart empty
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	
	public String getDisplayInfo() {
	    String result = "";
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	    result += "===== Order #" + orderID + " =====\n";
	    result += "Customer: " + customer.getName() + "\n";
	    result += "Date: " + ((date != null) ? date.format(formatter) : "N/A") + "\n\n";

	    result += "Items:\n";
	    for (Map.Entry<Product, Integer> entry : items.entrySet()) {
	        Product product = entry.getKey();
	        int qty = entry.getValue();
	        result += "- " + product.getName() + " x" + qty + " @ $" + product.getPrice() + "\n";
	    }

	    result += "\nSubtotal: $" + String.format("%.2f", totalCost) + "\n";
	    result += "Discount: " + discount + "%\n";
	    result += "Tax: " + tax + "%\n";
	    result += "Final Total: $" + String.format("%.2f", totalCost) + "\n";

	    return result;
	}
	
	public List<String> toCSVRows() {
	    List<String> rows = new ArrayList<>();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

	    for (Map.Entry<Product, Integer> entry : items.entrySet()) {
	        Product product = entry.getKey();
	        int qty = entry.getValue();
	        double price = product.getPrice() * qty;

	        String row = orderID + "," +
	                     product.getProductID() + "," +
	                     customer.getCustomerID() + "," +
	                     qty + "," +
	                     String.format("%.2f", price) + "," +
	                     date.format(formatter);

	        rows.add(row);
	    }

	    return rows;
	}

	
	// Getters
	public int getOrderID() {
		return orderID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Map<Product, Integer> getItems() {
		return items;
	}

	public double getTax() {
		return tax;
	}

	public double getDiscount() {
		return discount;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public LocalDate getDate() {
		return date;
	}

	public boolean isCheckedOut() {
		return isCheckedOut;
	}
	
	
	// Setter
	public void setDate(LocalDate date) {
	    this.date = date;
	}
	
	public void setTax(double tax) {
		this.tax = tax;
	}
	
}
