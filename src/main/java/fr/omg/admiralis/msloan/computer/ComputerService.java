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

    public Computer update(Computer updateComputer) {
        Computer computer = computerRestRepository.findById(updateComputer.getId());
        if (computerRestRepository.findById(updateComputer.getId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Computer not found");
        }
        return computerRestRepository.update(computer);
    }

    public Computer replace(Computer replaceComputer) {
        Computer computer = computerRestRepository.findById(replaceComputer.getId());

        if (computerRestRepository.findById(replaceComputer.getId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Computer not found");
        }

        computer.setProcessor(replaceComputer.getProcessor());
        computer.setRam(replaceComputer.getRam());
        computer.setCondition(replaceComputer.getCondition());
        computer.setComputerStatus(replaceComputer.getComputerStatus());
        computer.setComments(replaceComputer.getComments());

        return computerRestRepository.update(computer);
    }

}
