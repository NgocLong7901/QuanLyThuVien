package View;

import DAO.Sach_DAO;
import DAO.DocGia_Dao;
import DAO.DocGia_Dao_implement;
import DAO.KetNoiSQL;
import DAO.Sach_Dao_implement;
import DAO.Sach_dao_thuy;
import DAO.Thongke_Dao;
import Model.ChiTietPhieuMuon;
import Model.DanhMucSach;
import Model.Docgia;
import Model.PhieuMuon;
import Model.Sach;
import Model.sach_th;
import Service.CTPhieuMuon_Service;
import Service.DanhMucSach_Service;
import Service.PhieuMuon_Service;
import Service.Sach_Service;
import java.awt.Color;
import java.awt.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.html.FormView;

/**
 *
 * @author Viet
 */
public class TrangChuThuThu extends javax.swing.JFrame {
    

    private int tienPhat = 0;
    Sach_DAO sachdao = new Sach_DAO();
    DefaultTableModel defaultTableModel_DM;
    DanhMucSach_Service dms_Service;
    DefaultTableModel defaultTableModel_S;
    Sach_Service s_Service;

    DocGia_Dao accessDocGia = new DocGia_Dao_implement();
    Sach_dao_thuy getSach = new Sach_Dao_implement();
    Thongke_Dao getThongke = new Thongke_Dao();

    CTPhieuMuon_Service ctpmsService;
    PhieuMuon_Service pmsService;
    DefaultTableModel defaultTableModel_DSPM;
    DefaultTableModel defaultTableModel_CTPM;

    public Connection conn = KetNoiSQL.getConnection();

    public TrangChuThuThu() {
        initComponents();
        disable_DM();
        btnH_luuSach.setEnabled(false);
        btnH_suaSach.setEnabled(false);
        //QuanLyPM
        pmsService = new PhieuMuon_Service();
        ctpmsService = new CTPhieuMuon_Service();
        defaultTableModel_DSPM = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        defaultTableModel_CTPM = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loadData_TblDSPM();
        DesignTbl_DSPM();
        disable_CTPM();
        //Danh mục sách
        dms_Service = new DanhMucSach_Service();
        defaultTableModel_DM = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tbl_DMSach.setModel(defaultTableModel_DM);
        defaultTableModel_DM.addColumn("Mã danh mục");
        defaultTableModel_DM.addColumn("Tên danh mục");

        setTableData_DM(dms_Service.getDSDanhMucSach());

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tbl_DMSach.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(0);
        tbl_DMSach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tbl_DMSach.setRowHeight(30);

        TableColumnModel columnModel = tbl_DMSach.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(170);
        columnModel.getColumn(1).setPreferredWidth(300);

        //Sách
        loadmaDanhMuc();
        loadmaTheLoai();
        disable_S();
        refresh();

        s_Service = new Sach_Service();
        defaultTableModel_S = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblH_Sach.setModel(defaultTableModel_S);
        defaultTableModel_S.addColumn("Mã sách");
        defaultTableModel_S.addColumn("Tên sách");
        defaultTableModel_S.addColumn("Mã DM");
        defaultTableModel_S.addColumn("Mã TL");
        defaultTableModel_S.addColumn("Tác giả");
        defaultTableModel_S.addColumn("Nhà XB");
        defaultTableModel_S.addColumn("Năm XB");
        defaultTableModel_S.addColumn("SL");
        defaultTableModel_S.addColumn("Tóm tắt");
        //defaultTableModel_S.addColumn("Ảnh");

        mokhoa.setEnabled(false);

        setTableData_S(s_Service.getDSSach());

        DefaultTableCellRenderer renderers = (DefaultTableCellRenderer) tblH_Sach.getTableHeader().getDefaultRenderer();
        renderers.setHorizontalAlignment(0);
        tblH_Sach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
        tblH_Sach.setRowHeight(30);

        TableColumnModel columnModel1 = tblH_Sach.getColumnModel();
        columnModel1.getColumn(0).setPreferredWidth(150);
        columnModel1.getColumn(1).setPreferredWidth(350);
        columnModel1.getColumn(2).setPreferredWidth(150);
        columnModel1.getColumn(3).setPreferredWidth(150);
        columnModel1.getColumn(4).setPreferredWidth(170);
        columnModel1.getColumn(5).setPreferredWidth(250);
        columnModel1.getColumn(6).setPreferredWidth(120);
        columnModel1.getColumn(7).setPreferredWidth(50);
        columnModel1.getColumn(8).setPreferredWidth(120);
        //columnModel1.getColumn(9).setPreferredWidth(100);

        DefaultTableCellRenderer rendererdg = (DefaultTableCellRenderer) tableDocgia.getTableHeader().getDefaultRenderer();
        rendererdg.setHorizontalAlignment(0);
        tableDocgia.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tableDocgia.setRowHeight(30);

        TableColumnModel columnModeldg = tableDocgia.getColumnModel();
        columnModeldg.getColumn(0).setPreferredWidth(120);
        columnModeldg.getColumn(1).setPreferredWidth(180);
        columnModeldg.getColumn(4).setPreferredWidth(150);

        DefaultTableCellRenderer rendererxttdg = (DefaultTableCellRenderer) tablexemttdg.getTableHeader().getDefaultRenderer();
        rendererxttdg.setHorizontalAlignment(0);
        tablexemttdg.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tablexemttdg.setRowHeight(30);

        TableColumnModel columnModelxttdg = tablexemttdg.getColumnModel();
        columnModelxttdg.getColumn(0).setPreferredWidth(120);
        columnModelxttdg.getColumn(1).setPreferredWidth(180);
        columnModelxttdg.getColumn(2).setPreferredWidth(150);

        List<Docgia> alldg = accessDocGia.getAllDocgiar();
        
        DefaultTableModel dg = (DefaultTableModel) tablexemttdg.getModel();
        dg.setRowCount(0);
        int m = 0;
        for (Docgia s : alldg) {
            m++;
            dg.addRow(new Object[] {m, s.getMaTk(), s.getTenTk(), s.getNgaySinh(), s.getGioiTinh(),s.getEmail(),s.getSdt()});
        }
        DefaultTableCellRenderer rendertkbd = (DefaultTableCellRenderer) tabletkbandoc.getTableHeader().getDefaultRenderer();
        rendertkbd.setHorizontalAlignment(0);
        tabletkbandoc.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
        tabletkbandoc.setRowHeight(30);

        TableColumnModel columnModetkbd = tablexemttdg.getColumnModel();
        columnModetkbd.getColumn(0).setPreferredWidth(80);
        columnModetkbd.getColumn(1).setPreferredWidth(200);
        columnModetkbd.getColumn(2).setPreferredWidth(220);

        DefaultTableCellRenderer rendertks = (DefaultTableCellRenderer) tabletksach.getTableHeader().getDefaultRenderer();
        rendertks.setHorizontalAlignment(0);
        tabletksach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
        tabletksach.setRowHeight(30);

        DefaultTableCellRenderer rendertktp = (DefaultTableCellRenderer) tabletktienphat.getTableHeader().getDefaultRenderer();
        rendertktp.setHorizontalAlignment(0);
        tabletktienphat.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
        tabletktienphat.setRowHeight(30);

        // load du lieu quan lý độc giả
        this.loaddg();

        //load dữ liệu sách vào trang tra cứ sách
        DefaultTableCellRenderer renderers_SearchSach = (DefaultTableCellRenderer) tableSearchSach.getTableHeader().getDefaultRenderer();
        renderers_SearchSach.setHorizontalAlignment(0);
        tableSearchSach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
        tableSearchSach.setRowHeight(30);

        TableColumnModel columnModel_SearchSach = tableSearchSach.getColumnModel();
        columnModel_SearchSach.getColumn(0).setPreferredWidth(50);
        columnModel_SearchSach.getColumn(1).setPreferredWidth(300);
        columnModel_SearchSach.getColumn(2).setPreferredWidth(150);
        columnModel_SearchSach.getColumn(3).setPreferredWidth(200);
        columnModel_SearchSach.getColumn(4).setPreferredWidth(100);

        List<sach_th> allSach = getSach.getAllSach();

        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        int i = 0;
        for (sach_th s : allSach) {
            i++;
            sachtb.addRow(new Object[]{i, s.getTenSach(), s.getTacgia(), s.getNhaSB(), s.getSoluong()});
        }
    }

    public void loaddg() {
        madg.setText("");
        
        tenTaikhoan.setText("");
        ngaysinh.setText("");
        emailDocgia.setText("");
        this.sdt.setText("");
        madg.setEditable(true);
        List<Docgia> allDocgia = accessDocGia.getAllDocgiar();
        DefaultTableModel tb = (DefaultTableModel) tableDocgia.getModel();
        DefaultTableModel tbxtt = (DefaultTableModel) tablexemttdg.getModel();
        tb.setRowCount(0);
        tbxtt.setRowCount(0);
        allDocgia.forEach((docgia) -> {
            String gt = "Nam";
            if (docgia.getGioiTinh() == 2) {
                gt = "Nữ";
            }
            tb.addRow(new Object[]{docgia.getMaTk(), docgia.getTenTk(), docgia.getNgaySinh(), gt, docgia.getEmail(), docgia.getSdt()});
            tbxtt.addRow(new Object[]{docgia.getMaTk(), docgia.getTenTk(), docgia.getNgaySinh(), gt, docgia.getEmail(), docgia.getSdt()});
        });
    
    }
    public void reset(){
    madg.setText("");
       
        tenTaikhoan.setText("");
        ngaysinh.setText("");
        emailDocgia.setText("");
        this.sdt.setText("");
        
            
       
    
    }

    private void setTableData_DM(List<DanhMucSach> dms) {
        for (DanhMucSach dm : dms) {
            defaultTableModel_DM.addRow(new Object[]{dm.getMaDM(), dm.getTenDM()});
        }
    }

