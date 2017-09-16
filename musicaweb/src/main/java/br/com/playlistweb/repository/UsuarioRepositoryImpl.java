package br.com.playlistweb.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.playlistweb.entity.Usuario;

@Repository
@Transactional
public class UsuarioRepositoryImpl implements UsuarioRepository{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Usuario save(Usuario usuario) {
		return manager.merge(usuario);
	}

	@Override
	public List<Usuario> findUsuarioByNomeSenha(String nome, String senha) {
		
	    String hql = "SELECT u FROM Usuario u "
	    		+ " WHERE u.nome = '"+nome+"'"
	    		+ " AND u.senha = '"+senha+"'";
	    
	    TypedQuery<Usuario> query = manager.createQuery(hql, Usuario.class);	  
	    
	    return query.getResultList();
	}


	
}
