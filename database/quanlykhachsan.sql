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
    `OTP` VARCHAR(50) DEFAULT NULL COMMENT 'Mã OTP',
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

CREATE TABLE `TAIKHOANKH` (
    `MKH` INT(11) NOT NULL COMMENT 'Mã khách hàng',
    `MK` VARCHAR(255) NOT NULL COMMENT 'Mật khẩu',
    `TDN` VARCHAR(255) NOT NULL UNIQUE COMMENT 'Tên đăng nhập',
    `MNQ` INT(11) NOT NULL COMMENT 'Mã nhóm quyền',
    `TT` INT(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái',
    `OTP` VARCHAR(50) DEFAULT NULL COMMENT 'Mã OTP',
    PRIMARY KEY(MKH, TDN),
    FOREIGN KEY (`MKH`) REFERENCES `KHACHHANG` (`MKH`),
    FOREIGN KEY (`MNQ`) REFERENCES `NHOMQUYEN` (`MNQ`)
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
    `hinhThucThanhToan` NVARCHAR(100) NOT NULL COMMENT 'Hình thức thanh toán',
    `xuLy` INT NOT NULL DEFAULT 1 COMMENT 'Xử lý',
    PRIMARY KEY (`maHD`),
    FOREIGN KEY (`maCTT`) REFERENCES `DATPHONG` (`maDP`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `PHIEUNHAP` (
    `maPN` VARCHAR(20) NOT NULL COMMENT 'Mã phiếu nhập',
    `maNV` INT NOT NULL COMMENT 'Mã nhân viên',
    `ngayLap` DATETIME NOT NULL COMMENT 'Ngày lập',
    `tinhTrangXuLy` INT NOT NULL DEFAULT 1 COMMENT 'Tình trạng xử lý',
    PRIMARY KEY (`maPN`),
    FOREIGN KEY (`maNV`) REFERENCES `TAIKHOAN` (`MNV`)
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

INSERT INTO `DANHMUCCHUCNANG` ( `MCN`, `TCN`, `TT`)
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
	('Nguyễn Thị Ngọc Tú', 0, '2004-05-15', '0912345678', 'ngoctu@gmail.com', 1, 12, '2020-01-10 08:00:00', 300000),
    ('Võ Thị Thu Luyện', 0, '2004-08-22', '0987654321', 'thuluyn@gmail.com', 1, 10, '2021-03-15 09:00:00', 250000),
    ('Trần Thị Xuân Thanh', 0, '2005-12-01', '0935123456', 'xuanthanh@gmail.com', 1, 15, '2019-06-20 07:30:00', 350000),
    ('Nguyễn Huỳnh Yến Linh', 0, '2005-03-10', '0908765432', 'phamthidung@gmail.com', 1, 8, '2022-02-01 08:30:00', 280000),
    ('Lê Anh Khoa', 1, '2000-07-25', '0971234567', 'anhkhoa@gmail.com', 0, 10, '2020-11-11 09:00:00', 270000);
    
-- Dữ liệu cho tài khoản (Bổ sung sau)

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
    
-- Dữ liệu tài khoản khách hàng(Bổ sung sau)

INSERT INTO `PHONG` (`maP`, `tenP`, `loaiP`, `hinhAnh`, `giaP`, `chiTietLoaiPhong`, `tinhTrang`) 
VALUES
    ('P101', 'Phòng 101', 'Đơn', './images/P101.jpg', 500000, '1 giường đơn', 1),
    ('P102', 'Phòng 102', 'Đơn', './images/P102.jpg', 500000, '1 giường đơn', 1),
    ('P103', 'Phòng 103', 'Đơn', './images/P103.jpg', 500000, '1 giường đơn', 1),
    ('P104', 'Phòng 104', 'Đơn', './images/P104.jpg', 500000, '1 giường đơn', 1),
    ('P105', 'Phòng 105', 'Đơn', './images/P105.jpg', 500000, '1 giường đơn', 1),
    ('P106', 'Phòng 106', 'Đơn', './images/P106.jpg', 500000, '1 giường đơn', 1),
    ('P107', 'Phòng 107', 'Đơn', './images/P107.jpg', 500000, '1 giường đơn', 1),
    ('P108', 'Phòng 108', 'Đơn', './images/P108.jpg', 500000, '1 giường đơn', 1),
    ('P109', 'Phòng 109', 'Đơn', './images/P109.jpg', 500000, '1 giường đơn', 1),
    ('P110', 'Phòng 110', 'Đơn', './images/P110.jpg', 500000, '1 giường đơn', 1),
    ('P201', 'Phòng 201', 'Đôi', './images/P201.jpg', 800000, '1 giường đôi', 1),
    ('P202', 'Phòng 202', 'Đôi', './images/P202.jpg', 800000, '1 giường đôi', 1),
    ('P203', 'Phòng 203', 'Đôi', './images/P203.jpg', 800000, '1 giường đôi', 1),
    ('P204', 'Phòng 204', 'Đôi', './images/P204.jpg', 800000, '1 giường đôi', 1),
    ('P205', 'Phòng 205', 'Đôi', './images/P205.jpg', 800000, '1 giường đôi', 1),
    ('P206', 'Phòng 206', 'Gia đình', './images/P206.jpg', 1200000, '2 giường đôi', 1),
    ('P207', 'Phòng 207', 'Gia đình', './images/P207.jpg', 1200000, '2 giường đôi', 1),
    ('P208', 'Phòng 208', 'Gia đình', './images/P208.jpg', 1200000, '2 giường đôi', 1),
    ('P209', 'Phòng 209', 'Gia đình', './images/P209.jpg', 1200000, '2 giường đôi', 1),
    ('P210', 'Phòng 210', 'Gia đình', './images/P210.jpg', 1200000, '2 giường đôi', 1),
    ('P301', 'Phòng 301', 'VIP', './images/P301.jpg', 1500000, '1 giường king', 1),
    ('P302', 'Phòng 302', 'VIP', './images/P302.jpg', 1500000, '1 giường king', 1),
    ('P303', 'Phòng 303', 'VIP', './images/P303.jpg', 1500000, '1 giường king', 1),
    ('P304', 'Phòng 304', 'VIP', './images/P304.jpg', 1500000, '1 giường king', 1),
    ('P305', 'Phòng 305', 'VIP', './images/P305.jpg', 1500000, '1 giường king', 1),
    ('P306', 'Phòng 306', 'VIP', './images/P306.jpg', 1500000, '1 giường king', 1),
    ('P307', 'Phòng 307', 'VIP', './images/P307.jpg', 1500000, '1 giường king', 1),
    ('P308', 'Phòng 308', 'VIP', './images/P308.jpg', 1500000, '1 giường king', 1),
    ('P309', 'Phòng 309', 'VIP', './images/P309.jpg', 1500000, '1 giường king', 1),
    ('P310', 'Phòng 310', 'VIP', './images/P310.jpg', 1500000, '1 giường king', 1),
    ('P401', 'Phòng 401', 'Suite', 'images/P401.jpg', 2000000, '1 giường king + sofa', 1),
    ('P402', 'Phòng 402', 'Suite', 'images/P402.jpg', 2000000, '1 giường king + sofa', 1),
    ('P403', 'Phòng 403', 'Suite', 'images/P403.jpg', 2000000, '1 giường king + sofa', 1),
    ('P404', 'Phòng 404', 'Suite', 'images/P404.jpg', 2000000, '1 giường king + sofa', 1),
    ('P405', 'Phòng 405', 'Suite', 'images/P405.jpg', 2000000, '1 giường king + sofa', 1),
    ('P501', 'Phòng 501', 'Suite', 'images/P501.jpg', 2000000, '1 giường king + sofa', 1),
    ('P502', 'Phòng 502', 'Suite', 'images/P502.jpg', 2000000, '1 giường king + sofa', 1),
    ('P503', 'Phòng 503', 'Suite', 'images/P503.jpg', 2000000, '1 giường king + sofa', 1),
    ('P504', 'Phòng 504', 'Suite', 'images/P504.jpg', 2000000, '1 giường king + sofa', 1),
    ('P505', 'Phòng 505', 'Suite', 'images/P505.jpg', 2000000, '1 giường king + sofa', 1);
    

INSERT INTO `DATPHONG` (`maDP`, `maKH`, `ngayLapPhieu`, `tienDatCoc`, `tinhTrangXuLy`, `xuLy`) 
VALUES
    ('DP001', 1, '2025-04-04 09:00:00', 200000, 1, 1),
    ('DP002', 2, '2025-04-04 12:30:00', 300000, 0, 0),
    ('DP003', 3, '2025-04-04 15:45:00', 500000, 1, 1),
    ('DP004', 4, '2025-04-05 10:15:00', 400000, 1, 1),
    ('DP005', 5, '2025-04-05 14:00:00', 600000, 0, 0),
    ('DP006', 6, '2025-04-06 08:30:00', 200000, 1, 1),
    ('DP007', 7, '2025-04-06 11:45:00', 300000, 1, 1),
    ('DP008', 8, '2025-04-06 16:20:00', 700000, 0, 0),
    ('DP009', 9, '2025-04-07 09:10:00', 800000, 1, 1),
    ('DP010', 10, '2025-04-07 13:00:00', 500000, 1, 1),
    ('DP011', 11, '2025-04-07 17:30:00', 200000, 0, 0),
    ('DP012', 12, '2025-04-08 10:00:00', 300000, 1, 1),
    ('DP013', 13, '2025-04-08 14:15:00', 400000, 1, 1),
    ('DP014', 14, '2025-04-08 18:00:00', 600000, 0, 0),
    ('DP015', 15, '2025-04-09 09:30:00', 500000, 1, 1),
    ('DP016', 16, '2025-04-09 12:45:00', 200000, 1, 1),
    ('DP017', 17, '2025-04-09 16:00:00', 700000, 0, 0),
    ('DP018', 18, '2025-04-10 08:15:00', 800000, 1, 1),
    ('DP019', 19, '2025-04-10 11:30:00', 300000, 1, 1),
    ('DP020', 20, '2025-04-10 15:00:00', 400000, 0, 0),
    ('DP021', 21, '2025-04-11 09:45:00', 500000, 1, 1),
    ('DP022', 22, '2025-04-11 13:15:00', 200000, 1, 1),
    ('DP023', 23, '2025-04-11 17:00:00', 600000, 0, 0),
    ('DP024', 24, '2025-04-12 10:30:00', 700000, 1, 1),
    ('DP025', 25, '2025-04-12 14:00:00', 300000, 1, 1),
    ('DP026', 26, '2025-04-12 18:15:00', 800000, 0, 0),
    ('DP027', 27, '2025-04-13 09:00:00', 400000, 1, 1),
    ('DP028', 28, '2025-04-13 12:30:00', 500000, 1, 1),
    ('DP029', 29, '2025-04-13 16:45:00', 200000, 0, 0),
    ('DP030', 30, '2025-04-14 10:00:00', 600000, 1, 1),
    ('DP031', 31, '2025-04-14 13:15:00', 300000, 1, 1),
    ('DP032', 32, '2025-04-14 17:30:00', 700000, 0, 0),
    ('DP033', 33, '2025-04-15 09:45:00', 800000, 1, 1),
    ('DP034', 34, '2025-04-15 12:00:00', 400000, 1, 1),
    ('DP035', 35, '2025-04-15 15:15:00', 500000, 0, 0),
    ('DP036', 36, '2025-04-16 08:30:00', 200000, 1, 1),
    ('DP037', 37, '2025-04-16 11:45:00', 600000, 1, 1),
    ('DP038', 38, '2025-04-16 16:00:00', 300000, 0, 0),
    ('DP039', 39, '2025-04-17 09:15:00', 700000, 1, 1),
    ('DP040', 40, '2025-04-17 12:30:00', 800000, 1, 1),
    ('DP041', 1, '2025-04-17 17:00:00', 400000, 0, 0),
    ('DP042', 2, '2025-04-18 10:00:00', 500000, 1, 1),
    ('DP043', 3, '2025-04-18 13:15:00', 200000, 1, 1),
    ('DP044', 4, '2025-04-18 16:30:00', 600000, 0, 0),
    ('DP045', 5, '2025-04-19 09:45:00', 300000, 1, 1);


INSERT INTO `CHITIETDATPHONG` (`maCTDP`, `maP`, `ngayThue`, `ngayTra`, `ngayCheckOut`, `loaiHinhThue`, `giaThue`, `tinhTrang`) 
VALUES
    ('DP001', 'P101', '2025-04-05 14:00:00', '2025-04-07 12:00:00', NULL, 1, 500000, 1),
    ('DP002', 'P201', '2025-04-05 14:00:00', '2025-04-08 12:00:00', NULL, 1, 800000, 1),
    ('DP003', 'P301', '2025-04-05 14:00:00', '2025-04-09 12:00:00', NULL, 1, 1500000, 1),
    ('DP004', 'P206', '2025-04-06 14:00:00', '2025-04-08 12:00:00', NULL, 1, 1200000, 1),
    ('DP005', 'P401', '2025-04-06 14:00:00', '2025-04-10 12:00:00', NULL, 1, 2000000, 1),
    ('DP006', 'P102', '2025-04-07 14:00:00', '2025-04-09 12:00:00', NULL, 1, 500000, 1),
    ('DP007', 'P202', '2025-04-07 14:00:00', '2025-04-10 12:00:00', NULL, 1, 800000, 1),
    ('DP008', 'P302', '2025-04-07 14:00:00', '2025-04-11 12:00:00', NULL, 1, 1500000, 1),
    ('DP009', 'P207', '2025-04-08 14:00:00', '2025-04-10 12:00:00', NULL, 1, 1200000, 1),
    ('DP010', 'P402', '2025-04-08 14:00:00', '2025-04-12 12:00:00', NULL, 1, 2000000, 1),
    ('DP011', 'P103', '2025-04-08 14:00:00', '2025-04-10 12:00:00', NULL, 1, 500000, 1),
    ('DP012', 'P203', '2025-04-09 14:00:00', '2025-04-11 12:00:00', NULL, 1, 800000, 1),
    ('DP013', 'P303', '2025-04-09 14:00:00', '2025-04-12 12:00:00', NULL, 1, 1500000, 1),
    ('DP014', 'P208', '2025-04-09 14:00:00', '2025-04-13 12:00:00', NULL, 1, 1200000, 1),
    ('DP015', 'P403', '2025-04-10 14:00:00', '2025-04-13 12:00:00', NULL, 1, 2000000, 1),
    ('DP016', 'P104', '2025-04-10 14:00:00', '2025-04-12 12:00:00', NULL, 1, 500000, 1),
    ('DP017', 'P204', '2025-04-10 14:00:00', '2025-04-14 12:00:00', NULL, 1, 800000, 1),
    ('DP018', 'P304', '2025-04-11 14:00:00', '2025-04-14 12:00:00', NULL, 1, 1500000, 1),
    ('DP019', 'P209', '2025-04-11 14:00:00', '2025-04-13 12:00:00', NULL, 1, 1200000, 1),
    ('DP020', 'P404', '2025-04-11 14:00:00', '2025-04-15 12:00:00', NULL, 1, 2000000, 1),
    ('DP021', 'P105', '2025-04-12 14:00:00', '2025-04-14 12:00:00', NULL, 1, 500000, 1),
    ('DP022', 'P205', '2025-04-12 14:00:00', '2025-04-15 12:00:00', NULL, 1, 800000, 1),
    ('DP023', 'P305', '2025-04-12 14:00:00', '2025-04-16 12:00:00', NULL, 1, 1500000, 1),
    ('DP024', 'P210', '2025-04-13 14:00:00', '2025-04-15 12:00:00', NULL, 1, 1200000, 1),
    ('DP025', 'P405', '2025-04-13 14:00:00', '2025-04-17 12:00:00', NULL, 1, 2000000, 1),
    ('DP026', 'P106', '2025-04-13 14:00:00', '2025-04-15 12:00:00', NULL, 1, 500000, 1),
    ('DP027', 'P201', '2025-04-14 14:00:00', '2025-04-16 12:00:00', NULL, 1, 800000, 1),
    ('DP028', 'P306', '2025-04-14 14:00:00', '2025-04-17 12:00:00', NULL, 1, 1500000, 1),
    ('DP029', 'P206', '2025-04-14 14:00:00', '2025-04-16 12:00:00', NULL, 1, 1200000, 1),
    ('DP030', 'P501', '2025-04-15 14:00:00', '2025-04-18 12:00:00', NULL, 1, 2000000, 1),
    ('DP031', 'P107', '2025-04-15 14:00:00', '2025-04-17 12:00:00', NULL, 1, 500000, 1),
    ('DP032', 'P202', '2025-04-15 14:00:00', '2025-04-19 12:00:00', NULL, 1, 800000, 1),
    ('DP033', 'P307', '2025-04-16 14:00:00', '2025-04-19 12:00:00', NULL, 1, 1500000, 1),
    ('DP034', 'P207', '2025-04-16 14:00:00', '2025-04-18 12:00:00', NULL, 1, 1200000, 1),
    ('DP035', 'P502', '2025-04-16 14:00:00', '2025-04-20 12:00:00', NULL, 1, 2000000, 1),
    ('DP036', 'P108', '2025-04-17 14:00:00', '2025-04-19 12:00:00', NULL, 1, 500000, 1),
    ('DP037', 'P203', '2025-04-17 14:00:00', '2025-04-20 12:00:00', NULL, 1, 800000, 1),
    ('DP038', 'P308', '2025-04-17 14:00:00', '2025-04-21 12:00:00', NULL, 1, 1500000, 1),
    ('DP039', 'P208', '2025-04-18 14:00:00', '2025-04-20 12:00:00', NULL, 1, 1200000, 1),
    ('DP040', 'P503', '2025-04-18 14:00:00', '2025-04-22 12:00:00', NULL, 1, 2000000, 1),
    ('DP041', 'P109', '2025-04-18 14:00:00', '2025-04-20 12:00:00', NULL, 1, 500000, 1),
    ('DP042', 'P204', '2025-04-19 14:00:00', '2025-04-21 12:00:00', NULL, 1, 800000, 1),
    ('DP043', 'P309', '2025-04-19 14:00:00', '2025-04-22 12:00:00', NULL, 1, 1500000, 1),
    ('DP044', 'P209', '2025-04-19 14:00:00', '2025-04-23 12:00:00', NULL, 1, 1200000, 1),
    ('DP045', 'P504', '2025-04-20 14:00:00', '2025-04-23 12:00:00', NULL, 1, 2000000, 1);


INSERT INTO `DICHVU` (`maDV`, `tenDV`, `loaiDV`, `soLuong`, `giaDV`, `xuLy`) 
VALUES
    ('DV001', 'Ăn sáng', 'Ẩm thực', 50, 100000, 1),
    ('DV002', 'Giặt ủi', 'Dịch vụ phòng', 30, 50000, 1),
    ('DV003', 'Massage', 'Spa', 20, 300000, 1),
    ('DV004', 'Thuê xe', 'Di chuyển', 10, 500000, 1),
    ('DV005', 'Nước uống', 'Đồ uống', 100, 20000, 1);


INSERT INTO `CHITIETTHUEDICHVU` (`maCTT`, `maDV`, `ngaySuDung`, `SoLuong`, `giaDV`) 
VALUES
    ('DP001', 'DV001', '2025-04-05 08:00:00', 1, 100000),
    ('DP002', 'DV002', '2025-04-05 10:00:00', 2, 50000),
    ('DP003', 'DV003', '2025-04-06 15:00:00', 1, 300000),
    ('DP004', 'DV004', '2025-04-06 09:00:00', 1, 500000),
    ('DP005', 'DV005', '2025-04-07 12:00:00', 3, 20000),
    ('DP006', 'DV001', '2025-04-07 08:00:00', 2, 100000),
    ('DP007', 'DV002', '2025-04-08 11:00:00', 1, 50000),
    ('DP008', 'DV003', '2025-04-08 16:00:00', 1, 300000),
    ('DP009', 'DV004', '2025-04-09 10:00:00', 1, 500000),
    ('DP010', 'DV005', '2025-04-09 13:00:00', 2, 20000),
    ('DP011', 'DV001', '2025-04-08 08:00:00', 1, 100000),
    ('DP012', 'DV002', '2025-04-09 10:00:00', 2, 50000),
    ('DP013', 'DV003', '2025-04-10 15:00:00', 1, 300000),
    ('DP014', 'DV004', '2025-04-10 09:00:00', 1, 500000),
    ('DP015', 'DV005', '2025-04-11 12:00:00', 3, 20000),
    ('DP016', 'DV001', '2025-04-10 08:00:00', 2, 100000),
    ('DP017', 'DV002', '2025-04-11 11:00:00', 1, 50000),
    ('DP018', 'DV003', '2025-04-12 16:00:00', 1, 300000),
    ('DP019', 'DV004', '2025-04-12 10:00:00', 1, 500000),
    ('DP020', 'DV005', '2025-04-12 13:00:00', 2, 20000),
    ('DP021', 'DV001', '2025-04-12 08:00:00', 1, 100000),
    ('DP022', 'DV002', '2025-04-13 10:00:00', 2, 50000),
    ('DP023', 'DV003', '2025-04-13 15:00:00', 1, 300000),
    ('DP024', 'DV004', '2025-04-14 09:00:00', 1, 500000),
    ('DP025', 'DV005', '2025-04-14 12:00:00', 3, 20000),
    ('DP026', 'DV001', '2025-04-13 08:00:00', 2, 100000),
    ('DP027', 'DV002', '2025-04-14 11:00:00', 1, 50000),
    ('DP028', 'DV003', '2025-04-15 16:00:00', 1, 300000),
    ('DP029', 'DV004', '2025-04-15 10:00:00', 1, 500000),
    ('DP030', 'DV005', '2025-04-16 13:00:00', 2, 20000),
    ('DP031', 'DV001', '2025-04-15 08:00:00', 1, 100000),
    ('DP032', 'DV002', '2025-04-16 10:00:00', 2, 50000),
    ('DP033', 'DV003', '2025-04-17 15:00:00', 1, 300000),
    ('DP034', 'DV004', '2025-04-17 09:00:00', 1, 500000),
    ('DP035', 'DV005', '2025-04-17 12:00:00', 3, 20000),
    ('DP036', 'DV001', '2025-04-17 08:00:00', 2, 100000),
    ('DP037', 'DV002', '2025-04-18 11:00:00', 1, 50000),
    ('DP038', 'DV003', '2025-04-18 16:00:00', 1, 300000),
    ('DP039', 'DV004', '2025-04-19 10:00:00', 1, 500000),
    ('DP040', 'DV005', '2025-04-19 13:00:00', 2, 20000),
    ('DP041', 'DV001', '2025-04-18 08:00:00', 1, 100000),
    ('DP042', 'DV002', '2025-04-19 10:00:00', 2, 50000),
    ('DP043', 'DV003', '2025-04-20 15:00:00', 1, 300000),
    ('DP044', 'DV004', '2025-04-20 09:00:00', 1, 500000),
    ('DP045', 'DV005', '2025-04-21 12:00:00', 3, 20000);


INSERT INTO `HOADON` (`maHD`, `maCTT`, `tienP`, `tienDV`, `giamGia`, `phuThu`, `tongTien`, `ngayThanhToan`, `hinhThucThanhToan`, `xuLy`) 
VALUES
    ('HD001', 'DP001', 1000000, 100000, 0, 0, 1100000, '2025-04-07 12:00:00', 'Tiền mặt', 1),
    ('HD002', 'DP002', 2400000, 100000, 0, 0, 2500000, '2025-04-08 12:00:00', 'Thẻ tín dụng', 1),
    ('HD003', 'DP003', 6000000, 300000, 50000, 0, 6250000, '2025-04-09 12:00:00', 'Chuyển khoản', 1),
    ('HD004', 'DP004', 2400000, 500000, 0, 100000, 3000000, '2025-04-08 12:00:00', 'Tiền mặt', 1),
    ('HD005', 'DP005', 8000000, 60000, 0, 0, 8060000, '2025-04-10 12:00:00', 'Thẻ tín dụng', 1),
    ('HD006', 'DP006', 1000000, 200000, 0, 0, 1200000, '2025-04-09 12:00:00', 'Tiền mặt', 1),
    ('HD007', 'DP007', 2400000, 50000, 0, 0, 2450000, '2025-04-10 12:00:00', 'Chuyển khoản', 1),
    ('HD008', 'DP008', 6000000, 300000, 0, 0, 6300000, '2025-04-11 12:00:00', 'Thẻ tín dụng', 1),
    ('HD009', 'DP009', 2400000, 500000, 0, 0, 2900000, '2025-04-10 12:00:00', 'Tiền mặt', 1),
    ('HD010', 'DP010', 8000000, 40000, 0, 0, 8040000, '2025-04-12 12:00:00', 'Chuyển khoản', 1),
    ('HD011', 'DP011', 1000000, 100000, 0, 0, 1100000, '2025-04-10 12:00:00', 'Tiền mặt', 1),
    ('HD012', 'DP012', 1600000, 100000, 0, 0, 1700000, '2025-04-11 12:00:00', 'Thẻ tín dụng', 1),
    ('HD013', 'DP013', 4500000, 300000, 50000, 0, 4750000, '2025-04-12 12:00:00', 'Chuyển khoản', 1),
    ('HD014', 'DP014', 3600000, 500000, 0, 100000, 4200000, '2025-04-13 12:00:00', 'Tiền mặt', 1),
    ('HD015', 'DP015', 6000000, 60000, 0, 0, 6060000, '2025-04-13 12:00:00', 'Thẻ tín dụng', 1),
    ('HD016', 'DP016', 1000000, 200000, 0, 0, 1200000, '2025-04-12 12:00:00', 'Tiền mặt', 1),
    ('HD017', 'DP017', 3200000, 50000, 0, 0, 3250000, '2025-04-14 12:00:00', 'Chuyển khoản', 1),
    ('HD018', 'DP018', 4500000, 300000, 0, 0, 4800000, '2025-04-14 12:00:00', 'Thẻ tín dụng', 1),
    ('HD019', 'DP019', 2400000, 500000, 0, 0, 2900000, '2025-04-13 12:00:00', 'Tiền mặt', 1),
    ('HD020', 'DP020', 8000000, 40000, 0, 0, 8040000, '2025-04-15 12:00:00', 'Chuyển khoản', 1),
    ('HD021', 'DP021', 1000000, 100000, 0, 0, 1100000, '2025-04-14 12:00:00', 'Tiền mặt', 1),
    ('HD022', 'DP022', 2400000, 100000, 0, 0, 2500000, '2025-04-15 12:00:00', 'Thẻ tín dụng', 1),
    ('HD023', 'DP023', 6000000, 300000, 50000, 0, 6250000, '2025-04-16 12:00:00', 'Chuyển khoản', 1),
    ('HD024', 'DP024', 2400000, 500000, 0, 100000, 3000000, '2025-04-15 12:00:00', 'Tiền mặt', 1),
    ('HD025', 'DP025', 8000000, 60000, 0, 0, 8060000, '2025-04-17 12:00:00', 'Thẻ tín dụng', 1),
    ('HD026', 'DP026', 1000000, 200000, 0, 0, 1200000, '2025-04-15 12:00:00', 'Tiền mặt', 1),
    ('HD027', 'DP027', 1600000, 50000, 0, 0, 1650000, '2025-04-16 12:00:00', 'Chuyển khoản', 1),
    ('HD028', 'DP028', 4500000, 300000, 0, 0, 4800000, '2025-04-17 12:00:00', 'Thẻ tín dụng', 1),
    ('HD029', 'DP029', 2400000, 500000, 0, 0, 2900000, '2025-04-16 12:00:00', 'Tiền mặt', 1),
    ('HD030', 'DP030', 6000000, 40000, 0, 0, 6040000, '2025-04-18 12:00:00', 'Chuyển khoản', 1),
    ('HD031', 'DP031', 1000000, 100000, 0, 0, 1100000, '2025-04-17 12:00:00', 'Tiền mặt', 1),
    ('HD032', 'DP032', 3200000, 100000, 0, 0, 3300000, '2025-04-19 12:00:00', 'Thẻ tín dụng', 1),
    ('HD033', 'DP033', 4500000, 300000, 50000, 0, 4750000, '2025-04-19 12:00:00', 'Chuyển khoản', 1),
    ('HD034', 'DP034', 2400000, 500000, 0, 100000, 3000000, '2025-04-18 12:00:00', 'Tiền mặt', 1),
    ('HD035', 'DP035', 8000000, 60000, 0, 0, 8060000, '2025-04-20 12:00:00', 'Thẻ tín dụng', 1),
    ('HD036', 'DP036', 1000000, 200000, 0, 0, 1200000, '2025-04-19 12:00:00', 'Tiền mặt', 1),
    ('HD037', 'DP037', 2400000, 50000, 0, 0, 2450000, '2025-04-20 12:00:00', 'Chuyển khoản', 1),
    ('HD038', 'DP038', 6000000, 300000, 0, 0, 6300000, '2025-04-21 12:00:00', 'Thẻ tín dụng', 1),
    ('HD039', 'DP039', 2400000, 500000, 0, 0, 2900000, '2025-04-20 12:00:00', 'Tiền mặt', 1),
    ('HD040', 'DP040', 8000000, 40000, 0, 0, 8040000, '2025-04-22 12:00:00', 'Chuyển khoản', 1),
    ('HD041', 'DP041', 1000000, 100000, 0, 0, 1100000, '2025-04-20 12:00:00', 'Tiền mặt', 1),
    ('HD042', 'DP042', 1600000, 100000, 0, 0, 1700000, '2025-04-21 12:00:00', 'Thẻ tín dụng', 1),
    ('HD043', 'DP043', 4500000, 300000, 50000, 0, 4750000, '2025-04-22 12:00:00', 'Chuyển khoản', 1),
    ('HD044', 'DP044', 3600000, 500000, 0, 100000, 4200000, '2025-04-23 12:00:00', 'Tiền mặt', 1),
    ('HD045', 'DP045', 6000000, 60000, 0, 0, 6060000, '2025-04-23 12:00:00', 'Thẻ tín dụng', 1);


INSERT INTO `PHIEUNHAP` (`maPN`, `maNV`, `ngayLap`, `tinhTrangXuLy`) 
VALUES
    ('PN001', 1, '2025-03-01 09:00:00', 1),
    ('PN002', 2, '2025-03-02 14:00:00', 1),
    ('PN003', 3, '2025-03-03 10:30:00', 1),
    ('PN004', 1, '2025-03-04 15:00:00', 0),
    ('PN005', 2, '2025-03-05 11:00:00', 1);


INSERT INTO `CHITIETNHAP` (`maPN`, `maDV`, `giaDVNhap`, `soLuong`) 
VALUES
    ('PN001', 'DV001', 80000, 20),
    ('PN001', 'DV002', 40000, 15),
    ('PN001', 'DV003', 250000, 10),
    ('PN001', 'DV004', 450000, 5),
    ('PN001', 'DV005', 15000, 50),
    ('PN002', 'DV001', 80000, 25),
    ('PN002', 'DV002', 40000, 20),
    ('PN002', 'DV003', 250000, 8),
    ('PN002', 'DV004', 450000, 6),
    ('PN002', 'DV005', 15000, 40),
    ('PN003', 'DV001', 80000, 15),
    ('PN003', 'DV002', 40000, 10),
    ('PN003', 'DV003', 250000, 12),
    ('PN003', 'DV004', 450000, 7),
    ('PN003', 'DV005', 15000, 30),
    ('PN004', 'DV001', 80000, 10),
    ('PN004', 'DV002', 40000, 25),
    ('PN004', 'DV003', 250000, 5),
    ('PN004', 'DV004', 450000, 8),
    ('PN004', 'DV005', 15000, 20),
    ('PN005', 'DV001', 80000, 30),
    ('PN005', 'DV002', 40000, 15),
    ('PN005', 'DV003', 250000, 10),
    ('PN005', 'DV004', 450000, 5),
    ('PN005', 'DV005', 15000, 25),
    ('PN001', 'DV001', 80000, 20),
    ('PN001', 'DV002', 40000, 10),
    ('PN001', 'DV003', 250000, 15),
    ('PN001', 'DV004', 450000, 6),
    ('PN001', 'DV005', 15000, 35),
    ('PN002', 'DV001', 80000, 25),
    ('PN002', 'DV002', 40000, 20),
    ('PN002', 'DV003', 250000, 8),
    ('PN002', 'DV004', 450000, 7),
    ('PN002', 'DV005', 15000, 40),
    ('PN003', 'DV001', 80000, 15),
    ('PN003', 'DV002', 40000, 10),
    ('PN003', 'DV003', 250000, 12),
    ('PN003', 'DV004', 450000, 5),
    ('PN003', 'DV005', 15000, 30),
    ('PN004', 'DV001', 80000, 10),
    ('PN004', 'DV002', 40000, 25),
    ('PN004', 'DV003', 250000, 5),
    ('PN004', 'DV004', 450000, 8),
    ('PN004', 'DV005', 15000, 20);

INSERT INTO `TIENICH` (`maTI`, `tenTI`, `soLuong`, `xuLy`) 
VALUES
    ('TI001', 'Wifi', 100, 1),
    ('TI002', 'TV', 50, 1),
    ('TI003', 'Máy lạnh', 40, 1),
    ('TI004', 'Tủ lạnh', 30, 1),
    ('TI005', 'Bình nóng lạnh', 60, 1);

INSERT INTO `CHITIETTIENICH` (`maP`, `maTI`, `soLuong`) 
VALUES
    ('P101', 'TI001', 1), ('P101', 'TI002', 1),
    ('P102', 'TI001', 1), ('P102', 'TI003', 1),
    ('P103', 'TI001', 1), ('P103', 'TI002', 1),
    ('P104', 'TI001', 1), ('P104', 'TI003', 1),
    ('P105', 'TI001', 1), ('P105', 'TI004', 1),
    ('P106', 'TI001', 1), ('P106', 'TI002', 1),
    ('P107', 'TI001', 1), ('P107', 'TI003', 1),
    ('P108', 'TI001', 1), ('P108', 'TI004', 1),
    ('P109', 'TI001', 1), ('P109', 'TI005', 1),
    ('P110', 'TI001', 1), ('P110', 'TI002', 1),
    ('P201', 'TI001', 1), ('P201', 'TI003', 1),
    ('P202', 'TI001', 1), ('P202', 'TI004', 1),
    ('P203', 'TI001', 1), ('P203', 'TI002', 1),
    ('P204', 'TI001', 1), ('P204', 'TI005', 1),
    ('P205', 'TI001', 1), ('P205', 'TI003', 1),
    ('P206', 'TI001', 1), ('P206', 'TI004', 1),
    ('P207', 'TI001', 1), ('P207', 'TI005', 1),
    ('P208', 'TI001', 1), ('P208', 'TI002', 1),
    ('P209', 'TI001', 1), ('P209', 'TI003', 1),
    ('P210', 'TI001', 1), ('P210', 'TI004', 1),
    ('P301', 'TI001', 1), ('P301', 'TI005', 1),
    ('P302', 'TI001', 1), ('P302', 'TI002', 1),
    ('P303', 'TI001', 1), ('P303', 'TI003', 1),
    ('P304', 'TI001', 1), ('P304', 'TI004', 1),
    ('P305', 'TI001', 1), ('P305', 'TI005', 1),
    ('P010', 'TI001', 1), ('P010', 'TI003', 1), ('P010', 'TI004', 1), ('P010', 'TI005', 1);





