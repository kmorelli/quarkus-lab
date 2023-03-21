package kmorelli.auriga.services.mongo;

import java.time.LocalDate;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(database = "person")
public class Person {
    public ObjectId id;
    public String name;
    public LocalDate birth;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((birth == null) ? 0 : birth.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (birth == null) {
            if (other.birth != null)
                return false;
        } else if (!birth.equals(other.birth))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", birth=" + birth + "]";
    }

}