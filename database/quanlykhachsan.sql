CREATE DATABASE IF NOT EXISTS quanlykhachsan;
USE quanlykhachsan;

CREATE TABLE `DANHMUCCHUCNANG` (
    `MCN` INT(50) NOT NULL COMMENT 'Mã chức năng',
    `TENCN` VARCHAR(255) NOT NULL COMMENT 'Tên chức năng',
    `TT` INT(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái',
    PRIMARY KEY(MCN) 
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `NHOMQUYEN` (
    `MNQ` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã nhóm quyền',
    `TEN` VARCHAR(255) NOT NULL COMMENT 'Tên nhóm quyền',
    `TT` INT(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái',
    PRIMARY KEY(MNQ) 
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `CTQUYEN` (
    `MNQ` INT(11) NOT NULL COMMENT 'Mã nhóm quyền',
    `MCN` INT(50) NOT NULL COMMENT 'Mã chức năng',
    `HANHDONG` VARCHAR(255) NOT NULL COMMENT 'Hành động thực hiện',
    PRIMARY KEY(MNQ, MCN, HANHDONG),
    FOREIGN KEY (`MNQ`) REFERENCES `NHOMQUYEN` (`MNQ`),
    FOREIGN KEY (`MCN`) REFERENCES `DANHMUCCHUCNANG` (`MCN`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `NHANVIEN` (
    `MNV` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Mã nhân viên',
    `HOTEN` VARCHAR(255) NOT NULL COMMENT 'Họ và tên NV',
    `GIOITINH` INT(11) NOT NULL COMMENT 'Giới tính',
    `NGAYSINH` DATE NOT NULL COMMENT 'Ngày sinh',
    `SDT` VARCHAR(11) NOT NULL COMMENT 'Số điện thoại',
    `EMAIL` VARCHAR(50) NOT NULL UNIQUE COMMENT 'Email',
    `TT` INT(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái',
    `SNP` SMALLINT NOT NULL COMMENT 'Số ngày phép',
    `NVL` DATETIME NOT NULL COMMENT 'Ngày vào làm',
    `LN` INT NOT NULL COMMENT 'Lương một ngày',
    PRIMARY KEY(MNV)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `TAIKHOAN` (
    `MNV` INT(11) NOT NULL COMMENT 'Mã nhân viên',
    `MK` VARCHAR(255) NOT NULL COMMENT 'Mật khẩu',
    `TDN` VARCHAR(255) NOT NULL UNIQUE COMMENT 'Tên đăng nhập',
    `MNQ` INT(11) NOT NULL COMMENT 'Mã nhóm quyền',
    `TT` INT(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái',
    PRIMARY KEY(MNV, TDN),
    FOREIGN KEY (`MNV`) REFERENCES `NHANVIEN` (`MNV`),
    FOREIGN KEY (`MNQ`) REFERENCES `NHOMQUYEN` (`MNQ`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `KHACHHANG` (
	`MKH` INT(11) NOT NULL COMMENT 'Mã khách hàng',
    `TKH` VARCHAR(255) NOT NULL COMMENT 'Tên khách hàng',
    `GT` INT NOT NULL COMMENT 'Giới tính',
    `CCCD` INT NOT NULL COMMENT 'Căn cước công dân',
    `DIACHI` VARCHAR(255) COMMENT 'Địa chỉ',
    `SDT` VARCHAR(11) UNIQUE NOT NULL COMMENT 'Số điện thoại',
    `EMAIL` VARCHAR(50) UNIQUE COMMENT 'Email',
    `TT` INT(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái',
    `NS` DATE NOT NULL COMMENT 'Ngày sinh',
    PRIMARY KEY(MKH)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `DATPHONG` (
    `maDP` VARCHAR(20) NOT NULL COMMENT 'Mã đặt phòng',
    `maKH` INT NOT NULL COMMENT 'Mã khách hàng',
    `ngayLapPhieu` DATETIME NOT NULL COMMENT 'Ngày lập phiếu',
    `tienDatCoc` INT NOT NULL COMMENT 'Tiền đặt cọc',
    `tinhTrangXuLy` INT NOT NULL COMMENT 'Tình trạng xử lý',
    `xuLy` INT NOT NULL COMMENT 'Xử lý',
    PRIMARY KEY (`maDP`),
    FOREIGN KEY (`maKH`) REFERENCES `KHACHHANG` (`MKH`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `PHONG` (
	`maP` VARCHAR(20) NOT NULL COMMENT 'Mã phòng',
    `tenP` NVARCHAR(20) NOT NULL COMMENT 'Tên phòng',
    `loaiP` NVARCHAR(20) NOT NULL COMMENT 'Loại phòng',
    `hinhAnh` VARCHAR(255) NOT NULL COMMENT 'Hình ảnh',
    `giaP` INT NOT NULL COMMENT 'Giá phòng',
    `chiTietLoaiPhong` NVARCHAR(20) NOT NULL COMMENT 'Chi tiết loại phòng',
    `tinhTrang` INT NOT NULL DEFAULT 1 COMMENT 'Tình trạng', 
    PRIMARY KEY (`maP`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `CHITIETDATPHONG` (
    `maCTDP` VARCHAR(20) NOT NULL COMMENT 'Mã chi tiết đặt phòng',
    `maP` VARCHAR(20) NOT NULL COMMENT 'Mã phòng',
    `ngayThue` DATETIME NOT NULL COMMENT 'Ngày thuê',
    `ngayTra` DATETIME NOT NULL COMMENT 'Ngày trả',
    `ngayCheckOut` DATETIME COMMENT 'Ngày check out',
    `loaiHinhThue` INT NOT NULL COMMENT 'Loại hình thuê',
    `giaThue` INT NOT NULL COMMENT 'Giá thuê',
    `tinhTrang` INT NOT NULL DEFAULT 1 COMMENT 'Tình trạng',
    PRIMARY KEY (`maCTDP`, `maP`, `ngayThue`),
    FOREIGN KEY (`maCTDP`) REFERENCES `DATPHONG` (`maDP`),
    FOREIGN KEY (`maP`) REFERENCES `PHONG` (`maP`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `DICHVU` (
    `maDV` VARCHAR(20) NOT NULL COMMENT 'Mã dịch vụ',
    `tenDV` NVARCHAR(100) NOT NULL COMMENT 'Tên dịch vụ',
    `loaiDV` NVARCHAR(100) NOT NULL COMMENT 'Loại dịch vụ',
    `soLuong` INT NOT NULL COMMENT 'Số lượng',
    `giaDV` INT NOT NULL COMMENT 'Giá dịch vụ',
    `xuLy` INT NOT NULL DEFAULT 1 COMMENT 'Xử lý',
    PRIMARY KEY (`maDV`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `CHITIETTHUEDICHVU` (
    `maCTT` VARCHAR(20) NOT NULL COMMENT 'Mã chi tiết thuê dịch vụ',
    `maDV` VARCHAR(20) NOT NULL COMMENT 'Mã dịch vụ',
    `ngaySuDung` DATETIME NOT NULL COMMENT 'Ngày sử dụng',
    `SoLuong` INT NOT NULL COMMENT 'Số lượng',
    `giaDV` INT NOT NULL COMMENT 'Giá dịch vụ',
    PRIMARY KEY (`maCTT`, `maDV`, `ngaySuDung`),
    FOREIGN KEY (`maCTT`) REFERENCES `DATPHONG` (`maDP`),
    FOREIGN KEY (`maDV`) REFERENCES `DICHVU` (`maDV`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `HOADON` (
    `maHD` VARCHAR(20) NOT NULL COMMENT 'Mã hóa đơn',
    `maCTT` VARCHAR(20) NOT NULL COMMENT 'Mã chi tiết thuê',
    `tienP` INT NOT NULL COMMENT 'Tiền phòng',
    `tienDV` INT NOT NULL COMMENT 'Tiền dịch vụ',
    `giamGia` INT NOT NULL COMMENT 'Giảm giá',
    `phuThu` INT NOT NULL COMMENT 'Phụ thu',
    `tongTien` INT NOT NULL COMMENT 'Tổng tiền',
    `ngayThanhToan` DATETIME NOT NULL COMMENT 'Ngày thanh toán',
    `phuongThucThanhToan` NVARCHAR(100) NOT NULL COMMENT 'Phương thức thanh toán',
    `xuLy` INT NOT NULL DEFAULT 1 COMMENT 'Xử lý',
    PRIMARY KEY (`maHD`),
    FOREIGN KEY (`maCTT`) REFERENCES `DATPHONG` (`maDP`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `PHIEUNHAP` (
    `maPN` VARCHAR(20) NOT NULL COMMENT 'Mã phiếu nhập',
    `MNV` INT NOT NULL COMMENT 'Mã nhân viên',
    `ngayLap` DATETIME NOT NULL COMMENT 'Ngày lập',
    `tinhTrangXuLy` INT NOT NULL DEFAULT 1 COMMENT 'Tình trạng xử lý',
    PRIMARY KEY (`maPN`),
    FOREIGN KEY (`MNV`) REFERENCES `TAIKHOAN` (`MNV`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `CHITIETNHAP` (
    `maPN` VARCHAR(20) NOT NULL COMMENT 'Mã phiếu nhập',
    `maDV` VARCHAR(20) NOT NULL COMMENT 'Mã dịch vụ',
    `giaDVNhap` INT NOT NULL COMMENT 'Giá dịch vụ nhập',
    `soLuong` INT NOT NULL COMMENT 'Số lượng',
    PRIMARY KEY (`maPN`, `maDV`),
    FOREIGN KEY (`maPN`) REFERENCES `PHIEUNHAP` (`maPN`),
    FOREIGN KEY (`maDV`) REFERENCES `DICHVU` (`maDV`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `TIENICH` (
    `maTI` VARCHAR(20) NOT NULL COMMENT 'Mã tiện ích',
    `tenTI` NVARCHAR(30) NOT NULL COMMENT 'Tên tiện ích',
    `soLuong` INT NOT NULL COMMENT 'Số lượng',
    `xuLy` INT NOT NULL DEFAULT 1 COMMENT 'Xử lý',
    PRIMARY KEY (`maTI`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `CHITIETTIENICH` (
    `maP` VARCHAR(20) NOT NULL COMMENT 'Mã phòng',
    `maTI` VARCHAR(20) NOT NULL COMMENT 'Mã tiện ích',
    `soLuong` INT NOT NULL COMMENT 'Số lượng',
    PRIMARY KEY (`maP`, `maTI`),
    FOREIGN KEY (`maP`) REFERENCES `PHONG` (`maP`),
    FOREIGN KEY (`maTI`) REFERENCES `TIENICH` (`maTI`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

INSERT INTO `DANHMUCCHUCNANG` ( `MCN`, `TENCN`, `TT`)
VALUES
	(1, 'Quản lý phòng', 1),
    (2, 'Quản lý đặt phòng', 1),
    (3, 'Quản lý khách hàng', 1),
    (4, 'Quản lý dịch vụ', 1),
    (5, 'Quản lý nhân viên', 1),
    (6, 'Quản lý chức vụ', 1),
    (7, 'Quản lý nhập dịch vụ', 1),
    (8, 'Quản lý hóa đơn', 1),
    (9, 'Quản lý lễ tân', 1),
    (10, 'Quản lý kiểm kê tiện ích', 1),
    (11, 'Quản lý nhóm quyền', 1),
    (12, 'Quản lý tài khoản', 1),
    (13, 'Quản lý khuyến mãi', 1),
    (14, 'Quản lý thống kê', 1);

INSERT INTO `NHOMQUYEN` (`TEN`, `TT`) 
VALUES 
    ('Quản lý khách sạn', 1),
    ('Nhân viên lễ tân', 1),
    ('Nhân viên quản lý kho', 1),
    ('Khách hàng', 1);
    
-- Chèn dữ liệu vào bảng CTQUYEN
INSERT INTO `CTQUYEN` (`MNQ`, `MCN`, `HANHDONG`) 
VALUES 
    -- Quyền cho nhóm 'Quản lý khách sạn' (MNQ = 1)
    (1, 1, 'create'), (1, 1, 'delete'), (1, 1, 'update'), (1, 1, 'view'),
    (1, 2, 'create'), (1, 2, 'delete'), (1, 2, 'update'), (1, 2, 'view'),
    (1, 3, 'create'), (1, 3, 'delete'), (1, 3, 'update'), (1, 3, 'view'),
    (1, 4, 'create'), (1, 4, 'delete'), (1, 4, 'update'), (1, 4, 'view'),
    (1, 5, 'create'), (1, 5, 'delete'), (1, 5, 'update'), (1, 5, 'view'),
    (1, 6, 'create'), (1, 6, 'delete'), (1, 6, 'update'), (1, 6, 'view'),
    (1, 7, 'create'), (1, 7, 'delete'), (1, 7, 'update'), (1, 7, 'view'),
    (1, 8, 'create'), (1, 8, 'delete'), (1, 8, 'update'), (1, 8, 'view'),
    (1, 9, 'create'), (1, 9, 'view'),
    (1, 11, 'create'), (1, 11, 'delete'), (1, 11, 'update'), (1, 11, 'view'),
    (1, 12, 'create'), (1, 12, 'delete'), (1, 12, 'update'), (1, 12, 'view'),
    (1, 13, 'create'), (1, 13, 'delete'), (1, 13, 'update'), (1, 13, 'view'),
    (1, 14, 'create'), (1, 14, 'delete'), (1, 14, 'update'), (1, 14, 'view'),
    
    -- Quyền cho nhóm 'Nhân viên lễ tân' (MNQ = 2)
    (2, 1, 'view'),
    (2, 2, 'create'), (2, 2, 'view'),
    (2, 3, 'create'), (2, 3, 'delete'), (2, 3, 'update'), (2, 3, 'view'),
    (2, 8, 'view'),
    (2, 9, 'create'), (2, 9, 'view'),

    -- Quyền cho nhóm 'Nhân viên quản lý kho' (MNQ = 3)
    (3, 1, 'create'), (3, 1, 'delete'), (3, 1, 'update'), (3, 1, 'view'),
    (3, 4, 'create'), (3, 4, 'delete'), (3, 4, 'update'), (3, 4, 'view'),
    (3, 7, 'create'), (3, 7, 'update'), (3, 7, 'view'),
    (3, 10, 'create'), (3, 10, 'delete'), (3, 10, 'update'), (3, 10, 'view'),
    
    -- Quyền cho nhóm 'Khách hàng' (MNQ = 4)
    (4, 2, 'create'), (4, 2, 'view'),
    (4, 3, 'view'),
    (4, 8, 'view');
    
INSERT INTO `NHANVIEN` ( `MNV`, `HOTEN`, `GIOITINH`, `NGAYSINH`, `SDT`, `EMAIL`, `TT`, `SNP`, `NVL`, `LN`)
VALUES
	(1, 'Nguyễn Thị Ngọc Tú', 0, '2004-05-15', '0912345678', 'ngoctu@gmail.com', 1, 12, '2020-01-10 08:00:00', 300000),
    (2, 'Võ Thị Thu Luyện', 0, '2004-08-22', '0987654321', 'thuluyn@gmail.com', 1, 10, '2021-03-15 09:00:00', 250000),
    (3, 'Trần Thị Xuân Thanh', 0, '2005-12-01', '0935123456', 'xuanthanh@gmail.com', 1, 15, '2019-06-20 07:30:00', 350000),
    (4, 'Nguyễn Huỳnh Yến Linh', 0, '2005-03-10', '0908765432', 'phamthidung@gmail.com', 1, 8, '2022-02-01 08:30:00', 280000),
    (5, 'Lê Anh Khoa', 1, '2000-07-25', '0971234567', 'anhkhoa@gmail.com', 0, 10, '2020-11-11 09:00:00', 270000),
	(6, 'Phạm Minh Tuấn', 1, '1998-04-12', '0923456789', 'minhtuan@gmail.com', 1, 20, '2018-05-10 08:00:00', 320000),
	(7, 'Hoàng Thị Mai', 0, '2002-09-30', '0945678901', 'thimai@gmail.com', 1, 14, '2020-07-15 09:00:00', 290000),
	(8, 'Đặng Văn Hùng', 1, '2001-11-05', '0916789012', 'vanhung@gmail.com', 0, 12, '2021-01-20 07:30:00', 310000),
	(9, 'Lê Thị Hồng Nhung', 0, '2003-02-14', '0937890123', 'hongnhung@gmail.com', 1, 18, '2019-09-05 08:30:00', 330000),
	(10, 'Trần Văn Nam', 1, '1999-06-20', '0909012345', 'vannam@gmail.com', 1, 16, '2020-03-12 08:00:00', 300000),
	(11, 'Nguyễn Thị Lan', 0, '2004-01-25', '0970123456', 'thilan@gmail.com', 1, 10, '2022-04-01 09:00:00', 270000),
	(12, 'Vũ Minh Đức', 1, '2000-08-15', '0951234567', 'minhduc@gmail.com', 0, 15, '2021-06-10 08:30:00', 340000),
	(13, 'Bùi Thị Thanh Tâm', 0, '2005-03-03', '0942345678', 'thanhtam@gmail.com', 1, 13, '2020-12-15 07:45:00', 280000),
	(14, 'Phan Văn Hòa', 1, '1997-12-10', '0933456789', 'vanhoa@gmail.com', 1, 22, '2017-11-20 08:00:00', 360000),
	(15, 'Đỗ Thị Kim Ngân', 0, '2002-07-07', '0914567890', 'kimngan@gmail.com', 1, 11, '2021-08-25 09:00:00', 295000),
	(16, 'Lý Văn Long', 1, '2001-05-18', '0925678901', 'vanlong@gmail.com', 0, 17, '2019-04-10 08:15:00', 325000),
	(17, 'Ngô Thị Minh Thư', 0, '2003-10-22', '0906789012', 'minhthu@gmail.com', 1, 9, '2022-01-15 08:30:00', 265000),
	(18, 'Hà Văn Thành', 1, '1999-09-09', '0977890123', 'vanthanh@gmail.com', 1, 19, '2018-06-05 07:30:00', 350000),
	(19, 'Trương Thị Cẩm Tú', 0, '2004-04-01', '0949012345', 'camtu@gmail.com', 1, 12, '2020-10-20 08:45:00', 285000),
	(20, 'Nguyễn Văn Quốc', 1, '2000-02-28', '0930123456', 'vanquoc@gmail.com', 0, 14, '2021-02-10 09:00:00', 310000),
	(21, 'Lê Thị Ánh Tuyết', 0, '2005-06-15', '0911234567', 'anhtuyet@gmail.com', 1, 10, '2022-03-01 08:00:00', 275000),
	(22, 'Võ Văn Bình', 1, '1998-03-20', '0952345678', 'vanbinh@gmail.com', 1, 21, '2018-08-15 07:45:00', 370000),
	(23, 'Phạm Thị Ngọc Ánh', 0, '2002-11-11', '0923456789', 'ngocanh@gmail.com', 1, 13, '2020-05-25 08:30:00', 290000),
	(24, 'Hoàng Văn Đạt', 1, '2001-01-30', '0904567890', 'vandat@gmail.com', 0, 16, '2019-07-10 09:00:00', 335000),
	(25, 'Đặng Thị Thu Hà', 0, '2003-08-05', '0975678901', 'thuha@gmail.com', 1, 11, '2021-09-05 08:15:00', 280000),
	(26, 'Nguyễn Văn Hảo', 1, '1999-04-17', '0946789012', 'vanhao@gmail.com', 1, 18, '2018-12-20 07:30:00', 345000),
	(27, 'Trần Thị Bích Ngọc', 0, '2004-12-12', '0937890123', 'bichngoc@gmail.com', 1, 15, '2020-02-15 08:00:00', 300000),
	(28, 'Lê Văn Khánh', 1, '2000-10-08', '0919012345', 'vankhanh@gmail.com', 0, 12, '2021-04-10 09:00:00', 315000),
	(29, 'Vũ Thị Thanh Mai', 0, '2005-05-25', '0950123456', 'thanhmai@gmail.com', 1, 10, '2022-05-01 08:30:00', 270000),
	(30, 'Bùi Văn Tùng', 1, '1997-07-14', '0921234567', 'vantung@gmail.com', 1, 20, '2017-10-05 07:45:00', 355000),
	(31, 'Phan Thị Kim Chi', 0, '2002-02-09', '0902345678', 'kimchi@gmail.com', 1, 14, '2020-06-20 08:00:00', 295000),
	(32, 'Đỗ Văn Minh', 1, '2001-06-26', '0973456789', 'vanminh@gmail.com', 0, 17, '2019-03-15 09:00:00', 340000),
	(33, 'Lý Thị Hồng Phúc', 0, '2003-09-19', '0944567890', 'hongphuc@gmail.com', 1, 11, '2021-07-25 08:15:00', 285000),
	(34, 'Ngô Văn An', 1, '1999-05-04', '0935678901', 'vanan@gmail.com', 1, 19, '2018-04-10 07:30:00', 360000),
	(35, 'Hà Thị Minh Châu', 0, '2004-03-15', '0916789012', 'minhchau@gmail.com', 1, 13, '2020-11-05 08:45:00', 290000),
	(36, 'Trương Văn Phong', 1, '2000-08-22', '0957890123', 'vanphong@gmail.com', 0, 15, '2021-05-20 09:00:00', 320000),
	(37, 'Nguyễn Thị Diệu Linh', 0, '2005-01-10', '0929012345', 'dieulinh@gmail.com', 1, 12, '2022-06-15 08:00:00', 275000),
	(38, 'Lê Văn Quang', 1, '1998-11-27', '0900123456', 'vanquang@gmail.com', 1, 22, '2017-12-10 07:45:00', 375000),
	(39, 'Võ Thị Thùy Trang', 0, '2002-04-03', '0971234567', 'thuytrang@gmail.com', 1, 16, '2020-08-01 08:30:00', 305000),
	(40, 'Phạm Văn Huy', 1, '2001-07-16', '0942345678', 'vanhuy@gmail.com', 0, 14, '2019-05-25 09:00:00', 330000);
		
-- Dữ liệu cho tài khoản 
INSERT INTO TAIKHOAN (`MNV`, `MK`, `TDN`, `MNQ`, `TT`)
VALUES 
	(1, '$2a$12$DB1vW2l4LRiB1th6kZ5z5.2o5nRT6O3WhMhPqEK.FXsaczRZwyapy', 'admin', 1, 1),
    (2, '$2a$12$2Zg5jzqo5YlWm6O9gxbbhOlu7eig/B0GBr27gzDIJsdiVT4jzmF/W', 'NV1', 2, 1),
    (3, '$2a$12$2Zg5jzqo5YlWm6O9gxbbhOlu7eig/B0GBr27gzDIJsdiVT4jzmF/W', 'NV2', 3, 1);

INSERT INTO `KHACHHANG` (`MKH`,`TKH`, `GT`, `CCCD`, `DIACHI`, `SDT`, `EMAIL`, `TT`, `NS`)
VALUES
	(1, 'Nguyễn Văn Hùng', 1, 123456789, 'Hà Nội', '0912345678', 'nguyenvanhung@gmail.com', 1, '1990-05-15'),
    (2, 'Trần Thị Lan', 0, 123456790, 'TP. Hồ Chí Minh', '0987654321', 'tranthilan@gmail.com', 1, '1985-08-22'),
    (3, 'Lê Hoàng Nam', 1, 123456791, 'Đà Nẵng', '0935123456', 'lehoangnam@gmail.com', 1, '1992-12-01'),
    (4, 'Phạm Thị Mai', 0, 123456792, 'Hải Phòng', '0908765432', 'phamthimai@gmail.com', 1, '1995-03-10'),
    (5, 'Hoàng Văn Tâm', 1, 123456793, 'Cần Thơ', '0971234567', 'hoangvantam@gmail.com', 1, '1988-07-25'),
    (6, 'Nguyễn Thị Hồng', 0, 123456794, 'Nha Trang', '0913456789', 'nguyenthihong@gmail.com', 1, '1993-11-11'),
    (7, 'Trần Văn Long', 1, 123456795, 'Huế', '0986543210', 'tranvanlong@gmail.com', 1, '1980-09-30'),
    (8, 'Lê Thị Thu', 0, 123456796, 'Quảng Ninh', '0936789012', 'lethithu@gmail.com', 1, '1997-02-14'),
    (9, 'Phạm Văn Bình', 1, 123456797, 'Vũng Tàu', '0909876543', 'phamvanbinh@gmail.com', 1, '1986-06-20'),
    (10, 'Hoàng Thị Ngọc', 0, 123456798, 'Đà Lạt', '0972345678', 'hoangthingoc@gmail.com', 1, '1994-04-05'),
    (11, 'Nguyễn Văn Dũng', 1, 123456799, 'Hà Tĩnh', '0914567890', 'nguyenvandung@gmail.com', 1, '1989-01-25'),
    (12, 'Trần Thị Hoa', 0, 123456800, 'Bình Dương', '0985432109', 'tranthihoa@gmail.com', 1, '1991-10-10'),
    (13, 'Lê Văn Khoa', 1, 123456801, 'Quy Nhơn', '0937890123', 'levankhoa@gmail.com', 1, '1987-03-15'),
    (14, 'Phạm Thị Linh', 0, 123456802, 'Long An', '0908765431', 'phamthilinh@gmail.com', 1, '1996-07-07'),
    (15, 'Hoàng Văn Phong', 1, 123456803, 'Thanh Hóa', '0973456789', 'hoangvanphong@gmail.com', 1, '1984-12-20'),
    (16, 'Nguyễn Thị Yến', 0, 123456804, 'Phú Yên', '0915678901', 'nguyenthiyen@gmail.com', 1, '1998-05-30'),
    (17, 'Trần Văn Tuấn', 1, 123456805, 'Bắc Giang', '0984321098', 'tranvantuan@gmail.com', 1, '1983-08-12'),
    (18, 'Lê Thị Hạnh', 0, 123456806, 'Tiền Giang', '0938901234', 'lethihanh@gmail.com', 1, '1990-02-28'),
    (19, 'Phạm Văn Đức', 1, 123456807, 'Ninh Bình', '0907654321', 'phamvanduc@gmail.com', 1, '1985-11-15'),
    (20, 'Hoàng Thị Thảo', 0, 123456808, 'Bình Thuận', '0974567890', 'hoangthithao@gmail.com', 1, '1993-09-09'),
    (21, 'Nguyễn Văn Tài', 1, 123456809, 'Hà Nam', '0916789012', 'nguyenvantai@gmail.com', 1, '1988-04-18'),
    (22, 'Trần Thị Phương', 0, 123456810, 'Đồng Nai', '0983210987', 'tranthiphuong@gmail.com', 1, '1995-06-25'),
    (23, 'Lê Văn Hùng', 1, 123456811, 'Quảng Nam', '0939012345', 'levanhung@gmail.com', 1, '1982-01-10'),
    (24, 'Phạm Thị Oanh', 0, 123456812, 'Kiên Giang', '0906543210', 'phamthioanh@gmail.com', 1, '1997-03-22'),
    (25, 'Hoàng Văn Sơn', 1, 123456813, 'Lào Cai', '0975678901', 'hoangvanson@gmail.com', 1, '1986-10-05'),
    (26, 'Nguyễn Thị Bích', 0, 123456814, 'Sóc Trăng', '0917890123', 'nguyenthibich@gmail.com', 1, '1992-12-12'),
    (27, 'Trần Văn Quang', 1, 123456815, 'Yên Bái', '0981098765', 'tranvanquang@gmail.com', 1, '1989-07-30'),
    (28, 'Lê Thị Duyên', 0, 123456816, 'Bạc Liêu', '0930123456', 'lethiduyen@gmail.com', 1, '1994-08-15'),
    (29, 'Phạm Văn Thắng', 1, 123456817, 'Phú Thọ', '0905432109', 'phamvanthang@gmail.com', 1, '1987-05-20'),
    (30, 'Hoàng Thị Hương', 0, 123456818, 'Trà Vinh', '0976789012', 'hoangthihuong@gmail.com', 1, '1996-11-25'),
    (31, 'Nguyễn Văn Lâm', 1, 123456819, 'Vĩnh Phúc', '0918901234', 'nguyenvanlam@gmail.com', 1, '1984-02-14'),
    (32, 'Trần Thị Kim', 0, 123456820, 'Hậu Giang', '0980987654', 'tranthikim@gmail.com', 1, '1991-09-18'),
    (33, 'Lê Văn Phúc', 1, 123456821, 'Nam Định', '0931234567', 'levanphuc@gmail.com', 1, '1983-06-10'),
    (34, 'Phạm Thị Nhung', 0, 123456822, 'Bình Phước', '0904321098', 'phamthinhung@gmail.com', 1, '1998-04-30'),
    (35, 'Hoàng Văn Mạnh', 1, 123456823, 'Tuyên Quang', '0977890123', 'hoangvanmanh@gmail.com', 1, '1985-12-25'),
    (36, 'Nguyễn Thị Ánh', 0, 123456824, 'Cà Mau', '0919012345', 'nguyenthiang@gmail.com', 1, '1990-03-05'),
    (37, 'Trần Văn Đạt', 1, 123456825, 'Hòa Bình', '0987654320', 'tranvadat@gmail.com', 1, '1988-08-08'),
    (38, 'Lê Thị Tuyết', 0, 123456826, 'Kon Tum', '0932345678', 'lethituyet@gmail.com', 1, '1993-10-20'),
    (39, 'Phạm Văn Kiên', 1, 123456827, 'Lạng Sơn', '0903210987', 'phamvankien@gmail.com', 1, '1986-01-15'),
    (40, 'Hoàng Thị Minh', 0, 123456828, 'Gia Lai', '0978901234', 'hoangthiminh@gmail.com', 1, '1995-07-12');


-- Bảng PHONG
INSERT INTO `PHONG` (`maP`, `tenP`, `loaiP`, `hinhAnh`, `giaP`, `chiTietLoaiPhong`, `tinhTrang`) 
VALUES
    ('P001', 'Phòng 101', 'Đơn', 'images/phongdon1.jpg', 500000, '1 giường đơn', 1),
    ('P002', 'Phòng 102', 'Đơn', 'images/phongdon2.jpg', 500000, '1 giường đơn', 1),
    ('P003', 'Phòng 201', 'Đôi', 'images/phongdoi1.jpg', 800000, '1 giường đôi', 1),
    ('P004', 'Phòng 202', 'Đôi', 'images/phongdoi2.jpg', 800000, '1 giường đôi', 1),
    ('P005', 'Phòng 301', 'VIP', 'images/phongvip1.jpg', 1500000, '1 giường king', 1),
    ('P006', 'Phòng 302', 'VIP', 'images/phongvip2.jpg', 1500000, '1 giường king', 1),
    ('P007', 'Phòng 401', 'Gia đình', 'images/phonggd1.jpg', 1200000, '2 giường đôi', 1),
    ('P008', 'Phòng 402', 'Gia đình', 'images/phonggd2.jpg', 1200000, '2 giường đôi', 1),
    ('P009', 'Phòng 501', 'Suite', 'images/phongsuite1.jpg', 2000000, '1 giường king + sofa', 1),
    ('P010', 'Phòng 502', 'Suite', 'images/phongsuite2.jpg', 2000000, '1 giường king + sofa', 1);

-- Bảng DATPHONG
INSERT INTO `DATPHONG` (`maDP`, `maKH`, `ngayLapPhieu`, `tienDatCoc`, `tinhTrangXuLy`, `xuLy`) 
VALUES
    ('DP001', 1, '2025-04-01 10:00:00', 200000, 1, 1),
    ('DP002', 2, '2025-04-02 14:30:00', 300000, 0, 0),
    ('DP003', 3, '2025-04-03 09:15:00', 500000, 1, 1),
    ('DP004', 4, '2025-04-04 16:00:00', 400000, 1, 1),
    ('DP005', 5, '2025-04-05 11:30:00', 600000, 0, 0),
    ('DP006', 6, '2025-04-06 13:45:00', 200000, 1, 1),
    ('DP007', 7, '2025-04-07 08:00:00', 300000, 1, 1),
    ('DP008', 8, '2025-04-08 15:20:00', 700000, 0, 0),
    ('DP009', 9, '2025-04-09 12:10:00', 800000, 1, 1),
    ('DP010', 10, '2025-04-10 17:00:00', 500000, 1, 1);

-- Bảng CHITIETDATPHONG
INSERT INTO `CHITIETDATPHONG` (`maCTDP`, `maP`, `ngayThue`, `ngayTra`, `ngayCheckOut`, `loaiHinhThue`, `giaThue`, `tinhTrang`) 
VALUES
    ('DP001', 'P001', '2025-04-02 14:00:00', '2025-04-04 12:00:00', NULL, 1, 500000, 1),
    ('DP002', 'P003', '2025-04-03 14:00:00', '2025-04-05 12:00:00', NULL, 1, 800000, 1),
    ('DP003', 'P005', '2025-04-04 14:00:00', '2025-04-07 12:00:00', '2025-04-07 11:00:00', 1, 1500000, 0),
    ('DP004', 'P007', '2025-04-05 14:00:00', '2025-04-08 12:00:00', NULL, 1, 1200000, 1),
    ('DP005', 'P009', '2025-04-06 14:00:00', '2025-04-10 12:00:00', NULL, 1, 2000000, 1),
    ('DP006', 'P002', '2025-04-07 14:00:00', '2025-04-09 12:00:00', NULL, 1, 500000, 1),
    ('DP007', 'P004', '2025-04-08 14:00:00', '2025-04-10 12:00:00', NULL, 1, 800000, 1),
    ('DP008', 'P006', '2025-04-09 14:00:00', '2025-04-12 12:00:00', NULL, 1, 1500000, 1),
    ('DP009', 'P008', '2025-04-10 14:00:00', '2025-04-13 12:00:00', NULL, 1, 1200000, 1),
    ('DP010', 'P010', '2025-04-11 14:00:00', '2025-04-15 12:00:00', NULL, 1, 2000000, 1);

-- Bảng DICHVU
INSERT INTO `DICHVU` (`maDV`, `tenDV`, `loaiDV`, `soLuong`, `giaDV`, `xuLy`) 
VALUES
    ('DV001', 'Ăn sáng', 'Ẩm thực', 50, 100000, 1),
    ('DV002', 'Giặt ủi', 'Dịch vụ phòng', 30, 50000, 1),
    ('DV003', 'Massage', 'Spa', 20, 300000, 1),
    ('DV004', 'Thuê xe', 'Di chuyển', 10, 500000, 1),
    ('DV005', 'Nước uống', 'Đồ uống', 100, 20000, 1);

-- Bảng CHITIETTHUEDICHVU
INSERT INTO `CHITIETTHUEDICHVU` (`maCTT`, `maDV`, `ngaySuDung`, `SoLuong`, `giaDV`) 
VALUES
    ('DP001', 'DV001', '2025-04-02 08:00:00', 1, 100000),
    ('DP002', 'DV002', '2025-04-03 10:00:00', 2, 50000),
    ('DP003', 'DV003', '2025-04-04 15:00:00', 1, 300000),
    ('DP004', 'DV004', '2025-04-05 09:00:00', 1, 500000),
    ('DP005', 'DV005', '2025-04-06 12:00:00', 3, 20000),
    ('DP006', 'DV001', '2025-04-07 08:00:00', 2, 100000),
    ('DP007', 'DV002', '2025-04-08 11:00:00', 1, 50000),
    ('DP008', 'DV003', '2025-04-09 16:00:00', 1, 300000),
    ('DP009', 'DV004', '2025-04-10 10:00:00', 1, 500000),
    ('DP010', 'DV005', '2025-04-11 13:00:00', 2, 20000);

-- Bảng HOADON
INSERT INTO `HOADON` (`maHD`, `maCTT`, `tienP`, `tienDV`, `giamGia`, `phuThu`, `tongTien`, `ngayThanhToan`, `phuongThucThanhToan`, `xuLy`) 
VALUES
    ('HD001', 'DP001', 1000000, 100000, 0, 0, 1100000, '2025-04-04 12:00:00', 'Tiền mặt', 1),
    ('HD002', 'DP002', 1600000, 100000, 0, 0, 1700000, '2025-04-05 12:00:00', 'Thẻ tín dụng', 1),
    ('HD003', 'DP003', 4500000, 300000, 50000, 0, 4750000, '2025-04-07 11:00:00', 'Chuyển khoản', 1),
    ('HD004', 'DP004', 3600000, 500000, 0, 100000, 4200000, '2025-04-08 12:00:00', 'Tiền mặt', 1),
    ('HD005', 'DP005', 8000000, 60000, 0, 0, 8060000, '2025-04-10 12:00:00', 'Thẻ tín dụng', 1),
    ('HD006', 'DP006', 1000000, 200000, 0, 0, 1200000, '2025-04-09 12:00:00', 'Tiền mặt', 1),
    ('HD007', 'DP007', 1600000, 50000, 0, 0, 1650000, '2025-04-10 12:00:00', 'Chuyển khoản', 1),
    ('HD008', 'DP008', 4500000, 300000, 0, 0, 4800000, '2025-04-12 12:00:00', 'Thẻ tín dụng', 1),
    ('HD009', 'DP009', 3600000, 500000, 0, 0, 4100000, '2025-04-13 12:00:00', 'Tiền mặt', 1),
    ('HD010', 'DP010', 8000000, 40000, 0, 0, 8040000, '2025-04-15 12:00:00', 'Chuyển khoản', 1);

-- Bảng PHIEUNHAP
INSERT INTO `PHIEUNHAP` (`maPN`, `MNV`, `ngayLap`, `tinhTrangXuLy`) 
VALUES
    ('PN001', 1, '2025-03-01 09:00:00', 1),
    ('PN002', 2, '2025-03-02 14:00:00', 1),
    ('PN003', 3, '2025-03-03 10:30:00', 1),
    ('PN004', 1, '2025-03-04 15:00:00', 0),
    ('PN005', 2, '2025-03-05 11:00:00', 1);

-- Bảng CHITIETNHAP
INSERT INTO `CHITIETNHAP` (`maPN`, `maDV`, `giaDVNhap`, `soLuong`) 
VALUES
    ('PN001', 'DV001', 80000, 20),
    ('PN002', 'DV002', 40000, 15),
    ('PN003', 'DV003', 250000, 10),
    ('PN004', 'DV004', 450000, 5),
    ('PN005', 'DV005', 15000, 50);

-- Bảng TIENICH
INSERT INTO `TIENICH` (`maTI`, `tenTI`, `soLuong`, `xuLy`) 
VALUES
    ('TI001', 'Wifi', 100, 1),
    ('TI002', 'TV', 50, 1),
    ('TI003', 'Máy lạnh', 40, 1),
    ('TI004', 'Tủ lạnh', 30, 1),
    ('TI005', 'Bình nóng lạnh', 60, 1);

-- Bảng CHITIETTIENICH
INSERT INTO `CHITIETTIENICH` (`maP`, `maTI`, `soLuong`) 
VALUES
    ('P001', 'TI001', 1), ('P001', 'TI002', 1),
    ('P002', 'TI001', 1), ('P002', 'TI002', 1),
    ('P003', 'TI001', 1), ('P003', 'TI003', 1),
    ('P004', 'TI001', 1), ('P004', 'TI003', 1),
    ('P005', 'TI001', 1), ('P005', 'TI003', 1), ('P005', 'TI004', 1),
    ('P006', 'TI001', 1), ('P006', 'TI003', 1), ('P006', 'TI004', 1),
    ('P007', 'TI001', 1), ('P007', 'TI002', 1), ('P007', 'TI003', 1),
    ('P008', 'TI001', 1), ('P008', 'TI002', 1), ('P008', 'TI003', 1),
    ('P009', 'TI001', 1), ('P009', 'TI003', 1), ('P009', 'TI004', 1), ('P009', 'TI005', 1),
    ('P010', 'TI001', 1), ('P010', 'TI003', 1), ('P010', 'TI004', 1), ('P010', 'TI005', 1);





