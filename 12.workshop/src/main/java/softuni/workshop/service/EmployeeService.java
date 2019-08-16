package softuni.workshop.service;

public interface EmployeeService {

    void importEmployees();

    boolean areImported();

    String readEmployeesXmlFile();

    String exportEmployeesWithAgeAbove();
}
