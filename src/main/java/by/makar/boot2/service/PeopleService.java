package by.makar.boot2.service;


import by.makar.boot2.models.Person;
import by.makar.boot2.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }
    public Person findOne(int id){
        Person person = peopleRepository.findById(id).orElse(null);
        if(person != null)
            Hibernate.initialize(person.getBooks());
        return person;
    }

    @Transactional
    public void save(Person person){
        person.setDateOfRegister(new Date());
        peopleRepository.save(person);
    }
    @Transactional
    public void update(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
}
