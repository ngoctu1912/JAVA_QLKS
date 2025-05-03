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

CREATE TABLE `TAIKHOANKH` (
    `MKH` INT(11) NOT NULL COMMENT 'Mã khách hàng',
    `MK` VARCHAR(255) NOT NULL COMMENT 'Mật khẩu',
    `TDN` VARCHAR(255) NOT NULL UNIQUE COMMENT 'Tên đăng nhập',
    `MNQ` INT(11) NOT NULL COMMENT 'Mã nhóm quyền',
    `TT` INT(11) NOT NULL DEFAULT 1 COMMENT 'Trạng thái',
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
    (13, 'Quản lý tài khoản khách hàng', 1),
    (14, 'Quản lý khuyến mãi', 1),
    (15, 'Quản lý thống kê', 1);

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
    (1, 15, 'create'), (1, 15, 'delete'), (1, 15, 'update'), (1, 15, 'view'),
    
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
    (4, 3, 'update'), (4, 3, 'view'), 
    (4, 8, 'view');
    
INSERT INTO `NHANVIEN` (`MNV`, `HOTEN`, `GIOITINH`, `NGAYSINH`, `SDT`, `EMAIL`, `TT`)
VALUES
	(1, 'Nguyễn Thị Ngọc Tú', 0, '2004-05-15', '0912345678', 'ngoctu@gmail.com', 1),
    (2, 'Võ Thị Thu Luyện', 0, '2004-08-22', '0987654321', 'thuluyn@gmail.com', 1),
    (3, 'Trần Thị Xuân Thanh', 0, '2005-12-01', '0935123456', 'xuanthanh@gmail.com', 1),
    (4, 'Nguyễn Huỳnh Yến Linh', 0, '2005-03-10', '0908765432', 'phamthidung@gmail.com', 1),
    (5, 'Lê Anh Khoa', 1, '2000-07-25', '0971234567', 'anhkhoa@gmail.com', 0),
	(6, 'Phạm Minh Tuấn', 1, '1998-04-12', '0923456789', 'minhtuan@gmail.com', 1),
	(7, 'Hoàng Thị Mai', 0, '2002-09-30', '0945678901', 'thimai@gmail.com', 1),
	(8, 'Đặng Văn Hùng', 1, '2001-11-05', '0916789012', 'vanhung@gmail.com', 0),
	(9, 'Lê Thị Hồng Nhung', 0, '2003-02-14', '0937890123', 'hongnhung@gmail.com', 1),
	(10, 'Trần Văn Nam', 1, '1999-06-20', '0909012345', 'vannam@gmail.com', 1),
	(11, 'Nguyễn Thị Lan', 0, '2004-01-25', '0970123456', 'thilan@gmail.com', 1),
	(12, 'Vũ Minh Đức', 1, '2000-08-15', '0951234567', 'minhduc@gmail.com', 0),
	(13, 'Bùi Thị Thanh Tâm', 0, '2005-03-03', '0942345678', 'thanhtam@gmail.com', 1),
	(14, 'Phan Văn Hòa', 1, '1997-12-10', '0933456789', 'vanhoa@gmail.com', 1),
	(15, 'Đỗ Thị Kim Ngân', 0, '2002-07-07', '0914567890', 'kimngan@gmail.com', 1),
	(16, 'Lý Văn Long', 1, '2001-05-18', '0925678901', 'vanlong@gmail.com', 0),
	(17, 'Ngô Thị Minh Thư', 0, '2003-10-22', '0906789012', 'minhthu@gmail.com', 1),
	(18, 'Hà Văn Thành', 1, '1999-09-09', '0977890123', 'vanthanh@gmail.com', 1),
	(19, 'Trương Thị Cẩm Tú', 0, '2004-04-01', '0949012345', 'camtu@gmail.com', 1),
	(20, 'Nguyễn Văn Quốc', 1, '2000-02-28', '0930123456', 'vanquoc@gmail.com', 0),
	(21, 'Lê Thị Ánh Tuyết', 0, '2005-06-15', '0911234567', 'anhtuyet@gmail.com', 1),
	(22, 'Võ Văn Bình', 1, '1998-03-20', '0952345678', 'vanbinh@gmail.com', 1),
	(23, 'Phạm Thị Ngọc Ánh', 0, '2002-11-11', '0923456789', 'ngocanh@gmail.com', 1),
	(24, 'Hoàng Văn Đạt', 1, '2001-01-30', '0904567890', 'vandat@gmail.com', 0),
	(25, 'Đặng Thị Thu Hà', 0, '2003-08-05', '0975678901', 'thuha@gmail.com', 1),
	(26, 'Nguyễn Văn Hảo', 1, '1999-04-17', '0946789012', 'vanhao@gmail.com', 1),
	(27, 'Trần Thị Bích Ngọc', 0, '2004-12-12', '0937890123', 'bichngoc@gmail.com', 1),
	(28, 'Lê Văn Khánh', 1, '2000-10-08', '0919012345', 'vankhanh@gmail.com', 0),
	(29, 'Vũ Thị Thanh Mai', 0, '2005-05-25', '0950123456', 'thanhmai@gmail.com', 1),
	(30, 'Bùi Văn Tùng', 1, '1997-07-14', '0921234567', 'vantung@gmail.com', 1),
	(31, 'Phan Thị Kim Chi', 0, '2002-02-09', '0902345678', 'kimchi@gmail.com', 1),
	(32, 'Đỗ Văn Minh', 1, '2001-06-26', '0973456789', 'vanminh@gmail.com', 0),
	(33, 'Lý Thị Hồng Phúc', 0, '2003-09-19', '0944567890', 'hongphuc@gmail.com', 1),
	(34, 'Ngô Văn An', 1, '1999-05-04', '0935678901', 'vanan@gmail.com', 1),
	(35, 'Hà Thị Minh Châu', 0, '2004-03-15', '0916789012', 'minhchau@gmail.com', 1),
	(36, 'Trương Văn Phong', 1, '2000-08-22', '0957890123', 'vanphong@gmail.com', 0),
	(37, 'Nguyễn Thị Diệu Linh', 0, '2005-01-10', '0929012345', 'dieulinh@gmail.com', 1),
	(38, 'Lê Văn Quang', 1, '1998-11-27', '0900123456', 'vanquang@gmail.com', 1),
	(39, 'Võ Thị Thùy Trang', 0, '2002-04-03', '0971234567', 'thuytrang@gmail.com', 1),
	(40, 'Phạm Văn Huy', 1, '2001-07-16', '0942345678', 'vanhuy@gmail.com', 0),
    (41, 'Nguyễn Thị Kim Oanh', 0, '2000-07-19', '0913456790', 'kimoanh.nv@gmail.com', 1),
    (42, 'Trần Văn Quốc Anh', 1, '1999-03-25', '0986543223', 'quocanh.nv@gmail.com', 1),
    (43, 'Lê Thị Hồng Phấn', 0, '2002-11-08', '0936789023', 'hongphan.nv@gmail.com', 1),
    (44, 'Phạm Văn Minh Tuấn', 1, '2001-05-14', '0909876554', 'minhtuan.nv2@gmail.com', 1),
    (45, 'Hoàng Thị Thu Trang', 0, '2003-09-27', '0972345689', 'thutrang.nv@gmail.com', 1);

-- Dữ liệu cho tài khoản 
INSERT INTO TAIKHOAN (`MNV`, `MK`, `TDN`, `MNQ`, `TT`)
VALUES 
	(1, '$2a$12$60DFtqG98rqeJdqnuAIHouOjuQH3Pwn.1O87WwDdF1agFTzuuavOS', 'admin', 1, 1), 
    (2, '$2a$12$m6iLa3/gvkQsAziK3nB9.ew7QOv3uCMxm05UXpQiesoWhdMTFlN7.', 'NV1', 2, 1), 
    (3, '$2a$12$wGNwwp3.X89/BWOST.mdhORV2HQ5NjtQaZJGF4Ow6pqVuBEz8vJ4S', 'NV2', 3, 1),
    (4, '$2a$12$zzPycqmE/RU70offmzzKlOKGo8FkeY6m9letWzJnZcIgb2MIfIaxO', 'NV3', 2, 1),
    (5, '$2a$12$KbTu7eAJLYe3z.f.3HkkFOqyM5MMxLMEXTukfTu7ar/leQc5bEnE2', 'NV4', 2, 1),
    (6, '$2a$12$69T3j8g5Fcy8x5Rc8WgIz.tLOiDjReGsoMMStU6pwF1DC6J3UZvlu', 'NV5', 2, 1),
    (7, '$2a$12$KXrWMh.2hxfa.3sunTTUfeO40tGeBurt.1qZJRRzKG2K9Swd/PCh6', 'NV6', 2, 1),
    (8, '$2a$12$l.MI5sgHO3KFk1lL2JE4V.qJRR9m03NhQ26YNA1UxmvxK7B48iDO.', 'NV7', 2, 1),
    (9, '$2a$12$JZjpevlnDGUzijlQq9.XoOtxIY5u5pbjM1rlUmJl4HnDnADQz7uue', 'NV8', 2, 1),
    (10, '$2a$12$vt.JA4aiNvXZG2AoCyuzd.VZR.rYzcEqQhbP0nHHontb9tSuCcCuC', 'NV9', 3, 1),
    (11, '$2a$12$6woN9XXPHEFJALTVDb7NkOtiHXv2GYG7HI02TXcXAQwlvLpw1hEAG', 'NV10', 3, 1),
    (12, '$2a$12$jDwZL/S3YYf8OTMuGtkE1usHwelp4piBby.LaZq6IwTBFWdKl/HyK', 'NV11', 3, 1),
    (13, '$2a$12$3tAzh9tPSKXaqrwrRLSBa.HyXGHwl/IjuUHfQG4xSHu5yKQxCbP/2', 'NV12', 3, 1),
    (14, '$2a$12$z00AnaWVH5ClY.YtkpWsIu9ygmgTjwqMdjHW./Q57taFKb0nwcy22', 'NV13', 3, 1),
    (15, '$2a$12$/ScsYSWUvVF3ITYCEiqWsOlHdbRPKLkefGVeIU2bKeerflTytzrji', 'NV14', 3, 1),
    (16, '$2a$12$MGidZYBs0tE19M9ktSWlQeeTozBWdpCrZ3pcMl5szMKNqKA4GDA26', 'NV15', 3, 1),
    (17, '$2a$12$fYKIzgDOvor141vjbQEWDuKmlLSm45sL/jvQHLUtZzX9q8/ntxFsW', 'NV16', 2, 1),
    (18, '$2a$12$oVPJ.FeNVZzX0lZHZZiOYut4IDt3zxJLVacsTEd5FkkGJ96wBkB1a', 'NV17', 2, 1),
    (19, '$2a$12$K95dfs3bYRkp2LwPNhc1F.EllUCTq52n29f.6o5wfSmkfJ8YlZwHK', 'NV18', 2, 1),
    (20, '$2a$12$5EVjvNywLQFJYqpxuyRCdunvbTkpLSD6.MnUY3QnkoWmSEqfwRDzi', 'NV19', 2, 1),
    (21, '$2a$12$MjD/f0Ww/.8DoZXanTR/f.z.ylDMCWmhhtt.4IpVM.enn87PM5oQu', 'NV20', 2, 1),
    (22, '$2a$12$oN0eBSjpCw3kDwTaouN0QOSwUgm8Tj94ZOX7nO.A625dbVozZW01.', 'NV21', 2, 1),
    (23, '$2a$12$X/973bzsveHpGhcbbvIN3u3RTc1t2FHtDu1pXqgTZuTMRsRPZ30vC', 'NV22', 3, 1),
    (24, '$2a$12$WTk9HAOstlVMipvb9S1eNeMgRFC15zogO4PH40KfKA7oKPYlyTq/S', 'NV23', 3, 1),
    (25, '$2a$12$1IzmMhOST54htQn4ion0COnY9UsmNW446inaPcwrII.X8sAKJCSn6', 'NV24', 3, 1),
    (26, '$2a$12$Hq/i0fTpwl7Tj2yOyy3lIuU/aN5Bmc/dS1aQ13nMWg3dJCh05V/vy', 'NV25', 3, 1),
    (27, '$2a$12$D92gsFDcZRBgsys8Ksx8ROpojurifT/7zlkFDagcvDiVt77pGAKTq', 'NV26', 3, 1),
    (28, '$2a$12$xamp7S6nv6K7wgTqgjFRPedGMreWD9TPnNOEyU0gxVUblra8YEzKq', 'NV27', 3, 1),
    (29, '$2a$12$IccsqaAq.dn3N1kccSlvtOoNF2TcFkbY09rgSFxLK4ICFoQ2DFoXO', 'NV28', 3, 1),
    (30, '$2a$12$6.BLoq9hNB.UfjO9nltB4OmBMo06StAZWF4j/HfmEK6uHkkcqdV/S', 'NV29', 3, 1),
    (31, '$2a$12$HJXcvKVlES1hYxy9LTMfjeWOa1qemRcMEaoid8Hqd366fBVKNut0y', 'NV30', 3, 1),
    (32, '$2a$12$ugLT5wgKCWdvWZLb293Z1OsgOByjIWyScomlGHhNSNrrT74iECr0C', 'NV31', 3, 1),
    (33, '$2a$12$0xUJOqN/OUXpJ3Hq5b2EX.Dn5EbjFVVFko5uIeNWyFWfxDVGfN/wO', 'NV32', 2, 1),
    (34, '$2a$12$fScpC.0VQSPevq/AMO29cOVJ7OevsphB9BaqlkAIALTXllO1UZQUm', 'NV33', 2, 1),
    (35, '$2a$12$4Nmg78/yFxkTXtZBcNPBu.IsCDX/1SRbDHDHdxI3hf48x5DgJYdV2', 'NV34', 2, 1),
    (36, '$2a$12$DZInxiM3v.hoXchBNQ0YdeO/hDkdFWtDEjVhulyPMqYbZY1IVBiWu', 'NV35', 2, 1),
    (37, '$2a$12$5YBiAxFYYKdWZKDxR49S3u1F6HTIbmhBkH3QR5UItMSw5u.0rg1yy', 'NV36', 2, 1),
    (38, '$2a$12$FXjoi3Zf4w.6jObmtNQojO8qX0BgqnsrmJeTvlSGZ2IKYfM5d0kC6', 'NV37', 2, 1),
    (39, '$2a$12$7BlxME1VXtkLSNjpQgBmO.3iFDAkE9B4KugQT3fznNrepbCBHmsdS', 'NV38', 2, 1),
    (40, '$2a$12$J64qzQRmUotfzH7bI2DvjeNvmDym8SsZ/JtEE1Zxwi8GHUMmlaNqC', 'NV39', 2, 1),
    (41, '$2a$12$oYcHPWrUqJf2HMcxW4QloeyltBxafsSeCCWczAYtZT568H/Afrb1O', 'NV40', 2, 1);


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
    (40, 'Hoàng Thị Minh', 0, 123456828, 'Gia Lai', '0978901234', 'hoangthiminh@gmail.com', 1, '1995-07-12'),
    (41, 'Nguyễn Thị Minh Thư', 0, 123456829, 'Bến Tre', '0912345689', 'minhthu.kh@gmail.com', 1, '1992-06-17'),
    (42, 'Trần Văn Hoàng', 1, 123456830, 'Lâm Đồng', '0987654332', 'vanhoang.kh@gmail.com', 1, '1987-09-23'),
    (43, 'Lê Thị Ngọc Anh', 0, 123456831, 'Quảng Ngãi', '0935123467', 'ngocanh.kh@gmail.com', 1, '1995-04-12'),
    (44, 'Phạm Văn Tín', 1, 123456832, 'Đắk Lắk', '0908765443', 'vantin.kh@gmail.com', 1, '1989-11-30'),
    (45, 'Hoàng Thị Thanh Hương', 0, 123456833, 'An Giang', '0971234578', 'thanhhuong.kh@gmail.com', 1, '1993-02-08');
    
INSERT INTO `TAIKHOANKH` (`MKH`, `MK`, `TDN`, `MNQ`, `TT`)
VALUES
	(1, '$2a$12$So0CDASr5Z8ScjqEp.6yi.1BbQNjn33X7Gx3iVfRzAew66hbtuLm2', 'KH1', 4, 1),
    (2, '$2a$12$drrV2qCSmzBA91Z.jga99.buoWT4h5NpCfbeX.ryJsJxbGwhBcYOK', 'KH2', 4, 1),
    (3, '$2a$12$9FtN0zaBol8pzno1rmZe4uYS9OJv1MhiRRz.XVzTw1b9euFJXkkCO', 'KH3', 4, 1),
    (4, '$2a$12$JA44pCUWLKXS2Y5odn7jvuTIcw.fIqOrTk81MAzBbJW/vayhZJDyy', 'KH4', 4, 1),
    (5, '$2a$12$7PXr4a9JBRTAEKlbVgs0oOK1MV.74.y3seK532DIXJVCb22.bxcqm', 'KH5', 4, 1),
    (6, '$2a$12$6E4TbQ0vfATt7lpBJHNaAemYC4wmtIaozePrVBI0zYp00aWka75YC', 'KH6', 4, 1),
    (7, '$2a$12$5CHayOZJUgvMdghQlgzw6eK8/qqYtVwE7/zXf2BtPHFSx1ZQ3VJO6', 'KH7', 4, 1),
    (8, '$2a$12$Bg6MDfDobxDd/81WSPICYeIuVFCFCXCkE7LMqQbd3g9YMD8zhGxai', 'KH8', 4, 1),
    (9, '$2a$12$8RiW7xyvLbpRbMRAoGXG1ODDmtJU2fInHrjJO9MJVzoQZ8wJA.trG', 'KH9', 4, 1),
    (10, '$2a$12$64IugPhO/npWs7lHmzuOiOGzWjlHmSZ0PUNzLIuwZVuOw2UrJjMT2', 'KH10', 4, 1),
    (11, '$2a$12$urTXUgJdVAhX9AU5W1R6E.pBbRIXpfKBfKtGhz8Vg4ZhxyEmRNRge', 'KH11', 4, 1),
    (12, '$2a$12$urTXUgJdVAhX9AU5W1R6E.pBbRIXpfKBfKtGhz8Vg4ZhxyEmRNRge', 'KH12', 4, 1),
    (13, '$2a$12$TXwGmIG1EhWsMQhpIQyLQug1Uq8DnJyf4p3bwxkb3el3GURkqSWpm', 'KH13', 4, 1),
    (14, '$2a$12$mEjzAf578VKFFLsF2iiQtuCPqMxZcMCU8EEnfYANyveH5tc.1uhH6', 'KH14', 4, 1),
    (15, '$2a$12$rEtAJ6Muf6398bj/IWHgueqpMaEYPH3UUWA10KhW2EcSbSRrDYELu', 'KH15', 4, 1),
    (16, '$2a$12$mqQIqb2SKY3XJPA03VMji.IGxIY4C2iHkfRRP8t2sV9ScUsKmgYMa', 'KH16', 4, 1),
    (17, '$2a$12$LzXkvk70GpjrpWzKvZuKoeezABi192sFpkY6cm7wV6mb3MUg5WH4q', 'KH17', 4, 1),
    (18, '$2a$12$ZI0MfGhKvIhqhRtKX10bz.vbC6hL/9K22qGAax1LEe7ZF1Afz09/a', 'KH18', 4, 1),
    (19, '$2a$12$rRDfT74gbNX7igNsV273veo2rDx7EboDzZ0qBGU6Gu8toUWRWAAKu', 'KH19', 4, 1),
    (20, '$2a$12$oIJjje730qgkki6YCjd/7eaUKt/PbE.BwfEsXh.8slyqXLEOon8i.', 'KH20', 4, 1),
    (21, '$2a$12$/gzkuZ3bTFZsLWebCp4.peIli8IlWIezJ26FKiB8kBAX45KoV49ye', 'KH21', 4, 1),
    (22, '$2a$12$7SMQTn3h0N4eB5lr6WqXVe78dR4BFnyhzs0HSe7hASJL6oqcsb1uC', 'KH22', 4, 1),
    (23, '$2a$12$1zspFPKRTy3hNKLI2K07WOvvo.nCdn7EYY91XQvn3vTODseQpyd0G', 'KH23', 4, 1),
    (24, '$2a$12$j.HMBrzLoSSv7xyLSMl8C.0hK66dhG7DPQCSILgeXhOb1xwYKfoOS', 'KH24', 4, 1),
    (25, '$2a$12$P66fQ60.rjui6oVc.hUYwubW2ephKzX9rLd5KZiNBIrDOJPfwFmKy', 'KH25', 4, 1),
    (26, '$2a$12$mcnqc3UcaCPlFkmT38RBT.G7mlPXa/z872HBsIT68oL9DRUSjdzMK', 'KH26', 4, 1),
    (27, '$2a$12$c7wn9Gu.sEwDnpAlwXt6guIt8RYcBSsOS/K65UuIabE/v783JsqsW', 'KH27', 4, 1),
    (28, '$2a$12$phMzoVBlgaqGT9ahMbsSke1XbYkJ4p6UGofqVyRRNCWxICUlzG2bq', 'KH28', 4, 1),
    (29, '$2a$12$CbNGlmUTwCXwbnq068ifa.IpteJLRE4EuqySnNvYseKfeNz5L5HD.', 'KH29', 4, 1),
    (30, '$2a$12$ADvA8FgXz7qnYrolOnxSPeNHIhPpcWWlLXsQMMdStUClNEkG3FRyu', 'KH30', 4, 1),
    (31, '$2a$12$Yd0kvKPzeiSXrCztqhNGRe3mo.GBRWfIwQbrJllkE/3KuDmE.Zpri', 'KH31', 4, 1),
    (32, '$2a$12$Xl0/BY3cAdWD1oz0tXFOE.jWM8tVBGT5EwcAoyhdMhISuthZohPk6', 'KH32', 4, 1),
    (33, '$2a$12$eZHEWEwIw3gFP8CVBUb45eWA1eCagjXpleAHkUtODyVhmbrAECX2u', 'KH33', 4, 1),
    (34, '$2a$12$iZ4WBdyQsTKMu6rUI0EeYeL0pYLDcKGWeJDsXheaNHtCiqJAOxjTa', 'KH34', 4, 1),
    (35, '$2a$12$lHIQyffVXvEq4Mpwfh0cIOoNYWIzZNkg0Ykgmi.Q1a8xQK/qLpkIG', 'KH35', 4, 1),
    (36, '$2a$12$VYeoNph8wEZtouey6fh3i.TxoFa4aBiXkP/XCnKc31aM8kv9hC5TC', 'KH36', 4, 1),
    (37, '$2a$12$fsrXnVDvfrKV1ljQJjdcW.ltuFKTrVF5p9rlj.J3dVikTBnIDN6z6', 'KH37', 4, 1),
    (38, '$2a$12$IavViUJpp4TtJVp8wu6vNem2QXYtNaAdYdUH2lSrzvuFPjrXWIjee', 'KH38', 4, 1),
    (39, '$2a$12$7vnapATrtXK4qvvmuSxUh.4B5u0fvdYblWBcPZt..WtVgVqSo9cqu', 'KH39', 4, 1),
    (40, '$2a$12$ceoeLU0v/C3tQ8p.XnqiUeYlktOODjum4Exooy65MwlXyVJ898.5a', 'KH40', 4, 1);

INSERT INTO `PHONG` (`maP`, `tenP`, `loaiP`, `hinhAnh`, `giaP`, `chiTietLoaiPhong`, `tinhTrang`) 
VALUES
    ('P101', 'Phòng 101', 'Đơn', './images/P101.jpg', 500000, '1 giường đơn', 0),
    ('P102', 'Phòng 102', 'Đơn', './images/P102.jpg', 500000, '1 giường đơn', 0),
    ('P103', 'Phòng 103', 'Đơn', './images/P103.jpg', 500000, '1 giường đơn', 1),
    ('P104', 'Phòng 104', 'Đơn', './images/P104.jpg', 500000, '1 giường đơn', 1),
    ('P105', 'Phòng 105', 'Đơn', './images/P105.jpg', 500000, '1 giường đơn', 0),
    ('P106', 'Phòng 106', 'Đơn', './images/P106.jpg', 500000, '1 giường đơn', 1),
    ('P107', 'Phòng 107', 'Đơn', './images/P107.jpg', 500000, '1 giường đơn', 1),
    ('P108', 'Phòng 108', 'Đơn', './images/P108.jpg', 500000, '1 giường đơn', 1),
    ('P109', 'Phòng 109', 'Đơn', './images/P109.jpg', 500000, '1 giường đơn', 0),
    ('P110', 'Phòng 110', 'Đơn', './images/P110.jpg', 500000, '1 giường đơn', 1),
    ('P201', 'Phòng 201', 'Đôi', './images/P201.jpg', 800000, '1 giường đôi', 1),
    ('P202', 'Phòng 202', 'Đôi', './images/P202.jpg', 800000, '1 giường đôi', 0),
    ('P203', 'Phòng 203', 'Đôi', './images/P203.jpg', 800000, '1 giường đôi', 1),
    ('P204', 'Phòng 204', 'Đôi', './images/P204.jpg', 800000, '1 giường đôi', 0),
    ('P205', 'Phòng 205', 'Đôi', './images/P205.jpg', 800000, '1 giường đôi', 1),
    ('P206', 'Phòng 206', 'Gia đình', './images/P206.jpg', 1200000, '2 giường đôi', 0),
    ('P207', 'Phòng 207', 'Gia đình', './images/P207.jpg', 1200000, '2 giường đôi', 0),
    ('P208', 'Phòng 208', 'Gia đình', './images/P208.jpg', 1200000, '2 giường đôi', 1),
    ('P209', 'Phòng 209', 'Gia đình', './images/P209.jpg', 1200000, '2 giường đôi', 0),
    ('P210', 'Phòng 210', 'Gia đình', './images/P210.jpg', 1200000, '2 giường đôi', 1),
    ('P301', 'Phòng 301', 'VIP', './images/P301.jpg', 1500000, '1 giường king', 1),
    ('P302', 'Phòng 302', 'VIP', './images/P302.jpg', 1500000, '1 giường king', 0),
    ('P303', 'Phòng 303', 'VIP', './images/P303.jpg', 1500000, '1 giường king', 1),
    ('P304', 'Phòng 304', 'VIP', './images/P304.jpg', 1500000, '1 giường king', 0),
    ('P305', 'Phòng 305', 'VIP', './images/P305.jpg', 1500000, '1 giường king', 0),
    ('P306', 'Phòng 306', 'VIP', './images/P306.jpg', 1500000, '1 giường king', 1),
    ('P307', 'Phòng 307', 'VIP', './images/P307.jpg', 1500000, '1 giường king', 0),
    ('P308', 'Phòng 308', 'VIP', './images/P308.jpg', 1500000, '1 giường king', 1),
    ('P309', 'Phòng 309', 'VIP', './images/P309.jpg', 1500000, '1 giường king', 0),
    ('P310', 'Phòng 310', 'VIP', './images/P310.jpg', 1500000, '1 giường king', 1),
    ('P401', 'Phòng 401', 'Suite', 'images/P401.jpg', 2000000, '1 giường king + sofa', 0),
    ('P402', 'Phòng 402', 'Suite', 'images/P402.jpg', 2000000, '1 giường king + sofa', 0),
    ('P403', 'Phòng 403', 'Suite', 'images/P403.jpg', 2000000, '1 giường king + sofa', 1),
    ('P404', 'Phòng 404', 'Suite', 'images/P404.jpg', 2000000, '1 giường king + sofa', 1),
    ('P405', 'Phòng 405', 'Suite', 'images/P405.jpg', 2000000, '1 giường king + sofa', 0),
    ('P501', 'Phòng 501', 'Suite', 'images/P501.jpg', 2000000, '1 giường king + sofa', 1),
    ('P502', 'Phòng 502', 'Suite', 'images/P502.jpg', 2000000, '1 giường king + sofa', 0),
    ('P503', 'Phòng 503', 'Suite', 'images/P503.jpg', 2000000, '1 giường king + sofa', 1),
    ('P504', 'Phòng 504', 'Suite', 'images/P504.jpg', 2000000, '1 giường king + sofa', 0),
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
    ('PN005', 2, '2025-03-05 11:00:00', 1),
    ('PN006', 1, '2025-03-06 09:00:00', 1),
    ('PN007', 2, '2025-03-07 14:00:00', 1),
    ('PN008', 3, '2025-03-08 10:30:00', 1);


INSERT INTO `CHITIETNHAP` (`maPN`, `maDV`, `giaDVNhap`, `soLuong`) 
VALUES
    -- PN001
    ('PN001', 'DV001', 80000, 20),  -- Ăn sáng
    ('PN001', 'DV002', 40000, 15),  -- Giặt ủi
    ('PN001', 'DV003', 250000, 10), -- Massage
    ('PN001', 'DV004', 450000, 5),  -- Thuê xe
    ('PN001', 'DV005', 15000, 50),  -- Nước uống

    -- PN002
    ('PN002', 'DV001', 80000, 25),
    ('PN002', 'DV002', 40000, 20),
    ('PN002', 'DV003', 250000, 8),
    ('PN002', 'DV004', 450000, 6),
    ('PN002', 'DV005', 15000, 40),

    -- PN003
    ('PN003', 'DV001', 80000, 15),
    ('PN003', 'DV002', 40000, 10),
    ('PN003', 'DV003', 250000, 12),
    ('PN003', 'DV004', 450000, 7),
    ('PN003', 'DV005', 15000, 30),

    -- PN004
    ('PN004', 'DV001', 80000, 10),
    ('PN004', 'DV002', 40000, 25),
    ('PN004', 'DV003', 250000, 5),
    ('PN004', 'DV004', 450000, 8),
    ('PN004', 'DV005', 15000, 20),

    -- PN005
    ('PN005', 'DV001', 80000, 30),
    ('PN005', 'DV002', 40000, 15),
    ('PN005', 'DV003', 250000, 10),
    ('PN005', 'DV004', 450000, 5),
    ('PN005', 'DV005', 15000, 25),

    -- Thêm các phiếu nhập mới PN006
    ('PN006', 'DV001', 80000, 20),
    ('PN006', 'DV002', 40000, 10),
    ('PN006', 'DV003', 250000, 15),
    ('PN006', 'DV004', 450000, 6),
    ('PN006', 'DV005', 15000, 35),

    -- PN007
    ('PN007', 'DV001', 80000, 25),
    ('PN007', 'DV002', 40000, 20),
    ('PN007', 'DV003', 250000, 8),
    ('PN007', 'DV004', 450000, 7),
    ('PN007', 'DV005', 15000, 40),

    -- PN008
    ('PN008', 'DV001', 80000, 15),
    ('PN008', 'DV002', 40000, 10),
    ('PN008', 'DV003', 250000, 12),
    ('PN008', 'DV004', 450000, 5),
    ('PN008', 'DV005', 15000, 30);

INSERT INTO `TIENICH` (`maTI`, `tenTI`, `soLuong`, `xuLy`) 
VALUES
    ('TI001', 'Wifi', 100, 1),
    ('TI002', 'TV', 50, 1),
    ('TI003', 'Máy lạnh', 40, 1),
    ('TI004', 'Tủ lạnh', 30, 1),
    ('TI005', 'Bình nóng lạnh', 60, 1);

INSERT INTO `CHITIETTIENICH` (`maP`, `maTI`, `soLuong`) 
VALUES
    -- Phòng đơn (P101 - P110)
    ('P101', 'TI001', 1), ('P101', 'TI002', 1), -- Wifi, TV
    ('P102', 'TI001', 1), ('P102', 'TI003', 1), -- Wifi, Máy lạnh
    ('P103', 'TI001', 1), ('P103', 'TI004', 1), -- Wifi, Tủ lạnh
    ('P104', 'TI001', 1), ('P104', 'TI005', 1), -- Wifi, Bình nóng lạnh
    ('P105', 'TI001', 1), ('P105', 'TI002', 1), ('P105', 'TI003', 1), -- Wifi, TV, Máy lạnh
    ('P106', 'TI001', 1), ('P106', 'TI004', 1), -- Wifi, Tủ lạnh
    ('P107', 'TI001', 1), ('P107', 'TI005', 1), -- Wifi, Bình nóng lạnh
    ('P108', 'TI001', 1), ('P108', 'TI002', 1), -- Wifi, TV
    ('P109', 'TI001', 1), ('P109', 'TI003', 1), -- Wifi, Máy lạnh
    ('P110', 'TI001', 1), ('P110', 'TI004', 1), -- Wifi, Tủ lạnh

    -- Phòng đôi (P201 - P205)
    ('P201', 'TI001', 1), ('P201', 'TI003', 1), ('P201', 'TI005', 1), -- Wifi, Máy lạnh, Bình nóng lạnh
    ('P202', 'TI001', 1), ('P202', 'TI002', 1), ('P202', 'TI004', 1), -- Wifi, TV, Tủ lạnh
    ('P203', 'TI001', 1), ('P203', 'TI003', 1), -- Wifi, Máy lạnh
    ('P204', 'TI001', 1), ('P204', 'TI005', 1), -- Wifi, Bình nóng lạnh
    ('P205', 'TI001', 1), ('P205', 'TI002', 1), ('P205', 'TI004', 1), -- Wifi, TV, Tủ lạnh

    -- Phòng gia đình (P206 - P210)
    ('P206', 'TI001', 1), ('P206', 'TI003', 1), ('P206', 'TI005', 1), -- Wifi, Máy lạnh, Bình nóng lạnh
    ('P207', 'TI001', 1), ('P207', 'TI002', 1), ('P207', 'TI004', 1), -- Wifi, TV, Tủ lạnh
    ('P208', 'TI001', 1), ('P208', 'TI003', 1), -- Wifi, Máy lạnh
    ('P209', 'TI001', 1), ('P209', 'TI005', 1), ('P209', 'TI002', 1), -- Wifi, Bình nóng lạnh, TV
    ('P210', 'TI001', 1), ('P210', 'TI004', 1), ('P210', 'TI003', 1), -- Wifi, Tủ lạnh, Máy lạnh

    -- Phòng VIP (P301 - P310)
    ('P301', 'TI001', 1), ('P301', 'TI003', 1), ('P301', 'TI004', 1), -- Wifi, Máy lạnh, Tủ lạnh
    ('P302', 'TI001', 1), ('P302', 'TI005', 1), ('P302', 'TI002', 1), -- Wifi, Bình nóng lạnh, TV
    ('P303', 'TI001', 1), ('P303', 'TI003', 1), -- Wifi, Máy lạnh
    ('P304', 'TI001', 1), ('P304', 'TI004', 1), ('P304', 'TI005', 1), -- Wifi, Tủ lạnh, Bình nóng lạnh
    ('P305', 'TI001', 1), ('P305', 'TI002', 1), ('P305', 'TI003', 1), -- Wifi, TV, Máy lạnh
    ('P306', 'TI001', 1), ('P306', 'TI004', 1), -- Wifi, Tủ lạnh
    ('P307', 'TI001', 1), ('P307', 'TI005', 1), ('P307', 'TI002', 1), -- Wifi, Bình nóng lạnh, TV
    ('P308', 'TI001', 1), ('P308', 'TI003', 1), ('P308', 'TI004', 1), -- Wifi, Máy lạnh, Tủ lạnh
    ('P309', 'TI001', 1), ('P309', 'TI005', 1), -- Wifi, Bình nóng lạnh
    ('P310', 'TI001', 1), ('P310', 'TI002', 1), ('P310', 'TI003', 1), -- Wifi, TV, Máy lạnh

    -- Phòng Suite (P401 - P405, P501 - P505)
    ('P401', 'TI001', 1), ('P401', 'TI003', 1), ('P401', 'TI004', 1), ('P401', 'TI005', 1), -- Wifi, Máy lạnh, Tủ lạnh, Bình nóng lạnh
    ('P402', 'TI001', 1), ('P402', 'TI002', 1), ('P402', 'TI003', 1), -- Wifi, TV, Máy lạnh
    ('P403', 'TI001', 1), ('P403', 'TI004', 1), ('P403', 'TI005', 1), -- Wifi, Tủ lạnh, Bình nóng lạnh
    ('P404', 'TI001', 1), ('P404', 'TI002', 1), ('P404', 'TI003', 1), -- Wifi, TV, Máy lạnh
    ('P405', 'TI001', 1), ('P405', 'TI004', 1), ('P405', 'TI005', 1), -- Wifi, Tủ lạnh, Bình nóng lạnh
    ('P501', 'TI001', 1), ('P501', 'TI003', 1), ('P501', 'TI004', 1), ('P501', 'TI005', 1), -- Wifi, Máy lạnh, Tủ lạnh, Bình nóng lạnh
    ('P502', 'TI001', 1), ('P502', 'TI002', 1), ('P502', 'TI003', 1), -- Wifi, TV, Máy lạnh
    ('P503', 'TI001', 1), ('P503', 'TI004', 1), ('P503', 'TI005', 1), -- Wifi, Tủ lạnh, Bình nóng lạnh
    ('P504', 'TI001', 1), ('P504', 'TI002', 1), ('P504', 'TI003', 1), -- Wifi, TV, Máy lạnh
    ('P505', 'TI001', 1), ('P505', 'TI004', 1), ('P505', 'TI005', 1); -- Wifi, Tủ lạnh, Bình nóng lạnh