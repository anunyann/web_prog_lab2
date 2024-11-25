package mk.finki.ukim.wp.lab.web.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.wp.lab.model.Artist;
import mk.finki.ukim.wp.lab.model.Song;
import mk.finki.ukim.wp.lab.service.ArtistService;
import mk.finki.ukim.wp.lab.service.SongService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name="song details", urlPatterns = "/songDetails")
public class SongDetailsServlet extends HttpServlet {

    public final SpringTemplateEngine springTemplateEngine;
    public final SongService songService;
    public final ArtistService artistService;

    public SongDetailsServlet(SpringTemplateEngine springTemplateEngine, SongService songService, ArtistService artistService) {
        this.springTemplateEngine = springTemplateEngine;
        this.songService = songService;
        this.artistService = artistService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        String trackId = req.getParameter("trackId");
        Song song = songService.findById(Long.parseLong(trackId));
        context.setVariable("song", song);
        assert song!=null;
        List<Artist> artists = song.getPerformers();
        context.setVariable("artists", artists);
        springTemplateEngine.process("songDetails.html",context, resp.getWriter());
    }

}
