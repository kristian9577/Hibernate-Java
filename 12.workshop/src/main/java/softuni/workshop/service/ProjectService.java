package softuni.workshop.service;

public interface ProjectService {

    void importProjects();

    boolean areImported();

    String readProjectsXmlFile();

    String exportFinishedProjects();
}
