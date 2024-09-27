CREATE TABLE "Document"
(
    "CashRegisterId"   INT,
    "DatabaseName"     VARCHAR(255),
    "Date"             DATE,
    "DocumentNo"       INT,
    "ServerName"       VARCHAR(255),
    "CashierId"        VARCHAR(255),
    "CountryCode"      VARCHAR(255),
    "CustomerCode"     VARCHAR(255),
    "DocumentDateTime" TIMESTAMP,
    "SumGross"         DECIMAL(15, 2),
    "SumNet"           DECIMAL(15, 2),
    "TicketNo"         VARCHAR(255),
    "Type"             SMALLINT,
    "ZipCode"          VARCHAR(255),
    SalesReceiptId     VARCHAR(255),
    PRIMARY KEY ("CashRegisterId", "DatabaseName", "Date", "DocumentNo", "ServerName")
);

CREATE TABLE "DocumentPayments"
(
    "ServerName"             VARCHAR(128) NOT NULL,
    "DatabaseName"           VARCHAR(128) NOT NULL,
    "CashRegisterId"         INT          NOT NULL,
    "Date"                   DATE         NOT NULL,
    "DocumentNo"             INT          NOT NULL,
    "PositionNo"             INT          NOT NULL,
    "IsChange"               INT          NOT NULL,
    "PaymentMethodId"        INT          NOT NULL,
    "PaymentMethodSub"       INT          NOT NULL,
    "CurrencyCode"           VARCHAR(10)  NOT NULL,
    "Amount"                 DECIMAL(20, 2),
    "CardNo"                 VARCHAR(40),
    "VoucherNo"              VARCHAR(40),
    "Provider"               VARCHAR(20),
    "TransactionReferenceNo" VARCHAR(50),
    "SalesReceiptId"         VARCHAR(255),
    PRIMARY KEY ("ServerName", "DatabaseName", "CashRegisterId", "Date", "DocumentNo", "PositionNo")
);

CREATE TABLE "DocumentPositions"
(
    "ServerName"       VARCHAR(128)   NOT NULL,
    "DatabaseName"     VARCHAR(128)   NOT NULL,
    "CashRegisterId"   INT            NOT NULL,
    "Date"             DATE           NOT NULL,
    "DocumentNo"       INT            NOT NULL,
    "PositionNo"       INT            NOT NULL,
    "Type"             INT            NOT NULL,
    "VoucherNo"        VARCHAR(40),
    "ArticleCode"      VARCHAR(20)    NOT NULL,
    "ArticleGroupCode" VARCHAR(20)    NOT NULL,
    "Amount"           DECIMAL(20, 4) NOT NULL,
    "UnitPrice"        DECIMAL(20, 2),
    "Price"            DECIMAL(20, 2),
    "Discount"         DECIMAL(20, 2),
    "VATCode"          VARCHAR(20)    NOT NULL,
    "VATRate"          DECIMAL(5, 2)  NOT NULL,
    "VATAmount"        DECIMAL(20, 2),
    "Barcode"          VARCHAR(40),
    "ArticleName"      VARCHAR(255),
    "SalesReceiptId"   VARCHAR(255),
    PRIMARY KEY ("ServerName", "DatabaseName", "CashRegisterId", "Date", "DocumentNo", "PositionNo")
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