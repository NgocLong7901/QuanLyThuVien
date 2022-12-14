
 
CREATE DATABASE [QLTV1]
GO
ALTER DATABASE [QLTV1] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QLTV1].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QLTV1] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QLTV1] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QLTV1] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QLTV1] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QLTV1] SET ARITHABORT OFF 
GO
ALTER DATABASE [QLTV1] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [QLTV1] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QLTV1] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QLTV1] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QLTV1] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QLTV1] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QLTV1] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QLTV1] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QLTV1] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QLTV1] SET  ENABLE_BROKER 
GO
ALTER DATABASE [QLTV1] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QLTV1] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QLTV1] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QLTV1] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QLTV1] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QLTV1] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QLTV1] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QLTV1] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [QLTV1] SET  MULTI_USER 
GO
ALTER DATABASE [QLTV1] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QLTV1] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QLTV1] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QLTV1] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [QLTV1] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [QLTV1] SET QUERY_STORE = OFF
GO
USE [QLTV1]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Tự động tăng mã sách
create function [dbo].[func_nextID](@lastID varchar(5), @prefix varchar(2), @size int)
	returns varchar(5)
as
	BEGIN
		if(@lastID = '')
			set @lastID = @prefix + REPLICATE(0, @size - LEN(@prefix))
		declare @num_nextID int, @nextID varchar(5)
		set @lastID = LTRIM(RTRIM(@lastID))
		set @num_nextID = REPLACE(@lastID, @prefix, '') + 1
		set @size = @size - LEN(@prefix)
		set @nextID = @prefix + RIGHT(REPLICATE(0, @size) + CONVERT(VARCHAR(MAX), @num_nextID), @size)
		return  @nextID
	END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create function [dbo].[tinhSoNgayTre](@ngayMuon date, @ngayTra date, @songayMuon int)
	returns int
