# E-commerce System 

## Overview
This e-commerce platform provides a comprehensive set of services to manage online shopping operations. It supports customer management, product catalog management, cart and wishlist functionalities, order processing, payment handling, and more. The platform ensures a seamless shopping experience and efficient management of e-commerce activities.

## Technologies Used
- **Spring Boot**: Framework for creating stand-alone, production-grade Spring-based applications.
- **Spring Security**: For securing the application.
- **JWT (JSON Web Tokens)**: For handling authentication tokens.
- **Postgresql**: Relational database for storing persistent data.
- **Lombok**: To reduce boilerplate code for model objects.
- **Stripe**: Payment gateway integration for processing payments securely.

## Features

### Auth Service
The Auth Service handles user registration, login, password changing,logout, and token refreshing.

- **Register:** Creates a new user account if the email is not already registered.
- **Login:** Authenticates a user and provides an access token and refresh token.
- **Logout:** Invalidates the access and refresh tokens by blacklisting them.
- **Change Password:** Allows users to change their password after verifying the current password.
- **Reset Password**: Allows users to set a new password after verifying the reset token.
- **Refresh Token:** Generates a new access token using a valid refresh token.

### Category Service
The Category Service manages product categories.

- **Create Category:** Adds a new product category to the platform.
- **Update Category:** Updates the details of an existing product category.
- **Delete Category:** Removes a product category from the platform.
- **Get Category by ID:** Retrieves the details of a specific product category.
- **Get All Categories:** Retrieves all product categories available on the platform.

### Product Service
The Product Service handles product catalog management.

- **Add Product:** Adds a new product to the catalog with details such as name, price, description, and category.
- **Update Product:** Updates product details including price, description, and category.
- **Delete Product:** Removes a product from the catalog.

### Cart Service
The Cart Service manages the shopping cart functionalities.

- **Add Product to Cart:** Adds a product to the customer's shopping cart with specified quantity.
- **Update Cart Item:** Updates the quantity of a product in the shopping cart.
- **Delete Cart Item:** Removes a product from the shopping cart.
- **Get Cart by ID:** Retrieves the details of a customer's shopping cart.

### Order Service
The Order Service handles order processing and management.

- **Create Order:** Processes a customer's cart to create a new order with order items and calculates the total price.
- **Get Order by ID:** Retrieves the details of a specific order.
- **Get All Orders:** Retrieves all orders placed on the platform.

### Payment Service
The Payment Service manages payment processing using Stripe.

- **Process Payment:** Initiates a payment for an order using Stripe integration.
  
### Wishlist Service
The Wishlist Service manages customer wishlists for favorite products.

- **Add Product to Wishlist:** Adds a product to a customer's wishlist.
- **Remove Product from Wishlist:** Removes a product from a customer's wishlist.
- **Get Wishlist by Customer ID:** Retrieves the wishlist of a specific customer.

### Review Service
The Review Service handles customer reviews for products.

- **Add Review:** Allows customers to add a review for a product.
- **Get Review by ID:** Retrieves the details of a specific review.
- **Get Reviews by Product:** Retrieves all reviews for a specific product.



### Coupon Service
The Coupon Service manages discount coupons for customer orders.

- **Create Coupon:** Generates a new discount coupon with a specified code, discount percentage, and expiry date.
- **Delete Coupon:** Removes a discount coupon from the platform.
- **Get Discount for Coupon:** Retrieves the discount percentage for a valid coupon code.

### Email Service
The Email Service sends notification emails to customers.

- **Send Email:** Sends an email to a specified recipient with a subject and message content.

## Setup
1. Clone the repository.
2. Configure your database credentials in `enviroment in docker-compose.yml`.
3. Set up Maven:
   ```bash
   mvn clean install
4. Set up Docker and run Docker Compose:
   ```bash
   docker-compose up -d

## Usage
Use the provided endpoints to manage products, categories, carts, orders, payments, reviews, and wishlists through a RESTful API.

