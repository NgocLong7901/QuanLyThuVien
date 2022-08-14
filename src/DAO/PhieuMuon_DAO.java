/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.PhieuMuon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Viet
 */
public class PhieuMuon_DAO {
    public List<PhieuMuon> getAllDSPhieuMuon() {
        List<PhieuMuon> pmList = new ArrayList<PhieuMuon>();
        
        Connection connection = KetNoiSQL.getConnection();
        
        String sql = "select maPhieuMuon, DocGia.tenDocGia, maThuthu, ngayMuon, soNgayMuon, ghiChu, trangThai from PhieuMuon, DocGia where PhieuMuon.maDocGia = DocGia.maDocGia";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                PhieuMuon pm = new PhieuMuon();

                pm.setMaPM(rs.getString("maPhieuMuon"));
                pm.setMaDocGia(rs.getString("tenDocGia"));
                pm.setMaThuThu(rs.getString("maThuthu"));
                pm.setNgayMuon(rs.getString("ngayMuon"));
                pm.setSoNgayMuon(rs.getInt("soNgayMuon"));
                pm.setGhiChu(rs.getString("ghiChu"));
                pm.setTrangThai(rs.getString("trangThai"));
                
                pmList.add(pm);
            }
        } catch (SQLException e) {
        }

        return pmList;
    }
    public List<PhieuMuon> getDSPhieuMuon() {
        List<PhieuMuon> pmList = new ArrayList<PhieuMuon>();
        
        Connection connection = KetNoiSQL.getConnection();
        
        String sql = "select maPhieuMuon, DocGia.tenDocGia, Thuthu.tenThuthu, ngayMuon, soNgayMuon, ghiChu, trangThai from PhieuMuon, DocGia, Thuthu where PhieuMuon.maThuthu = Thuthu.maThuthu and PhieuMuon.maDocGia = DocGia.maDocGia";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                PhieuMuon pm = new PhieuMuon();

                pm.setMaPM(rs.getString("maPhieuMuon"));
                pm.setMaDocGia(rs.getString("tenDocGia"));
                pm.setMaThuThu(rs.getString("tenThuthu"));
                pm.setNgayMuon(rs.getString("ngayMuon"));
                pm.setSoNgayMuon(rs.getInt("soNgayMuon"));
                pm.setGhiChu(rs.getString("ghiChu"));
                pm.setTrangThai(rs.getString("trangThai"));
                
                pmList.add(pm);
            }
        } catch (SQLException e) {
        }

        return pmList;
    }
    
    public void addPhieuMuon(PhieuMuon phieuMuon) {
        Connection connection = KetNoiSQL.getConnection();
            String sql = "INSERT INTO PhieuMuon VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {           
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, phieuMuon.getMaPM());
                preparedStatement.setString(2, phieuMuon.getNgayMuon());
                preparedStatement.setInt(3, phieuMuon.getSoNgayMuon());
                preparedStatement.setString(4, phieuMuon.getMaDocGia());
                preparedStatement.setString(5, phieuMuon.getMaThuthu());
                preparedStatement.setString(6, phieuMuon.getGhiChu());
                preparedStatement.setString(7, phieuMuon.getTrangThai());
                
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
     
    }
    

    
    public void updatePhieuMuon(PhieuMuon phieuMuon) {
        Connection connection = KetNoiSQL.getConnection();
        String sql = "UPDATE PhieuMuon SET ghiChu = ?, trangThai = ?, ngayMuon = ?, maThuthu = ? WHERE maPhieuMuon = ?";
        try {           
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, phieuMuon.getGhiChu());
            preparedStatement.setString(2, phieuMuon.getTrangThai());
            preparedStatement.setString(3, phieuMuon.getNgayMuon());
            preparedStatement.setString(4, phieuMuon.getMaThuthu());
            preparedStatement.setString(5, phieuMuon.getMaPM());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("loi");
        }
    }
    
    public String getMaPMInserted() {
        Connection con = KetNoiSQL.getConnection();
        String sql = "select top 1 maPhieuMuon from PhieuMuon order by maPhieuMuon desc";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("maPhieuMuon");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }    
}
