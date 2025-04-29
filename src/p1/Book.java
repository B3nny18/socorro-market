package p1;

public class Book extends Product {
    // Attributes
    private String author;
    private String ISBN;
    private String publisher;
    private String genre;

    // Constructor
    public Book(int productID, String name, double price, String description, int stockQuantity, String author, String ISBN, String publisher, String genre) {
        super(productID, name, price, description, stockQuantity);
        this.author = author;
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.genre = genre;
    }

    // Getters
    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getGenre() {
        return genre;
    }

    // Setters
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    
    public String getDisplayInfo() {
        return "===== Book =====\n" +
               "Product ID: " + getProductID() + "\n" +
               "Title: " + getName() + "\n" +
               "Author: " + author + "\n" +
               "ISBN: " + ISBN + "\n" +
               "Publisher: " + publisher + "\n" +
               "Genre: " + genre + "\n" +
               "Price: $" + getPrice() + "\n" +
               "Description: " + getDescription() + "\n" +
               "Stock: " + getStockQuantity();
    }
}