as
	BEGIN
		declare @num int = (DAY(@ngayTra) - DAY(@ngayMuon)) - @songayMuon
		return @num
	END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Thuthu](
	[maThuthu] [char](10) NOT NULL,
	[matKhau] [varchar](20) NOT NULL,
	[tenThuthu] [nvarchar](100) NULL,
	[ngaySinh] [date] NULL,
	[gioiTinh] [int] NULL,
	[diaChi] [nvarchar](100) NULL,
	[sdt] [varchar](11) NULL,
	[email] [varchar](100) NULL,
	[status] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[maThuthu] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietPhieuMuon](
	[maPhieuMuon] [varchar](5) NOT NULL,
	[maSach] [varchar](5) NOT NULL,
	[ngayThucTra] [date] NULL,
	[tienPhat] [int] NULL,
	[tinhTrangSach] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[maPhieuMuon] ASC,
	[maSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DanhMucSach](
	[maDMSach] [char](10) NOT NULL,
	[tenDMSach] [nvarchar](100)NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maDMSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuMuon](
	[maPhieuMuon] [varchar](5) NOT NULL,
	[ngayMuon] [date]NOT NULL,
	[soNgayMuon] [int] NOT NULL,
	[maDocGia] [char](13) NULL,
	[maThuthu] [char](10) NULL,
	[ghiChu] [text] NULL,
	[trangThai] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[maPhieuMuon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sach](
	[maSach] [varchar](5) NOT NULL,
	[tenSach] [nvarchar](100) NOT NULL,
	[maDMSach] [char](10) NULL,
	[maTheLoai] [char](10) NULL,
	[TacGia] [nvarchar](100) NULL,
	[NXB] [nvarchar](100) NULL,
	[namXuatBan] [int] NULL,
	[soLuongCon] [int] NULL,
	[tomTatND] [ntext] NULL,
PRIMARY KEY CLUSTERED 
(
	[maSach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DocGia](
	[maDocGia] [char](13) NOT NULL,
	[matKhau] [varchar](20) NOT NULL,
	[tenDocGia] [nvarchar](100) NULL,
	[ngaySinh] [date] NULL,
	[gioiTinh] [int] NULL,
	[email] [varchar](100) NULL,
	[sdt] [varchar](11) NULL,
	[status] [int] NULL,
	[soLuongMuon] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[maDocGia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TheLoai](
	[maTheLoai] [char](10) NOT NULL,
	[tenTheLoai] [nvarchar](100)NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maTheLoai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
create trigger tr_nextSach on dbo.Sach
for insert
as
	begin
		declare @lastSach varchar(5)
		set @lastSach = (Select top 1 maSach from Sach order by maSach desc)
		UPDATE Sach set maSach = dbo.func_nextID(@lastSach, 'S', 5) where maSach = ''
	end
GO
create trigger tr_nextMaPM on dbo.PhieuMuon
for insert
as
	begin
		declare @lastIdMaPM varchar(5)
		set @lastIdMaPM = (Select top 1 maPhieuMuon from PhieuMuon order by maPhieuMuon desc)
		UPDATE PhieuMuon set maPhieuMuon = dbo.func_nextID(@lastIdMaPM, 'PM', 5) where maPhieuMuon = ''
	end
go
--Trigger cập nhật Số lượng mượn
create trigger tr_soLuongMuon on ChiTietPhieuMuon 
for insert 
as
	BEGIN
		update DocGia
		set	soluongMuon = soluongMuon + 1
		from DocGia, PhieuMuon, inserted
		where inserted.maPhieuMuon = PhieuMuon.maPhieuMuon and DocGia.maDocGIa = PhieuMuon.maDocGia
	END
go

create trigger tr_soLuongMuon_delete on ChiTietPhieuMuon 
for delete 
as
	BEGIN
		update DocGia
		set	soluongMuon = soluongMuon - 1
		from DocGia, PhieuMuon, inserted
		where inserted.maPhieuMuon = PhieuMuon.maPhieuMuon and DocGia.maDocGIa = PhieuMuon.maDocGia
	END
go

--Trigger tính tiền phạt
create trigger tr_tienPhat on ChiTietPhieuMuon
for update
as
	begin 
		declare @ngayThucTra date = (select ngayThucTra from inserted)
		declare @ngayMuon date = (select ngayMuon from inserted, PhieuMuon where inserted.maPhieuMuon = PhieuMuon.maPhieuMuon)
		declare @soNgayMuon int = (select soNgayMuon from inserted, PhieuMuon where inserted.maPhieuMuon = PhieuMuon.maPhieuMuon)
		declare @tinhTrang nvarchar(100) = (select inserted.tinhTrangSach from inserted)
		
		if @tinhTrang = N'Bình thường'
			if (datediff(day, @ngayMuon, @ngayThucTra) > @soNgayMuon)
				update ChiTietPhieuMuon
				set tienPhat = (datediff(Day, PhieuMuon.ngayMuon, inserted.ngayThucTra)) * 10000
				from inserted, PhieuMuon, ChiTietPhieuMuon
				where inserted.maPhieuMuon = PhieuMuon.maPhieuMuon and ChiTietPhieuMuon.maPhieuMuon = inserted.maPhieuMuon
				and ChiTietPhieuMuon.maSach = inserted.maSach and PhieuMuon.maPhieuMuon = ChiTietPhieuMuon.maPhieuMuon
			else 
				update ChiTietPhieuMuon
				set tienPhat = 0
				from ChiTietPhieuMuon, inserted
				where ChiTietPhieuMuon.maPhieuMuon = inserted.maPhieuMuon
				and ChiTietPhieuMuon.maSach = inserted.maSach
		else if @tinhTrang = N'Mất sách'
			update ChiTietPhieuMuon
			set tienPhat = 50000
			from ChiTietPhieuMuon, inserted
			where ChiTietPhieuMuon.maPhieuMuon = inserted.maPhieuMuon
				and ChiTietPhieuMuon.maSach = inserted.maSach
		else if @tinhTrang = N'Hư hỏng'
			if ((datediff(day, @ngayMuon, @ngayThucTra)) > @soNgayMuon)
				update ChiTietPhieuMuon
				set tienPhat = (datediff(Day, PhieuMuon.ngayMuon, inserted.ngayThucTra)) * 10000 + 20000
				from inserted, PhieuMuon, ChiTietPhieuMuon
				where inserted.maPhieuMuon = PhieuMuon.maPhieuMuon and ChiTietPhieuMuon.maPhieuMuon = inserted.maPhieuMuon
				and ChiTietPhieuMuon.maSach = inserted.maSach and PhieuMuon.maPhieuMuon = ChiTietPhieuMuon.maPhieuMuon
			else
				update ChiTietPhieuMuon
				set tienPhat = 20000
				from ChiTietPhieuMuon, inserted
				where ChiTietPhieuMuon.maPhieuMuon = inserted.maPhieuMuon
				and ChiTietPhieuMuon.maSach = inserted.maSach
		--Cập nhật số lượng mượn
		if (@ngayThucTra is not null)
			update DocGia
			set	soluongMuon = soluongMuon - 1
			from DocGia, PhieuMuon, inserted
			where inserted.maPhieuMuon = PhieuMuon.maPhieuMuon and DocGia.maDocGia = PhieuMuon.maDocGia
		else 
			return
	end
go

GO
INSERT [dbo].[Thuthu] ([maThuthu], [matKhau], [tenThuthu], [ngaySinh], [gioiTinh], [diaChi], [sdt], [email], [status]) VALUES (N'Admin', N'abc123', N'Vũ Thu Hằng', CAST(N'1990-07-02' AS Date), 1, N'02/Thanh Sơn', N'0773123889', N'tranthia@gmail.com', 1)


INSERT [dbo].[DocGia] ([maDocGia], [matKhau], [tenDocGia], [ngaySinh], [gioiTinh], [email], [sdt], [status], [soLuongMuon]) VALUES (N'224064', N'abc123', N'Nguyễn Hoàng Việt', CAST(N'2001-02-10' AS Date), 1, N'viet@gmail.com', N'0776155064', 1, 3)
INSERT [dbo].[DocGia] ([maDocGia], [matKhau], [tenDocGia], [ngaySinh], [gioiTinh], [email], [sdt], [status], [soLuongMuon]) VALUES (N'116464', N'abc123', N'Lê Văn Lâm', CAST(N'2001-07-09' AS Date), 1, N'lam@gmail.com', N'0777443873', 1, 1)
INSERT [dbo].[DocGia] ([maDocGia], [matKhau], [tenDocGia], [ngaySinh], [gioiTinh], [email], [sdt], [status], [soLuongMuon]) VALUES (N'136264', N'abc123', N'Nguyễn Ngọc Long', CAST(N'2001-10-09' AS Date), 1, N'long@gmail.com', N'0777443873', 1, 0)
INSERT [dbo].[DocGia] ([maDocGia], [matKhau], [tenDocGia], [ngaySinh], [gioiTinh], [email], [sdt], [status], [soLuongMuon]) VALUES (N'25664', N'abc123', N'Bùi Duy Tuyến', CAST(N'2001-02-09' AS Date), 1, N'thuthuy@gmail.com', N'0777443873', 1, 0)

INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0001', N'Lập trình cơ bản với C', N'DM003     ', N'TL001     ', N'Hoàng Thị Mỹ Lệ', N'NXB Công nghệ thông tin', 2016, 5, N'Với mong muốn chia sẻ kinh nghiệm học lập trình và các kỹ năng mà anh đã trải qua trong suốt quá trình học và làm việc với tư cách là người đi trước cũng như là một Developer Full Stack, nên anh đã quyết định xuất bản sách')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0002', N'Giáo trình kỹ thuật xung số và ứng dụng', N'DM003     ', N'TL001     ', N'Nguyễn Linh Nam', N'NXB Công nghệ thông tin', 2016, 3, N'Với mong muốn chia sẻ kinh nghiệm học lập trình và các kỹ năng mà anh đã trải qua trong suốt quá trình học và làm việc với tư cách là người đi trước cũng như là một Developer Full Stack, nên anh đã quyết định xuất bản sách')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0003', N'Trường điện từ - Lý thuyết và bài tập', N'DM003     ', N'TL001     ', N'Lê Văn Sung', N'NXB Công nghệ thông tin', 2016, 3, N'Với mong muốn chia sẻ kinh nghiệm học lập trình và các kỹ năng mà anh đã trải qua trong suốt quá trình học và làm việc với tư cách là người đi trước cũng như là một Developer Full Stack, nên anh đã quyết định xuất bản sách')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0004', N'Cơ sở dữ liệu', N'DM003     ', N'TL001     ', N'Hoàng Thị Mỹ Lệ', N'NXB Công nghệ thông tin', 2016, 3, N'Với mong muốn chia sẻ kinh nghiệm học lập trình và các kỹ năng mà anh đã trải qua trong suốt quá trình học và làm việc với tư cách là người đi trước cũng như là một Developer Full Stack, nên anh đã quyết định xuất bản sách')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0005', N'Tin học văn phòng', N'DM003     ', N'TL001     ', N'Hoàng Thị Mỹ Lệ', N'NXB Công nghệ thông tin', 2016, 3, N'Với mong muốn chia sẻ kinh nghiệm học lập trình và các kỹ năng mà anh đã trải qua trong suốt quá trình học và làm việc với tư cách là người đi trước cũng như là một Developer Full Stack, nên anh đã quyết định xuất bản sách')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0006', N'Bơm nhiệt', N'DM002     ', N'TL001     ', N'Nguyễn Đức Lợi', N'NXB Cơ Khí', 2018, 3, N'Giáo trình gồm có 10 chương, trình bày các vấn đề về cơ cấu máy, phân tích động lực học cơ cấu máy, các vấn đề cơ bản trong thiết kế truyền động cơ khí, thiết kế các bộ truyền cơ khí và bộ phận đỡ nổi các bộ truyền. ')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0007', N'Cơ sở thiết kế máy', N'DM002     ', N'TL001     ', N'Nguyễn Đức Lợi', N'NXB Cơ Khí', 2018, 3, N'Giáo trình gồm có 10 chương, trình bày các vấn đề về cơ cấu máy, phân tích động lực học cơ cấu máy, các vấn đề cơ bản trong thiết kế truyền động cơ khí, thiết kế các bộ truyền cơ khí và bộ phận đỡ nổi các bộ truyền. ')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0008', N'Đo lường nhiệt', N'DM002     ', N'TL001     ', N'Võ Huy Hoàng', N'NXB Cơ Khí', 2018, 3, N'Giáo trình gồm có 10 chương, trình bày các vấn đề về cơ cấu máy, phân tích động lực học cơ cấu máy, các vấn đề cơ bản trong thiết kế truyền động cơ khí, thiết kế các bộ truyền cơ khí và bộ phận đỡ nổi các bộ truyền. ')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0009', N'Nhiên liệu sạch', N'DM002     ', N'TL001     ', N'Nguyễn Đức Lợi', N'NXB Cơ Khí', 2018, 3, N'Giáo trình gồm có 10 chương, trình bày các vấn đề về cơ cấu máy, phân tích động lực học cơ cấu máy, các vấn đề cơ bản trong thiết kế truyền động cơ khí, thiết kế các bộ truyền cơ khí và bộ phận đỡ nổi các bộ truyền. ')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0010', N'Giáo trình kỹ thuật nhiệt', N'DM002     ', N'TL001     ', N'Nguyễn Đức Lợi', N'NXB Cơ Khí', 2018, 3, N'Giáo trình gồm có 10 chương, trình bày các vấn đề về cơ cấu máy, phân tích động lực học cơ cấu máy, các vấn đề cơ bản trong thiết kế truyền động cơ khí, thiết kế các bộ truyền cơ khí và bộ phận đỡ nổi các bộ truyền. ')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0011', N'Ngoại ngữ 1', N'DM005     ', N'TL002     ', N'Nguyễn Như Hiền', N'NXB Ngoại ngữ', 2010, 1, N'Nội dung chính của sách, gồm từ mới, mẫu câu được giới thiệu bằng hình ảnh trực quan kết hợp với việc nghe đĩa,
								kế đến là những bài tập đọc hiểu. Các chủ điểm ngữ pháp được đưa vào sách một cách nhẹ nhàng và tự nhiên thông qua 
								các tình huống thực tế.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0012', N'Grammar in use', N'DM005     ', N'TL002     ', N'Nguyễn Đức Trí', N'NXB Ngoại ngữ', 2018, 4, N'Nội dung chính của sách, gồm từ mới, mẫu câu được giới thiệu bằng hình ảnh trực quan kết hợp với việc nghe đĩa,
								kế đến là những bài tập đọc hiểu. Các chủ điểm ngữ pháp được đưa vào sách một cách nhẹ nhàng và tự nhiên thông qua 
								các tình huống thực tế.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0013', N'Listen carefully', N'DM005     ', N'TL002     ', N'Nguyễn Như Hiền', N'NXB Ngoại ngữ', 2018, 2, N'Nội dung chính của sách, gồm từ mới, mẫu câu được giới thiệu bằng hình ảnh trực quan kết hợp với việc nghe đĩa,
								kế đến là những bài tập đọc hiểu. Các chủ điểm ngữ pháp được đưa vào sách một cách nhẹ nhàng và tự nhiên thông qua 
								các tình huống thực tế.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0014', N'Ngoại ngữ cơ bản', N'DM005     ', N'TL002     ', N'Nguyễn Như Hiền', N'NXB Ngoại ngữ', 2018, 2, N'Nội dung chính của sách, gồm từ mới, mẫu câu được giới thiệu bằng hình ảnh trực quan kết hợp với việc nghe đĩa,
								kế đến là những bài tập đọc hiểu. Các chủ điểm ngữ pháp được đưa vào sách một cách nhẹ nhàng và tự nhiên thông qua 
								các tình huống thực tế.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0015', N'Ngoại ngữ 2', N'DM005     ', N'TL002     ', N'Nguyễn Như Hiền', N'NXB Ngoại ngữ', 2018, 1, N'Nội dung chính của sách, gồm từ mới, mẫu câu được giới thiệu bằng hình ảnh trực quan kết hợp với việc nghe đĩa,
								kế đến là những bài tập đọc hiểu. Các chủ điểm ngữ pháp được đưa vào sách một cách nhẹ nhàng và tự nhiên thông qua 
								các tình huống thực tế.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0016', N'Kỹ thuật xử lý tín hiệu điều khiển', N'DM001     ', N'TL001     ', N'	Phạm Ngọc Thắng', N'NXB Điện-Điện Tử', 2014, 1, N'Giáo trình này được sử dụng làm tài liệu học tập cho sinh viên các khối ngành kỹ thuật và các ngành có liên quan đến kỹ thuật.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0017', N'Bài tập vi điều khiển và PLC', N'DM001     ', N'TL001     ', N'Đặng Văn Tuệ', N'NXB Điện-Điện Tử', 2014, 4, N'')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0018', N'Khí cụ điện - kết cấu, sử dụng và sửa chữa', N'DM001     ', N'TL001     ', N'Nguyễn Xuân Phú', N'NXB Điện-Điện Tử', 2014, 2, N'Giáo trình này được sử dụng làm tài liệu học tập cho sinh viên các khối ngành kỹ thuật và các ngành có liên quan đến kỹ thuật.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0019', N'Sổ tay chuyên ngành điện', N'DM001     ', N'TL002     ', N'Tăng Văn Mùi - Trần Duy Nam', N'NXB Điện-Điện Tử', 2013, 2, N'Giáo trình này được sử dụng làm tài liệu học tập cho sinh viên các khối ngành kỹ thuật và các ngành có liên quan đến kỹ thuật.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0020', N'Bài tập điều khiển tự động', N'DM001     ', N'TL001     ', N'	Nguyễn Công Phương', N'NXB Điện-Điện Tử', 2013, 1, N'Giáo trình này được sử dụng làm tài liệu học tập cho sinh viên các khối ngành kỹ thuật và các ngành có liên quan đến kỹ thuật.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0021', N'Sổ tay máy làm đất và làm đường', N'DM004     ', N'TL002     ', N'Lưu Bá Thuận', N'NXB Xây dựng', 2015, 10, N'Cuốn sách này nhằm hệ thống hóa và trang bị các khái niệm, thông tin cơ bản về các hệ thống kỹ thuật trong công trình cho các sinh viên trong trường Đại học Xây dựng nói riêng cũng như các trường đại học có đạo tạo ngành kỹ thuật xây dựng.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0022', N'Móng cọc phân tích và thiết kế', N'DM004     ', N'TL001     ', N'Nguyễn Thái', N'NXB Xây dựng', 2014, 4, N'Cuốn sách này nhằm hệ thống hóa và trang bị các khái niệm, thông tin cơ bản về các hệ thống kỹ thuật trong công trình cho các sinh viên trong trường Đại học Xây dựng nói riêng cũng như các trường đại học có đạo tạo ngành kỹ thuật xây dựng.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0023', N'Cấu tạo bê tông cốt thép', N'DM004     ', N'TL001     ', N'Bộ Xây dựng', N'NXB Xây dựng', 2014, 2, N'Cuốn sách này nhằm hệ thống hóa và trang bị các khái niệm, thông tin cơ bản về các hệ thống kỹ thuật trong công trình cho các sinh viên trong trường Đại học Xây dựng nói riêng cũng như các trường đại học có đạo tạo ngành kỹ thuật xây dựng.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0024', N'Kết cấu thép - Công trình đặc biệt', N'DM004     ', N'TL001     ', N'GS.TS.Phạm Văn Hội ', N'NXB Xây dựng', 2013, 2, N'Cuốn sách này nhằm hệ thống hóa và trang bị các khái niệm, thông tin cơ bản về các hệ thống kỹ thuật trong công trình cho các sinh viên trong trường Đại học Xây dựng nói riêng cũng như các trường đại học có đạo tạo ngành kỹ thuật xây dựng.')
