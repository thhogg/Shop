USE [FashionShop]
GO

INSERT INTO [dbo].[Category]
           ([CategoryName]
           ,[ParentID])
VALUES

/*
1: Women
2: Men
3: Kids
4: Accessories
5: Cosmetic
*/

-- Level 1
('Women',0),  
('Men',0),    
('Kids',0),
('Accessories',0),
('Cosmetic',0),

--Women
('Dress',1),
('Blouse',1),
('Skirt',1),
('T-shirt',1),
('Heels',1),

--Men
('T-shirt',2),
('Shirt',2),
('Jeans',2),
('Jacket',2),
('Sneakers',2),

--Kids
('T-shirt',3),
('Hoodie',3),
('Shorts',3),
('Dress',3),
('Sneakers',3),

--Accessories
('Watch',4),
('Belt',4),
('Sunglasses',4),
('Hat',4),
('Backpack',4),

--Cosmetics
('Lipstick',5),
('Foundation',5),
('Perfume',5),
('Moisturizer',5),
('Body Lotion',5);
GO


