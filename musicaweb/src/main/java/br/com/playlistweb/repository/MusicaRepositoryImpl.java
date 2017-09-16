package br.com.playlistweb.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.playlistweb.entity.Musica;
import br.com.playlistweb.entity.Usuario;

@Repository
@Transactional
public class MusicaRepositoryImpl implements MusicaRepository{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void save(Musica musica) {
		manager.merge(musica);
	}

	@Override
	public List<Musica> findMusicaByNome(Musica musica) {
	    String hql = "SELECT NEW br.com.playlistweb.entity.Musica(m) FROM Musica m "
	    		+ " WHERE m.nome = '"+musica.getNome()+"'";
	    
	    TypedQuery<Musica> query = manager.createQuery(hql,Musica.class);	  
	    
	    return query.getResultList();
	}
	
	@Override
	public List<Musica> findMusicaByUsuario(Usuario usuario) {
	    String hql = "SELECT NEW br.com.playlistweb.entity.Musica(pl.musica) FROM PlayList pl "
	    		+ " WHERE pl.usuario.id = " + usuario.getId();	  
	    
	    TypedQuery<Musica> query = manager.createQuery(hql,Musica.class);
	    
	    return query.getResultList();
	}

	@Override
	public List<Musica> findMusicaByNoUsuario(Usuario usuario) {
	    String hql = "SELECT NEW br.com.playlistweb.entity.Musica(m) FROM Musica m "
	    		+ " WHERE m.id NOT IN ("
	    					+ "SELECT pl.musica.id FROM PlayList pl"
	    					+ " WHERE pl.usuario.id = " + usuario.getId()
	    					+ ")";
	    
	    TypedQuery<Musica> query = manager.createQuery(hql,Musica.class);	  
	    
	    return query.getResultList();
	}

	@Override
	public Musica findMusicaById(Integer id) {
		
		return manager.find(Musica.class,id);
		
	}


	
}
