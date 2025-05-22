package com.iucyh.jjapcloud.unit.music;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.common.util.FileManager;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.music.repository.MusicRepository;
import com.iucyh.jjapcloud.web.dto.RequestSuccessDto;
import com.iucyh.jjapcloud.web.dto.music.CreateMusicDto;
import com.iucyh.jjapcloud.web.dto.music.MusicDto;
import com.iucyh.jjapcloud.web.service.music.MusicService;
import com.iucyh.jjapcloud.web.service.music.MusicServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {

    @Mock
    private MusicRepository musicRepository;
    private FileManager fileManager = new FileManager();

    private MusicService musicService;

    private final Integer TEST_ID = 1;
    private final String TEST_NAME = "Test Song";
    private final String TEST_SINGER = "Test Singer";
    private final Integer TEST_RUNTIME = 180;

    @BeforeEach
    void setUp() {
        musicService = new MusicServiceImpl(musicRepository, fileManager);
    }

    @Test
    @DisplayName("getMusicById should return MusicDto when music exists")
    void getMusicByIdSuccess() {
        // Arrange
        Music music = new Music();
        music.setId(TEST_ID);
        music.setName(TEST_NAME);
        music.setSinger(TEST_SINGER);
        music.setRuntime(TEST_RUNTIME);

        when(musicRepository.findById(TEST_ID)).thenReturn(Optional.of(music));

        // Act
        MusicDto result = musicService.getMusicById(TEST_ID);

        // Assert
        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_SINGER, result.getSinger());
        assertEquals(TEST_RUNTIME, result.getRuntime());
        verify(musicRepository, times(1)).findById(TEST_ID);
    }

    @Test
    @DisplayName("getMusicById should throw ServiceException when music not found")
    void getMusicByIdMusicNotFound() {
        // Arrange
        when(musicRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            musicService.getMusicById(TEST_ID);
        });

        assertEquals(ServiceErrorCode.MUSIC_NOT_FOUND, exception.getErrorCode());
        verify(musicRepository, times(1)).findById(TEST_ID);
    }

    @Test
    @DisplayName("getMusics should return list of MusicDto")
    void getMusicsSuccess() {
        // Arrange
        Date testDate = new Date();

        Music music1 = new Music();
        music1.setId(1);
        music1.setName("Song 1");
        music1.setSinger("Singer 1");
        music1.setRuntime(180);

        Music music2 = new Music();
        music2.setId(2);
        music2.setName("Song 2");
        music2.setSinger("Singer 2");
        music2.setRuntime(240);

        List<Music> musicList = Arrays.asList(music1, music2);

        when(musicRepository.findMusics(testDate)).thenReturn(musicList);

        // Act
        List<MusicDto> result = musicService.getMusics(testDate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Song 1", result.get(0).getName());
        assertEquals("Singer 2", result.get(1).getSinger());
        verify(musicRepository, times(1)).findMusics(testDate);
    }

    @Test
    @DisplayName("createMusic should return new music ID")
    void createMusicSuccess() throws IOException {
        // Arrange
        CreateMusicDto createMusicDto = new CreateMusicDto();
        createMusicDto.setName(TEST_NAME);
        createMusicDto.setSinger(TEST_SINGER);
        createMusicDto.setRuntime(TEST_RUNTIME);

        when(musicRepository.create(any(Music.class))).thenReturn(TEST_ID);

        // Act
        int result = musicService.createMusic(createMusicDto);

        // Assert
        assertEquals(TEST_ID, result);
        verify(musicRepository, times(1)).create(any(Music.class));
    }

    @Test
    @DisplayName("deleteMusic should call repository delete method and return success message")
    void deleteMusicSuccess() {
        // Act
        RequestSuccessDto result = musicService.deleteMusic(TEST_ID);

        // Assert
        assertNotNull(result);
        assertEquals("Music Delete Success", result.getMessage());
        verify(musicRepository, times(1)).delete(TEST_ID);
    }
}
