CREATE TABLE `clientes` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `bussines_id` varchar(50) NOT NULL,
   `date_added` datetime(6) NOT NULL,
   `email` varchar(50) NOT NULL,
   `phone` varchar(11) NOT NULL,
   `shared_key` varchar(50) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `UK_1c96wv36rk2hwui7qhjks3mvg` (`email`)
 ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci