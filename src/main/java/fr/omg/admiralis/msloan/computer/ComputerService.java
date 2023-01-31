package fr.omg.admiralis.msloan.computer;

import fr.omg.admiralis.msloan.course.Course;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ComputerService {
    private final ComputerRestRepository computerRestRepository;

    public ComputerService(ComputerRestRepository computerRestRepository) {
        this.computerRestRepository = computerRestRepository;
    }


    public List<Computer> findAll() {
        return computerRestRepository.findAll();
    }

    public Computer save(Computer computer) {
        return computerRestRepository.save(computer);
    }

    public Computer findById(String id) {
        return computerRestRepository.findById(id);
    }

    public void deleteById(String id) {
        computerRestRepository.deleteById(id);
    }

}
