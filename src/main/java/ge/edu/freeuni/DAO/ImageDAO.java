package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO {
    private Connection con;

    public ImageDAO(String url, String user_name, String password) {
        try {
            this.con = DriverManager.getConnection(url, user_name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addImage(Image img) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("Insert into images values (?, ?)");
            st.setString(1, img.getUrl());
            st.setString(2, img.getName());
            int res = st.executeUpdate();
            st.close();
            return (res == 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean getImage(String imgName) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("Select * from images where name = ?");
            st.setString(1, imgName);
            ResultSet res = st.executeQuery();
            if (!res.next()) {
                st.close();
                return false;
            }
            st.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean removeUImage(String imgUrl) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("Delete from images where url = ?");
            st.setString(1, imgUrl);
            int res = st.executeUpdate();
            st.close();
            return (res == 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Image> getAll() {
        PreparedStatement st = null;
        List<Image> lst = new ArrayList<>();
        try {
            st = con.prepareStatement("Select * from images");
            ResultSet res = st.executeQuery();
            if (!res.next()) {
                st.close();
                return lst;
            }
            List<Image> images = new ArrayList<>();
            Image img = new Image(res.getString("name"), res.getString("url"));
            images.add(img);
            while (res.next()) {
                img = new Image(res.getString("name"), res.getString("url"));
                images.add(img);
            }
            st.close();
            return images;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lst;
    }

}
