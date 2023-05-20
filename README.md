# ManagementApp

#Invoice Endpoints
1. **GET /invoices?{page}&{limit}&{sortBy}&{sortDir}**
   - Description: Retrieve a paginated list of invoices.
   - Parameters:
     - page: Page number (default value: AppConstants.DEFAULT_PAGE_NO)
     - limit: Number of items per page (default value: AppConstants.DEFAULT_PAGE_SIZE)
     - sortBy: Sort field (default value: AppConstants.DEFAULT_SORT_BY)
     - sortDir: Sort direction (default value: AppConstants.DEFAULT_SORT_DIR)
   - Returns: `InvoiceResponseList` containing the paginated list of invoices.

2. **GET /invoices/{id}**
   - Description: Retrieve an invoice by ID.
   - Parameters:
     - id: ID of the invoice to retrieve.
   - Returns: `InvoiceResponseModel` representing the requested invoice.

3. **POST /invoices**
   - Description: Create a new invoice.
   - Request Body: `InvoiceRequestModel` containing the invoice details.
   - Returns: `InvoiceResponseModel` representing the created invoice.

   **Functionality (Stock):** When creating a new invoice, the stock of products associated with the invoice will be updated accordingly. 
   The quantity of each product in stock will be reduced based on the quantity sold in the invoice.

4. **PUT /invoices/{id}**
   - Description: Update an existing invoice.
   - Parameters:
     - id: ID of the invoice to update.
   - Request Body: Updated `InvoiceDto` with modified details.
   - Returns: `InvoiceResponseModel` representing the updated invoice.

   **Functionality (Stock):** When updating an invoice, if the products' quantities are modified, the stock will be adjusted accordingly.
   If the quantity of a product is increased, the stock quantity will be decreased accordingly, and if the quantity is decreased, the stock quantity will be increased.

5. **DELETE /invoices/{id}**
   - Description: Delete an existing invoice.
   - Parameters:
     - id: ID of the invoice to delete.
   - Returns: `OperationStatusModel` indicating the status of the deletion operation.

   **Functionality (Stock):** When deleting an invoice, the stock quantities of the products associated with the invoice will be restored. 
   The quantities will be increased by the quantities of the products in the deleted invoice.

These request links allow you to manage invoices while also ensuring the stock quantities are properly adjusted based on the invoiced products.

#Products endpoints

**1. Retrieve all products**
- Endpoint: `GET /products`
- Description: Retrieves a paginated list of all products.
- Parameters:
  - `page` (optional): Page number (default value: `AppConstants.DEFAULT_PAGE_NO`).
  - `limit` (optional): Number of items per page (default value: `AppConstants.DEFAULT_PAGE_SIZE`).
  - `sortBy` (optional): Sort field (default value: `AppConstants.DEFAULT_SORT_BY`).
  - `sortDir` (optional): Sort direction (default value: `AppConstants.DEFAULT_SORT_DIR`).
- Returns: `ProductResponseList` containing the paginated list of products.

**2. Retrieve a product by ID**
- Endpoint: `GET /products/{id}`
- Description: Retrieves a product by its ID.
- Parameters:
  - `id`: ID of the product to retrieve.
- Returns: `ProductsDto` representing the requested product.

**3. Create a new product**
- Endpoint: `POST /products`
- Description: Creates a new product.
- Request Body: `ProductsDto` containing the product details.
- Returns: `ProductsDto` representing the created product.

**4. Update an existing product**
- Endpoint: `PUT /products/{id}`
- Description: Updates an existing product.
- Parameters:
  - `id`: ID of the product to update.
- Request Body: Updated `ProductsDto` with modified details.
- Returns: `ProductsDto` representing the updated product.

**5. Delete an existing product**
- Endpoint: `DELETE /products/{id}`
- Description: Deletes an existing product.
- Parameters:
  - `id`: ID of the product to delete.
- Returns: `OperationStatusModel` indicating the status of the deletion operation.

Please note that the above explanations follow the pattern established for the invoice explanation, 
providing a brief description of each operation, the corresponding endpoint, the parameters (if any), the request body (if applicable), and the response type.
