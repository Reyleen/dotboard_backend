package it.uniroma3.siw.dotboard_backend.services;

import it.uniroma3.siw.dotboard_backend.model.Theme;
import it.uniroma3.siw.dotboard_backend.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    @Transactional
    public void deleteTheme(Theme theme){
        theme.getBoards().forEach(board -> board.setTheme(null));
        theme.setDeletedAt(new Date());
        this.themeRepository.save(theme);
    }

    @Transactional
    public Theme createTheme(Theme theme){
        return this.themeRepository.save(theme);
    }
}
