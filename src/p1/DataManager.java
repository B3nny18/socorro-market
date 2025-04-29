package p1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


// Combines InventorManager(manager) and FileManager into one class
public class DataManager {

	// Attributes
	private List<Product> products;
	private List<Customer> customers;
	private List<Order> orders;
	
	// Constructor
	public DataManager() {
        products = new ArrayList<>();
        customers = new ArrayList<>();
        orders = new ArrayList<>();
    }
	
	
	// Find a product by its ID
	public Product getProductByID(int id) {
	    for (Product p : products) {
	        if (p.getProductID() == id) {
	            return p;
	        }
	    }
	    return null;
	}
	
	// Find a customer by their ID
	public Customer getCustomerByID(int id) {
	    for (Customer c : customers) {
	        if (c.getCustomerID() == id) return c;
	    }
	    return null;
	}

	
	// Add a new product to the list
	public void addProduct(Product product) {
	    products.add(product);
	}

	// Update an existing product by ID
	public boolean updateProduct(int id, Product updatedProduct) {
	    for (int i = 0; i < products.size(); i++) {
	        if (products.get(i).getProductID() == id) {
	            products.set(i, updatedProduct);
	            return true;
	        }
	    }
	    return false;
	}

	// Delete a product by ID
	public boolean deleteProduct(int id) {
	    return products.removeIf(p -> p.getProductID() == id);
	}

	public List<Product> getAllProducts() {
	    return products;
	}
	
	public void printAllProducts() {
	    for (Product p : products) {
	        System.out.println(p.getName() + " (" + p.getProductID() + ")");
	    }
	}

	
	
	
	// Reading  book CSV
	public void loadBooksFromCSV(String filename) {
	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        br.readLine(); // Skip header

	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");

	            Book book = new Book(
	                Integer.parseInt(data[0]), // productID
	                data[1],                   // name
	                Double.parseDouble(data[2]),
	                data[3],                   // description
	                Integer.parseInt(data[4]), // stockQuantity
	                data[5],                   // author
	                data[6],                   // ISBN
	                data[7],                   // publisher
	                data[8]                    // genre
	            );

	            products.add(book);
	        }
	    } catch (IOException e) {
	        System.out.println("Error loading books: " + e.getMessage());
	    }
	}

	
	// Reading electronics CSV
	public void loadElectronicsFromCSV(String filename) {
	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        br.readLine(); // Skip header

	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");

	            Electronics e = new Electronics(
	                Integer.parseInt(data[0]),
	                data[1],
	                Double.parseDouble(data[2]),
	                data[3],
	                Integer.parseInt(data[4]),
	                data[5],  // brand
	                data[6],  // model
	                data[7]   // warrantyPeriod
	            );

	            products.add(e);
	        }
	    } catch (IOException e) {
	        System.out.println("Error loading electronics: " + e.getMessage());
	    }
	}

	
	// Reading customers CSV
	public void loadCustomersFromCSV(String filename) {
	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        br.readLine(); // Skip header

	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");

	            Customer customer = new Customer(
	            	Integer.parseInt(data[0]),	// customerID
	                data[1], 					// name
	                data[2]  					// contact
	            );

	            customers.add(customer);
	        }
	    } catch (IOException e) {
	        System.out.println("Error loading customers: " + e.getMessage());
	    }
	}

	
	// Reading orders CSV
	public void loadOrdersFromCSV(String filename) {
	    Map<Integer, Order> orderMap = new HashMap<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        br.readLine(); // Skip header

	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");

	            int orderID = Integer.parseInt(data[0]);
	            int productID = Integer.parseInt(data[1]);
	            int customerID = Integer.parseInt(data[2]);
	            int qty = Integer.parseInt(data[3]);
	            //double price = Double.parseDouble(data[4]);
	            LocalDate date = LocalDate.parse(data[5], DateTimeFormatter.ofPattern("M/d/yyyy"));

	            // Find matching product and customer
	            Product product = getProductByID(productID);
	            Customer customer = getCustomerByID(customerID);

	            if (product != null && customer != null) {
	                Order order = orderMap.get(orderID);

	                if (order == null) {
	                    order = new Order(orderID, customer);
	                    orderMap.put(orderID, order);
	                }

	                order.addItem(product, qty);
	                order.setDate(date);
	                order.setTax(7.62);  // Default tax rate
	                order.checkout();    // Finalize total, mark as checked out
	            }
	        }

	        // Save orders to main list
	        orders.addAll(orderMap.values());

	    } catch (IOException e) {
	        System.out.println("Error loading orders: " + e.getMessage());
	    }
	}
	
	public void saveBooksToCSV(String filename) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
	        writer.write("productID,name,price,description,stockQuantity,author,ISBN,publisher,genre");
	        writer.newLine();

	        for (Product p : products) {
	            if (p instanceof Book) {
	                Book b = (Book) p;
	                String row = b.getProductID() + "," +
	                             b.getName() + "," +
	                             b.getPrice() + "," +
	                             b.getDescription() + "," +
	                             b.getStockQuantity() + "," +
	                             b.getAuthor() + "," +
	                             b.getISBN() + "," +
	                             b.getPublisher() + "," +
	                             b.getGenre();
	                writer.write(row);
	                writer.newLine();
	            }
	        }

	    } catch (IOException e) {
	        System.out.println("Error saving books: " + e.getMessage());
	    }
	}

	
	public void saveElectronicsToCSV(String filename) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
	        writer.write("productID,name,price,description,stockQuantity,brand,model,warrantyPeriod");
	        writer.newLine();

	        for (Product p : products) {
	            if (p instanceof Electronics) {
	                Electronics e = (Electronics) p;
	                String row = e.getProductID() + "," +
	                             e.getName() + "," +
	                             e.getPrice() + "," +
	                             e.getDescription() + "," +
	                             e.getStockQuantity() + "," +
	                             e.getBrand() + "," +
	                             e.getModel() + "," +
	                             e.getWarrantyPeriod();
	                writer.write(row);
	                writer.newLine();
	            }
	        }

	    } catch (IOException e) {
	        System.out.println("Error saving electronics: " + e.getMessage());
	    }
	}

	
	public void saveCustomersToCSV(String filename) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
	        writer.write("customerID,name,contact");
	        writer.newLine();

	        for (Customer c : customers) {
	            String row = c.getCustomerID() + "," +
	                         c.getName() + "," +
	                         c.getContactDetails();
	            writer.write(row);
	            writer.newLine();
	        }

	    } catch (IOException e) {
	        System.out.println("Error saving customers: " + e.getMessage());
	    }
	}
	
	public void saveOrdersToCSV(String filename) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
	        // Write CSV header
	        writer.write("orderID,productID,customerID,qty,price,date");
	        writer.newLine();

	        // Write each order's rows
	        for (Order order : orders) {
	            List<String> rows = order.toCSVRows();
	            for (String row : rows) {
	                writer.write(row);
	                writer.newLine();
	            }
	        }

	        System.out.println("Orders saved to " + filename);

	    } catch (IOException e) {
	        System.out.println("Error saving orders: " + e.getMessage());
	    }
	}

	public void saveAll() {
	    saveBooksToCSV("books.csv");
	    saveElectronicsToCSV("electronics.csv");
	    saveCustomersToCSV("customers.csv");
	    saveOrdersToCSV("orders.csv");

	    System.out.println("All data successfully saved to CSV files.");
	}
	
	public int generateOrderID() {
	    return orders.size() + 100;  // or use max ID + 1 if you prefer
	}

	public List<Customer> getCustomers() {
	    return customers;
	}

	public List<Order> getAllOrders() {
	    return orders;
	}

}
