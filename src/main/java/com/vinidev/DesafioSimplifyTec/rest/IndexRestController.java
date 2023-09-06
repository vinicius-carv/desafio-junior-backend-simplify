package com.vinidev.DesafioSimplifyTec.rest;

import com.vinidev.DesafioSimplifyTec.entity.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexRestController {

    @RequestMapping("/")
    public String indexPage(Model model){
        return "index";
    }
    @GetMapping(value = "/login", produces = "application/x-www-form-urlencoded")
    public String loginPage(Model model){
        Usuario usuarioLogin = new Usuario();
        model.addAttribute("Usuario",usuarioLogin);
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return "redirect:/login";
    }
    @RequestMapping(value = "/cadastro", produces = "application/x-www-form-urlencoded")
    public String cadastroPage(Model model){
        Usuario novoUsuario = new Usuario();
        model.addAttribute("Usuario",novoUsuario);
        return "cadastro";
    }
}
