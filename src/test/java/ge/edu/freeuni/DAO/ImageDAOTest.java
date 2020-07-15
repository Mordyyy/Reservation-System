package ge.edu.freeuni.DAO;

import ge.edu.freeuni.Models.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImageDAOTest {
    private ImageDAO imageDAO;
    private final String url = "jdbc:mysql://localhost/Reservation";
    private final String username = "root";
    private final String password = "4546";
    private Image img;

    @BeforeEach
    public void init() throws SQLException {
        imageDAO = new ImageDAO(url,username,password);
        img = new Image("name", "url");
        assertTrue(imageDAO.addImage(img));
    }

    @Test
    public void simpleTests() throws SQLException {
        assertTrue(imageDAO.getImage("name"));
        assertTrue(imageDAO.removeUImage("url"));
        assertFalse(imageDAO.removeUImage("url"));
        assertFalse(imageDAO.getImage("name"));
    }

    @Test
    public void getAll() throws SQLException {
        List<Image> all = imageDAO.getAll();
        boolean result = false;
        for(Image image : all){
            if (image.getName().equals("name") && image.getUrl().equals("url")){
                result = true;
                break;
            }
        }
        assertTrue(result);
        assertTrue(imageDAO.removeUImage("url"));
    }

}