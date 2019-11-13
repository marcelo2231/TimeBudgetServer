package timebudget.model;


public class Category {

	public static final int NO_CATEGORY_ID = -1;
	public static final int NO_USER_ID = -1;
	public static final int NO_DELETED_AT = -1;
	public static final int NO_COLOR = -1;

	private int categoryID = NO_CATEGORY_ID;
	private int userID = NO_USER_ID; // auto created by database, may be null
	private String description = null;
	private int color = NO_COLOR;
	private int deletedAt = NO_DELETED_AT;
	public static final int AUTH_TOKEN_SIZE = 16;

	public Category(int categoryID, int userID, String description, int color, int deletedAt) {
		this.categoryID = categoryID;
		this.userID = userID;
		this.description = description;
		this.color = color;
		this.deletedAt = deletedAt;
	}

	public Category(int categoryID, int userID, int color, String description) {
		this.categoryID = categoryID;
		this.userID = userID;
		this.description = description;
		this.color = color;
	}

	public Category(Category category) {
		this.categoryID = category.getCategoryID();
		this.userID = category.getUserID();
		this.description = category.getDescription();
		this.deletedAt = category.getDeletedAt();
		this.color = category.getColor();
	}

	public boolean isDeleted() {
		return deletedAt == NO_DELETED_AT;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getColor(){
		return color;
	}

	public void setColor(int color){
		this.color = color;
	}

	public int getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(int deletedAt) {
		this.deletedAt = deletedAt;
	}

	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		Category category = (Category) object;
		return getCategoryID() == category.getCategoryID() &&
				getUserID() == category.getUserID() &&
				getColor() == category.getColor() &&
				getDeletedAt() == category.getDeletedAt() &&
				java.util.Objects.equals(getDescription(), category.getDescription());
	}

	public int hashCode() {
		return java.util.Objects.hash(super.hashCode(), getCategoryID(), getUserID(), getDescription(), getColor(), getDeletedAt());
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "Category{" +
				"categoryID=" + categoryID +
				", userID=" + userID +
				", description='" + description + '\'' +
				", color='" + color + '\'' +
				", deletedAt=" + deletedAt +
				'}';
	}
}