    private void setTableData_S(List<Sach> ss) {
        for (Sach s : ss) {
            defaultTableModel_S.addRow(new Object[]{s.getMaSach(), s.getTenSach(), s.getMaDMSach(), s.getMaTheLoai(), s.getTacGia(), s.getNXB(), s.getNamXuatBan(), s.getSoLuongCon(), s.getTomTatND()});
        }
    }

    public void enable_DM() {
        txt_maDMSach.setEnabled(true);
        txt_tenDMSach.setEnabled(true);
    }
   

    public void disable_DM() {
        txt_maDMSach.setEnabled(false);
        txt_tenDMSach.setEnabled(true);
    }

    public void enable_S() {
        H_tenSach.setEnabled(true);
        Hc_maTheLoai.setEnabled(true);
        //H_tenTheLoai.setEnabled(true);
        H_soLuongCon.setEnabled(true);
        H_namXB.setEnabled(true);
        H_tacGia.setEnabled(true);
        H_nhaXB.setEnabled(true);
        Hc_maDM.setEnabled(true);
        //H_tenDM.setEnabled(true);
        H_tomTat.setEnabled(true);
        //H_anhSach.setEnabled(true);
    }

    public void disable_S() {
        H_tenSach.setEnabled(false);
        Hc_maTheLoai.setEnabled(false);
        H_tenTheLoai.setEnabled(false);
        H_soLuongCon.setEnabled(false);
        H_namXB.setEnabled(false);
        H_tacGia.setEnabled(false);
        H_nhaXB.setEnabled(false);
        Hc_maDM.setEnabled(false);
        H_tenDM.setEnabled(false);
        H_tomTat.setEnabled(false);
        //H_anhSach.setEnabled(true);
    }

    public void refresh() {
        H_maSach.setText("");
        H_tenSach.setText("");
        Hc_maTheLoai.setSelectedIndex(-1);
        H_tenTheLoai.setText("");
        H_soLuongCon.setText("");
        H_namXB.setText("");
        H_tacGia.setText("");
        H_nhaXB.setText("");
        Hc_maDM.setSelectedIndex(-1);
        H_tenDM.setText("");
        H_tomTat.setText("");
        //H_anhSach.setText("");

//        btnH_themSach.setEnabled(true);
//        btnH_luuSach.setEnabled(false);
//        btnH_suaSach.setEnabled(false);
//        btnH_xoaSach.setEnabled(false);
    }

