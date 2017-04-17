CREATE TABLE supermarket.customer
(
  cust_id       INT                 NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  cust_name     VARCHAR(200)        NULL,
  cust_balance  DECIMAL DEFAULT '0' NULL,
  cust_points   INT DEFAULT '0'     NULL,
  cust_username VARCHAR(50)         NULL,
  cust_password VARCHAR(256)        NULL,
  CONSTRAINT customer_cust_username_uindex
  UNIQUE (cust_username)
);

CREATE TABLE supermarket.customer_order
(
  order_id       INT                 NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  customer       INT                 NULL,
  order_date     MEDIUMTEXT          NULL,
  order_subtotal DECIMAL DEFAULT '0' NULL,
  order_total    DECIMAL DEFAULT '0' NULL,
  order_discount DECIMAL DEFAULT '0' NULL,
  order_status   VARCHAR(20)         NULL
);

COMMENT ON COLUMN customer_order.status IS 'pending
placed
canceled'
;

CREATE TABLE supermarket.discount
(
  disc_quantity   INT NOT NULL,
  product         INT NOT NULL,
  disc_percentage INT NULL,
  PRIMARY KEY (disc_quantity, product)
);

CREATE INDEX discount_product_id_fk
  ON discount (product);

CREATE TABLE supermarket.employee
(
  emp_id       INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  emp_name     VARCHAR(200) NULL,
  emp_password VARCHAR(256) NULL,
  emp_username VARCHAR(50)  NULL,
  emp_role     INT          NULL,
  CONSTRAINT employee_emp_username_uindex
  UNIQUE (emp_username)
);

CREATE TABLE supermarket.order_item
(
  cust_order    INT NULL,
  product       INT NULL,
  item_quantity INT NULL,
  item_total    INT NULL,
  CONSTRAINT order_item_order_id_fk
  FOREIGN KEY (cust_order) REFERENCES supermarket.customer_order (order_id)
);

CREATE INDEX order_item_order_id_fk
  ON order_item (cust_order);

CREATE TABLE supermarket.product
(
  prod_id              INT                      NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  prod_name            VARCHAR(50)              NULL,
  prod_unit_price      DECIMAL DEFAULT '0'      NULL,
  prod_stock_level     INT                      NULL,
  prod_replenish_level INT                      NULL,
  prod_type            VARCHAR(3) DEFAULT 'pcs' NULL,
  supplier             INT                      NULL
);

CREATE INDEX product_supplier_id_fk
  ON product (supplier);

COMMENT ON COLUMN product.type IS 'pcs
kg'
;

ALTER TABLE discount
  ADD CONSTRAINT discount_product_id_fk
FOREIGN KEY (product) REFERENCES supermarket.product (prod_id);

CREATE TABLE supermarket.purchase_order
(
  prd__id  INT        NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  prd_date MEDIUMTEXT NULL,
  employee INT        NULL,
  supplier INT        NULL,
  CONSTRAINT purchase_order_employee_id_fk
  FOREIGN KEY (employee) REFERENCES supermarket.employee (emp_id)
);

CREATE INDEX purchase_order_employee_id_fk
  ON purchase_order (employee);

CREATE INDEX purchase_order_supplier_id_fk
  ON purchase_order (supplier);

CREATE TABLE supermarket.purchase_order_item
(
  purchase_order INT                 NULL,
  product        INT                 NULL,
  item_quantity  INT                 NULL,
  item_total     DECIMAL DEFAULT '0' NULL
);

CREATE TABLE supermarket.supplier
(
  sup_id       INT          NOT NULL AUTO_INCREMENT
    PRIMARY KEY,
  sup_name     VARCHAR(100) NULL,
  sup_address  VARCHAR(200) NULL,
  sup_postcode INT          NULL,
  sup_phone    VARCHAR(15)  NULL
);

ALTER TABLE product
  ADD CONSTRAINT product_supplier_id_fk
FOREIGN KEY (supplier) REFERENCES supermarket.supplier (sup_id);

ALTER TABLE purchase_order
  ADD CONSTRAINT purchase_order_supplier_id_fk
FOREIGN KEY (supplier) REFERENCES supermarket.supplier (sup_id);

