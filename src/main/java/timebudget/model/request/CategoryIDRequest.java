package timebudget.model.request;


public class CategoryIDRequest {
    private int categoryID;

    public CategoryIDRequest(int categoryID){
        this.categoryID = categoryID;
    }

    public int getID() {
        return this.categoryID;
    }

    public void setID(int categoryID) {
        this.categoryID = categoryID;
    }
}