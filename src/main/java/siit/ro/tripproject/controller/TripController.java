package siit.ro.tripproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import siit.ro.tripproject.model.Trip;
import siit.ro.tripproject.model.User;
import siit.ro.tripproject.persistence.TripRepository;
import siit.ro.tripproject.persistence.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TripController {
    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "/tripPage", method = RequestMethod.GET)
    public ModelAndView showTrips(@RequestParam(name = "id", required = false) Long id) {

        // We  saved all the trips from the repo in a list
        List<Trip> tripList = (List<Trip>) tripRepository.findAll();

        User looged = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Trip> tripListByUser = new ArrayList<>();

        // We create a new list populated with all the trips of the currently logged user
        for (Trip trip : tripList) {
            if (trip.getUser() != null && trip.getUser().equals(looged)) {
                tripListByUser.add(trip);
            }
        }

        // If the logged user doesn't have any trips, we show him a new view and invite him to create a new trip

        if (tripListByUser.size() == 0) {
            ModelAndView model1 = new ModelAndView("noTrips");
            return model1;
        }

        // In the view there is a dropdown, here we choose which trip is the selected trip, by a id parameter given or by default first from the list
        Trip currentTrip = null;
        if (id != null) {
            for (Trip trip : tripListByUser) {
                if (trip.getId().equals(id)) {
                    currentTrip = trip;
                    break;
                }
            }
        }
        if (currentTrip == null) {
            currentTrip = tripListByUser.iterator().next();
        }

        // We inject into the view the list of trips and the selected trip

        ModelAndView model = new ModelAndView("tripPage");
        model.addObject("tripList", tripListByUser);
        model.addObject("selectedId", currentTrip.getId());
        model.addObject("currentTrip", currentTrip);
        return model;

    }
}