INSERT [dbo].[Sach] ([maSach], [tenSach], [maDMSach], [maTheLoai], [TacGia], [NXB], [namXuatBan], [soLuongCon], [tomTatND]) VALUES (N'S0025', N'Kết cấu bê tông cốt thép - Phần cấu kiện cơ bản', N'DM004     ', N'TL001     ', N'Phan Quang Minh', N'NXB Xây dựng', 2013, 1, N'Cuốn sách này nhằm hệ thống hóa và trang bị các khái niệm, thông tin cơ bản về các hệ thống kỹ thuật trong công trình cho các sinh viên trong trường Đại học Xây dựng nói riêng cũng như các trường đại học có đạo tạo ngành kỹ thuật xây dựng.')

INSERT [dbo].[PhieuMuon] ([maPhieuMuon], [ngayMuon], [soNgayMuon], [maDocGia], [maThuthu], [ghiChu], [trangThai]) VALUES (N'PM001', CAST(N'2021-05-10' AS Date), 7, N'224064', N'Admin    ', N'', N'Chưa trả')
INSERT [dbo].[PhieuMuon] ([maPhieuMuon], [ngayMuon], [soNgayMuon], [maDocGia], [maThuthu], [ghiChu], [trangThai]) VALUES (N'PM002', CAST(N'2021-05-11' AS Date), 7, N'116464', N'Admin    ', N'', N'Chưa trả')
INSERT [dbo].[PhieuMuon] ([maPhieuMuon], [ngayMuon], [soNgayMuon], [maDocGia], [maThuthu], [ghiChu], [trangThai]) VALUES (N'PM003', CAST(N'2021-05-12' AS Date), 14, N'224064', N'Admin    ', N'', N'Chưa trả')
INSERT [dbo].[PhieuMuon] ([maPhieuMuon], [ngayMuon], [soNgayMuon], [maDocGia], [maThuthu], [ghiChu], [trangThai]) VALUES (N'PM004', CAST(N'2021-05-24' AS Date), 7, N'136264', N'Admin    ', N'', N'Đã trả')
INSERT [dbo].[PhieuMuon] ([maPhieuMuon], [ngayMuon], [soNgayMuon], [maDocGia], [maThuthu], [ghiChu], [trangThai]) VALUES (N'PM005', CAST(N'2021-05-24' AS Date), 7, N'25664', N'Admin    ', N'', N'Chưa trả')
INSERT [dbo].[PhieuMuon] ([maPhieuMuon], [ngayMuon], [soNgayMuon], [maDocGia], [maThuthu], [ghiChu], [trangThai]) VALUES (N'PM006', CAST(N'2021-05-24' AS Date), 7, N'25664', N'Admin    ', N'', N'Đã trả')
INSERT [dbo].[PhieuMuon] ([maPhieuMuon], [ngayMuon], [soNgayMuon], [maDocGia], [maThuthu], [ghiChu], [trangThai]) VALUES (N'PM007', CAST(N'2021-05-25' AS Date), 7, N'136264', N'Admin    ', N'khong', N'Chưa trả')
INSERT [dbo].[PhieuMuon] ([maPhieuMuon], [ngayMuon], [soNgayMuon], [maDocGia], [maThuthu], [ghiChu], [trangThai]) VALUES (N'PM008', CAST(N'2021-05-23' AS Date), 7, N'116464', N'Admin    ', N'', N'Chưa trả')


