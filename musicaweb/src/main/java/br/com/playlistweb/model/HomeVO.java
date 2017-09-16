package br.com.playlistweb.model;

import java.util.List;

import br.com.playlistweb.entity.Musica;
import br.com.playlistweb.entity.Usuario;

public class HomeVO {

	private Usuario usuario;
	
	private boolean hasUsuario;
	
	private Musica musica;
	
	private List<Musica> listMusica;
	
	private List<Musica> listPlayList;
	
	private String msg;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Musica> getListMusica() {
		return listMusica;
	}

	public void setListMusica(List<Musica> listMusica) {
		this.listMusica = listMusica;
	}

	public List<Musica> getListPlayList() {
		return listPlayList;
	}

	public void setListPlayList(List<Musica> listPlayList) {
		this.listPlayList = listPlayList;
	}

	public Musica getMusica() {
		return musica;
	}

	public void setMusica(Musica musica) {
		this.musica = musica;
	}

	public boolean isHasUsuario() {
		return hasUsuario;
	}

	public void setHasUsuario(boolean hasUsuario) {
		this.hasUsuario = hasUsuario;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
