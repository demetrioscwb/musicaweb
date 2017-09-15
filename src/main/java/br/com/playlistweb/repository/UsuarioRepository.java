package br.com.playlistweb.repository;

import java.util.List;

import br.com.playlistweb.entity.Usuario;

public interface UsuarioRepository{

	Usuario save(Usuario usuario);

	List<Usuario> findUsuarioByNomeSenha(String nome, String senha);
	
}
