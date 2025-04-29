package p1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataManager dataManager = new DataManager();

        // Load data from CSVs
        dataManager.loadBooksFromCSV("books.csv");
        dataManager.loadElectronicsFromCSV("electronics.csv");
        dataManager.loadCustomersFromCSV("customers.csv");
        dataManager.loadOrdersFromCSV("orders.csv");

        boolean running = true;
        while (running) {
            System.out.println("\n===== Welcome to Socorro Market =====");
            System.out.println("Are you a:");
            System.out.println("1. Customer");
            System.out.println("2. Manager");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    runCustomerMenu(scanner, dataManager);
                    break;
                case "2":
                    runManagerMenu(scanner, dataManager);
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        dataManager.saveAll();
        scanner.close();
        System.out.println("Thank you for using Socorro Market!");
    }

    public static void runCustomerMenu(Scanner scanner, DataManager dataManager) {
        boolean back = false;
        while (!back) {
        	System.out.println("1. View Products");
        	System.out.println("2. Search Products by Name"); 
        	System.out.println("3. Place an Order");
        	System.out.println("4. View My Purchase History");
        	System.out.println("5. Go Back");


            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    dataManager.printAllProducts();
                    break;
                    
                case "2":
                    searchProducts(scanner, dataManager);
                    break;
                    
                case "3":
                    System.out.print("Enter your customer ID: ");
                    int customerID = Integer.parseInt(scanner.nextLine());
                    Customer customer = dataManager.getCustomerByID(customerID);

                    if (customer == null) {
                        System.out.println("Customer not found.");
                        return;
                    }

                    Order order = new Order(dataManager.generateOrderID(), customer);
                    boolean ordering = true;
                    while (ordering) {
                        System.out.println("\nAvailable Products:");
                        dataManager.printAllProducts();

                        System.out.print("Enter product ID to add to order (or 0 to finish): ");
                        int productID = Integer.parseInt(scanner.nextLine());

                        if (productID == 0) break;

                        Product product = dataManager.getProductByID(productID);
                        if (product == null) {
                            System.out.println("Product not found.");
                            continue;
                        }

                        System.out.print("Enter quantity: ");
                        int qty = Integer.parseInt(scanner.nextLine());

                        if (qty > product.getStockQuantity()) {
                            System.out.println("Not enough stock available.");
                            continue;
                        }

                        order.addItem(product, qty);
                        System.out.println("Added to order.");
                    }

                    if (!order.getItems().isEmpty()) {
                        order.checkout();
                        System.out.println("Order placed successfully!");
                        System.out.println(order.getDisplayInfo());
                    } else {
                        System.out.println("Order was empty and was not placed.");
                    }
                    break;
                    
                case "4":
                    System.out.print("Enter your customer ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Customer c = dataManager.getCustomerByID(id);
                    if (c != null) {
                        System.out.println(c.getFormattedPurchaseHistory());
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                    
                case "5":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void runManagerMenu(Scanner scanner, DataManager dataManager) {
        boolean back = false;
        while (!back) {
            System.out.println("\n===== Manager Menu =====");
            System.out.println("1. View All Products");
            System.out.println("2. View Customers");
            System.out.println("3. Add a Product");
            System.out.println("4. Update a Product");
            System.out.println("5. Delete a Product");
            System.out.println("6. Exit to Main Menu");
            System.out.println("7. View Inventory Summary");
            System.out.println("8. View All Orders");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    dataManager.printAllProducts();
                    break;
                    
                case "2":
                    for (Customer c : dataManager.getCustomers()) {
                        System.out.println(c.getCustomerID() + ": " + c.getName());
                    }
                    break;
                    
                case "3":
                    addProduct(scanner, dataManager);
                    break;
                    
                case "4":
                    updateProduct(scanner, dataManager);
                    break;
                    
                case "5":
                    deleteProduct(scanner, dataManager);
                    break;
                    
                case "6":
                    back = true;
                    break;
                    
                case "7":
                    viewInventorySummary(dataManager);
                    break;
                    
                case "8":
                    viewAllOrders(dataManager);
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    
    }

    public static void addProduct(Scanner scanner, DataManager dataManager) {
        System.out.println("What type of product?");
        System.out.println("1. Book");
        System.out.println("2. Electronics");
        String type = scanner.nextLine();

        System.out.print("Enter Product ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Description: ");
        String desc = scanner.nextLine();

        System.out.print("Stock Quantity: ");
        int qty = Integer.parseInt(scanner.nextLine());

        if (type.equals("1")) {
            System.out.print("Author: ");
            String author = scanner.nextLine();
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Publisher: ");
            String publisher = scanner.nextLine();
            System.out.print("Genre: ");
            String genre = scanner.nextLine();

            Book book = new Book(id, name, price, desc, qty, author, isbn, publisher, genre);
            dataManager.addProduct(book);
            System.out.println("Book added!");
        } else if (type.equals("2")) {
            System.out.print("Brand: ");
            String brand = scanner.nextLine();
            System.out.print("Model: ");
            String model = scanner.nextLine();
            System.out.print("Warranty (years): ");
            String warranty = scanner.nextLine();

            Electronics e = new Electronics(id, name, price, desc, qty, brand, model, warranty);
            dataManager.addProduct(e);
            System.out.println("Electronics item added!");
        } else {
            System.out.println("Invalid type.");
        }
    }

    public static void updateProduct(Scanner scanner, DataManager dataManager) {
        System.out.print("Enter Product ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        Product existing = dataManager.getProductByID(id);
        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("New Name (" + existing.getName() + "): ");
        String name = scanner.nextLine();

        System.out.print("New Price (" + existing.getPrice() + "): ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("New Description: ");
        String desc = scanner.nextLine();

        System.out.print("New Stock Quantity: ");
        int qty = Integer.parseInt(scanner.nextLine());

        if (existing instanceof Book) {
            Book b = (Book) existing;
            System.out.print("New Author (" + b.getAuthor() + "): ");
            String author = scanner.nextLine();
            System.out.print("New ISBN (" + b.getISBN() + "): ");
            String isbn = scanner.nextLine();
            System.out.print("New Publisher (" + b.getPublisher() + "): ");
            String publisher = scanner.nextLine();
            System.out.print("New Genre (" + b.getGenre() + "): ");
            String genre = scanner.nextLine();

            Book updated = new Book(id, name, price, desc, qty, author, isbn, publisher, genre);
            dataManager.updateProduct(id, updated);
        } else if (existing instanceof Electronics) {
            Electronics e = (Electronics) existing;
            System.out.print("New Brand (" + e.getBrand() + "): ");
            String brand = scanner.nextLine();
            System.out.print("New Model (" + e.getModel() + "): ");
            String model = scanner.nextLine();
            System.out.print("New Warranty (" + e.getWarrantyPeriod() + "): ");
            String warranty = scanner.nextLine();

            Electronics updated = new Electronics(id, name, price, desc, qty, brand, model, warranty);
            dataManager.updateProduct(id, updated);
        }

        System.out.println("Product updated!");
    }

    public static void deleteProduct(Scanner scanner, DataManager dataManager) {
        System.out.print("Enter Product ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (dataManager.deleteProduct(id)) {
            System.out.println("Product deleted.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public static void viewInventorySummary(DataManager dataManager) {
        int totalBooks = 0;
        int totalElectronics = 0;
        int totalItems = 0;

        for (Product p : dataManager.getAllProducts()) {
            totalItems += p.getStockQuantity();
            if (p instanceof Book) totalBooks += p.getStockQuantity();
            else if (p instanceof Electronics) totalElectronics += p.getStockQuantity();
        }

        System.out.println("\n===== Inventory Summary =====");
        System.out.println("Total products in stock: " + totalItems);
        System.out.println("Books in stock: " + totalBooks);
        System.out.println("Electronics in stock: " + totalElectronics);
    }


    public static void viewAllOrders(DataManager dataManager) {
        System.out.println("\n===== All Orders =====");

        for (Order order : dataManager.getAllOrders()) {
            System.out.println(order.getDisplayInfo());
            System.out.println("---------------------------");
        }

        if (dataManager.getAllOrders().isEmpty()) {
            System.out.println("No orders found.");
        }
    }

    
    public static void searchProducts(Scanner scanner, DataManager dataManager) {
        System.out.print("Enter product name to search: ");
        String keyword = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Product product : dataManager.getAllProducts()) {
        	
            if (product.getName().toLowerCase().contains(keyword)) {
                System.out.println(product.getDisplayInfo());
                System.out.println("---------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No products matched your search.");
        }
    }
    
}