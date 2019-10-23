package siit.ro.tripproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import siit.ro.tripproject.model.Trip;
import siit.ro.tripproject.model.User;
import siit.ro.tripproject.persistence.TripRepository;
import siit.ro.tripproject.persistence.UserRepository;
import siit.ro.tripproject.service.FileService;

import java.util.UUID;

//This is the controller that manages the creation, deletion and editing of the trips

@Controller
public class AddTripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private FileService fileService;


    @RequestMapping(value = "/addTrip")
    public ModelAndView addTrip() {
        ModelAndView model = new ModelAndView("addTrip");
        Trip trip = new Trip();
        model.addObject("trip", trip);
        return model;
    }

    @RequestMapping(value = "/tripPage", method = RequestMethod.POST)
    public String saveTrip(
            @RequestParam("file") MultipartFile[] file,
            @ModelAttribute Trip trip) {
        ModelAndView model = new ModelAndView("addTrip");

        trip.setPhoto1(UUID.randomUUID().toString()); // we generate a uuid and save the name in Photo1 in Db
        trip.setPhoto2(UUID.randomUUID().toString()); // same as above with photo 2

        // We are setting on the new trip the id of the currently logged user

        User looged = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        trip.setUser(looged);

        // Saving the trip in the trip repository
        tripRepository.save(trip);

        //We are saving the photos in a specific location under a uuid name that we generated above

        fileService.store(trip.getPhoto1(), file[0]);
        fileService.store(trip.getPhoto2(), file[1]);

        return "redirect:/tripPage";
    }

// We delete a trip from the trip repository by looking for the id

    @RequestMapping(value = "/deleteTrip")
    public String deleteTrip(@RequestParam("id") Long id) {
        tripRepository.deleteById(id);

        return "redirect:/tripPage";
    }

    @RequestMapping(value = "/editTrip")
    public ModelAndView editTrip(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView("/editTrip");
        Trip edited = tripRepository.findById(id).get();
        model.addObject("trip", edited);

        return model;
    }

    @RequestMapping(value = "/editTrip", method = RequestMethod.POST)
    public String editTrip(
            @RequestParam("file") MultipartFile[] file,
            @ModelAttribute Trip trip) {

        // In the edit window we check to see if we added new photos to replace the old ones, and if so we save them.

        if (file[0].isEmpty() & file[1].isEmpty()) {
            trip.setPhoto1((tripRepository.findById(trip.getId())).get().getPhoto1());
            trip.setPhoto2((tripRepository.findById(trip.getId())).get().getPhoto2());

            User looged = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            trip.setUser(looged);
            tripRepository.save(trip);

            return "redirect:/tripPage";
        } else if (file[0].isEmpty() & !file[1].isEmpty()) {
            trip.setPhoto1((tripRepository.findById(trip.getId())).get().getPhoto1());
            trip.setPhoto2(UUID.randomUUID().toString());


            User looged = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            trip.setUser(looged);
            tripRepository.save(trip);

            fileService.store(trip.getPhoto2(), file[1]);

            return "redirect:/tripPage";
        } else if (!file[0].isEmpty() & file[1].isEmpty()) {
            trip.setPhoto1(UUID.randomUUID().toString());
            trip.setPhoto2((tripRepository.findById(trip.getId())).get().getPhoto2());


            User looged = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            trip.setUser(looged);
            tripRepository.save(trip);

            fileService.store(trip.getPhoto1(), file[0]);

            return "redirect:/tripPage";
        } else {

            trip.setPhoto1(UUID.randomUUID().toString());
            trip.setPhoto2(UUID.randomUUID().toString());


            User looged = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            trip.setUser(looged);
            tripRepository.save(trip);

            fileService.store(trip.getPhoto1(), file[0]);
            fileService.store(trip.getPhoto2(), file[1]);

            return "redirect:/tripPage";
        }
    }

}
