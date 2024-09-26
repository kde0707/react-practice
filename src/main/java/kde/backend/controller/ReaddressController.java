package kde.backend.controller;

import kde.backend.domain.Readdress;
import kde.backend.domain.Reuser;
import kde.backend.dto.ReaddressRequest;
import kde.backend.repository.ReaddressRepository;
import kde.backend.repository.ReuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ReaddressController {

    private ReaddressRepository readdressRepository;
    private ReuserRepository reuserRepository;

    public ReaddressController(ReaddressRepository readdressRepository, ReuserRepository reuserRepository) {
        this.readdressRepository = readdressRepository;
        this.reuserRepository = reuserRepository;
    }

    @GetMapping("/addrs")
    public Iterable<Readdress> getReaddress() {
        return readdressRepository.findAll();
    }

    @PostMapping("/addrs")
    public Readdress createReaddress(@RequestBody ReaddressRequest readdressRequest) {
        String zip = readdressRequest.getZip();
        String addr = readdressRequest.getAddr();
        String username = readdressRequest.getUsername();

        Readdress readdress = new Readdress();
        readdress.setZip(zip);
        readdress.setAddr(addr);

        Optional<Reuser> optionalReuser = reuserRepository.findByUsername(username);
        if (optionalReuser.isPresent()) {
            Reuser reuser = optionalReuser.get();
            readdress.setReuser(reuser);
        }
        return readdressRepository.save(readdress);
    }
}