package net.is_bg.ltf.businessmodels.menu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.is_bg.ltf.db.common.SelectSqlStatement;

public class MenuSql extends SelectSqlStatement {
	private List<MenuNode> unorderedMenuList = new ArrayList<MenuNode>();
	private long curUserId;

	public MenuSql(long curUserId) {
	    this.curUserId = curUserId;
    }

	@Override
	protected String getSqlString() {
		String sql = "select distinct m.menurole_id, " +
					"       m.menurole, " +
					"       m.menuname, " +
					"       m.nodeno, " +
					"       m.href, " +
					"       m.icon_id, " +
					"       m.parent_id  " +
					"from   menu m ";
		
		if (curUserId > 0) {
			sql += 	"       , usergroups ug, " +
					"       groupmenu gm " +
					"where  ug.grouprole_id = gm.grouprole_id " +
					"and    gm.menurole_id = m.menurole_id " +
					"and    ug.user_id = ? ";
		}
					
		sql += "order by m.parent_id, m.nodeno ";
		
		return sql;
	}
	@Override
    protected void setParameters(PreparedStatement prStmt) throws SQLException {
		if (curUserId > 0){
			bindVarData.setLong( curUserId);
			bindVarData.setParameters(prStmt);
		}      
    }

    @Override
	protected void retrieveResult(ResultSet rs) throws SQLException {
		//прочитане на извлечените записи
		while (rs.next()) {
			MenuNode menuNode = new MenuNode();
			menuNode.setMenuRoleId(rs.getInt("menurole_id"));
			menuNode.setMenuRole(rs.getString("menurole"));
			menuNode.setMenuName(rs.getString("menuname"));
			menuNode.setHref(rs.getString("href"));
			menuNode.setIconId(rs.getString("icon_id"));
			menuNode.setNodeNo(rs.getInt("nodeno"));
			menuNode.setParentId(rs.getInt("parent_id"));
			unorderedMenuList.add(menuNode);
		}
	}

	public List<MenuNode> getUnorderedMenuList() {
		return unorderedMenuList;
	}
}
