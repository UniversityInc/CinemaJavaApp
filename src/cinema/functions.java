/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vooveen
 */
public class functions {
    static Connection conn = null;
    public static Connection connect() {
        
        // SQLite connection string
        String url = "jdbc:sqlite:cinema.db";
        //String url = "jdbc:sqlite:C:\\Users\\Sofiane\\Desktop\\gestion_stock.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur de connection à la base de donnée", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public static void switchpanel(JPanel panel, JLayeredPane layeredpane) {
        layeredpane.removeAll();
        layeredpane.add(panel);
        layeredpane.repaint();
        layeredpane.revalidate();
    }
    
    public static ResultSet getdata(String sql){
        Statement stmt;
        ResultSet rs =null;
        try {
            Connection conn = connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Impossible d'avoir les données", "Erreur", JOptionPane.ERROR_MESSAGE);

        }
        return rs;
    }
    
    public static void adddata(String sql){
        Statement stmt = null;
        conn = connect();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ajout Impossible", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static DefaultTableModel model(){
        DefaultTableModel model = null;
        model = new DefaultTableModel(new String[]{"Nom", "N° Téléphone", "Film", "Salle", "Place"}, 0);
    return model;
    }
    
    public static void updatetable(JTable table, String sql){
        ResultSet rs = functions.getdata(sql);
        DefaultTableModel model = functions.model();
        try {
            while (rs.next()) {
                String nom_client = rs.getString("nom_client");
                String num_client = rs.getString("num_client");
                String heure = rs.getString("heure");
                String place = rs.getString("id_place");
                String salle = rs.getString("salle");
                String film = rs.getString("film");
                model.addRow(new Object[]{nom_client, num_client, film, salle, place});
            }
            table.setModel(model);
        } catch (SQLException ex) {
        }
    }
        
    
}
