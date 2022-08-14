/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.DanhMucSach;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Viet
 */
public class DanhMucSach_DAO {
    public List<DanhMucSach> getDSDanhMucSach(){
        List<DanhMucSach> dms = new ArrayList<DanhMucSach>();
        
        Connection connection = KetNoiSQL.getConnection();
        
        String sql = "SELECT * FROM DanhMucSach";
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                DanhMucSach danhmuc = new DanhMucSach();
                
                danhmuc.setMaDM(rs.getString("maDMSach"));
                danhmuc.setTenDM(rs.getString("tenDMSach"));
                
                dms.add(danhmuc);
                
            }
        }catch (SQLException e){
            
        }
        return dms;
    }
    public void addDanhMucSach(DanhMucSach danhmuc){
        Connection connection = KetNoiSQL.getConnection();
        String sql = "INSERT INTO DanhMucSach VALUES (?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, danhmuc.getMaDM());
            preparedStatement.setString(2, danhmuc.getTenDM()); 
            
            preparedStatement.executeUpdate();
        }catch(Exception e){
            
        }
    }

    public boolean checkMaDM(String maDM) {
        Connection connection = KetNoiSQL.getConnection();
        String sql = "SELECT * FROM DanhMucSach WHERE maDMSach like '%" + maDM + "%'";
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            if(rs.isBeforeFirst() == false){
                return false;
            } else
                return true;
        } catch(Exception e){
            
        }
        return false;
    }
    
    public void updateDanhMucSach(DanhMucSach danhmuc){
        Connection connection = KetNoiSQL.getConnection();
        String sql = "UPDATE DanhMucSach SET  tenDMSach = ? WHERE maDMSach = ?";//
        try{
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(2, danhmuc.getMaDM());
            pstm.setString(1, danhmuc.getTenDM()); 
           // pstm.setString(3, danhmuc.getMaDM());
            
            pstm.executeUpdate();
        }catch(Exception e){
            
        }
    }
    

}
