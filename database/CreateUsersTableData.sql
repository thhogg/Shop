USE [FashionShop]
GO

INSERT INTO [dbo].[Users]
           ([UserName],[Password],[FullName],[Email],[Phone],[Address],[Role])
         
VALUES
('admin','admin','administrator','admin@gmail.com','999','12 NY AME',0);


INSERT INTO [dbo].[Users]
           ([UserName],[Password],[Email],[Role])
         
VALUES
('sa','123','sa@gmail.com',0),
('thh','thh','thh@thh.com',0),
('manager','man123','manager@shop.com',0),
('customer01','Cus012','custard@gmail.com',1),
('Test02','Hi02','Test@facebook.com',1),
('hoang','123','hoang@ms.com',1);

GO


