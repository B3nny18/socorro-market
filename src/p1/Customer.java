package p1;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    // Attributes
	private int customerID;
    private String name;
    private String contactDetails;
    private List<Order> purchaseHistory;

    // Constructor
    public Customer(int customerID,String name, String contactDetails) {
        this.customerID = customerID;
    	this.name = name;
        this.contactDetails = contactDetails;
        this.purchaseHistory = new ArrayList<>();
    }

    // Getters
    public int getCustomerID() {
    	return customerID;
    }
    public String getName() {
        return name;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public List<Order> getPurchaseHistory() {
        return purchaseHistory;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    // Add to purchase history
    public void addPurchaseHistory(Order order) {
        purchaseHistory.add(order);
    }

    
    // Return formatted purchase history
    public String getFormattedPurchaseHistory() {
        if (purchaseHistory.isEmpty()) {
            return "No purchases yet.";
        }

        String result = "Purchase History for " + name + ":\n";

        for (Order order : purchaseHistory) {
            result += order.getDisplayInfo() + "\n\n";
        }

        return result;
    }

}
