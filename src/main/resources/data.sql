-- Catégories
INSERT INTO categories (name, description) VALUES
('Smartphones', 'Téléphones mobiles'),
('Laptops', 'Ordinateurs portables'),
('Audio', 'Écouteurs et enceintes');

-- Produits
INSERT INTO products (name, price, description, category_id, category_name, created_at) VALUES
('iPhone 16 Pro', 1229.00, 'Smartphone Apple 256 Go Titanium', 1, 'Smartphones', CURRENT_TIMESTAMP),
('Samsung Galaxy S25', 1099.00, 'Flagship Android 512 Go', 1, 'Smartphones', CURRENT_TIMESTAMP),
('MacBook Pro 14', 2199.00, 'M4 Pro 18 Go / 512 Go', 2, 'Laptops', CURRENT_TIMESTAMP),
('Dell XPS 13', 1499.00, 'Intel Ultra 7 16 Go / 1 To', 2, 'Laptops', CURRENT_TIMESTAMP),
('AirPods Pro 2', 279.00, 'ANC USB-C', 3, 'Audio', CURRENT_TIMESTAMP);
