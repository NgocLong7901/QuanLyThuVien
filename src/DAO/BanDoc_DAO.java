/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.BanDoc;
import Model.Sach;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Viet
 */
public class BanDoc_DAO {
    public List<BanDoc> getDSBanDocForFind() {
        List<BanDoc> listBanDoc = new ArrayList<BanDoc>();
        Connection connection = KetNoiSQL.getConnection();
        
        String sql = "SELECT * FROM DocGia";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                BanDoc bandoc = new BanDoc();
                
                bandoc.setMaTK(rs.getString("maDocGia"));
                bandoc.setMathau(rs.getString("matKhau"));
                bandoc.setTenDocGia(rs.getString("tenDocGia"));
                bandoc.setNgaySinh(rs.getString("ngaySinh"));
                bandoc.setGioiTinh(rs.getString("gioiTinh"));
                bandoc.setEmail(rs.getString("email"));
                bandoc.setSdt(rs.getString("sdt"));
                bandoc.setStatus(rs.getInt("status"));
                bandoc.setSoLuongMuon(rs.getInt("soLuongMuon"));
                
                listBanDoc.add(bandoc);
                
            }
        }catch (SQLException e){
            System.out.println("Loi o dau do roi");
            e.printStackTrace();
        }
        return listBanDoc;
    }
}
