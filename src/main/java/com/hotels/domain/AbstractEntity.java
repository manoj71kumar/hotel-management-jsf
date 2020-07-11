package com.hotels.domain;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity <ID>{
    public static final String FLD_ID = "id";

    @Id
    @Column(name = FLD_ID , unique = true, updatable = false , nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;

//    @Version
//    private  Integer version;
//
//    @CreationTimestamp
//    private LocalDateTime created;
//
//    @UpdateTimestamp
//    private LocalDateTime updated;

    public AbstractEntity() {}
    public AbstractEntity(ID id){

        this.id=id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
//
//    public Integer getVersion() {
//        return version;
//    }
//
//    public void setVersion(Integer version) {
//        this.version = version;
//    }
//
//    public LocalDateTime getCreated() {
//        return created;
//    }
//
//    public void setCreated(LocalDateTime created) {
//        this.created = created;
//    }
//
//    public LocalDateTime getUpdated() {
//        return updated;
//    }
//
//    public void setUpdated(LocalDateTime updated) {
//        this.updated = updated;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof AbstractEntity)) return false;
//        AbstractEntity<?> that = (AbstractEntity<?>) o;
//        return Objects.equals(id, that.id) &&
//                Objects.equals(version, that.version) &&
//                Objects.equals(created, that.created) &&
//                Objects.equals(updated, that.updated);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, version, created, updated);
//    }


}

