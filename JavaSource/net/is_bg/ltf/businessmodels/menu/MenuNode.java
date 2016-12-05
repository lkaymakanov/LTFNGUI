package net.is_bg.ltf.businessmodels.menu;

public class MenuNode {
	private int menuRoleId;
	private String menuRole;
	private String menuName;
	private int nodeNo;
	private String href;
	private String iconId;
	private long parentId;

	public int getMenuRoleId() {
		return menuRoleId;
	}

	public void setMenuRoleId(int menuroleId) {
		this.menuRoleId = menuroleId;
	}

	public String getMenuRole() {
		return menuRole;
	}

	public void setMenuRole(String menuRole) {
		this.menuRole = menuRole;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(int nodeNo) {
		this.nodeNo = nodeNo;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIconId() {
		return iconId;
	}

	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
}
