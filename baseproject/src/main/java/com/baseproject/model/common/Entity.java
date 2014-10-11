package com.baseproject.model.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 5210529725033309138L;

	@Transient
    private static final transient Repository<Entity> REPOSITORY = new Repository<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Date updatedAt;
	
	@Column(nullable = false)
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void updateEntity(Date date) {
		if (getId() == null) {
			this.createdAt = date;
		}
		
		this.updatedAt = date;
	}
	
	public void save() {
		REPOSITORY.save(this);
    }

    public void update(final Entity entity) {
		REPOSITORY.update(this);
    }

	public void remove() {
		REPOSITORY.remove(this);
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Entity other = (Entity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        
        return true;
    }
}