CREATE TABLE "DeliveryNote"
(
    "ServerName"        VARCHAR(128)  NOT NULL,
    "DatabaseName"      VARCHAR(128)  NOT NULL,
    "Date"              DATE          NOT NULL,
    "DocumentNo"        INT           NOT NULL,
    "CustomerCode"      VARCHAR(40),
    "SumGross"          DECIMAL(8, 2) NOT NULL,
    "SumNet"            DECIMAL(8, 2) NOT NULL,
    "ExternalReference" INT,
    "CustomerId"        VARCHAR(40),
    "SalesReceiptId"    VARCHAR(255),
    "DeviceNumber"      VARCHAR(40),
    "Year"              INT,
    "ShiftNumber"       VARCHAR(40),
    "ReceiptNumber"     INT,
    PRIMARY KEY ("ServerName", "DatabaseName", "Date", "DocumentNo")
);

CREATE TABLE "DeliveryNotePayments"
(
    "ServerName"       VARCHAR(128)  NOT NULL,
    "DatabaseName"     VARCHAR(128)  NOT NULL,
    "Date"             DATE          NOT NULL,
    "DocumentNo"       INT           NOT NULL,
    "PositionNo"       INT           NOT NULL,
    "PaymentMethodId"  INT           NOT NULL,
    "PaymentMethodSub" INT           NOT NULL,
    "CurrencyCode"     VARCHAR(10)   NOT NULL,
    "Amount"           DECIMAL(8, 2) NOT NULL,
    "TransactionNo"    VARCHAR(40),
    "VoucherNo"        VARCHAR(40),
    "SalesReceiptId"   VARCHAR(255),
    PRIMARY KEY ("ServerName", "DatabaseName", "Date", "DocumentNo", "PositionNo")
);

CREATE TABLE "DeliveryNotePositions"
(
    "ServerName"       VARCHAR(128)   NOT NULL,
    "DatabaseName"     VARCHAR(128)   NOT NULL,
    "Date"             DATE           NOT NULL,
    "DocumentNo"       INT            NOT NULL,
    "PositionNo"       INT            NOT NULL,
    "Type"             INT            NOT NULL,
    "VoucherNo"        VARCHAR(40),
    "ArticleCode"      VARCHAR(20)    NOT NULL,
    "ArticleGroupCode" VARCHAR(20)    NOT NULL,
    "Amount"           DECIMAL(20, 4) NOT NULL,
    "Price"            DECIMAL(8, 2)  NOT NULL,
    "VATCode"          VARCHAR(20)    NOT NULL,
    "VATRate"          DECIMAL(5, 2)  NOT NULL,
    "VATAmount"        DECIMAL(8, 2)  NOT NULL,
    "TicketNumber"     VARCHAR(40),
    "ArticleId"        VARCHAR(250),
    "UnitPrice"        DECIMAL(8, 2),
    "TotalDiscount"    DECIMAL(8, 2),
    "SalesReceiptId"   VARCHAR(255),
    "SalesReceiptDate" TIMESTAMP,
    PRIMARY KEY ("ServerName", "DatabaseName", "Date", "DocumentNo", "PositionNo")
);

CREATE TABLE "Customer"
(
    "Code"        VARCHAR(40) NOT NULL,
    "Company"     VARCHAR(40),
    "Salutation"  VARCHAR(40),
    "FirstName"   VARCHAR(40),
    "LastName"    VARCHAR(40),
    "Street"      TEXT,
    "CountryCode" VARCHAR(10),
    "ZipCode"     VARCHAR(10),
    "Location"    TEXT,
    "DateOfBirth" DATE,
    "EMail"       TEXT,
    PRIMARY KEY ("Code")
);