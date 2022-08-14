/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DAO.DocGia_Dao;
import DAO.DocGia_Dao_implement;
import DAO.KetNoiSQL;
import Model.TaiKhoan;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Viet
 */
public class DangNhap extends javax.swing.JFrame {
    DocGia_Dao getDogia = new DocGia_Dao_implement();
    public DangNhap() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_tenDangNhap = new javax.swing.JTextField();
        btn_DangNhap = new javax.swing.JButton();
        btn_Thoat = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txt_chucVu = new javax.swing.JComboBox<>();
        txt_matKhau = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đăng nhập");

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("ĐĂNG NHẬP");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Tên đăng nhập:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Mật khẩu:");

        txt_tenDangNhap.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_tenDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenDangNhapActionPerformed(evt);
            }
        });
        txt_tenDangNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_tenDangNhapKeyPressed(evt);
            }
        });

        btn_DangNhap.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_DangNhap.setText("Đăng nhập");
        btn_DangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DangNhapActionPerformed(evt);
            }
        });

        btn_Thoat.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_Thoat.setText("Thoát");
        btn_Thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThoatActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Chức vụ:");

        txt_chucVu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_chucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Độc giả", "Admin" }));
        txt_chucVu.setSelectedIndex(1);

        txt_matKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_matKhauActionPerformed(evt);
            }
        });
        txt_matKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_matKhauKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(264, 264, 264)
                        .addComponent(jLabel1)
                        .addGap(105, 105, 105))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(btn_DangNhap)
                                .addGap(18, 18, 18)
                                .addComponent(btn_Thoat)
                                .addGap(16, 16, 16))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txt_chucVu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_tenDangNhap)
                                    .addComponent(txt_matKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jLabel2)
                .addContainerGap(160, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_chucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_tenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txt_matKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_DangNhap)
                            .addComponent(btn_Thoat))))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    private void btn_DangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DangNhapActionPerformed
        if(txt_tenDangNhap.getText().equals("") || txt_matKhau.getPassword().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");
        }
        else{
            if(txt_chucVu.getSelectedItem().equals("Độc giả")){
                TaiKhoan taikhoan = new TaiKhoan();
                taikhoan.setTenDangNhap(txt_tenDangNhap.getText().trim());
                taikhoan.setMatKhau(String.valueOf(txt_matKhau.getPassword()).trim());
                try {
                    if(ktraDocGia(taikhoan)){
                        if (getDogia.getOneDocgia(taikhoan.getTenDangNhap()) == 1) {
                            JOptionPane.showMessageDialog(rootPane, "Bạn đã đăng nhập thành công!");
                            TrangChuDocGia muonsach = new TrangChuDocGia(taikhoan.getTenDangNhap(),getSoLuongMuonById(taikhoan.getTenDangNhap()));
                            muonsach.setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Tai khoản này đã bị khóa!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Đăng nhập thất bại! Vui lòng kiểm tra lại!");
                        txt_matKhau.setText("");
                        txt_tenDangNhap.setText("");
                        txt_tenDangNhap.requestFocus();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
                }
           }else {
                TaiKhoan taikhoan1 = new TaiKhoan();
                taikhoan1.setTenDangNhap(txt_tenDangNhap.getText());
                taikhoan1.setMatKhau(String.valueOf(txt_matKhau.getPassword()));
                try {
                    if(ktraThuThu(taikhoan1)){
                        JOptionPane.showMessageDialog(rootPane, "Bạn đã đăng nhập thành công!");
                        new TrangChuThuThu().setVisible(true);
                        this.setVisible(false);
                        
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Đăng nhập thất bại! Vui lòng kiểm tra lại!");
                        System.out.println(txt_matKhau.getPassword());
                        txt_tenDangNhap.requestFocus();
                        txt_matKhau.setText("");
                        txt_tenDangNhap.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btn_DangNhapActionPerformed

    private void btn_ThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThoatActionPerformed
        new TrangChuChinh().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_ThoatActionPerformed

    private void txt_tenDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenDangNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenDangNhapActionPerformed

    private void txt_matKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_matKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_matKhauActionPerformed

    private void txt_matKhauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_matKhauKeyPressed
        if (evt.getKeyCode() == 10 ) {
            if(txt_tenDangNhap.getText().equals("") || txt_matKhau.getPassword().equals("")){
                JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");
            }
            else{
                if(txt_chucVu.getSelectedItem().equals("Độc giả")){
                    TaiKhoan taikhoan = new TaiKhoan();
                    taikhoan.setTenDangNhap(txt_tenDangNhap.getText().trim());
                    taikhoan.setMatKhau(String.valueOf(txt_matKhau.getPassword()).trim());
                    try {
                        if(ktraDocGia(taikhoan)){
                            if (getDogia.getOneDocgia(taikhoan.getTenDangNhap()) == 1) {
                                JOptionPane.showMessageDialog(rootPane, "Bạn đã đăng nhập thành công!");
                                TrangChuDocGia muonsach = new TrangChuDocGia(taikhoan.getTenDangNhap(),getSoLuongMuonById(taikhoan.getTenDangNhap()));
                                muonsach.setVisible(true);
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Tai khoản này đã bị khóa!", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane, "Đăng nhập thất bại! Vui lòng kiểm tra lại!");
                            txt_matKhau.setText("");
                            txt_tenDangNhap.setText("");
                            txt_tenDangNhap.requestFocus();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }else {
                    TaiKhoan taikhoan1 = new TaiKhoan();
                    taikhoan1.setTenDangNhap(txt_tenDangNhap.getText());
                    taikhoan1.setMatKhau(String.valueOf(txt_matKhau.getPassword()));
                    try {
                        if(ktraThuThu(taikhoan1)){
                            JOptionPane.showMessageDialog(rootPane, "Bạn đã đăng nhập thành công!");
                            new TrangChuThuThu().setVisible(true);
                            this.setVisible(false);

                        }else{
                            JOptionPane.showMessageDialog(rootPane, "Đăng nhập thất bại! Vui lòng kiểm tra lại!");
                            System.out.println(txt_matKhau.getPassword());
                            txt_tenDangNhap.requestFocus();
                            txt_matKhau.setText("");
                            txt_tenDangNhap.setText("");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_txt_matKhauKeyPressed

    private void txt_tenDangNhapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tenDangNhapKeyPressed
        if (evt.getKeyCode() == 10) {
            txt_matKhau.requestFocus();
        }
    }//GEN-LAST:event_txt_tenDangNhapKeyPressed

    public boolean ktraDocGia(TaiKhoan taikhoan) throws SQLException{
        List<TaiKhoan> docGia = new ArrayList<TaiKhoan>();
        Connection conn = KetNoiSQL.getConnection();
        String sql = "SELECT maDocGia, matKhau FROM DocGia";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                TaiKhoan dg = new TaiKhoan();
                dg.setTenDangNhap(rs.getString("maDocGia").trim());
                dg.setMatKhau(rs.getString("matKhau").trim());
                docGia.add(dg);
                
                
                
            }
        }
        catch(SQLException e){
            
        }
         for(TaiKhoan dg : docGia){
             if(dg.getTenDangNhap().equals(taikhoan.getTenDangNhap()) && dg.getMatKhau().equals(taikhoan.getMatKhau())){
                 return true;
             }
         }
         return false;
    }
    
    public boolean ktraThuThu(TaiKhoan taikhoan) throws SQLException{
        List<TaiKhoan> thuthu = new ArrayList<TaiKhoan>();
        Connection conn = KetNoiSQL.getConnection();
        String sql = "SELECT maThuthu, matKhau FROM Thuthu";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                TaiKhoan tt = new TaiKhoan();
                
                tt.setTenDangNhap(rs.getString("maThuthu").trim());
                tt.setMatKhau(rs.getString("matKhau").trim());
                thuthu.add(tt);
            }
        }
        catch(SQLException e){
            
        }
        for(TaiKhoan tt : thuthu){
            if(tt.getTenDangNhap().equals(taikhoan.getTenDangNhap()) && tt.getMatKhau().equals(taikhoan.getMatKhau())){
                 return true;
            }
        }
        return false;
    }
    
        public int getSoLuongMuonById(String matk) {
        Connection con = KetNoiSQL.getConnection();
        String sql = "Select soLuongMuon from DocGia where maDocGia = " + matk;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            return rs.getInt("soLuongMuon");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
            
        
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_DangNhap;
    private javax.swing.JButton btn_Thoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> txt_chucVu;
    private javax.swing.JPasswordField txt_matKhau;
    private javax.swing.JTextField txt_tenDangNhap;
    // End of variables declaration//GEN-END:variables
}
