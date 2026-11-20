# Madeline Cosmetic – E-Commerce Web Application

A Java-based cosmetic e-commerce web application that allows customers to browse products, manage shopping carts, place orders, and track purchases. The system also provides staff with tools to manage inventory, monitor stock movements, process orders, and view sales information.

## About the Project

Madeline Cosmetic is a web-based shopping and management system developed to support the daily operations of a cosmetic retail business.

The application provides two main interfaces:

- **Customer Interface:** Allows customers to browse cosmetic products, manage their shopping carts, place orders, and view order history.
- **Staff Interface:** Allows staff to manage products, inventory, stock movements, customer orders, and sales information.

## Features

### Customer Features
- Customer registration and login
- Browse cosmetic products
- View product details and variations
- Add products to shopping cart
- Manage cart items
- Checkout and select payment type
- View order status
- Track order history
- View receipts
- Manage customer profile

### Staff Features
- Staff login and dashboard
- View sales and revenue information
- View sales per item
- Manage product inventory
- Add and edit products
- Manage product variations
- Manage staff accounts
- View and manage customer orders
- Process orders
- Record stock-in and stock-out movements
- View inventory and stock movement records
- Manage sales ledger

## Technologies Used

**Frontend**
- HTML5
- CSS3
- JavaScript
- JavaServer Pages (JSP)

**Backend**
- Java
- Java Servlets
- JavaServer Pages (JSP)
- JDBC

**Database**
- Apache Derby

**Development Tools**
- Apache NetBeans
- Apache Ant
- GlassFish Server

## Project Structure

```text
MCWebApp/
│
├── images/                 # Project images
│
├── MCWebApp/
│   ├── src/
│   │   ├── java/
│   │   │   ├── dao/        # Database access objects
│   │   │   ├── model/      # Data models
│   │   │   ├── servlet/    # Application controllers
│   │   │   ├── test/       # Database and DAO tests
│   │   │   ├── util/       # Database connection utilities
│   │   │   └── view/       # View-related Java classes
│   │   └── conf/           # Application configuration
│   │
│   ├── web/
│   │   ├── assets/
│   │   │   ├── css/        # Stylesheets
│   │   │   └── images/     # Product and UI images
│   │   ├── WEB-INF/        # Web application configuration
│   │   └── *.jsp           # Application pages
│   │
│   ├── nbproject/          # NetBeans project configuration
│   ├── build.xml           # Ant build configuration
│   └── SQL SCRIPTS.txt     # Database SQL scripts
│
├── MCWebApp ERD.drawio     # Entity Relationship Diagram
└── .gitignore
```

## Getting Started

### Prerequisites

Make sure you have installed:

- Java Development Kit (JDK)
- Apache NetBeans IDE
- GlassFish Server
- Apache Derby Database

### 1. Clone the Repository

```bash
git clone https://github.com/alya456nur/MCWebApp.git
```

Navigate into the project directory:

```bash
cd madeline-cosmetic-web-app
```

### 2. Set Up the Database

1. Start your Apache Derby Network Server.
2. Create or configure the `MCDB` database.
3. Open the `SQL SCRIPTS.txt` file.
4. Execute the database creation and sample data scripts.
5. Make sure the database connection settings match the configuration in `DBConnection.java`.

### 3. Configure Database Connection

The database connection is configured in:

```text
src/java/util/DBConnection.java
```

The current project configuration uses:

```java
private static final String URL =
    "jdbc:derby://localhost:1527/MCDB";

private static final String USER = "app";
private static final String PASSWORD = "app";
```

Update these values if your local database configuration is different.

### 4. Open the Project

1. Launch Apache NetBeans.
2. Select **File → Open Project**.
3. Navigate to the inner `MCWebApp` folder.
4. Open the project.
5. Configure your GlassFish Server if necessary.

### 5. Run the Application

1. Make sure the Derby database is running.
2. Make sure GlassFish Server is configured.
3. Right-click the project in NetBeans.
4. Select **Run**.
5. The application should open at its configured local URL.

## Database Design

The project uses a relational database with the following main entities:

- Customer
- Staff
- Product
- Variation
- Stock
- Orders
- Order Variation
- Receipt

The Entity Relationship Diagram (ERD) is included in the repository as `MCWebApp ERD.drawio`.

## Developer

Developed as an academic project.
