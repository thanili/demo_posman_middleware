# Parent Maven Project

## Overview

This parent Maven project consists of two child modules: Source API and Destination Service. Together, these modules facilitate a seamless integration between a Point of Sale (POS) management system and external services, enabling efficient data exchange and processing.

## Modules

### Source API

The Source API module simulates a segment of a POS management system, focusing on managing customer and transaction data. It provides secure access for third-party services to retrieve comprehensive customer information and handles the periodic posting of receipt data to an external middleware service.

#### Key Features:

* Customer and transaction management.
* Secure API access with API Key and Access Token authentication.
* Endpoints for retrieving customer details and handling user authentication.

### Destination Service

The Destination Service module serves as middleware, receiving and temporarily storing receipt data from various sources (POS stations and webshops). It allows external services to access this data through defined endpoints, ensuring that the data is organized and easily retrievable.

#### Key Features:

* API endpoints for importing and exporting receipt data.
* Basic Authentication for secure data imports.
* Distinct repository structures for webshop and POS data, with separate databases for each source.

## Architecture

The project employs a microservices architecture, with each module responsible for specific functionalities related to customer and receipt data management. The Source API interacts with external services to retrieve customer information, while the Destination Service ensures secure and organized storage of receipt data.

## Getting Started

To get started with the project, ensure that you have the necessary database configurations set up as specified in the dest_service module's application.properties. Follow the individual module instructions for deployment and usage.

## Contributing

Contributions are welcome! Please refer to the individual module documentation for guidelines on contributing to each component.

## License

This project is licensed under the MIT License.