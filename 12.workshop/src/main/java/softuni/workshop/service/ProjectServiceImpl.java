package softuni.workshop.service;

import org.springframework.stereotype.Service;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Override
    public void importProjects(){
        //TODO seed in database
    }


    @Override
    public boolean areImported() {
        //TODO check if repository has any records
       return true;
    }

    @Override
    public String readProjectsXmlFile() {
        //TODO read xml file
      return null;
    }

    @Override
    public String exportFinishedProjects(){
        //TODO export finished projects
        return null;
    }
}
