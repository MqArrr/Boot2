package by.makar.boot2.controllers;

import by.makar.boot2.models.Person;
import by.makar.boot2.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("library/people")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String people(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "/people/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Person person = peopleService.findOne(id);
        model.addAttribute("person", person);
        model.addAttribute("booksOfPerson", person.getBooks());
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") Person person){
        peopleService.save(person);
        return "redirect:/library/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/library/people";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute Person person, Model model){
        System.out.println("Editing");
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, @PathVariable("id") int id, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "people/new";
        person.setId(id);
        peopleService.update(person);
        return "redirect:/library/people/" + person.getId();
    }
}
