# GameStore App

A JavaFX-based application that simulates an online game store, offering both **admin** and **user** functionalities. This project is built as part of a class project, developed in milestones, progressively adding features from handling inventory to remote administration.

## Features
- **Admin Functions:**
  - Manage inventory (view, edit, delete products).
  - Search products and update quantities.
- **Customer Functions:**
  - Browse products, add/remove from shopping cart.
  - Update cart quantities and checkout.
- **Data Persistence:**
  - Save and load store data to/from JSON files.
- **Remote Admin App:**
  - Remotely manage the store via network communication.
- **Sorting:**
  - Sort products and cart by name, date, or price.
- **Unit Testing:**
  - Verify core functions of the store.

---

## Milestones Overview

### ✔️ Milestone 1: Initial Class Design
- Designed main classes for the app.
- Implemented navigation menus.
- Handles user input and validation.
  Note: Experimented with a Console app version before transitioning to JavaFX

**[View this Iteration here](https://github.com/Andrew-Forster/gamestore/releases/tag/v1.0.0)**

### ✔️ Milestone 2: Admin Functions
- Implemented admin functions for managing the inventory:
- Display, edit, delete products.
- Search for products and update quantities.
 
**[View this Iteration here](https://github.com/Andrew-Forster/gamestore/releases/tag/v2.0.0)**

### ✔️ Milestone 3: Customer Functions
- Implemented customer shopping functions:
- Display inventory and search for products.
- Add/remove items from the shopping cart.
- Update quantities in the cart and perform checkout.
    
**[View this Iteration here](https://github.com/Andrew-Forster/gamestore/releases/tag/v3.0.0)**

### Milestone 4: Save and Load Data using a Text File
- Save store data to a text file in **JSON format**.
- Use **Maven** to incorporate the **Jackson library** for data serialization.

### Milestone 5: Sorting Data
- Add sorting features to inventory and cart:
- Sort by name, date, and price.

### Milestone 6: Remote Admin App
- Develop a remote admin app using network communication:
- Client app handles admin functions and communicates with a server.
- CRUD operations are performed on the server and commands are sent using **JSON**.
- Network commands run on background threads to avoid UI delays.

### Milestone 7: Unit Testing
- Implement unit tests to verify the functionality of inventory management and shopping cart methods.

---

## Requirements
- **JavaFX** (GUI framework)
- **Maven** (dependency management)
- **Jackson** (for JSON serialization)
- **JUnit** (for unit testing)

## Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
