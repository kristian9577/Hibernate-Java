package softuni.workshop.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    private String name;
    private List<Project> projects;

    public Company() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
