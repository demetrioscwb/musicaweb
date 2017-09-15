package br.com.playlistweb.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.playlistweb.entity.Musica;
import br.com.playlistweb.entity.PlayList;
import br.com.playlistweb.entity.Usuario;

@Repository
@Transactional
public class PlayListRepositoryImpl implements PlayListRepository{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public PlayList save(PlayList pl) {
		return manager.merge(pl);
	}

	@Override
	public List<PlayList> findPlayListByUsuario(int idusuario) {
	    String hql = "SELECT pl FROM PlayList pl "
	    		+ " WHERE pl.usuario.id = "+idusuario;
	    
	    Query query = manager.createQuery(hql);	
	    return query.getResultList();
	}

	@Override
	public void removeMusicPlayList(Musica musica, Usuario usuario) {
		Query query = manager.createQuery("delete from PlayList AS pl"
				+ " WHERE pl.musica.id = " + musica.getId()
	    		+ " AND pl.usuario.id = " + usuario.getId()
		);
		query.executeUpdate();
	}

	@Override
	public void removeMusic(Musica musica) {
		Query query = manager.createQuery("delete from Musica "
				+ " WHERE id = " + musica.getId()
		);
		query.executeUpdate();
	}

	
}