    public void loadmaTheLoai() {
        Hc_maTheLoai.removeAllItems();
        String sql = "SELECT maTheLoai FROM TheLoai";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                this.Hc_maTheLoai.addItem(rs.getString("maTheLoai"));
            }
        } catch (Exception e) {

        }
    }

    public void loadmaDanhMuc() {
        Hc_maDM.removeAllItems();
        String sql = "SELECT maDMSach FROM DanhMucSach";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                this.Hc_maDM.addItem(rs.getString("maDMSach"));
            }
        } catch (Exception e) {

        }
    } 
    
   

    public int getSoNgayTre(String maPM, String maSach, String ngayThucTra) {
        String sql = "select ((DAY('" + ngayThucTra + "') - DAY(pm.ngayMuon)) - pm.soNgayMuon) as 'S' from ChiTietPhieuMuon as ctpm, PhieuMuon as pm where ctpm.maPhieuMuon = pm.maPhieuMuon and ((DAY(ctpm.ngayThucTra) - DAY(pm.ngayMuon)) > pm.soNgayMuon) and maSach like '%" + maSach + "%' and pm.maPhieuMuon like '%" + maPM + "%'";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {

                return rs.getInt("S");
            }
        } catch (Exception e) {
            System.out.println("Không tính được ");
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gioitinhbtngroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTP_main = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        quanlyttdg = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablexemttdg = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        textSearch = new javax.swing.JTextField();
        btnlammoi = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDocgia = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        madg = new javax.swing.JTextField();
        tenTaikhoan = new javax.swing.JTextField();
        gioitinhnam = new javax.swing.JRadioButton();
        gioitinhnu = new javax.swing.JRadioButton();
        sdt = new javax.swing.JTextField();
        emailDocgia = new javax.swing.JTextField();
        themmoidg = new javax.swing.JButton();
        updatedg = new javax.swing.JButton();
        khoatk = new javax.swing.JButton();
        ngaysinh = new javax.swing.JTextField();
        mokhoa = new javax.swing.JButton();
        mokhoa1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        H_maSach = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        H_tenSach = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        H_soLuongCon = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        H_tacGia = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        H_nhaXB = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        H_namXB = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        H_tomTat = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        H_tenDM = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        H_tenTheLoai = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblH_Sach = new javax.swing.JTable();
        btnH_themSach = new javax.swing.JButton();
        btnH_suaSach = new javax.swing.JButton();
        btnH_luuSach = new javax.swing.JButton();
        btn_lamMoiSach = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        Hc_maTheLoai = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        Hc_maDM = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_DMSach = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_maDMSach = new javax.swing.JTextField();
        txt_tenDMSach = new javax.swing.JTextField();
        btn_ThemDMSach = new javax.swing.JButton();
        txt_timkiemDMSach = new javax.swing.JTextField();
        btnreset = new javax.swing.JButton();
        btnluu = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        jPK_QuanLyPhieuMuon = new javax.swing.JPanel();
        jTPK_QuanLyPM = new javax.swing.JTabbedPane();
        Panel_DanhSachPM = new javax.swing.JPanel();
        jSPK_DSPM = new javax.swing.JScrollPane();
        tblK_DSPM = new javax.swing.JTable();
        btnK_themPM = new javax.swing.JButton();
        K_tieuDe = new javax.swing.JLabel();
        Panel_ChiTietPM = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        K_maPM = new javax.swing.JTextField();
        K_maSach = new javax.swing.JTextField();
        K_tinhTrangSach = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        K_ngayThucTra = new com.toedter.calendar.JDateChooser();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblK_ChiTiet = new javax.swing.JTable();
        btnK_suaChiTiet = new javax.swing.JButton();
        btnK_luuChiTiet = new javax.swing.JButton();
        btnK_lamMoi = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        cbb_chucNangThongKe = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabletkbandoc = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        cbb_chucNangThongKe1 = new javax.swing.JComboBox<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabletksach = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        cbb_chucNangThongKe2 = new javax.swing.JComboBox<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabletktienphat = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        timkiem = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        textboxsearch = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableSearchSach = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Trang chủ ");

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("QUẢN LÝ THƯ VIỆN");

        jButton1.setBackground(new java.awt.Color(255, 255, 204));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/choice.png"))); // NOI18N
        jButton1.setText("Đăng xuất");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTP_main.setBackground(new java.awt.Color(51, 102, 255));
        jTP_main.setForeground(new java.awt.Color(255, 255, 255));
        jTP_main.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTP_main.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTP_main.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jTP_main.setMaximumSize(new java.awt.Dimension(300, 300));
        jTP_main.setPreferredSize(new java.awt.Dimension(300, 300));
        jTP_main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTP_mainMouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        quanlyttdg.setForeground(new java.awt.Color(0, 0, 153));
        quanlyttdg.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(204, 255, 204));

        tablexemttdg.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tablexemttdg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã độc giả", "Tên độc giả", "Ngày Sinh", "Giới Tính", "Email", "Số điện thoại"
            }
        ));
        tablexemttdg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablexemttdgMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablexemttdg);
        if (tablexemttdg.getColumnModel().getColumnCount() > 0) {
            tablexemttdg.getColumnModel().getColumn(2).setMinWidth(50);
            tablexemttdg.getColumnModel().getColumn(2).setPreferredWidth(90);
            tablexemttdg.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/light-bulb.png"))); // NOI18N
        jLabel5.setText("Tìm thông tin độc giả:");

        textSearch.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        textSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSearchActionPerformed(evt);
            }
        });
        textSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textSearchKeyTyped(evt);
            }
        });

        btnlammoi.setBackground(new java.awt.Color(255, 204, 204));
        btnlammoi.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnlammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/nhapmoi.png"))); // NOI18N
        btnlammoi.setText("RESET");
        btnlammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlammoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnlammoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(199, Short.MAX_VALUE))
        );

        quanlyttdg.addTab("Xem thông tin độc giả", jPanel10);

        jPanel11.setBackground(new java.awt.Color(204, 255, 204));

        tableDocgia.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tableDocgia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã độc giả", "Tên độc giả", "Ngày sinh", "Giới tính", "Email", "SDT"
            }
        ));
        tableDocgia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDocgiaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableDocgia);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel7.setText("Mã độc giả:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel8.setText("Tên độc giả:");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel9.setText("Giới tính:");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel10.setText("Số điện thoại:");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel11.setText("Email:");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel12.setText("Ngày sinh");

        madg.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        madg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                madgActionPerformed(evt);
            }
        });
        madg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                madgKeyPressed(evt);
            }
        });

        tenTaikhoan.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tenTaikhoan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tenTaikhoanKeyPressed(evt);
            }
        });

        gioitinhnam.setBackground(new java.awt.Color(255, 255, 204));
        gioitinhbtngroup.add(gioitinhnam);
        gioitinhnam.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        gioitinhnam.setText("Nam");

        gioitinhnu.setBackground(new java.awt.Color(255, 255, 204));
        gioitinhbtngroup.add(gioitinhnu);
        gioitinhnu.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        gioitinhnu.setText("Nữ");

        sdt.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        sdt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sdtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sdtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sdtKeyTyped(evt);
            }
        });

        emailDocgia.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

        themmoidg.setBackground(new java.awt.Color(255, 204, 255));
        themmoidg.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        themmoidg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus.png"))); // NOI18N
        themmoidg.setText("Thêm ");
        themmoidg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themmoidgActionPerformed(evt);
            }
        });

        updatedg.setBackground(new java.awt.Color(255, 204, 255));
        updatedg.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        updatedg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exchange1.png"))); // NOI18N
        updatedg.setText("Sửa ");
        updatedg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatedgActionPerformed(evt);
            }
        });

        khoatk.setBackground(new java.awt.Color(255, 204, 255));
        khoatk.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        khoatk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/block-user.png"))); // NOI18N
        khoatk.setText("Khóa tài khoản");
        khoatk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                khoatkActionPerformed(evt);
            }
        });

        ngaysinh.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        ngaysinh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ngaysinhFocusLost(evt);
            }
        });
        ngaysinh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ngaysinhKeyPressed(evt);
            }
        });

        mokhoa.setBackground(new java.awt.Color(255, 204, 255));
        mokhoa.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        mokhoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/man.png"))); // NOI18N
        mokhoa.setText("Mở Khóa");
        mokhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mokhoaActionPerformed(evt);
            }
        });

        mokhoa1.setBackground(new java.awt.Color(255, 204, 255));
        mokhoa1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        mokhoa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/nhapmoi.png"))); // NOI18N
        mokhoa1.setText("Làm mới");
        mokhoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mokhoa1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(madg, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(18, 18, 18)
                            .addComponent(gioitinhnam)
                            .addGap(18, 18, 18)
                            .addComponent(gioitinhnu))
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tenTaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(emailDocgia, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addComponent(ngaysinh)
                    .addComponent(sdt))
                .addGap(95, 95, 95))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(themmoidg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updatedg, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(khoatk, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(mokhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(mokhoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(madg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(32, 32, 32)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(ngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(tenTaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(emailDocgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(gioitinhnam)
                        .addComponent(gioitinhnu)))
                .addGap(38, 38, 38)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themmoidg, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatedg, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(khoatk, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mokhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mokhoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );

        quanlyttdg.addTab("Quản lý độc giả", jPanel11);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(quanlyttdg))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(quanlyttdg)
        );

        jTP_main.addTab("     QUẢN LÝ ĐỘC GIẢ ", new javax.swing.ImageIcon(getClass().getResource("/Icon/docgia.png")), jPanel3); // NOI18N

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jTabbedPane2.setForeground(new java.awt.Color(0, 0, 153));
        jTabbedPane2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jPanel8.setBackground(new java.awt.Color(204, 255, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 51, 0));
        jLabel13.setText("Mã sách:");

        H_maSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        H_maSach.setForeground(new java.awt.Color(255, 0, 0));
        H_maSach.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 51, 0));
        jLabel14.setText("Tên sách:");

        H_tenSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 51, 0));
        jLabel17.setText("Số lượng còn:");

        H_soLuongCon.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 51, 0));
        jLabel18.setText("Tác giả:");

        H_tacGia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 51, 0));
        jLabel19.setText("Nhà xuất bản:");

        H_nhaXB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 51, 0));
        jLabel20.setText("Năm xuất bản:");

        H_namXB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 51, 0));
        jLabel21.setText("Tóm tắt nội dung:");

        H_tomTat.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        H_tomTat.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 51, 0));
        jLabel22.setText("Tên danh mục:");

        H_tenDM.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        H_tenDM.setEnabled(false);

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 51, 0));
        jLabel23.setText("Thể loại:");

        H_tenTheLoai.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        H_tenTheLoai.setEnabled(false);

        tblH_Sach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblH_Sach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblH_Sach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblH_SachMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblH_Sach);

        btnH_themSach.setBackground(new java.awt.Color(255, 204, 255));
        btnH_themSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnH_themSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus.png"))); // NOI18N
        btnH_themSach.setText("Thêm");
        btnH_themSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnH_themSachActionPerformed(evt);
            }
        });

        btnH_suaSach.setBackground(new java.awt.Color(255, 204, 255));
        btnH_suaSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnH_suaSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exchange1.png"))); // NOI18N
        btnH_suaSach.setText("Sửa ");
        btnH_suaSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnH_suaSachActionPerformed(evt);
            }
        });

        btnH_luuSach.setBackground(new java.awt.Color(255, 204, 255));
        btnH_luuSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnH_luuSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/luu.png"))); // NOI18N
        btnH_luuSach.setText("Lưu");
        btnH_luuSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnH_luuSachActionPerformed(evt);
            }
        });

        btn_lamMoiSach.setBackground(new java.awt.Color(255, 204, 255));
        btn_lamMoiSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_lamMoiSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/nhapmoi.png"))); // NOI18N
        btn_lamMoiSach.setText("Làm mới");
        btn_lamMoiSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lamMoiSachActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 51, 0));
        jLabel24.setText("Mã thể loại:");

        Hc_maTheLoai.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        Hc_maTheLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Hc_maTheLoaiActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 51, 0));
        jLabel25.setText("Mã danh mục:");

        Hc_maDM.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        Hc_maDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Hc_maDMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel17))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(H_soLuongCon, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                                    .addComponent(H_namXB)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel14))
                                    .addComponent(jLabel23))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(H_tenSach, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                                            .addComponent(H_maSach)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(H_tenTheLoai)
                                        .addComponent(Hc_maTheLoai, 0, 263, Short.MAX_VALUE))))
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel25)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(H_nhaXB, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                            .addComponent(H_tenDM, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                            .addComponent(H_tomTat, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                            .addComponent(H_tacGia)
                            .addComponent(Hc_maDM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(64, 64, 64))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnH_themSach, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnH_luuSach, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(btnH_suaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(btn_lamMoiSach)
                        .addGap(176, 176, 176))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(H_maSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(H_tacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(H_nhaXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(H_tenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Hc_maTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25)
                        .addComponent(Hc_maDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel24))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(H_tenTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(H_tenDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel23)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(H_tomTat))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(H_soLuongCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17)))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(H_namXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnH_suaSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnH_luuSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnH_themSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_lamMoiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Quản lý Sách", jPanel8);

        jPanel9.setBackground(new java.awt.Color(204, 255, 204));

        tbl_DMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbl_DMSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_DMSach.setGridColor(new java.awt.Color(255, 255, 255));
        tbl_DMSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_DMSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_DMSach);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book.png"))); // NOI18N
        jLabel1.setText("Thông tin danh mục Sách:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel2.setText("Mã danh mục:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel3.setText("Tên danh mục:");

        txt_maDMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_maDMSach.setEnabled(false);
        txt_maDMSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maDMSachActionPerformed(evt);
            }
        });

        txt_tenDMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        btn_ThemDMSach.setBackground(new java.awt.Color(255, 204, 204));
        btn_ThemDMSach.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btn_ThemDMSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/them.png"))); // NOI18N
        btn_ThemDMSach.setText("Thêm");
        btn_ThemDMSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemDMSachActionPerformed(evt);
            }
        });

        txt_timkiemDMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_timkiemDMSach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_timkiemDMSachKeyReleased(evt);
            }
        });

        btnreset.setBackground(new java.awt.Color(255, 204, 204));
        btnreset.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnreset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/nhapmoi.png"))); // NOI18N
        btnreset.setText("Làm mới");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        btnluu.setBackground(new java.awt.Color(255, 204, 204));
        btnluu.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnluu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/luu.png"))); // NOI18N
        btnluu.setText("Lưu");
        btnluu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnluuActionPerformed(evt);
            }
        });

        btnsua.setBackground(new java.awt.Color(255, 204, 204));
        btnsua.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnsua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/sua.png"))); // NOI18N
        btnsua.setText("Sửa");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txt_maDMSach))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(15, 15, 15)
                                .addComponent(txt_tenDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btn_ThemDMSach, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(btnsua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnreset, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                    .addComponent(btnluu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(78, 78, 78))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(62, 62, 62)))))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txt_timkiemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_timkiemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_maDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_tenDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_ThemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnluu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnreset, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(btnsua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Quản lý Danh mục", jPanel9);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        jTP_main.addTab("          QUẢN LÝ SÁCH  ", new javax.swing.ImageIcon(getClass().getResource("/Icon/sach.png")), jPanel4); // NOI18N

        jPK_QuanLyPhieuMuon.setBackground(new java.awt.Color(204, 204, 255));

        jTPK_QuanLyPM.setForeground(new java.awt.Color(51, 0, 102));
        jTPK_QuanLyPM.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        Panel_DanhSachPM.setBackground(new java.awt.Color(204, 255, 204));
        Panel_DanhSachPM.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        tblK_DSPM.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblK_DSPM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblK_DSPM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblK_DSPMMouseClicked(evt);
            }
        });
        jSPK_DSPM.setViewportView(tblK_DSPM);

        btnK_themPM.setBackground(new java.awt.Color(255, 204, 204));
        btnK_themPM.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnK_themPM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus.png"))); // NOI18N
        btnK_themPM.setText("Thêm phiếu mượn");
        btnK_themPM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnK_themPMActionPerformed(evt);
            }
        });

        K_tieuDe.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        K_tieuDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bill.png"))); // NOI18N
        K_tieuDe.setText("CÁC PHIẾU MƯỢN ĐÃ ĐĂNG KÝ");

        javax.swing.GroupLayout Panel_DanhSachPMLayout = new javax.swing.GroupLayout(Panel_DanhSachPM);
        Panel_DanhSachPM.setLayout(Panel_DanhSachPMLayout);
        Panel_DanhSachPMLayout.setHorizontalGroup(
            Panel_DanhSachPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_DanhSachPMLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(Panel_DanhSachPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSPK_DSPM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 978, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnK_themPM, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29))
            .addGroup(Panel_DanhSachPMLayout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(K_tieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_DanhSachPMLayout.setVerticalGroup(
            Panel_DanhSachPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_DanhSachPMLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(K_tieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSPK_DSPM, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnK_themPM, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        jTPK_QuanLyPM.addTab("Danh sách phiếu mượn", Panel_DanhSachPM);

        Panel_ChiTietPM.setBackground(new java.awt.Color(204, 255, 204));

        jPanel12.setBackground(new java.awt.Color(204, 255, 204));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel26.setText("Mã phiếu mượn:");

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel27.setText("Mã sách:");

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel28.setText("Ngày thực trả:");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel30.setText("Tình trạng sách:");

        K_maPM.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        K_maPM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                K_maPMActionPerformed(evt);
            }
        });

        K_maSach.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

        K_tinhTrangSach.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        K_tinhTrangSach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bình thường", "Mất sách", "Hư hỏng" }));
        K_tinhTrangSach.setSelectedIndex(-1);
        K_tinhTrangSach.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                K_tinhTrangSachItemStateChanged(evt);
            }
        });
        K_tinhTrangSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                K_tinhTrangSachActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 0, 0));
        jLabel38.setText("* Chú ý: - Nếu sách bị hư hỏng, tiền phạt là 20.000đ");

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(102, 0, 0));
        jLabel39.setText("- Nếu sách bị mất, tiền phạt là 50.000đ");

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(102, 0, 0));
        jLabel40.setText("- Nếu trả sách quá hạn, tiền phạt bằng số ngày quá hạn * 10.000đ");

        K_ngayThucTra.setDateFormatString("yyyy-MM-dd");
        K_ngayThucTra.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        K_ngayThucTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                K_ngayThucTraMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                K_ngayThucTraMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(28, 28, 28)))))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(K_maSach, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                    .addComponent(K_maPM)
                    .addComponent(K_ngayThucTra, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(K_tinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel38)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(K_ngayThucTra, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(K_maPM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(K_tinhTrangSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel27)
                                .addComponent(K_maSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel39)))
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel40))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel28)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(204, 255, 204));

        tblK_ChiTiet.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tblK_ChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblK_ChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblK_ChiTietMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblK_ChiTiet);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        btnK_suaChiTiet.setBackground(new java.awt.Color(255, 204, 255));
        btnK_suaChiTiet.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btnK_suaChiTiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exchange1.png"))); // NOI18N
        btnK_suaChiTiet.setText("Sửa thông tin");
        btnK_suaChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnK_suaChiTietActionPerformed(evt);
            }
        });

        btnK_luuChiTiet.setBackground(new java.awt.Color(255, 204, 255));
        btnK_luuChiTiet.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btnK_luuChiTiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/luu.png"))); // NOI18N
        btnK_luuChiTiet.setText("Lưu ");
        btnK_luuChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnK_luuChiTietActionPerformed(evt);
            }
        });

        btnK_lamMoi.setBackground(new java.awt.Color(255, 204, 255));
        btnK_lamMoi.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btnK_lamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/nhapmoi.png"))); // NOI18N
        btnK_lamMoi.setText("Làm mới");
        btnK_lamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnK_lamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_ChiTietPMLayout = new javax.swing.GroupLayout(Panel_ChiTietPM);
        Panel_ChiTietPM.setLayout(Panel_ChiTietPMLayout);
        Panel_ChiTietPMLayout.setHorizontalGroup(
            Panel_ChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Panel_ChiTietPMLayout.createSequentialGroup()
                .addContainerGap(335, Short.MAX_VALUE)
                .addComponent(btnK_suaChiTiet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnK_luuChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(256, 256, 256)
                .addComponent(btnK_lamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        Panel_ChiTietPMLayout.setVerticalGroup(
            Panel_ChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ChiTietPMLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(Panel_ChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnK_suaChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnK_luuChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnK_lamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTPK_QuanLyPM.addTab("Chi tiết phiếu mượn", Panel_ChiTietPM);

        javax.swing.GroupLayout jPK_QuanLyPhieuMuonLayout = new javax.swing.GroupLayout(jPK_QuanLyPhieuMuon);
        jPK_QuanLyPhieuMuon.setLayout(jPK_QuanLyPhieuMuonLayout);
        jPK_QuanLyPhieuMuonLayout.setHorizontalGroup(
            jPK_QuanLyPhieuMuonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTPK_QuanLyPM)
        );
        jPK_QuanLyPhieuMuonLayout.setVerticalGroup(
            jPK_QuanLyPhieuMuonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTPK_QuanLyPM)
        );

        jTP_main.addTab(" QUẢN LÝ MƯỢN TRẢ ", new javax.swing.ImageIcon(getClass().getResource("/Icon/muontra.png")), jPK_QuanLyPhieuMuon); // NOI18N

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        jTabbedPane5.setForeground(new java.awt.Color(0, 0, 102));
        jTabbedPane5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jPanel14.setBackground(new java.awt.Color(204, 255, 204));
        jPanel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book.png"))); // NOI18N
        jLabel32.setText("Lựa chọn: ");

        cbb_chucNangThongKe.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbb_chucNangThongKe.setForeground(new java.awt.Color(0, 0, 153));
        cbb_chucNangThongKe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ðộc giả chưa trả sách", "Ðộc giả mượn quá hạn", "Ðộc giả mượn nhiều nhất" }));
        cbb_chucNangThongKe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_chucNangThongKeItemStateChanged(evt);
            }
        });

        tabletkbandoc.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tabletkbandoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(tabletkbandoc);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jLabel32)
                        .addGap(26, 26, 26)
                        .addComponent(cbb_chucNangThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 956, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_chucNangThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Thống kê độc giả", jPanel14);

        jPanel15.setBackground(new java.awt.Color(204, 255, 204));

        jLabel33.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book.png"))); // NOI18N
        jLabel33.setText("Lựa chọn:");

        cbb_chucNangThongKe1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbb_chucNangThongKe1.setForeground(new java.awt.Color(0, 0, 153));
        cbb_chucNangThongKe1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sách được mượn nhiều nhất theo ngày", "Tổng số sách được mượn theo từng tháng" }));
        cbb_chucNangThongKe1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_chucNangThongKe1ItemStateChanged(evt);
            }
        });

        tabletksach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tabletksach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(tabletksach);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18)
                        .addComponent(cbb_chucNangThongKe1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 897, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(140, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_chucNangThongKe1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167))
        );

        jTabbedPane5.addTab("Thống kê sách", jPanel15);

        jPanel16.setBackground(new java.awt.Color(204, 255, 204));

        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book.png"))); // NOI18N
        jLabel34.setText("Lựa chọn:");

        cbb_chucNangThongKe2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cbb_chucNangThongKe2.setForeground(new java.awt.Color(0, 0, 153));
        cbb_chucNangThongKe2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tổng tiền phạt theo tháng" }));

        tabletktienphat.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tabletktienphat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane9.setViewportView(tabletktienphat);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbb_chucNangThongKe2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbb_chucNangThongKe2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(211, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Thống kê tiền phạt", jPanel16);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane5)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );

        jTP_main.addTab("                   THỐNG KÊ ", new javax.swing.ImageIcon(getClass().getResource("/Icon/thongke.png")), jPanel6); // NOI18N

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));

        timkiem.setBackground(new java.awt.Color(204, 255, 204));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/searching.png"))); // NOI18N
        jLabel15.setText("Tìm kiếm sách:");

        textboxsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textboxsearchActionPerformed(evt);
            }
        });
        textboxsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textboxsearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textboxsearchKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setText("Lọc theo :");

        jComboBox3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Danh mục", "Chuyên ngành Điện-Điện tử", "Chuyên ngành Cơ khí", "Chuyên ngành Công nghệ thông tin", "Chuyên ngành Xây dựng", "Sách Tiếng Anh", "Kỹ năng sống" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jComboBox4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thể loại", "Giáo trình học", "Sách tham khảo", "Văn hóa lịch sử", "Chính trị, Pháp luật", "Tạp chí" }));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        tableSearchSach.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tableSearchSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên sách", "Tác giả", "Nhà sản xuất", "Số lượng"
            }
        ));
        jScrollPane5.setViewportView(tableSearchSach);

        jButton11.setBackground(new java.awt.Color(255, 204, 204));
        jButton11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/boloc.png"))); // NOI18N
        jButton11.setText("Bỏ lọc");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout timkiemLayout = new javax.swing.GroupLayout(timkiem);
        timkiem.setLayout(timkiemLayout);
        timkiemLayout.setHorizontalGroup(
            timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timkiemLayout.createSequentialGroup()
                .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timkiemLayout.createSequentialGroup()
                        .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(timkiemLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jLabel15)
                                .addGap(26, 26, 26))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timkiemLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)))
                        .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox4, 0, 353, Short.MAX_VALUE)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textboxsearch, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
                        .addGap(76, 76, 76)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 264, Short.MAX_VALUE))
                    .addGroup(timkiemLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane5)))
                .addContainerGap())
        );
        timkiemLayout.setVerticalGroup(
            timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timkiemLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textboxsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(36, 36, 36)
                .addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1067, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(timkiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(timkiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTP_main.addTab("                      TRA CỨU", new javax.swing.ImageIcon(getClass().getResource("/Icon/timkiem.png")), jPanel7); // NOI18N

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/logonuce.png"))); // NOI18N

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 102));

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel31.setText("TRƯỜNG ĐẠI HỌC XÂY DỰNG HÀ NỘI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jButton2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(305, 305, 305)
                                .addComponent(jLabel29))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(208, 208, 208)
                                .addComponent(jLabel31)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(73, 73, 73))))
            .addComponent(jTP_main, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel31))
                            .addComponent(jButton2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTP_main, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTP_main.getAccessibleContext().setAccessibleDescription("");

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1334, 1334, 1334)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_timkiemDMSachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_timkiemDMSachKeyReleased
        String query = txt_timkiemDMSach.getText();
        timkiem(query);
    }//GEN-LAST:event_txt_timkiemDMSachKeyReleased

    private void btn_ThemDMSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemDMSachActionPerformed

        enable_DM();
