# Destination Service (Middleware)

## Overview

The **Destination Service** acts as **middleware** between a POS management system and external services, receiving and temporarily storing receipt data in a dedicated database. This service facilitates the transfer of receipt data from the POS management system to third-party services.

## API Endpoints

* **ImportDataController**: Exposes the `/api/import/receipt` endpoint for receiving receipt data via POST requests. Based on the receipt's source, either from a webshop or a POS station, it calls the appropriate service method to process and store the data.

* **ExportDataController**: Provides the following endpoints for external services to fetch stored receipt data:

  * `/api/export/receipt/pos`: Fetch receipts from POS stations.

  * `/api/export/receipt/webshop`: Fetch receipts from the webshop.
  
## Security 

### SecurityConfiguration:

* **ImportDataController** endpoints are secured with Basic Authentication to ensure that only authorized services can post receipt data. 

* **ExportDataController** endpoints are open to external services without authentication for easy access to receipt data.

## Data Sources

Receipt data can originate from two distinct sources: POS stations and the Webshop. To ensure data integrity and separation, each source’s data is stored in a different database, with distinct entity classes and repository structures for each:

### Entities 

#### Webshop Entities

* DeliveryNote (with DeliveryNoteId)
* DeliveryNotePositions (with DeliveryNotePositionId)
* DeliveryNotePayments (with DeliveryNotePaymentId)

#### POS Entities

* Document (with DocumentId)
* DocumentPositions (with DocumentPositionId)
* DocumentPayments (with DocumentPaymentId)

#### Common Entity

* CustomerLocal: A shared entity that stores customer information for both POS and webshop transactions.

### Repository Structure

#### Webshop Repositories

* DeliveryNoteRepository
* DeliveryNotePositionsRepository
* DeliveryNotePaymentsRepository
* CustomerWebshopRepository

#### POS Repositories

* DocumentRepository
* DocumentPositionsRepository
* DocumentPaymentsRepository
* CustomerPosRepository

### Data Source Configurations

Two separate data source configurations ensure that receipt data is stored in the correct database based on its origin:

* **PosDataSourceConfiguration** (Primary): Handles data from POS stations, storing it in the destination_database_02.
* **WebshopDataSourceConfiguration**: Handles data from the webshop, storing it in the destination_database_01.

#### Configuration in application.properties

```properties
#Webshop database configuration
webshop.datasource.url=jdbc:mariadb://localhost:3306/destination_database_01
webshop.datasource.username=test
webshop.datasource.password=test
webshop.datasource.driverClassName=org.mariadb.jdbc.Driver

#POS stations database configuration
pos.datasource.url=jdbc:mariadb://localhost:3306/destination_database_02
pos.datasource.username=test
pos.datasource.password=test
pos.datasource.driverClassName=org.mariadb.jdbc.Driver
```

This setup ensures that data from each receipt source is consistently routed to the correct database.

## Main Data Service

**DataTransformationService**: Responsible for processing receipt data based on its source. It transforms the incoming data from webshop or POS stations into the corresponding entity models and stores it in the respective database.

Additionally, for each receipt, this service interacts with the POS management system to fetch detailed customer information and stores the enriched customer data in the appropriate database.