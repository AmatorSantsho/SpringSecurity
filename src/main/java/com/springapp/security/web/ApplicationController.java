package com.springapp.security.web;

import com.springapp.security.model.Role;
import com.springapp.security.model.User;
import com.springapp.security.service.UserService;
import com.springapp.security.util.DateUtil;
import com.springapp.security.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ApplicationController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    //    @Autowired
//    private UserServiceAdmin userServiceAdmin;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();
        List<SimpleGrantedAuthority> list = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        model.addAttribute("userDetails", userDetails.getName());
        model.addAttribute("list", list);
        log.info("redirect to logPage from root");
        return "index";
    }

    @RequestMapping(value = {"/log"}, method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if ("true".equals(error)) {
            model.addAttribute("errorMessage", "Invalid username or password. Try again");
            log.info("loading logPage with error");
        }
        log.info("loading logPage");
        return "log";
    }

    @RequestMapping(value = "/crud", method = RequestMethod.GET)
    public String welcome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();
        List<SimpleGrantedAuthority> list = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        model.addAttribute("userDetails", userDetails.getName());
        model.addAttribute("list", list);
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        User user = new User();

        model.addAttribute("userToEdit", user);
        return "crud";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id, Model model) {
        userService.deleteUser(id);
        log.info("User with id=" + id + " was deleted");
        return "redirect:/crud";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, Model model) {
        User userToEdit = userService.get(id);
        log.info(userToEdit + " is find");
        model.addAttribute("userToEdit", userToEdit);
        log.info("adding " + userToEdit + " to model");
        return "crud";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam(value = "id", required = false)  String id ,
                      @RequestParam(value = "name") String name,
                      @RequestParam(value = "password") String password,
                      @RequestParam(value = "roles") String roles,
                      @RequestParam(value = "email") String email,
                      @RequestParam(value = "datatime") String datatime) {

        Set<Role> rolesRole = new HashSet<>();
        if (!roles.isEmpty()) {
            String[] stringsRoles = roles.split(",");
            for (int i = 0; i < stringsRoles.length; i++) {
                stringsRoles[i] = stringsRoles[i].replaceAll("\\[|\\]|\\s","");
                rolesRole.add(Role.valueOf(stringsRoles[i]));
            }
        }
        User user = new User(id==null ? null : Integer.valueOf(id), name.isEmpty() ? "DefaultName" : name,
                id==null ? PasswordUtil.encodePasswordToBcrypt(password) : password,
                email.isEmpty() ? "Default@email.ru" : email,rolesRole,
                datatime.isEmpty() ? new Date() : DateUtil.converteStringToDate(datatime));
        if (user.isNew()) {
            Date date = user.getRegistration();
            log.info("date is " + date);
            userService.create(user);
            log.info(user + "was created and save in DB");
        } else {

            Date date = user.getRegistration();
            log.info("date is " + date);
            userService.update(user);

            log.info(user + "was  save in DB");

        }

        return "redirect:/crud";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(ModelMap map) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                map.addAttribute("userDetails", userDetails);
        map.addAttribute("userAuthorities", userDetails.getAuthorities());
        log.info("loading adminPage");
        return "admin";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied() {
        log.info("loading accessDeniedPage");
        return "accessDenied";
    }
}
