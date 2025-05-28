package com.iucyh.jjapcloud.unit.music;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import com.iucyh.jjapcloud.common.util.FileManager;
import com.iucyh.jjapcloud.domain.music.Music;
import com.iucyh.jjapcloud.domain.music.repository.MusicRepository;
import com.iucyh.jjapcloud.web.dto.IdDto;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {

    @Mock
    private MusicRepository musicRepository;
    
    @Mock
    private FileManager fileManager;

    private MusicService musicService;

    private final Integer TEST_ID = 1;
    private final String TEST_ORIGINAL_NAME = "Test Song";
    private final String TEST_SINGER = "Test Singer";
    private final Long TEST_PLAY_TIME = 180L;

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
        music.setOriginalName(TEST_ORIGINAL_NAME);
        music.setSinger(TEST_SINGER);
        music.setPlayTime(TEST_PLAY_TIME);
    
        when(musicRepository.findById(TEST_ID)).thenReturn(Optional.of(music));
    
        // Act
        MusicDto result = musicService.getMusicById(TEST_ID);
    
        // Assert
        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_ORIGINAL_NAME, result.getOriginalName());
        assertEquals(TEST_SINGER, result.getSinger());
        assertEquals(TEST_PLAY_TIME, result.getPlayTime());
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
        music1.setOriginalName("Song 1");
        music1.setSinger("Singer 1");
        music1.setPlayTime(180L);
    
        Music music2 = new Music();
        music2.setId(2);
        music2.setOriginalName("Song 2");
        music2.setSinger("Singer 2");
        music2.setPlayTime(240L);
    
        List<Music> musicList = Arrays.asList(music1, music2);
    
        when(musicRepository.findMusics(testDate)).thenReturn(musicList);
    
        // Act
        List<MusicDto> result = musicService.getMusics(testDate);
    
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Song 1", result.get(0).getOriginalName());
        assertEquals("Singer 2", result.get(1).getSinger());
        verify(musicRepository, times(1)).findMusics(testDate);
    }

    @Test
    @DisplayName("createMusic should return new music ID")
    void createMusicSuccess() throws IOException {
        // Arrange
        MockMultipartFile musicFile = new MockMultipartFile(
            "musicFile", 
            "test.mp3", 
            "audio/mpeg", 
            "test file content".getBytes()
        );
        
        CreateMusicDto createMusicDto = new CreateMusicDto();
        createMusicDto.setName(TEST_ORIGINAL_NAME);
        createMusicDto.setSinger(TEST_SINGER);
        createMusicDto.setMusicFile(musicFile);
        
        when(fileManager.isCorrectMimeType(any(), eq("audio/mpeg"))).thenReturn(true);
        when(fileManager.uploadFile(any(), eq("music"))).thenReturn("storedFileName.mp3");
        when(fileManager.getPlayTime(anyLong(), anyInt())).thenReturn(TEST_PLAY_TIME);
        when(musicRepository.create(any(Music.class))).thenReturn(TEST_ID);
    
        // Act
        IdDto result = musicService.createMusic(createMusicDto);
    
        // Assert
        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        verify(musicRepository, times(1)).create(any(Music.class));
        verify(fileManager).isCorrectMimeType(any(), eq("audio/mpeg"));
        verify(fileManager).uploadFile(any(), eq("music"));
    }

    @Test
    @DisplayName("deleteMusic should call repository delete method and return success message")
    void deleteMusicSuccess() {
        // Act
        musicService.deleteMusic(TEST_ID);

        // Assert
        verify(musicRepository, times(1)).delete(TEST_ID);
    }
    
    @Test
    @DisplayName("createMusic should throw exception when file type is invalid")
    void createMusicInvalidFileType() throws IOException {
        // Arrange
        MockMultipartFile musicFile = new MockMultipartFile(
            "musicFile", 
            "test.txt", 
            "text/plain", 
            "test file content".getBytes()
        );
        
        CreateMusicDto createMusicDto = new CreateMusicDto();
        createMusicDto.setName(TEST_ORIGINAL_NAME);
        createMusicDto.setSinger(TEST_SINGER);
        createMusicDto.setMusicFile(musicFile);
        
        when(fileManager.isCorrectMimeType(any(), eq("audio/mpeg"))).thenReturn(false);
    
        // Act & Assert
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            musicService.createMusic(createMusicDto);
        });
    
        assertEquals(ServiceErrorCode.NOT_VALID_MUSIC_FILE, exception.getErrorCode());
        verify(fileManager).isCorrectMimeType(any(), eq("audio/mpeg"));
        verify(musicRepository, never()).create(any(Music.class));
    }
}
