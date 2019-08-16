package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.CompanyService;
import softuni.workshop.service.EmployeeService;
import softuni.workshop.service.ProjectService;

@Controller
public class HomeController extends BaseController {

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public HomeController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index(){
        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView){
        boolean areImported = this.companyService.areImported() &&
                this.projectService.areImported() &&
                this.employeeService.areImported();
        modelAndView.addObject("areImported", areImported);
        return super.view("home", modelAndView);
    }
}
