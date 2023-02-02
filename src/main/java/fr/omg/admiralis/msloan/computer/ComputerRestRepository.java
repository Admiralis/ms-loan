package fr.omg.admiralis.msloan.computer;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Repository
public class ComputerRestRepository {

    private final String computerUrl;
    private final RestTemplate restTemplate;

    public ComputerRestRepository(RestTemplate restTemplate, @Value("${ms.computer.url}") String computerUrl) {
        this.restTemplate = restTemplate;
        this.computerUrl = computerUrl;
    }

    public List<Computer> findAll() {
        Computer[] computers = restTemplate.getForObject(computerUrl, Computer[].class);
        return computers == null ? Collections.emptyList() : List.of(computers);
    }

    public Computer save(Computer computer) {
        return restTemplate.postForObject(computerUrl, computer, Computer.class);
    }

    public Computer findById(String id) {
        return restTemplate.getForObject(computerUrl + "/" + id, Computer.class);
    }

    public void deleteById(String id) {
        restTemplate.delete(computerUrl + "/" + id);
    }

    public Computer update(Computer updateComputer) {
        return restTemplate.patchForObject(computerUrl + "/" + updateComputer.getId(), updateComputer, Computer.class);
    }

    public Computer findBySerialNumber(String serialNumber) {
        return restTemplate.getForObject(computerUrl + "/search?serialNumber=" + serialNumber, Computer.class);
    }
}
