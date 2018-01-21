package com.springapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Controller
public class ApplicationController {
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
        return "welcome";
    }

    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if ("true".equals(error)) {
            model.addAttribute("errorMessage", "Invalid username or password. Try again");
        }
        return "log";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(ModelMap map) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> securedMessage = userService.getAuthorities(userDetails);
        map.addAttribute("userDetails", userDetails);
        map.addAttribute("userAuthorities", securedMessage);
        return "admin";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied() {
        return "accessDenied";
    }
}
