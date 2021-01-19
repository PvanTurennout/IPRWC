package nl.pietervanturennout.dao;

import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.OperationFailedException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageDAO {

    public String save(InputStream image, String directoryPath, String fileName) throws OperationFailedException, InvalidDataException{
        Path path = Paths.get(directoryPath);

        try (BufferedInputStream file = new BufferedInputStream(image)) {
            Path filePath = path.resolve(fileName);
            Files.copy(file, filePath);
            return fileName;
        } catch (FileAlreadyExistsException e){
            throw new InvalidDataException("file name is already taken!");
        } catch (IOException e) {
            throw new OperationFailedException("File Couldn't be created", e);
        }
    }
}