INSERT [dbo].[DanhMucSach] ([maDMSach], [tenDMSach]) VALUES (N'DM001     ', N'Chuyên ngành Điện-Điện tử')
INSERT [dbo].[DanhMucSach] ([maDMSach], [tenDMSach]) VALUES (N'DM002     ', N'Chuyên ngành Cơ khí')
INSERT [dbo].[DanhMucSach] ([maDMSach], [tenDMSach]) VALUES (N'DM003     ', N'Chuyên ngành Công nghệ thông tin')
INSERT [dbo].[DanhMucSach] ([maDMSach], [tenDMSach]) VALUES (N'DM004     ', N'Chuyên ngành Xây dựng')
INSERT [dbo].[DanhMucSach] ([maDMSach], [tenDMSach]) VALUES (N'DM005     ', N'Sách Tiếng Anh')
INSERT [dbo].[DanhMucSach] ([maDMSach], [tenDMSach]) VALUES (N'DM006     ', N'Kỹ năng sống')
INSERT [dbo].[DanhMucSach] ([maDMSach], [tenDMSach]) VALUES (N'DM007     ', N'Sách nước ngoài')

INSERT [dbo].[TheLoai] ([maTheLoai], [tenTheLoai]) VALUES (N'TL001     ', N'Giáo trình học')
INSERT [dbo].[TheLoai] ([maTheLoai], [tenTheLoai]) VALUES (N'TL002     ', N'Sách tham khảo')
INSERT [dbo].[TheLoai] ([maTheLoai], [tenTheLoai]) VALUES (N'TL003     ', N'Văn hóa lịch sử')
INSERT [dbo].[TheLoai] ([maTheLoai], [tenTheLoai]) VALUES (N'TL004     ', N'Chính trị, Pháp luật')
INSERT [dbo].[TheLoai] ([maTheLoai], [tenTheLoai]) VALUES (N'TL005     ', N'Tạp chí')

INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM001', N'S0001', CAST(N'2021-05-27' AS Date), 50000.0000, N'Mất sách')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM001', N'S0002', CAST(N'2021-06-20' AS Date), 430000.0000, N'Hư hỏng')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM002', N'S0003', CAST(N'2021-06-20' AS Date), 0.0000, N'Bình thường')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM002', N'S0004', NULL, 0.0000, N'')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM002', N'S0005', NULL, 0.0000, N'')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM003', N'S0006', NULL, 0.0000, N'')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM004', N'S0006', CAST(N'2021-05-27' AS Date), 0.0000, N'Bình thường')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM004', N'S0008', CAST(N'2021-05-27' AS Date), 0.0000, N'Bình thường')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM004', N'S0009', CAST(N'2021-05-27' AS Date), 0.0000, N'Bình thường')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM005', N'S0006', CAST(N'2021-05-27' AS Date), 0.0000, N'Bình thường')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM005', N'S0008', NULL, 0.0000, N'')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM006', N'S0002', CAST(N'2021-05-26' AS Date), 0.0000, N'Bình thường')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM006', N'S0003', CAST(N'2021-05-26' AS Date), 0.0000, N'Bình thường')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM006', N'S0007', CAST(N'2021-05-26' AS Date), 0.0000, N'Bình thường')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM007', N'S0003', NULL, 0.0000, N'')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM007', N'S0005', NULL, 0.0000, N'')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM008', N'S0007', CAST(N'2021-05-31' AS Date), 10000.0000, N'Trễ hạn')
INSERT [dbo].[ChiTietPhieuMuon] ([maPhieuMuon], [maSach], [ngayThucTra], [tienPhat], [tinhTrangSach]) VALUES (N'PM008', N'S0010', CAST(N'2021-05-31' AS Date), 10000.0000, N'Trễ hạn')

