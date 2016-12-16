package net.is_bg.ltf.login;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import net.is_bg.ltf.db.common.DbUtils;
import net.is_bg.ltf.db.common.SelectSqlStatement;
import net.is_bg.ltfn.commons.old.models.address.municipality.Municipality;
import net.is_bg.ltfn.commons.old.models.province.Province;
import net.is_bg.ltfn.commons.old.models.user.User;

public class CurrUserSelect extends SelectSqlStatement {
    private long userId;
    private User user;
    
    public CurrUserSelect(long userId) {
	super();
	this.userId = userId;
    }

    @Override
    protected String getSqlString() {
	String sqlString = " select u.user_id userId,\n"+
		"        u.username username,\n"+
		"        u.password userpass,\n"+
		"        u.fullname fullName,\n"+
		"        u.begin_date beginDate,\n"+
		"        u.end_date endDate,\n"+
		"        u.created_date createdDate,\n"+
		"        u.other other,\n"+
		"        u.default_printer,\n"+
		"        u.is_matrix_printer,\n"+
		"	 u.skin,\n"+
		"	 coalesce(u.mustchangepass, 0) mustchangepass,\n"+
		"        coalesce(u.faultcounter, 0) faultCounter,\n"+
		"        u.faultdate faultDate,\n"+
		"        coalesce(u.accessdenied, 0) accessDenied,\n"+
		"        coalesce(u.deleted, 0) deleted,\n"+
		"        uu.user_id createdById,\n"+
		"        uu.fullname createdByName,\n"+
		"        m.municipality_id municipalityId,\n"+
		"        coalesce(m.parentmunicipality_id, - 1) parentMunicipalityId,\n"+
		"        m.fullname municipalityName,\n"+
		"        m.ebk_code muncode,\n"+
		"        m.url munurl,\n"+
		"        d.department_id departmentId,\n"+
		"        d.name departmentName,\n"+
		"        c.company_id companyId,\n"+
		"        ts.name companyName,\n"+
		"        o.office_id officeId,\n"+
		"        o.fullname officeName,\n"+
		"        p.province_id province_id,\n"+
		"        p.name province_name,\n"+
		"        p.code province_code\n"+
		" from users u\n"+
		"      join users uu on u.createdby_id = uu.user_id\n"+
		"      join municipality m on u.municipality_id = m.municipality_id\n"+
		"      join province p on p.province_id = m.province_id\n"+
		"      left join department d on u.department_id = d.department_id\n"+
		"      left join company c on u.company_id = c.company_id\n"+
		"      left join taxsubject ts on c.taxsubject_id = ts.taxsubject_id\n"+
		"      left join office o on u.office_id = o.office_id\n"+
		" where u.user_id = ?\n";

	return sqlString;
    }
    
    @Override
    protected void setParameters(PreparedStatement prStmt) throws SQLException {
	prStmt.setLong(1, userId);
    }

    @Override
    protected void retrieveResult(ResultSet rs) throws SQLException {
	while (rs.next()) {
	    User createdBy = new User();
	    createdBy.setId(rs.getLong("createdById"));
	    createdBy.setFullName(rs.getString("createdByName"));
	    Province province = new Province();
	    province.setId(rs.getLong("province_id"));
	    province.setName(rs.getString("province_name"));
	    province.setCode(rs.getString("province_code"));
	    Municipality municipality = new Municipality();
	    municipality.setId(rs.getLong("municipalityId"));
	    //municipality.setFullName(rs.getString("municipalityName"));
	    municipality.setCode(rs.getString("muncode"));
	    municipality.setUrl(rs.getString("munurl"));
	    municipality.setName(rs.getString("municipalityName"));
	   // municipality.setParentMunicipalityId(rs.getLong("parentMunicipalityId"));
	    municipality.setProvince(province);

	   /* Department department = new Department(rs.getLong("departmentId"), rs.getString("departmentName"), null);

	    Office office = new Office();
	    office.setId(rs.getLong("officeId"));
	    office.setFullName(rs.getString("officeName"));

	    Company company = new Company();
	    company.setId(rs.getLong("companyId"));
	    company.setName(rs.getString("companyName"));*/
	    /**
	     * @JsonProperty("user_id") Long id,
		@JsonProperty("username") String userName,
		@JsonProperty("fullname") String fullName,
		@JsonProperty("other") String other,
		@JsonProperty("begin_date") Date beginDate,
		@JsonProperty("end_date") Date endDate,
		@JsonProperty("createdby") User createdBy,
		@JsonProperty("created_date") Date creationDate,
		@JsonProperty("password") String password,
		@JsonProperty("faultcounter") int faultCounter,
		@JsonProperty("faultdate") Date faultDate,
		@JsonProperty("logged") boolean logged,
		@JsonProperty("municipality") Municipality municipality,
		@JsonProperty("accessdenied") boolean accessDenied
	     */
		user = new User(rs.getLong("userId"),
			    rs.getString("username"),
			    rs.getString("fullName"),
			    "other",
			    rs.getDate("beginDate"),
			    rs.getDate("endDate"),
			    createdBy,
			    rs.getDate("createdDate"),
			    rs.getString("userpass"),
			    rs.getInt("faultCounter"),
			    rs.getDate("faultDate"),
			    false,
			    municipality,
			    DbUtils.IntToBool(rs.getInt("accessDenied")));
		}
    }

    public User getUser() {
        return user;
    }
}
