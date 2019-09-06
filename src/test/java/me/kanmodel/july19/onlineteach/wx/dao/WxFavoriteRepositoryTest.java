package me.kanmodel.july19.onlineteach.wx.dao;

import me.kanmodel.july19.onlineteach.wx.entity.WxFavorite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxFavoriteRepositoryTest {
    @Autowired
    private WxFavoriteRepository wxFavoriteRepository;

    @Test
    public void insertFav() {
        WxFavorite wxFavorite = new WxFavorite(null, null);
        wxFavoriteRepository.save(wxFavorite);
    }

}