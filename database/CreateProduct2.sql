USE [FashionShop]
GO

INSERT INTO [dbo].[Product]
           ([CategoryID]
           ,[ProductName]
           ,[Price]
           ,[Description]
           ,[CreatedAt])
VALUES
--9: T-shirt (Women)
(9,'Short sleeve print T-shirt', 450000, 
'This is a clean, simple image of a white, short-sleeved t-shirt with a printed graphic. 
The graphic on the chest features the words "CAMDEN DISTRICT" in a clean sans-serif font above the script text "Trust Your Vibes" in black. 
The t-shirt, which appears to be 100% cotton, is displayed flat against a plain light grey or white background.', 
GETDATE());
GO

INSERT INTO [dbo].[ProductColor]
           ([ProductID]
           ,[ColorID])
VALUES
(2,2),  --while
(2,3),	--red
(2,9);	--brown

GO

INSERT INTO [dbo].[ProductImage]
           ([ProductColorID]
           ,[ImageUrl]
           ,[Main])
VALUES
--while image
(5,'images/WCDTS_1.jpg',1),
(5,'images/WCDTS_2.jpg',0),

--red image
(6,'images/RCDTS_1.jpg',1),
(6,'images/RCDTS_2.jpg',0),

--brown image
(7,'images/BCDTS_1.jpg',1),
(7,'images/BCDTS_2.jpg',0);
GO


INSERT INTO [dbo].[ProductVariant]
           ([ProductColorID]
           ,[SizeID]
           ,[Quantity])
VALUES

--while image
(5,1,175), --size S
(5,2,180), --size M
(5,3,185), --size L
(5,4,190), --size XL
(5,5,200), --size XXL

--red image
(6,3,100), 
(6,4,120), 
(6,5,150), 

--brown image
(7,1,110), 
(7,2,120), 
(7,4,130); 

GO


