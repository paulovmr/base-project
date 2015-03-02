package com.baseproject.model.filters;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.ws.rs.QueryParam;

import com.baseproject.model.common.Filter;
import com.baseproject.model.common.Loader;
import com.baseproject.model.common.MyEntityManager;
import com.baseproject.model.entities.User;
import com.baseproject.util.utils.QueryUtils;

public class UserFilter implements Filter<User> {

	@QueryParam("load")
	private String load;

	@QueryParam("name")
	private String name;

	@QueryParam("username")
	private String username;

	@QueryParam("profileId")
	private Long profileId;

	@QueryParam("companyId")
	private Long companyId;

	@Override
	public TypedQuery<User> buildQuery() {
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder("FROM users e");
		
		hql.append(QueryUtils.loadQuery(Loader.load(load, User.class)));
		
		hql.append(" WHERE 1 = 1");
		
		if (name != null && !name.isEmpty()) {
			hql.append(" AND UPPER(e.name) LIKE :name");
			params.put("name", "%" + name.toUpperCase() + "%");
		}
		
		if (username != null && !username.isEmpty()) {
			hql.append(" AND UPPER(e.username) LIKE :username");
			params.put("username", "%" + username.toUpperCase() + "%");
		}
		
		if (profileId != null) {
			hql.append(" AND e.profile.id = :profileId");
			params.put("profileId", profileId);
		}
		
		if (companyId != null) {
			hql.append(" AND e.company.id = :companyId");
			params.put("companyId", companyId);
		}
		
		TypedQuery<User> query = MyEntityManager.get().createQuery(hql.toString(), User.class);
		QueryUtils.setParameters(query, params);
		
		return query;
	}
	
	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
