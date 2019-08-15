import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Engine implements Runnable {
    private final EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
        String task = null;
        try {
            task = readInput("Choose Task Number to Run:");
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (task) {
            case "2":
                // Task 2
                this.removeObjects();
                break;
            case "3":
                // Task 3
                try {
                    this.containsEmployee();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "4":
                // Task 4
                this.employeesWithSalaryOver();
                break;
            case "5":
                this.employeesFromDepartment();
                break;
            case "6":
                try {

                    this.addAddressAndUpdateEmployee();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "7":
                this.addressesWithEmployeeCount();
                break;
            case "8":
                try {
                    this.getEmployeesWithProject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "9":
                this.findLatest10Projects();
                break;
            case "10":
                this.increaseSalary();
                break;
            case "11":
                try {
                    this.removeTowns();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "12":
                try {
                    this.findEmployeeByFirstName();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "13":
                this.employeesMaxSalary();
            default:
                System.out.println("Choose again: ");
                run();
                break;
        }
        try {
            String nextTask = readInput("\nWanna continue: Y/N ? ");
            switch (nextTask.toLowerCase()) {
                case "y":
                    run();
                    break;
                default:
                    System.out.println("Bye");
                    this.entityManager.close();
                    return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void employeesMaxSalary() {
        final BigDecimal lowerLimit = BigDecimal.valueOf(30000);
        final BigDecimal upperLimit = BigDecimal.valueOf(70000);

        this.entityManager.getTransaction().begin();
        var filterDepartments = this.entityManager
                .createQuery("SELECT department.name, MAX(salary) " +
                                "FROM Employee " +
                                "GROUP BY department.name\n" +
                                "HAVING MAX(salary) NOT BETWEEN :low AND :up",
                        Object[].class)
                .setParameter("low", lowerLimit)
                .setParameter("up", upperLimit)
                .getResultList();

        filterDepartments.forEach(empl -> System.out.println(empl[0] + " - " + empl[1]));

        this.entityManager.getTransaction().commit();
    }

    private void findEmployeeByFirstName() throws IOException {
        String firstName2Letters = readInput("Enter First 2 Letters from Empoyee's First Name: ");

        this.entityManager.getTransaction().begin();
        List<Employee> employees = this.entityManager
                .createQuery("FROM Employee "
                        + "WHERE firstName LIKE CONCAT(:letters, '%')", Employee.class)
                .setParameter("letters", firstName2Letters)
                .getResultList();

        employees.forEach(empl -> System.out.printf("%s %s - %s - ($%s)%n",
                empl.getFirstName(),
                empl.getLastName(),
                empl.getJobTitle(),
                empl.getSalary()));

        this.entityManager.getTransaction().commit();
    }

    private void removeTowns() throws IOException {
        String townName = readInput("Enter Town Name for Deletion: ");

        this.entityManager.getTransaction().begin();

        Town townDelete = this.entityManager.createQuery("FROM Town "
                + "WHERE name = :town", Town.class)
                .setParameter("town", townName)
                .getSingleResult();

        List<Address> addressesToDelete = this.entityManager
                .createQuery("FROM Address WHERE town.id= :id", Address.class)
                .setParameter("id", townDelete.getId())
                .getResultList();

        addressesToDelete
                .forEach(t -> t.getEmployees()
                        .forEach(em -> em.setAddress(null)));

        addressesToDelete.forEach(this.entityManager::remove);
        this.entityManager.remove(townDelete);

        int countDeletedAddresses = addressesToDelete.size();
        System.out.printf("%d address%s in %s deleted",
                countDeletedAddresses,
                countDeletedAddresses == 1 ? "" : "es",
                townName);

        this.entityManager.getTransaction().commit();
    }

    private void increaseSalary() {
        double increasingRate = 0.12;
        List<String> departmentsToIncrease = Arrays.asList(
                "Engineering",
                "Tool Design",
                "Marketing",
                "Information Services");

        this.entityManager.getTransaction().begin();

        List<Employee> employees = this.entityManager
                .createQuery("FROM Employee WHERE department.name IN (:deps)", Employee.class)
                .setParameter("deps", departmentsToIncrease)
                .getResultList();

        employees
                .forEach(employee ->
                        employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1 + increasingRate))));
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

        employees.forEach(employee ->
                System.out.printf("%s %s ($%s)%n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary()));
    }

    private void findLatest10Projects() {
        this.entityManager.getTransaction().begin();

        int projectCount = 10;

        List<Project> projects = this.entityManager
                .createQuery("FROM Project ORDER BY startDate Desc,name", Project.class)
                .setMaxResults(projectCount)
                .getResultList();

        projects.stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project ->
                        System.out.printf("Project Name: %s%n\tProject Description: %s%n\tProject Start Date: %s%n\tProject End Date: %s%n",
                                project.getName(),
                                project.getDescription(),
                                project.getStartDate(),
                                project.getEndDate()));

        this.entityManager.getTransaction().commit();
    }

    private void getEmployeesWithProject() throws IOException {
        this.entityManager.getTransaction().begin();

        int employeeId = Integer.parseInt(readInput("Enter employee ID:"));

        Employee employee = this.entityManager
                .createQuery("FROM Employee WHERE id = :employeeId", Employee.class)
                .setParameter("employeeId", employeeId)
                .getSingleResult();
        System.out.printf("%s %s - %s%n\t%s",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle(),
                employee.getProjects().stream()
                        .map(Project::getName)
                        .sorted()
                        .collect(Collectors.joining(System.lineSeparator() + "\t")));


        this.entityManager.getTransaction().commit();
    }


    private void addressesWithEmployeeCount() {
        this.entityManager.getTransaction().begin();

        List<Address> addresses = this.entityManager
                .createQuery("FROM Address ORDER BY size(employees) DESC,town.id ", Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses.forEach(e -> System.out.printf("%s, %s - %s employees%n",
                e.getText(),
                e.getTown().getName(),
                e.getEmployees().size()));

        this.entityManager.getTransaction().commit();
    }

    private void addAddressAndUpdateEmployee() throws IOException {
        this.entityManager.getTransaction().begin();
        String lastName = readInput("Insert Employee Last Name:");

        Town town = this.entityManager
                .createQuery("FROM  Town WHERE id=32", Town.class)
                .getSingleResult();

        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);

        this.entityManager.persist(address);


        Employee employee = this.entityManager
                .createQuery("FROM Employee  WHERE lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getSingleResult();

        employee.setAddress(address);

        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private void employeesFromDepartment() {
        this.entityManager.getTransaction().begin();
        String departString = "Research and Development";

        List<Employee> employees = this.entityManager
                .createQuery("FROM Employee WHERE department.name = :name " +
                        "ORDER BY salary,id", Employee.class)
                .setParameter("name", departString)
                .getResultList();

        employees.forEach(employee -> System.out.printf("%s %s from %s - $%s%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartment().getName(),
                employee.getSalary()));

        this.entityManager.getTransaction().commit();
    }

    private void employeesWithSalaryOver() {
        this.entityManager.getTransaction().begin();
        BigDecimal maxSalary = new BigDecimal("50000");

        List<Employee> employees = this.entityManager
                .createQuery("FROM Employee WHERE salary > :maxSalary", Employee.class)
                .setParameter("maxSalary", maxSalary)
                .getResultList();

        employees.forEach(employee -> System.out.println(employee.getFirstName()));

        this.entityManager.getTransaction().commit();
    }

    private void removeObjects() {
        this.entityManager.getTransaction().begin();
        List<Town> towns = this.entityManager
                .createQuery("FROM Town", Town.class)
                .getResultList();

        for (Town town : towns) {
            if (town.getName().length() > 5) {
                this.entityManager.detach(town);
            }
        }

        this.entityManager.getTransaction().commit();
    }

    private void containsEmployee() throws IOException {
        String employeeName = readInput("Enter Name and Last Name");

        this.entityManager.getTransaction().begin();

        try {
            this.entityManager
                    .createQuery("FROM Employee WHERE CONCAT(firstName, ' ',  lastName) = :name", Employee.class)
                    .setParameter("name", employeeName)
                    .getSingleResult();

            this.entityManager.getTransaction().commit();
            System.out.println("Yes");
        } catch (Exception e) {
            System.out.println("No");
        }
    }

    private String readInput(String promptMsgString) throws IOException {
        System.out.println(promptMsgString);

        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        return bReader.readLine();
    }
}
