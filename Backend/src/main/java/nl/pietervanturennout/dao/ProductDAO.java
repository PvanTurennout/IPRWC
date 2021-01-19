package nl.pietervanturennout.dao;

import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Product;
import nl.pietervanturennout.model.Watch;
import nl.pietervanturennout.services.DAOService;
import nl.pietervanturennout.services.DatabaseService;
import nl.pietervanturennout.sql.NamedParameterStatement;
import nl.pietervanturennout.sql.SQLDuplicateKeyException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAO<Product>{

    private final String TABLE = "product";

    public Product makeModel(ResultSet data) throws OperationFailedException{
        try{
            Product newProduct = new Product();

            newProduct.setProductId(data.getInt("product_id"));
            newProduct.setName(data.getString("product_name"));
            newProduct.setBrand(data.getString("product_brand"));
            newProduct.setPrice(data.getInt("product_price"));
            newProduct.setDescription(data.getString("product_description"));
            newProduct.setStock(data.getInt("product_stock"));
            newProduct.setUnitsSold(data.getInt("product_sold"));
            newProduct.setImagePath(data.getString("product_image_path"));
            newProduct.setWatch(new Watch(data.getInt("product_watch")));

            return newProduct;
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public List<Product> getAllProducts() throws OperationFailedException, NotFoundException {
        return DAOService.getListOfModels(TABLE, this);
    }

    public List<Product> getTopSellingProducts() throws OperationFailedException, NotFoundException {
        return DAOService.getListOfModels("top_selling_products", this);
    }

    public List<Product> getHighlightedProducts() throws OperationFailedException, NotFoundException {
        return DAOService.getListOfModels("highlighted_products", this);
    }

    public Product getProductById(int productId) throws OperationFailedException, NotFoundException {
        return DAOService.searchModelById(productId, TABLE, TABLE, this);
    }

    public List<Product> getProductsFromIdList(List<Integer> ids) throws NotFoundException, OperationFailedException {
        List<Product> products = new ArrayList<>();
        for (Integer id : ids){
            products.add(getProductById(id));
        }
        return products;
    }

    public void removeProduct(int productId) throws OperationFailedException, NotFoundException{
        DAOService.deleteModelById(productId, TABLE, TABLE);
    }

    public int insertProduct(Product product, int watchId) throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "INSERT INTO Product " +
                            "(product_name, product_brand, product_price, product_description, product_stock," +
                            " product_sold, product_image_path, product_watch) VALUES" +
                            "(:name, :brand, :price, :description, :stock, :sold, :image, :watch);");
            updateAndInsertQueryParameterSetter(product, watchId, statement);
            statement.setInt("sold", product.getUnitsSold());

            if (statement.executeUpdate() > 0) {
                return statement.getGeneratedId("product_id");
            } else {
                throw new InvalidDataException("Bracelet couldn't be created");
            }

        } catch (SQLDuplicateKeyException e) {
            throw new DuplicateEntryException(e);
        }
        catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public void updateProduct(Product product)
            throws OperationFailedException, DuplicateEntryException, NotFoundException {
        if (product.getProductId() == 0)
            throw new OperationFailedException("can't update if no id is specified for product");

        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "UPDATE Product SET " +
                            "product_name = :name, " +
                            "product_brand = :brand, " +
                            "product_price = :price, " +
                            "product_description = :description, " +
                            "product_stock = :stock, " +
                            "product_image_path = :image, " +
                            "product_watch = :watch " +
                            "WHERE product_id = :id;");

            statement.setInt("id", product.getProductId());
            updateAndInsertQueryParameterSetter(product, product.getWatch().getWatchId(), statement);

            if (statement.executeUpdate() == 0) {
                throw new NotFoundException("No account with this id exists");
            }
        } catch (SQLDuplicateKeyException e) {
            throw new DuplicateEntryException(e);
        }
        catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    private void updateAndInsertQueryParameterSetter(Product product, int watchId, NamedParameterStatement statement) throws SQLException {
        statement.setString("name", product.getName());
        statement.setString("brand", product.getBrand());
        statement.setDouble("price", product.getPrice());
        statement.setString("description", product.getDescription());
        statement.setInt("stock", product.getStock());
        statement.setString("image", product.getImagePath());
        statement.setInt("watch", watchId);
    }

}
