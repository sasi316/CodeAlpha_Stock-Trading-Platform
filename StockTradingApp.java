import java.util.*;

// --------- Stock Class ---------
class Stock {
    private String symbol;
    private String name;
    private double price;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    public void updatePrice() {
        double change = (Math.random() - 0.5) * 10;
        this.price = Math.max(1, this.price + change);
    }

    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}

// --------- User Class ---------
class User {
    private String name;
    private double balance;
    private Map<String, Integer> portfolio;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new HashMap<>();
    }

    public void buy(Stock stock, int quantity) {
        double total = stock.getPrice() * quantity;
        if (balance >= total) {
            balance -= total;
            portfolio.put(stock.getSymbol(), portfolio.getOrDefault(stock.getSymbol(), 0) + quantity);
            System.out.println("[INFO] Bought " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("[ERROR] Insufficient balance.");
        }
    }

    public void sell(Stock stock, int quantity) {
        String symbol = stock.getSymbol();
        if (portfolio.getOrDefault(symbol, 0) >= quantity) {
            portfolio.put(symbol, portfolio.get(symbol) - quantity);
            balance += stock.getPrice() * quantity;
            System.out.println("[INFO] Sold " + quantity + " shares of " + symbol);
        } else {
            System.out.println("[ERROR] Not enough shares to sell.");
        }
    }

    public void displayPortfolio() {
        System.out.println("\n===== Portfolio for " + name + " =====");
        System.out.println("Available Balance: Rs." + String.format("%.2f", balance));
        if (portfolio.isEmpty()) {
            System.out.println("You do not own any stocks.");
        } else {
            System.out.println("Stock\tQuantity");
            for (String symbol : portfolio.keySet()) {
                System.out.println(symbol + "\t" + portfolio.get(symbol));
            }
        }
    }
}

// --------- Market Class ---------
class Market {
    private Map<String, Stock> stocks = new HashMap<>();

    public void initSampleStocks() {
        stocks.put("AAPL", new Stock("AAPL", "Apple", 150));
        stocks.put("GOOG", new Stock("GOOG", "Google", 2800));
        stocks.put("TSLA", new Stock("TSLA", "Tesla", 700));
    }

    public void updatePrices() {
        for (Stock stock : stocks.values()) {
            stock.updatePrice();
        }
    }

    public void displayMarket() {
        System.out.println("\nSymbol\tName\tPrice");
        for (Stock stock : stocks.values()) {
            System.out.printf("%s\t%s\t%.2f\n", stock.getSymbol(), stock.getName(), stock.getPrice());
        }
    }

    public void buyStock(User user, String symbol, int quantity) {
        if (!stocks.containsKey(symbol)) {
            System.out.println("Invalid stock symbol.");
            return;
        }
        Stock stock = stocks.get(symbol);
        user.buy(stock, quantity);
    }

    public void sellStock(User user, String symbol, int quantity) {
        if (!stocks.containsKey(symbol)) {
            System.out.println("Invalid stock symbol.");1
            11
            return;
        }
        Stock stock = stocks.get(symbol);
        user.sell(stock, quantity);
    }
}

// --------- Main Class ---------
public class StockTradingApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Market market = new Market();
        User user = new User("Vijaya", 10000); // Starting balance: 10,000

        market.initSampleStocks();

        int choice;
        do {
            market.updatePrices();
            System.out.println("\n===============================");
            System.out.println("      STOCK TRADING SYSTEM     ");
            System.out.println("===============================");
            System.out.println("1. Display Market Stock Prices");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    market.displayMarket();
                    break;
                case 2:
                    market.displayMarket();
                    System.out.print("Enter Stock Symbol to Buy: ");
                    String buySymbol = sc.next();
                    System.out.print("Enter Quantity: ");
                    int buyQty = sc.nextInt();
                    market.buyStock(user, buySymbol.toUpperCase(), buyQty);
                    break;
                case 3:
                    user.displayPortfolio();
                    System.out.print("Enter Stock Symbol to Sell: ");
                    String sellSymbol = sc.next();
                    System.out.print("Enter Quantity: ");
                    int sellQty = sc.nextInt();
                    market.sellStock(user, sellSymbol.toUpperCase(), sellQty);
                    break;
                case 4:
                    user.displayPortfolio();
                    break;
                case 5:
                    System.out.println("Thank you for using the Stock Trading System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select between 1 and 5.");
            }
        } while (choice != 5);
    }
}
