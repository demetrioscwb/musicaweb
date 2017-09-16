package br.com.playlistweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import br.com.playlistweb.entity.Musica;
import br.com.playlistweb.entity.PlayList;
import br.com.playlistweb.entity.Usuario;
import br.com.playlistweb.model.HomeVO;
import br.com.playlistweb.service.MusicaService;
import br.com.playlistweb.service.PlayListService;
import br.com.playlistweb.service.UsuarioService;
import br.com.playlistweb.util.MP3Musica;

@Path("/file")
@Controller
public class HomeController{
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MusicaService musicaService;
	
	@Autowired
	private PlayListService playListService;
	
	@Autowired
	private ServletContext context;

	private HomeVO homeVO = new HomeVO();
	
	private MP3Musica musicaX = null;
	
	@RequestMapping("/")
	public String goHome(){
		return "index";
	}
	
    @RequestMapping(value = "/init",params = {}, 
    		method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody HomeVO init() {
    	
    	homeVO.setMsg(null);
 	
    	return homeVO;
    }
    
    @RequestMapping(value = "/logout",params = {}, 
    		method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody HomeVO logout() {
 	
    	return homeVO = new HomeVO();
    }

    @RequestMapping(value = "/logar",params = {}, 
    		method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody HomeVO logar(
    		@RequestParam(value = "nome") String nome,
    		@RequestParam(value = "senha") String senha){
    	
    	List<Usuario> list = usuarioService.findUsuarioByNomeSenha(nome,senha);
    	    	
    	if(!list.isEmpty()){
    		homeVO.setMsg(null);
       		homeVO.setHasUsuario(true);    	
        	homeVO.setUsuario(list.get(0));
        	homeVO.setListMusica(musicaService.findMusicaByNoUsuario(homeVO.getUsuario()));
        	homeVO.setListPlayList(musicaService.findMusicaByUsuario(homeVO.getUsuario()));
    	}else{
    		homeVO.setMsg("Usuário não possui cadastro!");
    	}
    	    	
    	return homeVO;
    }
    
	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
	public @ResponseBody HomeVO cadastrarUsuario(@RequestBody Usuario usuario) {
		
    	List<Usuario> list = usuarioService.
    			findUsuarioByNomeSenha(usuario.getNome(),usuario.getSenha());
    	
    	if(list.isEmpty()){
    		homeVO.setMsg(null);
    		usuario = usuarioService.save(usuario);
    	}else{
    		homeVO.setMsg("Usuário já cadastro!");
    	}

		return homeVO;
		
	}
	
	@RequestMapping(value = "/addMusica", method = RequestMethod.POST)
	public @ResponseBody HomeVO addMusica(@RequestBody Musica musica) {
		
    	List<Musica> list = musicaService.findMusicaByNome(musica);
		
    	if(list.isEmpty()){
    		homeVO.setMsg(null);
    		musicaService.save(musica);
    	}else{
    		homeVO.setMsg("Musica já cadastrada!");
    	}
    	
		homeVO.setListMusica(musicaService.findMusicaByNoUsuario(homeVO.getUsuario()));
		
		return homeVO;
		
	}
	
	@RequestMapping(value = "/addMusicPlayList", method = RequestMethod.POST)
	public @ResponseBody HomeVO addMusicPlayList(@RequestBody Musica musica) {
		
		playListService.save(
				new PlayList(homeVO.getUsuario(),musica)); 
		
		homeVO.setListPlayList(musicaService.findMusicaByUsuario(homeVO.getUsuario()));
		
		homeVO.setListMusica(musicaService.findMusicaByNoUsuario(homeVO.getUsuario()));
				
		return homeVO;
		
	}
	
	@RequestMapping(value = "/removeMusic", method = RequestMethod.PUT)
	public @ResponseBody HomeVO removeMusic(@RequestBody Musica musica) {
		
		playListService.removeMusic(musica);
    	
		homeVO.setListPlayList(musicaService.findMusicaByUsuario(homeVO.getUsuario()));
		
		homeVO.setListMusica(musicaService.findMusicaByNoUsuario(homeVO.getUsuario()));
		
		return homeVO;
		
	}
	
	@RequestMapping(value = "/removeMusicPlayList", method = RequestMethod.PUT)
	public @ResponseBody HomeVO removeMusicPlayList(@RequestBody Musica musica) {
		
		playListService.removeMusicPlayList(musica,homeVO.getUsuario());
    	
		homeVO.setListPlayList(musicaService.findMusicaByUsuario(homeVO.getUsuario()));
		
		homeVO.setListMusica(musicaService.findMusicaByNoUsuario(homeVO.getUsuario()));
		
		return homeVO;
		
	}
		
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody HomeVO upload( 
            @RequestParam("file") MultipartFile file) throws Exception{
		
		musicaService.salvarMusica(file,context);	
		
		homeVO.setListPlayList(musicaService.findMusicaByUsuario(homeVO.getUsuario()));
		homeVO.setListMusica(musicaService.findMusicaByNoUsuario(homeVO.getUsuario()));
		
		
		return homeVO;
    }
	
    @RequestMapping(value = "/playMusic",params = {},method = RequestMethod.GET, 
    		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody Long playMusic(
    		@RequestParam(value = "id") Integer id) throws UnsupportedTagException, InvalidDataException, IOException {

    	Musica m = musicaService.findMusicaById(id);
			
		File fileM = new File(m.getCaminho()+m.getNome()); 
		
		Mp3File mp3file = new Mp3File(m.getCaminho()+m.getNome());			
		
		musicaX = new MP3Musica();
		
		musicaX.tocar(fileM);				

		musicaX.start();
    	    	
    	return mp3file.getLengthInSeconds();    	
    	
    }
        
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/stopMusic",params = {},method = RequestMethod.GET, 
    		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody void stopMusic(){

		musicaX.stop();
    	    	
    }
    

	
}
