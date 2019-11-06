package timebudget.model;


public class Category {

	public static final int NO_CATEGORY_ID = -1;
	public static final int NO_USER_ID = -1;
	public static final int NO_DELETED_AT = -1;

	private int categoryID = NO_CATEGORY_ID;
	private int userID = NO_USER_ID; // auto created by database, may be null
	private String description = null;
	private int deletedAt = NO_DELETED_AT;
	public static final int AUTH_TOKEN_SIZE = 16;

	public Category(int categoryID, int userID, String description, int deletedAt) {
		this.categoryID = categoryID;
		this.userID = userID;
		this.description = description;
		this.deletedAt = deletedAt;
	}

	public Category(int categoryID, int userID, String description) {
		this.categoryID = categoryID;
		this.userID = userID;
		this.description = description;
	}

	public Category(Category category) {
		this.categoryID = category.getCategoryID();
		this.userID = category.getUserID();
		this.description = category.getDescription();
		this.deletedAt = category.getDeletedAt();
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
				getDeletedAt() == category.getDeletedAt() &&
				java.util.Objects.equals(getDescription(), category.getDescription());
	}

	public int hashCode() {
		return java.util.Objects.hash(super.hashCode(), getCategoryID(), getUserID(), getDescription(), getDeletedAt());
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "Category{" +
				"categoryID=" + categoryID +
				", userID=" + userID +
				", description='" + description + '\'' +
				", deletedAt=" + deletedAt +
				'}';
	}
}

