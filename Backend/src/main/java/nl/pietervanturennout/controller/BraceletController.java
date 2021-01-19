package nl.pietervanturennout.controller;

import nl.pietervanturennout.dao.BraceletDAO;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Bracelet;

import javax.inject.Singleton;
import java.sql.ResultSet;

@Singleton
public class BraceletController {

    private static final BraceletController instance;
    static{ instance = new BraceletController(); }
    public static BraceletController getInstance() {return instance; }

    private final BraceletDAO braceletDAO;

    public BraceletController() {
        this.braceletDAO = new BraceletDAO();
    }

    public Bracelet braceletModelFromResultSet(ResultSet resultSetWithBraceletModel) throws OperationFailedException {
        return braceletDAO.makeModel(resultSetWithBraceletModel);
    }

    public Bracelet getBraceletById(int braceletId) throws OperationFailedException, NotFoundException {
        return braceletDAO.getBraceletFromId(braceletId);
    }

    public int createBraceletFromModel(Bracelet bracelet) throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        int braceletId = braceletDAO.insertBracelet(bracelet);

        if (braceletId == 0)
            throw new OperationFailedException("Bracelet couldn't be created!");

        return braceletId;
    }

    public void updateBraceletFromModel(Bracelet bracelet) throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        braceletDAO.updateBracelet(bracelet);
    }

    public void removeBracelet(int braceletId) throws NotFoundException, OperationFailedException {
        braceletDAO.deleteBracelet(braceletId);
    }
}
