package com.vinidev.DesafioSimplifyTec.rest;

import com.vinidev.DesafioSimplifyTec.entity.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexRestController {

    @RequestMapping("/")
    public String indexPage(Model model){
        return "index";
    }
    @GetMapping("/login")
    public String loginPage(Model model){
        return "login";
    }
    @RequestMapping(value = "/cadastro", produces = "application/x-www-form-urlencoded")
    public String cadastroPage(Model model){
        Usuario novoUsuario = new Usuario();
        model.addAttribute("Usuario",novoUsuario);
        return "cadastro";
    }
}
