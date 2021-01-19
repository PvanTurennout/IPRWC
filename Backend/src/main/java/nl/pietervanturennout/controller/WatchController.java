package nl.pietervanturennout.controller;

import nl.pietervanturennout.dao.WatchDAO;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Watch;

import javax.inject.Singleton;
import java.sql.ResultSet;
import java.util.List;

@Singleton
public class WatchController {

    //Singleton
    private static final WatchController instance;
    static { instance = new WatchController(); }
    public static WatchController getInstance() { return instance; }


    private final WatchDAO watchDAO;

    public WatchController() {
        watchDAO = new WatchDAO();
    }

    public List<Watch> getListOfWatches() throws OperationFailedException, NotFoundException {
        return watchDAO.getAllWatchesList();
    }

    public Watch getWatchById(int id) throws OperationFailedException, NotFoundException {
        return watchDAO.searchWatchById(id);
    }

    public Watch watchModelFromResultSet(ResultSet resultSetWithWatchModel) throws OperationFailedException {
        return watchDAO.makeModel(resultSetWithWatchModel);
    }

    public int createWatchFromModel(Watch watch) throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        int braceletId = BraceletController.getInstance().createBraceletFromModel(watch.getWatchBracelet());
        return watchDAO.insertWatch(watch, braceletId);
    }

    public void updateWatchFromModel(Watch watch) throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        BraceletController.getInstance().updateBraceletFromModel(watch.getWatchBracelet());
        watchDAO.updateWatch(watch);
    }

    public void removeWatch(Watch watch) throws NotFoundException, OperationFailedException {
        watchDAO.deleteWatch(watch.getWatchId());
        BraceletController.getInstance().removeBracelet(watch.getWatchBracelet().getBraceletId());
    }
}
