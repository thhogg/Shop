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
(9,'Short sleeve crew neck T-shirt', 1455000, 
'A short sleeve crew neck T-shirt features a round, close-fitting neckline and short sleeves ending around the mid-upper arm. 
Made from breathable materials like cotton, it is comfortable and versatile for casual or athletic wear. 
Its simple design makes it easy to pair with various outfits.', 
GETDATE());
GO

INSERT INTO [dbo].[ProductColor]
           ([ProductID]
           ,[ColorID])
VALUES
(1,1),  --black
(1,2),	--while
(1,3),	--red
(1,4);	--grey

GO

INSERT INTO [dbo].[ProductImage]
           ([ProductColorID]
           ,[ImageUrl]
           ,[Main])
VALUES
--black image
(1,'images/BTSW_1.jpg',1),
(1,'images/BTSW_2.jpg',0),
(1,'images/BTSW_3.jpg',0),
(1,'images/BTSW_4.jpg',0),
(1,'images/BTSW_5.jpg',0),

--while image
(2,'images/WTSW_1.jpg',1),
(2,'images/WTSW_2.jpg',0),
(2,'images/WTSW_3.jpg',0),
(2,'images/WTSW_4.jpg',0),
(2,'images/WTSW_5.jpg',0),

--red image
(3,'images/RTSW_1.jpg',1),
(3,'images/RTSW_2.jpg',0),
(3,'images/RTSW_3.jpg',0),
(3,'images/RTSW_4.jpg',0),
(3,'images/RTSW_5.jpg',0),

--grey image
(4,'images/GTSW_1.jpg',1),
(4,'images/GTSW_2.jpg',0),
(4,'images/GTSW_3.jpg',0),
(4,'images/GTSW_4.jpg',0),
(4,'images/GTSW_5.jpg',0);
GO


INSERT INTO [dbo].[ProductVariant]
           ([ProductColorID]
           ,[SizeID]
           ,[Quantity])
VALUES

--black image
(1,1,200), --size S
(1,2,215), --size M
(1,3,185), --size L
(1,4,300), --size XL
(1,5,150), --size XXL

--while image
(2,1,132), 
(2,2,222), 
(2,3,170), 
(2,4,223), 
(2,5,178), 

--red image
(3,1,150), 
(3,2,200), 
(3,3,230), 
(3,4,250), 
(3,5,160), 

--grey image
(4,1,175), 
(4,2,185), 
(4,3,196), 
(4,4,258), 
(4,5,175);
GO


