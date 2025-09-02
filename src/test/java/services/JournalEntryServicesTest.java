package services;

import dto.JournalEntryDTO;
import entity.JournalEntry;
import entity.UserEntry;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.JournalEntryRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JournalEntryServicesTest {

    @Mock
    private JournalEntryRepository journalEntryRepository;

    @Mock
    private UserEntryServices userEntryServices;

    @InjectMocks
    private JournalEntryServices journalEntryServices;

    private UserEntry user;

    @BeforeEach
    void setUp() {
        user = UserEntry.builder()
                .id(new ObjectId())
                .userName("johndoe")
                .password("password")
                .roles(List.of("User"))
                .journalEntries(new ArrayList<>())
                .build();
    }

    @Test
    void saveEntry_ShouldSaveJournalEntryAndAttachToUser() {
        // Arrange
        JournalEntryDTO dto = new JournalEntryDTO();
        dto.setTitle("My Title");
        dto.setContent("My Content");

        JournalEntry saved = new JournalEntry();
        saved.setId(new ObjectId());
        saved.setTitle("My Title");
        saved.setContent("My Content");
        saved.setDate(LocalDateTime.now());

        when(journalEntryRepository.save(any(JournalEntry.class))).thenReturn(saved);
        when(userEntryServices.findUserByName("johndoe")).thenReturn(user);

        // Act
        JournalEntry result = journalEntryServices.saveEntry(dto, "johndoe");

        // Assert
        assertNotNull(result);
        assertEquals("My Title", result.getTitle());
        assertEquals(1, user.getJournalEntries().size());
        verify(userEntryServices).saveUserEntry(user);
    }

    @Test
    void saveEntry_ShouldThrow_WhenUserNotFound() {
        JournalEntryDTO dto = new JournalEntryDTO();
        dto.setTitle("My Title");
        dto.setContent("My Content");

        when(journalEntryRepository.save(any(JournalEntry.class))).thenReturn(new JournalEntry());
        when(userEntryServices.findUserByName("johndoe")).thenReturn(null);

        assertThrows(JournalEntrySaveException.class,
                () -> journalEntryServices.saveEntry(dto, "johndoe"));
    }

    @Test
    void deleteById_ShouldRemoveEntry_WhenFound() {
        ObjectId entryId = new ObjectId();
        JournalEntry entry = new JournalEntry();
        entry.setId(entryId);

        user.getJournalEntries().add(entry);

        when(userEntryServices.findUserByName("johndoe")).thenReturn(user);

        journalEntryServices.deleteById(entryId, "johndoe");

        assertTrue(user.getJournalEntries().isEmpty());
        verify(journalEntryRepository).deleteById(entryId);
        verify(userEntryServices).saveUserEntry(user);
    }

    @Test
    void deleteById_ShouldThrow_WhenExceptionOccurs() {
        ObjectId entryId = new ObjectId();
        when(userEntryServices.findUserByName("johndoe")).thenThrow(new RuntimeException("DB error"));

        assertThrows(JournalEntryDeleteException.class,
                () -> journalEntryServices.deleteById(entryId, "johndoe"));
    }

    @Test
    void getJournalEntriesByName_ShouldReturnEntries_WhenUserExists() {
        JournalEntry entry = new JournalEntry();
        entry.setTitle("Test");

        user.getJournalEntries().add(entry);

        when(userEntryServices.findUserByName("johndoe")).thenReturn(user);

        List<JournalEntry> result = journalEntryServices.getJournalEntriesByName("johndoe");

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getTitle());
    }

    @Test
    void getJournalEntriesByName_ShouldReturnEmpty_WhenUserNotFound() {
        when(userEntryServices.findUserByName("johndoe")).thenReturn(null);

        List<JournalEntry> result = journalEntryServices.getJournalEntriesByName("johndoe");

        assertTrue(result.isEmpty());
    }

    @Test
    void findById_ShouldReturnEntry_WhenFound() {
        ObjectId id = new ObjectId();
        JournalEntry entry = new JournalEntry();
        entry.setId(id);

        when(journalEntryRepository.findById(id)).thenReturn(Optional.of(entry));

        Optional<JournalEntry> result = journalEntryServices.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }
}
