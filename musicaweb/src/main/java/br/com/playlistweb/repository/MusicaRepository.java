package br.com.playlistweb.repository;

import java.util.List;

import br.com.playlistweb.entity.Musica;
import br.com.playlistweb.entity.Usuario;

public interface MusicaRepository{

	void save(Musica musica);

	List<Musica> findMusicaByNome(Musica musica);

	List<Musica> findMusicaByUsuario(Usuario usuario);

	List<Musica> findMusicaByNoUsuario(Usuario usuario);

	Musica findMusicaById(Integer id);
	
}
