-- insert for initializing the database with default users
insert into users (username, password_hash, roles, creation_time, created_by) values ('adas', '$2a$10$9uj1/JpNjlZyBO41pDxQ1exdp/STdLDyFRKA2AvZ9TP2RngHrwkA6', 'ROLE_ADMIN,ROLE_USER', current_timestamp, 'system');
insert into users (username, password_hash, roles, creation_time, created_by) values ('user', '$2a$10$ySnOf0BW0Ct93pt/MaQ2WeV.QcfGH1WOtlLaWn.IYkdSFr0B.iIfu', 'ROLE_USER', current_timestamp, 'system');
insert into users (username, password_hash, roles, creation_time, created_by) values ('newuser', '$2a$10$86fM56p47jB0.gSyVaQDKuwmwacvUJY0Z22l.WI.Q8/LDeffRppB.', 'ROLE_USER', current_timestamp, 'system');

-- insert for initializing the database with default items
insert into item (category, name, description, price, creation_time, created_by) values ('ALCOHOLIC', 'Gin', 'Juniperberry alcoholic drink', 11.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('MEAT', 'Chicken', 'Boneless poultry', 7.59, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE', 'Tonic', 'Carbonated herbal drink', 2.59, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('NON_ALCOHOLIC_ALTERNATIVES', 'Cordino', 'Mixer', 4.59, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('NON_ALCOHOLIC_ALTERNATIVES', 'Sanbitter', 'Bitter mixer', 7.29, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SPICES', 'Red curry paste', 'Spice mix', 3.59, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('GRAINS', 'Rice', 'Carobohyderate grains', 1.99, current_timestamp, 'system');
-- BEVERAGE
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE', 'Coca-Cola Classic', 'Original formula cola soft drink with natural flavors', 1.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE', 'Starbucks Pike Place Roast', 'Medium roast ground coffee with smooth, balanced flavor', 8.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE', 'Tropicana Pure Premium Orange Juice', 'Not from concentrate orange juice with pulp', 4.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE', 'Evian Natural Spring Water', 'Premium natural spring water from the French Alps', 2.29, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE', 'Red Bull Energy Drink', 'Caffeinated energy drink with taurine and B-vitamins', 2.79, current_timestamp, 'system');

-- VEGETABLES
insert into item (category, name, description, price, creation_time, created_by) values ('VEGETABLES', 'Organic Baby Spinach', 'Fresh tender baby spinach leaves, perfect for salads', 3.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('VEGETABLES', 'Roma Tomatoes', 'Firm, meaty tomatoes ideal for cooking and sauces', 2.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('VEGETABLES', 'Sweet Yellow Onions', 'Mild, sweet onions perfect for caramelizing', 1.89, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('VEGETABLES', 'Fresh Broccoli Crowns', 'Crisp green broccoli florets, high in vitamins', 2.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('VEGETABLES', 'Red Bell Peppers', 'Sweet, crunchy red peppers rich in vitamin C', 4.99, current_timestamp, 'system');

-- BAKERY
insert into item (category, name, description, price, creation_time, created_by) values ('BAKERY', 'Artisan Sourdough Bread', 'Handcrafted sourdough with crispy crust and tangy flavor', 4.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BAKERY', 'Chocolate Croissants', 'Buttery, flaky pastries filled with rich dark chocolate', 6.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BAKERY', 'Blueberry Muffins', 'Fresh baked muffins bursting with wild blueberries', 3.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BAKERY', 'Everything Bagels', 'New York style bagels topped with sesame, poppy, garlic blend', 2.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BAKERY', 'Red Velvet Cupcakes', 'Moist red velvet cupcakes with cream cheese frosting', 8.99, current_timestamp, 'system');

-- DAIRY
insert into item (category, name, description, price, creation_time, created_by) values ('DAIRY', 'Organic Whole Milk', 'Farm-fresh organic whole milk from grass-fed cows', 4.29, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('DAIRY', 'Sharp Cheddar Cheese', 'Aged sharp cheddar with bold, tangy flavor', 5.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('DAIRY', 'Greek Yogurt Vanilla', 'Thick, creamy Greek yogurt with natural vanilla flavor', 5.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('DAIRY', 'Farm Fresh Eggs', 'Grade A large eggs from free-range chickens', 3.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('DAIRY', 'Unsalted Butter', 'Premium unsalted butter perfect for baking', 4.79, current_timestamp, 'system');

-- FRUITS
insert into item (category, name, description, price, creation_time, created_by) values ('FRUITS', 'Honeycrisp Apples', 'Sweet, crisp apples with perfect crunch and flavor', 3.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('FRUITS', 'Organic Bananas', 'Naturally sweet organic bananas, perfect for snacking', 1.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('FRUITS', 'Fresh Strawberries', 'Juicy, sweet strawberries picked at peak ripeness', 4.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('FRUITS', 'Naval Oranges', 'Sweet, seedless oranges bursting with vitamin C', 2.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('FRUITS', 'Fresh Avocados', 'Creamy Hass avocados perfect for guacamole', 5.99, current_timestamp, 'system');

-- MEAT
insert into item (category, name, description, price, creation_time, created_by) values ('MEAT', 'Grass-Fed Ground Beef', 'Lean 85/15 ground beef from grass-fed cattle', 8.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('MEAT', 'Boneless Chicken Breast', 'Fresh, skinless chicken breast fillets', 6.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('MEAT', 'Applewood Smoked Bacon', 'Thick-cut bacon smoked over applewood', 7.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('MEAT', 'Ribeye Steaks', 'Premium marbled ribeye steaks, perfect for grilling', 24.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('MEAT', 'Italian Sausage Links', 'Seasoned pork sausage with traditional Italian herbs', 5.99, current_timestamp, 'system');

-- SEAFOOD
insert into item (category, name, description, price, creation_time, created_by) values ('SEAFOOD', 'Atlantic Salmon Fillets', 'Fresh wild-caught salmon fillets rich in omega-3', 14.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SEAFOOD', 'Large Shrimp', 'Jumbo shrimp, peeled and deveined, ready to cook', 12.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SEAFOOD', 'Fresh Cod Fillets', 'Mild, flaky white fish perfect for baking', 11.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SEAFOOD', 'Sea Scallops', 'Large, sweet sea scallops perfect for searing', 18.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SEAFOOD', 'Fresh Mussels', 'Live blue mussels in shell, perfect for steaming', 6.99, current_timestamp, 'system');

-- SNACKS
insert into item (category, name, description, price, creation_time, created_by) values ('SNACKS', 'Lay''s Classic Potato Chips', 'Crispy potato chips with just the right amount of salt', 2.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SNACKS', 'Mixed Nuts Trail Mix', 'Premium mix of almonds, cashews, and dried cranberries', 4.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SNACKS', 'Oreo Cookies', 'Classic chocolate sandwich cookies with cream filling', 3.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SNACKS', 'Cheez-Its Crackers', 'Baked cheese crackers with real cheddar', 3.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SNACKS', 'Pretzel Rods', 'Crunchy salted pretzel rods perfect for dipping', 2.79, current_timestamp, 'system');

-- CONFECTIONERY
insert into item (category, name, description, price, creation_time, created_by) values ('CONFECTIONERY', 'Hershey''s Milk Chocolate Bar', 'Classic milk chocolate bar made with farm fresh milk', 1.89, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('CONFECTIONERY', 'Haribo Gummy Bears', 'Fruity gummy bears in assorted flavors', 2.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('CONFECTIONERY', 'Lindt Dark Chocolate Truffles', 'Premium dark chocolate truffles with smooth ganache', 8.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('CONFECTIONERY', 'Sour Patch Kids', 'Sour then sweet chewy candy in fun shapes', 2.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('CONFECTIONERY', 'Godiva Chocolate Assortment', 'Luxury chocolate collection with various flavors', 24.99, current_timestamp, 'system');

-- FROZEN
insert into item (category, name, description, price, creation_time, created_by) values ('FROZEN', 'Ben & Jerry''s Chunky Monkey', 'Banana ice cream with fudge chunks and walnuts', 5.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('FROZEN', 'Frozen Broccoli Florets', 'Flash-frozen broccoli florets, ready to steam', 2.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('FROZEN', 'DiGiorno Supreme Pizza', 'Rising crust pizza with pepperoni, sausage, and vegetables', 6.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('FROZEN', 'Frozen Blueberries', 'Wild frozen blueberries perfect for smoothies', 4.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('FROZEN', 'Lean Cuisine Chicken Alfredo', 'Healthy frozen meal with grilled chicken and pasta', 4.49, current_timestamp, 'system');

-- CANNED
insert into item (category, name, description, price, creation_time, created_by) values ('CANNED', 'Hunt''s Diced Tomatoes', 'Vine-ripened tomatoes diced and canned at peak freshness', 1.29, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('CANNED', 'Campbell''s Chicken Noodle Soup', 'Classic comfort soup with tender chicken and noodles', 1.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('CANNED', 'StarKist Albacore Tuna', 'Premium white albacore tuna in water', 2.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('CANNED', 'Green Giant Green Beans', 'Tender cut green beans preserved at peak freshness', 1.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('CANNED', 'Libby''s Pumpkin Puree', 'Pure pumpkin puree perfect for pies and baking', 1.99, current_timestamp, 'system');

-- GRAINS
insert into item (category, name, description, price, creation_time, created_by) values ('GRAINS', 'Jasmine White Rice', 'Fragrant long-grain jasmine rice with delicate flavor', 3.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('GRAINS', 'Quaker Old-Fashioned Oats', 'Whole grain rolled oats perfect for breakfast', 4.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('GRAINS', 'Barilla Whole Wheat Pasta', 'Nutritious whole wheat penne pasta', 2.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('GRAINS', 'Bob''s Red Mill Quinoa', 'Organic tri-color quinoa, complete protein grain', 6.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('GRAINS', 'Uncle Ben''s Brown Rice', 'Nutritious whole grain brown rice', 2.79, current_timestamp, 'system');

-- SPICES
insert into item (category, name, description, price, creation_time, created_by) values ('SPICES', 'McCormick Ground Cinnamon', 'Pure ground cinnamon with warm, sweet flavor', 3.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SPICES', 'Simply Organic Oregano', 'Certified organic dried oregano leaves', 4.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SPICES', 'Old Bay Seasoning', 'Classic Maryland seafood seasoning blend', 3.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SPICES', 'Himalayan Pink Salt', 'Pure pink salt crystals from ancient sea beds', 5.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SPICES', 'Cumin Ground', 'Aromatic ground cumin seeds with earthy flavor', 2.99, current_timestamp, 'system');

-- SAUCES
insert into item (category, name, description, price, creation_time, created_by) values ('SAUCES', 'Heinz Ketchup', 'Classic tomato ketchup made from vine-ripened tomatoes', 2.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SAUCES', 'Rao''s Marinara Sauce', 'Premium marinara sauce with Italian tomatoes', 6.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SAUCES', 'Frank''s RedHot Sauce', 'Original cayenne pepper hot sauce', 2.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SAUCES', 'Hellmann''s Real Mayonnaise', 'Rich and creamy mayonnaise made with real eggs', 4.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SAUCES', 'Lee Kum Kee Soy Sauce', 'Premium soy sauce aged for authentic umami flavor', 3.99, current_timestamp, 'system');

-- DAIRY_ALTERNATIVES
insert into item (category, name, description, price, creation_time, created_by) values ('DAIRY_ALTERNATIVES', 'Oat Dream Oat Milk', 'Creamy oat milk perfect for coffee and cereal', 4.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('DAIRY_ALTERNATIVES', 'Miyoko''s Cashew Cream Cheese', 'Plant-based cream cheese made from cashews', 5.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('DAIRY_ALTERNATIVES', 'So Delicious Coconut Yogurt', 'Probiotic coconut yogurt with live cultures', 4.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('DAIRY_ALTERNATIVES', 'Earth Balance Buttery Spread', 'Plant-based butter alternative for spreading', 4.79, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('DAIRY_ALTERNATIVES', 'Silk Almond Milk Unsweetened', 'Smooth almond milk with no added sugar', 3.49, current_timestamp, 'system');

-- PLANT_BASED
insert into item (category, name, description, price, creation_time, created_by) values ('PLANT_BASED', 'Beyond Meat Burgers', 'Plant-based burger patties that look, cook, and taste like beef', 8.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('PLANT_BASED', 'Gardein Chicken Nuggets', 'Crispy plant-based chicken nuggets', 5.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('PLANT_BASED', 'Lightlife Tempeh', 'Fermented soy protein with nutty flavor', 4.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('PLANT_BASED', 'Field Roast Sausages', 'Plant-based sausages with traditional spices', 6.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('PLANT_BASED', 'Tofurky Deli Slices', 'Plant-based deli meat slices for sandwiches', 4.99, current_timestamp, 'system');

-- ORGANIC
insert into item (category, name, description, price, creation_time, created_by) values ('ORGANIC', 'Organic Kale Bunch', 'Fresh organic kale leaves, superfood green', 3.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('ORGANIC', 'Organic Free-Range Eggs', 'Certified organic eggs from pasture-raised hens', 6.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('ORGANIC', 'Annie''s Organic Mac & Cheese', 'Organic macaroni and cheese with real cheddar', 3.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('ORGANIC', 'Organic Blueberries', 'Sweet organic blueberries packed with antioxidants', 5.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('ORGANIC', 'Earthbound Organic Spring Mix', 'Tender organic baby greens and herbs', 4.49, current_timestamp, 'system');

-- GLUTEN_FREE
insert into item (category, name, description, price, creation_time, created_by) values ('GLUTEN_FREE', 'King Arthur Gluten-Free Flour', 'All-purpose gluten-free flour blend for baking', 6.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('GLUTEN_FREE', 'Jovial Brown Rice Pasta', 'Gluten-free brown rice penne pasta', 4.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('GLUTEN_FREE', 'Canyon Bakehouse Bread', 'Soft gluten-free whole grain bread', 5.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('GLUTEN_FREE', 'Mary''s Gone Crackers', 'Organic gluten-free seed crackers', 4.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('GLUTEN_FREE', 'Pamela''s Baking Mix', 'Gluten-free pancake and baking mix', 5.49, current_timestamp, 'system');

-- BEVERAGE_ALTERNATIVES
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE_ALTERNATIVES', 'Kombucha GT''s Gingerade', 'Organic fermented kombucha with ginger', 3.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE_ALTERNATIVES', 'Bulletproof MCT Oil', 'Pure MCT oil derived from coconuts', 24.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE_ALTERNATIVES', 'Zevia Cola Zero Calorie', 'Natural cola with stevia sweetener', 4.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE_ALTERNATIVES', 'Celsius Energy Drink', 'Fitness drink with natural caffeine and vitamins', 2.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('BEVERAGE_ALTERNATIVES', 'Cold-Pressed Green Juice', 'Fresh vegetable juice with kale, celery, and apple', 6.99, current_timestamp, 'system');

-- HEALTH_SUPPLEMENTS
insert into item (category, name, description, price, creation_time, created_by) values ('HEALTH_SUPPLEMENTS', 'Nature''s Way Vitamin D3', 'High-potency vitamin D3 supplement for bone health', 12.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('HEALTH_SUPPLEMENTS', 'Garden of Life Probiotics', 'Multi-strain probiotic supplement for digestive health', 24.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('HEALTH_SUPPLEMENTS', 'Nordic Naturals Fish Oil', 'Pure omega-3 fish oil supplement', 29.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('HEALTH_SUPPLEMENTS', 'Emergen-C Vitamin C', 'Immune support vitamin C powder packets', 8.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('HEALTH_SUPPLEMENTS', 'Magnesium Glycinate Capsules', 'Highly absorbable magnesium for muscle relaxation', 16.99, current_timestamp, 'system');

-- INTERNATIONAL
insert into item (category, name, description, price, creation_time, created_by) values ('INTERNATIONAL', 'Kimchi Traditional Korean', 'Fermented cabbage with authentic Korean spices', 5.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('INTERNATIONAL', 'Prosciutto di Parma', 'Authentic Italian dry-cured ham', 18.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('INTERNATIONAL', 'Panko Breadcrumbs', 'Japanese-style light and crispy breadcrumbs', 3.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('INTERNATIONAL', 'Tahini Sesame Paste', 'Middle Eastern sesame seed paste', 6.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('INTERNATIONAL', 'Garam Masala Spice Blend', 'Traditional Indian warming spice mixture', 4.99, current_timestamp, 'system');

-- SEASONAL
insert into item (category, name, description, price, creation_time, created_by) values ('SEASONAL', 'Pumpkin Spice Coffee', 'Limited edition fall-flavored ground coffee', 7.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SEASONAL', 'Fresh Cranberries', 'Tart seasonal cranberries perfect for holiday cooking', 3.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SEASONAL', 'Candy Canes Peppermint', 'Traditional holiday peppermint candy canes', 2.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SEASONAL', 'Eggnog Classic', 'Rich and creamy holiday eggnog beverage', 4.49, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SEASONAL', 'Hot Chocolate Bombs', 'Chocolate spheres that melt into hot cocoa', 8.99, current_timestamp, 'system');

-- SPECIALTY
insert into item (category, name, description, price, creation_time, created_by) values ('SPECIALTY', 'Truffle Oil Extra Virgin', 'Premium olive oil infused with black truffle', 24.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SPECIALTY', 'Saffron Threads Spanish', 'Premium saffron spice threads from Spain', 19.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SPECIALTY', 'Wagyu Beef Jerky', 'Artisanal jerky made from premium wagyu beef', 29.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SPECIALTY', 'Manuka Honey Raw', 'Pure New Zealand manuka honey with healing properties', 34.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('SPECIALTY', 'Aged Balsamic Vinegar', '25-year aged balsamic vinegar from Modena', 39.99, current_timestamp, 'system');

-- LOCAL
insert into item (category, name, description, price, creation_time, created_by) values ('LOCAL', 'Mountain View Farm Honey', 'Raw wildflower honey from local beekeepers', 8.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('LOCAL', 'Artisan Goat Cheese', 'Creamy goat cheese from nearby dairy farm', 12.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('LOCAL', 'Heritage Tomatoes', 'Heirloom tomatoes grown by local farmers', 6.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('LOCAL', 'Fresh Apple Cider', 'Pressed apple cider from regional orchards', 5.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('LOCAL', 'Sourdough Starter Culture', 'Living sourdough culture from local bakery', 9.99, current_timestamp, 'system');

-- ALCOHOLIC
insert into item (category, name, description, price, creation_time, created_by) values ('ALCOHOLIC', 'Cabernet Sauvignon Wine', 'Full-bodied red wine with rich berry flavors', 15.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('ALCOHOLIC', 'Craft IPA Beer', 'Hoppy India Pale Ale with citrus notes', 9.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('ALCOHOLIC', 'Premium Vodka', 'Ultra-smooth premium vodka distilled five times', 28.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('ALCOHOLIC', 'Single Malt Whiskey', 'Aged single malt with smoky oak finish', 45.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('ALCOHOLIC', 'Tequila Blanco', 'Pure agave tequila with clean, crisp flavor', 32.99, current_timestamp, 'system');

-- NON_ALCOHOLIC_ALTERNATIVES
insert into item (category, name, description, price, creation_time, created_by) values ('NON_ALCOHOLIC_ALTERNATIVES', 'Seedlip Garden 108', 'Non-alcoholic herbal spirit with pea, hay, and spearmint', 29.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('NON_ALCOHOLIC_ALTERNATIVES', 'Athletic Brewing IPA', 'Non-alcoholic craft IPA with full hop flavor', 8.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('NON_ALCOHOLIC_ALTERNATIVES', 'Ritual Zero Proof Whiskey', 'Non-alcoholic whiskey alternative for cocktails', 24.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('NON_ALCOHOLIC_ALTERNATIVES', 'Fre Chardonnay Wine', 'Alcohol-removed white wine with crisp fruit flavors', 6.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values ('NON_ALCOHOLIC_ALTERNATIVES', 'Mocktail Mixer Elderflower', 'Premium mixer for alcohol-free cocktails', 7.99, current_timestamp, 'system');