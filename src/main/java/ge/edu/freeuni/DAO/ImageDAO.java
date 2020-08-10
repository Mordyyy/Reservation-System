package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    Has two columns:
    1. url - location of image
    2. name - name of special avatars
 */

public class ImageDAO {
    private Connection con;

    public ImageDAO(String url, String user_name, String password) throws SQLException {
        this.con = DriverManager.getConnection(url, user_name, password);
    }

    public boolean addImage(Image img) throws SQLException {
        PreparedStatement st = con.prepareStatement("Insert into images values (?, ?)");
        st.setString(1, img.getUrl());
        st.setString(2, img.getName());
        int res = st.executeUpdate();
        st.close();
        return (res == 1);
    }

    public boolean getImage(String imgName) throws SQLException {
        PreparedStatement st = con.prepareStatement("Select * from images where name = ?");
        st.setString(1, imgName);
        ResultSet res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return false;
        }
        st.close();
        return true;
    }

    public boolean removeUImage(String imgUrl) throws SQLException {
        PreparedStatement st = con.prepareStatement("Delete from images where url = ?");
        st.setString(1, imgUrl);
        int res = st.executeUpdate();
        st.close();
        return (res == 1);
    }

    public List<Image> getAll() throws SQLException {
        List<Image> lst = new ArrayList<>();
        PreparedStatement st = con.prepareStatement("Select * from images");
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
    }

}
