package nl.pietervanturennout.dao;

import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.utils.types.Group;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class test {


    @Test
    public void roleGroupTestSuccess() throws OperationFailedException {
        AuthorizationDAO dao = new AuthorizationDAO();

        assertTrue(dao.roleCheck("product_read", Group.ADMIN));
        assertTrue(dao.roleCheck("product_read", Group.SELLER));
        assertTrue(dao.roleCheck("product_read", Group.CUSTOMER));
    }

    @Test
    public void roleGroupTestFail() throws OperationFailedException {
        AuthorizationDAO dao = new AuthorizationDAO();

        assertFalse(dao.roleCheck("product_crud", Group.CUSTOMER));
    }

    @Test
    public void pathsTest() {
        int one = 1;
        int two = 2;
        Path test = Paths.get("C:\\iprwc-images");
        System.out.println(test);
        System.out.println(test.toString());

        assertNotEquals(two, one);
    }

    @Test
    public void checkIfFileExistsTestSuccess() {
        File file = new File("C:/iprwc-images/patek.png");
        assertTrue(file.exists());
    }

    @Test
    public void checkIfFileExistsTestFail() {
        File file = new File("C:/iprwc-images/pate.png");
        assertFalse(file.exists());
    }
}
