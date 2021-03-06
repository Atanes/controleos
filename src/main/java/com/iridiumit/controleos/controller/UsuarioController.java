package com.iridiumit.controleos.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.controleos.model.Usuario;
import com.iridiumit.controleos.repository.filtros.UsuarioFiltro;
import com.iridiumit.controleos.service.UsuarioService;

@Controller
@RequestMapping("/administracao/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") UsuarioFiltro filtro) {
		
		ModelAndView modelAndView = new ModelAndView("administracao/lista-usuarios");
		
		List<Usuario> usuarios = usuarioService.filtrar(filtro);

		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		
		usuarioService.excluir(id);

		attributes.addFlashAttribute("mensagem", "Usuário excluido com sucesso!!");
		
		return "redirect:/administracao/usuarios";
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return editar(usuarioService.localizar(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("/administracao/cadastro-usuario");

		modelAndView.addObject(usuario);
		
		modelAndView.addObject("permissoes", usuarioService.permissoes());

		return modelAndView;
	}
	
	@GetMapping("/editar")
	public ModelAndView editar(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("/administracao/editar-usuario");

		modelAndView.addObject(usuario);
		
		modelAndView.addObject("permissoes", usuarioService.permissoes());

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {

		Usuario u = usuarioService.localizarLogin(usuario.getLogin());
		
		if (u != null) {
			result.rejectValue("login", "erro.login");
        }
		
		if (result.hasErrors()) {
			usuario.setSenha(null);
            return novo(usuario);
        } else {
        	//usuarioDAO.adicionaUsuario(usuario);
        	usuarioService.incluir(usuario);
        	attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso!!");
        }
		
		return new ModelAndView("redirect:/administracao/usuarios/novo");
	}
	
	@PostMapping("/atualizar")
	public ModelAndView atualizar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
            return editar(usuario);
        } else {
        	usuarioService.salvar(usuario);
        	attributes.addFlashAttribute("mensagem", "Usuario atualizado com sucesso!!");
        }
		
		return new ModelAndView("redirect:/administracao/usuarios");
			
	}
}