ALTER TABLE [dbo].[Thuthu] ADD  DEFAULT ('1') FOR [status]
GO
ALTER TABLE [dbo].[DocGia] ADD  DEFAULT ('1') FOR [status]
GO
ALTER TABLE [dbo].[DocGia] ADD  DEFAULT ((0)) FOR [soLuongMuon]
GO
ALTER TABLE [dbo].[ChiTietPhieuMuon]  WITH CHECK ADD FOREIGN KEY([maPhieuMuon])
REFERENCES [dbo].[PhieuMuon] ([maPhieuMuon])
GO
ALTER TABLE [dbo].[ChiTietPhieuMuon]  WITH CHECK ADD FOREIGN KEY([maSach])
REFERENCES [dbo].[Sach] ([maSach])
GO
ALTER TABLE [dbo].[PhieuMuon]  WITH CHECK ADD FOREIGN KEY([maThuthu])
REFERENCES [dbo].[Thuthu] ([maThuthu])
GO
ALTER TABLE [dbo].[PhieuMuon]  WITH CHECK ADD FOREIGN KEY([maDocGia])
REFERENCES [dbo].[DocGia] ([maDocGia])
GO
ALTER TABLE [dbo].[Sach]  WITH CHECK ADD FOREIGN KEY([maDMSach])
REFERENCES [dbo].[DanhMucSach] ([maDMSach])
GO
ALTER TABLE [dbo].[Sach]  WITH CHECK ADD FOREIGN KEY([maTheLoai])
REFERENCES [dbo].[TheLoai] ([maTheLoai])
GO
ALTER TABLE [dbo].[Thuthu]  WITH CHECK ADD CHECK  (([Email] like '[a-z]%@%'))
GO
ALTER TABLE [dbo].[Thuthu]  WITH CHECK ADD CHECK  (([SDT] like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' OR [SDT] like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'))
GO
ALTER TABLE [dbo].[DocGia]  WITH CHECK ADD CHECK  (([Email] like '[a-z]%@%'))
GO
ALTER TABLE [dbo].[DocGia]  WITH CHECK ADD CHECK  (([SDT] like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' OR [SDT] like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'))
GO
USE [master]
GO
ALTER DATABASE [QLTV1] SET  READ_WRITE 
GO
