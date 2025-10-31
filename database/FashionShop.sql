----------------------------------------------------------
-- TẠO DATABASE
----------------------------------------------------------
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'FashionShop')
BEGIN
    CREATE DATABASE FashionShop;
END;
GO

USE FashionShop;
GO

----------------------------------------------------------
-- BẢNG NGƯỜI DÙNG
----------------------------------------------------------
CREATE TABLE [Users] (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    UserName NVARCHAR(50) NOT NULL UNIQUE,
    [Password] NVARCHAR(255) NOT NULL,
    FullName NVARCHAR(100),
    Email NVARCHAR(100) NOT NULL,
    Phone NVARCHAR(20),
    [Address] NVARCHAR(255),
    [Role] INT NOT NULL DEFAULT 1, -- 0 = admin, 1 = customer
    CreatedAt DATETIME DEFAULT GETDATE()
);
GO

----------------------------------------------------------
-- BẢNG DANH MỤC (CÓ PHÂN CẤP)
----------------------------------------------------------
CREATE TABLE Category (
    CategoryID INT IDENTITY(1,1) PRIMARY KEY,
    CategoryName NVARCHAR(100) NOT NULL,
    ParentID INT NOT NULL
);
GO

----------------------------------------------------------
-- BẢNG SẢN PHẨM
----------------------------------------------------------
CREATE TABLE Product (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    CategoryID INT,
    ProductName NVARCHAR(100) NOT NULL,
    Price INT NOT NULL,
    [Description] NVARCHAR(MAX),
    CreatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID)
);
GO

----------------------------------------------------------
-- BẢNG MÀU SẮC
----------------------------------------------------------
CREATE TABLE Color (
    ColorID INT IDENTITY(1,1) PRIMARY KEY,
    ColorName NVARCHAR(50) NOT NULL,
	HexCode char(7)
);
GO

----------------------------------------------------------
-- BẢNG KÍCH CỠ
----------------------------------------------------------
CREATE TABLE Size (
    SizeID INT IDENTITY(1,1) PRIMARY KEY,
    SizeName NVARCHAR(20) NOT NULL
);
GO

----------------------------------------------------------
-- BẢNG LIÊN KẾT SẢN PHẨM - MÀU
----------------------------------------------------------
CREATE TABLE ProductColor (
    ProductColorID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT NOT NULL,
    ColorID INT NOT NULL,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID),
    FOREIGN KEY (ColorID) REFERENCES Color(ColorID)
);
GO

----------------------------------------------------------
-- BẢNG ẢNH SẢN PHẨM
----------------------------------------------------------
CREATE TABLE ProductImage (
    ImageID INT IDENTITY(1,1) PRIMARY KEY,
    ProductColorID INT NOT NULL,
    ImageUrl NVARCHAR(255) NOT NULL,
    Main BIT DEFAULT 0,
    FOREIGN KEY (ProductColorID) REFERENCES ProductColor(ProductColorID)
);
GO

----------------------------------------------------------
-- BẢNG BIẾN THỂ (MÀU + SIZE)
----------------------------------------------------------
CREATE TABLE ProductVariant (
    ProductVariantID INT IDENTITY(1,1) PRIMARY KEY,
    ProductColorID INT NOT NULL,
    SizeID INT NOT NULL,
    Quantity INT,
    FOREIGN KEY (ProductColorID) REFERENCES ProductColor(ProductColorID),
    FOREIGN KEY (SizeID) REFERENCES Size(SizeID)
);
GO

----------------------------------------------------------
-- BẢNG GIỎ HÀNG
----------------------------------------------------------
CREATE TABLE Cart (
    CartID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (UserID) REFERENCES [Users](UserID)
);
GO

----------------------------------------------------------
-- BẢNG CHI TIẾT GIỎ HÀNG
----------------------------------------------------------
CREATE TABLE CartItem (
    CartItemID INT IDENTITY(1,1) PRIMARY KEY,
    CartID INT NOT NULL,
    ProductVariantID INT NOT NULL,
    Quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (CartID) REFERENCES Cart(CartID),
    FOREIGN KEY (ProductVariantID) REFERENCES ProductVariant(ProductVariantID)
);
GO

----------------------------------------------------------
-- BẢNG HÓA ĐƠN
----------------------------------------------------------
CREATE TABLE Bill (
    BillID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    Total DECIMAL(12,2) NOT NULL,
    Status NVARCHAR(50) DEFAULT N'Pending',
    CreatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (UserID) REFERENCES [Users](UserID)
);
GO

----------------------------------------------------------
-- BẢNG CHI TIẾT HÓA ĐƠN
----------------------------------------------------------
CREATE TABLE BillDetail (
    BillDetailID INT IDENTITY(1,1) PRIMARY KEY,
    BillID INT NOT NULL,
    ProductVariantID INT NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (BillID) REFERENCES Bill(BillID),
    FOREIGN KEY (ProductVariantID) REFERENCES ProductVariant(ProductVariantID)
);
GO


