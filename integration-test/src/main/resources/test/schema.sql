DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  quantity INT NOT NULL,
  price INT DEFAULT NULL
);

DROP TABLE IF EXISTS providers;

CREATE TABLE providers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  phone VARCHAR(250) NOT NULL,
  street VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS products_providers;

CREATE TABLE products_providers (
  product_id INT NOT NULL,
  provider_id INT NOT NULL,
  PRIMARY KEY (product_id, provider_id),
  FOREIGN KEY (product_id) REFERENCES products (id),
  FOREIGN KEY (provider_id) REFERENCES providers (id)
);

DROP TABLE IF EXISTS sales;

CREATE TABLE sales (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  total DECIMAL(10,2) NOT NULL,
  sale_date DATE NOT NULL,
  state VARCHAR(250) NOT NULL,
  FOREIGN KEY (product_id) REFERENCES products (id)
);