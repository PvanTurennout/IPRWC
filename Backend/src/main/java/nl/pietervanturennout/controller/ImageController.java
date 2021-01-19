package nl.pietervanturennout.controller;

import nl.pietervanturennout.IprwcBackendMain;
import nl.pietervanturennout.dao.ImageDAO;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.OperationFailedException;

import java.io.InputStream;

public class ImageController {

    private static final ImageController instance;
    static{ instance = new ImageController(); }
    public static ImageController getInstance() {return instance; }

    private final ImageDAO imageDAO;
    private final String imageDirectory;

    public ImageController() {
        this.imageDAO = new ImageDAO();
        this.imageDirectory = IprwcBackendMain.getServerConfiguration().getFilePath();
    }

    public String upload(InputStream image, String fileName) throws OperationFailedException, InvalidDataException {
        return imageDAO.save(image, imageDirectory, fileName);
    }
}
