/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Context.DBcontext;
import Entity.Account;
import Entity.Category;
import Entity.Game;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anxem
 */
public class DAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Game> getTrendingGame() {
        List<Game> list = new ArrayList<>();
        String query = "SELECT top 8 Game.*, CategoryGame.nameCategory\n"
                + "FROM Game\n"
                + "JOIN CategoryGame ON CategoryGame.id = Game.cateId;";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Game(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getInt(5),
                        rs.getString(6)
                ));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Game> getAllGame() {
        List<Game> list = new ArrayList<>();
        String query = "SELECT Game.*, CategoryGame.nameCategory\n"
                + "FROM Game\n"
                + "JOIN CategoryGame ON CategoryGame.id = Game.cateId;";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Game(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getInt(5),
                        rs.getString(6)
                ));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String query = "select * from CategoryGame";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Game> getGameSale() {
        List<Game> list = new ArrayList<>();
        String query = "SELECT TOP 2 *\n"
                + "FROM Game\n"
                + "ORDER BY price;";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Game(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5)));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public Game getNewGame() {
        String query = "SELECT TOP 1 *\n"
                + "FROM Game\n"
                + "ORDER BY id desc";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Game(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5));
            }
        } catch (Exception e) {

        }
        return null;
    }

    public Game getGameByID(String id) {
        String query = "SELECT Game.*, Infomation.imageGame1, Infomation.imageGame2, Infomation.imageGame3, Infomation.imageGame4, Infomation.description, Infomation.Video, Configuration.OS, Configuration.Processor, Configuration.Memory, Configuration.Graphics, Configuration.Storage, CategoryGame.nameCategory\n"
                + "FROM Game\n"
                + "JOIN Infomation ON Game.id = Infomation.id \n"
                + "JOIN Configuration ON Game.id = Configuration.id \n"
                + "JOIN CategoryGame ON Game.cateId = CategoryGame.id \n"
                + "where Game.id = ?";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Game(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17));
            }

        } catch (Exception e) {

        }
        return null;
    }

    public List<Game> getGameByCate(String cid) {
        List<Game> list = new ArrayList<>();
        String query = "SELECT Game.*, CategoryGame.nameCategory\n"
                + "FROM Game \n"
                + "JOIN CategoryGame ON CategoryGame.id = Game.cateId and cateId = ?";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, cid);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Game(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5), rs.getString(6)));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Game> searchByName(String txtSeatch) {
        List<Game> list = new ArrayList<>();
        String query = "select * from Game\n"
                + "where nameGame like ?";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + txtSeatch + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Game(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5)));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public Account login(String user, String pass) {
        String query = "select * from Account\n"
                + "where username = ?\n"
                + "and [password] =?";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Account checkAccountExist(String user) {
        String query = "select * from Account\n"
                + "where username = ?\n";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, user);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void signup(String fullname, String user, String email, String pass) {
        String query = "insert into Account\n"
                + "values(?,?,?,?,0)";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, fullname);
            ps.setString(2, user);
            ps.setString(3, email);
            ps.setString(4, pass);
            ps.executeUpdate();

        } catch (Exception e) {
        }

    }

    public void deleteGame(String gid) {
        String query = "delete from Game\n"
                + "where id = ?";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, gid);

            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        DAO dao = new DAO();
//        List<Game> list = dao.getAllGame();
//        List<Category> listC = dao.getAllCategory();
//        for (Game game : list) {
//            System.out.println(game);
//
//        }
            Game g = dao.getGameByID("2");
            System.out.println(g);
    }
}
