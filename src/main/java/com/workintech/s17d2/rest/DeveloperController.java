package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.workintech.s17d2.tax.Taxable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private final Taxable taxable;

    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    public Map<Integer, Developer> developers = new HashMap<Integer, Developer>();

    @PostConstruct
    public void init() {
        developers.put(1,new Developer(1, "Numan", 75000, Experience.MID));
        developers.put(2, new Developer(2, "Begüm", 50000, Experience.JUNIOR));
        developers.put(3, new Developer(3, "Çiğdem", 95000, Experience.SENIOR));
    }

    @GetMapping
    public List<Developer> getAllDevelopers(){
        return new ArrayList<>(developers.values());
    }

    @GetMapping("/{id}")
    public Developer getDeveloperById(@PathVariable int id) {
        return developers.get(id);
    }

    @PutMapping("/{id}")
    public Developer updateDeveloper(@PathVariable int id, @RequestBody Developer updatedDev) {
        developers.put(id, updatedDev);
        return updatedDev;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Developer createDeveloper(@RequestBody Developer developer) {
        int id = developer.getId();
        String name = developer.getName();
        double salary = developer.getSalary();
        Experience experience = developer.getExperience();

        Developer newDev;

        switch (experience) {
            case JUNIOR:
                salary -= salary * (taxable.getSimpleTaxRate() / 100);
                newDev = new JuniorDeveloper(id, name, salary);
                break;
            case MID:
                salary -= salary * (taxable.getMiddleTaxRate() / 100);
                newDev = new MidDeveloper(id, name, salary);
                break;
            case SENIOR:
                salary -= salary * (taxable.getUpperTaxRate() / 100);
                newDev = new SeniorDeveloper(id, name, salary);
                break;
            default:
                throw new IllegalArgumentException("Geçersiz deneyim seviyesi.");
        }

        developers.put(id, newDev);
        return newDev;
    }

    @DeleteMapping("/{id}")
    public Developer deleteDeveloper(@PathVariable int id) {
        return developers.remove(id);
    }
}