txt_maDMSach.setEditable(true);
        txt_maDMSach.setText("");
        txt_tenDMSach.setText("");
    }//GEN-LAST:event_btn_ThemDMSachActionPerformed

    private void tbl_DMSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DMSachMouseClicked
        int selectedRow = tbl_DMSach.getSelectedRow();
txt_maDMSach.setEditable(false);
        txt_maDMSach.setText((String) tbl_DMSach.getValueAt(selectedRow, 0));
        txt_tenDMSach.setText((String) tbl_DMSach.getValueAt(selectedRow, 1));


    }//GEN-LAST:event_tbl_DMSachMouseClicked

    private void Hc_maDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hc_maDMActionPerformed
        String sql = "SELECT tenDMSach FROM DanhMucSach WHERE maDMSach = ?";
        try {
            String maDMString = (String) Hc_maDM.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maDMString);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                H_tenDM.setText(rs.getString("tenDMSach"));
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_Hc_maDMActionPerformed

    private void Hc_maTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hc_maTheLoaiActionPerformed
        String sql = "SELECT tenTheLoai FROM TheLoai WHERE maTheLoai = ?";
        try {
            String maTheLoaiString = (String) Hc_maTheLoai.getSelectedItem();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maTheLoaiString);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                H_tenTheLoai.setText(rs.getString("tenTheLoai"));
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_Hc_maTheLoaiActionPerformed

    private void btn_lamMoiSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lamMoiSachActionPerformed
        refresh();
        disable_S();
        btnH_luuSach.setEnabled(false);
        btnH_themSach.setEnabled(true);
        btnH_suaSach.setEnabled(false);
    }//GEN-LAST:event_btn_lamMoiSachActionPerformed

    private void btnH_luuSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH_luuSachActionPerformed
        if (H_tenSach.getText().trim().equals("") || H_tenTheLoai.getText().trim().equals("") || H_tenDM.getText().trim().equals("") || H_namXB.getText().trim().equals("") || H_nhaXB.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");
        } else {
            int x = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm không?");
            if (x == JOptionPane.NO_OPTION) {
                return;
            } else {
                Sach sach = new Sach();

                sach.setTenSach(H_tenSach.getText());
                sach.setMaDMSach(Hc_maDM.getItemAt(Hc_maDM.getSelectedIndex()));
                sach.setMaTheLoai(Hc_maTheLoai.getItemAt(Hc_maTheLoai.getSelectedIndex()));
                sach.setTacGia(H_tacGia.getText());
                sach.setNXB(H_nhaXB.getText());
                sach.setNamXuatBan(Integer.parseInt(H_namXB.getText()));
                sach.setSoLuongCon(Integer.parseInt(H_soLuongCon.getText()));
                sach.setTomTatND(H_tomTat.getText());
                //sach.setAnh(H_linkAnh.getText());
                ;

                s_Service.addSach(sach);
                defaultTableModel_S.setRowCount(0);
                setTableData_S(s_Service.getDSSach());
            }
            refresh();
            btnH_luuSach.setEnabled(false);
            btnH_themSach.setEnabled(true);
            btnH_suaSach.setEnabled(false);

        }
    }//GEN-LAST:event_btnH_luuSachActionPerformed

    private void btnH_suaSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH_suaSachActionPerformed
        if (H_tenSach.getText().trim().equals("") || H_tenTheLoai.getText().trim().equals("") || H_tenDM.getText().trim().equals("") || H_namXB.getText().trim().equals("") || H_nhaXB.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin sách muốn thay đổi!");
        } else {
            int x = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thay đổi không?");
            if (x == JOptionPane.NO_OPTION) {
                return;
            } else {
                Sach sach = new Sach();

                sach.setMaSach(H_maSach.getText());
                sach.setTenSach(H_tenSach.getText());
                sach.setMaDMSach(Hc_maDM.getItemAt(Hc_maDM.getSelectedIndex()));
                sach.setMaTheLoai(Hc_maTheLoai.getItemAt(Hc_maTheLoai.getSelectedIndex()));
                sach.setTacGia(H_tacGia.getText());
                sach.setNXB(H_nhaXB.getText());
                sach.setNamXuatBan(Integer.parseInt(H_namXB.getText()));
                sach.setSoLuongCon(Integer.parseInt(H_soLuongCon.getText()));
                sach.setTomTatND(H_tomTat.getText());

                s_Service.updateSach(sach);
                defaultTableModel_S.setRowCount(0);
                setTableData_S(s_Service.getDSSach());
            }
        }
        btnH_themSach.setEnabled(true);

        btnH_suaSach.setEnabled(false);
        btnH_luuSach.setEnabled(false);
        disable_S();
    }//GEN-LAST:event_btnH_suaSachActionPerformed

    private void btnH_themSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnH_themSachActionPerformed
        enable_S();
        refresh();
        H_tenSach.requestFocus();
        btnH_luuSach.setEnabled(true);
        btnH_suaSach.setEnabled(false);

        btnH_themSach.setEnabled(false);
    }//GEN-LAST:event_btnH_themSachActionPerformed

    private void tblH_SachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblH_SachMouseClicked
        int selectedRow = tblH_Sach.getSelectedRow();
        //TableModel model = tblH_Sach.getModel();
        enable_S();

        H_maSach.setText((String) tblH_Sach.getValueAt(selectedRow, 0));
        H_tenSach.setText((String) tblH_Sach.getValueAt(selectedRow, 1));
        Hc_maDM.setSelectedItem((String) tblH_Sach.getValueAt(selectedRow, 2));
        Hc_maTheLoai.setSelectedItem((String) tblH_Sach.getValueAt(selectedRow, 3));
        H_tacGia.setText((String) tblH_Sach.getValueAt(selectedRow, 4));
        H_nhaXB.setText((String) tblH_Sach.getValueAt(selectedRow, 5));
        H_namXB.setText(tblH_Sach.getValueAt(selectedRow, 6).toString());
        H_soLuongCon.setText(tblH_Sach.getValueAt(selectedRow, 7).toString());
        H_tomTat.setText(tblH_Sach.getValueAt(selectedRow, 8).toString());

        enable_S();
        btnH_themSach.setEnabled(false);
        H_maSach.setEnabled(false);
        btnH_suaSach.setEnabled(true);
        btnH_luuSach.setEnabled(false);
        // btnH_xoaSach.setEnabled(true);
    }//GEN-LAST:event_tblH_SachMouseClicked

    private void K_tinhTrangSachItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_K_tinhTrangSachItemStateChanged
