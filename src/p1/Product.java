package p1;

// We make an abstract class because it acts as a template or base class
public abstract class Product{
	// Attributes
	private int productID;
	private String name;
	private double price;
	private String description;
	private int stockQuantity;
	
	// Constructor
	public Product( int productID, String name, double price, String description, int stockQuantity) {
		this.productID = productID;
		this.name = name;
		this.price = price;
		this.description = description; 
		this.stockQuantity = stockQuantity;
		
	}

	// Getters: get product information
	public int getProductID() {
		return productID;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	
	
	// Setters if the manager wants to change the name, price, description, and quantity of product
	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		if (price >=0) {
			this.price = price;
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStockQuantity(int stockQuantity) {
		if (stockQuantity >=0) {
			this.stockQuantity = stockQuantity;
		}
	}

	
	// Stock Helper: make sure the stock doesn't reach below 0 and alert user when stock is too low
	public void reduceStock(int quantity) {
		if (quantity <= stockQuantity) {
			this.stockQuantity -= quantity;
		}
		else {
			System.out.println("Not enough stock quantity to reduce.");
		}
	}
	
	
	// Abstract Method: forces subclasses like Electronics and Book to display their own details
	public abstract String getDisplayInfo();
	
}

