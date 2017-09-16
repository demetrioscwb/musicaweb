package br.com.playlistweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.playlistweb.entity.Musica;
import br.com.playlistweb.entity.PlayList;
import br.com.playlistweb.entity.Usuario;
import br.com.playlistweb.repository.PlayListRepository;

@Service
public class PlayListService {
	
	
	@Autowired
	PlayListRepository playListRepository;

	public PlayList save(PlayList pl) {
		return playListRepository.save(pl);
	}

	public List<PlayList> findPlayListByUsuario(int idusuario) {
		return playListRepository.findPlayListByUsuario(idusuario);
	}

	public void removeMusicPlayList(Musica musica, Usuario usuario) {
		playListRepository.removeMusicPlayList(musica, usuario);
	}

	public void removeMusic(Musica musica) {
		playListRepository.removeMusic(musica);
	}

}
