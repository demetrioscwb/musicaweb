package br.com.playlistweb.repository;

import java.util.List;

import br.com.playlistweb.entity.Musica;
import br.com.playlistweb.entity.PlayList;
import br.com.playlistweb.entity.Usuario;

public interface PlayListRepository{

	PlayList save(PlayList pl);

	List<PlayList> findPlayListByUsuario(int idusuario);

	void removeMusicPlayList(Musica musica, Usuario usuario);

	void removeMusic(Musica musica);
	
}