//        
    }//GEN-LAST:event_K_tinhTrangSachItemStateChanged

    private void tblK_DSPMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblK_DSPMMouseClicked
        // TODO add your handling code here:
        jTPK_QuanLyPM.setSelectedIndex(1);
        int selectedRow = tblK_DSPM.getSelectedRow();
        K_maPM.setText(tblK_DSPM.getValueAt(selectedRow, 0).toString());
        loadData_TblCTPM();

    }//GEN-LAST:event_tblK_DSPMMouseClicked

    private void tblK_ChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblK_ChiTietMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblK_ChiTiet.getSelectedRow();
        K_maSach.setText(tblK_ChiTiet.getValueAt(selectedRow, 1).toString());
        ((JTextField) K_ngayThucTra.getDateEditor().getUiComponent()).setText("");
        try {
            ((JTextField) K_ngayThucTra.getDateEditor().getUiComponent()).setText(tblK_ChiTiet.getValueAt(selectedRow, 2).toString());
        } catch (Exception e) {

        }
        K_tinhTrangSach.setSelectedItem(tblK_ChiTiet.getValueAt(selectedRow, 3));
        btnK_suaChiTiet.setEnabled(true);
    }//GEN-LAST:event_tblK_ChiTietMouseClicked

    private void btnK_suaChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnK_suaChiTietActionPerformed
        // TODO add your handling code here:
        if (K_ngayThucTra.getDate() == null) {
            K_ngayThucTra.setEnabled(true);
            K_tinhTrangSach.setEnabled(true);
        } else {
            K_ngayThucTra.setEnabled(false);
            K_tinhTrangSach.setEnabled(false);
        }

        btnK_luuChiTiet.setEnabled(true);
        btnK_suaChiTiet.setEnabled(true);
    }//GEN-LAST:event_btnK_suaChiTietActionPerformed

    private void btnK_luuChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnK_luuChiTietActionPerformed
        // TODO add your handling code here:
        int soNgayTre = getSoNgayTre(K_maPM.getText(), K_maSach.getText(), new java.sql.Date(K_ngayThucTra.getDate().getTime()).toString());
        this.tienPhat = soNgayTre * 10000;
        ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon();
        ctpm.setMaPM(K_maPM.getText());
        ctpm.setMaSach(K_maSach.getText());
        ctpm.setNgayThucTra(new java.sql.Date(K_ngayThucTra.getDate().getTime()).toString());
        ctpm.setTinhTrangSach(K_tinhTrangSach.getSelectedItem().toString());
        ctpm.setTienPhat(this.tienPhat);

        ctpmsService.updateChiTietPM(ctpm);
        loadData_TblCTPM();
        Refresh_CTPM();
    }//GEN-LAST:event_btnK_luuChiTietActionPerformed

    private void btnK_lamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnK_lamMoiActionPerformed
        // TODO add your handling code here:
        Refresh_CTPM();
    }//GEN-LAST:event_btnK_lamMoiActionPerformed

    private void btnK_themPMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnK_themPMActionPerformed
        // TODO add your handling code here:
        TrangChuThuThu_QLPM qlPM = new TrangChuThuThu_QLPM();
        qlPM.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnK_themPMActionPerformed

    private void textSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textSearchKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textSearchKeyTyped

    private void tableDocgiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDocgiaMouseClicked
        // int selectedRow = tableDocgia.getSelectedRow();
        DefaultTableModel tbxtt = (DefaultTableModel) tableDocgia.getModel();
        int selected = tableDocgia.getSelectedRow();
        if (selected != -1) {
            String madg = tbxtt.getValueAt(selected, 0).toString();
            String tenDg = tbxtt.getValueAt(selected, 1).toString();
            String ngaySinh = tbxtt.getValueAt(selected, 2).toString();
            String gioTinh = tbxtt.getValueAt(selected, 3).toString();
            String email = tbxtt.getValueAt(selected, 4).toString();
            String sdt = tbxtt.getValueAt(selected, 5).toString();
            if (gioTinh.equals("Nam")) {
                gioitinhnam.setSelected(true);
            } else {
                gioitinhnu.setSelected(true);
            }
            this.madg.setText(madg);
            this.madg.setEnabled(false);
            tenTaikhoan.setText(tenDg);
            ngaysinh.setText(ngaySinh);
            emailDocgia.setText(email);
            this.sdt.setText(sdt);
            int i = accessDocGia.getOneDocgia(tbxtt.getValueAt(selected, 0).toString());
            if (i == 0) {
                mokhoa.setEnabled(true);
                khoatk.setEnabled(false);
            } else {
                khoatk.setEnabled(true);
                mokhoa.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui Lòng Chọn ", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_tableDocgiaMouseClicked

    private void sdtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sdtKeyPressed
        int key = evt.getKeyCode();
        System.out.println(key);
        if ((key < 48 || key > 57) && key != 8 && (key < 96 || key > 105)) {
            if (key == 10) {
                ngaysinh.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Số Điện Thoại không Chứ Ký Tự Khác Số", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                sdt.setBorder(new LineBorder(Color.red, 1));
            }
        }
    }//GEN-LAST:event_sdtKeyPressed

    private void sdtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sdtKeyTyped
        String sodt = sdt.getText();
        if (sodt.length() > 11 || sodt.length() < 9) {
            sdt.setBorder(new LineBorder(Color.red, 1));
        } else {
            sdt.setBorder(new LineBorder(Color.green, 1));
        }

    }//GEN-LAST:event_sdtKeyTyped

    private void themmoidgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themmoidgActionPerformed
        String madg = this.madg.getText();
        String tendg = tenTaikhoan.getText();
        String email = emailDocgia.getText();
        String sdt = this.sdt.getText();
        String ngaysinh = this.ngaysinh.getText();
        int gioitinh;

        if (gioitinhnam.isSelected()) {
            gioitinh = 1;
        } else {
            gioitinh = 2;
        }

        if (madg.equals("") || tendg.equals("") || email.equals("") || sdt.equals("") || ngaysinh.equals("")) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");
            return;
        }
        Docgia dg = new Docgia(madg, tendg, ngaysinh, gioitinh, email, sdt);
        DefaultTableModel tbadddg = (DefaultTableModel) tableDocgia.getModel();
        tbadddg.setRowCount(0);
        DefaultTableModel tbxttdg = (DefaultTableModel) tablexemttdg.getModel();
        int addcheck = accessDocGia.addDocgia(dg);
        if (addcheck != 0) {
            this.loaddg();
            tbadddg.addRow(new Object[]{madg, tendg, ngaysinh, (gioitinh == 1 ? "Nam" : "Nữ"), email, sdt});
            tbxttdg.addRow(new Object[]{madg, tendg, ngaysinh, (gioitinh == 1 ? "Nam" : "Nữ"), email, sdt});
            JOptionPane.showMessageDialog(null, "Đã thêm thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Thông tin chưa chính xác hoặc mã độc giả đã trùng, Thêm thất bại");
        }
        loaddg();
    }//GEN-LAST:event_themmoidgActionPerformed

    private void updatedgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatedgActionPerformed
        DefaultTableModel tbxtt = (DefaultTableModel) tableDocgia.getModel();
        int selected = tableDocgia.getSelectedRow();
        if (selected != -1) {
            String madg = tbxtt.getValueAt(selected, 0).toString();
             
            String tenDg = tenTaikhoan.getText();
            String ngaySinh = ngaysinh.getText();
            int gioitinh;
            if (gioitinhnam.isSelected()) {
                gioitinh = 1;
            } else {
                gioitinh = 2;
            }
            String email = emailDocgia.getText();
            String sdt = this.sdt.getText();

            int addcheck = accessDocGia.updateDocgia(new Docgia(madg, tenDg, ngaySinh, gioitinh, email, sdt));
            if (addcheck != 0) {
                this.loaddg();
                JOptionPane.showMessageDialog(null, "Sửa thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thông tin chưa chính xác, Sửa thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui Lòng Chọn ", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_updatedgActionPerformed

    private void khoatkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_khoatkActionPerformed
        DefaultTableModel tbxtt = (DefaultTableModel) tableDocgia.getModel();
        int selected = tableDocgia.getSelectedRow();
        if (selected != -1) {
            String madg = tbxtt.getValueAt(selected, 0).toString();
            int xacnhan = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn khóa độc giả  ?", "Xác nhận khóa",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (xacnhan == 0) {
                if (accessDocGia.deleteDocgia(madg) != 0) {
//                    tbxtt.removeRow(selected);
//                    tbxtt.setRow
                    tenTaikhoan.setText("");
                    ngaysinh.setText("");
                    emailDocgia.setText("");
                    this.sdt.setText("");
                    khoatk.setEnabled(false);
                    mokhoa.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Đã khóa", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Có Lỗi", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui Lòng Chọn ", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_khoatkActionPerformed

    private void cbb_chucNangThongKeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_chucNangThongKeItemStateChanged

        try {
            int index = cbb_chucNangThongKe.getSelectedIndex();
            if (index == 0) {
                ResultSet chuatrasach = getThongke.chuatrasach();
                DefaultTableModel tkdg = (DefaultTableModel) tabletkbandoc.getModel();
                tkdg.setColumnCount(0);
                tkdg.addColumn("Mã phiếu mượn");
                tkdg.addColumn("Mã độc giả");
                tkdg.addColumn("Tên độc giả");
                
                tkdg.setRowCount(0);
                while (chuatrasach.next()) {
                    tkdg.addRow(new Object[]{chuatrasach.getString(1), chuatrasach.getString(2),chuatrasach.getString(3)});
                }
            }
            if (index == 1) {
                ResultSet quahan = getThongke.quahantra();
                DefaultTableModel tkdg = (DefaultTableModel) tabletkbandoc.getModel();
                tkdg.setColumnCount(0);
                tkdg.addColumn("Tên độc giả");
                tkdg.addColumn("Ngày mượn");
                tkdg.addColumn("Số ngày mượn");
                tkdg.addColumn("Ngày thực trả");
                tkdg.addColumn("Số ngày quá hạn");

                tkdg.setRowCount(0);
                while (quahan.next()) {
                    tkdg.addRow(new Object[]{quahan.getString(1), quahan.getString(2), quahan.getInt(3), quahan.getString(4), quahan.getInt(5)});
                }
            }
            if (index == 2) {
                ResultSet quahan = getThongke.muonnhiunhat();
                DefaultTableModel tkdg = (DefaultTableModel) tabletkbandoc.getModel();
                tkdg.setColumnCount(0);
                tkdg.addColumn("Tên độc giả");
                tkdg.addColumn("Số lần mượn");
                tkdg.setRowCount(0);
                while (quahan.next()) {
                    tkdg.addRow(new Object[]{quahan.getString(2), quahan.getInt(1)});
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuThuThu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbb_chucNangThongKeItemStateChanged

    private void cbb_chucNangThongKe1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_chucNangThongKe1ItemStateChanged

        try {
            int index = cbb_chucNangThongKe1.getSelectedIndex();
            if (index == 0) {
                ResultSet sachmuonnhiunhat = getThongke.sachdocnhiunhat();
                DefaultTableModel sach = (DefaultTableModel) tabletksach.getModel();

                sach.setColumnCount(0);
                sach.addColumn("Mã sách");
                sach.addColumn("Tên sách");
                sach.addColumn("Số lượng mượn");
                sach.addColumn("Ngày mượn");

                sach.setRowCount(0);
                while (sachmuonnhiunhat.next()) {

                    sach.addRow(new Object[]{sachmuonnhiunhat.getString(2), sachmuonnhiunhat.getString(3), sachmuonnhiunhat.getInt(1), sachmuonnhiunhat.getString(4)});
                }
            }
            if (index == 1) {
                ResultSet quahan = getThongke.sachnhatthang();
                DefaultTableModel tkdg = (DefaultTableModel) tabletksach.getModel();
                tkdg.setColumnCount(0);
                tkdg.addColumn("Tháng");
                tkdg.addColumn("Tổng Sách Được Mượn");

                tkdg.setRowCount(0);
                while (quahan.next()) {
                    tkdg.addRow(new Object[]{quahan.getString(2), quahan.getInt(1)});
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(TrangChuThuThu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbb_chucNangThongKe1ItemStateChanged

    private void textboxsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textboxsearchKeyReleased
        String textSearch = textboxsearch.getText();
        List<sach_th> searchSach = getSach.getAllSach();
        List<sach_th> resultSearch = new ArrayList<>();
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        int i = 0;
        for (sach_th s : searchSach) {
            if (s.getTenSach().toLowerCase().contains(textSearch.toLowerCase())) {
                resultSearch.add(s);
            }
        }
        for (sach_th s : resultSearch) {
            i++;
            sachtb.addRow(new Object[]{i, s.getTenSach(), s.getTacgia(), s.getNhaSB(), s.getSoluong()});
        }
    }//GEN-LAST:event_textboxsearchKeyReleased

    private void textboxsearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textboxsearchKeyTyped

    }//GEN-LAST:event_textboxsearchKeyTyped

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        String DM = "DM00";
        int index = jComboBox3.getSelectedIndex();
        if (index == 0) {
            return;
        }
        DM += Integer.toString(index);
        List<sach_th> sachByCate = getSach.getSachByCategory(DM);
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        int i = 0;
        for (sach_th s : sachByCate) {
            i++;
            sachtb.addRow(new Object[]{i, s.getTenSach(), s.getTacgia(), s.getNhaSB(), s.getSoluong()});
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        // TODO add your handling code here:
        String TL = "TL00";
        int index = jComboBox4.getSelectedIndex();
        if (index == 0) {
            return;
        }
        TL += Integer.toString(index);
        List<sach_th> sachBytheloai = getSach.getSachByTheloai(TL);
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        int i = 0;
        for (sach_th s : sachBytheloai) {
            i++;
            sachtb.addRow(new Object[]{i, s.getTenSach(), s.getTacgia(), s.getNhaSB(), s.getSoluong()});
        }
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jTP_mainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTP_mainMouseClicked
        try {
            ResultSet chuatrasach = getThongke.chuatrasach();
            DefaultTableModel tkdg = (DefaultTableModel) tabletkbandoc.getModel();
            tkdg.setColumnCount(0);
            tkdg.addColumn("Mã phiếu mượn");
            tkdg.addColumn("Mã độc giả");
            tkdg.addColumn("Tên độc giả");
            tkdg.setRowCount(0);
            while (chuatrasach.next()) {
                tkdg.addRow(new Object[]{chuatrasach.getString(1), chuatrasach.getString(2),chuatrasach.getString(3)});
            }

            ResultSet sachmuonnhiunhat = getThongke.sachdocnhiunhat();
            DefaultTableModel sach = (DefaultTableModel) tabletksach.getModel();
            sach.setColumnCount(0);
            sach.addColumn("Mã sách");
            sach.addColumn("Tên sách");
            sach.addColumn("Số lần mượn sách");
            sach.addColumn("Ngày mượn");
            sach.setRowCount(0);
            while (sachmuonnhiunhat.next()) {

                sach.addRow(new Object[]{sachmuonnhiunhat.getString(2), sachmuonnhiunhat.getString(3), sachmuonnhiunhat.getInt(1), sachmuonnhiunhat.getString(4)});
            }

            DefaultTableModel tien = (DefaultTableModel) tabletktienphat.getModel();
            tien.setColumnCount(0);
            tien.addColumn("Tháng");
            tien.addColumn("Tổng tiền");

            ResultSet tienphat = getThongke.tienphatthang();

            tien.setRowCount(0);
            while (tienphat.next()) {
                tien.addRow(new Object[]{tienphat.getString(1), tienphat.getString(2)});
            }

        } catch (SQLException ex) {
            Logger.getLogger(TrangChuThuThu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jTP_mainMouseClicked

    private void textboxsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textboxsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textboxsearchActionPerformed

    private void textSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textSearchKeyReleased

        String matk = textSearch.getText();
        DefaultTableModel tbxtt = (DefaultTableModel) tablexemttdg.getModel();
        tbxtt.setRowCount(0);
        List<Docgia> searchDocgia = accessDocGia.getAllDocgiar();
        List<Docgia> resultSearch = new ArrayList<>();
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        
        for (Docgia d : searchDocgia) {
            if (d.getMaTk().toLowerCase().contains(matk.toLowerCase())) {
                resultSearch.add(d);
            }
            else if (d.getTenTk().toLowerCase().contains(matk.toLowerCase())) {
                resultSearch.add(d);
            }
        }
       
//         resultSearch.forEach((d) -> 
//         {
//            String gt = "Nam";
//            if (d.getGioiTinh() == 2) {
//                gt = "Nữ";
//            }}
        for (Docgia d : resultSearch) 
        {
            
         
           String gt = "Nam";
           if (d.getGioiTinh() == 2) {
               gt = "Nữ";
           }
            
           tbxtt.addRow(new Object[]{d.getMaTk(), d.getTenTk(), d.getNgaySinh(),gt, d.getEmail(), d.getSdt()//d.getGioiTinh(),
           
            }); 
        }
        //loaddg();
    }//GEN-LAST:event_textSearchKeyReleased
                 
    private void mokhoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mokhoaActionPerformed
        DefaultTableModel tbxtt = (DefaultTableModel) tableDocgia.getModel();
        int selected = tableDocgia.getSelectedRow();
        if (selected != -1) {
            String madg = tbxtt.getValueAt(selected, 0).toString();
            int xacnhan = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn mở tài khoản ?", "Xác nhận khóa",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (xacnhan == 0) {
                if (accessDocGia.unlock(madg) != 0) {
//                    tbxtt.removeRow(selected);
//                    tbxtt.setRow
                    tenTaikhoan.setText("");
                    ngaysinh.setText("");
                    emailDocgia.setText("");
                    this.sdt.setText("");
                    JOptionPane.showMessageDialog(null, "Đã mở khóa", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Có Lỗi", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Trước Khi Mở khóa", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_mokhoaActionPerformed

    private void tablexemttdgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablexemttdgMouseClicked
        DefaultTableModel tbxtt = (DefaultTableModel) tablexemttdg.getModel();
        DefaultTableModel tbx1 = (DefaultTableModel) tableDocgia.getModel();
        int selected = tablexemttdg.getSelectedRow();
        if (selected != -1) {
            quanlyttdg.setSelectedIndex(1);
            String matk = tbxtt.getValueAt(selected, 0).toString();
            String tenDg = tbxtt.getValueAt(selected, 1).toString();
            String ngaySinh = tbxtt.getValueAt(selected, 2).toString();
            String gioTinh = tbxtt.getValueAt(selected, 3).toString();
            String email = tbxtt.getValueAt(selected, 4).toString();
            String sdt = tbxtt.getValueAt(selected, 5).toString();
            if (gioTinh.equals("Nam")) {
                gioitinhnam.setSelected(true);
            } else {
                gioitinhnu.setSelected(true);
            }
            this.madg.setEditable(false);
            tenTaikhoan.setText(tenDg);
            ngaysinh.setText(ngaySinh);
            emailDocgia.setText(email);
            this.sdt.setText(sdt);

            int i = accessDocGia.getOneDocgia(tbxtt.getValueAt(selected, 0).toString());
            if (i == 0) {
                mokhoa.setEnabled(true);
                khoatk.setEnabled(false);
            } else {
                khoatk.setEnabled(true);
                mokhoa.setEnabled(false);
            }
            tbx1.setRowCount(0);
            tbx1.addRow(new Object[]{matk, tenDg, ngaySinh, gioTinh, email, sdt});
            quanlyttdg.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ! ", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
        tbxtt.setRowCount(0);
        loaddg();
    }//GEN-LAST:event_tablexemttdgMouseClicked

    private void mokhoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mokhoa1ActionPerformed

        this.madg.setEnabled(true);
        this.madg.setText("");
        this.loaddg();

    }//GEN-LAST:event_mokhoa1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        new TrangChuChinh().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
    private String cutChar(String arry) {
        return arry.replaceAll("\\D+", "");
    }

    private void sdtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sdtKeyReleased
        sdt.setText(cutChar(sdt.getText()).trim());
        sdt.setVisible(true);
    }//GEN-LAST:event_sdtKeyReleased

    private void ngaysinhFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ngaysinhFocusLost
        String dateString = ngaysinh.getText();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false); // set false để kiểm tra tính hợp lệ của date. VD: tháng 2 phải có 28-29 ngày, năm có 12 tháng,....
        try {
            df.parse(dateString); // parse dateString thành kiểu Date
        } catch (ParseException e) { // quăng lỗi nếu dateString ko hợp lệ
            JOptionPane.showMessageDialog(this, "Sai định dạng! Nhâp lại! (VD: 2001-01-01");
            ngaysinh.requestFocus();
            ngaysinh.setText("");
        }
    }//GEN-LAST:event_ngaysinhFocusLost

    private void madgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_madgKeyPressed
        if (evt.getKeyCode() == 10)
            tenTaikhoan.requestFocus();
    }//GEN-LAST:event_madgKeyPressed

    private void tenTaikhoanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tenTaikhoanKeyPressed
        if (evt.getKeyCode() == 10)
            sdt.requestFocus();
    }//GEN-LAST:event_tenTaikhoanKeyPressed

    private void ngaysinhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ngaysinhKeyPressed
        if (evt.getKeyCode() == 10)
            emailDocgia.requestFocus();
    }//GEN-LAST:event_ngaysinhKeyPressed

    private void K_ngayThucTraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_K_ngayThucTraMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_K_ngayThucTraMousePressed

    private void K_ngayThucTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_K_ngayThucTraMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_K_ngayThucTraMouseClicked

    private void K_maPMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_K_maPMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_K_maPMActionPerformed

    private void K_tinhTrangSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_K_tinhTrangSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_K_tinhTrangSachActionPerformed

    private void madgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_madgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_madgActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        // TODO add your handling code here:
        enable_DM();
        txt_maDMSach.setEditable(true);
        txt_maDMSach.setText("");
        txt_tenDMSach.setText("");
    }//GEN-LAST:event_btnresetActionPerformed

    private void txt_maDMSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maDMSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maDMSachActionPerformed

    private void btnluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnluuActionPerformed
        // TODO add your handling code here:
        if (txt_maDMSach.getText().trim().equals("") || txt_tenDMSach.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");

        } else {
            int x = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm không?");
            if (x == JOptionPane.NO_OPTION) {
                return;
            } else {
                DanhMucSach danhmuc = new DanhMucSach();
                danhmuc.setMaDM(txt_maDMSach.getText());
                danhmuc.setTenDM(txt_tenDMSach.getText());

                dms_Service.addDanhMucSach(danhmuc, txt_maDMSach.getText());
                defaultTableModel_DM.setRowCount(0);
                setTableData_DM(dms_Service.getDSDanhMucSach());

            }
        }
        loadmaDanhMuc();
    }//GEN-LAST:event_btnluuActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        // TODO add your handling code here:

        if (txt_maDMSach.getText().trim().equals("") || txt_tenDMSach.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin danh mục muốn sửa muốn sửa!");
        } else {
            int x = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa không?");
            if (x == JOptionPane.NO_OPTION) {
                return;
            } else {
                DanhMucSach danhmuc = new DanhMucSach();
                danhmuc.setMaDM(txt_maDMSach.getText());
                danhmuc.setTenDM(txt_tenDMSach.getText());

                dms_Service.updateDanhMucSach(danhmuc);
                defaultTableModel_DM.setRowCount(0);
                setTableData_DM(dms_Service.getDSDanhMucSach());
            }
        }
        disable_DM();

        txt_maDMSach.setText("");
        txt_tenDMSach.setText("");
    }//GEN-LAST:event_btnsuaActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String textSearch = textboxsearch.getText();
        List<sach_th> searchSach = getSach.getAllSach();
        List<sach_th> resultSearch = new ArrayList<>();
        DefaultTableModel sachtb = (DefaultTableModel) tableSearchSach.getModel();
        sachtb.setRowCount(0);
        int i = 0;
        for (sach_th s : searchSach) {
            if (s.getTenSach().toLowerCase().contains(textSearch.toLowerCase())){
                resultSearch.add(s);
            }
        }
        for (sach_th s : resultSearch) {
            i++;
            sachtb.addRow(new Object[] {i, s.getTenSach(), s.getTacgia(), s.getNhaSB(), s.getSoluong()});
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        textSearch.setText("");
        this.loaddg();        // TODO add your handling code here:
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void textSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSearchActionPerformed
        
    }//GEN-LAST:event_textSearchActionPerformed

    private void timkiem(String query) {
        TableRowSorter<DefaultTableModel> tbl = new TableRowSorter<DefaultTableModel>(defaultTableModel_DM);
        tbl_DMSach.setRowSorter(tbl);
        tbl.setRowFilter(RowFilter.regexFilter(query));

    }

    private void setTableData_DSPM(List<PhieuMuon> pms) {
        for (PhieuMuon pm : pms) {
            defaultTableModel_DSPM.addRow(new Object[]{pm.getMaPM(), pm.getMaDocGia(), pm.getMaThuthu(),
                pm.getNgayMuon(), pm.getSoNgayMuon(), pm.getGhiChu(), pm.getTrangThai()});
        }
    }

    private void setTableData_CTPM(List<ChiTietPhieuMuon> ctpms) {
        for (ChiTietPhieuMuon ctpm : ctpms) {
            defaultTableModel_CTPM.addRow(new Object[]{ctpm.getMaPM(), ctpm.getMaSach(), ctpm.getNgayThucTra(),
                ctpm.getTinhTrangSach(), ctpm.getTienPhat()});
        }
    }

    private void disable_CTPM() {
        K_maPM.setEnabled(false);
        K_ngayThucTra.setEnabled(false);
        K_maSach.setEnabled(false);
        K_tinhTrangSach.setEnabled(false);
//        K_tienPhat.setEnabled(false);

        btnK_suaChiTiet.setEnabled(false);
        btnK_luuChiTiet.setEnabled(false);
    }

    private void loadData_TblDSPM() {
        tblK_DSPM.setModel(defaultTableModel_DSPM);
        defaultTableModel_DSPM.addColumn("Mã phiếu mượn");
        defaultTableModel_DSPM.addColumn("Tên độc giả");
        defaultTableModel_DSPM.addColumn("Tên thủ thư");
        defaultTableModel_DSPM.addColumn("Ngày mượn");
        defaultTableModel_DSPM.addColumn("Số ngày mượn");
        defaultTableModel_DSPM.addColumn("Ghi chú");
        defaultTableModel_DSPM.addColumn("Trạng thái");

        setTableData_DSPM(pmsService.getDSPhieuMuon());
    }

    private void loadData_TblCTPM() {
        tblK_ChiTiet.setModel(defaultTableModel_CTPM);
        defaultTableModel_CTPM.setColumnCount(0);
        defaultTableModel_CTPM.setRowCount(0);
        defaultTableModel_CTPM.addColumn("Mã phiếu mượn");
        defaultTableModel_CTPM.addColumn("Mã sách");
        defaultTableModel_CTPM.addColumn("Ngày thực trả");
        defaultTableModel_CTPM.addColumn("Tình trạng sách");
        defaultTableModel_CTPM.addColumn("Tiền phạt");
        DesignTbl_CTPM();

        setTableData_CTPM(ctpmsService.getDSCHiTietPM(K_maPM.getText()));
    }

    private void DesignTbl_DSPM() {
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblK_DSPM.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(0);
        tblK_DSPM.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tblK_DSPM.setRowHeight(30);

        TableColumnModel columnModel_DSPM = tblK_DSPM.getColumnModel();
        columnModel_DSPM.getColumn(0).setPreferredWidth(200);
        columnModel_DSPM.getColumn(1).setPreferredWidth(225);
        columnModel_DSPM.getColumn(2).setPreferredWidth(225);
        columnModel_DSPM.getColumn(3).setPreferredWidth(200);
        columnModel_DSPM.getColumn(4).setPreferredWidth(200);
        columnModel_DSPM.getColumn(5).setPreferredWidth(200);
        columnModel_DSPM.getColumn(6).setPreferredWidth(150);
    }

    private void DesignTbl_CTPM() {
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblK_ChiTiet.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(0);
        tblK_ChiTiet.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tblK_ChiTiet.setRowHeight(30);

        TableColumnModel columnModel_DSPM = tblK_ChiTiet.getColumnModel();
        columnModel_DSPM.getColumn(0).setPreferredWidth(200);
        columnModel_DSPM.getColumn(1).setPreferredWidth(200);
        columnModel_DSPM.getColumn(2).setPreferredWidth(200);
        columnModel_DSPM.getColumn(3).setPreferredWidth(200);
        columnModel_DSPM.getColumn(4).setPreferredWidth(200);
    }

    private void Refresh_CTPM() {
        ((JTextField) K_ngayThucTra.getDateEditor().getUiComponent()).setText("");
//        K_tienPhat.setText("");
       K_ngayThucTra.setEnabled(false);
        K_tinhTrangSach.setEnabled(false);
//        K_tienPhat.setEnabled(false);
       btnK_suaChiTiet.setEnabled(false);
        btnK_luuChiTiet.setEnabled(false);
        K_maSach.setText("");
        loadData_TblCTPM();
    }

    public void setDSPM() {
        jTP_main.setSelectedIndex(2);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrangChuThuThu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField H_maSach;
    private javax.swing.JTextField H_namXB;
    private javax.swing.JTextField H_nhaXB;
    private javax.swing.JTextField H_soLuongCon;
    private javax.swing.JTextField H_tacGia;
    private javax.swing.JTextField H_tenDM;
    private javax.swing.JTextField H_tenSach;
    private javax.swing.JTextField H_tenTheLoai;
    private javax.swing.JTextField H_tomTat;
    private javax.swing.JComboBox<String> Hc_maDM;
    private javax.swing.JComboBox<String> Hc_maTheLoai;
    private javax.swing.JTextField K_maPM;
    private javax.swing.JTextField K_maSach;
    private com.toedter.calendar.JDateChooser K_ngayThucTra;
    private javax.swing.JLabel K_tieuDe;
    private javax.swing.JComboBox<String> K_tinhTrangSach;
    private javax.swing.JPanel Panel_ChiTietPM;
    private javax.swing.JPanel Panel_DanhSachPM;
    private javax.swing.JButton btnH_luuSach;
    private javax.swing.JButton btnH_suaSach;
    private javax.swing.JButton btnH_themSach;
    private javax.swing.JButton btnK_lamMoi;
    private javax.swing.JButton btnK_luuChiTiet;
    private javax.swing.JButton btnK_suaChiTiet;
    private javax.swing.JButton btnK_themPM;
    private javax.swing.JButton btn_ThemDMSach;
    private javax.swing.JButton btn_lamMoiSach;
    private javax.swing.JButton btnlammoi;
    private javax.swing.JButton btnluu;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnsua;
    private javax.swing.JComboBox<String> cbb_chucNangThongKe;
    private javax.swing.JComboBox<String> cbb_chucNangThongKe1;
    private javax.swing.JComboBox<String> cbb_chucNangThongKe2;
    private javax.swing.JTextField emailDocgia;
    private javax.swing.ButtonGroup gioitinhbtngroup;
    private javax.swing.JRadioButton gioitinhnam;
    private javax.swing.JRadioButton gioitinhnu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPK_QuanLyPhieuMuon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jSPK_DSPM;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTPK_QuanLyPM;
    private javax.swing.JTabbedPane jTP_main;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JButton khoatk;
    private javax.swing.JTextField madg;
    private javax.swing.JButton mokhoa;
    private javax.swing.JButton mokhoa1;
    private javax.swing.JTextField ngaysinh;
    private javax.swing.JTabbedPane quanlyttdg;
    private javax.swing.JTextField sdt;
    private javax.swing.JTable tableDocgia;
    private javax.swing.JTable tableSearchSach;
    private javax.swing.JTable tabletkbandoc;
    private javax.swing.JTable tabletksach;
    private javax.swing.JTable tabletktienphat;
    private javax.swing.JTable tablexemttdg;
    private javax.swing.JTable tblH_Sach;
    private javax.swing.JTable tblK_ChiTiet;
    private javax.swing.JTable tblK_DSPM;
    private javax.swing.JTable tbl_DMSach;
    private javax.swing.JTextField tenTaikhoan;
    private javax.swing.JTextField textSearch;
    private javax.swing.JTextField textboxsearch;
    private javax.swing.JButton themmoidg;
    private javax.swing.JPanel timkiem;
    private javax.swing.JTextField txt_maDMSach;
    private javax.swing.JTextField txt_tenDMSach;
    private javax.swing.JTextField txt_timkiemDMSach;
    private javax.swing.JButton updatedg;
    // End of variables declaration//GEN-END:variables
}
