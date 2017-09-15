package br.com.playlistweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.playlistweb.entity.Usuario;
import br.com.playlistweb.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Transactional 
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public List<Usuario> findUsuarioByNomeSenha(String nome, String senha) {
		
		List<Usuario> list = usuarioRepository.findUsuarioByNomeSenha(nome, senha);
		
		return list;
	}

}
