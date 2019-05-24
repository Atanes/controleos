package com.iridiumit.controleos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.controleos.model.Cliente;
import com.iridiumit.controleos.model.Equipamento;
import com.iridiumit.controleos.repository.Clientes;
import com.iridiumit.controleos.repository.Equipamentos;
import com.iridiumit.controleos.utils.Disco;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {
	
	public static String uploadDirectory = "/public/imagens_produtos/";

	@Autowired
	private Equipamentos equipamentos;

	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Disco disco;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("tecnico/lista-equipamentos");

		modelAndView.addObject("equipamentos", equipamentos.findAll());
		return modelAndView;
	}

	@DeleteMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {

		Cliente c = equipamentos.findOne(id).getCliente();

		equipamentos.delete(id);

		attributes.addFlashAttribute("mensagem", "Equipamento excluido com sucesso!!");

		return "redirect:/equipamentos/selecao/" + c.getId();
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(equipamentos.findOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Equipamento equipamento) {
		ModelAndView modelAndView = new ModelAndView("atendimento/equipamentos/cadastro-equipamento");

		modelAndView.addObject(equipamento);
		modelAndView.addObject("clientes", clientes.findAll());

		return modelAndView;
	}

	@GetMapping("/novo/{id}")
	public ModelAndView incluir(Equipamento equipamento, @PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("atendimento/equipamentos/cadastro-equipamento");

		modelAndView.addObject(equipamento);
		modelAndView.addObject("clientes", clientes.findOne(id));

		return modelAndView;
	}
	
	@PostMapping("/salvando")
	public ModelAndView salvando(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attributes,
			MultipartFile imagem) {
		if (result.hasErrors()) {
			return novo(equipamento);
		}
		
		disco.salvarFoto(imagem);
		
		equipamento.setUrl_imagen("/imagens_produtos/" + imagem.getOriginalFilename());
		
		//Em uma aplicação publicada na WEB esse endereço deve ser trocado para um caminho no servidor de aplicações
		//ou um servidor de arquivos
		/*String path = new File("").getAbsolutePath() + "/src/main/resources/static/imagens_produtos/" + imagem.getOriginalFilename();
		
		System.out.println(path);

		File saveFile = new File(path);
		try {
			FileUtils.writeByteArrayToFile(saveFile, imagem.getBytes());
			equipamento.setUrl_imagen("/imagens_produtos/" + imagem.getOriginalFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		equipamentos.save(equipamento);

		attributes.addFlashAttribute("mensagem", "Equipamento salvo com sucesso!!");

		return new ModelAndView("redirect:/equipamentos/novo");
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(equipamento);
		}

		equipamentos.save(equipamento);

		attributes.addFlashAttribute("mensagem", "Equipamento salvo com sucesso!!");

		return new ModelAndView("redirect:/equipamentos/novo");
	}

	// Teste para preencher lista de equipamentos na tela - sem uso no momento
	@GetMapping("/selecao/{id}")
	public ModelAndView SelecaoPorCliente(@PathVariable Long id) {

		Cliente c = clientes.findOne(id);
		ModelAndView modelAndView = new ModelAndView("tecnico/lista-equipamentos");

		modelAndView.addObject("equipamentos", equipamentos.findByCliente(c));
		modelAndView.addObject("mensagem", "Equipamentos do cliente " + c.getNome());
		return modelAndView;

	}

	// Teste para preencher lista de equipamentos na tela - sem uso no momento
	@RequestMapping(value = "/lista/{id}", method = RequestMethod.GET)
	public String showGuestList(Model model, @PathVariable("id") Long id) {
		Cliente c = clientes.findOne(id);
		model.addAttribute("equipamentos", equipamentos.findByCliente(c));

		return "orcamento/lista-clientes :: resultsList";
	}
}
