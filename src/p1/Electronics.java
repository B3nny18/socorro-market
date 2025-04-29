package p1;

public class Electronics extends Product {
	// Attributes
	private String brand;
	private String model;
	private String warrantyPeriod;
	
	// Constructor
	public Electronics(int productID, String name, double price, String description, int stockQuantity, String brand, String model, String warrantyPeriod) {
		// Calls parent constructor
		super(productID, name, price, description, stockQuantity);
		this.brand = brand;
		this.model = model;
		this.warrantyPeriod = warrantyPeriod;
	}

	// Getters
	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}

	
	// Setters
	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}


    public String getDisplayInfo() {
        return "===== Electronics =====\n" +
               "Product ID: " + getProductID() + "\n" +
               "Name: " + getName() + "\n" +
               "Brand: " + brand + "\n" +
               "Model: " + model + "\n" +
               "Price: $" + getPrice() + "\n" +
               "Description: " + getDescription() + "\n" +
               "Warranty: " + warrantyPeriod + "\n" +
               "Stock: " + getStockQuantity();
    }
	
	
	
}