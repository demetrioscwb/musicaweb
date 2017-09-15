package br.com.playlistweb.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.playlistweb.entity.Musica;
import br.com.playlistweb.entity.Usuario;
import br.com.playlistweb.repository.MusicaRepository;

@Service
public class MusicaService {
	
	
	@Autowired
	MusicaRepository musicaRepository;

	public void save(Musica musica) {
		musicaRepository.save(musica);
	}

	public List<Musica> findMusicaByNome(Musica musica) {
		return musicaRepository.findMusicaByNome(musica);
	}

	public List<Musica> findMusicaByUsuario(Usuario usuario) {
		return musicaRepository.findMusicaByUsuario(usuario);
	}

	public List<Musica> findMusicaByNoUsuario(Usuario usuario) {
		return musicaRepository.findMusicaByNoUsuario(usuario);
	}

	public Musica findMusicaById(Integer id) {
		return musicaRepository.findMusicaById(id);
	}

	@Transactional
	public void salvarMusica(MultipartFile file, ServletContext context) {
		
		try {
			
			inserImagemDiretorio(file, context);
			
			Musica musica = new Musica();
			musica.setNome(file.getOriginalFilename());
			musica.setCaminho(context.getRealPath("/uploads/"));
					
			save(musica);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
    public void inserImagemDiretorio(MultipartFile MPFile, ServletContext context) throws Exception{

        final String PATH_ARQUIVOS  = context.getRealPath("/uploads/");
        final String PATH_ABSOLUTO  = (PATH_ARQUIVOS);

        try {

            File diretorio  = new File(PATH_ABSOLUTO);

            if(!diretorio.exists())
                diretorio.mkdir();

            String fileName = MPFile.getOriginalFilename();
            String arq[] = fileName.split("\\\\");
            for (int i = 0; i < arq.length; i++) {
                fileName = arq[i];
            }

            File file = new File(diretorio,fileName);
            FileOutputStream out = new FileOutputStream(file);
            InputStream in = MPFile.getInputStream();    

            byte[] buffer = new byte[1024 * 5];
            int nLidos;
            while ((nLidos = in.read(buffer)) >= 0) {
                out.write(buffer, 0, nLidos);
            }

            out.flush();
            out.close();


        } catch (Exception e) {
            throw new Exception("Erro ao carregar imagem para o diretorio !!\n "
                    + "Error : "      + e.getMessage() 
                    + "\nCausa : " + e.getCause());
        }

    }
	

}
